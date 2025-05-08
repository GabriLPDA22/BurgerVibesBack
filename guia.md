# BuergerVibes - Guía para desarrolladores

## Introducción

Hola compañero desarrollador! Si estás leyendo esto, probablemente hayas recibido el código fuente de nuestro proyecto BuergerVibes y necesitas entender cómo funciona para poder trabajar con él. No te preocupes, he creado esta guía paso a paso para explicarte cómo está estructurado el proyecto y cómo implementar nuevas funcionalidades.

Este documento te mostrará cómo funciona todo usando una de nuestras entidades existentes (Cliente) como ejemplo.

## Lo que necesitas para empezar

- Java JDK 22
- Maven 
- Algún IDE (recomiendo IntelliJ IDEA o Eclipse)
- Un servidor Tomcat para desplegar la aplicación

## Estructura del Proyecto

Nuestro proyecto sigue el patrón MVC (Modelo-Vista-Controlador) y está organizado de la siguiente manera:

```
BuergerVibes/
├── Controller.java      # Nuestro servlet principal que maneja todas las peticiones
├── Actions/             # Clases que procesan acciones específicas
├── Model/
│   ├── Entities/        # Objetos que representan tablas de la BD
│   ├── DAO/             # Objetos de acceso a datos
│   └── MotorOracle/     # Nuestra conexión a la BD Oracle
```

## El Corazón del Sistema: MotorOracle

Primero, vamos a ver nuestro `MotorOracle.java`. Esta clase es la encargada de conectarse a la base de datos:

```java
public class MotorOracle {
    private static final String URL = "jdbc:oracle:thin:@burgervibesbbdd.ceotvomboedr.us-east-1.rds.amazonaws.com:1521:orcl";
    private static final String USER = "admin";
    private static final String PASSWORD = "123456789";
    private Connection conn;

    // Método para conectarse a la BD
    public void connect() throws SQLException {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Oracle JDBC Driver not found", e);
        }
    }

    // Método para desconectarse
    public void disconnect() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    // Métodos para ejecutar consultas
    // ...
}
```

Es importante que sepas que esta clase ya está configurada para conectarse a nuestra base de datos en AWS. Si necesitas cambiar esto para un entorno local o de desarrollo, tendrás que modificar las constantes URL, USER y PASSWORD.

## Entendiendo el Flujo con la Entidad Cliente

Voy a explicarte cómo funciona todo el sistema usando la entidad Cliente que ya tenemos implementada.

### Paso 1: La Entidad (Cliente.java)

En primer lugar, tenemos la clase Cliente que representa los datos de un cliente en nuestra base de datos:

```java
public class Cliente {
    // Atributos - Estos corresponden a las columnas en la tabla CLIENTE
    private String ID_Cliente;
    private String Nombre;
    private String Direccion;
    private String Email;
    private String Telefono;
    private String FechaRegistro;
    private String Contraseña;
    private String NombreUsuario;

    // Constructor vacío
    public Cliente() { }

    // Constructor con parámetros
    public Cliente(String ID_Cliente, String Nombre, String Direccion, String Email, 
                 String Telefono, String FechaRegistro, String NombreUsuario, String Contraseña) {
        this.ID_Cliente = ID_Cliente;
        this.Nombre = Nombre;
        // Inicialización de los demás atributos...
    }

    // Getters y setters...

    // Método estático para convertir ArrayList a JSON
    public static String toArrayJSon(ArrayList<Cliente> clientes) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(clientes);
    }
}
```

Como puedes ver, esta clase es bastante simple: atributos privados, constructores, getters/setters y un método para convertir a JSON. Cada entidad en nuestro sistema sigue este mismo patrón.

### Paso 2: El DAO (ClienteDAO.java)

El DAO (Data Access Object) es el encargado de interactuar con la base de datos:

