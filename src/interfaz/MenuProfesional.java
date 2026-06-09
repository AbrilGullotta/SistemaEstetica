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

        VentanaDisponibilidadProfesionalForm ventana =
                new VentanaDisponibilidadProfesionalForm(
                        profesionalLogueado
                );

        ventana.setVisible(true);
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