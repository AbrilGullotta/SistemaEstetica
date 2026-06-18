package interfaz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.toedter.calendar.JDateChooser;

import modelo.Cliente;
import modelo.Profesional;
import modelo.Servicio;
import servicio.ServicioService;
import servicio.TurnoService;
import servicio.UsuarioService;
import util.Validador;

public class VentanaRegistrarTurnoAdmin extends JFrame {

    private JComboBox<Cliente> comboClientes;
    private JComboBox<Servicio> comboServicios;
    private JComboBox<Profesional> comboProfesionales;
    private JComboBox<String> comboHorarios;

    private JDateChooser dateChooser;

    private JButton btnBuscarHorarios;
    private JButton btnRegistrar;
    private JButton btnCerrar;

    private ServicioService servicioService = new ServicioService();
    private UsuarioService usuarioService = new UsuarioService();
    private TurnoService turnoService = new TurnoService();

    public VentanaRegistrarTurnoAdmin() {

        setTitle("Registrar turno");
        setBounds(400, 100, 520, 460);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializarComponentes();
        cargarClientes();
        cargarServicios();
        cargarProfesionales();
    }

    private void inicializarComponentes() {

        JLabel lblTitulo = new JLabel("REGISTRAR TURNO");
        lblTitulo.setBounds(200, 20, 180, 30);
        add(lblTitulo);

        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setBounds(50, 75, 120, 25);
        add(lblCliente);

        comboClientes = new JComboBox<>();
        comboClientes.setBounds(180, 75, 260, 25);
        add(comboClientes);

        JLabel lblServicio = new JLabel("Servicio:");
        lblServicio.setBounds(50, 115, 120, 25);
        add(lblServicio);

        comboServicios = new JComboBox<>();
        comboServicios.setBounds(180, 115, 260, 25);
        add(comboServicios);

        JLabel lblProfesional = new JLabel("Profesional:");
        lblProfesional.setBounds(50, 155, 120, 25);
        add(lblProfesional);

        comboProfesionales = new JComboBox<>();
        comboProfesionales.setBounds(180, 155, 260, 25);
        add(comboProfesionales);

        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(50, 195, 120, 25);
        add(lblFecha);

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setBounds(180, 195, 260, 25);
        add(dateChooser);

        btnBuscarHorarios = new JButton("Buscar horarios");
        btnBuscarHorarios.setBounds(180, 235, 180, 30);
        add(btnBuscarHorarios);

        JLabel lblHorario = new JLabel("Horario:");
        lblHorario.setBounds(50, 280, 120, 25);
        add(lblHorario);

        comboHorarios = new JComboBox<>();
        comboHorarios.setBounds(180, 280, 260, 25);
        add(comboHorarios);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(120, 350, 120, 30);
        add(btnRegistrar);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(270, 350, 120, 30);
        add(btnCerrar);

        btnBuscarHorarios.addActionListener(e -> buscarHorarios());
        btnRegistrar.addActionListener(e -> registrarTurno());
        btnCerrar.addActionListener(e -> dispose());
    }

    private void cargarClientes() {
        ArrayList<Cliente> clientes = usuarioService.listarClientes();
        for (Cliente c : clientes) {
            comboClientes.addItem(c);
        }
    }

    private void cargarServicios() {
        ArrayList<Servicio> servicios = servicioService.listarServicios();
        for (Servicio s : servicios) {
            comboServicios.addItem(s);
        }
    }

    private void cargarProfesionales() {
        ArrayList<Profesional> profesionales = usuarioService.listarProfesionales();
        for (Profesional p : profesionales) {
            comboProfesionales.addItem(p);
        }
    }

    private void buscarHorarios() {

        comboHorarios.removeAllItems();

        Cliente cliente = (Cliente) comboClientes.getSelectedItem();
        Servicio servicio = (Servicio) comboServicios.getSelectedItem();
        Profesional profesional = (Profesional) comboProfesionales.getSelectedItem();

        if (cliente == null || servicio == null || profesional == null || dateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Completá todos los campos antes de buscar horarios.");
            return;
        }

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = formato.format(dateChooser.getDate());

        if (!Validador.esFechaFutura(fecha)) {
            JOptionPane.showMessageDialog(this, "La fecha debe ser hoy o en el futuro.");
            return;
        }

        ArrayList<String> horarios = turnoService.obtenerHorariosDisponibles(
                profesional.getIdUsuario(),
                fecha,
                servicio.getDuracion()
        );

        if (horarios.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay horarios disponibles para esa fecha.");
            return;
        }

        for (String h : horarios) {
            comboHorarios.addItem(h);
        }
    }

    private void registrarTurno() {

        Cliente clienteElegido = (Cliente) comboClientes.getSelectedItem();
        Servicio servicio = (Servicio) comboServicios.getSelectedItem();
        Profesional profesional = (Profesional) comboProfesionales.getSelectedItem();
        String hora = (String) comboHorarios.getSelectedItem();

        if (clienteElegido == null || servicio == null || profesional == null
                || dateChooser.getDate() == null || hora == null) {
            JOptionPane.showMessageDialog(this, "Completá todos los datos y buscá horarios.");
            return;
        }

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = formato.format(dateChooser.getDate());

        int confirmar = JOptionPane.showConfirmDialog(
                this,
                "Confirmás el registro del turno?\n\n"
                + "Cliente: " + clienteElegido.getNombre() + " " + clienteElegido.getApellido() + "\n"
                + "Servicio: " + servicio.getNombre() + "\n"
                + "Profesional: " + profesional.getNombre() + " " + profesional.getApellido() + "\n"
                + "Fecha: " + fecha + "\n"
                + "Hora: " + hora + "\n"
                + "Precio: $" + servicio.getPrecio(),
                "Confirmar registro",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmar != JOptionPane.YES_OPTION) return;

        String resultado = turnoService.reservarTurno(
                clienteElegido,
                profesional,
                servicio,
                fecha,
                hora
        );

        if ("OK".equals(resultado)) {
            JOptionPane.showMessageDialog(this, "¡Turno registrado correctamente!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, resultado);
        }
    }
}