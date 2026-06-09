package interfaz;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import modelo.Disponibilidad;
import modelo.Turno;
import modelo.Usuario;
import repository.DisponibilidadRepository;
import servicio.TurnoService;
import util.Validador;

public class MenuProfesional extends JFrame{

    private Usuario profesionalLogueado;
    private DisponibilidadRepository dispRepo = new DisponibilidadRepository();
    private TurnoService turnoService = new TurnoService();
    private JLabel lblTitulo;
    private JButton btnCargarDisponibilidad;
    private JButton btnVerDisponibilidad;
    private JButton btnConsultarTurnos;
    private JButton btnCerrar;
    
    public MenuProfesional(Usuario usuario) {
        this.profesionalLogueado = usuario;
        inicializarVentana();
    }

    public void mostrarMenuProfesional() {
        setVisible(true);
    }

    private void cargarDisponibilidad() {

        String dia = JOptionPane.showInputDialog("Fecha (AAAA-MM-DD):");
        if (dia == null || dia.trim().isEmpty()) return;

        if (!Validador.esFechaFutura(dia.trim())) {
            JOptionPane.showMessageDialog(null, "La fecha debe ser hoy o en el futuro. Formato: AAAA-MM-DD.");
            return;
        }

        String horaInicio = JOptionPane.showInputDialog("Hora de inicio (HH:MM:SS):");
        if (horaInicio == null || horaInicio.trim().isEmpty()) return;

        if (!horaInicio.trim().matches("^([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$")) {
            JOptionPane.showMessageDialog(null, "Hora de inicio inválida. Formato: HH:MM:SS.");
            return;
        }

        String horaFin = JOptionPane.showInputDialog("Hora de fin (HH:MM:SS):");
        if (horaFin == null || horaFin.trim().isEmpty()) return;

        if (!horaFin.trim().matches("^([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$")) {
            JOptionPane.showMessageDialog(null, "Hora de fin inválida. Formato: HH:MM:SS.");
            return;
        }

        if (horaFin.trim().compareTo(horaInicio.trim()) <= 0) {
            JOptionPane.showMessageDialog(null, "La hora de fin debe ser posterior a la hora de inicio.");
            return;
        }

        Disponibilidad d = new Disponibilidad();
        d.setDia(dia.trim());
        d.setHoraInicio(horaInicio.trim());
        d.setHoraFin(horaFin.trim());
        d.setIdProfesional(profesionalLogueado.getIdUsuario());

        boolean ok = dispRepo.guardar(d);

        if (ok) {
            JOptionPane.showMessageDialog(null, "Disponibilidad cargada correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Error al cargar disponibilidad.");
        }
    }

    private void verDisponibilidad() {

        ArrayList<Disponibilidad> lista = dispRepo.listarPorProfesional(
                profesionalLogueado.getIdUsuario()
        );

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tenés disponibilidad cargada.");
            return;
        }

        VentanaDisponibilidadProfesional ventana =
                new VentanaDisponibilidadProfesional(lista);

        ventana.setVisible(true);
    }

    private void consultarTurnos() {

        ArrayList<Turno> turnos = turnoService.listarTurnosPorProfesional(
                profesionalLogueado.getIdUsuario()
        );

        if (turnos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tenés turnos asignados.");
            return;
        }

        StringBuilder sb = new StringBuilder("Tus turnos asignados:\n\n");
        for (Turno t : turnos) {
            sb.append("Fecha: " + t.getFecha() + " " + t.getHora() + "\n");
            sb.append("Cliente: " + t.getCliente().getNombre()
                    + " " + t.getCliente().getApellido() + "\n");
            sb.append("Servicio: " + t.getServicio().getNombre() + "\n");
            sb.append("Estado: " + t.getEstado() + "\n");
            sb.append("-----------------------------\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }
    private void inicializarVentana() {
        setTitle("Menú Profesional");
        setLayout(null);
        setBounds(400, 120, 430, 350);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        lblTitulo = new JLabel("MENÚ PROFESIONAL - " + profesionalLogueado.getNombre());
        lblTitulo.setBounds(90, 25, 300, 30);
        add(lblTitulo);

        btnCargarDisponibilidad = new JButton("Cargar disponibilidad");
        btnCargarDisponibilidad.setBounds(105, 80, 220, 30);
        add(btnCargarDisponibilidad);

        btnVerDisponibilidad = new JButton("Ver mi disponibilidad");
        btnVerDisponibilidad.setBounds(105, 125, 220, 30);
        add(btnVerDisponibilidad);

        btnConsultarTurnos = new JButton("Consultar turnos asignados");
        btnConsultarTurnos.setBounds(105, 170, 220, 30);
        add(btnConsultarTurnos);

        btnCerrar = new JButton("Cerrar sesión");
        btnCerrar.setBounds(105, 230, 220, 30);
        add(btnCerrar);

        btnCargarDisponibilidad.addActionListener(e -> cargarDisponibilidad());
        btnVerDisponibilidad.addActionListener(e -> verDisponibilidad());
        btnConsultarTurnos.addActionListener(e -> consultarTurnos());

        btnCerrar.addActionListener(e -> {
            dispose();
            MenuPrincipal menu = new MenuPrincipal();
            menu.setVisible(true);
        });
    }
}