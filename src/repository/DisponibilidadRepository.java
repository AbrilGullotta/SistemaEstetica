package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexion.Conexion;
import modelo.Disponibilidad;

public class DisponibilidadRepository {

    public boolean guardar(Disponibilidad disponibilidad) {

        String sql = "INSERT INTO disponibilidad (dia, hora_inicio, hora_fin, id_profesional) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, disponibilidad.getDia());
            ps.setString(2, disponibilidad.getHoraInicio());
            ps.setString(3, disponibilidad.getHoraFin());
            ps.setInt(4, disponibilidad.getIdProfesional());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al guardar disponibilidad: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Disponibilidad> listarPorProfesional(int idProfesional) {

        ArrayList<Disponibilidad> lista = new ArrayList<>();
        String sql = "SELECT * FROM disponibilidad WHERE id_profesional = ? ORDER BY dia, hora_inicio";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProfesional);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Disponibilidad d = new Disponibilidad();
                d.setIdDisponibilidad(rs.getInt("id_disponibilidad"));
                d.setDia(rs.getString("dia"));
                d.setHoraInicio(rs.getString("hora_inicio"));
                d.setHoraFin(rs.getString("hora_fin"));
                d.setIdProfesional(rs.getInt("id_profesional"));
                lista.add(d);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar disponibilidad: " + e.getMessage());
        }

        return lista;
    }
}