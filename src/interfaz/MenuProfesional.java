package interfaz;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Disponibilidad;
import modelo.Turno;
import modelo.Usuario;
import repository.DisponibilidadRepository;
import servicio.TurnoService;

public class MenuProfesional {

    private Usuario profesionalLogueado;
    private DisponibilidadRepository dispRepo = new DisponibilidadRepository();
    private TurnoService turnoService = new TurnoService();

    public MenuProfesional(Usuario usuario) {
        this.profesionalLogueado = usuario;
    }

    public void mostrarMenuProfesional() {
        int opcion;

        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(
                    "MENU PROFESIONAL - " + profesionalLogueado.getNombre() + "\n"
                    + "1. Cargar disponibilidad\n"
                    + "2. Ver mi disponibilidad\n"
                    + "3. Consultar turnos asignados\n"
                    + "0. Volver"
            ));

            switch (opcion) {
                case 1:
                    cargarDisponibilidad();
                    break;
                case 2:
                    verDisponibilidad();
                    break;
                case 3:
                    consultarTurnos();
                    break;
                case 0:
                    JOptionPane.showMessageDialog(null, "Volviendo al menú principal");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida");
            }

        } while (opcion != 0);
    }

    private void cargarDisponibilidad() {

        String dia = JOptionPane.showInputDialog("Fecha (AAAA-MM-DD):");
        if (dia == null || dia.trim().isEmpty()) return;

        String horaInicio = JOptionPane.showInputDialog("Hora de inicio (HH:MM:SS):");
        if (horaInicio == null || horaInicio.trim().isEmpty()) return;

        String horaFin = JOptionPane.showInputDialog("Hora de fin (HH:MM:SS):");
        if (horaFin == null || horaFin.trim().isEmpty()) return;

        Disponibilidad d = new Disponibilidad();
        d.setDia(dia.trim());
        d.setHoraInicio(horaInicio.trim());
        d.setHoraFin(horaFin.trim());
        d.setIdProfesional(profesionalLogueado.getIdUsuario());

        boolean ok = dispRepo.guardar(d);

        if (ok) {
            JOptionPane.showMessageDialog(null, "Disponibilidad cargada correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Error al cargar disponibilidad.");
        }
    }

    private void verDisponibilidad() {

        ArrayList<Disponibilidad> lista = dispRepo.listarPorProfesional(
                profesionalLogueado.getIdUsuario()
        );

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tenés disponibilidad cargada.");
            return;
        }

        StringBuilder sb = new StringBuilder("Tu disponibilidad:\n\n");
        for (Disponibilidad d : lista) {
            sb.append("Fecha: " + d.getDia() + "\n");
            sb.append("De " + d.getHoraInicio() + " a " + d.getHoraFin() + "\n");
            sb.append("-----------------------------\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private void consultarTurnos() {

        ArrayList<Turno> turnos = turnoService.listarTurnosPorProfesional(
                profesionalLogueado.getIdUsuario()
        );

        if (turnos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tenés turnos asignados.");
            return;
        }

        StringBuilder sb = new StringBuilder("Tus turnos asignados:\n\n");
        for (Turno t : turnos) {
            sb.append("Fecha: " + t.getFecha() + " " + t.getHora() + "\n");
            sb.append("Cliente: " + t.getCliente().getNombre()
                    + " " + t.getCliente().getApellido() + "\n");
            sb.append("Servicio: " + t.getServicio().getNombre() + "\n");
            sb.append("Estado: " + t.getEstado() + "\n");
            sb.append("-----------------------------\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }
}