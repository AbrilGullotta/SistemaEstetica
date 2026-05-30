package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexion.Conexion;
import modelo.Cliente;
import modelo.Profesional;
import modelo.Servicio;
import modelo.Turno;

public class TurnoRepository {

   
    // Guardar
    
    public boolean guardarTurno(Turno turno) {

        String sql = "INSERT INTO turno (id_cliente, id_profesional, id_servicio, fecha, hora, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, turno.getCliente().getIdUsuario());
            ps.setInt(2, turno.getProfesional().getIdUsuario());
            ps.setInt(3, turno.getServicio().getIdServicio());
            ps.setString(4, turno.getFecha());
            ps.setString(5, turno.getHora());
            ps.setString(6, turno.getEstado());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al guardar turno: " + e.getMessage());
            return false;
        }
    }


    // Listar todos 

    public ArrayList<Turno> listarTurnos() {

        ArrayList<Turno> turnos = new ArrayList<>();

        // Ahora ambos JOINs van contra la tabla usuario
        String sql = "SELECT t.id_turno, t.fecha, t.hora, t.estado, "
                + "c.id_usuario AS id_cliente, c.nombre AS nombre_cliente, c.apellido AS apellido_cliente, "
                + "p.id_usuario AS id_profesional, p.nombre AS nombre_profesional, p.apellido AS apellido_profesional, p.especialidad, "
                + "s.id_servicio, s.nombre AS nombre_servicio, s.precio, s.duracion "
                + "FROM turno t "
                + "INNER JOIN usuario c ON t.id_cliente     = c.id_usuario "
                + "INNER JOIN usuario p ON t.id_profesional = p.id_usuario "
                + "INNER JOIN servicio s ON t.id_servicio   = s.id_servicio "
                + "ORDER BY t.fecha DESC, t.hora DESC";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                turnos.add(mapearTurno(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar turnos: " + e.getMessage());
        }

        return turnos;
    }

    // Listar por cliente 
 
    public ArrayList<Turno> listarTurnosPorCliente(int idCliente) {

        ArrayList<Turno> turnos = new ArrayList<>();

        String sql = "SELECT t.id_turno, t.fecha, t.hora, t.estado, "
                + "c.id_usuario AS id_cliente, c.nombre AS nombre_cliente, c.apellido AS apellido_cliente, "
                + "p.id_usuario AS id_profesional, p.nombre AS nombre_profesional, p.apellido AS apellido_profesional, p.especialidad, "
                + "s.id_servicio, s.nombre AS nombre_servicio, s.precio, s.duracion "
                + "FROM turno t "
                + "INNER JOIN usuario c ON t.id_cliente     = c.id_usuario "
                + "INNER JOIN usuario p ON t.id_profesional = p.id_usuario "
                + "INNER JOIN servicio s ON t.id_servicio   = s.id_servicio "
                + "WHERE t.id_cliente = ? "
                + "ORDER BY t.fecha DESC, t.hora DESC";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                turnos.add(mapearTurno(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar turnos del cliente: " + e.getMessage());
        }

        return turnos;
    }

 
    // Listar por profesional 


    public ArrayList<Turno> listarTurnosPorProfesional(int idProfesional) {

        ArrayList<Turno> turnos = new ArrayList<>();

        String sql = "SELECT t.id_turno, t.fecha, t.hora, t.estado, "
                + "c.id_usuario AS id_cliente, c.nombre AS nombre_cliente, c.apellido AS apellido_cliente, "
                + "p.id_usuario AS id_profesional, p.nombre AS nombre_profesional, p.apellido AS apellido_profesional, p.especialidad, "
                + "s.id_servicio, s.nombre AS nombre_servicio, s.precio, s.duracion "
                + "FROM turno t "
                + "INNER JOIN usuario c ON t.id_cliente     = c.id_usuario "
                + "INNER JOIN usuario p ON t.id_profesional = p.id_usuario "
                + "INNER JOIN servicio s ON t.id_servicio   = s.id_servicio "
                + "WHERE t.id_profesional = ? "
                + "ORDER BY t.fecha ASC, t.hora ASC";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProfesional);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                turnos.add(mapearTurno(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar turnos del profesional: " + e.getMessage());
        }

        return turnos;
    }


    // Validar solapamiento 


    public boolean existeTurnoReservado(int idProfesional, String fecha, String hora) {

    	String sql = "SELECT id_turno FROM turno "
    		    + "WHERE id_profesional = ? AND fecha = ? AND hora = ? "
    		    + "AND estado IN ('RESERVADO', 'CONFIRMADO')";
    	
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProfesional);
            ps.setString(2, fecha);
            ps.setString(3, hora);

            return ps.executeQuery().next();

        } catch (SQLException e) {
            System.out.println("Error al validar turno: " + e.getMessage());
        }

        return false;
    }
    
 // Excluir turno que se esta editanto
    
    public boolean existeTurnoOcupadoExcluyendo(int idProfesional, String fecha,
                                                 String hora, int idTurnoExcluir) {

        String sql = "SELECT id_turno FROM turno "
                   + "WHERE id_profesional = ? AND fecha = ? AND hora = ? "
                   + "AND estado IN ('RESERVADO', 'CONFIRMADO') "
                   + "AND id_turno != ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProfesional);
            ps.setString(2, fecha);
            ps.setString(3, hora);
            ps.setInt(4, idTurnoExcluir);

            return ps.executeQuery().next();

        } catch (SQLException e) {
            System.out.println("Error al validar turno: " + e.getMessage());
            return false;
        }
    }


    // Mapper privado


    private Turno mapearTurno(ResultSet rs) throws SQLException {

        Cliente cliente = new Cliente();
        cliente.setIdUsuario(rs.getInt("id_cliente"));
        cliente.setNombre(rs.getString("nombre_cliente"));
        cliente.setApellido(rs.getString("apellido_cliente"));

        Profesional profesional = new Profesional();
        profesional.setIdUsuario(rs.getInt("id_profesional"));
        profesional.setNombre(rs.getString("nombre_profesional"));
        profesional.setApellido(rs.getString("apellido_profesional"));
        profesional.setEspecialidad(rs.getString("especialidad"));

        Servicio servicio = new Servicio();
        servicio.setIdServicio(rs.getInt("id_servicio"));
        servicio.setNombre(rs.getString("nombre_servicio"));
        servicio.setPrecio(rs.getDouble("precio"));
        servicio.setDuracion(rs.getString("duracion"));

        Turno turno = new Turno();
        turno.setIdTurno(rs.getInt("id_turno"));
        turno.setCliente(cliente);
        turno.setProfesional(profesional);
        turno.setServicio(servicio);
        turno.setFecha(rs.getString("fecha"));
        turno.setHora(rs.getString("hora"));
        turno.setEstado(rs.getString("estado"));

        return turno;
    }
    
    public boolean cambiarEstado(int idTurno, String nuevoEstado) {

        String sql = "UPDATE turno SET estado = ? WHERE id_turno = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, idTurno);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al cambiar estado: " + e.getMessage());
            return false;
        }
    }
    
    
   // Modificar turno
    
    public boolean modificarTurno(int idTurno, String nuevaFecha, String nuevaHora,
                                   int idServicio, int idProfesional) {

        String sql = "UPDATE turno SET fecha=?, hora=?, id_servicio=?, id_profesional=? "
                   + "WHERE id_turno=?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevaFecha);
            ps.setString(2, nuevaHora);
            ps.setInt(3, idServicio);
            ps.setInt(4, idProfesional);
            ps.setInt(5, idTurno);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al modificar turno: " + e.getMessage());
            return false;
        }
    }
    
    //Obtiene el ultimo id de reserva
    public int obtenerUltimoIdTurno(int idCliente) {

        String sql = "SELECT id_turno FROM turno WHERE id_cliente = ? "
                   + "ORDER BY id_turno DESC LIMIT 1";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id_turno");

        } catch (SQLException e) {
            System.out.println("Error al obtener id turno: " + e.getMessage());
        }

        return -1;
    }
}