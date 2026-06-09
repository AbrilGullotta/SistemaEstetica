package interfaz;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import servicio.TurnoService;
import modelo.Turno;

public class VentanaMisTurnosCliente extends JFrame {

    private JTable tablaTurnos;
    private DefaultTableModel modelo;
    private JButton btnCancelar;
    private TurnoService turnoService = new TurnoService();
    
    public VentanaMisTurnosCliente(ArrayList<Turno> turnos) {

        setTitle("Mis Turnos");
        setBounds(250, 150, 900, 400);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modelo = new DefaultTableModel(
                new String[]{
                        "ID",
                        "Fecha",
                        "Hora",
                        "Servicio",
                        "Profesional",
                        "Estado"
                },
                0
        );

        tablaTurnos = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tablaTurnos);
        scroll.setBounds(20, 20, 840, 300);
        btnCancelar = new JButton("Cancelar turno seleccionado");
        btnCancelar.setBounds(320, 330, 250, 30);
        add(btnCancelar);

        btnCancelar.addActionListener(e -> cancelarTurnoSeleccionado());
        add(scroll);

        cargarTurnos(turnos);
    }

    private void cargarTurnos(ArrayList<Turno> turnos) {

        modelo.setRowCount(0);

        for (Turno t : turnos) {

            modelo.addRow(new Object[]{
                    t.getIdTurno(),
                    t.getFecha(),
                    t.getHora(),
                    t.getServicio().getNombre(),
                    t.getProfesional().getNombre()
                            + " "
                            + t.getProfesional().getApellido(),
                    t.getEstado()
            });
        }
    }
    private void cancelarTurnoSeleccionado() {

        int fila = tablaTurnos.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccioná un turno de la tabla.");
            return;
        }

        int idTurno = Integer.parseInt(modelo.getValueAt(fila, 0).toString());

        String servicio = modelo.getValueAt(fila, 3).toString();
        String fecha = modelo.getValueAt(fila, 1).toString();
        String hora = modelo.getValueAt(fila, 2).toString();

        int confirmar = JOptionPane.showConfirmDialog(
                null,
                "¿Confirmás la cancelación del turno?\n"
                + fecha + " " + hora + " - " + servicio,
                "Confirmar cancelación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmar == JOptionPane.YES_OPTION) {
            String resultado = turnoService.cancelarTurno(idTurno);

            if ("OK".equals(resultado)) {
                JOptionPane.showMessageDialog(null, "Turno cancelado correctamente.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, resultado);
            }
        }
    }
}