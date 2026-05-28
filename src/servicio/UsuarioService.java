package servicio;

import java.util.ArrayList;

import modelo.Cliente;
import modelo.Profesional;
import modelo.Usuario;
import repository.UsuarioRepository;

public class UsuarioService {

    private UsuarioRepository repo = new UsuarioRepository();

   
    // Login
   

    public Usuario iniciarSesion(String email, String contrasenia) {
        if (email == null || email.trim().isEmpty() || contrasenia == null || contrasenia.trim().isEmpty()) {
            return null;
        }
        return repo.login(email, contrasenia);
    }


    // Registro
   
      //Registra un cliente nuevo.
      //Retorna un mensaje de resultado para mostrar en la interfaz.
     
    public String registrarCliente(String nombre, String apellido, String dni,
                                    String email, String telefono,
                                    String contrasenia, String fechaNacimiento) {

        if (nombre.trim().isEmpty()|| apellido.trim().isEmpty() || email.trim().isEmpty() || contrasenia.trim().isEmpty()) {
            return "ERROR: Nombre, apellido, email y contraseña son obligatorios.";
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre.trim());
        cliente.setApellido(apellido.trim());
        cliente.setDni(dni.trim());
        cliente.setEmail(email.trim());
        cliente.setTelefono(telefono.trim());
        cliente.setContrasenia(contrasenia);
        cliente.setFechaNacimiento(fechaNacimiento.trim());

        boolean ok = repo.registrar(cliente);
        return ok ? "OK" : "ERROR: No se pudo registrar. El email puede estar en uso.";
    }

    
     // Registra un profesional nuevo (solo el ADMIN puede hacer esto).
    
    public String registrarProfesional(String nombre, String apellido, String dni,
                                        String email, String telefono,
                                        String contrasenia, String especialidad) {

        if (nombre.trim().isEmpty() || apellido.trim().isEmpty() || email.trim().isEmpty()
                || contrasenia.trim().isEmpty() || especialidad.trim().isEmpty()) {
            return "ERROR: Todos los campos son obligatorios para registrar un profesional.";
        }

        Profesional profesional = new Profesional();
        profesional.setNombre(nombre.trim());
        profesional.setApellido(apellido.trim());
        profesional.setDni(dni.trim());
        profesional.setEmail(email.trim());
        profesional.setTelefono(telefono.trim());
        profesional.setContrasenia(contrasenia);
        profesional.setEspecialidad(especialidad.trim());

        boolean ok = repo.registrar(profesional);
        return ok ? "OK" : "ERROR: No se pudo registrar el profesional.";
    }

   
    // Listados
    

    public ArrayList<Cliente> listarClientes() {
        return repo.listarClientes();
    }

    public ArrayList<Profesional> listarProfesionales() {
        return repo.listarProfesionales();
    }


    // Modificar
    
    public String modificarDatos(Usuario usuario) {
        boolean ok = repo.modificar(usuario);
        return ok ? "OK" : "ERROR: No se pudieron actualizar los datos.";
    }

    public String cambiarContrasenia(int idUsuario, String contraseniaActual,
                                      String nuevaContrasenia) {
        // Verificar contraseña actual antes de cambiar
        Usuario u = repo.buscarPorId(idUsuario);
        if (u == null) return "ERROR: Usuario no encontrado.";

        // Reutilizamos el login para verificar
        Usuario verificado = repo.login(u.getEmail(), contraseniaActual);
        if (verificado == null) return "ERROR: La contraseña actual es incorrecta.";

        if (nuevaContrasenia.length() < 4) return "ERROR: La nueva contraseña debe tener al menos 4 caracteres.";

        boolean ok = repo.cambiarContrasenia(idUsuario, nuevaContrasenia);
        return ok ? "OK" : "ERROR: No se pudo cambiar la contraseña.";
    }
}