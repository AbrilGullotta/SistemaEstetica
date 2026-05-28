package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexion.Conexion;
import modelo.Cliente;
import modelo.Profesional;
import modelo.Usuario;
import util.Encriptador;

public class UsuarioRepository {

  
    // Login


    public Usuario login(String email, String contrasenia) {

        String sql = "SELECT * FROM usuario WHERE email = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hashGuardado = rs.getString("contrasenia");

                if (Encriptador.verificar(contrasenia, hashGuardado)) {
                    return mapearUsuario(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error en login: " + e.getMessage());
        }

        return null;
    }

    // Registro


    
      //Registra cualquier usuario. El rol se toma del objeto usuario.
      //Para CLIENTE:      especialidad = null
      //Para PROFESIONAL:  especialidad = valor correspondiente
     
    public boolean registrar(Usuario usuario) {

        String sql = "INSERT INTO usuario "
                + "(nombre, apellido, dni, email, telefono, contrasenia, fecha_nacimiento, rol, especialidad) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getDni());
            ps.setString(4, usuario.getEmail());
            ps.setString(5, usuario.getTelefono());
            ps.setString(6, Encriptador.hash(usuario.getContrasenia()));
            ps.setString(7, usuario.getFechaNacimiento());
            ps.setString(8, usuario.getRol());
            ps.setString(9, usuario.getEspecialidad()); // null si no es profesional

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }

    
    // Buscar por id


    public Usuario buscarPorId(int idUsuario) {

        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearUsuario(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar usuario: " + e.getMessage());
        }

        return null;
    }


    // Listar por rol 


    public ArrayList<Cliente> listarClientes() {

        ArrayList<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE rol = 'CLIENTE' ORDER BY apellido, nombre";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdUsuario(rs.getInt("id_usuario"));
                c.setNombre(rs.getString("nombre"));
                c.setApellido(rs.getString("apellido"));
                c.setDni(rs.getString("dni"));
                c.setEmail(rs.getString("email"));
                c.setTelefono(rs.getString("telefono"));
                c.setFechaNacimiento(rs.getString("fecha_nacimiento"));
                clientes.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }

        return clientes;
    }

    public ArrayList<Profesional> listarProfesionales() {

        ArrayList<Profesional> profesionales = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE rol = 'PROFESIONAL' ORDER BY apellido, nombre";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Profesional p = new Profesional();
                p.setIdUsuario(rs.getInt("id_usuario"));
                p.setNombre(rs.getString("nombre"));
                p.setApellido(rs.getString("apellido"));
                p.setDni(rs.getString("dni"));
                p.setEmail(rs.getString("email"));
                p.setTelefono(rs.getString("telefono"));
                p.setEspecialidad(rs.getString("especialidad"));
                profesionales.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar profesionales: " + e.getMessage());
        }

        return profesionales;
    }


    // Modificar


    public boolean modificar(Usuario usuario) {

        String sql = "UPDATE usuario SET nombre=?, apellido=?, dni=?, telefono=?, "
                + "fecha_nacimiento=?, especialidad=? "
                + "WHERE id_usuario=?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getDni());
            ps.setString(4, usuario.getTelefono());
            ps.setString(5, usuario.getFechaNacimiento());
            ps.setString(6, usuario.getEspecialidad());
            ps.setInt(7, usuario.getIdUsuario());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al modificar usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean cambiarContrasenia(int idUsuario, String nuevaContrasenia) {

        String sql = "UPDATE usuario SET contrasenia=? WHERE id_usuario=?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, Encriptador.hash(nuevaContrasenia));
            ps.setInt(2, idUsuario);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al cambiar contraseña: " + e.getMessage());
            return false;
        }
    }


    // Mapper privado: ResultSet → objeto del tipo correcto según rol


    private Usuario mapearUsuario(ResultSet rs) throws SQLException {

        String rol = rs.getString("rol");
        Usuario u;

        if ("PROFESIONAL".equals(rol)) {
            Profesional p = new Profesional();
            p.setEspecialidad(rs.getString("especialidad"));
            u = p;
        } else if ("CLIENTE".equals(rol)) {
            u = new Cliente();
        } else {
            u = new Usuario(); // ADMIN u otro rol
        }

        u.setIdUsuario(rs.getInt("id_usuario"));
        u.setNombre(rs.getString("nombre"));
        u.setApellido(rs.getString("apellido"));
        u.setDni(rs.getString("dni"));
        u.setEmail(rs.getString("email"));
        u.setTelefono(rs.getString("telefono"));
        u.setContrasenia(rs.getString("contrasenia"));
        u.setFechaNacimiento(rs.getString("fecha_nacimiento"));
        u.setRol(rol);

        return u;
    }
}