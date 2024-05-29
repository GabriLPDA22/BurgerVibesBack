package Action;

import Model.DAO.ProductoDao;
import Model.Entities.Producto;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class ProductoAction implements IAction {
    @Override
    //ACTION=PRODUCTO.FIND_ALL+ID_Producto="P01"+Nombre="Producto1"
    public String execute(HttpServletRequest request, HttpServletResponse response, String action) {
        String strReturn = "";
        switch (action) {
            case "ADD":
                strReturn = add(request);
                break;
            case "UPDATE":
                strReturn = update(request);
                break;
            case "DELETE":
                strReturn = delete(request);
                break;
            case "FIND_ALL":
                strReturn = findAll();
                break;
            default:
                strReturn = "ERROR. Invalid Action";
        }
        return strReturn;
    }

    private String add(HttpServletRequest request) {
        String idProducto = request.getParameter("ID_PRODUCTO");
        String nombre = request.getParameter("NOMBRE");
        String precioStr = request.getParameter("PRECIO");
        String descripcion = request.getParameter("DESCRIPCION");
        String disponibleEnVlc = request.getParameter("DISPONIBLEENVLC");
        String disponibleEnZgz = request.getParameter("DISPONIBLEENZGZ");
        String idCategoriaPro = request.getParameter("ID_CATEGORIA_PRO");
        String Producto_IMG = request.getParameter("PRODUCTO_IMG");

        if (idProducto != null && !idProducto.isEmpty() &&
                nombre != null && !nombre.isEmpty() &&
                precioStr != null && !precioStr.isEmpty() &&
                descripcion != null && !descripcion.isEmpty() &&
                disponibleEnVlc != null && !disponibleEnVlc.isEmpty() &&
                disponibleEnZgz != null && !disponibleEnZgz.isEmpty() &&
                Producto_IMG != null && !Producto_IMG.isEmpty() &&
                idCategoriaPro != null && !idCategoriaPro.isEmpty()) {

            Producto producto = new Producto();
            producto.setID_Producto(idProducto);
            producto.setNombre(nombre);
            try {
                double precio = Double.parseDouble(precioStr);
                producto.setPrecio(precio);
            } catch (NumberFormatException e) {
                return "Error. El precio debe ser un número válido.";
            }
            producto.setDescripcion(descripcion);
            producto.setDisponibleEnVlc(disponibleEnVlc);
            producto.setDisponibleEnZgz(disponibleEnZgz);
            producto.setID_Categoria_pro(idCategoriaPro);

            try {
                ProductoDao productoDao = new ProductoDao();
                int result = productoDao.add(producto);
                if (result > 0) {
                    return "Producto añadido con éxito";
                } else {
                    return "Error. No se pudo añadir el producto";
                }
            } catch (Exception e) {
                return "Error. Ocurrió una excepción al intentar añadir el producto: " + e.getMessage();
            }
        } else {
            return "Error. Se deben proporcionar todos los datos del producto";
        }
    }

    private String delete(HttpServletRequest request) {
        String idReq = request.getParameter("ID_PRODUCTO");
        if (idReq != null && !idReq.isEmpty()) {
            try {
                ProductoDao productoDao = new ProductoDao();
                int result = productoDao.delete(idReq);
                if (result > 0) {
                    return "Producto eliminado con éxito";
                } else {
                    return "Error. No se pudo eliminar el producto";
                }
            } catch (Exception e) {
                return "ERROR. Ocurrió una excepción al intentar eliminar el producto: " + e.getMessage();
            }
        }
        return "ERROR. ID_PRODUCTO no proporcionado";
    }

    private String findAll() {
        ProductoDao productoDao = new ProductoDao();
        ArrayList<Producto> productos = productoDao.findAll(null);
        return Producto.toArrayJSon(productos);
    }


    public String update(HttpServletRequest request) {
        String idProducto = request.getParameter("ID_PRODUCTO");
        String nombre = request.getParameter("NOMBRE");
        String precioStr = request.getParameter("PRECIO");
        String descripcion = request.getParameter("DESCRIPCION");
        String disponibleEnVlc = request.getParameter("DISPONIBLEENVLC");
        String disponibleEnZgz = request.getParameter("DISPONIBLEENZGZ");
        String idCategoriaPro = request.getParameter("ID_CATEGORIA_PRO");
        String Producto_IMG = request.getParameter("PRODUCTO_IMG");

        if (idProducto != null && !idProducto.isEmpty()) {
            Producto producto = new Producto();
            producto.setID_Producto(idProducto);
            if (nombre != null && !nombre.isEmpty()) producto.setNombre(nombre);
            if (precioStr != null && !precioStr.isEmpty()) {
                try {
                    int precio = Integer.parseInt(precioStr);
                    producto.setPrecio(precio);
                } catch (NumberFormatException e) {
                    return "Error. El precio debe ser un número válido.";
                }
            }
            if (descripcion != null && !descripcion.isEmpty()) producto.setDescripcion(descripcion);
            if (disponibleEnVlc != null && !disponibleEnVlc.isEmpty()) producto.setDisponibleEnVlc(disponibleEnVlc);
            if (disponibleEnZgz != null && !disponibleEnZgz.isEmpty()) producto.setDisponibleEnZgz(disponibleEnZgz);
            if (idCategoriaPro != null && !idCategoriaPro.isEmpty()) producto.setID_Categoria_pro(idCategoriaPro);

            try {
                ProductoDao productoDao = new ProductoDao();
                int result = productoDao.update(producto);
                if (result > 0) {
                    return "Producto actualizado con éxito";
                } else {
                    return "Error. No se pudo actualizar el producto";
                }
            } catch (Exception e) {
                return "Error. Ocurrió una excepción al intentar actualizar el producto: " + e.getMessage();
            }
        } else {
            return "Error. Se debe proporcionar el ID del producto";
        }
    }

}