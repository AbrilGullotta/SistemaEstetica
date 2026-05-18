package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import conexion.Conexion;
import modelo.Turno;
import java.sql.ResultSet;
import java.util.ArrayList;

import modelo.Cliente;
import modelo.Profesional;
import modelo.Servicio;

public class TurnoRepository {

    public void guardarTurno(Turno turno) {

        String sql = "INSERT INTO turno "
                + "(id_cliente, id_profesional, id_servicio, fecha, hora, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = Conexion.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, turno.getCliente().getIdUsuario());
            ps.setInt(2, turno.getProfesional().getIdUsuario());
            ps.setInt(3, turno.getServicio().getIdServicio());
            ps.setString(4, turno.getFecha());
            ps.setString(5, turno.getHora());
            ps.setString(6, turno.getEstado());

            ps.executeUpdate();

            System.out.println("Turno guardado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al guardar turno: " + e.getMessage());
        }
    }
    public ArrayList<Turno> listarTurnos() {

        ArrayList<Turno> turnos = new ArrayList<>();

        String sql = "SELECT t.id_turno, t.fecha, t.hora, t.estado, "
                + "c.id_cliente, c.nombre AS nombre_cliente, c.apellido AS apellido_cliente, "
                + "p.id_profesional, p.nombre AS nombre_profesional, p.apellido AS apellido_profesional, "
                + "s.id_servicio, s.nombre AS nombre_servicio "
                + "FROM turno t "
                + "INNER JOIN cliente c ON t.id_cliente = c.id_cliente "
                + "INNER JOIN profesional p ON t.id_profesional = p.id_profesional "
                + "INNER JOIN servicio s ON t.id_servicio = s.id_servicio";

        try {
            Connection conn = Conexion.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Cliente cliente = new Cliente();
                cliente.setIdUsuario(rs.getInt("id_cliente"));
                cliente.setNombre(rs.getString("nombre_cliente"));
                cliente.setApellido(rs.getString("apellido_cliente"));

                Profesional profesional = new Profesional();
                profesional.setIdUsuario(rs.getInt("id_profesional"));
                profesional.setNombre(rs.getString("nombre_profesional"));
                profesional.setApellido(rs.getString("apellido_profesional"));

                Servicio servicio = new Servicio();
                servicio.setIdServicio(rs.getInt("id_servicio"));
                servicio.setNombre(rs.getString("nombre_servicio"));

                Turno turno = new Turno();
                turno.setIdTurno(rs.getInt("id_turno"));
                turno.setCliente(cliente);
                turno.setProfesional(profesional);
                turno.setServicio(servicio);
                turno.setFecha(rs.getString("fecha"));
                turno.setHora(rs.getString("hora"));
                turno.setEstado(rs.getString("estado"));

                turnos.add(turno);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar turnos: " + e.getMessage());
        }

        return turnos;
    }
}