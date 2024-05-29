package Action;

import Model.DAO.ClienteDAO;
import Model.Entities.Cliente;
import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class ClienteAction implements IAction {
    private Gson gson = new Gson();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String action) {
        String strReturn = "";
        switch (action) {
            case "FIND_FIRST":
                break;
            case "FIND_ALL":
                strReturn = findAll();
                break;
            case "LOGIN":
                strReturn = login(request, response);
                break;
            case "REGISTER":
                strReturn = register(request, response);
                break;
            default:
                strReturn = "{\"message\": \"ERROR. Invalid Action\"}";
        }
        return strReturn;
    }

    private String findAll() {
        ClienteDAO clienteDao = new ClienteDAO();
        ArrayList<Cliente> clientes = clienteDao.findAll(null);
        return Cliente.toArrayJSon(clientes);
    }

    private String login(HttpServletRequest request, HttpServletResponse response) {
        String emailOrUsername = request.getParameter("usernameEmail");
        String password = request.getParameter("password");

        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.login(emailOrUsername, password);

        if (cliente != null) {
            return gson.toJson(cliente);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "{\"message\": \"ERROR. Credenciales incorrectas.\"}";
        }
    }

    private String register(HttpServletRequest request, HttpServletResponse response) {
        Cliente newCliente = new Cliente(
                request.getParameter("idCliente"),
                request.getParameter("nombre"),
                request.getParameter("direccion"),
                request.getParameter("email"),
                request.getParameter("telefono"),
                request.getParameter("fechaRegistro"),
                request.getParameter("nombreUsuario"),
                request.getParameter("contraseña")
        );

        ClienteDAO clienteDao = new ClienteDAO();
        int result = clienteDao.add(newCliente);
        if (result > 0) {
            return gson.toJson(newCliente);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "{\"message\": \"ERROR. Registration Failed\"}";
        }
    }
}
