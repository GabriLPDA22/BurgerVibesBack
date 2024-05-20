package Action;

import Model.DAO.PedidoDao;
import Model.Entities.Pedido;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class PedidoAction implements IAction {
    @Override
    //ACTION=PEDIDO.FIND_ALL+ID_Pedido="P01"+TipoEntrega="Entrega1"
    public String execute(HttpServletRequest request, HttpServletResponse response, String action) {
        String strReturn = "";
        switch (action) {
            case "FIND_FIRST":
                // Implementación para FIND_FIRST si es necesario
                break;
            case "FIND_ALL":
                // Pedido pedido = new Pedido();
                // pedido.setID_Pedido("P01");
                // pedido.setTipoEntrega("Entrega1");
                // strReturn = findAll(pedido);
                strReturn = findAll();
                break;
            default:
                strReturn = "ERROR. Invalid Action";
        }
        return strReturn;
    }

    private String findAll() {
        PedidoDao pedidoDao = new PedidoDao();
        // ArrayList<Pedido> pedidos = pedidoDao.findAll(pedido);
        ArrayList<Pedido> pedidos = pedidoDao.findAll(null);
        return Pedido.toArrayJSon(pedidos);
    }
}
