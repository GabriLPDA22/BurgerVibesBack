package Model.DAO;

import Model.Entities.Producto;
import Model.MotorOracle.MotorOracle;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ProductoDao implements IDao {
    private final String SQL_FIND_ALL = "SELECT * FROM PRODUCTO WHERE 1=1 ";

    @Override
    public int add(Object bean) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int delete(Integer e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String delete(String nombre, String apellidos) {
        return "";
    }

    @Override
    public int update(Object bean) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Producto> findAll(Object bean) {
        ArrayList<Producto> productos = new ArrayList<>();
        MotorOracle motor = new MotorOracle();
        try {
            motor.connect();
            String sql = SQL_FIND_ALL;
            if (bean != null) {
                if (((Producto) bean).getID_Producto() != null) {
                    sql += " AND ID_PRODUCTO='" + ((Producto) bean).getID_Producto() + "'";
                }
                if (((Producto) bean).getPrecio() > 0) {
                    sql += " AND PRECIO=" + ((Producto) bean).getPrecio();
                }
                if (((Producto) bean).getNombre() != null) {
                    sql += " AND NOMBRE='" + ((Producto) bean).getNombre() + "'";
                }
                if (((Producto) bean).getDescripcion() != null) {
                    sql += " AND DESCRIPCION='" + ((Producto) bean).getDescripcion() + "'";
                }
                if (((Producto) bean).getDisponibleEnVlc() != null) {
                    sql += " AND DISPONIBLEENVLC='" + ((Producto) bean).getDisponibleEnVlc() + "'";
                }
                if (((Producto) bean).getDisponibleEnZgz() != null) {
                    sql += " AND DISPONIBLEENZGZ='" + ((Producto) bean).getDisponibleEnZgz() + "'";
                }
                if (((Producto) bean).getID_Categoria_pro() != null) {
                    sql += " AND ID_CATEGORIA_PRO='" + ((Producto) bean).getID_Categoria_pro() + "'";//FALTA HACERLO BIEN POR SER FK
                }
            }
            ResultSet rs = motor.executeQuery(sql);

            while (rs.next()) {
                Producto producto = new Producto();
                producto.setID_Producto(rs.getString("ID_PRODUCTO"));
                producto.setPrecio(rs.getDouble("PRECIO"));
                producto.setNombre(rs.getString("NOMBRE"));
                producto.setDescripcion(rs.getString("DESCRIPCION"));
                producto.setDisponibleEnVlc(rs.getString("DISPONIBLEENVLC"));
                producto.setDisponibleEnZgz(rs.getString("DISPONIBLEENZGZ"));
                producto.setID_Categoria_pro(rs.getString("ID_CATEGORIA_PRO"));

                productos.add(producto);
            }

        } catch (Exception ex) {
            productos.clear();
        } finally {
            motor.disconnect();
        }
        return productos;
    }
}
