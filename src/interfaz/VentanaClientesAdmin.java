package interfaz;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelo.Cliente;

public class VentanaClientesAdmin extends JFrame {

    private JTable tablaClientes;
    private DefaultTableModel modelo;

    public VentanaClientesAdmin(ArrayList<Cliente> clientes) {

        setTitle("Clientes registrados");
        setBounds(250, 150, 850, 400);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modelo = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Apellido", "DNI", "Email", "Teléfono"},
                0
        );

        tablaClientes = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tablaClientes);
        scroll.setBounds(20, 20, 790, 300);
        add(scroll);

        cargarClientes(clientes);
    }

    private void cargarClientes(ArrayList<Cliente> clientes) {

        modelo.setRowCount(0);

        for (Cliente c : clientes) {
            modelo.addRow(new Object[]{
                    c.getIdUsuario(),
                    c.getNombre(),
                    c.getApellido(),
                    c.getDni(),
                    c.getEmail(),
                    c.getTelefono()
            });
        }
    }
}