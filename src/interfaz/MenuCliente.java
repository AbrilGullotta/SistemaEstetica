package interfaz;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Cliente;
import modelo.Profesional;
import modelo.Servicio;
import modelo.Usuario;
import servicio.ServicioService;
import servicio.TurnoService;
import servicio.UsuarioService;

public class MenuCliente {

    private Cliente clienteLogueado;
    private TurnoService turnoService = new TurnoService();
    private ServicioService servicioService = new ServicioService();
    private UsuarioService usuarioService = new UsuarioService();

    public MenuCliente(Usuario usuario) {
        this.clienteLogueado = new Cliente();
        this.clienteLogueado.setIdUsuario(usuario.getIdUsuario());
        this.clienteLogueado.setNombre(usuario.getNombre());
        this.clienteLogueado.setApellido(usuario.getApellido());
        this.clienteLogueado.setEmail(usuario.getEmail());
    }

    public void mostrarMenuCliente() {
        int opcion;

        do {
        	opcion = Integer.parseInt(JOptionPane.showInputDialog(
        	        " MENU CLIENTE - " + clienteLogueado.getNombre() + "\n"
        	        + "1. Reservar turno\n"
        	        + "2. Consultar mis turnos\n"
        	        + "3. Ver servicios disponibles\n"
        	        + "4. Ver profesionales\n"
        	        + "5. Pagar seña\n"
        	        + "0. Volver"
        	));

            switch (opcion) {
            case 1: reservarTurno(); break;
            case 2: consultarTurnos(); break;
            case 3: verServicios(); break;
            case 4: verProfesionales(); break;
            case 5:
                JOptionPane.showMessageDialog(null, "Pantalla de pago de seña");
                break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida");
            }

        } while (opcion != 0);
    }

    private void reservarTurno() {

        // 1. Elegir servicio
        ArrayList<Servicio> servicios = servicioService.listarServicios();
        if (servicios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay servicios disponibles.");
            return;
        }

        StringBuilder sbServ = new StringBuilder("Elegí un servicio:\n");
        for (int i = 0; i < servicios.size(); i++) {
            Servicio s = servicios.get(i);
            sbServ.append((i + 1) + ". " + s.getNombre()
                    + " - $" + s.getPrecio()
                    + " - " + s.getDuracion() + "\n");
        }

        int numServ = Integer.parseInt(JOptionPane.showInputDialog(sbServ.toString()));
        if (numServ < 1 || numServ > servicios.size()) {
            JOptionPane.showMessageDialog(null, "Opción inválida.");
            return;
        }
        Servicio servicioElegido = servicios.get(numServ - 1);

        // 2. Elegir profesional
        ArrayList<Profesional> profesionales = usuarioService.listarProfesionales();
        if (profesionales.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay profesionales disponibles.");
            return;
        }

        StringBuilder sbProf = new StringBuilder("Elegí un profesional:\n");
        for (int i = 0; i < profesionales.size(); i++) {
            Profesional p = profesionales.get(i);
            sbProf.append((i + 1) + ". " + p.getNombre() + " " + p.getApellido()
                    + " - " + p.getEspecialidad() + "\n");
        }

        int numProf = Integer.parseInt(JOptionPane.showInputDialog(sbProf.toString()));
        if (numProf < 1 || numProf > profesionales.size()) {
            JOptionPane.showMessageDialog(null, "Opción inválida.");
            return;
        }
        Profesional profesionalElegido = profesionales.get(numProf - 1);

        // 3. Elegir fecha
        String fecha = JOptionPane.showInputDialog("Ingresá la fecha (AAAA-MM-DD):");
        if (fecha == null || fecha.trim().isEmpty()) return;

        // 4. Mostrar horarios disponibles
        ArrayList<String> horarios = turnoService.obtenerHorariosDisponibles(
                profesionalElegido.getIdUsuario(),
                fecha.trim(),
                servicioElegido.getDuracion()
        );

        if (horarios.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No hay horarios disponibles para esa fecha con ese profesional.");
            return;
        }

        StringBuilder sbHor = new StringBuilder("Horarios disponibles:\n");
        for (int i = 0; i < horarios.size(); i++) {
            sbHor.append((i + 1) + ". " + horarios.get(i) + "\n");
        }

        int numHor = Integer.parseInt(JOptionPane.showInputDialog(sbHor.toString()));
        if (numHor < 1 || numHor > horarios.size()) {
            JOptionPane.showMessageDialog(null, "Opción inválida.");
            return;
        }
        String horaElegida = horarios.get(numHor - 1);

        // 5. Confirmar y reservar
        String confirmacion = JOptionPane.showInputDialog(
                "Confirmás la reserva?\n"
                + "Servicio: " + servicioElegido.getNombre() + "\n"
                + "Profesional: " + profesionalElegido.getNombre() + " " + profesionalElegido.getApellido() + "\n"
                + "Fecha: " + fecha + "\n"
                + "Hora: " + horaElegida + "\n"
                + "Precio: $" + servicioElegido.getPrecio() + "\n\n"
                + "Escribí SI para confirmar:"
        );

        if (confirmacion == null || !confirmacion.trim().equalsIgnoreCase("SI")) {
            JOptionPane.showMessageDialog(null, "Reserva cancelada.");
            return;
        }

        String resultado = turnoService.reservarTurno(
                clienteLogueado, profesionalElegido,
                servicioElegido, fecha.trim(), horaElegida
        );

        if ("OK".equals(resultado)) {
            JOptionPane.showMessageDialog(null, "¡Turno reservado correctamente!");
        } else {
            JOptionPane.showMessageDialog(null, resultado);
        }
    }

    private void consultarTurnos() {

        ArrayList<modelo.Turno> turnos = turnoService.listarTurnosPorCliente(
                clienteLogueado.getIdUsuario()
        );

        if (turnos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tenés turnos registrados.");
            return;
        }

        StringBuilder sb = new StringBuilder("Tus turnos:\n\n");
        for (modelo.Turno t : turnos) {
            sb.append("Fecha: " + t.getFecha() + " " + t.getHora() + "\n");
            sb.append("Servicio: " + t.getServicio().getNombre() + "\n");
            sb.append("Profesional: " + t.getProfesional().getNombre()
                    + " " + t.getProfesional().getApellido() + "\n");
            sb.append("Estado: " + t.getEstado() + "\n");
            sb.append("-----------------------------\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }
    private void verServicios() {

        ArrayList<Servicio> servicios = servicioService.listarServicios();

        if (servicios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay servicios disponibles.");
            return;
        }

        StringBuilder sb = new StringBuilder("Servicios disponibles:\n\n");
        for (Servicio s : servicios) {
            sb.append("• " + s.getNombre() + "\n");
            sb.append("  Precio: $" + s.getPrecio() + "\n");
            sb.append("  Duración: " + s.getDuracion() + "\n");
            sb.append("-----------------------------\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private void verProfesionales() {

        ArrayList<Profesional> profesionales = usuarioService.listarProfesionales();

        if (profesionales.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay profesionales disponibles.");
            return;
        }

        StringBuilder sb = new StringBuilder("Profesionales disponibles:\n\n");
        for (Profesional p : profesionales) {
            sb.append("• " + p.getNombre() + " " + p.getApellido() + "\n");
            sb.append("  Especialidad: " + p.getEspecialidad() + "\n");
            sb.append("  Teléfono: " + p.getTelefono() + "\n");
            sb.append("-----------------------------\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }
}