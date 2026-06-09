package interfaz;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelo.Cliente;
import modelo.Usuario;
import servicio.UsuarioService;

public class VentanaModificarClienteAdmin extends JFrame {

    private JTable tablaClientes;
    private DefaultTableModel modelo;
    private JButton btnModificar;

    private ArrayList<Cliente> clientes;
    private UsuarioService usuarioService = new UsuarioService();

    public VentanaModificarClienteAdmin(ArrayList<Cliente> clientes) {

        this.clientes = clientes;

        setTitle("Modificar cliente");
        setBounds(250, 150, 850, 420);
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

        btnModificar = new JButton("Modificar cliente seleccionado");
        btnModificar.setBounds(290, 335, 250, 30);
        add(btnModificar);

        btnModificar.addActionListener(e -> modificarSeleccionado());

        cargarClientes();
    }

    private void cargarClientes() {

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

    private void modificarSeleccionado() {

        int fila = tablaClientes.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccioná un cliente de la tabla.");
            return;
        }

        int idCliente = Integer.parseInt(modelo.getValueAt(fila, 0).toString());

        Usuario usuario = usuarioService.buscarPorId(idCliente);

        if (usuario == null) {
            JOptionPane.showMessageDialog(null, "No se pudo obtener la información del cliente.");
            return;
        }

        VentanaPerfilCliente ventana = new VentanaPerfilCliente(usuario, true);
        ventana.setVisible(true);
    }
}