package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexion.Conexion;
import modelo.Servicio;

public class ServicioRepository {

    public boolean guardar(Servicio servicio) {

        String sql = "INSERT INTO servicio (nombre, precio, duracion) VALUES (?, ?, ?)";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, servicio.getNombre());
            ps.setDouble(2, servicio.getPrecio());
            ps.setString(3, servicio.getDuracion());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al guardar servicio: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Servicio> listar() {

        ArrayList<Servicio> lista = new ArrayList<>();
        String sql = "SELECT * FROM servicio ORDER BY nombre";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Servicio s = new Servicio();
                s.setIdServicio(rs.getInt("id_servicio"));
                s.setNombre(rs.getString("nombre"));
                s.setPrecio(rs.getDouble("precio"));
                s.setDuracion(rs.getString("duracion"));
                lista.add(s);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar servicios: " + e.getMessage());
        }

        return lista;
    }
    
 // Modificar servicio 
    public boolean modificar(Servicio servicio) {

        String sql = "UPDATE servicio SET nombre=?, precio=?, duracion=? WHERE id_servicio=?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, servicio.getNombre());
            ps.setDouble(2, servicio.getPrecio());
            ps.setString(3, servicio.getDuracion());
            ps.setInt(4, servicio.getIdServicio());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al modificar servicio: " + e.getMessage());
            return false;
        }
    }
}