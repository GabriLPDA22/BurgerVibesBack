package Model.DAO;

import Model.Entities.Pago;
import Model.MotorOracle.MotorOracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PagoDao implements IDao<Pago, String> {
    private final String SQL_FIND_ALL = "SELECT * FROM PAGO WHERE 1=1 ";
    private final String SQL_INSERT_PAGO = "INSERT INTO PAGO (ID_PAGO, METODOPAGO, ID_PEDIDO_PAG, PAIS) VALUES (PAGO_SEQ.NEXTVAL, ?, ?, ?)";

    @Override
    public int add(Pago pago) {
        MotorOracle motor = new MotorOracle();
        Connection connection = null;
        PreparedStatement pstmt = null;
        int rowsAffected = 0;

        try {
            motor.connect();
            connection = motor.getConnection();
            pstmt = connection.prepareStatement(SQL_INSERT_PAGO);
            pstmt.setString(1, pago.getMetodoPago());
            pstmt.setString(2, pago.getID_Pedido_pag());
            pstmt.setString(3, pago.getPais());

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
    public int update(Pago bean) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Pago> findAll(Pago bean) {
        ArrayList<Pago> pagos = new ArrayList<>();
        MotorOracle motor = new MotorOracle();
        try {
            motor.connect();
            String sql = SQL_FIND_ALL;
            if (bean != null) {
                if (bean.getID_Pago() != null) {
                    sql += " AND ID_PAGO='" + bean.getID_Pago() + "'";
                }
                if (bean.getMetodoPago() != null) {
                    sql += " AND METODO_PAGO='" + bean.getMetodoPago() + "'";
                }
                if (bean.getID_Pedido_pag() != null) {
                    sql += " AND ID_PEDIDO_PAG='" + bean.getID_Pedido_pag() + "'";
                }
                if (bean.getPais() != null) {
                    sql += " AND PAIS='" + bean.getPais() + "'";
                }
            }
            ResultSet rs = motor.executeQuery(sql);

            while (rs.next()) {
                Pago pago = new Pago();
                pago.setID_Pago(rs.getString("ID_PAGO"));
                pago.setMetodoPago(rs.getString("METODO_PAGO"));
                pago.setID_Pedido_pag(rs.getString("ID_PEDIDO_PAG"));
                pago.setPais(rs.getString("PAIS"));

                pagos.add(pago);
            }

        } catch (Exception ex) {
            pagos.clear();
            System.err.println("Error en findAll: " + ex.getMessage());
            ex.printStackTrace(); // Imprimir la traza completa de la excepción
        } finally {
            try {
                motor.disconnect();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return pagos;
    }
}
