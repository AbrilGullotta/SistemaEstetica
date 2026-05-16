package modelo;

public class Usuario {

    protected int idUsuario;
    protected String nombre;
    protected String apellido;
    protected String email;
    protected String contrasenia;
    protected String rol;
    protected String dni;
    protected String telefono;
    protected String fechaNacimiento;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nombre, String apellido,
            String email, String contrasenia, String rol,
            String dni, String telefono, String fechaNacimiento) {

 this.idUsuario = idUsuario;
 this.nombre = nombre;
 this.apellido = apellido;
 this.email = email;
 this.contrasenia = contrasenia;
 this.rol = rol;
 this.dni = dni;
 this.telefono = telefono;
 this.fechaNacimiento = fechaNacimiento;
}

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
        
    }
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        
    }
}