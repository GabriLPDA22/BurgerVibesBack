package Model.Entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;

public class DetallesPedido {
    private String ID_Detalles;
    private int Cantidad;
    private double PrecioUnitario;
    private String ID_Pedido_det;
    private String ID_Producto_det;

    public DetallesPedido() {
    }

    public DetallesPedido(String ID_Detalles, int Cantidad, double PrecioUnitario, String ID_Pedido_det, String ID_Producto_det) {
        this.ID_Detalles = ID_Detalles;
        this.Cantidad = Cantidad;
        this.PrecioUnitario = PrecioUnitario;
        this.ID_Pedido_det = ID_Pedido_det;
        this.ID_Producto_det = ID_Producto_det;
    }

    // Getters & Setters
    public String getID_Detalles() {
        return ID_Detalles;
    }

    public void setID_Detalles(String ID_Detalles) {
        this.ID_Detalles = ID_Detalles;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    public double getPrecioUnitario() {
        return PrecioUnitario;
    }

    public void setPrecioUnitario(double PrecioUnitario) {
        this.PrecioUnitario = PrecioUnitario;
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

    @Override
    public String toString() {
        return "DetallesPedido{" +
                "ID_Detalles='" + ID_Detalles + '\'' +
                ", Cantidad=" + Cantidad +
                ", PrecioUnitario=" + PrecioUnitario +
                ", ID_Pedido_det='" + ID_Pedido_det + '\'' +
                ", ID_Producto_det='" + ID_Producto_det + '\'' +
                '}';
    }

    public static String fromArrayToJson(ArrayList<DetallesPedido> detallesPedidos) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(detallesPedidos);
    }

    public static String toArrayJSon(ArrayList<DetallesPedido> detallesPedidos) {
        StringBuilder resp = new StringBuilder("[");
        for (DetallesPedido detalle : detallesPedidos) {
            resp.append("{")
                    .append("'ID_Detalles':'").append(detalle.getID_Detalles()).append("', ")
                    .append("'Cantidad':").append(detalle.getCantidad()).append(", ")
                    .append("'PrecioUnitario':").append(detalle.getPrecioUnitario()).append(", ")
                    .append("'ID_Pedido_det':'").append(detalle.getID_Pedido_det()).append("', ")
                    .append("'ID_Producto_det':'").append(detalle.getID_Producto_det()).append("'")
                    .append("},");
        }
        if (resp.length() > 1) {
            resp = new StringBuilder(resp.substring(0, resp.length() - 1));
        }
        resp.append("]");
        return resp.toString();
    }
}
