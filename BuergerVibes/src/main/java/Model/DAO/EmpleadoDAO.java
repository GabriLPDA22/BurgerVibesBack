package Model.DAO;

import Model.Entities.Empleado;
import Model.MotorOracle.MotorOracle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmpleadoDAO implements IDao {
    private final String SQL_FIND_ALL = "SELECT * FROM EMPLEADO WHERE 1=1 ";
    private final String SQL_DELETE = "DELETE FROM NOMBRE WHERE NOMBRE = ?";

    @Override
    public int add(Object bean) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int delete(Integer bean) {
        return 0;
    }

    @Override
    public String delete(String nombre, String apellidos) {
        int rowsDeleted = 0;
        MotorOracle motor = new MotorOracle();
        try {
            motor.connect();
            rowsDeleted = motor.executeDelete(SQL_DELETE);
            System.out.println("Filas eliminadas: " + rowsDeleted);
        } finally {
            motor.disconnect();
        }
        return String.valueOf(rowsDeleted);
    }



    @Override
    public int update(Object bean) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Empleado> findAll(Object bean) {
        ArrayList<Empleado> empleados = new ArrayList<Empleado>();
        MotorOracle motor = new MotorOracle();
        try {
            motor.connect();
            String sql = SQL_FIND_ALL;
            if (bean != null) {
                if (((Empleado) bean).getID_Empleado() != null && !((Empleado) bean).getID_Empleado().isEmpty()) {
                    sql += " AND ID_EMPLEADO='" + ((Empleado) bean).getID_Empleado() + "'";
                }
                if (((Empleado) bean).getNombre() != null) {
                    sql += " AND NOMBRE='" + ((Empleado) bean).getNombre() + "'";
                }
                if (((Empleado) bean).getApellidos() != null) {
                    sql += " AND APELLIDOS='" + ((Empleado) bean).getApellidos() + "'";
                }
                if (((Empleado) bean).getDireccion() != null) {
                    sql += " AND DIRECCION='" + ((Empleado) bean).getDireccion() + "'";
                }
                if (((Empleado) bean).getCargo() != null) {
                    sql += " AND CARGO='" + ((Empleado) bean).getCargo() + "'";
                }
                if (((Empleado) bean).getEmail() != null) {
                    sql += " AND EMAIL='" + ((Empleado) bean).getEmail() + "'";
                }
                if (((Empleado) bean).getTelefono() != null) {
                    sql += " AND TELEFONO='" + ((Empleado) bean).getTelefono() + "'";
                }
                if (((Empleado) bean).getID_ZonaPrivada() != null && !((Empleado) bean).getID_ZonaPrivada().isEmpty()) {
                    sql += " AND ID_ZONAPRIVADA_EMP='" + ((Empleado) bean).getID_ZonaPrivada() + "'";
                }
            }
            System.out.println("Ejecutando SQL: " + sql);
            ResultSet rs = motor.executeQuery(sql);

            while (rs.next()) {
                Empleado empleado = new Empleado();
                try {
                    empleado.setID_Empleado(rs.getString("ID_EMPLEADO"));
                    empleado.setNombre(rs.getString("NOMBRE"));
                    empleado.setApellidos(rs.getString("APELLIDOS"));
                    empleado.setDireccion(rs.getString("DIRECCION"));
                    empleado.setCargo(rs.getString("CARGO"));
                    empleado.setEmail(rs.getString("EMAIL"));
                    empleado.setTelefono(rs.getString("TELEFONO"));
                    empleado.setID_ZonaPrivada(rs.getString("ID_ZONAPRIVADA_EMP"));

                    empleados.add(empleado);
                } catch (SQLException e) {
                    System.err.println("Error al convertir los datos del empleado: " + e.getMessage());
                    e.printStackTrace(); // Imprimir la traza completa de la excepción
                }
            }
            System.out.println("Número de empleados encontrados: " + empleados.size());

        } catch (Exception ex) {
            empleados.clear();
            System.err.println("Error en findAll: " + ex.getMessage());
            ex.printStackTrace(); // Imprimir la traza completa de la excepción
        } finally {
            motor.disconnect();
        }
        return empleados;
    }
}
