package servicio;

import java.time.LocalDate;

import modelo.Senia;
import modelo.Turno;
import repository.SeniaRepository;

public class SeniaService {

    private SeniaRepository seniaRepo = new SeniaRepository();
    private TurnoService turnoService = new TurnoService();

    private static final double PORCENTAJE_SENIA = 0.30; // 30%

    public double calcularMonto(double precioServicio) {
        return precioServicio * PORCENTAJE_SENIA;
    }

    public String registrarSenia(Turno turno) {

        // Verificar que no tenga seña ya registrada
        if (seniaRepo.existeSeniaPorTurno(turno.getIdTurno())) {
            return "ERROR: Este turno ya tiene una seña registrada.";
        }

        // Verificar que el turno esté reservado
        if (!turno.getEstado().equals("RESERVADO")) {
            return "ERROR: Solo se puede señar un turno con estado RESERVADO.";
        }

        double monto = calcularMonto(turno.getServicio().getPrecio());

        Senia senia = new Senia();
        senia.setMonto(monto);
        senia.setFechaPago(LocalDate.now().toString());
        senia.setEstadoPago(true);
        senia.setIdTurno(turno.getIdTurno());

        boolean okSenia = seniaRepo.guardar(senia);
        if (!okSenia) return "ERROR: No se pudo registrar la seña.";

        // Cambiar estado del turno a confirmado 
        String okTurno = turnoService.cambiarEstado(turno.getIdTurno(), "CONFIRMADO");
        if (!"OK".equals(okTurno)) return "ERROR: Seña registrada pero no se pudo confirmar el turno.";

        return "OK:" + monto;
    }
}