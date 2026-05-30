package modelo;

import java.time.LocalDate;

public class Recordatorio {

    private int idRecordatorio;
    private int idTurno;
    private LocalDate fechaEnvio;
    private String medio;
    private boolean estado; // false = pendiente, true = enviado

    public Recordatorio() {}

    public Recordatorio(int idTurno) {
        this.idTurno    = idTurno;
        this.fechaEnvio = LocalDate.now();
        this.medio      = "EMAIL";
        this.estado     = false;
    }

    public int getIdRecordatorio() { return idRecordatorio; }
    public void setIdRecordatorio(int idRecordatorio) { this.idRecordatorio = idRecordatorio; }

    public int getIdTurno() { return idTurno; }
    public void setIdTurno(int idTurno) { this.idTurno = idTurno; }

    public LocalDate getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDate fechaEnvio) { this.fechaEnvio = fechaEnvio; }

    public String getMedio() { return medio; }
    public void setMedio(String medio) { this.medio = medio; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }
}