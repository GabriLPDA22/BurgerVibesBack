package Model.DAO;

import Model.Entities.DetallesPedido;
import Model.MotorOracle.MotorOracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DetallesPedidoDao implements IDao<DetallesPedido, String> {
    private final String SQL_FIND_ALL = "SELECT * FROM DETALLESPEDIDO WHERE 1=1 ";
    private final String SQL_INSERT_DETALLE = "INSERT INTO DETALLESPEDIDO (ID_DETALLES, ID_PEDIDO_DET, NOMBRE, TELEFONO, EMAIL, DIRECCION, HORA_ENTREGA, NOTA, COD_PROMOCIONAL, TOTALPEDIDO, ID_PRODUCTO_DET) VALUES (DETALLESPEDIDO_SEQ.NEXTVAL, ?, ?, ?, ?, ?, TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS'), ?, ?, ?, ?)";

    @Override
    public int add(DetallesPedido detalle) {
        MotorOracle motor = new MotorOracle();
        Connection connection = null;
        PreparedStatement pstmt = null;
        int rowsAffected = 0;

        try {
            motor.connect();
            connection = motor.getConnection();
            pstmt = connection.prepareStatement(SQL_INSERT_DETALLE);
            pstmt.setString(1, detalle.getID_Pedido_det());
            pstmt.setString(2, detalle.getNombre());
            pstmt.setString(3, detalle.getTelefono());
            pstmt.setString(4, detalle.getEmail());
            pstmt.setString(5, detalle.getDireccion());
            pstmt.setString(6, detalle.getHora_Entrega());
            pstmt.setString(7, detalle.getNota());
            pstmt.setString(8, detalle.getCod_promocional());
            pstmt.setDouble(9, detalle.getTotalPedido());
            pstmt.setString(10, detalle.getID_Producto_det());

            rowsAffected = pstmt.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rowsAffected;
    }

    @Override
    public int delete(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int update(DetallesPedido bean) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<DetallesPedido> findAll(DetallesPedido bean) {
        ArrayList<DetallesPedido> detallesPedidos = new ArrayList<>();
        MotorOracle motor = new MotorOracle();
        try {
            motor.connect();
            String sql = SQL_FIND_ALL;
            if (bean != null) {
                if (bean.getID_Detalles() != null) {
                    sql += " AND ID_DETALLES='" + bean.getID_Detalles() + "'";
                }
                if (bean.getID_Pedido_det() != null) {
                    sql += " AND ID_PEDIDO_DET='" + bean.getID_Pedido_det() + "'";
                }
                if (bean.getID_Producto_det() != null) {
                    sql += " AND ID_PRODUCTO_DET='" + bean.getID_Producto_det() + "'";
                }
                if (bean.getTotalPedido() != 0) {
                    sql += " AND TOTALPEDIDO=" + bean.getTotalPedido();
                }
                if (bean.getNombre() != null) {
                    sql += " AND NOMBRE='" + bean.getNombre() + "'";
                }
                if (bean.getTelefono() != null) {
                    sql += " AND TELEFONO='" + bean.getTelefono() + "'";
                }
                if (bean.getEmail() != null) {
                    sql += " AND EMAIL='" + bean.getEmail() + "'";
                }
                if (bean.getDireccion() != null) {
                    sql += " AND DIRECCION='" + bean.getDireccion() + "'";
                }
                if (bean.getHora_Entrega() != null) {
                    sql += " AND HORA_ENTREGA=TO_TIMESTAMP('" + bean.getHora_Entrega() + "', 'YYYY-MM-DD HH24:MI:SS')";
                }
                if (bean.getNota() != null) {
                    sql += " AND NOTA='" + bean.getNota() + "'";
                }
                if (bean.getCod_promocional() != null) {
                    sql += " AND COD_PROMOCIONAL='" + bean.getCod_promocional() + "'";
                }
            }
            ResultSet rs = motor.executeQuery(sql);

            while (rs.next()) {
                DetallesPedido detalle = new DetallesPedido();
                detalle.setID_Detalles(rs.getString("ID_DETALLES"));
                detalle.setID_Pedido_det(rs.getString("ID_PEDIDO_DET"));
                detalle.setID_Producto_det(rs.getString("ID_PRODUCTO_DET"));
                detalle.setTotalPedido(rs.getDouble("TOTALPEDIDO"));
                detalle.setNombre(rs.getString("NOMBRE"));
                detalle.setTelefono(rs.getString("TELEFONO"));
                detalle.setEmail(rs.getString("EMAIL"));
                detalle.setDireccion(rs.getString("DIRECCION"));
                detalle.setHora_Entrega(rs.getString("HORA_ENTREGA"));
                detalle.setNota(rs.getString("NOTA"));
                detalle.setCod_promocional(rs.getString("COD_PROMOCIONAL"));

                detallesPedidos.add(detalle);
            }

        } catch (Exception ex) {
            detallesPedidos.clear();
            System.err.println("Error en findAll: " + ex.getMessage());
            ex.printStackTrace(); // Imprimir la traza completa de la excepción
        } finally {
            try {
                motor.disconnect();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return detallesPedidos;
    }
}
