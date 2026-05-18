package test;

import java.util.ArrayList;

import modelo.Turno;
import servicio.TurnoService;

public class TurnoTest {

    public static void main(String[] args) {

        TurnoService turnoService = new TurnoService();

        ArrayList<Turno> turnos = turnoService.listarTurnos();

        for (Turno turno : turnos) {
            System.out.println("-------------------------");
            System.out.println("Turno ID: " + turno.getIdTurno());
            System.out.println("Cliente: " + turno.getCliente().getNombre() + " " + turno.getCliente().getApellido());
            System.out.println("Profesional: " + turno.getProfesional().getNombre() + " " + turno.getProfesional().getApellido());
            System.out.println("Servicio: " + turno.getServicio().getNombre());
            System.out.println("Fecha: " + turno.getFecha());
            System.out.println("Hora: " + turno.getHora());
            System.out.println("Estado: " + turno.getEstado());
        }
    }
}