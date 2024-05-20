package Action;

import Model.DAO.EmpleadoDAO;
import Model.Entities.Empleado;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class EmpleadoAction implements IAction {
    @Override
    // ACTION=EMPLEADO.FIND_ALL+ID_Empleado="1"+Nombre="Juan"
    public String execute(HttpServletRequest request, HttpServletResponse response, String action) {
        String strReturn = "";
        switch (action) {
            case "FIND_FIRST":
                // Implementación para FIND_FIRST si es necesario
                break;
            case "FIND_ALL":
                strReturn = findAll();
                break;
            case "DELETE":
                String nombre = request.getParameter("NOMBRE");
                strReturn = delete(nombre);
                break;
            default:
                strReturn = "ERROR. Invalid Action";
        }
        return strReturn;
    }

    private String findAll() {
        EmpleadoDAO empleadoDao = new EmpleadoDAO();
        ArrayList<Empleado> empleados = empleadoDao.findAll(null);
        return Empleado.toArrayJSon(empleados);
    }

    private String delete(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            return "ERROR. NOMBRE is required.";
        }

        EmpleadoDAO empleadoDao = new EmpleadoDAO();
        String rowsDeleted = String.valueOf(Integer.parseInt(String.valueOf(empleadoDao.delete(Integer.valueOf(String.join(nombre))))));

        if (false) {
            return "Empleado with NOMBRE=" + nombre + " deleted successfully.";
        } else {
            return "ERROR. No Empleado found with NOMBRE=" + nombre + ".";
        }
    }
}