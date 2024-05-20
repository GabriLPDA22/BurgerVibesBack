package Model.DAO;

import Model.Entities.Pago;
import Model.MotorOracle.MotorOracle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PagoDao implements IDao {
    private final String SQL_FIND_ALL = "SELECT * FROM PAGO WHERE 1=1 ";

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
    public ArrayList<Pago> findAll(Object bean) {
        ArrayList<Pago> pagos = new ArrayList<>();
        MotorOracle motor = new MotorOracle();
        try {
            motor.connect();
            String sql = SQL_FIND_ALL;
            if (bean != null) {
                if (((Pago) bean).getID_Pago() != null) {
                    sql += " AND ID_PAGO='" + ((Pago) bean).getID_Pago() + "'";
                }
                if (((Pago) bean).getMetodoPago() != null) {
                    sql += " AND METODO_PAGO='" + ((Pago) bean).getMetodoPago() + "'";
                }
                if (((Pago) bean).getID_Pedido_pag() != null) {
                    sql += " AND ID_PEDIDO_PAG='" + ((Pago) bean).getID_Pedido_pag() + "'";
                }
            }
            ResultSet rs = motor.executeQuery(sql);

            while (rs.next()) {
                Pago pago = new Pago();
                try {
                    pago.setID_Pago(rs.getString("ID_PAGO"));
                    pago.setMetodoPago(rs.getString("METODO_PAGO"));
                    pago.setID_Pedido_pag(rs.getString("ID_PEDIDO_PAG"));

                    pagos.add(pago);
                } catch (SQLException e) {
                    System.err.println("Error al convertir los datos del pago: " + e.getMessage());
                    e.printStackTrace(); // Imprimir la traza completa de la excepción
                }
            }
            System.out.println("Número de pagos encontrados: " + pagos.size());

        } catch (Exception ex) {
            pagos.clear();
            System.err.println("Error en findAll: " + ex.getMessage());
            ex.printStackTrace(); // Imprimir la traza completa de la excepción
        } finally {
            motor.disconnect();
        }
        return pagos;
    }
}
