package Action;

import Model.Entities.ZonaPrivada;
import Model.DAO.ZonaPrivadaDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class ZonaPrivadaAction implements IAction {
    @Override
    //ACTION=ZONAPRIVADA.FIND_ALL+ID_ZonaPrivada="ZP01"+VerPedidos="Si"
    public String execute(HttpServletRequest request, HttpServletResponse response, String action) {
        String strReturn = "";
        switch (action) {
            case "FIND_FIRST":
                // Implementación para FIND_FIRST si es necesario
                break;
            case "FIND_ALL":
                // ZonaPrivada zonaPrivada = new ZonaPrivada();
                // zonaPrivada.setID_ZonaPrivada("ZP01");
                // zonaPrivada.setVerPedidos("Si");
                // strReturn = findAll(zonaPrivada);
                strReturn = findAll();
                break;
            default:
                strReturn = "ERROR. Invalid Action";
        }
        return strReturn;
    }

    private String findAll() {
        ZonaPrivadaDao zonaPrivadaDao = new ZonaPrivadaDao();
        // ArrayList<ZonaPrivada> zonasPrivadas = zonaPrivadaDao.findAll(zonaPrivada);
        ArrayList<ZonaPrivada> zonasPrivadas = zonaPrivadaDao.findAll(null);
        return ZonaPrivada.toArrayJSon(zonasPrivadas);
    }
}
