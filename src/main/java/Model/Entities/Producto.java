package Model.Entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class Producto {
    private String ID_Producto;
    private double Precio;
    private String Nombre;
    private String Descripcion;
    private String DisponibleEnVlc;
    private String DisponibleEnZgz;
    private String ID_Categoria_pro;
    private String Producto_IMG;

    public Producto() {
    }

    public Producto(String ID_Producto, double Precio, String Nombre, String Descripcion, String DisponibleEnVlc, String DisponibleEnZgz, String ID_Categoria_pro,String Producto_IMG) {
        this.ID_Producto = ID_Producto;
        this.Precio = Precio;
        this.Nombre = Nombre;
        this.Descripcion = Descripcion;
        this.DisponibleEnVlc = DisponibleEnVlc;
        this.DisponibleEnZgz = DisponibleEnZgz;
        this.ID_Categoria_pro = ID_Categoria_pro;
        this.Producto_IMG = Producto_IMG;
    }

    // GETTERS & SETTERS
    public String getID_Producto() {
        return ID_Producto;
    }

    public void setID_Producto(String ID_Producto) {
        this.ID_Producto = ID_Producto;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double Precio) {
        this.Precio = Precio;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getDisponibleEnVlc() {
        return DisponibleEnVlc;
    }

    public void setDisponibleEnVlc(String DisponibleEnVlc) {
        this.DisponibleEnVlc = DisponibleEnVlc;
    }

    public String getDisponibleEnZgz() {
        return DisponibleEnZgz;
    }

    public void setDisponibleEnZgz(String DisponibleEnZgz) {
        this.DisponibleEnZgz = DisponibleEnZgz;
    }

    public String getID_Categoria_pro() {
        return ID_Categoria_pro;
    }
    public void setID_Categoria_pro(String ID_Categoria_pro) {
        this.ID_Categoria_pro = ID_Categoria_pro;
    }

    public void setProducto_IMG(String Producto_IMG) {
        this.Producto_IMG = Producto_IMG;
    }
    public String getProducto_IMG() {
        return Producto_IMG;
    }



    @Override
    public String toString() {
        return "Producto{" +
                "ID_Producto='" + ID_Producto + '\'' +
                ", Precio=" + Precio +
                ", Nombre='" + Nombre + '\'' +
                ", Descripcion='" + Descripcion + '\'' +
                ", Producto_IMG='" + Producto_IMG + '\'' +
                ", DisponibleEnVlc='" + DisponibleEnVlc + '\'' +
                ", DisponibleEnZgz='" + DisponibleEnZgz + '\'' +
                ", ID_Categoria_pro='" + ID_Categoria_pro + '\'' +
                '}';
    }

    public static String fromArrayToJson(ArrayList<Producto> productos) {
        StringBuilder resp = new StringBuilder("[");
        for (Producto producto : productos) {
            resp.append("{")
                    .append("'ID_Producto':'").append(producto.getID_Producto()).append("', ")
                    .append("'Precio':").append(producto.getPrecio()).append(", ")
                    .append("'Nombre':'").append(producto.getNombre()).append("', ")
                    .append("'Descripcion':'").append(producto.getDescripcion()).append("', ")
                    .append("'DisponibleEnVlc':'").append(producto.getDisponibleEnVlc()).append("', ")
                    .append("'DisponibleEnZgz':'").append(producto.getDisponibleEnZgz()).append("', ")
                    .append("'ID_Categoria_pro':'").append(producto.getID_Categoria_pro()).append("'")
                    .append("'Producto_IMG':'").append(producto.getProducto_IMG()).append("'")
                    .append("},");
        }
        if (resp.length() > 1) {
            resp = new StringBuilder(resp.substring(0, resp.length() - 1));
        }
        resp.append("]");
        return resp.toString();
    }

    public static String toArrayJSon(ArrayList<Producto> productos) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(productos);
    }
}
