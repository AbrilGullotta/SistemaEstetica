package modelo;

public class Cliente extends Usuario {

    public Cliente() {
        this.rol = "CLIENTE";
    }
    
    @Override
    public String toString() {
        return nombre + " " + apellido;
    }
}