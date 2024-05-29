package Model.DAO;

import Model.Entities.CategoriaProducto;
import Model.MotorOracle.MotorOracle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoriaProductoDao implements IDao<CategoriaProducto, String> {
    private final String SQL_FIND_ALL = "SELECT * FROM CATEGORIAPRODUCTO WHERE 1=1 ";

    @Override
    public int add(CategoriaProducto bean) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int delete(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int update(CategoriaProducto bean) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<CategoriaProducto> findAll(CategoriaProducto bean) {
        ArrayList<CategoriaProducto> categoriasProducto = new ArrayList<>();
        MotorOracle motor = new MotorOracle();
        try {
            motor.connect();
            String sql = SQL_FIND_ALL;
            if (bean != null) {
                if (((CategoriaProducto)bean).getID_Categoria() != null) {
                    sql += " AND ID_CATEGORIA='" + ((CategoriaProducto)bean).getID_Categoria() + "'";
                }
                if (((CategoriaProducto)bean).getNombre() != null) {
                    sql += " AND NOMBRE='" + ((CategoriaProducto)bean).getNombre() + "'";
                }
            }
            ResultSet rs = motor.executeQuery(sql);

            while (rs.next()) {
                CategoriaProducto categoriaProducto = new CategoriaProducto();
                categoriaProducto.setID_Categoria(rs.getString("ID_CATEGORIA"));
                categoriaProducto.setNombre(rs.getString("NOMBRE"));

                categoriasProducto.add(categoriaProducto);
            }

        } catch (Exception ex) {
            categoriasProducto.clear();
        } finally {
            try {
                motor.disconnect();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return categoriasProducto;
    }
}

