package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL =
            "jdbc:mysql://localhost:3306/sistema_estetica";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection instancia;
    
    public static Connection conectar() {
        
    	try {

    		 if (instancia == null || instancia.isClosed()) {
                 instancia = DriverManager.getConnection(URL, USER, PASSWORD);
             }
         } catch (SQLException e) {
             System.out.println("Error de conexión: " + e.getMessage());
         }
         return instancia;
     }
 }