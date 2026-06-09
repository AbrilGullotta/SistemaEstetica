package interfaz;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import servicio.ServicioService;

public class VentanaRegistrarServicioAdmin extends JFrame {

    private JLabel lblTitulo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel lblDuracion;

    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtDuracion;

    private JButton btnGuardar;
    private JButton btnCerrar;

    private ServicioService servicioService = new ServicioService();

    public VentanaRegistrarServicioAdmin() {

        setTitle("Registrar servicio");
        setBounds(400, 150, 430, 330);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        lblTitulo = new JLabel("REGISTRAR SERVICIO");
        lblTitulo.setBounds(140, 20, 180, 30);
        add(lblTitulo);

        lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(50, 70, 100, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(160, 70, 200, 25);
        add(txtNombre);

        lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(50, 110, 100, 25);
        add(lblPrecio);

        txtPrecio = new JTextField();
        txtPrecio.setBounds(160, 110, 200, 25);
        add(txtPrecio);

        lblDuracion = new JLabel("Duración:");
        lblDuracion.setBounds(50, 150, 100, 25);
        add(lblDuracion);

        txtDuracion = new JTextField("00:45:00");
        txtDuracion.setBounds(160, 150, 200, 25);
        add(txtDuracion);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(90, 220, 110, 30);
        add(btnGuardar);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(220, 220, 110, 30);
        add(btnCerrar);

        btnGuardar.addActionListener(e -> guardarServicio());
        btnCerrar.addActionListener(e -> dispose());
    }

    private void guardarServicio() {

        String nombre = txtNombre.getText();
        String precio = txtPrecio.getText();
        String duracion = txtDuracion.getText();

        String resultado = servicioService.registrarServicio(nombre, precio, duracion);

        if ("OK".equals(resultado)) {
            JOptionPane.showMessageDialog(null, "Servicio registrado correctamente.");
            txtNombre.setText("");
            txtPrecio.setText("");
            txtDuracion.setText("00:45:00");
        } else {
            JOptionPane.showMessageDialog(null, resultado);
        }
    }
}