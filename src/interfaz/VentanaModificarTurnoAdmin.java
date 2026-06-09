package interfaz;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelo.Profesional;
import modelo.Servicio;
import modelo.Turno;
import servicio.ServicioService;
import servicio.TurnoService;
import servicio.UsuarioService;

public class VentanaModificarTurnoAdmin extends JFrame {

    private JTable tablaTurnos;
    private DefaultTableModel modelo;
    private JButton btnModificar;

    private ArrayList<Turno> turnos;
    private TurnoService turnoService = new TurnoService();

    public VentanaModificarTurnoAdmin(ArrayList<Turno> turnos) {

        this.turnos = turnos;

        setTitle("Modificar turno");
        setBounds(200, 120, 1000, 430);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modelo = new DefaultTableModel(
                new String[]{"ID", "Cliente", "Profesional", "Servicio", "Fecha", "Hora", "Estado"},
                0
        );

        tablaTurnos = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tablaTurnos);
        scroll.setBounds(20, 20, 940, 300);
        add(scroll);

        btnModificar = new JButton("Modificar turno seleccionado");
        btnModificar.setBounds(360, 335, 260, 30);
        add(btnModificar);

        btnModificar.addActionListener(e -> modificarSeleccionado());

        cargarTurnos();
    }

    private void cargarTurnos() {

        modelo.setRowCount(0);

        for (Turno t : turnos) {
            modelo.addRow(new Object[]{
                    t.getIdTurno(),
                    t.getCliente().getNombre() + " " + t.getCliente().getApellido(),
                    t.getProfesional().getNombre() + " " + t.getProfesional().getApellido(),
                    t.getServicio().getNombre(),
                    t.getFecha(),
                    t.getHora(),
                    t.getEstado()
            });
        }
    }

    private void modificarSeleccionado() {

        int fila = tablaTurnos.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccioná un turno de la tabla.");
            return;
        }

        Turno turno = turnos.get(fila);

        String nuevaFecha = JOptionPane.showInputDialog(
                "Nueva fecha (AAAA-MM-DD):",
                turno.getFecha()
        );

        if (nuevaFecha == null || nuevaFecha.trim().isEmpty()) return;

        ServicioService servicioService = new ServicioService();
        ArrayList<Servicio> servicios = servicioService.listarServicios();

        if (servicios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay servicios disponibles.");
            return;
        }

        Servicio servicioElegido = elegirServicio(servicios);
        if (servicioElegido == null) return;

        UsuarioService usuarioService = new UsuarioService();
        ArrayList<Profesional> profesionales = usuarioService.listarProfesionales();

        if (profesionales.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay profesionales disponibles.");
            return;
        }

        Profesional profesionalElegido = elegirProfesional(profesionales);
        if (profesionalElegido == null) return;

        ArrayList<String> horarios = turnoService.obtenerHorariosDisponibles(
                profesionalElegido.getIdUsuario(),
                nuevaFecha.trim(),
                servicioElegido.getDuracion()
        );

        if (horarios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay horarios disponibles para esa fecha y profesional.");
            return;
        }

        String horaElegida = elegirHorario(horarios);
        if (horaElegida == null) return;

        int confirmar = JOptionPane.showConfirmDialog(
                null,
                "Confirmás la modificación?\n\n"
                + "Fecha: " + nuevaFecha.trim() + "\n"
                + "Hora: " + horaElegida + "\n"
                + "Servicio: " + servicioElegido.getNombre() + "\n"
                + "Profesional: " + profesionalElegido.getNombre() + " " + profesionalElegido.getApellido(),
                "Confirmar modificación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmar == JOptionPane.YES_OPTION) {

            String resultado = turnoService.modificarTurno(
                    turno.getIdTurno(),
                    nuevaFecha.trim(),
                    horaElegida,
                    profesionalElegido.getIdUsuario(),
                    servicioElegido.getIdServicio()
            );

            if ("OK".equals(resultado)) {
                JOptionPane.showMessageDialog(null, "Turno modificado correctamente.");

                turno.setFecha(nuevaFecha.trim());
                turno.setHora(horaElegida);
                turno.setProfesional(profesionalElegido);
                turno.setServicio(servicioElegido);

                modelo.setValueAt(profesionalElegido.getNombre() + " " + profesionalElegido.getApellido(), fila, 2);
                modelo.setValueAt(servicioElegido.getNombre(), fila, 3);
                modelo.setValueAt(nuevaFecha.trim(), fila, 4);
                modelo.setValueAt(horaElegida, fila, 5);

            } else {
                JOptionPane.showMessageDialog(null, resultado);
            }
        }
    }

    private Servicio elegirServicio(ArrayList<Servicio> servicios) {

        StringBuilder sb = new StringBuilder("Seleccioná el servicio:\n");

        for (int i = 0; i < servicios.size(); i++) {
            Servicio s = servicios.get(i);
            sb.append((i + 1)).append(". ")
                    .append(s.getNombre())
                    .append(" - $")
                    .append(s.getPrecio())
                    .append("\n");
        }

        String input = JOptionPane.showInputDialog(sb.toString());

        if (input == null) return null;

        int num = Integer.parseInt(input);

        if (num < 1 || num > servicios.size()) return null;

        return servicios.get(num - 1);
    }

    private Profesional elegirProfesional(ArrayList<Profesional> profesionales) {

        StringBuilder sb = new StringBuilder("Seleccioná el profesional:\n");

        for (int i = 0; i < profesionales.size(); i++) {
            Profesional p = profesionales.get(i);
            sb.append((i + 1)).append(". ")
                    .append(p.getNombre()).append(" ")
                    .append(p.getApellido()).append(" - ")
                    .append(p.getEspecialidad())
                    .append("\n");
        }

        String input = JOptionPane.showInputDialog(sb.toString());

        if (input == null) return null;

        int num = Integer.parseInt(input);

        if (num < 1 || num > profesionales.size()) return null;

        return profesionales.get(num - 1);
    }

    private String elegirHorario(ArrayList<String> horarios) {

        StringBuilder sb = new StringBuilder("Horarios disponibles:\n");

        for (int i = 0; i < horarios.size(); i++) {
            sb.append((i + 1)).append(". ")
                    .append(horarios.get(i))
                    .append("\n");
        }

        String input = JOptionPane.showInputDialog(sb.toString());

        if (input == null) return null;

        int num = Integer.parseInt(input);

        if (num < 1 || num > horarios.size()) return null;

        return horarios.get(num - 1);
    }
}