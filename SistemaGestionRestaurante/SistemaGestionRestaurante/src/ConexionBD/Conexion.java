package ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL ="jdbc:mysql://localhost:3306/restaurante";
    private static final String USER = "root";
    private static final String PASSWORD = "Qaz200570#";

    public static Connection conectar() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa.");
        } catch (SQLException e) {
            System.out.println( "Error de conexión: " + e.getMessage());
        }
        return con;
    }
}