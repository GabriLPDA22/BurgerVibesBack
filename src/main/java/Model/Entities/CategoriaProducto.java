package Model.Entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class CategoriaProducto {
    private String ID_Categoria;
    private String Nombre;

    public CategoriaProducto() {
    }

    public CategoriaProducto(String ID_Categoria, String Nombre) {
        this.ID_Categoria = ID_Categoria;
        this.Nombre = Nombre;
    }

    // GETTERS & SETTERS
    public String getID_Categoria() {
        return ID_Categoria;
    }

    public void setID_Categoria(String ID_Categoria) {
        this.ID_Categoria = ID_Categoria;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    @Override
    public String toString() {
        return "CategoriaProducto{" +
                "ID_Categoria='" + ID_Categoria + '\'' +
                ", Nombre='" + Nombre + '\'' +
                '}';
    }

    public static String fromArrayToJson(ArrayList<CategoriaProducto> categorias) {
        StringBuilder resp = new StringBuilder("[");
        for (CategoriaProducto categoria : categorias) {
            resp.append("{")
                    .append("'ID_Categoria':'").append(categoria.getID_Categoria()).append("', ")
                    .append("'Nombre':'").append(categoria.getNombre()).append("'")
                    .append("},");
        }
        if (resp.length() > 1) {
            resp = new StringBuilder(resp.substring(0, resp.length() - 1));
        }
        resp.append("]");
        return resp.toString();
    }

    public static String toArrayJSon(ArrayList<CategoriaProducto> categorias) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(categorias);
    }
}

