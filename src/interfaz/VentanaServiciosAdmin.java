package interfaz;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelo.Servicio;

public class VentanaServiciosAdmin extends JFrame {

    private JTable tablaServicios;
    private DefaultTableModel modelo;

    public VentanaServiciosAdmin(ArrayList<Servicio> servicios) {

        setTitle("Servicios registrados");
        setBounds(250, 150, 700, 400);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modelo = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Precio", "Duración"},
                0
        );

        tablaServicios = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tablaServicios);
        scroll.setBounds(20, 20, 640, 300);
        add(scroll);

        cargarServicios(servicios);
    }

    private void cargarServicios(ArrayList<Servicio> servicios) {

        modelo.setRowCount(0);

        for (Servicio s : servicios) {

            modelo.addRow(new Object[]{
                    s.getIdServicio(),
                    s.getNombre(),
                    s.getPrecio(),
                    s.getDuracion()
            });

        }
    }
}