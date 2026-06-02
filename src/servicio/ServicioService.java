package servicio;

import java.util.ArrayList;
import modelo.Servicio;
import repository.ServicioRepository;
import util.Validador;

public class ServicioService {

    private ServicioRepository repo = new ServicioRepository();

    public String registrarServicio(String nombre, String precioStr, String duracion) {

    	if (!Validador.esValido(nombre) || !Validador.esValido(precioStr) || !Validador.esValido(duracion)) {
    	    return "ERROR: Todos los campos son obligatorios.";
    	}

        double precio;
        try {
            precio = Double.parseDouble(precioStr.replace(",", "."));
        } catch (NumberFormatException e) {
            return "ERROR: El precio debe ser un número válido.";
        }

        if (precio <= 0) {
            return "ERROR: El precio debe ser mayor a cero.";
        }

        Servicio s = new Servicio();
        s.setNombre(nombre.trim());
        s.setPrecio(precio);
        s.setDuracion(duracion.trim());

        boolean ok = repo.guardar(s);
        return ok ? "OK" : "ERROR: No se pudo registrar el servicio.";
    }

    public ArrayList<Servicio> listarServicios() {
        return repo.listar();
    }
    
 // Modificar servicios
    public String modificarServicio(Servicio servicio) {

        if (servicio.getNombre().trim().isEmpty()) {
            return "ERROR: El nombre no puede estar vacío.";
        }
        if (servicio.getPrecio() <= 0) {
            return "ERROR: El precio debe ser mayor a cero.";
        }

        boolean ok = repo.modificar(servicio);
        return ok ? "OK" : "ERROR: No se pudo modificar el servicio.";
    }
}