```java
public class ClienteDAO implements IDao<Cliente, String> {
    // Consultas SQL predefinidas
    private final String SQL_FIND_ALL = "SELECT * FROM CLIENTE WHERE 1=1 ";
    private final String SQL_LOGIN = "SELECT * FROM CLIENTE WHERE (EMAIL = ? OR NOMBRE_USUARIO = ?) AND CONTRASEÑA = ?";
    private final String SQL_REGISTER = "INSERT INTO CLIENTE (ID_CLIENTE, NOMBRE, DIRECCION, EMAIL, TELEFONO, FECHAREGISTRO, NOMBRE_USUARIO, CONTRASEÑA) VALUES (?, ?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?)";
    
    // Método para registrar un cliente
    @Override
    public int add(Cliente bean) {
        MotorOracle motor = new MotorOracle();
        try {
            motor.connect();
            PreparedStatement ps = motor.preparedStatement(SQL_REGISTER);
            ps.setString(1, bean.getID_Cliente());
            ps.setString(2, bean.getNombre());
            // Configurar el resto de parámetros...
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            try {
                motor.disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para autenticar un cliente
    public Cliente login(String emailOrUsername, String password) {
        Cliente cliente = null;
        MotorOracle motor = new MotorOracle();
        try {
            motor.connect();
            PreparedStatement ps = motor.preparedStatement(SQL_LOGIN);
            ps.setString(1, emailOrUsername);
            ps.setString(2, emailOrUsername);
            ps.setString(3, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cliente = new Cliente();
                cliente.setID_Cliente(rs.getString("ID_CLIENTE"));
                cliente.setNombre(rs.getString("NOMBRE"));
                // Configurar el resto de atributos...
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                motor.disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cliente;
    }

    // Método para obtener todos los clientes
    @Override
    public ArrayList<Cliente> findAll(Cliente bean) {
        ArrayList<Cliente> clientes = new ArrayList<>();
        MotorOracle motor = new MotorOracle();
        try {
            motor.connect();
            String sql = SQL_FIND_ALL;
            
            // Añadir filtros si se proporciona un bean no nulo
            if (bean != null) {
                if (bean.getID_Cliente() != null && !bean.getID_Cliente().isEmpty()) {
                    sql += " AND ID_CLIENTE='" + bean.getID_Cliente() + "'";
                }
                // Más filtros...
            }
            
            ResultSet rs = motor.executeQuery(sql);

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setID_Cliente(rs.getString("ID_CLIENTE"));
                cliente.setNombre(rs.getString("NOMBRE"));
                // Configurar el resto de atributos...
                clientes.add(cliente);
            }
        } catch (Exception ex) {
            clientes.clear();
            ex.printStackTrace();
        } finally {
            try {
                motor.disconnect();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return clientes;
    }
    
    // Otros métodos requeridos por la interfaz IDao...
}
```

El DAO sigue un patrón común:

1. Define consultas SQL como constantes
2. Implementa métodos para operaciones CRUD
3. Usa el MotorOracle para conectarse a la BD
4. En cada método: Conecta -> Ejecuta consulta -> Procesa resultados -> Desconecta

### Paso 3: La Clase Action (ClienteAction.java)

La clase Action es el puente entre las peticiones HTTP y los métodos del DAO:

```java
public class ClienteAction implements IAction {
    private Gson gson = new Gson();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String action) {
        String strReturn = "";
        switch (action) {
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

    // Método para obtener todos los clientes
    private String findAll() {
        ClienteDAO clienteDao = new ClienteDAO();
        ArrayList<Cliente> clientes = clienteDao.findAll(null);
        return Cliente.toArrayJSon(clientes);
    }

    // Método para autenticar un cliente
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

    // Método para registrar un cliente
    private String register(HttpServletRequest request, HttpServletResponse response) {
        Cliente newCliente = new Cliente(
                request.getParameter("idCliente"),
                request.getParameter("nombre"),
                // Obtener resto de parámetros...
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
```

La clase Action:

