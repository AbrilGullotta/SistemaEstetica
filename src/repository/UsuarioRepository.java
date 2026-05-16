package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexion.Conexion;
import modelo.Usuario;
import javax.swing.JOptionPane;

public class UsuarioRepository {

    public Usuario login(String email, String contrasenia) {

        String sql = "SELECT * FROM usuario WHERE email = ? AND contrasenia = ?";

        try {

            Connection conn = Conexion.conectar();

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, contrasenia);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setEmail(rs.getString("email"));
                usuario.setContrasenia(rs.getString("contrasenia"));
                usuario.setRol(rs.getString("rol"));

                return usuario;
            }

        } catch (SQLException e) {
            System.out.println("Error login: " + e.getMessage());
        }

        return null;
    }
    
    public void registrarCliente(Usuario usuario) {

        String sql = "INSERT INTO usuario "
                + "(nombre, apellido, dni, email, telefono, contrasenia, fecha_nacimiento, rol) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = Conexion.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getDni());
            ps.setString(4, usuario.getEmail());
            ps.setString(5, usuario.getTelefono());
            ps.setString(6, usuario.getContrasenia());
            ps.setString(7, usuario.getFechaNacimiento());
            ps.setString(8, "CLIENTE");

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Cliente registrado correctamente");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar cliente: " + e.getMessage());
        }
    }
}