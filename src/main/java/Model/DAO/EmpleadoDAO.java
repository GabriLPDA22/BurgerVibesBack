package Model.DAO;

import Model.Entities.Empleado;
import Model.MotorOracle.MotorOracle;

import java.sql.ResultSet;
import java.util.ArrayList;

public class EmpleadoDAO implements IDao {
    private final String SQL_FIND_ALL = "SELECT * FROM EMPLEADO WHERE 1=1 ";

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
    public ArrayList<Empleado> findAll(Object bean) {
        ArrayList<Empleado> empleados = new ArrayList<>();
        MotorOracle motor = new MotorOracle();
        try {
            motor.connect();
            String sql = SQL_FIND_ALL;
            if (bean != null) {
                if (((Empleado) bean).getID_Empleado() != 0) {
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
                if (((Empleado) bean).getID_ZonaPrivada() != 0) { //SE TIENE QUE HACER DE OTRA FORMA YA QUE ES FK
                    sql += " AND ID_ZONAPRIVADA='" + ((Empleado) bean).getID_ZonaPrivada() + "'";
                }
            }
            ResultSet rs = motor.executeQuery(sql);

            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setID_Empleado(rs.getInt("ID_EMPLEADO"));
                empleado.setNombre(rs.getString("NOMBRE"));
                empleado.setApellidos(rs.getString("APELLIDOS"));
                empleado.setDireccion(rs.getString("DIRECCION"));
                empleado.setCargo(rs.getString("CARGO"));
                empleado.setEmail(rs.getString("EMAIL"));
                empleado.setTelefono(rs.getString("TELEFONO"));
                empleado.setID_ZonaPrivada(rs.getInt("ID_ZONAPRIVADA"));

                empleados.add(empleado);
            }

        } catch (Exception ex) {
            empleados.clear();
        } finally {
            motor.disconnect();
        }
        return empleados;
    }
}
