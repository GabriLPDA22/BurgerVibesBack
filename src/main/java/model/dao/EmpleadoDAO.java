package model.dao;

import model.entities.Empleado;
import model.motorsql.MotorSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmpleadoDAO implements DAO<Empleado, Integer> {

    private final String SQL_FIND_ALL = "SELECT * FROM empleados";

    private MotorSQL motorSQL;
    private int id;

    public EmpleadoDAO() {
        this.motorSQL = new MotorSQL();
    }

    @Override
    public ArrayList<Empleado> findAll() {
        ArrayList<Empleado> empleados = new ArrayList<>();
        this.motorSQL.connect();
        try {
            ResultSet rs = this.motorSQL.executeQuery(SQL_FIND_ALL);

            while (rs.next()) {
                Empleado empleado = new Empleado();
                
                empleado.setID_Empleado(rs.getInt("ID_Empleado"));
                empleado.setNombre(rs.getString("Nombre"));
                empleado.setApellidos(rs.getString("Apellidos"));
                empleado.setDireccion(rs.getString("Direccion"));
                empleado.setCargo(rs.getString("Cargo"));
                empleado.setEmail(rs.getString("Email"));
                empleado.setTelefono(rs.getString("Telefono"));
                empleado.setID_ZonaPrivada(rs.getInt("ID_ZonaPrivada"));

                empleados.add(empleado);
            }

        } catch (SQLException sqlex) {
            sqlex.getMessage();
            System.out.println(sqlex);
        } finally {
            this.motorSQL.disconnect();
        }
        return empleados;
    }

    public boolean add(Empleado empleado) {
        boolean isAdded = false;
        String sql = "INSERT INTO empleados (Nombre, Apellidos, Direccion, Cargo, Email, Telefono, ID_ZonaPrivada) VALUES (?, ?, ?, ?, ?, ?, ?)";
        this.motorSQL.connect();
        try {
            this.motorSQL.prepareStatement(sql);
            this.motorSQL.setString(1, empleado.getNombre());
            this.motorSQL.setString(2, empleado.getApellidos());
            this.motorSQL.setString(3, empleado.getDireccion());
            this.motorSQL.setString(4, empleado.getCargo());
            this.motorSQL.setString(5, empleado.getEmail());
            this.motorSQL.setString(6, empleado.getTelefono());
            this.motorSQL.setInt(7, empleado.getID_ZonaPrivada());
            isAdded = this.motorSQL.executeUpdate() > 0;
        } catch (SQLException sqlex) {
            sqlex.getMessage();
            System.out.println(sqlex);
        } finally {
            this.motorSQL.disconnect();
        }
        return isAdded;
    }

    @Override
    public boolean delete(Integer e) {
        boolean isDeleted = false;
        String sql = "DELETE FROM empleados WHERE ID_Empleado=?";
        this.motorSQL.connect();
        try {
            this.motorSQL.prepareStatement(sql);
            this.motorSQL.setInt(1, id);
            isDeleted = this.motorSQL.executeUpdate() > 0;
        } catch (SQLException sqlex) {
            sqlex.getMessage();
            System.out.println(sqlex);
        } finally {
            this.motorSQL.disconnect();
        }
        return isDeleted;
    }


    public int update(Empleado empleado) {
        boolean isUpdated = false;
        String sql = "UPDATE empleados SET Nombre=?, Apellidos=?, Direccion=?, Cargo=?, Email=?, Telefono=?, ID_ZonaPrivada=? WHERE ID_Empleado=?";
        this.motorSQL.connect();
        try {
            this.motorSQL.prepareStatement(sql);
            this.motorSQL.setString(1, empleado.getNombre());
            this.motorSQL.setString(2, empleado.getApellidos());
            this.motorSQL.setString(3, empleado.getDireccion());
            this.motorSQL.setString(4, empleado.getCargo());
            this.motorSQL.setString(5, empleado.getEmail());
            this.motorSQL.setString(6, empleado.getTelefono());
            this.motorSQL.setInt(7, empleado.getID_ZonaPrivada());
            this.motorSQL.setInt(8, empleado.getID_Empleado());
            isUpdated = this.motorSQL.executeUpdate() > 0;
        } catch (SQLException sqlex) {
            sqlex.getMessage();
            System.out.println(sqlex);
        } finally {

        }
        this.motorSQL.disconnect();
        return Integer.parseInt(null);
    }

    @Override
    public ArrayList<Empleado> findAll(Empleado bean) {
        return null;
    }

    // Implement other methods (find by ID, find by filter, etc.) as needed
}