1. Implementa la interfaz IAction que define el método execute
2. El método execute decide qué acción realizar según el parámetro recibido
3. Cada acción específica tiene su propio método privado
4. Estos métodos extraen parámetros de la petición, llaman al DAO y devuelven respuestas JSON

### Paso 4: El Controlador (Controller.java)

Finalmente, tenemos el Controller, que es un servlet que recibe todas las peticiones HTTP. Este es el punto de entrada de nuestra aplicación:

```java
package Controller;

import Action.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

// Esta anotación registra este servlet para que responda a peticiones en /Controller
@WebServlet(name = "Controller", urlPatterns = {"/Controller"})
public class Controller extends HttpServlet {

    // Este método maneja las peticiones OPTIONS para CORS (importante para aplicaciones web modernas)
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Permitir peticiones desde cualquier origen
        response.setHeader("Access-Control-Allow-Origin", "*");
        // Permitir varios métodos HTTP
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        // Permitir ciertos headers
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    // Este es el método principal que procesa todas las peticiones
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        // Configurar el tipo de contenido de la respuesta como JSON con UTF-8
        response.setContentType("application/json;charset=UTF-8");
        // Configuración completa de CORS para aplicaciones web
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Max-Age", "3600");

        // Obtener el writer para enviar la respuesta
        PrintWriter out = response.getWriter();
        
        // Obtener el parámetro ACTION de la petición
        // Ejemplo: CLIENTE.LOGIN, PRODUCTO.FIND_ALL, etc.
        String strAction = request.getParameter("ACTION");

        // Si no se proporcionó ACTION, devolver error 400
        if (strAction == null || strAction.equals("")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"message\": \"Acción no proporcionada. Asegúrate de que el parámetro 'ACTION' está presente en la solicitud.\"}");
            return;
        }

        // Dividir ACTION en entidad y operación
        // Ej: "CLIENTE.LOGIN" -> arrayAction[0] = "CLIENTE", arrayAction[1] = "LOGIN"
        String[] arrayAction = strAction.split("\\.");
        
        // Verificar que ACTION tiene formato correcto (ENTIDAD.OPERACION)
        if (arrayAction.length < 2) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"message\": \"Formato de acción inválido. La acción debe estar en el formato 'ENTIDAD.OPERACION'.\"}");
            return;
        }

        try {
            // Según la entidad, crear la clase Action correspondiente y llamar a su método execute
            switch (arrayAction[0].toUpperCase()) {
                case "CLIENTE":
                    // Para CLIENTE.LOGIN, crea ClienteAction y llama a execute("LOGIN")
                    out.print(new ClienteAction().execute(request, response, arrayAction[1]));
                    break;
                case "CATEGORIAPRODUCTO":
                    out.print(new CategoriaProductoAction().execute(request, response, arrayAction[1]));
                    break;
                case "ZONAPRIVADA":
                    out.print(new ZonaPrivadaAction().execute(request, response, arrayAction[1]));
                    break;
                case "PRODUCTO":
                    out.print(new ProductoAction().execute(request, response, arrayAction[1]));
                    break;
                case "EMPLEADO":
                    out.print(new EmpleadoAction().execute(request, response, arrayAction[1]));
                    break;
                case "PEDIDO":
                    out.print(new PedidoAction().execute(request, response, arrayAction[1]));
                    break;
                case "PAGO":
                    out.print(new PagoAction().execute(request, response, arrayAction[1]));
                    break;
                case "DETALLESPEDIDO":
                    out.print(new DetallesPedidoAction().execute(request, response, arrayAction[1]));
                    break;
                default:
                    // Si la entidad no es válida, devolver error 400
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.write("{\"message\": \"Acción " + arrayAction[0] + " no válida\"}");
                    break;
            }
        } catch (SQLException e) {
            // En caso de error de base de datos, devolver error 500
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"message\": \"Error al procesar la solicitud\"}");
        } finally {
            // Siempre cerrar el writer
            out.close();
        }

        // Útil para depuración - imprime la acción en el log del servidor
        System.out.println(strAction);
    }

    // Implementación de métodos HTTP que simplemente llaman a processRequest
    // Esto permite que nuestro controlador responda a diferentes tipos de peticiones HTTP
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
```

