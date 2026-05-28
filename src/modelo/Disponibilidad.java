package modelo;

public class Disponibilidad {

    private int idDisponibilidad;
    private String dia;
    private String horaInicio;
    private String horaFin;
    private int idProfesional;

    public Disponibilidad() {}

    public int getIdDisponibilidad() { return idDisponibilidad; }
    public void setIdDisponibilidad(int idDisponibilidad) { this.idDisponibilidad = idDisponibilidad; }

    public String getDia() { return dia; }
    public void setDia(String dia) { this.dia = dia; }

    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }

    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin; }

    public int getIdProfesional() { return idProfesional; }
    public void setIdProfesional(int idProfesional) { this.idProfesional = idProfesional; }
}