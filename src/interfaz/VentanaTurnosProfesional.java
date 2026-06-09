package interfaz;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelo.Turno;

public class VentanaTurnosProfesional extends JFrame {

    private JTable tablaTurnos;
    private DefaultTableModel modelo;

    public VentanaTurnosProfesional(ArrayList<Turno> turnos) {

        setTitle("Turnos asignados");
        setBounds(250, 150, 900, 400);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modelo = new DefaultTableModel(
                new String[]{
                        "ID",
                        "Fecha",
                        "Hora",
                        "Cliente",
                        "Servicio",
                        "Estado"
                },
                0
        );

        tablaTurnos = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tablaTurnos);
        scroll.setBounds(20, 20, 840, 300);
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
                    t.getCliente().getNombre() + " " + t.getCliente().getApellido(),
                    t.getServicio().getNombre(),
                    t.getEstado()
            });
        }
    }
}