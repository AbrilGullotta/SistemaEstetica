package interfaz;

import javax.swing.JOptionPane;
import modelo.Usuario;
import servicio.UsuarioService;

public class MenuPrincipal {

    private UsuarioService usuarioService = new UsuarioService();

    public void mostrarMenuPrincipal() {
        int opcion;

        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(
                    " SISTEMA ESTÉTICA \n"
                    + "1. Iniciar sesión\n"
                    + "2. Registrarse\n"
                    + "0. Salir"
            ));

            switch (opcion) {
                case 1:
                    login();
                    break;
                case 2:
                    registrarCliente();
                    break;
                case 0:
                    JOptionPane.showMessageDialog(null, "¡Hasta pronto!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida");
            }

        } while (opcion != 0);
    }

    private void login() {
        String email      = JOptionPane.showInputDialog("Email:");
        String contrasenia = JOptionPane.showInputDialog("Contraseña:");

        Usuario usuario = usuarioService.iniciarSesion(email, contrasenia);

        if (usuario == null) {
            JOptionPane.showMessageDialog(null, "Email o contraseña incorrectos.");
            return;
        }

        JOptionPane.showMessageDialog(null, "Bienvenido/a, " + usuario.getNombre() + "!");

        switch (usuario.getRol()) {
            case "ADMIN":
            	new MenuAdministrador().mostrarMenuAdministrador();
                break;
            case "CLIENTE":
                new MenuCliente(usuario).mostrarMenuCliente();
                break;
            case "PROFESIONAL":
                new MenuProfesional(usuario).mostrarMenuProfesional();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Rol no reconocido: " + usuario.getRol());
        }
    }

    private void registrarCliente() {
        String nombre    = JOptionPane.showInputDialog("Nombre:");
        String apellido  = JOptionPane.showInputDialog("Apellido:");
        String dni       = JOptionPane.showInputDialog("DNI:");
        String email     = JOptionPane.showInputDialog("Email:");
        String telefono  = JOptionPane.showInputDialog("Teléfono:");
        String pass      = JOptionPane.showInputDialog("Contraseña:");
        String fnac      = JOptionPane.showInputDialog("Fecha de nacimiento (AAAA-MM-DD):");

        String resultado = usuarioService.registrarCliente(
                nombre, apellido, dni, email, telefono, pass, fnac
        );

        if ("OK".equals(resultado)) {
            JOptionPane.showMessageDialog(null, "¡Registro exitoso! Ya podés iniciar sesión.");
        } else {
            JOptionPane.showMessageDialog(null, resultado);
        }
    }
}