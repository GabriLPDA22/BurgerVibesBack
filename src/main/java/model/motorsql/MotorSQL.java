package model.motorsql;

/*
 * Motor genérico para la conexión con bases de datos SQL
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class MotorSQL {
    // Objetos necesarios para hablar con la BD
    private Connection conn = null;
    private Statement st = null;
    private ResultSet rs = null;
    private PreparedStatement ps = null;

    private static final String URL = "jdbc:oracle:thin:@burgervibesbbdd.ceotvomboedr.us-east-1.rds.amazonaws.com:1521:orcl";
    private static final String USER = "admin";
    private static final String PASSWORD = "123456789";

    // Conectar a la base de datos
    public void connect() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            st = conn.createStatement();
        } catch (SQLException ex) {
            // Manejar errores
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
        } catch (Exception ex) {
            System.err.println("Exception: " + ex.getMessage());
        }
    }

    // Ejecutar consulta
    public ResultSet executeQuery(String sql) {
        try {
            rs = st.executeQuery(sql);
        } catch (SQLException ex) {
            // Manejar errores
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
        }
        return rs;
    }

    // Ejecutar actualización
    public int executeUpdate(String sql) {
        int iResults = 0;
        try {
            iResults = st.executeUpdate(sql);
        } catch (SQLException ex) {
            // Manejar errores
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
        }
        return iResults;
    }

    // Preparar declaración
    public void prepareStatement(String sql) throws SQLException {
        ps = conn.prepareStatement(sql);
    }

    // Establecer parámetros de declaración preparada
    public void setString(int index, String value) throws SQLException {
        if (ps != null) {
            ps.setString(index, value);
        }
    }

    public void setInt(int index, int value) throws SQLException {
        if (ps != null) {
            ps.setInt(index, value);
        }
    }

    // Ejecutar declaración preparada
    public int executeUpdate() throws SQLException {
        if (ps != null) {
            return ps.executeUpdate();
        }
        return 0;
    }

    // Desconectar de la base de datos
    public void disconnect() {
        close(rs);
        close(st);
        close(ps);
        close(conn);
    }

    // Método auxiliar para cerrar recursos
    private void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception e) {
                System.err.println("Failed to close resource: " + e.getMessage());
            }
        }
    }
}
