package interfaz;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelo.Turno;
import servicio.SeniaService;

public class VentanaRegistrarSeniaAdmin extends JFrame {

    private JTable tablaTurnos;
    private DefaultTableModel modelo;
    private JButton btnRegistrarSenia;
    private ArrayList<Turno> turnos;
    private SeniaService seniaService = new SeniaService();
    

    public VentanaRegistrarSeniaAdmin(ArrayList<Turno> turnos) {
    	this.turnos = turnos;
        setTitle("Registrar seña");
        setBounds(250, 120, 950, 430);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modelo = new DefaultTableModel(
                new String[]{
                        "ID",
                        "Cliente",
                        "Servicio",
                        "Fecha",
                        "Hora",
                        "Precio",
                        "Seña 30%"
                },
                0
        );

        tablaTurnos = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tablaTurnos);
        scroll.setBounds(20, 20, 890, 300);
        add(scroll);

        btnRegistrarSenia = new JButton("Registrar seña seleccionada");
        btnRegistrarSenia.setBounds(330, 340, 260, 30);
        add(btnRegistrarSenia);

        btnRegistrarSenia.addActionListener(e -> registrarSeniaSeleccionada());

        cargarTurnos(turnos);
    }

    private void cargarTurnos(ArrayList<Turno> turnos) {

        modelo.setRowCount(0);

        for (Turno t : turnos) {

            double montoSenia = seniaService.calcularMonto(
                    t.getServicio().getPrecio()
            );

            modelo.addRow(new Object[]{
                    t.getIdTurno(),
                    t.getCliente().getNombre() + " " + t.getCliente().getApellido(),
                    t.getServicio().getNombre(),
                    t.getFecha(),
                    t.getHora(),
                    "$" + t.getServicio().getPrecio(),
                    "$" + String.format("%.2f", montoSenia)
            });
        }
    }

    private void registrarSeniaSeleccionada() {

        int fila = tablaTurnos.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccioná un turno de la tabla.");
            return;
        }

        int idTurno = Integer.parseInt(modelo.getValueAt(fila, 0).toString());

        int confirmar = JOptionPane.showConfirmDialog(
                null,
                "¿Confirmás registrar la seña del turno ID " + idTurno + "?",
                "Confirmar seña",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmar == JOptionPane.YES_OPTION) {

            Turno turno = buscarTurnoPorId(idTurno);

            if (turno == null) {
                JOptionPane.showMessageDialog(null, "No se encontró el turno seleccionado.");
                return;
            }

            String resultado = seniaService.registrarSenia(turno);

            if (resultado.startsWith("OK")) {
                JOptionPane.showMessageDialog(null, "Seña registrada correctamente.");
                modelo.removeRow(fila);
            } else {
                JOptionPane.showMessageDialog(null, resultado);
            }
        }
    }

    private Turno buscarTurnoPorId(int idTurno) {

        for (Turno t : turnos) {

            if (t.getIdTurno() == idTurno) {
                return t;
            }

        }

        return null;
    }
}