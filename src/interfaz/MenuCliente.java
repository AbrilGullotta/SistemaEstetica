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
import servicio.SeniaService;
import util.Validador;
import java.text.SimpleDateFormat;
import javax.swing.JPanel;
import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MenuCliente extends JFrame{

    private Cliente clienteLogueado;
    private TurnoService turnoService = new TurnoService();
    private ServicioService servicioService = new ServicioService();
    private UsuarioService usuarioService = new UsuarioService();
    
    private JLabel lblTitulo;
    private JButton btnReservarTurno;
    private JButton btnConsultarTurnos;
    private JButton btnCancelarTurno;
    private JButton btnVerServicios;
    private JButton btnVerProfesionales;
    private JButton btnPagarSenia;
    private JButton btnVerPerfil;
    private JButton btnModificarPerfil;
    private JButton btnCerrar;

    public MenuCliente(Usuario usuario) {
        this.clienteLogueado = new Cliente();
        this.clienteLogueado.setIdUsuario(usuario.getIdUsuario());
        this.clienteLogueado.setNombre(usuario.getNombre());
        this.clienteLogueado.setApellido(usuario.getApellido());
        this.clienteLogueado.setEmail(usuario.getEmail());
    inicializarVentana();
    }
    
    private void inicializarVentana() {
        setTitle("Menú Cliente");
        setLayout(null);
        setBounds(400, 100, 430, 520);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        lblTitulo = new JLabel("MENÚ CLIENTE - " + clienteLogueado.getNombre());
        lblTitulo.setBounds(120, 20, 250, 30);
        add(lblTitulo);

        btnReservarTurno = new JButton("Reservar turno");
        btnReservarTurno.setBounds(110, 70, 200, 30);
        add(btnReservarTurno);

        btnConsultarTurnos = new JButton("Consultar mis turnos");
        btnConsultarTurnos.setBounds(110, 110, 200, 30);
        add(btnConsultarTurnos);

        btnCancelarTurno = new JButton("Cancelar turno");
        btnCancelarTurno.setBounds(110, 150, 200, 30);
        add(btnCancelarTurno);

        btnVerServicios = new JButton("Ver servicios disponibles");
        btnVerServicios.setBounds(110, 190, 200, 30);
        add(btnVerServicios);

        btnVerProfesionales = new JButton("Ver profesionales");
        btnVerProfesionales.setBounds(110, 230, 200, 30);
        add(btnVerProfesionales);

        btnPagarSenia = new JButton("Pagar seña");
        btnPagarSenia.setBounds(110, 270, 200, 30);
        add(btnPagarSenia);

        btnVerPerfil = new JButton("Mi perfil");
        btnVerPerfil.setBounds(110, 310, 200, 30);
        add(btnVerPerfil);

        btnModificarPerfil = new JButton("Modificar mis datos");
        btnModificarPerfil.setBounds(110, 350, 200, 30);
        add(btnModificarPerfil);

        btnCerrar = new JButton("Cerrar sesión");
        btnCerrar.setBounds(110, 400, 200, 30);
        add(btnCerrar);

        btnReservarTurno.addActionListener(e -> reservarTurno());
        btnConsultarTurnos.addActionListener(e -> consultarTurnos());
        btnCancelarTurno.addActionListener(e -> cancelarTurno());
        btnVerServicios.addActionListener(e -> verServicios());
        btnVerProfesionales.addActionListener(e -> verProfesionales());
        btnPagarSenia.addActionListener(e -> pagarSenia());
        btnVerPerfil.addActionListener(e -> verPerfil());
        btnModificarPerfil.addActionListener(e -> modificarPerfil());
        btnCerrar.addActionListener(e -> {
            dispose();
            MenuPrincipal menu = new MenuPrincipal();
            menu.setVisible(true);
        });
    }
    
    public void mostrarMenuCliente() {
        setVisible(true);
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

        String inputServ = JOptionPane.showInputDialog(sbServ.toString());
        if (inputServ == null) return;
        int numServ = Integer.parseInt(inputServ);
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

        String inputProf = JOptionPane.showInputDialog(sbProf.toString());
        if (inputProf == null) return;
        int numProf = Integer.parseInt(inputProf);
        if (numProf < 1 || numProf > profesionales.size()) {
            JOptionPane.showMessageDialog(null, "Opción inválida.");
            return;
        }
        Profesional profesionalElegido = profesionales.get(numProf - 1);

        // 3. Elegir fecha
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");

        JPanel panelFecha = new JPanel();
        panelFecha.add(dateChooser);

        int resultadoFecha = JOptionPane.showConfirmDialog(
                null,
                panelFecha,
                "Seleccioná la fecha del turno",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (resultadoFecha != JOptionPane.OK_OPTION || dateChooser.getDate() == null) {
            return;
        }

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = formato.format(dateChooser.getDate());

        if (!Validador.esFechaFutura(fecha)) {
            JOptionPane.showMessageDialog(null, "La fecha debe ser hoy o en el futuro.");
            return;
        }

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

        String inputHor = JOptionPane.showInputDialog(sbHor.toString());
        if (inputHor == null) return;
        int numHor = Integer.parseInt(inputHor);
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

        VentanaServiciosCliente ventana = new VentanaServiciosCliente(servicios);
        ventana.setVisible(true);
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

    private void cancelarTurno() {

        ArrayList<modelo.Turno> turnos = turnoService.listarTurnosPorCliente(
                clienteLogueado.getIdUsuario()
        );

        ArrayList<modelo.Turno> cancelables = new ArrayList<>();
        for (modelo.Turno t : turnos) {
            if (t.getEstado().equals("RESERVADO") || t.getEstado().equals("CONFIRMADO")) {
                cancelables.add(t);
            }
        }

        if (cancelables.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tenés turnos activos para cancelar.");
            return;
        }

        StringBuilder sb = new StringBuilder("¿Cuál turno querés cancelar?\n\n");
        for (int i = 0; i < cancelables.size(); i++) {
            modelo.Turno t = cancelables.get(i);
            sb.append((i + 1) + ". " + t.getFecha() + " " + t.getHora()
                    + " | " + t.getServicio().getNombre()
                    + " | " + t.getProfesional().getNombre() + " " + t.getProfesional().getApellido()
                    + "\n");
        }
        sb.append("\nIngresá el número (0 para volver):");

        String inputNum = JOptionPane.showInputDialog(sb.toString());
        if (inputNum == null) return;
        int num = Integer.parseInt(inputNum);
        if (num == 0 || num > cancelables.size()) return;

        modelo.Turno turnoElegido = cancelables.get(num - 1);

        int confirmar = JOptionPane.showConfirmDialog(null,
                "¿Confirmás la cancelación del turno?\n"
                + turnoElegido.getFecha() + " " + turnoElegido.getHora()
                + " - " + turnoElegido.getServicio().getNombre(),
                "Confirmar cancelación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmar == JOptionPane.YES_OPTION) {
            String resultado = turnoService.cancelarTurno(turnoElegido.getIdTurno());
            if ("OK".equals(resultado)) {
                JOptionPane.showMessageDialog(null, "Turno cancelado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, resultado);
            }
        }
    }

    private void pagarSenia() {

        ArrayList<modelo.Turno> turnos = turnoService.listarTurnosPorCliente(
                clienteLogueado.getIdUsuario()
        );

        ArrayList<modelo.Turno> reservados = new ArrayList<>();
        for (modelo.Turno t : turnos) {
            if (t.getEstado().equals("RESERVADO")) {
                reservados.add(t);
            }
        }

        if (reservados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tenés turnos RESERVADOS para señar.");
            return;
        }

        StringBuilder sb = new StringBuilder("¿A qué turno querés pagar la seña?\n\n");
        for (int i = 0; i < reservados.size(); i++) {
            modelo.Turno t = reservados.get(i);
            sb.append((i + 1) + ". " + t.getFecha() + " " + t.getHora()
                    + " | " + t.getServicio().getNombre()
                    + " | $" + t.getServicio().getPrecio() + "\n");
        }
        sb.append("\nIngresá el número (0 para volver):");

        String inputNum = JOptionPane.showInputDialog(sb.toString());
        if (inputNum == null) return;
        int num = Integer.parseInt(inputNum);
        if (num == 0 || num > reservados.size()) return;

        modelo.Turno turnoElegido = reservados.get(num - 1);
        SeniaService seniaService = new SeniaService();
        double montoSenia = seniaService.calcularMonto(turnoElegido.getServicio().getPrecio());

        int confirmar = JOptionPane.showConfirmDialog(null,
                "Seña del 30% para el turno:\n"
                + turnoElegido.getFecha() + " " + turnoElegido.getHora()
                + " - " + turnoElegido.getServicio().getNombre() + "\n\n"
                + "Monto a pagar: $" + String.format("%.2f", montoSenia) + "\n\n"
                + "¿Confirmás el pago?",
                "Pagar seña",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmar == JOptionPane.YES_OPTION) {
            String resultado = seniaService.registrarSenia(turnoElegido);

            if (resultado.startsWith("OK")) {
                double monto = Double.parseDouble(resultado.split(":")[1]);
                JOptionPane.showMessageDialog(null,
                        "¡Seña registrada!\n"
                        + "Monto: $" + String.format("%.2f", monto) + "\n"
                        + "El turno fue confirmado.");
            } else {
                JOptionPane.showMessageDialog(null, resultado);
            }
        }
    }

    private void verPerfil() {

        UsuarioService us = new UsuarioService();
        modelo.Usuario u = us.buscarPorId(clienteLogueado.getIdUsuario());

        if (u == null) {
            JOptionPane.showMessageDialog(null, "No se pudo obtener la información del perfil.");
            return;
        }

        JOptionPane.showMessageDialog(null,
                "MI PERFIL\n\n"
                + "Nombre: " + u.getNombre() + " " + u.getApellido() + "\n"
                + "DNI: " + (u.getDni() != null ? u.getDni() : "-") + "\n"
                + "Email: " + u.getEmail() + "\n"
                + "Teléfono: " + (u.getTelefono() != null ? u.getTelefono() : "-") + "\n"
                + "Fecha de nacimiento: " + (u.getFechaNacimiento() != null ? u.getFechaNacimiento() : "-")
        );
    }

    private void modificarPerfil() {

        UsuarioService us = new UsuarioService();
        modelo.Usuario u = us.buscarPorId(clienteLogueado.getIdUsuario());

        if (u == null) {
            JOptionPane.showMessageDialog(null, "No se pudo obtener la información del perfil.");
            return;
        }

        String nuevoNombre   = JOptionPane.showInputDialog("Nombre:", u.getNombre());
        if (nuevoNombre == null) return;

        String nuevoApellido = JOptionPane.showInputDialog("Apellido:", u.getApellido());
        if (nuevoApellido == null) return;

        String nuevoDni  = JOptionPane.showInputDialog("DNI:", u.getDni());
        if (!Validador.esDniValido(nuevoDni)) {
            JOptionPane.showMessageDialog(null, "DNI inválido. Debe tener 7 u 8 dígitos.");
            return;
        }

        String nuevoTel = JOptionPane.showInputDialog("Teléfono:", u.getTelefono());
        if (!Validador.esTelefonoValido(nuevoTel)) {
            JOptionPane.showMessageDialog(null, "Teléfono inválido. Solo números, entre 8 y 15 dígitos.");
            return;
        }

        String nuevaFnac = JOptionPane.showInputDialog("Fecha de nacimiento (AAAA-MM-DD):", u.getFechaNacimiento());
        if (!Validador.esFechaValida(nuevaFnac)) {
            JOptionPane.showMessageDialog(null, "Fecha inválida. Formato: AAAA-MM-DD.");
            return;
        }

        u.setNombre(nuevoNombre.trim());
        u.setApellido(nuevoApellido.trim());
        u.setDni(nuevoDni.trim());
        u.setTelefono(nuevoTel.trim());
        u.setFechaNacimiento(nuevaFnac.trim());

        String resultado = us.modificarDatos(u);

        if ("OK".equals(resultado)) {
            clienteLogueado.setNombre(u.getNombre());
            clienteLogueado.setApellido(u.getApellido());
            JOptionPane.showMessageDialog(null, "Datos actualizados correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, resultado);
        }
    }
}