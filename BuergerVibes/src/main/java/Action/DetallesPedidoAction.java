package Action;

import Model.DAO.DetallesPedidoDao;
import Model.Entities.DetallesPedido;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class DetallesPedidoAction implements IAction {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String action) {
        String strReturn = "";
        switch (action) {
            case "FIND_FIRST":
                // Implementación para FIND_FIRST si es necesario
                break;
            case "FIND_ALL":
                strReturn = findAll();
                break;
            default:
                strReturn = "ERROR. Invalid Action";
        }
        return strReturn;
    }

    private String findAll() {
        DetallesPedidoDao detallesPedidoDao = new DetallesPedidoDao();
        ArrayList<DetallesPedido> detallesPedidos = detallesPedidoDao.findAll(null);
        return DetallesPedido.toArrayJSon(detallesPedidos);
    }
}
