package Action;

import Model.DAO.DetallesPagoDao;
import Model.Entities.DetallesPago;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class DetallesPagoAction implements IAction {
    @Override
    // ACTION=DETALLESPAGO.FIND_ALL+ID_DetallesPago="DP01"+TotalPagado="100.50"
    public String execute(HttpServletRequest request, HttpServletResponse response, String action) {
        String strReturn = "";
        switch (action) {
            case "FIND_FIRST":
                // Implementación para FIND_FIRST si es necesario
                break;
            case "FIND_ALL":
                // DetallesPago detallesPago = new DetallesPago();
                // detallesPago.setID_DetallesPago("DP01");
                // detallesPago.setTotalPagado(100.50);
                // strReturn = findAll(detallesPago);
                strReturn = findAll();
                break;
            default:
                strReturn = "ERROR. Invalid Action";
        }
        return strReturn;
    }

    private String findAll() {
        DetallesPagoDao detallesPagoDao = new DetallesPagoDao();
        // ArrayList<DetallesPago> detallesPagos = detallesPagoDao.findAll(detallesPago);
        ArrayList<DetallesPago> detallesPagos = detallesPagoDao.findAll(null);
        return DetallesPago.toArrayJSon(detallesPagos);
    }
}
