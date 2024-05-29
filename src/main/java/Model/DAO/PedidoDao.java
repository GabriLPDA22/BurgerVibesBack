package Model.DAO;

import Model.Entities.DetallesPedido;
import Model.Entities.Pedido;
import Model.Entities.Pago;
import Model.MotorOracle.MotorOracle;

import java.sql.*;
import java.util.ArrayList;

public class PedidoDao implements IDao<Pedido, String> {
    private final String SQL_FIND_ALL = "SELECT * FROM PEDIDO WHERE 1=1 ";
    private final String SQL_INSERT_PEDIDO = "INSERT INTO PEDIDO (ID_PEDIDO, TIPOENTREGA, FECHA, ID_CLIENTE_PED, ID_EMPLEADO_PED, CANTIDAD) VALUES (PEDIDO_SEQ.NEXTVAL, ?, TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS'), ?, ?, ?)";
    private final String SQL_INSERT_DETALLE_PEDIDO = "INSERT INTO DETALLESPEDIDO (ID_DETALLES, ID_PEDIDO_DET, NOMBRE, TELEFONO, EMAIL, DIRECCION, HORA_ENTREGA, NOTA, COD_PROMOCIONAL, TOTALPEDIDO, ID_PRODUCTO_DET) VALUES (DETALLESPEDIDO_SEQ.NEXTVAL, ?, ?, ?, ?, ?, TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS'), ?, ?, ?, ?)";
    private final String SQL_INSERT_PAGO = "INSERT INTO PAGO (ID_PAGO, METODOPAGO, ID_PEDIDO_PAG, PAIS) VALUES (PAGO_SEQ.NEXTVAL, ?, ?, ?)";

    @Override
    public int add(Pedido bean) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int addPedido(Pedido pedido) throws SQLException {
        MotorOracle motor = new MotorOracle();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int idPedido = -1;

        try {
            motor.connect();
            connection = motor.getConnection();
            connection.setAutoCommit(false); // Desactivar la confirmación automática

            // Insertar en la tabla PEDIDO
            String sqlPedido = SQL_INSERT_PEDIDO;
            pstmt = connection.prepareStatement(sqlPedido, new String[]{"ID_PEDIDO"});
            pstmt.setString(1, pedido.getTipoEntrega());
            pstmt.setString(2, pedido.getFecha());
            pstmt.setString(3, pedido.getID_Cliente_ped());
            pstmt.setString(4, pedido.getID_Empleado_ped());
            pstmt.setInt(5, pedido.getDetalles().size());
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                idPedido = rs.getInt(1);
            }

            // Insertar en la tabla DETALLESPEDIDO
            String sqlDetalles = SQL_INSERT_DETALLE_PEDIDO;
            for (DetallesPedido detalle : pedido.getDetalles()) {
                pstmt = connection.prepareStatement(sqlDetalles);
                pstmt.setInt(1, idPedido);
                pstmt.setString(2, detalle.getNombre());
                pstmt.setString(3, detalle.getTelefono());
                pstmt.setString(4, detalle.getEmail());
                pstmt.setString(5, detalle.getDireccion());
                pstmt.setString(6, detalle.getHora_Entrega());
                pstmt.setString(7, detalle.getNota());
                pstmt.setString(8, detalle.getCod_promocional());
                pstmt.setDouble(9, detalle.getTotalPedido());
                pstmt.setString(10, detalle.getID_Producto_det());
                pstmt.executeUpdate();
            }

            // Insertar en la tabla PAGO
            String sqlPago = SQL_INSERT_PAGO;
            pstmt = connection.prepareStatement(sqlPago);
            pstmt.setString(1, pedido.getPago().getMetodoPago());
            pstmt.setInt(2, idPedido);
            pstmt.setString(3, pedido.getPago().getPais());
            pstmt.executeUpdate();

            connection.commit(); // Confirmar la transacción
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback(); // Hacer rollback si hay un error
            }
            throw new SQLException("Error al realizar el pedido", e);
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true); // Reactivar la confirmación automática
            }
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (connection != null) connection.close();
        }

        return idPedido;
    }

    @Override
    public int delete(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int update(Pedido bean) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Pedido> findAll(Pedido bean) {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        MotorOracle motor = new MotorOracle();
        try {
            motor.connect();
            String sql = SQL_FIND_ALL;
            if (bean != null) {
                if (bean.getID_Pedido() != null) {
                    sql += " AND ID_PEDIDO='" + bean.getID_Pedido() + "'";
                }
                if (bean.getFecha() != null) {
                    sql += " AND FECHA=TO_TIMESTAMP('" + bean.getFecha() + "', 'YYYY-MM-DD HH24:MI:SS')";
                }
                if (bean.getTipoEntrega() != null) {
                    sql += " AND TIPOENTREGA='" + bean.getTipoEntrega() + "'";
                }

                if (bean.getID_Cliente_ped() != null) {
                    sql += " AND ID_CLIENTE_PED='" + bean.getID_Cliente_ped() + "'";
                }
                if (bean.getID_Empleado_ped() != null) {
                    sql += " AND ID_EMPLEADO_PED='" + bean.getID_Empleado_ped() + "'";
                }
            }
            ResultSet rs = motor.executeQuery(sql);

            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setID_Pedido(rs.getString("ID_PEDIDO"));
                pedido.setFecha(rs.getString("FECHA"));
                pedido.setTipoEntrega(rs.getString("TIPOENTREGA"));
                pedido.setCantidad(rs.getString("CANTIDAD"));
                pedido.setID_Cliente_ped(rs.getString("ID_CLIENTE_PED"));
                pedido.setID_Empleado_ped(rs.getString("ID_EMPLEADO_PED"));

                pedidos.add(pedido);
            }

        } catch (Exception ex) {
            pedidos.clear();
            System.err.println("Error en findAll: " + ex.getMessage());
            ex.printStackTrace(); // Imprimir la traza completa de la excepción
        } finally {
            try {
                motor.disconnect();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return pedidos;
    }
}
