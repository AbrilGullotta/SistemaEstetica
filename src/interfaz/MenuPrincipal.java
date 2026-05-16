package interfaz;

import javax.swing.JOptionPane;

import modelo.Usuario;
import servicio.LoginService;

public class MenuPrincipal {

    public void mostrarMenuPrincipal() {
        int opcion;

        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(
                    " SISTEMA ESTETICA \n"
                    + "1. Iniciar sesión\n"
                    + "2. Registrarse\n"
                    + "0. Salir"
            ));

            switch (opcion) {
                case 1:
                    String email = JOptionPane.showInputDialog("Ingrese email:");
                    String contrasenia = JOptionPane.showInputDialog("Ingrese contraseña:");

                    LoginService loginService = new LoginService();
                    Usuario usuario = loginService.iniciarSesion(email, contrasenia);

                    if (usuario != null) {
                        JOptionPane.showMessageDialog(null, "Bienvenido " + usuario.getNombre());

                        if (usuario.getRol().equals("ADMIN")) {
                            MenuAdministrador menuAdmin = new MenuAdministrador();
                            menuAdmin.mostrarMenuAdministrador();

                        } else if (usuario.getRol().equals("CLIENTE")) {
                            MenuCliente menuCliente = new MenuCliente();
                            menuCliente.mostrarMenuCliente();

                        } else if (usuario.getRol().equals("PROFESIONAL")) {
                            MenuProfesional menuProfesional = new MenuProfesional();
                            menuProfesional.mostrarMenuProfesional();

                        } else {
                            JOptionPane.showMessageDialog(null, "Rol no reconocido");
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Credenciales incorrectas");
                    }
                    break;

                case 2:

                    Usuario nuevoUsuario = new Usuario();

                    nuevoUsuario.setNombre(
                            JOptionPane.showInputDialog("Ingrese nombre:")
                    );

                    nuevoUsuario.setApellido(
                            JOptionPane.showInputDialog("Ingrese apellido:")
                    );

                    nuevoUsuario.setDni(
                            JOptionPane.showInputDialog("Ingrese DNI:")
                    );

                    nuevoUsuario.setEmail(
                            JOptionPane.showInputDialog("Ingrese email:")
                    );

                    nuevoUsuario.setTelefono(
                            JOptionPane.showInputDialog("Ingrese teléfono:")
                    );

                    nuevoUsuario.setContrasenia(
                            JOptionPane.showInputDialog("Ingrese contraseña:")
                    );

                    nuevoUsuario.setFechaNacimiento(
                            JOptionPane.showInputDialog("Ingrese fecha de nacimiento (AAAA-MM-DD):")
                    );

                    LoginService registroService = new LoginService();

                    registroService.registrarCliente(nuevoUsuario);

                    break;
                case 0:
                    JOptionPane.showMessageDialog(null, "Saliendo del sistema");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida");
            }

        } while (opcion != 0);
    }
}