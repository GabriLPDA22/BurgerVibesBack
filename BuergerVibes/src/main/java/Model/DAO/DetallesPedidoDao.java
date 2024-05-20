package Model.DAO;

import Model.Entities.DetallesPedido;
import Model.MotorOracle.MotorOracle;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DetallesPedidoDao implements IDao {
    private final String SQL_FIND_ALL = "SELECT * FROM DETALLESPEDIDO WHERE 1=1 ";

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
    public ArrayList<DetallesPedido> findAll(Object bean) {
        ArrayList<DetallesPedido> detallesPedidos = new ArrayList<>();
        MotorOracle motor = new MotorOracle();
        try {
            motor.connect();
            String sql = SQL_FIND_ALL;
            if (bean != null) {
                if (((DetallesPedido) bean).getID_Detalles() != null) {
                    sql += " AND ID_DETALLES='" + ((DetallesPedido) bean).getID_Detalles() + "'";
                }
                // Agregar más condiciones según sea necesario
            }
            ResultSet rs = motor.executeQuery(sql);

            while (rs.next()) {
                DetallesPedido detalle = new DetallesPedido();
                detalle.setID_Detalles(rs.getString("ID_DETALLES"));
                detalle.setCantidad(rs.getInt("CANTIDAD"));
                detalle.setPrecioUnitario(rs.getDouble("PRECIO_UNITARIO"));
                detalle.setID_Pedido_det(rs.getString("ID_PEDIDO_DET"));
                detalle.setID_Producto_det(rs.getString("ID_PRODUCTO_DET"));

                detallesPedidos.add(detalle);
            }

        } catch (Exception ex) {
            detallesPedidos.clear();
        } finally {
            motor.disconnect();
        }
        return detallesPedidos;
    }
}
