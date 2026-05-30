package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import conexion.Conexion;
import modelo.Recordatorio;

public class RecordatorioRepository {

    // Guarda el recordatorio en la DB (estado = false = pendiente)
    public boolean guardar(Recordatorio recordatorio) {

        String sql = "INSERT INTO recordatorio (id_turno, fecha_envio, medio, estado) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, recordatorio.getIdTurno());
            ps.setDate(2, java.sql.Date.valueOf(recordatorio.getFechaEnvio()));
            ps.setString(3, recordatorio.getMedio());
            ps.setBoolean(4, recordatorio.isEstado());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al guardar recordatorio: " + e.getMessage());
            return false;
        }
    }

    // Marca el recordatorio como enviado
    public boolean marcarComoEnviado(int idTurno) {

        String sql = "UPDATE recordatorio SET estado = true WHERE id_turno = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTurno);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al marcar recordatorio: " + e.getMessage());
            return false;
        }
    }

    // Verifica si ya existe un recordatorio para ese turno
    public boolean existeRecordatorio(int idTurno) {

        String sql = "SELECT id_recordatorio FROM recordatorio WHERE id_turno = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTurno);
            return ps.executeQuery().next();

        } catch (SQLException e) {
            System.out.println("Error al verificar recordatorio: " + e.getMessage());
            return false;
        }
    }
}