package Model.DAO;

import Model.Entities.Cliente;
import Model.MotorOracle.MotorOracle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteDAO implements IDao {
    private final String SQL_FIND_ALL = "SELECT * FROM CLIENTE WHERE 1=1 ";

    @Override
    public int add(Object bean) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int delete(Integer e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int update(Object bean) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Cliente> findAll(Object bean) {
        ArrayList<Cliente> clientes = new ArrayList<>();
        MotorOracle motor = new MotorOracle();
        try {
            motor.connect();
            String sql = SQL_FIND_ALL;
            if (bean != null) {
                if (((Cliente) bean).getID_Cliente() != null && !((Cliente) bean).getID_Cliente().isEmpty()) {
                    sql += " AND ID_CLIENTE='" + ((Cliente) bean).getID_Cliente() + "'";
                }
                if (((Cliente) bean).getNombre() != null) {
                    sql += " AND NOMBRE='" + ((Cliente) bean).getNombre() + "'";
                }
                if (((Cliente) bean).getDireccion() != null) {
                    sql += " AND DIRECCION='" + ((Cliente) bean).getDireccion() + "'";
                }
                if (((Cliente) bean).getEmail() != null) {
                    sql += " AND EMAIL='" + ((Cliente) bean).getEmail() + "'";
                }
                if (((Cliente) bean).getTelefono() != null) {
                    sql += " AND TELEFONO='" + ((Cliente) bean).getTelefono() + "'";
                }
                if (((Cliente) bean).getFechaRegistro() != null) {
                    sql += " AND FECHAREGISTRO=TO_DATE('" + ((Cliente) bean).getFechaRegistro() + "', 'YYYY-MM-DD')";
                }
            }
            System.out.println("Ejecutando SQL: " + sql);
            ResultSet rs = motor.executeQuery(sql);

            while (rs.next()) {
                Cliente cliente = new Cliente();
                try {
                    cliente.setID_Cliente(rs.getString("ID_CLIENTE"));
                    cliente.setNombre(rs.getString("NOMBRE"));
                    cliente.setDireccion(rs.getString("DIRECCION"));
                    cliente.setEmail(rs.getString("EMAIL"));
                    cliente.setTelefono(rs.getString("TELEFONO"));
                    cliente.setFechaRegistro(rs.getDate("FECHAREGISTRO").toString());

                    clientes.add(cliente);
                } catch (SQLException e) {
                    System.err.println("Error al convertir los datos del cliente: " + e.getMessage());
                    e.printStackTrace(); // Imprimir la traza completa de la excepción
                }
            }
            System.out.println("Número de clientes encontrados: " + clientes.size());

        } catch (Exception ex) {
            clientes.clear();
            System.err.println("Error en findAll: " + ex.getMessage());
            ex.printStackTrace(); // Imprimir la traza completa de la excepción
        } finally {
            motor.disconnect();
        }
        return clientes;
    }
}
