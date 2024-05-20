package Model.DAO;

import Model.Entities.ZonaPrivada;
import Model.MotorOracle.MotorOracle;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ZonaPrivadaDao implements IDao {
    private final String SQL_FIND_ALL = "SELECT * FROM ZONAPRIVADA WHERE 1=1 ";

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
    public ArrayList<ZonaPrivada> findAll(Object bean) {
        ArrayList<ZonaPrivada> zonasPrivadas = new ArrayList<>();
        MotorOracle motor = new MotorOracle();
        try {
            motor.connect();
            String sql = SQL_FIND_ALL;
            if (bean != null) {
                if (((ZonaPrivada)bean).getID_ZonaPrivada() != null) {
                    sql += " AND ID_ZONAPRIVADA='" + ((ZonaPrivada)bean).getID_ZonaPrivada() + "'";
                }
                if (((ZonaPrivada)bean).getVerPedidos() != null) {
                    sql += " AND VERPEDIDOS='" + ((ZonaPrivada)bean).getVerPedidos() + "'";
                }
                if (((ZonaPrivada)bean).getAdministrarReserva() != null) {
                    sql += " AND ADMINISTRARRESERVA='" + ((ZonaPrivada)bean).getAdministrarReserva() + "'";
                }
            }
            ResultSet rs = motor.executeQuery(sql);

            while (rs.next()) {
                ZonaPrivada zonaPrivada = new ZonaPrivada();
                zonaPrivada.setID_ZonaPrivada(rs.getString("ID_ZONAPRIVADA"));
                zonaPrivada.setVerPedidos(rs.getString("VERPEDIDOS"));
                zonaPrivada.setAdministrarReserva(rs.getString("ADMINISTRARRESERVA"));

                zonasPrivadas.add(zonaPrivada);
            }

        } catch (Exception ex) {
            zonasPrivadas.clear();
        } finally {
            motor.disconnect();
        }
        return zonasPrivadas;
    }
}
