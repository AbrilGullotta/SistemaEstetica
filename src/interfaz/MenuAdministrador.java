package interfaz;

import javax.swing.JOptionPane;

public class MenuAdministrador {

    public void mostrarMenuAdministrador() {
        int opcion;

        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(
                    "MENU ADMINISTRADOR\n"
                    + "1. Registrar cliente\n"
                    + "2. Registrar profesional\n"
                    + "3. Registrar servicio\n"
                    + "4. Gestionar turnos\n"
                    + "5. Registrar seña\n"
                    + "6. Ver clientes\n"
                    + "7. Ver profesionales\n"
                    + "8. Ver servicios\n"
                    + "9. Ver turnos\n"
                    + "0. Volver"
            ));

            switch (opcion) {
                case 1:
                    JOptionPane.showMessageDialog(null, "Pantalla de registro de cliente");
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Pantalla de registro de profesional");
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Pantalla de registro de servicio");
                    break;
                case 4:
                    JOptionPane.showMessageDialog(null, "Pantalla de gestión de turnos");
                    break;
                case 5:
                    JOptionPane.showMessageDialog(null, "Pantalla de registro de seña");
                    break;
                case 6:
                    JOptionPane.showMessageDialog(null, "Pantalla para ver clientes");
                    break;
                case 7:
                    JOptionPane.showMessageDialog(null, "Pantalla para ver profesionales");
                    break;
                case 8:
                    JOptionPane.showMessageDialog(null, "Pantalla para ver servicios");
                    break;
                case 9:
                    JOptionPane.showMessageDialog(null, "Pantalla para ver turnos");
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