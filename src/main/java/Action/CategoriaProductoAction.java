package Action;
import Model.Entities.CategoriaProducto;
import Model.DAO.CategoriaProductoDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class CategoriaProductoAction implements IAction {
    @Override
    //ACTION=CATEGORIAPRODUCTO.FIND_ALL+ID_Categoria="ENT01"+Nombre="Entrantes"
    public String execute(HttpServletRequest request, HttpServletResponse response, String action) {
        String strReturn = "";
        switch (action) {
            case "FIND_FIRST":
                // Implementación para FIND_FIRST si es necesario
                break;
            case "FIND_ALL":
                // CategoriaProducto categoria = new CategoriaProducto();
                // categoria.setID_Categoria("ENT01");
                // categoria.setNombre("Entrantes");
                // strReturn = findAll(categoria);
                strReturn = findAll();
                break;
            default:
                strReturn = "ERROR. Invalid Action";
        }
        return strReturn;
    }

    private String findAll() {
        CategoriaProductoDao categoriaProductoDao = new CategoriaProductoDao();
        // ArrayList<CategoriaProducto> categorias = categoriaProductoDao.findAll(categoria);
        ArrayList<CategoriaProducto> categoria = categoriaProductoDao.findAll(null);
        return CategoriaProducto.toArrayJSon(categoria);
    }
}
