package interfaz;

import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.toedter.calendar.JDateChooser;

import modelo.Disponibilidad;
import modelo.Usuario;
import repository.DisponibilidadRepository;
import util.Validador;

public class VentanaDisponibilidadProfesionalForm extends JFrame {

    private Usuario profesionalLogueado;

    private JDateChooser dateChooser;

    private JComboBox<String> comboHoraInicio;
    private JComboBox<String> comboHoraFin;

    private JButton btnGuardar;
    private JButton btnCerrar;

    private DisponibilidadRepository dispRepo =
            new DisponibilidadRepository();

    public VentanaDisponibilidadProfesionalForm(Usuario profesionalLogueado) {

        this.profesionalLogueado = profesionalLogueado;

        setTitle("Cargar disponibilidad");
        setBounds(420, 150, 450, 320);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializarComponentes();
    }

    private void inicializarComponentes() {

        JLabel lblTitulo = new JLabel("CARGAR DISPONIBILIDAD");
        lblTitulo.setBounds(130, 20, 220, 30);
        add(lblTitulo);

        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(50, 80, 120, 25);
        add(lblFecha);

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setBounds(180, 80, 180, 25);
        add(dateChooser);

        JLabel lblHoraInicio = new JLabel("Hora inicio:");
        lblHoraInicio.setBounds(50, 130, 120, 25);
        add(lblHoraInicio);

        comboHoraInicio = new JComboBox<>();
        comboHoraInicio.setBounds(180, 130, 180, 25);
        add(comboHoraInicio);

        JLabel lblHoraFin = new JLabel("Hora fin:");
        lblHoraFin.setBounds(50, 180, 120, 25);
        add(lblHoraFin);

        comboHoraFin = new JComboBox<>();
        comboHoraFin.setBounds(180, 180, 180, 25);
        add(comboHoraFin);

        cargarHorarios();

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(90, 235, 120, 30);
        add(btnGuardar);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(240, 235, 120, 30);
        add(btnCerrar);

        btnGuardar.addActionListener(e -> guardarDisponibilidad());
        btnCerrar.addActionListener(e -> dispose());
    }

    private void cargarHorarios() {

        for (int hora = 8; hora <= 22; hora++) {

            comboHoraInicio.addItem(
                    String.format("%02d:00:00", hora)
            );

            comboHoraFin.addItem(
                    String.format("%02d:00:00", hora)
            );
        }
    }

    private void guardarDisponibilidad() {

        if (dateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(null,
                    "Seleccioná una fecha.");
            return;
        }

        SimpleDateFormat formato =
                new SimpleDateFormat("yyyy-MM-dd");

        String fecha =
                formato.format(dateChooser.getDate());

        if (!Validador.esFechaFutura(fecha)) {
            JOptionPane.showMessageDialog(null,
                    "La fecha debe ser hoy o futura.");
            return;
        }

        String horaInicio =
                comboHoraInicio.getSelectedItem().toString();

        String horaFin =
                comboHoraFin.getSelectedItem().toString();

        if (horaFin.compareTo(horaInicio) <= 0) {
            JOptionPane.showMessageDialog(null,
                    "La hora fin debe ser posterior a la hora inicio.");
            return;
        }

        Disponibilidad d = new Disponibilidad();

        d.setDia(fecha);
        d.setHoraInicio(horaInicio);
        d.setHoraFin(horaFin);
        d.setIdProfesional(
                profesionalLogueado.getIdUsuario()
        );

        boolean ok = dispRepo.guardar(d);

        if (ok) {

            JOptionPane.showMessageDialog(null,
                    "Disponibilidad cargada correctamente.");

            dispose();

        } else {

            JOptionPane.showMessageDialog(null,
                    "Error al guardar disponibilidad.");
        }
    }
}