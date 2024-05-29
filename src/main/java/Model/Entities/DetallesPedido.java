package Model.Entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DetallesPedido {
    private String ID_Detalles;
    private String ID_Pedido_det;
    private String ID_Producto_det;
    private double TotalPedido;
    private String Nombre;
    private String Telefono;
    private String Email;
    private String Direccion;
    private String Hora_Entrega;
    private String Nota;
    private String Cod_promocional;

    public DetallesPedido() {
        this.Hora_Entrega = getCurrentDateTime(); // Asignar fecha actual al crear un nuevo detalle
    }

    public DetallesPedido(String ID_Detalles, String ID_Pedido_det, String ID_Producto_det, double TotalPedido, String Nombre, String Telefono, String Email, String Direccion, String Hora_Entrega, String Nota, String Cod_promocional) {
        this.ID_Detalles = ID_Detalles;
        this.ID_Pedido_det = ID_Pedido_det;
        this.ID_Producto_det = ID_Producto_det;
        this.TotalPedido = TotalPedido;
        this.Nombre = Nombre;
        this.Telefono = Telefono;
        this.Email = Email;
        this.Direccion = Direccion;
        this.Hora_Entrega = Hora_Entrega;
        this.Nota = Nota;
        this.Cod_promocional = Cod_promocional;
    }

    // Getters & Setters
    public String getID_Detalles() {
        return ID_Detalles;
    }

    public void setID_Detalles(String ID_Detalles) {
        this.ID_Detalles = ID_Detalles;
    }

    public String getID_Pedido_det() {
        return ID_Pedido_det;
    }

    public void setID_Pedido_det(String ID_Pedido_det) {
        this.ID_Pedido_det = ID_Pedido_det;
    }

    public String getID_Producto_det() {
        return ID_Producto_det;
    }

    public void setID_Producto_det(String ID_Producto_det) {
        this.ID_Producto_det = ID_Producto_det;
    }

    public double getTotalPedido() {
        return TotalPedido;
    }

    public void setTotalPedido(double TotalPedido) {
        this.TotalPedido = TotalPedido;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public String getHora_Entrega() {
        return Hora_Entrega;
    }

    public void setHora_Entrega(String Hora_Entrega) {
        this.Hora_Entrega = Hora_Entrega;
    }

    public String getNota() {
        return Nota;
    }

    public void setNota(String Nota) {
        this.Nota = Nota;
    }

    public String getCod_promocional() {
        return Cod_promocional;
    }

    public void setCod_promocional(String Cod_promocional) {
        this.Cod_promocional = Cod_promocional;
    }

    private String getCurrentDateTime() {
        Date Fecha = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(Fecha);
    }

    @Override
    public String toString() {
        return "DetallesPedido{" +
                "ID_Detalles='" + ID_Detalles + '\'' +
                ", ID_Pedido_det='" + ID_Pedido_det + '\'' +
                ", ID_Producto_det='" + ID_Producto_det + '\'' +
                ", TotalPedido=" + TotalPedido +
                ", Nombre='" + Nombre + '\'' +
                ", Telefono='" + Telefono + '\'' +
                ", Email='" + Email + '\'' +
                ", Direccion='" + Direccion + '\'' +
                ", Hora_Entrega='" + Hora_Entrega + '\'' +
                ", Nota='" + Nota + '\'' +
                ", Cod_promocional='" + Cod_promocional + '\'' +
                '}';
    }

    public String toJson() {
        return "{" +
                "'ID_Detalles':'" + ID_Detalles + '\'' +
                ", 'ID_Pedido_det':'" + ID_Pedido_det + '\'' +
                ", 'ID_Producto_det':'" + ID_Producto_det + '\'' +
                ", 'TotalPedido':" + TotalPedido +
                ", 'Nombre':'" + Nombre + '\'' +
                ", 'Telefono':'" + Telefono + '\'' +
                ", 'Email':'" + Email + '\'' +
                ", 'Direccion':'" + Direccion + '\'' +
                ", 'Hora_Entrega':'" + Hora_Entrega + '\'' +
                ", 'Nota':'" + Nota + '\'' +
                ", 'Cod_promocional':'" + Cod_promocional + '\'' +
                '}';
    }

    public static String fromArrayToJson(ArrayList<DetallesPedido> detallesPedidos) {
        StringBuilder resp = new StringBuilder("[");
        for (DetallesPedido detalle : detallesPedidos) {
            resp.append(detalle.toJson()).append(",");
        }
        if (resp.length() > 1) {
            resp = new StringBuilder(resp.substring(0, resp.length() - 1));
        }
        resp.append("]");
        return resp.toString();
    }

    public static String toArrayJSon(ArrayList<DetallesPedido> detallesPedidos) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(detallesPedidos);
    }
}
