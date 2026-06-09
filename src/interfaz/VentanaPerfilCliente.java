package interfaz;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import modelo.Usuario;
import servicio.UsuarioService;
import util.Validador;

public class VentanaPerfilCliente extends JFrame {

    private Usuario usuario;
    private boolean editable;

    private JLabel lblTitulo;
    private JLabel lblNombre;
    private JLabel lblApellido;
    private JLabel lblDni;
    private JLabel lblEmail;
    private JLabel lblTelefono;
    private JLabel lblFechaNacimiento;

    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtDni;
    private JTextField txtEmail;
    private JTextField txtTelefono;
    private JTextField txtFechaNacimiento;

    private JButton btnGuardar;
    private JButton btnCerrar;

    private UsuarioService usuarioService = new UsuarioService();

    public VentanaPerfilCliente(Usuario usuario, boolean editable) {
        this.usuario = usuario;
        this.editable = editable;

        setTitle(editable ? "Modificar mis datos" : "Mi perfil");
        setBounds(400, 120, 450, 430);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializarComponentes();
        cargarDatos();
    }

    private void inicializarComponentes() {

        lblTitulo = new JLabel(editable ? "MODIFICAR MIS DATOS" : "MI PERFIL");
        lblTitulo.setBounds(150, 20, 200, 30);
        add(lblTitulo);

        lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(40, 70, 120, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(180, 70, 200, 25);
        add(txtNombre);

        lblApellido = new JLabel("Apellido:");
        lblApellido.setBounds(40, 110, 120, 25);
        add(lblApellido);

        txtApellido = new JTextField();
        txtApellido.setBounds(180, 110, 200, 25);
        add(txtApellido);

        lblDni = new JLabel("DNI:");
        lblDni.setBounds(40, 150, 120, 25);
        add(lblDni);

        txtDni = new JTextField();
        txtDni.setBounds(180, 150, 200, 25);
        add(txtDni);

        lblEmail = new JLabel("Email:");
        lblEmail.setBounds(40, 190, 120, 25);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(180, 190, 200, 25);
        add(txtEmail);

        lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(40, 230, 120, 25);
        add(lblTelefono);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(180, 230, 200, 25);
        add(txtTelefono);

        lblFechaNacimiento = new JLabel("Fecha nac. (AAAA-MM-DD):");
        lblFechaNacimiento.setBounds(40, 270, 150, 25);
        add(lblFechaNacimiento);

        txtFechaNacimiento = new JTextField();
        txtFechaNacimiento.setBounds(180, 270, 200, 25);
        add(txtFechaNacimiento);

        btnGuardar = new JButton("Guardar cambios");
        btnGuardar.setBounds(70, 330, 150, 30);
        add(btnGuardar);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(240, 330, 120, 30);
        add(btnCerrar);

        btnGuardar.addActionListener(e -> guardarCambios());
        btnCerrar.addActionListener(e -> dispose());

        txtEmail.setEditable(false);

        if (!editable) {
            txtNombre.setEditable(false);
            txtApellido.setEditable(false);
            txtDni.setEditable(false);
            txtTelefono.setEditable(false);
            txtFechaNacimiento.setEditable(false);
            btnGuardar.setVisible(false);
        }
    }

    private void cargarDatos() {

        txtNombre.setText(usuario.getNombre());
        txtApellido.setText(usuario.getApellido());
        txtDni.setText(usuario.getDni() != null ? usuario.getDni() : "");
        txtEmail.setText(usuario.getEmail());
        txtTelefono.setText(usuario.getTelefono() != null ? usuario.getTelefono() : "");
        txtFechaNacimiento.setText(usuario.getFechaNacimiento() != null ? usuario.getFechaNacimiento() : "");
    }

    private void guardarCambios() {

        String nuevoNombre = txtNombre.getText().trim();
        String nuevoApellido = txtApellido.getText().trim();
        String nuevoDni = txtDni.getText().trim();
        String nuevoTelefono = txtTelefono.getText().trim();
        String nuevaFecha = txtFechaNacimiento.getText().trim();

        if (nuevoNombre.isEmpty() || nuevoApellido.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nombre y apellido son obligatorios.");
            return;
        }

        if (!Validador.esDniValido(nuevoDni)) {
            JOptionPane.showMessageDialog(null, "DNI inválido. Debe tener 7 u 8 dígitos.");
            return;
        }

        if (!Validador.esTelefonoValido(nuevoTelefono)) {
            JOptionPane.showMessageDialog(null, "Teléfono inválido. Solo números, entre 8 y 15 dígitos.");
            return;
        }

        if (!Validador.esFechaValida(nuevaFecha)) {
            JOptionPane.showMessageDialog(null, "Fecha inválida. Formato: AAAA-MM-DD.");
            return;
        }

        usuario.setNombre(nuevoNombre);
        usuario.setApellido(nuevoApellido);
        usuario.setDni(nuevoDni);
        usuario.setTelefono(nuevoTelefono);
        usuario.setFechaNacimiento(nuevaFecha);

        String resultado = usuarioService.modificarDatos(usuario);

        if ("OK".equals(resultado)) {
            JOptionPane.showMessageDialog(null, "Datos actualizados correctamente.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, resultado);
        }
    }
}