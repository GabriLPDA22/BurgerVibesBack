package services;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Controller", urlPatterns = {"/Controller"})
public class Controller extends HttpServlet {

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String strAction = request.getParameter("ACTION"); // Debe coincidir con el parámetro en la URL

        if (strAction == null || strAction.isEmpty()) {
            throw new ServletException("No action specified");
        }

        String[] arrayAction = strAction.split("\\.");
        if (arrayAction.length < 2) {
            throw new ServletException("Invalid action format");
        }

        switch (arrayAction[0].toUpperCase()) {
            case "EMPLEADO":
                out.print(new EmpleadoAction().execute(request, response, arrayAction[1]));
                break;
            default:
                System.out.println(arrayAction[0]);
                throw new ServletException("Acción " + arrayAction[0] + " no valida");
        }
        System.out.println(strAction);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
