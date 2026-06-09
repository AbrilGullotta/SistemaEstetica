package interfaz;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import servicio.UsuarioService;
import util.Validador;

public class VentanaRegistrarClienteAdmin extends JFrame {

    private JTextField txtNombre, txtApellido, txtDni, txtEmail, txtTelefono, txtContrasenia, txtFechaNacimiento;
    private JButton btnGuardar, btnCerrar;

    private UsuarioService usuarioService = new UsuarioService();

    public VentanaRegistrarClienteAdmin() {

        setTitle("Registrar cliente");
        setBounds(400, 100, 450, 460);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblTitulo = new JLabel("REGISTRAR CLIENTE");
        lblTitulo.setBounds(150, 20, 200, 30);
        add(lblTitulo);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(50, 70, 130, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(190, 70, 200, 25);
        add(txtNombre);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setBounds(50, 110, 130, 25);
        add(lblApellido);

        txtApellido = new JTextField();
        txtApellido.setBounds(190, 110, 200, 25);
        add(txtApellido);

        JLabel lblDni = new JLabel("DNI:");
        lblDni.setBounds(50, 150, 130, 25);
        add(lblDni);

        txtDni = new JTextField();
        txtDni.setBounds(190, 150, 200, 25);
        add(txtDni);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(50, 190, 130, 25);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(190, 190, 200, 25);
        add(txtEmail);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(50, 230, 130, 25);
        add(lblTelefono);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(190, 230, 200, 25);
        add(txtTelefono);

        JLabel lblContrasenia = new JLabel("Contraseña:");
        lblContrasenia.setBounds(50, 270, 130, 25);
        add(lblContrasenia);

        txtContrasenia = new JTextField();
        txtContrasenia.setBounds(190, 270, 200, 25);
        add(txtContrasenia);

        JLabel lblFecha = new JLabel("Fecha nac. AAAA-MM-DD:");
        lblFecha.setBounds(50, 310, 160, 25);
        add(lblFecha);

        txtFechaNacimiento = new JTextField();
        txtFechaNacimiento.setBounds(210, 310, 180, 25);
        add(txtFechaNacimiento);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(90, 365, 120, 30);
        add(btnGuardar);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(230, 365, 120, 30);
        add(btnCerrar);

        btnGuardar.addActionListener(e -> guardarCliente());
        btnCerrar.addActionListener(e -> dispose());
    }

    private void guardarCliente() {

        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String dni = txtDni.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String contrasenia = txtContrasenia.getText().trim();
        String fechaNacimiento = txtFechaNacimiento.getText().trim();

        if (!Validador.esValido(nombre) || !Validador.esValido(apellido)) {
            JOptionPane.showMessageDialog(null, "Nombre y apellido son obligatorios.");
            return;
        }

        if (!Validador.esDniValido(dni)) {
            JOptionPane.showMessageDialog(null, "DNI inválido. Debe tener 7 u 8 dígitos.");
            return;
        }

        if (!Validador.esEmailValido(email)) {
            JOptionPane.showMessageDialog(null, "Email inválido.");
            return;
        }

        if (!Validador.esTelefonoValido(telefono)) {
            JOptionPane.showMessageDialog(null, "Teléfono inválido. Solo números, entre 8 y 15 dígitos.");
            return;
        }

        if (!Validador.esContraseniaValida(contrasenia)) {
            JOptionPane.showMessageDialog(null, "La contraseña debe tener al menos 8 caracteres, una letra y un número.");
            return;
        }

        if (!Validador.esFechaValida(fechaNacimiento)) {
            JOptionPane.showMessageDialog(null, "Fecha inválida. Usá formato AAAA-MM-DD.");
            return;
        }

        String resultado = usuarioService.registrarCliente(
                nombre, apellido, dni, email, telefono, contrasenia, fechaNacimiento
        );

        if ("OK".equals(resultado)) {
            JOptionPane.showMessageDialog(null, "Cliente registrado correctamente.");
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(null, resultado);
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtDni.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        txtContrasenia.setText("");
        txtFechaNacimiento.setText("");
    }
}