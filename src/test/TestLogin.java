package test;

import modelo.Usuario;
import repository.UsuarioRepository;

public class TestLogin {

    public static void main(String[] args) {

        UsuarioRepository repo = new UsuarioRepository();

        Usuario usuario = repo.login("admin@test.com", "1234");

        if(usuario != null) {

            System.out.println("Login correcto");
            System.out.println("Bienvenido " + usuario.getNombre());
            System.out.println("Rol: " + usuario.getRol());

        } else {

            System.out.println("Credenciales incorrectas");

        }
    }
}