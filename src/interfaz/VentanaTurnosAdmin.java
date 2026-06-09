package interfaz;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Turno;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import servicio.TurnoService;

public class VentanaTurnosAdmin extends JFrame {

    private JTable tablaTurnos;
    private DefaultTableModel modelo;
    private JButton btnCambiarEstado;
    private JComboBox<String> comboEstado;
    private TurnoService turnoService = new TurnoService();

    public VentanaTurnosAdmin(ArrayList<Turno> turnos) {

        setTitle("Turnos registrados");
        setBounds(200, 120, 1000, 430);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modelo = new DefaultTableModel(
                new String[]{
                        "ID",
                        "Cliente",
                        "Profesional",
                        "Servicio",
                        "Fecha",
                        "Hora",
                        "Estado"
                },
                0
        );

        tablaTurnos = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tablaTurnos);
        scroll.setBounds(20, 20, 940, 330);
        add(scroll);

        comboEstado = new JComboBox<>(new String[]{"CONFIRMADO", "CANCELADO", "COMPLETADO"});
        comboEstado.setBounds(250, 365, 180, 30);
        add(comboEstado);

        btnCambiarEstado = new JButton("Cambiar estado seleccionado");
        btnCambiarEstado.setBounds(450, 365, 250, 30);
        add(btnCambiarEstado);

        btnCambiarEstado.addActionListener(e -> cambiarEstadoSeleccionado());
        cargarTurnos(turnos);
    }

    private void cargarTurnos(ArrayList<Turno> turnos) {

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
    private void cambiarEstadoSeleccionado() {

        int fila = tablaTurnos.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccioná un turno de la tabla.");
            return;
        }

        int idTurno = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
        String nuevoEstado = comboEstado.getSelectedItem().toString();

        int confirmar = JOptionPane.showConfirmDialog(
                null,
                "¿Confirmás cambiar el estado del turno ID " + idTurno + " a " + nuevoEstado + "?",
                "Confirmar cambio",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmar == JOptionPane.YES_OPTION) {

            String resultado = turnoService.cambiarEstado(idTurno, nuevoEstado);

            if ("OK".equals(resultado)) {
                JOptionPane.showMessageDialog(null, "Estado actualizado correctamente.");
                modelo.setValueAt(nuevoEstado, fila, 6);
            } else {
                JOptionPane.showMessageDialog(null, resultado);
            }
        }
    }
}