package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL =
            "jdbc:mysql://localhost:3306/sistema_estetica";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection conectar() {

        Connection conexion = null;

        try {

            conexion = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Conexión exitosa");

        } catch (SQLException e) {

            System.out.println("Error de conexión");
            e.printStackTrace();

        }

        return conexion;
    }
}