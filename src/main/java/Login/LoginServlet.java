/*package Login;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import Model.Entities.Empleado;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MotorOracle motorOracle = new MotorOracle();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        JSONObject jsonResponse = new JSONObject();

        StringBuilder jb = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        }

        try {
            JSONObject jsonRequest = new JSONObject(jb.toString());
            String email = jsonRequest.getString("email");
            String password = jsonRequest.getString("password");

            motorOracle.connect();
            String query = "SELECT * FROM Empleado WHERE Email = '" + email + "' AND Password = '" + password + "'";
            try (ResultSet rs = motorOracle.executeQuery(query)) {
                if (rs.next()) {
                    Empleado empleado = new Empleado(
                            rs.getString("ID_Empleado"),
                            rs.getString("Nombre"),
                            rs.getString("Apellidos"),
                            rs.getString("Direccion"),
                            rs.getString("Cargo"),
                            rs.getString("Email"),
                            rs.getString("Telefono"),
                            rs.getString("ID_ZonaPrivada"),
                            rs.getString("CONTRASEÑA")
                    );
                    jsonResponse.put("success", true);

                    if ("admin".equalsIgnoreCase(empleado.getCargo())) {
                        jsonResponse.put("redirectUrl", "zona_privada.html");
                    } else if ("empleado".equalsIgnoreCase(empleado.getCargo())) {
                        jsonResponse.put("redirectUrl", "zona_privada_empleados.html");
                    }
                } else {
                    jsonResponse.put("success", false);
                }
            }
            motorOracle.disconnect();
        } catch (SQLException | JSONException e) {
            e.printStackTrace();
            jsonResponse.put("success", false);
        }

        response.getWriter().write(jsonResponse.toString());
    }
}*/

