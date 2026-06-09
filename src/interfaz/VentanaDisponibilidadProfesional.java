package interfaz;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelo.Disponibilidad;

public class VentanaDisponibilidadProfesional extends JFrame {

    private JTable tablaDisponibilidad;
    private DefaultTableModel modelo;

    public VentanaDisponibilidadProfesional(ArrayList<Disponibilidad> lista) {

        setTitle("Mi disponibilidad");
        setBounds(350, 150, 650, 400);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modelo = new DefaultTableModel(
                new String[]{"Fecha", "Hora inicio", "Hora fin"},
                0
        );

        tablaDisponibilidad = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tablaDisponibilidad);
        scroll.setBounds(20, 20, 590, 300);
        add(scroll);

        cargarDisponibilidad(lista);
    }

    private void cargarDisponibilidad(ArrayList<Disponibilidad> lista) {

        modelo.setRowCount(0);

        for (Disponibilidad d : lista) {
            modelo.addRow(new Object[]{
                    d.getDia(),
                    d.getHoraInicio(),
                    d.getHoraFin()
            });
        }
    }
}