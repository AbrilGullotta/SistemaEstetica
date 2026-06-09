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
import java.text.SimpleDateFormat;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import com.toedter.calendar.JDateChooser;

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

        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");

        JSpinner spinnerHoraInicio = new JSpinner(
                new SpinnerNumberModel(9, 0, 23, 1)
        );

        JSpinner spinnerMinutoInicio = new JSpinner(
                new SpinnerNumberModel(0, 0, 59, 15)
        );

        JSpinner spinnerHoraFin = new JSpinner(
                new SpinnerNumberModel(18, 0, 23, 1)
        );

        JSpinner spinnerMinutoFin = new JSpinner(
                new SpinnerNumberModel(0, 0, 59, 15)
        );

        JPanel panel = new JPanel();
        panel.add(new JLabel("Fecha:"));
        panel.add(dateChooser);
        panel.add(new JLabel("Inicio:"));
        panel.add(spinnerHoraInicio);
        panel.add(new JLabel(":"));
        panel.add(spinnerMinutoInicio);
        panel.add(new JLabel("Fin:"));
        panel.add(spinnerHoraFin);
        panel.add(new JLabel(":"));
        panel.add(spinnerMinutoFin);

        int resultado = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Cargar disponibilidad",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (resultado != JOptionPane.OK_OPTION || dateChooser.getDate() == null) {
            return;
        }

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String dia = formato.format(dateChooser.getDate());

        if (!Validador.esFechaFutura(dia)) {
            JOptionPane.showMessageDialog(null, "La fecha debe ser hoy o en el futuro.");
            return;
        }

        int hInicio = (int) spinnerHoraInicio.getValue();
        int mInicio = (int) spinnerMinutoInicio.getValue();
        int hFin = (int) spinnerHoraFin.getValue();
        int mFin = (int) spinnerMinutoFin.getValue();

        String horaInicio = String.format("%02d:%02d:00", hInicio, mInicio);
        String horaFin = String.format("%02d:%02d:00", hFin, mFin);

        if (horaFin.compareTo(horaInicio) <= 0) {
            JOptionPane.showMessageDialog(null, "La hora de fin debe ser posterior a la hora de inicio.");
            return;
        }

        Disponibilidad d = new Disponibilidad();
        d.setDia(dia);
        d.setHoraInicio(horaInicio);
        d.setHoraFin(horaFin);
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

        VentanaTurnosProfesional ventana = new VentanaTurnosProfesional(turnos);
        ventana.setVisible(true);
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