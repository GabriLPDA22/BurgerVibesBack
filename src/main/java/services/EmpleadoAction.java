package services;

/*
 * Acción para gestionar las operaciones relacionadas con Empleados
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.EmpleadoDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class EmpleadoAction implements IAction {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String action) {
        String cadDestino = "";
        switch (action) {
            case "SQL_FIND_ALL":
                cadDestino = findAll(request, response);
                break;
            default:
                cadDestino = "Unknown action: " + action;
        }
        return cadDestino;
    }

    private String findAll(HttpServletRequest request, HttpServletResponse response) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();
        String responseJSON = gson.toJson(new EmpleadoDAO().findAll());
        if (responseJSON != null) {
            return responseJSON;
        }
        return "Error, no hemos encontrado los empleados";
    }
}
