package Action;

import Model.DAO.ClienteDAO;
import Model.Entities.Cliente;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class ClienteAction implements IAction {
    @Override
    //ACTION=CLIENTE.FIND_ALL+ID_Cliente="C01"+Nombre="Cliente1"
    public String execute(HttpServletRequest request, HttpServletResponse response, String action) {
        String strReturn = "";
        switch (action) {
            case "FIND_FIRST":
                // Implementación para FIND_FIRST si es necesario
                break;
            case "FIND_ALL":
                // Cliente cliente = new Cliente();
                // cliente.setID_Cliente("C01");
                // cliente.setNombre("Cliente1");
                // strReturn = findAll(cliente);
                strReturn = findAll();
                break;
            default:
                strReturn = "ERROR. Invalid Action";
        }
        return strReturn;
    }

    private String findAll() {
        ClienteDAO clienteDao = new ClienteDAO();
        // ArrayList<Cliente> clientes = clienteDao.findAll(cliente);
        ArrayList<Cliente> clientes = clienteDao.findAll(null);
        return Cliente.toArrayJSon(clientes);
    }
}
