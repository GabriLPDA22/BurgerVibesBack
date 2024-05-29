package Controller;

import Action.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "Controller", urlPatterns = {"/Controller"})
public class Controller extends HttpServlet {

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Max-Age", "3600");

        PrintWriter out = response.getWriter();
        String strAction = request.getParameter("ACTION");

        if (strAction == null || strAction.equals("")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"message\": \"Acción no proporcionada. Asegúrate de que el parámetro 'ACTION' está presente en la solicitud.\"}");
            return;
        }

        String[] arrayAction = strAction.split("\\."); // [0] ENTITY <-> [1] ACTION
        if (arrayAction.length < 2) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"message\": \"Formato de acción inválido. La acción debe estar en el formato 'ENTITY.ACTION'.\"}");
            return;
        }

        try {
            switch (arrayAction[0].toUpperCase()) {
                case "CLIENTE":
                    out.print(new ClienteAction().execute(request, response, arrayAction[1]));
                    break;
                case "CATEGORIAPRODUCTO":
                    out.print(new CategoriaProductoAction().execute(request, response, arrayAction[1]));
                    break;
                case "ZONAPRIVADA":
                    out.print(new ZonaPrivadaAction().execute(request, response, arrayAction[1]));
                    break;
                case "PRODUCTO":
                    out.print(new ProductoAction().execute(request, response, arrayAction[1]));
                    break;
                case "EMPLEADO":
                    out.print(new EmpleadoAction().execute(request, response, arrayAction[1]));
                    break;
                case "PEDIDO":
                    out.print(new PedidoAction().execute(request, response, arrayAction[1]));
                    break;
                case "PAGO":
                    out.print(new PagoAction().execute(request, response, arrayAction[1]));
                    break;
                case "DETALLESPEDIDO":
                    out.print(new DetallesPedidoAction().execute(request, response, arrayAction[1]));
                    break;
                default:
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.write("{\"message\": \"Acción " + arrayAction[0] + " no válida\"}");
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"message\": \"Error al procesar la solicitud\"}");
        } finally {
            out.close();
        }

        System.out.println(strAction);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
