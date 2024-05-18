package Action;

import Model.DAO.EmpleadoDAO;
import Model.Entities.Empleado;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class EmpleadoAction implements IAction {
    @Override
    //ACTION=EMPLEADO.FIND_ALL+ID_Empleado="1"+Nombre="Juan"
    public String execute(HttpServletRequest request, HttpServletResponse response, String action) {
        String strReturn = "";
        switch (action) {
            case "FIND_FIRST":
                // Implementación para FIND_FIRST si es necesario
                break;
            case "FIND_ALL":
                // Empleado empleado = new Empleado();
                // empleado.setID_Empleado(1);
                // empleado.setNombre("Juan");
                // strReturn = findAll(empleado);
                strReturn = findAll();
                break;
            default:
                strReturn = "ERROR. Invalid Action";
        }
        return strReturn;
    }

    private String findAll() {
        EmpleadoDAO empleadoDao = new EmpleadoDAO();
        // ArrayList<Empleado> empleados = empleadoDao.findAll(empleado);
        ArrayList<Empleado> empleados = empleadoDao.findAll(null);
        return Empleado.toArrayJSon(empleados);
    }
}
