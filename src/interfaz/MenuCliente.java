package interfaz;

import javax.swing.JOptionPane;

public class MenuCliente {

    public void mostrarMenuCliente() {
        int opcion;

        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(
                    " MENU CLIENTE\n"
                    + "1. Reservar turno\n"
                    + "2. Pagar seña\n"
                    + "3. Consultar turnos\n"
                    + "4. Modificar datos\n"
                    + "0. Volver"
            ));

            switch (opcion) {
                case 1:
                    JOptionPane.showMessageDialog(null, "Pantalla de reserva de turno");
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Pantalla de pago de seña");
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Pantalla de consulta de turnos");
                    break;
                case 4:
                    JOptionPane.showMessageDialog(null, "Pantalla de modificación de datos");
                    break;
                case 0:
                    JOptionPane.showMessageDialog(null, "Volviendo al menú principal");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida");
            }

        } while (opcion != 0);
    }
}