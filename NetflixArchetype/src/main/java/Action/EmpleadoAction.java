/*package Controller.Actions;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Model.*;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class EmpleadoAction implements IAction {
    @Override

    public String execute(HttpServletRequest request, HttpServletResponse response, String action) {
        String strReturn ="";
        switch (action)
        {
            case "FIND_FIRST":

                break;
            case "FIND_ALL":

                strReturn = findAll();
                break;
            default:
                strReturn = "ERROR. Invalid Action";
        }
        return strReturn;
    }

    private String findAll(/*Pelicula peli*//* ) {

        EmpleadoDAO empleadoDao = new EmpleadoDAO();

        ArrayList<Empleado> empleados = empleadoDao.findAll(null);
        return Empleado.toArrayJSon(empleados);
    }
}*/
