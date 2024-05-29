package Action;

import Model.DAO.PedidoDao;
import Model.Entities.DetallesPedido;
import Model.Entities.Pedido;
import Model.Entities.Pago;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "PedidoAction", urlPatterns = {"/api/pedido"})
public class PedidoAction implements IAction {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String action) {
        switch (action.toUpperCase()) {
            case "ADD":
                return add(request, response);
            case "FIND_ALL":
                return findAll();
            default:
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return "{\"message\": \"Acción no válida\"}";
        }
    }

    private String add(HttpServletRequest request, HttpServletResponse response) {
        try {
            PedidoDao pedidoDao = new PedidoDao();
            Pedido pedido = new Pedido();

            String fullName = request.getParameter("fullName");
            String phoneNumber = request.getParameter("phoneNumber");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            String pickupTime = request.getParameter("pickupTime");
            String restaurantNote = request.getParameter("restaurantNote");
            String promoCode = request.getParameter("promoCode");
            String country = request.getParameter("country");
            String idCliente = request.getParameter("idCliente");
            String idEmpleado = request.getParameter("idEmpleado");

            // Obtener la fecha y hora actuales formateadas
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date());

            pedido.setTipoEntrega("PICKUP");
            pedido.setFecha(currentDateTime);
            pedido.setID_Cliente_ped(idCliente);
            pedido.setID_Empleado_ped(idEmpleado);

            ArrayList<DetallesPedido> detalles = new ArrayList<>();
            String itemsJson = request.getParameter("items");

            if (itemsJson != null) {
                try {
                    Type itemListType = new TypeToken<List<DetallesPedido>>() {}.getType();
                    detalles = new Gson().fromJson(itemsJson, itemListType);
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return "{\"message\": \"Error al procesar los items\"}";
                }
            }

            for (DetallesPedido detalle : detalles) {
                detalle.setNombre(fullName);
                detalle.setTelefono(phoneNumber);
                detalle.setEmail(email);
                detalle.setDireccion(address);
                detalle.setHora_Entrega(pickupTime);
                detalle.setNota(restaurantNote);
                detalle.setCod_promocional(promoCode);
                detalle.setTotalPedido(Double.parseDouble(request.getParameter("totalPedido")));
            }

            pedido.setDetalles(detalles);

            Pago pago = new Pago();
            pago.setMetodoPago("CARD");
            pago.setPais(country);
            pedido.setPago(pago);

            int idPedido = pedidoDao.addPedido(pedido);

            return "{\"message\": \"Pedido realizado con éxito\", \"idPedido\": \"" + idPedido + "\"}";
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "{\"message\": \"Error al realizar el pedido\"}";
        }
    }

    private String findAll() {
        PedidoDao pedidoDao = new PedidoDao();
        ArrayList<Pedido> pedidos = pedidoDao.findAll(null);
        return Pedido.toArrayJSon(pedidos);
    }
}
