package interfaz;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import modelo.Turno;
import servicio.TurnoService;
import servicio.UsuarioService;
import servicio.UsuarioService;
import servicio.ServicioService;

public class MenuAdministrador {
	
	TurnoService turnoService = new TurnoService();

    public void mostrarMenuAdministrador() {
        int opcion;

        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(
                    "MENU ADMINISTRADOR\n"
                    + "1. Registrar cliente\n"
                    + "2. Registrar profesional\n"
                    + "3. Registrar servicio\n"
                    + "4. Gestionar turnos\n"
                    + "5. Registrar seña\n"
                    + "6. Ver clientes\n"
                    + "7. Ver profesionales\n"
                    + "8. Ver servicios\n"
                    + "10. Buscar cliente\n"
                    + "9. Ver turnos\n"
                    + "0. Volver"
            ));

            switch (opcion) {
            case 1:
                String nombre    = JOptionPane.showInputDialog("Nombre:");
                String apellido  = JOptionPane.showInputDialog("Apellido:");
                String dni       = JOptionPane.showInputDialog("DNI:");
                String email     = JOptionPane.showInputDialog("Email:");
                String telefono  = JOptionPane.showInputDialog("Teléfono:");
                String pass      = JOptionPane.showInputDialog("Contraseña:");
                String fnac      = JOptionPane.showInputDialog("Fecha de nacimiento (AAAA-MM-DD):");

                UsuarioService usuarioService = new UsuarioService();
                String resultado = usuarioService.registrarCliente(
                        nombre, apellido, dni, email, telefono, pass, fnac
                );

                if ("OK".equals(resultado)) {
                    JOptionPane.showMessageDialog(null, "Cliente registrado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, resultado);
                }
                break;
                
            case 2:
                String nomProf   = JOptionPane.showInputDialog("Nombre:");
                String apProf    = JOptionPane.showInputDialog("Apellido:");
                String dniProf   = JOptionPane.showInputDialog("DNI:");
                String emailProf = JOptionPane.showInputDialog("Email:");
                String telProf   = JOptionPane.showInputDialog("Teléfono:");
                String passProf  = JOptionPane.showInputDialog("Contraseña:");
                String espec     = JOptionPane.showInputDialog("Especialidad:");

                UsuarioService us = new UsuarioService();
                String resProf = us.registrarProfesional(
                        nomProf, apProf, dniProf, emailProf, telProf, passProf, espec
                );

                if ("OK".equals(resProf)) {
                    JOptionPane.showMessageDialog(null, "Profesional registrado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, resProf);
                }
                break;

            case 3:
                String nomServ  = JOptionPane.showInputDialog("Nombre del servicio:");
                String precio   = JOptionPane.showInputDialog("Precio:");
                String duracion = JOptionPane.showInputDialog("Duración (HH:MM:SS):");

                ServicioService servicioService = new ServicioService();
                String resServ = servicioService.registrarServicio(nomServ, precio, duracion);

                if ("OK".equals(resServ)) {
                    JOptionPane.showMessageDialog(null, "Servicio registrado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, resServ);
                }
                break;
                
            case 4:
                ArrayList<Turno> todosLosTurnos = turnoService.listarTurnos();

                if (todosLosTurnos.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No hay turnos registrados.");
                    break;
                }

                StringBuilder sbGestion = new StringBuilder("GESTIONAR TURNOS\n\n");
                for (int i = 0; i < todosLosTurnos.size(); i++) {
                    Turno t = todosLosTurnos.get(i);
                    sbGestion.append((i + 1) + ". " + t.getFecha() + " " + t.getHora()
                            + " | " + t.getCliente().getNombre() + " " + t.getCliente().getApellido()
                            + " | " + t.getServicio().getNombre()
                            + " | Estado: " + t.getEstado() + "\n");
                }
                sbGestion.append("\nIngresá el número del turno a gestionar (0 para cancelar):");

                int numTurno = Integer.parseInt(JOptionPane.showInputDialog(sbGestion.toString()));
                if (numTurno == 0 || numTurno > todosLosTurnos.size()) break;

                Turno turnoElegido = todosLosTurnos.get(numTurno - 1);

                String accion = JOptionPane.showInputDialog(
                        "Turno seleccionado:\n"
                        + "Cliente: " + turnoElegido.getCliente().getNombre() + " " + turnoElegido.getCliente().getApellido() + "\n"
                        + "Fecha: " + turnoElegido.getFecha() + " " + turnoElegido.getHora() + "\n"
                        + "Estado actual: " + turnoElegido.getEstado() + "\n\n"
                        + "Nuevo estado:\n"
                        + "1. CONFIRMADO\n"
                        + "2. CANCELADO\n"
                        + "3. COMPLETADO\n"
                        + "0. Volver"
                );

                String nuevoEstado = null;
                switch (accion) {
                    case "1": nuevoEstado = "CONFIRMADO"; break;
                    case "2": nuevoEstado = "CANCELADO"; break;
                    case "3": nuevoEstado = "COMPLETADO"; break;
                    default: break;
                }

                if (nuevoEstado != null) {
                    String resEstado = turnoService.cambiarEstado(turnoElegido.getIdTurno(), nuevoEstado);
                    if ("OK".equals(resEstado)) {
                        JOptionPane.showMessageDialog(null, "Estado actualizado a: " + nuevoEstado);
                    } else {
                        JOptionPane.showMessageDialog(null, resEstado);
                    }
                }
                break;
             
            case 5:
                ArrayList<Turno> turnosReservados = turnoService.listarTurnos();
                ArrayList<Turno> aptosSenia = new ArrayList<>();

                for (Turno t : turnosReservados) {
                    if (t.getEstado().equals("RESERVADO")) {
                        aptosSenia.add(t);
                    }
                }

                if (aptosSenia.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No hay turnos RESERVADOS para registrar seña.");
                    break;
                }

                StringBuilder sbSenia = new StringBuilder("Seleccioná el turno para registrar la seña:\n\n");
                for (int i = 0; i < aptosSenia.size(); i++) {
                    Turno t = aptosSenia.get(i);
                    sbSenia.append((i + 1) + ". " + t.getFecha() + " " + t.getHora()
                            + " | " + t.getCliente().getNombre() + " " + t.getCliente().getApellido()
                            + " | " + t.getServicio().getNombre()
                            + " | $" + t.getServicio().getPrecio() + "\n");
                }
                sbSenia.append("\nIngresá el número (0 para cancelar):");

                int numSenia = Integer.parseInt(JOptionPane.showInputDialog(sbSenia.toString()));
                if (numSenia == 0 || numSenia > aptosSenia.size()) break;

                Turno turnoParaSenia = aptosSenia.get(numSenia - 1);
                servicio.SeniaService seniaServiceAdmin = new servicio.SeniaService();
                double montoAdmin = seniaServiceAdmin.calcularMonto(turnoParaSenia.getServicio().getPrecio());

                int confirmaSenia = JOptionPane.showConfirmDialog(null,
                        "Registrar seña para:\n"
                        + turnoParaSenia.getCliente().getNombre() + " " + turnoParaSenia.getCliente().getApellido() + "\n"
                        + turnoParaSenia.getFecha() + " " + turnoParaSenia.getHora()
                        + " - " + turnoParaSenia.getServicio().getNombre() + "\n\n"
                        + "Monto (30%): $" + String.format("%.2f", montoAdmin) + "\n\n"
                        + "¿Confirmás el registro?",
                        "Registrar seña",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmaSenia == JOptionPane.YES_OPTION) {
                    String resSenia = seniaServiceAdmin.registrarSenia(turnoParaSenia);
                    if (resSenia.startsWith("OK")) {
                        double monto = Double.parseDouble(resSenia.split(":")[1]);
                        JOptionPane.showMessageDialog(null,
                                "Seña registrada correctamente.\nMonto: $" + String.format("%.2f", monto));
                    } else {
                        JOptionPane.showMessageDialog(null, resSenia);
                    }
                }
                break;
                
                case 6:
                    UsuarioService usVer = new UsuarioService();
                    ArrayList<modelo.Cliente> clientes = usVer.listarClientes();

                    if (clientes.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No hay clientes registrados.");
                        break;
                    }

                    StringBuilder sbCli = new StringBuilder("CLIENTES REGISTRADOS\n\n");
                    for (modelo.Cliente c : clientes) {
                        sbCli.append("• " + c.getNombre() + " " + c.getApellido() + "\n");
                        sbCli.append("  DNI: " + c.getDni() + "\n");
                        sbCli.append("  Email: " + c.getEmail() + "\n");
                        sbCli.append("  Tel: " + c.getTelefono() + "\n");
                        sbCli.append("-----------------------------\n");
                    }
                    JOptionPane.showMessageDialog(null, sbCli.toString());
                    break;

                case 7:
                    UsuarioService usProf = new UsuarioService();
                    ArrayList<modelo.Profesional> profs = usProf.listarProfesionales();

                    if (profs.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No hay profesionales registrados.");
                        break;
                    }

                    StringBuilder sbProfs = new StringBuilder("PROFESIONALES REGISTRADOS\n\n");
                    for (modelo.Profesional p : profs) {
                        sbProfs.append("• " + p.getNombre() + " " + p.getApellido() + "\n");
                        sbProfs.append("  Especialidad: " + p.getEspecialidad() + "\n");
                        sbProfs.append("  Email: " + p.getEmail() + "\n");
                        sbProfs.append("  Tel: " + p.getTelefono() + "\n");
                        sbProfs.append("-----------------------------\n");
                    }
                    JOptionPane.showMessageDialog(null, sbProfs.toString());
                    break;
                case 8:
                    ServicioService ssVer = new ServicioService();
                    ArrayList<modelo.Servicio> servs = ssVer.listarServicios();

                    if (servs.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No hay servicios registrados.");
                        break;
                    }

                    StringBuilder sbServs = new StringBuilder("SERVICIOS REGISTRADOS\n\n");
                    for (modelo.Servicio s : servs) {
                        sbServs.append("• " + s.getNombre() + "\n");
                        sbServs.append("  Precio: $" + s.getPrecio() + "\n");
                        sbServs.append("  Duración: " + s.getDuracion() + "\n");
                        sbServs.append("-----------------------------\n");
                    }
                    JOptionPane.showMessageDialog(null, sbServs.toString());
                    break;
                case 9:
                    ArrayList<Turno> turnos = turnoService.listarTurnos();

                    if (turnos.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No hay turnos registrados.");
                    } else {
                        String mensaje = "TURNOS REGISTRADOS\n\n";

                        for (Turno turno : turnos) {
                            mensaje += "ID: " + turno.getIdTurno() + "\n";
                            mensaje += "Cliente: " + turno.getCliente().getNombre() + " " + turno.getCliente().getApellido() + "\n";
                            mensaje += "Profesional: " + turno.getProfesional().getNombre() + " " + turno.getProfesional().getApellido() + "\n";
                            mensaje += "Servicio: " + turno.getServicio().getNombre() + "\n";
                            mensaje += "Fecha: " + turno.getFecha() + "\n";
                            mensaje += "Hora: " + turno.getHora() + "\n";
                            mensaje += "Estado: " + turno.getEstado() + "\n";
                            mensaje += "-------------------------\n";
                        }

                        JOptionPane.showMessageDialog(null, mensaje);
                    }
                    break;
                 
                case 10:
                    String criterio = JOptionPane.showInputDialog(
                            "Buscar cliente\nIngresá nombre, apellido o teléfono:");

                    if (criterio == null || criterio.trim().isEmpty()) break;

                    UsuarioService usBuscar = new UsuarioService();
                    ArrayList<modelo.Cliente> clientesEncontrados = usBuscar.buscarClientes(criterio);

                    if (clientesEncontrados.isEmpty()) {
                        JOptionPane.showMessageDialog(null,
                                "No se encontraron clientes con ese criterio.");
                        break;
                    }

                    StringBuilder sbBusq = new StringBuilder(
                            "Resultados para \"" + criterio + "\":\n\n");
                    for (modelo.Cliente c : clientesEncontrados) {
                        sbBusq.append("• " + c.getNombre() + " " + c.getApellido() + "\n");
                        sbBusq.append("  DNI: " + c.getDni() + "\n");
                        sbBusq.append("  Email: " + c.getEmail() + "\n");
                        sbBusq.append("  Tel: " + c.getTelefono() + "\n");
                        sbBusq.append("-----------------------------\n");
                    }

                    JOptionPane.showMessageDialog(null, sbBusq.toString());
                    break;
                    
                case 0:
                    JOptionPane.showMessageDialog(null, "Volviendo al menú principal");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida");
            }

        } while (opcion != 0);
    }
}