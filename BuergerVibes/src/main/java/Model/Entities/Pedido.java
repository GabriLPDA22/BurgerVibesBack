package Model.Entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class Pedido {
    private String ID_Pedido;
    private String Fecha;  // Cambiado a String para simplificación
    private String TipoEntrega;
    private String EstadoPedido;
    private String ID_Cliente_ped;
    private String ID_Empleado_ped;

    public Pedido() {
    }

    public Pedido(String ID_Pedido, String Fecha, String TipoEntrega, String EstadoPedido, String ID_Cliente_ped, String ID_Empleado_ped) {
        this.ID_Pedido = ID_Pedido;
        this.Fecha = Fecha;
        this.TipoEntrega = TipoEntrega;
        this.EstadoPedido = EstadoPedido;
        this.ID_Cliente_ped = ID_Cliente_ped;
        this.ID_Empleado_ped = ID_Empleado_ped;
    }

    // GETTERS & SETTERS
    public String getID_Pedido() {
        return ID_Pedido;
    }

    public void setID_Pedido(String ID_Pedido) {
        this.ID_Pedido = ID_Pedido;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public String getTipoEntrega() {
        return TipoEntrega;
    }

    public void setTipoEntrega(String TipoEntrega) {
        this.TipoEntrega = TipoEntrega;
    }

    public String getEstadoPedido() {
        return EstadoPedido;
    }

    public void setEstadoPedido(String EstadoPedido) {
        this.EstadoPedido = EstadoPedido;
    }

    public String getID_Cliente_ped() {
        return ID_Cliente_ped;
    }

    public void setID_Cliente_ped(String ID_Cliente_ped) {
        this.ID_Cliente_ped = ID_Cliente_ped;
    }

    public String getID_Empleado_ped() {
        return ID_Empleado_ped;
    }

    public void setID_Empleado_ped(String ID_Empleado_ped) {
        this.ID_Empleado_ped = ID_Empleado_ped;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "ID_Pedido='" + ID_Pedido + '\'' +
                ", Fecha='" + Fecha + '\'' +
                ", TipoEntrega='" + TipoEntrega + '\'' +
                ", EstadoPedido='" + EstadoPedido + '\'' +
                ", ID_Cliente_ped='" + ID_Cliente_ped + '\'' +
                ", ID_Empleado_ped='" + ID_Empleado_ped + '\'' +
                '}';
    }

    public static String fromArrayToJson(ArrayList<Pedido> pedidos) {
        StringBuilder resp = new StringBuilder("[");
        for (Pedido pedido : pedidos) {
            resp.append("{")
                    .append("'ID_Pedido':'").append(pedido.getID_Pedido()).append("', ")
                    .append("'Fecha':'").append(pedido.getFecha()).append("', ")
                    .append("'TipoEntrega':'").append(pedido.getTipoEntrega()).append("', ")
                    .append("'EstadoPedido':'").append(pedido.getEstadoPedido()).append("', ")
                    .append("'ID_Cliente_ped':'").append(pedido.getID_Cliente_ped()).append("', ")
                    .append("'ID_Empleado_ped':'").append(pedido.getID_Empleado_ped()).append("'")
                    .append("},");
        }
        if (resp.length() > 1) {
            resp = new StringBuilder(resp.substring(0, resp.length() - 1));
        }
        resp.append("]");
        return resp.toString();
    }

    public static String toArrayJSon(ArrayList<Pedido> pedidos) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(pedidos);
    }
}
