package servicio;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import modelo.Cliente;
import modelo.Disponibilidad;
import modelo.Profesional;
import modelo.Servicio;
import modelo.Turno;
import repository.DisponibilidadRepository;
import repository.TurnoRepository;

public class TurnoService {

    private TurnoRepository turnoRepo = new TurnoRepository();
    private DisponibilidadRepository dispRepo = new DisponibilidadRepository();
    private RecordatorioService recordatorioService = new RecordatorioService();
    
    public void registrarTurno(Turno turno) {
        boolean ocupado = turnoRepo.existeTurnoReservado(
                turno.getProfesional().getIdUsuario(),
                turno.getFecha(),
                turno.getHora()
        );

        if (ocupado) {
            System.out.println("El profesional ya tiene un turno en ese horario.");
        } else {
            turnoRepo.guardarTurno(turno);
        }
    }

    public ArrayList<Turno> listarTurnos() {
        return turnoRepo.listarTurnos();
    }

    public ArrayList<Turno> listarTurnosPorCliente(int idCliente) {
        return turnoRepo.listarTurnosPorCliente(idCliente);
    }

    public ArrayList<Turno> listarTurnosPorProfesional(int idProfesional) {
        return turnoRepo.listarTurnosPorProfesional(idProfesional);
    }

   
      //Genera los slots horarios disponibles para un profesional en un día,
      //según la duración del servicio, descartando los ya reservados.
    
    public ArrayList<String> obtenerHorariosDisponibles(int idProfesional,
                                                         String dia,
                                                         String duracionServicio) {
        ArrayList<String> horariosLibres = new ArrayList<>();

        ArrayList<Disponibilidad> disponibilidades = dispRepo.listarPorProfesional(idProfesional);

        // Duración del servicio en minutos (formato HH:MM:SS)
        int duracionMinutos = parsearDuracion(duracionServicio);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");

        for (Disponibilidad d : disponibilidades) {
            if (!d.getDia().equals(dia)) continue;

            LocalTime cursor  = LocalTime.parse(d.getHoraInicio(), fmt);
            LocalTime horaFin = LocalTime.parse(d.getHoraFin(), fmt);

            while (!cursor.plusMinutes(duracionMinutos).isAfter(horaFin)) {
                String horaStr = cursor.format(fmt);

                boolean ocupado = turnoRepo.existeTurnoReservado(idProfesional, dia, horaStr);
                if (!ocupado) {
                    horariosLibres.add(horaStr);
                }

                cursor = cursor.plusMinutes(duracionMinutos);
            }
        }

        return horariosLibres;
    }
    
     //Reserva un turno completo dado cliente, profesional, servicio, fecha y hora.
    
    public String reservarTurno(Cliente cliente, Profesional profesional,
                                 Servicio servicio, String fecha, String hora) {

        boolean ocupado = turnoRepo.existeTurnoReservado(
                profesional.getIdUsuario(), fecha, hora
        );

        if (ocupado) {
            return "ERROR: Ese horario ya fue reservado.";
        }

        Turno turno = new Turno();
        turno.setCliente(cliente);
        turno.setProfesional(profesional);
        turno.setServicio(servicio);
        turno.setFecha(fecha);
        turno.setHora(hora);
        turno.setEstado("RESERVADO");

        boolean ok = turnoRepo.guardarTurno(turno);
        if (ok) {
            int idTurno = turnoRepo.obtenerUltimoIdTurno(cliente.getIdUsuario());
            recordatorioService.programarRecordatorio(idTurno);
        }
        return ok ? "OK" : "ERROR: No se pudo guardar el turno.";
    }

    private int parsearDuracion(String duracion) {
       
    	// Formato esperado: HH:MM:SS
        String[] partes = duracion.split(":");
        int horas   = Integer.parseInt(partes[0]);
        int minutos = Integer.parseInt(partes[1]);
        return horas * 60 + minutos;
    }
    public String cambiarEstado(int idTurno, String nuevoEstado) {
        boolean ok = turnoRepo.cambiarEstado(idTurno, nuevoEstado);
        return ok ? "OK" : "ERROR: No se pudo actualizar el estado.";
    }
    
    public String cancelarTurno(int idTurno) {
        // Solo se puede cancelar si está reservado o cpnfirmado 
    	
        return turnoRepo.cambiarEstado(idTurno, "CANCELADO") ? "OK" : "ERROR: No se pudo cancelar el turno.";
    }
    
 // Modificar turno
    public String modificarTurno(int idTurno, String nuevaFecha, String nuevaHora,
                                  int idProfesional, int idServicio) {

        // Verificar que el nuevo horario no esté ocupado por otro turno
        boolean ocupado = turnoRepo.existeTurnoOcupadoExcluyendo(idProfesional, nuevaFecha, nuevaHora, idTurno);
        if (ocupado) {
            return "ERROR: El profesional ya tiene un turno en ese horario.";
        }

        boolean ok = turnoRepo.modificarTurno(idTurno, nuevaFecha, nuevaHora, idServicio, idProfesional);
        return ok ? "OK" : "ERROR: No se pudo modificar el turno.";
    }
    
}