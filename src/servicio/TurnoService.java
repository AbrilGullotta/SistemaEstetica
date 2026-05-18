package servicio;

import modelo.Turno;
import repository.TurnoRepository;
import java.util.ArrayList;

public class TurnoService {

    private TurnoRepository turnoRepository = new TurnoRepository();

    public void registrarTurno(Turno turno) {

        turnoRepository.guardarTurno(turno);
    }
    public ArrayList<Turno> listarTurnos() {
        return turnoRepository.listarTurnos();
    }
}