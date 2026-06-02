package servicio;

import java.util.ArrayList;
import modelo.Cliente;
import modelo.Profesional;
import modelo.Usuario;
import repository.UsuarioRepository;
import util.Validador;

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

    	if (!Validador.esValido(nombre) || !Validador.esValido(apellido)) {
            return "ERROR: Nombre y apellido son obligatorios.";
        }
        if (!Validador.esDniValido(dni)) {
            return "ERROR: DNI inválido. Debe tener 7 u 8 dígitos.";
        }
        if (!Validador.esEmailValido(email)) {
            return "ERROR: Email inválido.";
        }
        if (!Validador.esTelefonoValido(telefono)) {
            return "ERROR: Teléfono inválido. Solo números, entre 8 y 15 dígitos.";
        }
        if (!Validador.esContraseniaValida(contrasenia)) {
            return "ERROR: La contraseña debe tener al menos 8 caracteres, una letra y un número.";
        }
        if (!Validador.esFechaValida(fechaNacimiento)) {
            return "ERROR: Fecha de nacimiento inválida. Formato: AAAA-MM-DD.";
        
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

    	  if (!Validador.esValido(nombre) || !Validador.esValido(apellido)) {
    	        return "ERROR: Nombre y apellido son obligatorios.";
    	    }
    	    if (!Validador.esDniValido(dni)) {
    	        return "ERROR: DNI inválido.";
    	    }
    	    if (!Validador.esEmailValido(email)) {
    	        return "ERROR: Email inválido.";
    	    }
    	    if (!Validador.esTelefonoValido(telefono)) {
    	        return "ERROR: Teléfono inválido.";
    	    }
    	    if (!Validador.esContraseniaValida(contrasenia)) {
    	        return "ERROR: La contraseña debe tener al menos 8 caracteres, una letra y un número.";
    	    }
    	    if (!Validador.esValido(especialidad)) {
    	        return "ERROR: La especialidad es obligatoria.";
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
    
    //Buscar por id 
    
    public modelo.Usuario buscarPorId(int idUsuario) {
        return repo.buscarPorId(idUsuario);
    }
    
   // Buscar Cliente
    public ArrayList<Cliente> buscarClientes(String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return repo.buscarClientes(criterio);
    }


    // Modificar
    
    public String modificarDatos(Usuario usuario) {
    	if (!Validador.esValido(usuario.getNombre()) || !Validador.esValido(usuario.getApellido())) {
            return "ERROR: Nombre y apellido son obligatorios.";
        }
        if (!Validador.esEmailValido(usuario.getEmail())) {
            return "ERROR: Email inválido.";
        }
        if (!Validador.esTelefonoValido(usuario.getTelefono())) {
            return "ERROR: Teléfono inválido.";
        }
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

        if (!Validador.esContraseniaValida(nuevaContrasenia)) {
            return "ERROR: La nueva contraseña debe tener al menos 8 caracteres, una letra y un número.";
        }

        boolean ok = repo.cambiarContrasenia(idUsuario, nuevaContrasenia);
        return ok ? "OK" : "ERROR: No se pudo cambiar la contraseña.";
    }
}