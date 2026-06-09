package interfaz;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelo.Servicio;
import servicio.ServicioService;
import util.Validador;

public class VentanaModificarServicioAdmin extends JFrame {

    private JTable tablaServicios;
    private DefaultTableModel modelo;
    private JButton btnModificar;

    private ArrayList<Servicio> servicios;
    private ServicioService servicioService = new ServicioService();

    public VentanaModificarServicioAdmin(ArrayList<Servicio> servicios) {

        this.servicios = servicios;

        setTitle("Modificar servicio");
        setBounds(300, 150, 750, 420);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modelo = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Precio", "Duración"},
                0
        );

        tablaServicios = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tablaServicios);
        scroll.setBounds(20, 20, 690, 300);
        add(scroll);

        btnModificar = new JButton("Modificar servicio seleccionado");
        btnModificar.setBounds(240, 335, 260, 30);
        add(btnModificar);

        btnModificar.addActionListener(e -> modificarSeleccionado());

        cargarServicios();
    }

    private void cargarServicios() {

        modelo.setRowCount(0);

        for (Servicio s : servicios) {
            modelo.addRow(new Object[]{
                    s.getIdServicio(),
                    s.getNombre(),
                    s.getPrecio(),
                    s.getDuracion()
            });
        }
    }

    private void modificarSeleccionado() {

        int fila = tablaServicios.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccioná un servicio de la tabla.");
            return;
        }

        Servicio servicio = servicios.get(fila);

        String nuevoNombre = JOptionPane.showInputDialog("Nombre:", servicio.getNombre());
        if (nuevoNombre == null) return;

        String nuevoPrecioStr = JOptionPane.showInputDialog("Precio:", servicio.getPrecio());
        if (nuevoPrecioStr == null) return;

        String nuevaDuracion = JOptionPane.showInputDialog("Duración (HH:MM:SS):", servicio.getDuracion());
        if (nuevaDuracion == null) return;

        double nuevoPrecio;

        try {
            nuevoPrecio = Double.parseDouble(nuevoPrecioStr.replace(",", "."));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ERROR: El precio debe ser un número válido.");
            return;
        }

        if (!Validador.esValido(nuevoNombre)) {
            JOptionPane.showMessageDialog(null, "El nombre del servicio es obligatorio.");
            return;
        }

        servicio.setNombre(nuevoNombre.trim());
        servicio.setPrecio(nuevoPrecio);
        servicio.setDuracion(nuevaDuracion.trim());

        String resultado = servicioService.modificarServicio(servicio);

        if ("OK".equals(resultado)) {
            JOptionPane.showMessageDialog(null, "Servicio modificado correctamente.");

            modelo.setValueAt(servicio.getNombre(), fila, 1);
            modelo.setValueAt(servicio.getPrecio(), fila, 2);
            modelo.setValueAt(servicio.getDuracion(), fila, 3);

        } else {
            JOptionPane.showMessageDialog(null, resultado);
        }
    }
}