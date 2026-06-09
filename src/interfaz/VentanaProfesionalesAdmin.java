package interfaz;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelo.Profesional;

public class VentanaProfesionalesAdmin extends JFrame {

    private JTable tablaProfesionales;
    private DefaultTableModel modelo;

    public VentanaProfesionalesAdmin(ArrayList<Profesional> profesionales) {

        setTitle("Profesionales registrados");
        setBounds(250, 150, 900, 400);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modelo = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Apellido", "Especialidad", "Email", "Teléfono"},
                0
        );

        tablaProfesionales = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tablaProfesionales);
        scroll.setBounds(20, 20, 840, 300);
        add(scroll);

        cargarProfesionales(profesionales);
    }

    private void cargarProfesionales(ArrayList<Profesional> profesionales) {

        modelo.setRowCount(0);

        for (Profesional p : profesionales) {
            modelo.addRow(new Object[]{
                    p.getIdUsuario(),
                    p.getNombre(),
                    p.getApellido(),
                    p.getEspecialidad(),
                    p.getEmail(),
                    p.getTelefono()
            });
        }
    }
}