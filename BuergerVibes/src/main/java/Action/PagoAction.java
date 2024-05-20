package Action;

import Model.DAO.PagoDao;
import Model.Entities.Pago;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class PagoAction implements IAction {
    @Override
    //ACTION=PAGO.FIND_ALL+ID_Pago="P01"+MetodoPago="Tarjeta"
    public String execute(HttpServletRequest request, HttpServletResponse response, String action) {
        String strReturn = "";
        switch (action) {
            case "FIND_FIRST":
                // Implementación para FIND_FIRST si es necesario
                break;
            case "FIND_ALL":
                // Pago pago = new Pago();
                // pago.setID_Pago("P01");
                // pago.setMetodoPago("Tarjeta");
                // strReturn = findAll(pago);
                strReturn = findAll();
                break;
            default:
                strReturn = "ERROR. Invalid Action";
        }
        return strReturn;
    }

    private String findAll() {
        PagoDao pagoDao = new PagoDao();
        // ArrayList<Pago> pagos = pagoDao.findAll(pago);
        ArrayList<Pago> pagos = pagoDao.findAll(null);
        return Pago.toArrayJSon(pagos);
    }
}