El Controller:

1. Recibe todas las peticiones HTTP mediante los métodos doGet, doPost, etc.
2. Todos estos métodos llaman a processRequest
3. processRequest extrae el parámetro ACTION
4. Divide ACTION en entidad y operación (ej: "CLIENTE.LOGIN")
5. Según la entidad, crea la clase Action correspondiente y le pasa la operación

## Ejemplos de Uso

Ahora te mostraré algunos ejemplos de cómo se usan estos endpoints:

### Obtener Todos los Clientes

**URL:** 
```
http://localhost:8080/BuergerVibes/Controller?ACTION=CLIENTE.FIND_ALL
```

**Respuesta:**
```json
[
  {
    "ID_Cliente": "C001",
    "Nombre": "Juan Pérez",
    "Direccion": "Calle Principal 123",
    "Email": "juan@example.com",
    "Telefono": "123456789",
    "FechaRegistro": "2023-01-15",
    "NombreUsuario": "juanp",
    "Contraseña": "********"
  },
  // Más clientes...
]
```

### Login de Cliente

**URL:**
```
http://localhost:8080/BuergerVibes/Controller?ACTION=CLIENTE.LOGIN&usernameEmail=juan@example.com&password=contraseña123
```

**Respuesta (éxito):**
```json
{
  "ID_Cliente": "C001",
  "Nombre": "Juan Pérez",
  "Direccion": "Calle Principal 123",
  "Email": "juan@example.com",
  "Telefono": "123456789",
  "FechaRegistro": "2023-01-15",
  "NombreUsuario": "juanp",
  "Contraseña": "********"
}
```

**Respuesta (error):**
```json
{
  "message": "ERROR. Credenciales incorrectas."
}
```

### Registro de Cliente

**URL:**
```
http://localhost:8080/BuergerVibes/Controller?ACTION=CLIENTE.REGISTER&idCliente=C003&nombre=Ana López&direccion=Plaza Mayor 789&email=ana@example.com&telefono=654789123&fechaRegistro=2023-05-10&nombreUsuario=anal&contraseña=secreto123
```

**Respuesta (éxito):**
```json
{
  "ID_Cliente": "C003",
  "Nombre": "Ana López",
  "Direccion": "Plaza Mayor 789",
  "Email": "ana@example.com",
  "Telefono": "654789123",
  "FechaRegistro": "2023-05-10",
  "NombreUsuario": "anal",
  "Contraseña": "secreto123"
}
```

## Cómo Añadir una Nueva Entidad

Si necesitas crear una nueva entidad, sigue estos pasos:

1. **Crea la clase Entidad**:
   - Crea una nueva clase en el paquete `Model.Entities`
   - Define atributos privados que correspondan a columnas de la tabla
   - Añade constructores, getters, setters y métodos para JSON

2. **Crea el DAO**:
   - Crea una nueva clase en el paquete `Model.DAO` que implemente `IDao<TuEntidad, String>`
   - Define consultas SQL como constantes
   - Implementa métodos para operaciones CRUD
   - Usa MotorOracle para conectarte a la BD

3. **Crea la clase Action**:
   - Crea una nueva clase en el paquete `Action` que implemente `IAction`
   - Implementa el método `execute` con un switch para diferentes operaciones
   - Crea métodos privados para cada operación

4. **Actualiza el Controller**:
   - Añade un nuevo caso en el switch del Controller para tu nueva entidad
   - El caso debe crear una instancia de tu nueva clase Action

## Conclusión

Ahora deberías tener una buena comprensión de cómo funciona nuestro proyecto BuergerVibes. Recuerda que todas las entidades siguen el mismo patrón, así que una vez que entiendes una, entiendes todas.

Si tienes alguna duda o problema, no dudes en preguntarme. ¡Buena suerte con el desarrollo! 😉
