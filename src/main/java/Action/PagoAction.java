package Action;

import Model.DAO.PagoDao;
import Model.Entities.Pago;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;

public class PagoAction implements IAction {
    @Override
    // ACTION=PAGO.FIND_ALL+ID_Pago="P01"+MetodoPago="CARD"
    public String execute(HttpServletRequest request, HttpServletResponse response, String action) {
        String strReturn = "";
        switch (action.toUpperCase()) {
            case "FIND_ALL":
                strReturn = findAll();
                break;
            case "ADD":
                strReturn = add(request, response);
                break;
            default:
                strReturn = "ERROR. Acción no válida";
        }
        return strReturn;
    }

    private String findAll() {
        PagoDao pagoDao = new PagoDao();
        ArrayList<Pago> pagos = pagoDao.findAll(null);
        return Pago.toArrayJSon(pagos);
    }

    private String add(HttpServletRequest request, HttpServletResponse response) {
        PagoDao pagoDao = new PagoDao();
        Pago pago = new Pago();

        pago.setMetodoPago(request.getParameter("metodoPago"));
        pago.setID_Pedido_pag(request.getParameter("idPedido"));
        pago.setPais(request.getParameter("pais"));

        int result = pagoDao.add(pago);
        if (result > 0) {
            return "{\"message\": \"Pago agregado con éxito\"}";
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "{\"message\": \"Error al agregar el pago\"}";
        }
    }
}
