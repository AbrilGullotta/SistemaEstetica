package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import conexion.Conexion;
import modelo.Senia;

public class SeniaRepository {

    public boolean guardar(Senia senia) {

        String sql = "INSERT INTO senia (monto, fecha_pago, estado_pago, id_turno) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, senia.getMonto());
            ps.setString(2, senia.getFechaPago());
            ps.setBoolean(3, senia.isEstadoPago());
            ps.setInt(4, senia.getIdTurno());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al guardar seña: " + e.getMessage());
            return false;
        }
    }

    public boolean existeSeniaPorTurno(int idTurno) {

        String sql = "SELECT id_senia FROM senia WHERE id_turno = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTurno);
            return ps.executeQuery().next();

        } catch (SQLException e) {
            System.out.println("Error al verificar seña: " + e.getMessage());
            return false;
        }
    }
}