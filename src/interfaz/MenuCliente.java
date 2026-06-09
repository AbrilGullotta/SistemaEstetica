package interfaz;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Cliente;
import modelo.Profesional;
import modelo.Servicio;
import modelo.Usuario;
import servicio.ServicioService;
import servicio.TurnoService;
import servicio.UsuarioService;
import servicio.SeniaService;
import util.Validador;
import java.text.SimpleDateFormat;
import javax.swing.JPanel;
import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import modelo.Turno;

public class MenuCliente extends JFrame{

    private Cliente clienteLogueado;
    private TurnoService turnoService = new TurnoService();
    private ServicioService servicioService = new ServicioService();
    private UsuarioService usuarioService = new UsuarioService();
    
    private JLabel lblTitulo;
    private JButton btnReservarTurno;
    private JButton btnConsultarTurnos;
    private JButton btnCancelarTurno;
    private JButton btnVerServicios;
    private JButton btnVerProfesionales;
    private JButton btnPagarSenia;
    private JButton btnVerPerfil;
    private JButton btnModificarPerfil;
    private JButton btnCerrar;

    public MenuCliente(Usuario usuario) {
        this.clienteLogueado = new Cliente();
        this.clienteLogueado.setIdUsuario(usuario.getIdUsuario());
        this.clienteLogueado.setNombre(usuario.getNombre());
        this.clienteLogueado.setApellido(usuario.getApellido());
        this.clienteLogueado.setEmail(usuario.getEmail());
    inicializarVentana();
    }
    
    private void inicializarVentana() {
        setTitle("Menú Cliente");
        setLayout(null);
        setBounds(400, 100, 430, 520);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        lblTitulo = new JLabel("MENÚ CLIENTE - " + clienteLogueado.getNombre());
        lblTitulo.setBounds(120, 20, 250, 30);
        add(lblTitulo);

        btnReservarTurno = new JButton("Reservar turno");
        btnReservarTurno.setBounds(110, 70, 200, 30);
        add(btnReservarTurno);

        btnConsultarTurnos = new JButton("Consultar mis turnos");
        btnConsultarTurnos.setBounds(110, 110, 200, 30);
        add(btnConsultarTurnos);

        btnCancelarTurno = new JButton("Cancelar turno");
        btnCancelarTurno.setBounds(110, 150, 200, 30);
        add(btnCancelarTurno);

        btnVerServicios = new JButton("Ver servicios disponibles");
        btnVerServicios.setBounds(110, 190, 200, 30);
        add(btnVerServicios);

        btnVerProfesionales = new JButton("Ver profesionales");
        btnVerProfesionales.setBounds(110, 230, 200, 30);
        add(btnVerProfesionales);

        btnPagarSenia = new JButton("Pagar seña");
        btnPagarSenia.setBounds(110, 270, 200, 30);
        add(btnPagarSenia);

        btnVerPerfil = new JButton("Mi perfil");
        btnVerPerfil.setBounds(110, 310, 200, 30);
        add(btnVerPerfil);

        btnModificarPerfil = new JButton("Modificar mis datos");
        btnModificarPerfil.setBounds(110, 350, 200, 30);
        add(btnModificarPerfil);

        btnCerrar = new JButton("Cerrar sesión");
        btnCerrar.setBounds(110, 400, 200, 30);
        add(btnCerrar);

        btnReservarTurno.addActionListener(e -> reservarTurno());
        btnConsultarTurnos.addActionListener(e -> consultarTurnos());
        btnCancelarTurno.addActionListener(e -> cancelarTurno());
        btnVerServicios.addActionListener(e -> verServicios());
        btnVerProfesionales.addActionListener(e -> verProfesionales());
        btnPagarSenia.addActionListener(e -> pagarSenia());
        btnVerPerfil.addActionListener(e -> verPerfil());
        btnModificarPerfil.addActionListener(e -> modificarPerfil());
        btnCerrar.addActionListener(e -> {
            dispose();
            MenuPrincipal menu = new MenuPrincipal();
            menu.setVisible(true);
        });
    }
    
    public void mostrarMenuCliente() {
        setVisible(true);
    }

    private void reservarTurno() {

        VentanaReservarTurnoCliente ventana =
                new VentanaReservarTurnoCliente(clienteLogueado);

        ventana.setVisible(true);
    }
    private void consultarTurnos() {

        ArrayList<Turno> turnos = turnoService.listarTurnosPorCliente(
                clienteLogueado.getIdUsuario()
        );

        if (turnos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tenés turnos registrados.");
            return;
        }

        VentanaMisTurnosCliente ventana =
                new VentanaMisTurnosCliente(turnos);

        ventana.setVisible(true);
    }

