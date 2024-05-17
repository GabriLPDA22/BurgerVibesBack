package Model.DAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;




















/*
public class EmpleadoDAO implements IDao<Empleado, Integer> {

    private final String SQL_FIND_ALL = "SELECT * FROM empleados";

    private MotorOracle MotorOracle;

    public EmpleadoDAO() {
        this.MotorOracle = new MotorOracle();
    }

    @Override
    public ArrayList<Empleado> findAll() {
        ArrayList<Empleado> empleados = new ArrayList<>();
        this.MotorOracle.connect();
        try {
            ResultSet rs = this.MotorOracle.executeQuery(SQL_FIND_ALL);

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
            sqlex.printStackTrace();
        } finally {
            this.MotorOracle.disconnect();
        }
        return empleados;
    }

    @Override
    public boolean add(Empleado empleado) {
        boolean isAdded = false;
        String sql = "INSERT INTO empleados (Nombre, Apellidos, Direccion, Cargo, Email, Telefono, ID_ZonaPrivada) VALUES (?, ?, ?, ?, ?, ?, ?)";
        this.MotorOracle.connect();
        try {
            this.MotorOracle.prepareStatement(sql);
            this.MotorOracle.setString(1, empleado.getNombre());
            this.MotorOracle.setString(2, empleado.getApellidos());
            this.MotorOracle.setString(3, empleado.getDireccion());
            this.MotorOracle.setString(4, empleado.getCargo());
            this.MotorOracle.setString(5, empleado.getEmail());
            this.MotorOracle.setString(6, empleado.getTelefono());
            this.MotorOracle.setInt(7, empleado.getID_ZonaPrivada());
            isAdded = this.MotorOracle.executeUpdate() > 0;
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            this.MotorOracle.disconnect();
        }
        return isAdded;
    }

    @Override
    public boolean delete(Integer e) {
        boolean isDeleted = false;
        String sql = "DELETE FROM empleados WHERE ID_Empleado=?";
        this.MotorOracle.connect();
        try {
            this.MotorOracle.prepareStatement(sql);
            this.MotorOracle.setInt(1, e); // Usar el parámetro e en lugar de id
            isDeleted = this.MotorOracle.executeUpdate() > 0;
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            this.MotorOracle.disconnect();
        }
        return isDeleted;
    }

    @Override
    public int update(Empleado empleado) {
        boolean isUpdated = false;
        String sql = "UPDATE empleados SET Nombre=?, Apellidos=?, Direccion=?, Cargo=?, Email=?, Telefono=?, ID_ZonaPrivada=? WHERE ID_Empleado=?";
        this.MotorOracle.connect();
        try {
            this.MotorOracle.prepareStatement(sql);
            this.MotorOracle.setString(1, empleado.getNombre());
            this.MotorOracle.setString(2, empleado.getApellidos());
            this.MotorOracle.setString(3, empleado.getDireccion());
            this.MotorOracle.setString(4, empleado.getCargo());
            this.MotorOracle.setString(5, empleado.getEmail());
            this.MotorOracle.setString(6, empleado.getTelefono());
            this.MotorOracle.setInt(7, empleado.getID_ZonaPrivada());
            this.MotorOracle.setInt(8, empleado.getID_Empleado());
            isUpdated = this.MotorOracle.executeUpdate() > 0;
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            this.MotorOracle.disconnect();
        }
        return isUpdated ? 1 : 0; // Devolver un valor significativo
    }

    @Override

    public ArrayList<Empleado> findAll(Empleado bean) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

 */


