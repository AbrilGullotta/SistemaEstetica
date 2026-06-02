package interfaz;

import javax.swing.JOptionPane;
import modelo.Usuario;
import servicio.UsuarioService;
import util.Validador;

public class MenuPrincipal {

    private UsuarioService usuarioService = new UsuarioService();

    public void mostrarMenuPrincipal() {
        int opcion;

        do {
            String inputOpcion = JOptionPane.showInputDialog(
                    " SISTEMA ESTÉTICA \n"
                    + "1. Iniciar sesión\n"
                    + "2. Registrarse\n"
                    + "0. Salir"
            );
            if (inputOpcion == null) break;
            opcion = Integer.parseInt(inputOpcion);

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
        if (email == null) return;
        String contrasenia = JOptionPane.showInputDialog("Contraseña:");
        if (contrasenia == null) return;

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

        String nombre = Validador.pedirCampoObligatorio("Nombre");
        if (nombre == null) return;

        String apellido = Validador.pedirCampoObligatorio("Apellido");
        if (apellido == null) return;

        String dni = JOptionPane.showInputDialog("DNI:");
        if (!Validador.esDniValido(dni)) {
            JOptionPane.showMessageDialog(null, "DNI inválido. Debe tener 7 u 8 dígitos.");
            return;
        }

        String email = JOptionPane.showInputDialog("Email:");
        if (!Validador.esEmailValido(email)) {
            JOptionPane.showMessageDialog(null, "Email inválido.");
            return;
        }

        String telefono = JOptionPane.showInputDialog("Teléfono:");
        if (!Validador.esTelefonoValido(telefono)) {
            JOptionPane.showMessageDialog(null, "Teléfono inválido. Solo números, entre 8 y 15 dígitos.");
            return;
        }

        String pass = JOptionPane.showInputDialog("Contraseña:");
        if (!Validador.esContraseniaValida(pass)) {
            JOptionPane.showMessageDialog(null, "La contraseña debe tener al menos 8 caracteres, una letra y un número.");
            return;
        }

        String fnac = JOptionPane.showInputDialog("Fecha de nacimiento (AAAA-MM-DD):");
        if (!Validador.esFechaValida(fnac)) {
            JOptionPane.showMessageDialog(null, "Fecha inválida. Usá el formato AAAA-MM-DD.");
            return;
        }

        String resultado = usuarioService.registrarCliente(nombre, apellido, dni, email, telefono, pass, fnac);

        if ("OK".equals(resultado)) {
            JOptionPane.showMessageDialog(null, "¡Registro exitoso! Ya podés iniciar sesión.");
        } else {
            JOptionPane.showMessageDialog(null, resultado);
        }
    }
}