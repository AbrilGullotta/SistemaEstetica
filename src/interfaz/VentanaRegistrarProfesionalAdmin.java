package interfaz;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import servicio.UsuarioService;
import util.Validador;

public class VentanaRegistrarProfesionalAdmin extends JFrame {

    private JTextField txtNombre, txtApellido, txtDni, txtEmail, txtTelefono, txtContrasenia, txtEspecialidad;
    private JButton btnGuardar, btnCerrar;

    private UsuarioService usuarioService = new UsuarioService();

    public VentanaRegistrarProfesionalAdmin() {

        setTitle("Registrar profesional");
        setBounds(400, 100, 450, 440);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblTitulo = new JLabel("REGISTRAR PROFESIONAL");
        lblTitulo.setBounds(135, 20, 220, 30);
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

        JLabel lblEspecialidad = new JLabel("Especialidad:");
        lblEspecialidad.setBounds(50, 310, 130, 25);
        add(lblEspecialidad);

        txtEspecialidad = new JTextField();
        txtEspecialidad.setBounds(190, 310, 200, 25);
        add(txtEspecialidad);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(90, 355, 120, 30);
        add(btnGuardar);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(230, 355, 120, 30);
        add(btnCerrar);

        btnGuardar.addActionListener(e -> guardarProfesional());
        btnCerrar.addActionListener(e -> dispose());
    }

    private void guardarProfesional() {

        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String dni = txtDni.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String contrasenia = txtContrasenia.getText().trim();
        String especialidad = txtEspecialidad.getText().trim();

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

        if (!Validador.esValido(especialidad)) {
            JOptionPane.showMessageDialog(null, "La especialidad es obligatoria.");
            return;
        }

        String resultado = usuarioService.registrarProfesional(
                nombre, apellido, dni, email, telefono, contrasenia, especialidad
        );

        if ("OK".equals(resultado)) {
            JOptionPane.showMessageDialog(null, "Profesional registrado correctamente.");
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
        txtEspecialidad.setText("");
    }
}