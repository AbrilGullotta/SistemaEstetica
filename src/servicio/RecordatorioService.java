package servicio;

import modelo.Recordatorio;
import repository.RecordatorioRepository;

public class RecordatorioService {

    private RecordatorioRepository repo = new RecordatorioRepository();

    // Se llama automáticamente al registrar o confirmar un turno
    public String programarRecordatorio(int idTurno) {

        // Evitar duplicados
        if (repo.existeRecordatorio(idTurno)) {
            return "INFO: Ya existe un recordatorio para este turno.";
        }

        Recordatorio r = new Recordatorio(idTurno);
        boolean ok = repo.guardar(r);

        return ok ? "OK" : "ERROR: No se pudo programar el recordatorio.";
    }

    // Simula el envío: cambia estado a true
    public String enviarRecordatorio(int idTurno) {
        boolean ok = repo.marcarComoEnviado(idTurno);
        if (ok) {
            System.out.println("[RECORDATORIO] Recordatorio enviado para turno #" + idTurno);
            return "OK";
        }
        return "ERROR: No se pudo enviar el recordatorio.";
    }
}