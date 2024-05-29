package Model.DAO;

import Model.Entities.Cliente;
import Model.MotorOracle.MotorOracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteDAO implements IDao<Cliente, String> {
    private final String SQL_FIND_ALL = "SELECT * FROM CLIENTE WHERE 1=1 ";
    private final String SQL_LOGIN = "SELECT * FROM CLIENTE WHERE (EMAIL = ? OR NOMBRE_USUARIO = ?) AND CONTRASEÑA = ?";
    private final String SQL_REGISTER = "INSERT INTO CLIENTE (ID_CLIENTE, NOMBRE, DIRECCION, EMAIL, TELEFONO, FECHAREGISTRO, NOMBRE_USUARIO, CONTRASEÑA) VALUES (?, ?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?)";
    private final String SQL_FIND_BY_ID = "SELECT * FROM CLIENTE WHERE ID_CLIENTE = ?";
    private final String SQL_FIND_BY_EMAIL = "SELECT * FROM CLIENTE WHERE EMAIL = ?";
    private final String SQL_FIND_BY_EMAIL_AND_PASSWORD = "SELECT * FROM CLIENTE WHERE EMAIL = ? AND CONTRASENA = ?";
    private final String SQL_AUTHENTICATE = "SELECT * FROM CLIENTE WHERE EMAIL = ? AND CONTRASENA = ?";

    @Override
    public int add(Cliente bean) {
        MotorOracle motor = new MotorOracle();
        try {
            motor.connect();
            PreparedStatement ps = motor.preparedStatement(SQL_REGISTER);
            ps.setString(1, bean.getID_Cliente());
            ps.setString(2, bean.getNombre());
            ps.setString(3, bean.getDireccion());
            ps.setString(4, bean.getEmail());
            ps.setString(5, bean.getTelefono());
            ps.setString(6, bean.getFechaRegistro());
            ps.setString(7, bean.getNombreUsuario());
            ps.setString(8, bean.getContraseña());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            try {
                motor.disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Cliente authenticate(String email, String password) {
        Cliente cliente = null;
        MotorOracle motor = new MotorOracle();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            motor.connect();
            connection = motor.getConnection();
            pstmt = connection.prepareStatement(SQL_AUTHENTICATE);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setID_Cliente(rs.getString("ID_CLIENTE"));
                cliente.setNombre(rs.getString("NOMBRE"));
                cliente.setDireccion(rs.getString("DIRECCION"));
                cliente.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
                cliente.setEmail(rs.getString("EMAIL"));
                cliente.setTelefono(rs.getString("TELEFONO"));
                cliente.setFechaRegistro(rs.getString("FECHA_REGISTRO"));
            }
        } catch (SQLException e) {
            System.err.println("Error al autenticar el cliente: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                motor.disconnect();
            } catch (SQLException e) {
                System.err.println("Error al cerrar los recursos: " + e.getMessage());
            }
        }

        return cliente;
    }

    public Cliente login(String emailOrUsername, String password) {
        Cliente cliente = null;
        MotorOracle motor = new MotorOracle();
        try {
            motor.connect();
            PreparedStatement ps = motor.preparedStatement(SQL_LOGIN);
            ps.setString(1, emailOrUsername);
            ps.setString(2, emailOrUsername);
            ps.setString(3, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cliente = new Cliente();
                cliente.setID_Cliente(rs.getString("ID_CLIENTE"));
                cliente.setNombre(rs.getString("NOMBRE"));
                cliente.setDireccion(rs.getString("DIRECCION"));
                cliente.setEmail(rs.getString("EMAIL"));
                cliente.setTelefono(rs.getString("TELEFONO"));
                cliente.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
                cliente.setFechaRegistro(rs.getDate("FECHAREGISTRO").toString());
                cliente.setContraseña(rs.getString("CONTRASEÑA"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                motor.disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cliente;
    }
    public Cliente findByEmailAndPassword(String email, String password) {
        Cliente cliente = null;
        MotorOracle motor = new MotorOracle();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            motor.connect();
            connection = motor.getConnection();
            ps = connection.prepareStatement(SQL_FIND_BY_EMAIL_AND_PASSWORD);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setID_Cliente(rs.getString("ID_CLIENTE"));
                cliente.setNombre(rs.getString("NOMBRE"));
                cliente.setDireccion(rs.getString("DIRECCION"));
                cliente.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
                cliente.setEmail(rs.getString("EMAIL"));
                cliente.setTelefono(rs.getString("TELEFONO"));
                cliente.setFechaRegistro(String.valueOf(rs.getDate("FECHA_REGISTRO")));
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar cliente: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                motor.disconnect();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return cliente;
    }
    @Override
    public int delete(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int update(Cliente bean) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Cliente> findAll(Cliente bean) {
        ArrayList<Cliente> clientes = new ArrayList<>();
        MotorOracle motor = new MotorOracle();
        try {
            motor.connect();
            String sql = SQL_FIND_ALL;
            if (bean != null) {
                if (bean.getID_Cliente() != null && !bean.getID_Cliente().isEmpty()) {
                    sql += " AND ID_CLIENTE='" + bean.getID_Cliente() + "'";
                }
                if (bean.getNombre() != null) {
                    sql += " AND NOMBRE='" + bean.getNombre() + "'";
                }
                if (bean.getDireccion() != null) {
                    sql += " AND DIRECCION='" + bean.getDireccion() + "'";
                }
                if (bean.getEmail() != null) {
                    sql += " AND EMAIL='" + bean.getEmail() + "'";
                }
                if (bean.getNombreUsuario() != null) {
                    sql += " AND NOMBRE_USUARIO='" + bean.getNombreUsuario() + "'";
                }
                if (bean.getContraseña() != null) {
                    sql += " AND CONTRASEÑA='" + bean.getContraseña() + "'";
                }
                if (bean.getTelefono() != null) {
                    sql += " AND TELEFONO='" + bean.getTelefono() + "'";
                }
                if (bean.getFechaRegistro() != null) {
                    sql += " AND FECHAREGISTRO=TO_DATE('" + bean.getFechaRegistro() + "', 'YYYY-MM-DD')";
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
                    cliente.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
                    cliente.setFechaRegistro(rs.getDate("FECHAREGISTRO").toString());
                    cliente.setContraseña(rs.getString("CONTRASEÑA"));

                    clientes.add(cliente);
                } catch (SQLException e) {
                    System.err.println("Error al convertir los datos del cliente: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            System.out.println("Número de clientes encontrados: " + clientes.size());

        } catch (Exception ex) {
            clientes.clear();
            System.err.println("Error en findAll: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                motor.disconnect();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return clientes;
    }
    public Cliente findByEmail(String email) {
        Cliente cliente = null;
        MotorOracle motor = new MotorOracle();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            motor.connect();
            connection = motor.getConnection();
            ps = ((Connection) connection).prepareStatement(SQL_FIND_BY_EMAIL);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setID_Cliente(rs.getString("ID_CLIENTE"));
                cliente.setNombre(rs.getString("NOMBRE"));
                cliente.setDireccion(rs.getString("DIRECCION"));
                cliente.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
                cliente.setEmail(rs.getString("EMAIL"));
                cliente.setTelefono(rs.getString("TELEFONO"));
                cliente.setFechaRegistro(String.valueOf(rs.getDate("FECHA_REGISTRO")));
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar cliente: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                motor.disconnect();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return cliente;
    }
   
}
