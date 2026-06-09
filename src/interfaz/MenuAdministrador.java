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

    StringBuilder sbCli = new StringBuilder("CLIENTES REGISTRADOS\n\n");
    for (modelo.Cliente c : clientes) {
        sbCli.append("• " + c.getNombre() + " " + c.getApellido() + "\n");
        sbCli.append("  DNI: " + c.getDni() + "\n");
        sbCli.append("  Email: " + c.getEmail() + "\n");
        sbCli.append("  Tel: " + c.getTelefono() + "\n");
        sbCli.append("-----------------------------\n");
    }

    JOptionPane.showMessageDialog(null, sbCli.toString());
}
private void verProfesionales() {

    UsuarioService usProf = new UsuarioService();
    ArrayList<modelo.Profesional> profs = usProf.listarProfesionales();

    if (profs.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No hay profesionales registrados.");
        return;
    }

    StringBuilder sbProfs = new StringBuilder("PROFESIONALES REGISTRADOS\n\n");
    for (modelo.Profesional p : profs) {
        sbProfs.append("• " + p.getNombre() + " " + p.getApellido() + "\n");
        sbProfs.append("  Especialidad: " + p.getEspecialidad() + "\n");
        sbProfs.append("  Email: " + p.getEmail() + "\n");
        sbProfs.append("  Tel: " + p.getTelefono() + "\n");
        sbProfs.append("-----------------------------\n");
    }

    JOptionPane.showMessageDialog(null, sbProfs.toString());
}
private void verServicios() {

    ServicioService ssVer = new ServicioService();
    ArrayList<modelo.Servicio> servs = ssVer.listarServicios();

    if (servs.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No hay servicios registrados.");
        return;
    }

    StringBuilder sbServs = new StringBuilder("SERVICIOS REGISTRADOS\n\n");
    for (modelo.Servicio s : servs) {
        sbServs.append("• " + s.getNombre() + "\n");
        sbServs.append("  Precio: $" + s.getPrecio() + "\n");
        sbServs.append("  Duración: " + s.getDuracion() + "\n");
        sbServs.append("-----------------------------\n");
    }

    JOptionPane.showMessageDialog(null, sbServs.toString());
}
private void verTurnos() {

    ArrayList<Turno> turnos = turnoService.listarTurnos();

    if (turnos.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No hay turnos registrados.");
        return;
    }

    String mensaje = "TURNOS REGISTRADOS\n\n";

    for (Turno turno : turnos) {
        mensaje += "ID: " + turno.getIdTurno() + "\n";
        mensaje += "Cliente: " + turno.getCliente().getNombre() + " " + turno.getCliente().getApellido() + "\n";
        mensaje += "Profesional: " + turno.getProfesional().getNombre() + " " + turno.getProfesional().getApellido() + "\n";
        mensaje += "Servicio: " + turno.getServicio().getNombre() + "\n";
        mensaje += "Fecha: " + turno.getFecha() + "\n";
        mensaje += "Hora: " + turno.getHora() + "\n";
        mensaje += "Estado: " + turno.getEstado() + "\n";
        mensaje += "-------------------------\n";
    }

    JOptionPane.showMessageDialog(null, mensaje);
}
private void buscarCliente() {

    String criterio = JOptionPane.showInputDialog(
            "Buscar cliente\nIngresá nombre, apellido o teléfono:");

    if (criterio == null || criterio.trim().isEmpty()) {
        return;
    }

    UsuarioService usBuscar = new UsuarioService();
    ArrayList<modelo.Cliente> clientesEncontrados = usBuscar.buscarClientes(criterio);

    if (clientesEncontrados.isEmpty()) {
        JOptionPane.showMessageDialog(null,
                "No se encontraron clientes con ese criterio.");
        return;
    }

    StringBuilder sbBusq = new StringBuilder(
            "Resultados para \"" + criterio + "\":\n\n");

    for (modelo.Cliente c : clientesEncontrados) {
        sbBusq.append("• " + c.getNombre() + " " + c.getApellido() + "\n");
        sbBusq.append("  DNI: " + c.getDni() + "\n");
        sbBusq.append("  Email: " + c.getEmail() + "\n");
        sbBusq.append("  Tel: " + c.getTelefono() + "\n");
        sbBusq.append("-----------------------------\n");
    }

    JOptionPane.showMessageDialog(null, sbBusq.toString());
}
private void gestionarTurnos() {

    ArrayList<Turno> todosLosTurnos = turnoService.listarTurnos();

    if (todosLosTurnos.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No hay turnos registrados.");
        return;
    }

    StringBuilder sbGestion = new StringBuilder("GESTIONAR TURNOS\n\n");

    for (int i = 0; i < todosLosTurnos.size(); i++) {
        Turno t = todosLosTurnos.get(i);
        sbGestion.append((i + 1) + ". " + t.getFecha() + " " + t.getHora()
                + " | " + t.getCliente().getNombre() + " " + t.getCliente().getApellido()
                + " | " + t.getServicio().getNombre()
                + " | Estado: " + t.getEstado() + "\n");
    }

    sbGestion.append("\nIngresá el número del turno a gestionar (0 para cancelar):");

    String inputGestion = JOptionPane.showInputDialog(sbGestion.toString());
    if (inputGestion == null) return;

    int numTurno = Integer.parseInt(inputGestion);
    if (numTurno == 0 || numTurno > todosLosTurnos.size()) return;

    Turno turnoElegido = todosLosTurnos.get(numTurno - 1);

    String accion = JOptionPane.showInputDialog(
            "Turno seleccionado:\n"
            + "Cliente: " + turnoElegido.getCliente().getNombre() + " " + turnoElegido.getCliente().getApellido() + "\n"
            + "Fecha: " + turnoElegido.getFecha() + " " + turnoElegido.getHora() + "\n"
            + "Estado actual: " + turnoElegido.getEstado() + "\n\n"
            + "Nuevo estado:\n"
            + "1. CONFIRMADO\n"
            + "2. CANCELADO\n"
            + "3. COMPLETADO\n"
            + "0. Volver"
    );

    if (accion == null) return;

    String nuevoEstado = null;

    switch (accion) {
        case "1":
            nuevoEstado = "CONFIRMADO";
            break;
        case "2":
            nuevoEstado = "CANCELADO";
            break;
        case "3":
            nuevoEstado = "COMPLETADO";
            break;
        default:
            return;
    }

    String resEstado = turnoService.cambiarEstado(turnoElegido.getIdTurno(), nuevoEstado);

    if ("OK".equals(resEstado)) {
        JOptionPane.showMessageDialog(null, "Estado actualizado a: " + nuevoEstado);
    } else {
        JOptionPane.showMessageDialog(null, resEstado);
    }
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
    ArrayList<modelo.Cliente> clientesModif = usModif.listarClientes();

    if (clientesModif.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No hay clientes registrados.");
        return;
    }

    StringBuilder sbModif = new StringBuilder("Seleccioná el cliente a modificar:\n\n");

    for (int i = 0; i < clientesModif.size(); i++) {
        modelo.Cliente c = clientesModif.get(i);
        sbModif.append((i + 1) + ". " + c.getNombre() + " " + c.getApellido()
                + " | " + c.getEmail() + "\n");
    }

    sbModif.append("\nIngresá el número (0 para cancelar):");

    String inputModif = JOptionPane.showInputDialog(sbModif.toString());
    if (inputModif == null) return;

    int numModif = Integer.parseInt(inputModif);
    if (numModif == 0 || numModif > clientesModif.size()) return;

    modelo.Cliente clienteAModif = clientesModif.get(numModif - 1);

    String nuevoNombre = JOptionPane.showInputDialog("Nombre:", clienteAModif.getNombre());
    String nuevoApellido = JOptionPane.showInputDialog("Apellido:", clienteAModif.getApellido());
    String nuevoDni = JOptionPane.showInputDialog("DNI:", clienteAModif.getDni());
    String nuevoTel = JOptionPane.showInputDialog("Teléfono:", clienteAModif.getTelefono());
    String nuevaFnac = JOptionPane.showInputDialog("Fecha de nacimiento (AAAA-MM-DD):", clienteAModif.getFechaNacimiento());

    if (nuevoNombre == null || nuevoApellido == null) return;

    if (!Validador.esDniValido(nuevoDni)) {
        JOptionPane.showMessageDialog(null, "DNI inválido. Debe tener 7 u 8 dígitos.");
        return;
    }

    if (!Validador.esTelefonoValido(nuevoTel)) {
        JOptionPane.showMessageDialog(null, "Teléfono inválido. Solo números, entre 8 y 15 dígitos.");
        return;
    }

    if (!Validador.esFechaValida(nuevaFnac)) {
        JOptionPane.showMessageDialog(null, "Fecha inválida. Formato: AAAA-MM-DD.");
        return;
    }

    clienteAModif.setNombre(nuevoNombre.trim());
    clienteAModif.setApellido(nuevoApellido.trim());
    clienteAModif.setDni(nuevoDni.trim());
    clienteAModif.setTelefono(nuevoTel.trim());
    clienteAModif.setFechaNacimiento(nuevaFnac.trim());

    String resModif = usModif.modificarDatos(clienteAModif);

    if ("OK".equals(resModif)) {
        JOptionPane.showMessageDialog(null, "Cliente modificado correctamente.");
    } else {
        JOptionPane.showMessageDialog(null, resModif);
    }
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

    StringBuilder sbTurnos = new StringBuilder("Seleccioná el turno a modificar:\n\n");

    for (int i = 0; i < modificables.size(); i++) {
        Turno t = modificables.get(i);
        sbTurnos.append((i + 1) + ". " + t.getFecha() + " " + t.getHora()
                + " | " + t.getCliente().getNombre() + " " + t.getCliente().getApellido()
                + " | " + t.getServicio().getNombre()
                + " | " + t.getProfesional().getNombre() + " " + t.getProfesional().getApellido()
                + " | " + t.getEstado() + "\n");
    }

    sbTurnos.append("\nIngresá el número (0 para cancelar):");

    String inputTModif = JOptionPane.showInputDialog(sbTurnos.toString());
    if (inputTModif == null) return;

    int numTModif = Integer.parseInt(inputTModif);
    if (numTModif == 0 || numTModif > modificables.size()) return;

    Turno turnoAModif = modificables.get(numTModif - 1);

    String nuevaFecha = JOptionPane.showInputDialog("Nueva fecha (AAAA-MM-DD):", turnoAModif.getFecha());
    if (nuevaFecha == null || nuevaFecha.trim().isEmpty()) return;

    if (!Validador.esFechaFutura(nuevaFecha.trim())) {
        JOptionPane.showMessageDialog(null, "La fecha debe ser hoy o en el futuro.");
        return;
    }

    ServicioService ssModif = new ServicioService();
    ArrayList<modelo.Servicio> serviciosModif = ssModif.listarServicios();

    StringBuilder sbServModif = new StringBuilder("Seleccioná el servicio:\n");
    for (int i = 0; i < serviciosModif.size(); i++) {
        modelo.Servicio s = serviciosModif.get(i);
        sbServModif.append((i + 1) + ". " + s.getNombre() + " - $" + s.getPrecio() + "\n");
    }

    String inputServModif = JOptionPane.showInputDialog(sbServModif.toString());
    if (inputServModif == null) return;

    int numServModif = Integer.parseInt(inputServModif);
    if (numServModif < 1 || numServModif > serviciosModif.size()) return;

    modelo.Servicio servicioModif = serviciosModif.get(numServModif - 1);

    UsuarioService usProfModif = new UsuarioService();
    ArrayList<modelo.Profesional> profsModif = usProfModif.listarProfesionales();

    StringBuilder sbProfModif = new StringBuilder("Seleccioná el profesional:\n");
    for (int i = 0; i < profsModif.size(); i++) {
        modelo.Profesional p = profsModif.get(i);
        sbProfModif.append((i + 1) + ". " + p.getNombre() + " " + p.getApellido()
                + " - " + p.getEspecialidad() + "\n");
    }

    String inputProfModif = JOptionPane.showInputDialog(sbProfModif.toString());
    if (inputProfModif == null) return;

    int numProfModif = Integer.parseInt(inputProfModif);
    if (numProfModif < 1 || numProfModif > profsModif.size()) return;

    modelo.Profesional profesionalModif = profsModif.get(numProfModif - 1);

    ArrayList<String> horariosModif = turnoService.obtenerHorariosDisponibles(
            profesionalModif.getIdUsuario(),
            nuevaFecha.trim(),
            servicioModif.getDuracion()
    );

    if (horariosModif.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No hay horarios disponibles para esa fecha y profesional.");
        return;
    }

    StringBuilder sbHorModif = new StringBuilder("Horarios disponibles:\n");
    for (int i = 0; i < horariosModif.size(); i++) {
        sbHorModif.append((i + 1) + ". " + horariosModif.get(i) + "\n");
    }

    String inputHorModif = JOptionPane.showInputDialog(sbHorModif.toString());
    if (inputHorModif == null) return;

    int numHorModif = Integer.parseInt(inputHorModif);
    if (numHorModif < 1 || numHorModif > horariosModif.size()) return;

    String horaModif = horariosModif.get(numHorModif - 1);

    int confirmaModif = JOptionPane.showConfirmDialog(null,
            "Confirmás la modificación?\n\n"
            + "Fecha: " + nuevaFecha.trim() + "\n"
            + "Hora: " + horaModif + "\n"
            + "Servicio: " + servicioModif.getNombre() + "\n"
            + "Profesional: " + profesionalModif.getNombre() + " " + profesionalModif.getApellido(),
            "Confirmar modificación",
            JOptionPane.YES_NO_OPTION
    );

    if (confirmaModif == JOptionPane.YES_OPTION) {
        String resModifT = turnoService.modificarTurno(
                turnoAModif.getIdTurno(),
                nuevaFecha.trim(),
                horaModif,
                profesionalModif.getIdUsuario(),
                servicioModif.getIdServicio()
        );

        if ("OK".equals(resModifT)) {
            JOptionPane.showMessageDialog(null, "Turno modificado correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, resModifT);
        }
    }
}
private void modificarServicio() {

    ServicioService ssModifServ = new ServicioService();
    ArrayList<modelo.Servicio> serviciosEdit = ssModifServ.listarServicios();

    if (serviciosEdit.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No hay servicios registrados.");
        return;
    }

    StringBuilder sbServEdit = new StringBuilder("Seleccioná el servicio a modificar:\n\n");

    for (int i = 0; i < serviciosEdit.size(); i++) {
        modelo.Servicio s = serviciosEdit.get(i);
        sbServEdit.append((i + 1) + ". " + s.getNombre()
                + " | $" + s.getPrecio()
                + " | " + s.getDuracion() + "\n");
    }

    sbServEdit.append("\nIngresá el número (0 para cancelar):");

    String inputServEdit = JOptionPane.showInputDialog(sbServEdit.toString());
    if (inputServEdit == null) return;

    int numServEdit = Integer.parseInt(inputServEdit);
    if (numServEdit == 0 || numServEdit > serviciosEdit.size()) return;

    modelo.Servicio servicioAEditar = serviciosEdit.get(numServEdit - 1);

    String nuevoNombreServ = JOptionPane.showInputDialog("Nombre:", servicioAEditar.getNombre());
    if (nuevoNombreServ == null) return;

    String nuevoPrecioStr = JOptionPane.showInputDialog("Precio:", servicioAEditar.getPrecio());
    if (nuevoPrecioStr == null) return;

    String nuevaDuracion = JOptionPane.showInputDialog("Duración (HH:MM:SS):", servicioAEditar.getDuracion());
    if (nuevaDuracion == null) return;

    double nuevoPrecio;

    try {
        nuevoPrecio = Double.parseDouble(nuevoPrecioStr.replace(",", "."));
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "ERROR: El precio debe ser un número válido.");
        return;
    }

    if (!Validador.esValido(nuevoNombreServ)) {
        JOptionPane.showMessageDialog(null, "El nombre del servicio es obligatorio.");
        return;
    }

    servicioAEditar.setNombre(nuevoNombreServ.trim());
    servicioAEditar.setPrecio(nuevoPrecio);
    servicioAEditar.setDuracion(nuevaDuracion.trim());

    String resServEdit = ssModifServ.modificarServicio(servicioAEditar);

    if ("OK".equals(resServEdit)) {
        JOptionPane.showMessageDialog(null, "Servicio modificado correctamente.");
    } else {
        JOptionPane.showMessageDialog(null, resServEdit);
    }
}
}