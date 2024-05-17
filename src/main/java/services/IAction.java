package services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Interfaz base para las acciones
 */
public interface IAction {
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response, String action);
}

