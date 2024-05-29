package Action;

import Model.DAO.EmpleadoDAO;
import Model.Entities.Empleado;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmpleadoAction implements IAction {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String action) throws SQLException {
        String strReturn = "";
        switch (action.toUpperCase()) {
            case "LOGIN":
                strReturn = login(request, response);
                break;
            case "FIND_ALL":
                strReturn = findAll();
                break;
            case "FIND_BY_EMAIL_AND_PASSWORD":
                strReturn = findByEmailAndPassword(request, response);
                break;
            case "AUTHENTICATE":
                strReturn = authenticate(request, response);
                break;
            case "DELETE":
                strReturn = delete(request);
                break;
            case "ADD":
                strReturn = add(request);
                break;
            case "INFO":
                strReturn = info(request, response);
                break;
            case "UPDATE":
                strReturn = update(request);
                break;
            default:
                strReturn = "ERROR. Invalid Action";
        }
        return strReturn;
    }
    private String info(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");

        if (email != null && !email.isEmpty()) {
            EmpleadoDAO empleadoDAO = new EmpleadoDAO();
            Empleado empleado = empleadoDAO.findByEmail(email);

            if (empleado != null) {
                return String.format(
                        "{\"nombre\": \"%s\", \"apellidos\": \"%s\", \"cargo\": \"%s\", \"email\": \"%s\", \"telefono\": \"%s\", \"direccion\": \"%s\"}",
                        empleado.getNombre(), empleado.getApellidos(), empleado.getCargo(), empleado.getEmail(), empleado.getTelefono(), empleado.getDireccion()
                );
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return "{\"message\": \"Empleado no encontrado\"}";
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "{\"message\": \"Email no proporcionado\"}";
        }
    }
    private String login(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        EmpleadoDAO empleadoDAO = new EmpleadoDAO();
        Empleado empleado = empleadoDAO.authenticate(email, password);

        if (empleado != null) {
            String zoneId = empleado.getID_ZonaPrivada_EMP();
            String cargo = empleado.getCargo();

            if ("A001".equals(zoneId)) {
                return "{\"message\": \"Inicio de sesión exitoso\", \"role\": \"admin\", \"email\": \"" + empleado.getEmail() + "\"}";
            } else if ("E001".equals(zoneId)) {
                return "{\"message\": \"Inicio de sesión exitoso\", \"role\": \"employee\", \"email\": \"" + empleado.getEmail() + "\", \"cargo\": \"" + cargo + "\"}";
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return "{\"message\": \"Credenciales incorrectas\"}";
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "{\"message\": \"Credenciales incorrectas\"}";
        }
    }
    private String authenticate(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        EmpleadoDAO empleadoDAO = new EmpleadoDAO();
        Empleado empleado = empleadoDAO.authenticate(email, password);

        if (empleado != null) {
            return "Autenticación exitosa";
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "Credenciales incorrectas";
        }
    }
    private String findByEmailAndPassword(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        EmpleadoDAO empleadoDAO = new EmpleadoDAO();
        final Empleado empleado = empleadoDAO.findByEmailAndPassword(email, password);

        if (empleado != null) {
            return Empleado.toArrayJSon(new ArrayList<Empleado>() {{ add(empleado); }});
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "Empleado no encontrado";
        }
    }
    private String delete(HttpServletRequest request) {
        String idReq = request.getParameter("ID_EMPLEADO");
        if (idReq != null && !idReq.isEmpty()) {
            try {
                EmpleadoDAO empleadoDAO = new EmpleadoDAO();
                int result = empleadoDAO.delete(idReq);
                if (result > 0) {
                    return "Empleado eliminado con éxito";
                } else {
                    return "Error. No se pudo eliminar el empleado";
                }
            } catch (Exception e) {
                return "ERROR. Ocurrió una excepción al intentar eliminar el empleado: " + e.getMessage();
            }
        }
        return "ERROR. ID_EMPLEADO no proporcionado";
    }

    private String findAll() {
        EmpleadoDAO empleadoDao = new EmpleadoDAO();
        ArrayList<Empleado> empleados = empleadoDao.findAll(null);
        return Empleado.toArrayJSon(empleados);
    }

    public String add(HttpServletRequest request) {
        String idEmpleado = request.getParameter("ID_EMPLEADO");
        String nombre = request.getParameter("NOMBRE");
        String apellidos = request.getParameter("APELLIDOS");
        String direccion = request.getParameter("DIRECCION");
        String cargo = request.getParameter("CARGO");
        String email = request.getParameter("EMAIL");
        String telefono = request.getParameter("TELEFONO");
        String idZonaPrivada_EMP = request.getParameter("ID_ZONAPRIVADA_EMP");
        String contrasena = request.getParameter("CONTRASENA");

        if (idEmpleado != null && !idEmpleado.isEmpty() &&
                nombre != null && !nombre.isEmpty() &&
                apellidos != null && !apellidos.isEmpty() &&
                direccion != null && !direccion.isEmpty() &&
                cargo != null && !cargo.isEmpty() &&
                email != null && !email.isEmpty() &&
                telefono != null && !telefono.isEmpty() &&
                idZonaPrivada_EMP != null && !idZonaPrivada_EMP.isEmpty() &&
                contrasena != null && !contrasena.isEmpty()) {

            Empleado nuevoEmpleado = new Empleado(idEmpleado, nombre, apellidos, direccion, cargo, email, telefono, idZonaPrivada_EMP, contrasena);
            try {
                EmpleadoDAO empleadoDAO = new EmpleadoDAO();
                int result = empleadoDAO.add(nuevoEmpleado);
                if (result > 0) {
                    return "Empleado agregado con éxito";
                } else {
                    return "Error. No se pudo agregar el empleado";
                }
            } catch (Exception e) {
                return "Error. Ocurrió una excepción al intentar agregar el empleado: " + e.getMessage();
            }
        } else {
            return "Error. Se deben proporcionar todos los datos del empleado";
        }
    }

    public String update(HttpServletRequest request) {
        String idEmpleado = request.getParameter("ID_EMPLEADO");
        String nombre = request.getParameter("NOMBRE");
        String apellidos = request.getParameter("APELLIDOS");
        String direccion = request.getParameter("DIRECCION");
        String cargo = request.getParameter("CARGO");
        String email = request.getParameter("EMAIL");
        String telefono = request.getParameter("TELEFONO");
        String idZonaPrivada_EMP = request.getParameter("ID_ZONAPRIVADA_EMP");
        String contrasena = request.getParameter("CONTRASENA");

        if (idEmpleado != null && !idEmpleado.isEmpty()) {
            Empleado empleado = new Empleado();
            empleado.setID_Empleado(idEmpleado);
            if (nombre != null && !nombre.isEmpty()) empleado.setNombre(nombre);
            if (apellidos != null && !apellidos.isEmpty()) empleado.setApellidos(apellidos);
            if (direccion != null && !direccion.isEmpty()) empleado.setDireccion(direccion);
            if (cargo != null && !cargo.isEmpty()) empleado.setCargo(cargo);
            if (email != null && !email.isEmpty()) empleado.setEmail(email);
            if (telefono != null && !telefono.isEmpty()) empleado.setTelefono(telefono);
            if (idZonaPrivada_EMP != null && !idZonaPrivada_EMP.isEmpty()) empleado.setID_ZonaPrivada_EMP(idZonaPrivada_EMP);
            if (contrasena != null && !contrasena.isEmpty()) empleado.setContrasena(contrasena);

            try {
                EmpleadoDAO empleadoDAO = new EmpleadoDAO();
                int result = empleadoDAO.update(empleado);
                if (result > 0) {
                    return "Empleado actualizado con éxito";
                } else {
                    return "Error. No se pudo actualizar el empleado";
                }
            } catch (Exception e) {
                return "Error. Ocurrió una excepción al intentar actualizar el empleado: " + e.getMessage();
            }
        } else {
            return "Error. Se debe proporcionar el ID del empleado";
        }
    }
}
