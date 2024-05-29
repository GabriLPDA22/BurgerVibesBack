package Action;

import Model.DAO.DetallesPedidoDao;
import Model.Entities.DetallesPedido;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DetallesPedidoAction implements IAction {
    @Override
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
        DetallesPedidoDao detallesPedidoDao = new DetallesPedidoDao();
        ArrayList<DetallesPedido> detallesPedidos = detallesPedidoDao.findAll(null);
        return DetallesPedido.toArrayJSon(detallesPedidos);
    }

    private String add(HttpServletRequest request, HttpServletResponse response) {
        DetallesPedidoDao detallesPedidoDao = new DetallesPedidoDao();
        DetallesPedido detalle = new DetallesPedido();

        detalle.setID_Pedido_det(request.getParameter("idPedido"));
        detalle.setID_Producto_det(request.getParameter("idProductoDet"));
        detalle.setTotalPedido(Double.parseDouble(request.getParameter("totalPedido")));
        detalle.setNombre(request.getParameter("nombre"));
        detalle.setTelefono(request.getParameter("telefono"));
        detalle.setEmail(request.getParameter("email"));
        detalle.setDireccion(request.getParameter("direccion"));

        // Obtener la fecha actual formateada
        Date Fecha = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat.format(Fecha);

        detalle.setHora_Entrega(currentDateTime); // Usar la fecha formateada

        detalle.setNota(request.getParameter("nota"));
        detalle.setCod_promocional(request.getParameter("codPromocional"));

        int result = detallesPedidoDao.add(detalle);
        if (result > 0) {
            return "{\"message\": \"Detalle del pedido agregado con éxito\"}";
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "{\"message\": \"Error al agregar el detalle del pedido\"}";
        }
    }
}
