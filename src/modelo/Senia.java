package modelo;

public class Senia {

    private int idSenia;
    private double monto;
    private String fechaPago;
    private boolean estadoPago;
    private int idTurno;

    public Senia() {}

    public int getIdSenia() { return idSenia; }
    public void setIdSenia(int idSenia) { this.idSenia = idSenia; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public String getFechaPago() { return fechaPago; }
    public void setFechaPago(String fechaPago) { this.fechaPago = fechaPago; }

    public boolean isEstadoPago() { return estadoPago; }
    public void setEstadoPago(boolean estadoPago) { this.estadoPago = estadoPago; }

    public int getIdTurno() { return idTurno; }
    public void setIdTurno(int idTurno) { this.idTurno = idTurno; }
}