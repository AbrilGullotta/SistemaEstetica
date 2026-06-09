package interfaz;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import modelo.Usuario;
import servicio.UsuarioService;
import util.Validador;

public class MenuPrincipal extends JFrame {

    private UsuarioService usuarioService = new UsuarioService();

    private JLabel lblTitulo;
    private JLabel lblEmail;
    private JLabel lblContrasenia;

    private JTextField txtEmail;
    private JPasswordField txtContrasenia;

    private JButton btnLogin;
    private JButton btnRegistro;
    private JButton btnSalir;

    public MenuPrincipal() {
        setTitle("Sistema Estética");
        setLayout(null);
        setBounds(400, 150, 400, 330);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblTitulo = new JLabel("SISTEMA ESTÉTICA");
        lblTitulo.setBounds(130, 20, 200, 30);
        add(lblTitulo);

        lblEmail = new JLabel("Email:");
        lblEmail.setBounds(50, 80, 100, 25);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(150, 80, 180, 25);
        add(txtEmail);

        lblContrasenia = new JLabel("Contraseña:");
        lblContrasenia.setBounds(50, 120, 100, 25);
        add(lblContrasenia);

        txtContrasenia = new JPasswordField();
        txtContrasenia.setBounds(150, 120, 180, 25);
        add(txtContrasenia);

        btnLogin = new JButton("Iniciar sesión");
        btnLogin.setBounds(120, 165, 150, 30);
        add(btnLogin);

        btnRegistro = new JButton("Registrarse");
        btnRegistro.setBounds(120, 205, 150, 30);
        add(btnRegistro);

        btnSalir = new JButton("Salir");
        btnSalir.setBounds(120, 245, 150, 30);
        add(btnSalir);

        btnLogin.addActionListener(e -> login());
        btnRegistro.addActionListener(e -> registrarCliente());
        btnSalir.addActionListener(e -> System.exit(0));
    }

    private void login() {
        String email = txtEmail.getText();
        String contrasenia = new String(txtContrasenia.getPassword());

        if (email.trim().isEmpty() || contrasenia.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Completá email y contraseña.");
            return;
        }

        Usuario usuario = usuarioService.iniciarSesion(email, contrasenia);

        if (usuario == null) {
            JOptionPane.showMessageDialog(null, "Email o contraseña incorrectos.");
            return;
        }

        JOptionPane.showMessageDialog(null, "Bienvenido/a, " + usuario.getNombre() + "!");

        this.dispose();

        switch (usuario.getRol()) {
            case "ADMIN":
                new MenuAdministrador().mostrarMenuAdministrador();
                break;
            case "CLIENTE":
                new MenuCliente(usuario).mostrarMenuCliente();
                break;
            case "PROFESIONAL":
                new MenuProfesional(usuario).mostrarMenuProfesional();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Rol no reconocido: " + usuario.getRol());
        }
    }

    private void registrarCliente() {

        String nombre = Validador.pedirCampoObligatorio("Nombre");
        if (nombre == null) return;

        String apellido = Validador.pedirCampoObligatorio("Apellido");
        if (apellido == null) return;

        String dni = JOptionPane.showInputDialog("DNI:");
        if (!Validador.esDniValido(dni)) {
            JOptionPane.showMessageDialog(null, "DNI inválido. Debe tener 7 u 8 dígitos.");
            return;
        }

        String email = JOptionPane.showInputDialog("Email:");
        if (!Validador.esEmailValido(email)) {
            JOptionPane.showMessageDialog(null, "Email inválido.");
            return;
        }

        String telefono = JOptionPane.showInputDialog("Teléfono:");
        if (!Validador.esTelefonoValido(telefono)) {
            JOptionPane.showMessageDialog(null, "Teléfono inválido. Solo números, entre 8 y 15 dígitos.");
            return;
        }

        String pass = JOptionPane.showInputDialog("Contraseña:");
        if (!Validador.esContraseniaValida(pass)) {
            JOptionPane.showMessageDialog(null, "La contraseña debe tener al menos 8 caracteres, una letra y un número.");
            return;
        }

        String fnac = JOptionPane.showInputDialog("Fecha de nacimiento (AAAA-MM-DD):");
        if (!Validador.esFechaValida(fnac)) {
            JOptionPane.showMessageDialog(null, "Fecha inválida. Usá el formato AAAA-MM-DD.");
            return;
        }

        String resultado = usuarioService.registrarCliente(nombre, apellido, dni, email, telefono, pass, fnac);

        if ("OK".equals(resultado)) {
            JOptionPane.showMessageDialog(null, "¡Registro exitoso! Ya podés iniciar sesión.");
        } else {
            JOptionPane.showMessageDialog(null, resultado);
        }
    }
}