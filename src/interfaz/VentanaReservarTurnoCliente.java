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

public class VentanaReservarTurnoCliente extends JFrame {

    private Cliente clienteLogueado;

    private JComboBox<Servicio> comboServicios;
    private JComboBox<Profesional> comboProfesionales;
    private JComboBox<String> comboHorarios;

    private JDateChooser dateChooser;

    private JButton btnBuscarHorarios;
    private JButton btnReservar;
    private JButton btnCerrar;

    private ServicioService servicioService = new ServicioService();
    private UsuarioService usuarioService = new UsuarioService();
    private TurnoService turnoService = new TurnoService();

    public VentanaReservarTurnoCliente(Cliente clienteLogueado) {

        this.clienteLogueado = clienteLogueado;

        setTitle("Reservar turno");
        setBounds(400, 100, 520, 420);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializarComponentes();
        cargarServicios();
        cargarProfesionales();
    }

    private void inicializarComponentes() {

        JLabel lblTitulo = new JLabel("RESERVAR TURNO");
        lblTitulo.setBounds(200, 20, 180, 30);
        add(lblTitulo);

        JLabel lblServicio = new JLabel("Servicio:");
        lblServicio.setBounds(50, 75, 120, 25);
        add(lblServicio);

        comboServicios = new JComboBox<>();
        comboServicios.setBounds(180, 75, 260, 25);
        add(comboServicios);

        JLabel lblProfesional = new JLabel("Profesional:");
        lblProfesional.setBounds(50, 115, 120, 25);
        add(lblProfesional);

        comboProfesionales = new JComboBox<>();
        comboProfesionales.setBounds(180, 115, 260, 25);
        add(comboProfesionales);

        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(50, 155, 120, 25);
        add(lblFecha);

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setBounds(180, 155, 260, 25);
        add(dateChooser);

        btnBuscarHorarios = new JButton("Buscar horarios");
        btnBuscarHorarios.setBounds(180, 195, 180, 30);
        add(btnBuscarHorarios);

        JLabel lblHorario = new JLabel("Horario:");
        lblHorario.setBounds(50, 245, 120, 25);
        add(lblHorario);

        comboHorarios = new JComboBox<>();
        comboHorarios.setBounds(180, 245, 260, 25);
        add(comboHorarios);

        btnReservar = new JButton("Reservar");
        btnReservar.setBounds(120, 315, 120, 30);
        add(btnReservar);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(270, 315, 120, 30);
        add(btnCerrar);

        btnBuscarHorarios.addActionListener(e -> buscarHorarios());
        btnReservar.addActionListener(e -> reservarTurno());
        btnCerrar.addActionListener(e -> dispose());
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

        Servicio servicio = (Servicio) comboServicios.getSelectedItem();
        Profesional profesional = (Profesional) comboProfesionales.getSelectedItem();

        if (servicio == null || profesional == null || dateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Seleccioná servicio, profesional y fecha.");
            return;
        }

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = formato.format(dateChooser.getDate());

        if (!Validador.esFechaFutura(fecha)) {
            JOptionPane.showMessageDialog(null, "La fecha debe ser hoy o en el futuro.");
            return;
        }

        ArrayList<String> horarios = turnoService.obtenerHorariosDisponibles(
                profesional.getIdUsuario(),
                fecha,
                servicio.getDuracion()
        );

        if (horarios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay horarios disponibles para esa fecha.");
            return;
        }

        for (String h : horarios) {
            comboHorarios.addItem(h);
        }
    }

    private void reservarTurno() {

        Servicio servicio = (Servicio) comboServicios.getSelectedItem();
        Profesional profesional = (Profesional) comboProfesionales.getSelectedItem();
        String hora = (String) comboHorarios.getSelectedItem();

        if (servicio == null || profesional == null || dateChooser.getDate() == null || hora == null) {
            JOptionPane.showMessageDialog(null, "Completá todos los datos y buscá horarios.");
            return;
        }

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = formato.format(dateChooser.getDate());

        int confirmar = JOptionPane.showConfirmDialog(
                null,
                "Confirmás la reserva?\n\n"
                + "Servicio: " + servicio.getNombre() + "\n"
                + "Profesional: " + profesional.getNombre() + " " + profesional.getApellido() + "\n"
                + "Fecha: " + fecha + "\n"
                + "Hora: " + hora + "\n"
                + "Precio: $" + servicio.getPrecio(),
                "Confirmar reserva",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmar != JOptionPane.YES_OPTION) {
            return;
        }

        String resultado = turnoService.reservarTurno(
                clienteLogueado,
                profesional,
                servicio,
                fecha,
                hora
        );

        if ("OK".equals(resultado)) {
            JOptionPane.showMessageDialog(null, "¡Turno reservado correctamente!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, resultado);
        }
    }
}