package interfaz;

import javax.swing.JOptionPane;

public class MenuProfesional {

    public void mostrarMenuProfesional() {
        int opcion;

        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(
                    "MENU PROFESIONAL\n"
                    + "1. Cargar disponibilidad\n"
                    + "2. Modificar disponibilidad\n"
                    + "3. Consultar turnos asignados\n"
                    + "0. Volver"
            ));

            switch (opcion) {
                case 1:
                    JOptionPane.showMessageDialog(null, "Pantalla de carga de disponibilidad");
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Pantalla de modificación de disponibilidad");
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Pantalla de consulta de turnos asignados");
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