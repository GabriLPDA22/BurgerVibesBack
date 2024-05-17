package Model.DAO;
import Model.Entities.Cliente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteDAO {

    private static final String SQL_FIND_ALL = "SELECT * FROM Cliente WHERE 1=1";

    private Connection getConnection() throws SQLException {
        String url = "jdbc:oracle:thin:@burgervibesbbdd.ceotvomboedr.us-east-1.rds.amazonaws.com:1521:xe";
        String user = "admin"; // Reemplaza con tu usuario de la base de datos
        String password = "123456789"; // Reemplaza con tu contraseña de la base de datos
        return DriverManager.getConnection(url, user, password);
    }

    public ArrayList<Cliente> findAll(Object bean) {
        ArrayList<Cliente> clientes = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String sql = SQL_FIND_ALL;
            if (bean != null) {
                Cliente cliente = (Cliente) bean;
                if (cliente.getID_Cliente() != null && !cliente.getID_Cliente().isEmpty()) {
                    sql += " AND ID_Cliente='" + cliente.getID_Cliente() + "'";
                }
                if (cliente.getNombre() != null && !cliente.getNombre().isEmpty()) {
                    sql += " AND Nombre='" + cliente.getNombre() + "'";
                }
                if (cliente.getDireccion() != null && !cliente.getDireccion().isEmpty()) {
                    sql += " AND Direccion='" + cliente.getDireccion() + "'";
                }
                if (cliente.getEmail() != null && !cliente.getEmail().isEmpty()) {
                    sql += " AND Email='" + cliente.getEmail() + "'";
                }
                if (cliente.getTelefono() != null && !cliente.getTelefono().isEmpty()) {
                    sql += " AND Telefono='" + cliente.getTelefono() + "'";
                }
                if (cliente.getFechaRegistro() != null) {
                    sql += " AND FechaRegistro='" + cliente.getFechaRegistro() + "'";
                }
            }
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setID_Cliente(rs.getString("ID_Cliente"));
                    cliente.setNombre(rs.getString("Nombre"));
                    cliente.setDireccion(rs.getString("Direccion"));
                    cliente.setEmail(rs.getString("Email"));
                    cliente.setTelefono(rs.getString("Telefono"));
                    cliente.setFechaRegistro(rs.getString("FechaRegistro"));
                    clientes.add(cliente);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            clientes.clear();
        }
        return clientes;
    }
}
