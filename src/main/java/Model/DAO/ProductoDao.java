package Model.DAO;
import Model.Entities.Empleado;
import Model.Entities.Producto;
import Model.MotorOracle.MotorOracle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductoDao implements IDao<Producto, String> {
    private final String SQL_FIND_ALL = "SELECT * FROM PRODUCTO WHERE 1=1 ";
    private final String SQL_ADD = "INSERT INTO PRODUCTO (ID_PRODUCTO, NOMBRE, PRECIO, DESCRIPCION, DISPONIBLEENVLC, DISPONIBLEENZGZ, ID_CATEGORIA_PRO) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final String SQL_DELETE = "DELETE FROM PRODUCTO WHERE ID_PRODUCTO = ?";
    private final String SQL_UPDATE = "UPDATE PRODUCTO SET ";
    @Override
    public int add(Producto bean) {
        if (bean.getID_Producto() == null || bean.getID_Producto().isEmpty() ||
                bean.getNombre() == null || bean.getNombre().isEmpty() ||
                bean.getPrecio() <= 0 ||
                bean.getDescripcion() == null || bean.getDescripcion().isEmpty() ||
                bean.getDisponibleEnVlc() == null || bean.getDisponibleEnVlc().isEmpty() ||
                bean.getDisponibleEnZgz() == null || bean.getDisponibleEnZgz().isEmpty() ||
                bean.getID_Categoria_pro() == null || bean.getID_Categoria_pro().isEmpty()) {

            System.out.println("Error. Se deben proporcionar todos los datos del producto");
            return 0;
        }

        int resp = 0;
        MotorOracle motor = new MotorOracle();
        PreparedStatement ps = null;
        try {
            motor.connect();
            ps = motor.preparedStatement(SQL_ADD);
            ps.setString(1, bean.getID_Producto());
            ps.setString(2, bean.getNombre());
            ps.setDouble(3, bean.getPrecio());
            ps.setString(4, bean.getDescripcion());
            ps.setString(5, bean.getDisponibleEnVlc());
            ps.setString(6, bean.getDisponibleEnZgz());
            ps.setString(7, bean.getID_Categoria_pro());

            resp = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al insertar producto: " + e.getMessage());
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
            System.out.println("Producto insertado con éxito.");
        }
        return resp;
    }

    @Override
    public int update(Producto bean) {
        if (bean == null || bean.getID_Producto() == null || bean.getID_Producto().isEmpty()) {
            System.out.println("Error. Se debe proporcionar el ID del producto y al menos un campo a actualizar.");
            return 0;
        }

        StringBuilder sql = new StringBuilder("UPDATE PRODUCTO SET ");
        ArrayList<Object> params = new ArrayList<>();

        if (bean.getNombre() != null && !bean.getNombre().isEmpty()) {
            sql.append("Nombre = ?, ");
            params.add(bean.getNombre());
        }

        if (bean.getPrecio() > 0) {
            sql.append("Precio = ?, ");
            params.add(bean.getPrecio());
        }

        if (bean.getDescripcion() != null && !bean.getDescripcion().isEmpty()) {
            sql.append("Descripcion = ?, ");
            params.add(bean.getDescripcion());
        }

        if (bean.getDisponibleEnVlc() != null && !bean.getDisponibleEnVlc().isEmpty()) {
            sql.append("DisponibleEnVlc = ?, ");
            params.add(bean.getDisponibleEnVlc());
        }

        if (bean.getDisponibleEnZgz() != null && !bean.getDisponibleEnZgz().isEmpty()) {
            sql.append("DisponibleEnZgz = ?, ");
            params.add(bean.getDisponibleEnZgz());
        }

        if (bean.getID_Categoria_pro() != null && !bean.getID_Categoria_pro().isEmpty()) {
            sql.append("ID_Categoria_pro = ?, ");
            params.add(bean.getID_Categoria_pro());
        }
        if (bean.getProducto_IMG() != null && !bean.getProducto_IMG().isEmpty()) {
            sql.append("Descripcion = ?, ");
            params.add(bean.getProducto_IMG());
        }

        // Remove the last comma and space
        if (params.isEmpty()) {
            System.out.println("No hay campos para actualizar.");
            return 0;
        } else {
            sql.setLength(sql.length() - 2);
        }

        sql.append(" WHERE ID_PRODUCTO = ?");
        params.add(bean.getID_Producto());

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
            System.out.println("Error al actualizar producto: " + e.getMessage());
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
            System.out.println("Producto actualizado con éxito.");
        } else {
            System.out.println("No se pudo actualizar.");
        }
        return resp;
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
            System.out.println("Error al eliminar el producto: " + e.getMessage());
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
    public ArrayList<Producto> findAll(Producto bean) {
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
                if (((Producto) bean).getProducto_IMG() != null) {
                    sql += " AND PRODUCTO_IMG='" + ((Producto) bean).getProducto_IMG() + "'";
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
                producto.setProducto_IMG(rs.getString("PRODUCTO_IMG"));
                productos.add(producto);
            }

        } catch (Exception ex) {
            productos.clear();
        } finally {
            try {
                motor.disconnect();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return productos;
    }
}