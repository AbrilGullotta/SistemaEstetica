package modelo;

public class Profesional extends Usuario {

    public Profesional() {
        this.rol = "PROFESIONAL";
    }
    @Override
    public String toString() {
        return nombre + " " + apellido + " - " + especialidad;
    }
    
    
}