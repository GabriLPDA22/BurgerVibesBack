package Model.DAO;

import Model.Entities.Empleado;
import Model.MotorOracle.MotorOracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmpleadoDAO implements IDao<Empleado, String> {
    private final String SQL_LOGIN = "SELECT EMAIL, ID_ZONAPRIVADA_EMP, CARGO FROM EMPLEADO WHERE EMAIL = ? AND CONTRASENA = ?";
    private final String SQL_FIND_BY_EMAIL = "SELECT NOMBRE, APELLIDOS, CARGO, EMAIL, TELEFONO, DIRECCION FROM EMPLEADO WHERE EMAIL = ?";
    private final String SQL_FIND_ALL = "SELECT * FROM EMPLEADO WHERE 1=1";
    private final String SQL_DELETE = "DELETE FROM EMPLEADO WHERE ID_EMPLEADO = ?";
    private final String SQL_ADD = "INSERT INTO EMPLEADO (ID_EMPLEADO, NOMBRE, APELLIDOS, DIRECCION, CARGO, EMAIL, TELEFONO, ID_ZonaPrivada_EMP, CONTRASENA) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String SQL_UPDATE = "UPDATE EMPLEADO SET ";
    private final String SQL_AUTHENTICATE = "SELECT EMAIL, ID_ZONAPRIVADA_EMP, CARGO FROM EMPLEADO WHERE EMAIL = ? AND CONTRASENA = ?";

    public Empleado login(String email, String password) throws SQLException {
        MotorOracle motor = new MotorOracle();
        Connection connection = null;
        Empleado empleado = null;

        try {
            motor.connect();
            connection = motor.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(SQL_LOGIN);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                empleado = new Empleado();
                empleado.setEmail(rs.getString("EMAIL"));
                empleado.setID_ZonaPrivada_EMP(rs.getString("ID_ZONAPRIVADA_EMP"));
                empleado.setCargo(rs.getString("CARGO"));
            }
        } finally {
            if (connection != null) {
                motor.disconnect();
            }
        }
        return empleado;
    }

    public Empleado authenticate(String email, String password) {
        Empleado empleado = null;
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
                empleado = new Empleado();
                empleado.setEmail(rs.getString("EMAIL"));
                empleado.setID_ZonaPrivada_EMP(rs.getString("ID_ZONAPRIVADA_EMP"));
                empleado.setCargo(rs.getString("CARGO"));
            }
        } catch (SQLException e) {
            System.err.println("Error al autenticar el empleado: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                motor.disconnect();
            } catch (SQLException e) {
                System.err.println("Error al cerrar los recursos: " + e.getMessage());
            }
        }

        return empleado;
    }
    public Empleado findByEmail(String email) {
        Empleado empleado = null;
        MotorOracle motor = new MotorOracle();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            motor.connect();
            String sql = "SELECT NOMBRE, APELLIDOS, CARGO, EMAIL, TELEFONO, DIRECCION FROM EMPLEADO WHERE EMAIL = ?";
            ps = motor.preparedStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                empleado = new Empleado();
                empleado.setNombre(rs.getString("NOMBRE"));
                empleado.setApellidos(rs.getString("APELLIDOS"));
                empleado.setCargo(rs.getString("CARGO"));
                empleado.setEmail(rs.getString("EMAIL"));
                empleado.setTelefono(rs.getString("TELEFONO"));
                empleado.setDireccion(rs.getString("DIRECCION"));
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar empleado por email: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                motor.disconnect();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return empleado;
    }
    public Empleado findByEmailAndPassword(String email, String contrasena) throws SQLException {
        Empleado empleado = null;
        MotorOracle motor = new MotorOracle();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            motor.connect();
            String sql = "SELECT * FROM EMPLEADO WHERE EMAIL = ? AND CONTRASENA = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, contrasena);
            rs = ps.executeQuery();

            if (rs.next()) {
                empleado = new Empleado();
                empleado.setID_Empleado(rs.getString("ID_EMPLEADO"));
                empleado.setNombre(rs.getString("NOMBRE"));
                empleado.setApellidos(rs.getString("APELLIDOS"));
                empleado.setDireccion(rs.getString("DIRECCION"));
                empleado.setCargo(rs.getString("CARGO"));
                empleado.setEmail(rs.getString("EMAIL"));
                empleado.setTelefono(rs.getString("TELEFONO"));
                empleado.setID_ZonaPrivada_EMP(rs.getString("ID_ZONAPRIVADA_EMP"));
                empleado.setContrasena(rs.getString("CONTRASENA"));
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (connection != null) connection.close();
        }

        return empleado;
    }


    @Override
    public int delete(String id) {
        int resp = 0;
        MotorOracle motor = new MotorOracle();
        PreparedStatement ps = null;
        try {
            motor.connect();
            ps = motor.preparedStatement(SQL_DELETE);
            ps.setString(1, id);
            resp = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar el empleado: " + e.getMessage());
            e.printStackTrace();
            resp = -1;
        } finally {
            try {
                if (ps != null) ps.close();
                motor.disconnect();
            } catch (SQLException se) {
                System.out.println("Error al cerrar recursos: " + se.getMessage());
                se.printStackTrace();
            }
        }
        return resp;
    }
    @Override
    public int update(Empleado bean) {
        if (bean == null || bean.getID_Empleado() == null || bean.getID_Empleado().isEmpty()) {
            System.out.println("Error. Se debe proporcionar el ID del empleado y al menos un campo a actualizar.");
            return 0;
        }

        StringBuilder sql = new StringBuilder("UPDATE EMPLEADO SET ");
        ArrayList<Object> params = new ArrayList<>();

        if (bean.getNombre() != null && !bean.getNombre().isEmpty()) {
            sql.append("NOMBRE = ?, ");
            params.add(bean.getNombre());
        }

        if (bean.getApellidos() != null && !bean.getApellidos().isEmpty()) {
            sql.append("APELLIDOS = ?, ");
            params.add(bean.getApellidos());
        }

        if (bean.getDireccion() != null && !bean.getDireccion().isEmpty()) {
            sql.append("DIRECCION = ?, ");
            params.add(bean.getDireccion());
        }

        if (bean.getCargo() != null && !bean.getCargo().isEmpty()) {
            sql.append("CARGO = ?, ");
            params.add(bean.getCargo());
        }

        if (bean.getEmail() != null && !bean.getEmail().isEmpty()) {
            sql.append("EMAIL = ?, ");
            params.add(bean.getEmail());
        }

        if (bean.getTelefono() != null && !bean.getTelefono().isEmpty()) {
            sql.append("TELEFONO = ?, ");
            params.add(bean.getTelefono());
        }

        if (bean.getID_ZonaPrivada_EMP() != null && !bean.getID_ZonaPrivada_EMP().isEmpty()) {
            sql.append("ID_ZonaPrivada_EMP = ?, ");
            params.add(bean.getID_ZonaPrivada_EMP());
        }

        if (bean.getContrasena() != null && !bean.getContrasena().isEmpty()) {
            sql.append("CONTRASENA = ?, ");
            params.add(bean.getContrasena());
        }

        // Remove the last comma and space
        if (params.isEmpty()) {
            System.out.println("No hay campos para actualizar.");
            return 0;
        } else {
            sql.setLength(sql.length() - 2);
        }

        sql.append(" WHERE ID_EMPLEADO = ?");
        params.add(bean.getID_Empleado());

        MotorOracle motor = new MotorOracle();
        PreparedStatement ps = null;
        int resp = 0;

        try {
            motor.connect();
            ps = motor.preparedStatement(sql.toString());

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            resp = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al actualizar empleado: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                motor.disconnect();
            } catch (SQLException se) {
                System.out.println("Error al cerrar recursos: " + se.getMessage());
                se.printStackTrace();
            }
        }

        if (resp > 0) {
            System.out.println("Empleado actualizado con Ã©xito.");
        } else {
            System.out.println("No se pudo actualizar.");
        }
        return resp;
    }

    @Override
    public ArrayList<Empleado> findAll(Empleado bean) {
        ArrayList<Empleado> empleados = new ArrayList<>();
        MotorOracle motor = new MotorOracle();
        try {
            motor.connect();
            String sql = SQL_FIND_ALL;
            if (bean != null) {
                if (bean.getID_Empleado() != null && !bean.getID_Empleado().isEmpty()) {
                    sql += " AND ID_EMPLEADO='" + bean.getID_Empleado() + "'";
                }
                if (bean.getNombre() != null) {
                    sql += " AND NOMBRE='" + bean.getNombre() + "'";
                }
                if (bean.getApellidos() != null) {
                    sql += " AND APELLIDOS='" + bean.getApellidos() + "'";
                }
                if (bean.getDireccion() != null) {
                    sql += " AND DIRECCION='" + bean.getDireccion() + "'";
                }
                if (bean.getCargo() != null) {
                    sql += " AND CARGO='" + bean.getCargo() + "'";
                }
                if (bean.getEmail() != null) {
                    sql += " AND EMAIL='" + bean.getEmail() + "'";
                }
                if (bean.getTelefono() != null) {
                    sql += " AND TELEFONO='" + bean.getTelefono() + "'";
                }
                if (bean.getID_ZonaPrivada_EMP() != null) {
                    sql += " AND ID_ZonaPrivada_EMP='" + bean.getID_ZonaPrivada_EMP() + "'";
                }
                if (bean.getContrasena() != null) {
                    sql += " AND CONTRASENA='" + bean.getContrasena() + "'";
                }
            }
            ResultSet rs = motor.executeQuery(sql);
            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setID_Empleado(rs.getString("ID_EMPLEADO"));
                empleado.setNombre(rs.getString("NOMBRE"));
                empleado.setApellidos(rs.getString("APELLIDOS"));
                empleado.setDireccion(rs.getString("DIRECCION"));
                empleado.setCargo(rs.getString("CARGO"));
                empleado.setEmail(rs.getString("EMAIL"));
                empleado.setTelefono(rs.getString("TELEFONO"));
                empleado.setID_ZonaPrivada_EMP(rs.getString("ID_ZonaPrivada_EMP"));
                empleado.setContrasena(rs.getString("CONTRASENA"));
                empleados.add(empleado);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar empleados: " + e.getMessage());
        } finally {
            try {
                motor.disconnect();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return empleados;

    }

    public int add(Empleado bean) {
        if (bean.getID_Empleado() == null || bean.getID_Empleado().isEmpty() ||
                bean.getNombre() == null || bean.getNombre().isEmpty() ||
                bean.getApellidos() == null || bean.getApellidos().isEmpty() ||
                bean.getDireccion() == null || bean.getDireccion().isEmpty() ||
                bean.getCargo() == null || bean.getCargo().isEmpty() ||
                bean.getEmail() == null || bean.getEmail().isEmpty() ||
                bean.getTelefono() == null || bean.getTelefono().isEmpty() ||
                bean.getID_ZonaPrivada_EMP() == null || bean.getID_ZonaPrivada_EMP().isEmpty() ||
                bean.getContrasena() == null || bean.getContrasena().isEmpty()
        )
        {

            System.out.println("Error. Se deben proporcionar todos los datos del empleado");
            return 0;
        }
        int resp = 0;
        MotorOracle motor = new MotorOracle();
        PreparedStatement ps = null;
        try {
            motor.connect();
            ps = motor.preparedStatement(SQL_ADD);
            ps.setString(1, bean.getID_Empleado());
            ps.setString(2, bean.getNombre());
            ps.setString(3, bean.getApellidos());
            ps.setString(4, bean.getDireccion());
            ps.setString(5, bean.getCargo());
            ps.setString(6, bean.getEmail());
            ps.setString(7, bean.getTelefono());
            ps.setString(8, bean.getID_ZonaPrivada_EMP());
            ps.setString(9, bean.getContrasena());

            resp = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al insertar empleado: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                motor.disconnect();
            } catch (SQLException se) {
                System.out.println("Error al cerrar recursos: " + se.getMessage());
                se.printStackTrace();
            }
        }
        if (resp > 0) {
            System.out.println("Empleado insertado con Ã©xito.");
        }
        return resp;
    }
}
