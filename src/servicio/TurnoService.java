package servicio;

import modelo.Turno;
import repository.TurnoRepository;
import java.util.ArrayList;

public class TurnoService {

    private TurnoRepository turnoRepository = new TurnoRepository();

    public void registrarTurno(Turno turno) {

        boolean ocupado = turnoRepository.existeTurnoReservado(
                turno.getProfesional().getIdUsuario(),
                turno.getFecha(),
                turno.getHora()
        );

        if (ocupado) {
            System.out.println("No se puede registrar el turno: el profesional ya tiene un turno reservado en ese horario.");
        } else {
            turnoRepository.guardarTurno(turno);
        }
    }
}