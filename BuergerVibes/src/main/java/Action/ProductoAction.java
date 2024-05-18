package Action;

import Model.DAO.ProductoDao;
import Model.Entities.Producto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class ProductoAction implements IAction {
    @Override
    //ACTION=PRODUCTO.FIND_ALL+ID_Producto="P01"+Nombre="Producto1"
    public String execute(HttpServletRequest request, HttpServletResponse response, String action) {
        String strReturn = "";
        switch (action) {
            case "FIND_FIRST":
                // Implementación para FIND_FIRST si es necesario
                break;
            case "FIND_ALL":
                // Producto producto = new Producto();
                // producto.setID_Producto("P01");
                // producto.setNombre("Producto1");
                // strReturn = findAll(producto);
                strReturn = findAll();
                break;
            default:
                strReturn = "ERROR. Invalid Action";
        }
        return strReturn;
    }

    private String findAll() {
        ProductoDao productoDao = new ProductoDao();
        // ArrayList<Producto> productos = productoDao.findAll(producto);
        ArrayList<Producto> productos = productoDao.findAll(null);
        return Producto.toArrayJSon(productos);
    }
}
