package servicio;

import modelo.Usuario;
import repository.UsuarioRepository;

public class LoginService {

    UsuarioRepository repo = new UsuarioRepository();

    public Usuario iniciarSesion(String email, String contrasenia) {

        return repo.login(email, contrasenia);
       
    }
    
    public void registrarCliente(Usuario usuario) {

        repo.registrarCliente(usuario);
    }
}