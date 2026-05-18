package test;

import modelo.Cliente;
import modelo.Profesional;
import modelo.Servicio;
import modelo.Turno;
import repository.TurnoRepository;
import servicio.TurnoService;

public class TurnoTest {

    public static void main(String[] args) {

    	Cliente cliente = new Cliente();
    	cliente.setIdUsuario(1);

    	Profesional profesional = new Profesional();
    	profesional.setIdUsuario(1);

    	Servicio servicio = new Servicio();
    	servicio.setIdServicio(1);

    	Turno turno = new Turno();
    	turno.setCliente(cliente);
    	turno.setProfesional(profesional);
    	turno.setServicio(servicio);
    	turno.setFecha("2026-05-18");
    	turno.setHora("15:30:00");
    	turno.setEstado("RESERVADO");

    	TurnoService turnoService = new TurnoService();

    	turnoService.registrarTurno(turno);
    }
}