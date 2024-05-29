package Model.DAO;

import Model.Entities.ZonaPrivada;
import Model.MotorOracle.MotorOracle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ZonaPrivadaDao implements IDao<ZonaPrivada, String> {
    private final String SQL_FIND_ALL = "SELECT * FROM ZONAPRIVADA WHERE 1=1 ";

    @Override
    public int add(ZonaPrivada bean) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int delete(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int update(ZonaPrivada bean) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<ZonaPrivada> findAll(ZonaPrivada bean) {
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
            try {
                motor.disconnect();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return zonasPrivadas;
    }
}
