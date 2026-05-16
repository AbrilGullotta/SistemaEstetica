package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import conexion.Conexion;
import modelo.Cliente;

public class ClienteRepository {

    public void guardarCliente(Cliente cliente) {

        String sql = "INSERT INTO cliente "
        		+ "(nombre, apellido, dni, email, telefono, usuario, contrasenia, fecha_nacimiento) "
        		+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conexion = Conexion.conectar();

            PreparedStatement ps = conexion.prepareStatement(sql);

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3, cliente.getDni());
            ps.setString(4, cliente.getMail());
            ps.setString(5, cliente.getTelefono());
            ps.setString(6, cliente.getUsuario());
            ps.setString(7, cliente.getContrasenia());
            ps.setString(8, cliente.getFechaNacimiento());
            ps.executeUpdate();

            System.out.println("Cliente guardado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al guardar cliente");
            e.printStackTrace();
        }
    }
}