    private void verServicios() {

        ArrayList<Servicio> servicios = servicioService.listarServicios();

        if (servicios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay servicios disponibles.");
            return;
        }

        VentanaServiciosCliente ventana = new VentanaServiciosCliente(servicios);
        ventana.setVisible(true);
    }

    private void verProfesionales() {

        ArrayList<Profesional> profesionales = usuarioService.listarProfesionales();

        if (profesionales.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay profesionales disponibles.");
            return;
        }

        VentanaProfesionalesCliente ventana =
                new VentanaProfesionalesCliente(profesionales);

        ventana.setVisible(true);
    }
    private void cancelarTurno() {

        ArrayList<Turno> turnos = turnoService.listarTurnosPorCliente(
                clienteLogueado.getIdUsuario()
        );

        ArrayList<Turno> cancelables = new ArrayList<>();

        for (Turno t : turnos) {
            if (t.getEstado().equals("RESERVADO") || t.getEstado().equals("CONFIRMADO")) {
                cancelables.add(t);
            }
        }

        if (cancelables.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tenés turnos activos para cancelar.");
            return;
        }

        VentanaMisTurnosCliente ventana = new VentanaMisTurnosCliente(cancelables);
        ventana.setVisible(true);
    }
    private void pagarSenia() {

        ArrayList<modelo.Turno> turnos = turnoService.listarTurnosPorCliente(
                clienteLogueado.getIdUsuario()
        );

        ArrayList<modelo.Turno> reservados = new ArrayList<>();
        for (modelo.Turno t : turnos) {
            if (t.getEstado().equals("RESERVADO")) {
                reservados.add(t);
            }
        }

        if (reservados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tenés turnos RESERVADOS para señar.");
            return;
        }

        StringBuilder sb = new StringBuilder("¿A qué turno querés pagar la seña?\n\n");
        for (int i = 0; i < reservados.size(); i++) {
            modelo.Turno t = reservados.get(i);
            sb.append((i + 1) + ". " + t.getFecha() + " " + t.getHora()
                    + " | " + t.getServicio().getNombre()
                    + " | $" + t.getServicio().getPrecio() + "\n");
        }
        sb.append("\nIngresá el número (0 para volver):");

        String inputNum = JOptionPane.showInputDialog(sb.toString());
        if (inputNum == null) return;
        int num = Integer.parseInt(inputNum);
        if (num == 0 || num > reservados.size()) return;

        modelo.Turno turnoElegido = reservados.get(num - 1);
        SeniaService seniaService = new SeniaService();
        double montoSenia = seniaService.calcularMonto(turnoElegido.getServicio().getPrecio());

        int confirmar = JOptionPane.showConfirmDialog(null,
                "Seña del 30% para el turno:\n"
                + turnoElegido.getFecha() + " " + turnoElegido.getHora()
                + " - " + turnoElegido.getServicio().getNombre() + "\n\n"
                + "Monto a pagar: $" + String.format("%.2f", montoSenia) + "\n\n"
                + "¿Confirmás el pago?",
                "Pagar seña",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmar == JOptionPane.YES_OPTION) {
            String resultado = seniaService.registrarSenia(turnoElegido);

            if (resultado.startsWith("OK")) {
                double monto = Double.parseDouble(resultado.split(":")[1]);
                JOptionPane.showMessageDialog(null,
                        "¡Seña registrada!\n"
                        + "Monto: $" + String.format("%.2f", monto) + "\n"
                        + "El turno fue confirmado.");
            } else {
                JOptionPane.showMessageDialog(null, resultado);
            }
        }
    }

    private void verPerfil() {

        UsuarioService us = new UsuarioService();
        Usuario u = us.buscarPorId(clienteLogueado.getIdUsuario());

        if (u == null) {
            JOptionPane.showMessageDialog(null, "No se pudo obtener la información del perfil.");
            return;
        }

        VentanaPerfilCliente ventana = new VentanaPerfilCliente(u, false);
        ventana.setVisible(true);
    }

    private void modificarPerfil() {

        UsuarioService us = new UsuarioService();
        Usuario u = us.buscarPorId(clienteLogueado.getIdUsuario());

        if (u == null) {
            JOptionPane.showMessageDialog(null, "No se pudo obtener la información del perfil.");
            return;
        }

        VentanaPerfilCliente ventana = new VentanaPerfilCliente(u, true);
        ventana.setVisible(true);
    }
}