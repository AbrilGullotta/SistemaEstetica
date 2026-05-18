package repository;

import java.sql.Connection;
import util.Encriptador;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import conexion.Conexion;
import modelo.Cliente;

public class ClienteRepository {

    public void guardarCliente(Cliente cliente) {

        String sql = "INSERT INTO cliente "
        		+ "(nombre, apellido, dni, email, telefono, contrasenia, fecha_nacimiento) "
        		+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conexion = Conexion.conectar();

            PreparedStatement ps = conexion.prepareStatement(sql);

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getDni());
            ps.setString(4, cliente.getEmail());
            ps.setString(5, cliente.getTelefono());
            ps.setString(6, Encriptador.hash(cliente.getContrasenia()));
            ps.setString(7, cliente.getFechaNacimiento());
            ps.executeUpdate();

            System.out.println("Cliente guardado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al guardar cliente");
            e.printStackTrace();
        }
    }
}