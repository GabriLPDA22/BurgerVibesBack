/*package Controller.Actions;

import Model.Entities.Cliente;
import Model.DAO.ClienteDAO;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class ClienteAction implements IAction {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String action) {
        String strReturn = "";
        switch (action) {
            case "FIND_FIRST":
                // Implementación para FIND_FIRST si es necesario
                break;
            case "FIND_ALL":
                // Puedes agregar filtros de búsqueda basados en parámetros del request
                Cliente cliente = new Cliente();
                // Ejemplo de cómo establecer filtros basados en parámetros del request
                // cliente.setID_Cliente(request.getParameter("ID_Cliente"));
                // cliente.setNombre(request.getParameter("Nombre"));
                // Puedes añadir más filtros según sea necesario
                strReturn = findAll(cliente);
                break;
            default:
                strReturn = "ERROR. Invalid Action";
        }
        return strReturn;
    }

    private String findAll(Cliente cliente) {
        ClienteDAO clienteDao = new ClienteDAO();
        ArrayList<Cliente> clientes = clienteDao.findAll(cliente);
        return Cliente.toArrayJSon(clientes);
    }
}*/
