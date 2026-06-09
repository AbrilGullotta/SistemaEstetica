package interfaz;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import modelo.Turno;
import servicio.TurnoService;
import servicio.UsuarioService;
import servicio.ServicioService;
import util.Validador;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MenuAdministrador extends JFrame{
	
	TurnoService turnoService = new TurnoService();
	private JLabel lblTitulo;

	private JButton btnRegistrarCliente;
	private JButton btnRegistrarProfesional;
	private JButton btnRegistrarServicio;
	private JButton btnGestionarTurnos;
	private JButton btnRegistrarSenia;
	private JButton btnVerClientes;
	private JButton btnVerProfesionales;
	private JButton btnVerServicios;
	private JButton btnVerTurnos;
	private JButton btnBuscarCliente;
	private JButton btnModificarCliente;
	private JButton btnModificarTurno;
	private JButton btnModificarServicio;
	private JButton btnCerrar;

	public MenuAdministrador() {
	    inicializarVentana();
	}
	
	private void inicializarVentana() {

	    setTitle("Menú Administrador");
	    setLayout(null);
	    setBounds(350, 80, 500, 650);
	    setResizable(false);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	    lblTitulo = new JLabel("MENÚ ADMINISTRADOR");
	    lblTitulo.setBounds(170, 20, 200, 30);
	    add(lblTitulo);

	    btnRegistrarCliente = new JButton("Registrar cliente");
	    btnRegistrarCliente.setBounds(60, 70, 180, 30);
	    add(btnRegistrarCliente);

	    btnRegistrarProfesional = new JButton("Registrar profesional");
	    btnRegistrarProfesional.setBounds(260, 70, 180, 30);
	    add(btnRegistrarProfesional);

	    btnRegistrarServicio = new JButton("Registrar servicio");
	    btnRegistrarServicio.setBounds(60, 120, 180, 30);
	    add(btnRegistrarServicio);

	    btnGestionarTurnos = new JButton("Gestionar turnos");
	    btnGestionarTurnos.setBounds(260, 120, 180, 30);
	    add(btnGestionarTurnos);

	    btnRegistrarSenia = new JButton("Registrar seña");
	    btnRegistrarSenia.setBounds(60, 170, 180, 30);
	    add(btnRegistrarSenia);

	    btnVerClientes = new JButton("Ver clientes");
	    btnVerClientes.setBounds(260, 170, 180, 30);
	    add(btnVerClientes);

	    btnVerProfesionales = new JButton("Ver profesionales");
	    btnVerProfesionales.setBounds(60, 220, 180, 30);
	    add(btnVerProfesionales);

	    btnVerServicios = new JButton("Ver servicios");
	    btnVerServicios.setBounds(260, 220, 180, 30);
	    add(btnVerServicios);

	    btnVerTurnos = new JButton("Ver turnos");
	    btnVerTurnos.setBounds(60, 270, 180, 30);
	    add(btnVerTurnos);

	    btnBuscarCliente = new JButton("Buscar cliente");
	    btnBuscarCliente.setBounds(260, 270, 180, 30);
	    add(btnBuscarCliente);

	    btnModificarCliente = new JButton("Modificar cliente");
	    btnModificarCliente.setBounds(60, 320, 180, 30);
	    add(btnModificarCliente);

	    btnModificarTurno = new JButton("Modificar turno");
	    btnModificarTurno.setBounds(260, 320, 180, 30);
	    add(btnModificarTurno);

	    btnModificarServicio = new JButton("Modificar servicio");
	    btnModificarServicio.setBounds(60, 370, 180, 30);
	    add(btnModificarServicio);

	    btnCerrar = new JButton("Cerrar sesión");
	    btnCerrar.setBounds(160, 460, 180, 30);
	    add(btnCerrar);

	    btnRegistrarCliente.addActionListener(e -> registrarCliente());
	    btnRegistrarProfesional.addActionListener(e -> registrarProfesional());
	    btnRegistrarServicio.addActionListener(e -> registrarServicio());
	    btnGestionarTurnos.addActionListener(e -> gestionarTurnos());
	    btnRegistrarSenia.addActionListener(e -> registrarSenia());
	    btnVerClientes.addActionListener(e -> verClientes());
	    btnVerProfesionales.addActionListener(e -> verProfesionales());
	    btnVerServicios.addActionListener(e -> verServicios());
	    btnVerTurnos.addActionListener(e -> verTurnos());
	    btnBuscarCliente.addActionListener(e -> buscarCliente());
	    btnModificarCliente.addActionListener(e -> modificarCliente());
	    btnModificarTurno.addActionListener(e -> modificarTurno());
	    btnModificarServicio.addActionListener(e -> modificarServicio());

	    btnCerrar.addActionListener(e -> {
	        dispose();
	        MenuPrincipal menu = new MenuPrincipal();
	        menu.setVisible(true);
	    });
	}
	
	public void mostrarMenuAdministrador() {
	    setVisible(true);
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

    UsuarioService usuarioService = new UsuarioService();
    String resultado = usuarioService.registrarCliente(
            nombre, apellido, dni, email, telefono, pass, fnac);

    if ("OK".equals(resultado)) {
        JOptionPane.showMessageDialog(null, "Cliente registrado correctamente.");
    } else {
        JOptionPane.showMessageDialog(null, resultado);
    }
}
private void registrarProfesional() {

    String nomProf = Validador.pedirCampoObligatorio("Nombre");
    if (nomProf == null) return;

    String apProf = Validador.pedirCampoObligatorio("Apellido");
    if (apProf == null) return;

    String dniProf = JOptionPane.showInputDialog("DNI:");
    if (!Validador.esDniValido(dniProf)) {
        JOptionPane.showMessageDialog(null, "DNI inválido. Debe tener 7 u 8 dígitos.");
        return;
    }

    String emailProf = JOptionPane.showInputDialog("Email:");
    if (!Validador.esEmailValido(emailProf)) {
        JOptionPane.showMessageDialog(null, "Email inválido.");
        return;
    }

    String telProf = JOptionPane.showInputDialog("Teléfono:");
    if (!Validador.esTelefonoValido(telProf)) {
        JOptionPane.showMessageDialog(null, "Teléfono inválido. Solo números, entre 8 y 15 dígitos.");
        return;
    }

    String passProf = JOptionPane.showInputDialog("Contraseña:");
    if (!Validador.esContraseniaValida(passProf)) {
        JOptionPane.showMessageDialog(null, "La contraseña debe tener al menos 8 caracteres, una letra y un número.");
        return;
    }

    String espec = JOptionPane.showInputDialog("Especialidad:");
    if (!Validador.esValido(espec)) {
        JOptionPane.showMessageDialog(null, "La especialidad es obligatoria.");
        return;
    }

    UsuarioService us = new UsuarioService();
    String resProf = us.registrarProfesional(
            nomProf, apProf, dniProf, emailProf, telProf, passProf, espec);

    if ("OK".equals(resProf)) {
        JOptionPane.showMessageDialog(null, "Profesional registrado correctamente.");
    } else {
        JOptionPane.showMessageDialog(null, resProf);
    }
}
private void registrarServicio() {

    String nomServ = JOptionPane.showInputDialog("Nombre del servicio:");
    String precio = JOptionPane.showInputDialog("Precio:");
    String duracion = JOptionPane.showInputDialog("Duración (HH:MM:SS):");

    ServicioService servicioService = new ServicioService();
    String resServ = servicioService.registrarServicio(nomServ, precio, duracion);

    if ("OK".equals(resServ)) {
        JOptionPane.showMessageDialog(null, "Servicio registrado correctamente.");
    } else {
        JOptionPane.showMessageDialog(null, resServ);
    }
}
private void verClientes() {

    UsuarioService usVer = new UsuarioService();
    ArrayList<modelo.Cliente> clientes = usVer.listarClientes();

    if (clientes.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No hay clientes registrados.");
        return;
    }

    VentanaClientesAdmin ventana = new VentanaClientesAdmin(clientes);
    ventana.setVisible(true);
}
private void verProfesionales() {

    UsuarioService usProf = new UsuarioService();
    ArrayList<modelo.Profesional> profs = usProf.listarProfesionales();

    if (profs.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No hay profesionales registrados.");
        return;
    }

    VentanaProfesionalesAdmin ventana = new VentanaProfesionalesAdmin(profs);
    ventana.setVisible(true);
}
private void verServicios() {

    ServicioService ssVer = new ServicioService();
    ArrayList<modelo.Servicio> servicios = ssVer.listarServicios();

    if (servicios.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No hay servicios registrados.");
        return;
    }

    VentanaServiciosAdmin ventana =
            new VentanaServiciosAdmin(servicios);

    ventana.setVisible(true);
}
private void verTurnos() {

    ArrayList<Turno> turnos = turnoService.listarTurnos();

    if (turnos.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No hay turnos registrados.");
        return;
    }

    VentanaTurnosAdmin ventana = new VentanaTurnosAdmin(turnos);
    ventana.setVisible(true);
}
private void buscarCliente() {

    String criterio = JOptionPane.showInputDialog(
            "Buscar cliente\nIngresá nombre, apellido o teléfono:");

    if (criterio == null || criterio.trim().isEmpty()) {
        return;
    }

    UsuarioService usBuscar = new UsuarioService();
    ArrayList<modelo.Cliente> clientesEncontrados =
            usBuscar.buscarClientes(criterio.trim());

    if (clientesEncontrados.isEmpty()) {
        JOptionPane.showMessageDialog(null,
                "No se encontraron clientes con ese criterio.");
        return;
    }

    VentanaClientesAdmin ventana =
            new VentanaClientesAdmin(clientesEncontrados);

    ventana.setVisible(true);
}
private void gestionarTurnos() {

    ArrayList<Turno> turnos = turnoService.listarTurnos();

    if (turnos.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No hay turnos registrados.");
        return;
    }

    VentanaTurnosAdmin ventana = new VentanaTurnosAdmin(turnos);
    ventana.setVisible(true);
}
private void registrarSenia() {

    ArrayList<Turno> turnosReservados = turnoService.listarTurnos();
    ArrayList<Turno> aptosSenia = new ArrayList<>();

    for (Turno t : turnosReservados) {
        if (t.getEstado().equals("RESERVADO")) {
            aptosSenia.add(t);
        }
    }

    if (aptosSenia.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No hay turnos RESERVADOS para registrar seña.");
        return;
    }

    StringBuilder sbSenia = new StringBuilder("Seleccioná el turno para registrar la seña:\n\n");

    for (int i = 0; i < aptosSenia.size(); i++) {
        Turno t = aptosSenia.get(i);
        sbSenia.append((i + 1) + ". " + t.getFecha() + " " + t.getHora()
                + " | " + t.getCliente().getNombre() + " " + t.getCliente().getApellido()
                + " | " + t.getServicio().getNombre()
                + " | $" + t.getServicio().getPrecio() + "\n");
    }

    sbSenia.append("\nIngresá el número (0 para cancelar):");

    String inputSenia = JOptionPane.showInputDialog(sbSenia.toString());
    if (inputSenia == null) return;

    int numSenia = Integer.parseInt(inputSenia);
    if (numSenia == 0 || numSenia > aptosSenia.size()) return;

    Turno turnoParaSenia = aptosSenia.get(numSenia - 1);

    servicio.SeniaService seniaServiceAdmin = new servicio.SeniaService();
    double montoAdmin = seniaServiceAdmin.calcularMonto(turnoParaSenia.getServicio().getPrecio());

    int confirmaSenia = JOptionPane.showConfirmDialog(null,
            "Registrar seña para:\n"
            + turnoParaSenia.getCliente().getNombre() + " " + turnoParaSenia.getCliente().getApellido() + "\n"
            + turnoParaSenia.getFecha() + " " + turnoParaSenia.getHora()
            + " - " + turnoParaSenia.getServicio().getNombre() + "\n\n"
            + "Monto (30%): $" + String.format("%.2f", montoAdmin) + "\n\n"
            + "¿Confirmás el registro?",
            "Registrar seña",
            JOptionPane.YES_NO_OPTION
    );

    if (confirmaSenia == JOptionPane.YES_OPTION) {
        String resSenia = seniaServiceAdmin.registrarSenia(turnoParaSenia);

        if (resSenia.startsWith("OK")) {
            double monto = Double.parseDouble(resSenia.split(":")[1]);
            JOptionPane.showMessageDialog(null,
                    "Seña registrada correctamente.\nMonto: $" + String.format("%.2f", monto));
        } else {
            JOptionPane.showMessageDialog(null, resSenia);
        }
    }
}
private void modificarCliente() {

    UsuarioService usModif = new UsuarioService();
    ArrayList<modelo.Cliente> clientes = usModif.listarClientes();

    if (clientes.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No hay clientes registrados.");
        return;
    }

    VentanaModificarClienteAdmin ventana =
            new VentanaModificarClienteAdmin(clientes);

    ventana.setVisible(true);
}
private void modificarTurno() {

    ArrayList<Turno> turnosModif = turnoService.listarTurnos();
    ArrayList<Turno> modificables = new ArrayList<>();

    for (Turno t : turnosModif) {
        if (t.getEstado().equals("RESERVADO") || t.getEstado().equals("CONFIRMADO")) {
            modificables.add(t);
        }
    }

    if (modificables.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No hay turnos activos para modificar.");
        return;
    }

    VentanaModificarTurnoAdmin ventana =
            new VentanaModificarTurnoAdmin(modificables);

    ventana.setVisible(true);
}
private void modificarServicio() {

    ServicioService ssModifServ = new ServicioService();
    ArrayList<modelo.Servicio> servicios = ssModifServ.listarServicios();

    if (servicios.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No hay servicios registrados.");
        return;
    }

    VentanaModificarServicioAdmin ventana =
            new VentanaModificarServicioAdmin(servicios);

    ventana.setVisible(true);
}
}