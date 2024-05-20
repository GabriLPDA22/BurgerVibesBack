package Model.DAO;

import Model.Entities.Pedido;
import Model.MotorOracle.MotorOracle;

import java.sql.ResultSet;
import java.util.ArrayList;

public class PedidoDao implements IDao {
    private final String SQL_FIND_ALL = "SELECT * FROM PEDIDO WHERE 1=1 ";

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
    public ArrayList<Pedido> findAll(Object bean) {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        MotorOracle motor = new MotorOracle();
        try {
            motor.connect();
            String sql = SQL_FIND_ALL;
            if (bean != null) {
                if (((Pedido) bean).getID_Pedido() != null) {
                    sql += " AND ID_PEDIDO='" + ((Pedido) bean).getID_Pedido() + "'";
                }
                if (((Pedido) bean).getFecha() != null) {
                    sql += " AND FECHA=TO_DATE('" + ((Pedido) bean).getFecha() + "', 'YYYY-MM-DD')";
                }
                if (((Pedido) bean).getTipoEntrega() != null) {
                    sql += " AND TIPOENTREGA='" + ((Pedido) bean).getTipoEntrega() + "'";
                }
                if (((Pedido) bean).getEstadoPedido() != null) {
                    sql += " AND ESTADOPEDIDO='" + ((Pedido) bean).getEstadoPedido() + "'";
                }
                if (((Pedido) bean).getID_Cliente_ped() != null) {
                    sql += " AND ID_CLIENTE_PED='" + ((Pedido) bean).getID_Cliente_ped() + "'";
                }
                if (((Pedido) bean).getID_Empleado_ped() != null) {
                    sql += " AND ID_EMPLEADO_PED='" + ((Pedido) bean).getID_Empleado_ped() + "'";
                }
            }
            ResultSet rs = motor.executeQuery(sql);

            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setID_Pedido(rs.getString("ID_PEDIDO"));
                pedido.setFecha(rs.getString("FECHA"));  // Formato simplificado a String
                pedido.setTipoEntrega(rs.getString("TIPOENTREGA"));
                pedido.setEstadoPedido(rs.getString("ESTADOPEDIDO"));
                pedido.setID_Cliente_ped(rs.getString("ID_CLIENTE_PED"));
                pedido.setID_Empleado_ped(rs.getString("ID_EMPLEADO_PED"));

                pedidos.add(pedido);
            }

        } catch (Exception ex) {
            pedidos.clear();
            System.err.println("Error en findAll: " + ex.getMessage());
            ex.printStackTrace(); // Imprimir la traza completa de la excepción
        } finally {
            motor.disconnect();
        }
        return pedidos;
    }
}
