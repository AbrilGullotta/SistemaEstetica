package interfaz;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelo.Turno;

public class VentanaMisTurnosCliente extends JFrame {

    private JTable tablaTurnos;
    private DefaultTableModel modelo;

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
}