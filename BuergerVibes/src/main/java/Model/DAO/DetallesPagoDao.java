package Model.DAO;

import Model.Entities.DetallesPago;
import Model.MotorOracle.MotorOracle;

import java.sql.ResultSet;
import java.util.ArrayList;

public class DetallesPagoDao implements IDao {
    private final String SQL_FIND_ALL = "SELECT * FROM DETALLESPAGO WHERE 1=1 ";

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
    public ArrayList<DetallesPago> findAll(Object bean) {
        ArrayList<DetallesPago> detallesPagos = new ArrayList<>();
        MotorOracle motor = new MotorOracle();
        try {
            motor.connect();
            String sql = SQL_FIND_ALL;
            if (bean != null) {
                if (((DetallesPago) bean).getID_DetallesPago() != null && !((DetallesPago) bean).getID_DetallesPago().isEmpty()) {
                    sql += " AND ID_DETALLESPAGO='" + ((DetallesPago) bean).getID_DetallesPago() + "'";
                }
                if (((DetallesPago) bean).getTotalPagado() > 0) {
                    sql += " AND TOTALPAGADO=" + ((DetallesPago) bean).getTotalPagado();
                }
                if (((DetallesPago) bean).getFechaCaducidad() != null) {
                    sql += " AND FECHACADUCIDAD='" + ((DetallesPago) bean).getFechaCaducidad() + "'";
                }
                if (((DetallesPago) bean).getCVV() != null) {
                    sql += " AND CVV='" + ((DetallesPago) bean).getCVV() + "'";
                }
                if (((DetallesPago) bean).getNombreTitular() != null) {
                    sql += " AND NOMBRETITULAR='" + ((DetallesPago) bean).getNombreTitular() + "'";
                }
                if (((DetallesPago) bean).getID_Pago_detpag() != null && !((DetallesPago) bean).getID_Pago_detpag().isEmpty()) {
                    sql += " AND ID_PAGO_DETPAG='" + ((DetallesPago) bean).getID_Pago_detpag() + "'";
                }
            }
            ResultSet rs = motor.executeQuery(sql);

            while (rs.next()) {
                DetallesPago detallesPago = new DetallesPago();
                detallesPago.setID_DetallesPago(rs.getString("ID_DETALLESPAGO"));
                detallesPago.setTotalPagado(rs.getDouble("TOTALPAGADO"));
                detallesPago.setFechaCaducidad(rs.getString("FECHACADUCIDAD"));
                detallesPago.setCVV(rs.getString("CVV"));
                detallesPago.setNombreTitular(rs.getString("NOMBRETITULAR"));
                detallesPago.setID_Pago_detpag(rs.getString("ID_PAGO_DETPAG"));

                detallesPagos.add(detallesPago);
            }

        } catch (Exception ex) {
            detallesPagos.clear();
            ex.printStackTrace(); // Manejar adecuadamente esta excepción en una aplicación real
        } finally {
            motor.disconnect();
        }
        return detallesPagos;
    }
}
