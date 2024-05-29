package Model.Entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
public class Pedido {
    private String ID_Pedido;
    private String Fecha;
    private String TipoEntrega;
    private String Cantidad;
    private String ID_Cliente_ped;
    private String ID_Empleado_ped;
    private ArrayList<DetallesPedido> Detalles;
    private Pago Pago;

    public Pedido() {
        Detalles = new ArrayList<>();
        this.Fecha = getCurrentDateTime(); // Asignar fecha actual al crear un nuevo pedido
    }

    public Pedido(String ID_Pedido, String Fecha, String TipoEntrega,String Cantidad, String ID_Cliente_ped, String ID_Empleado_ped, ArrayList<DetallesPedido> Detalles, Pago Pago) {
        this.ID_Pedido = ID_Pedido;
        this.Fecha = Fecha;
        this.TipoEntrega = TipoEntrega;
        this.Cantidad = Cantidad;
        this.ID_Cliente_ped = ID_Cliente_ped;
        this.ID_Empleado_ped = ID_Empleado_ped;
        this.Detalles = Detalles;
        this.Pago = Pago;
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

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String Cantidad) {
        this.Cantidad = Cantidad;
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

    public ArrayList<DetallesPedido> getDetalles() {
        return Detalles;
    }

    public void setDetalles(ArrayList<DetallesPedido> detalles) {
        this.Detalles = detalles;
    }

    public Pago getPago() {
        return Pago;
    }

    public void setPago(Pago pago) {
        this.Pago = pago;
    }

    private String getCurrentDateTime() {
        Date Fecha = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(Fecha);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "ID_Pedido='" + ID_Pedido + '\'' +
                ", Fecha='" + Fecha + '\'' +
                ", TipoEntrega='" + TipoEntrega + '\'' +
                ", Cantidad='" + Cantidad + '\'' +
                ", ID_Cliente_ped='" + ID_Cliente_ped + '\'' +
                ", ID_Empleado_ped='" + ID_Empleado_ped + '\'' +
                ", Detalles=" + Detalles +
                ", Pago=" + Pago +
                '}';
    }

    public static String fromArrayToJson(ArrayList<Pedido> pedidos) {
        StringBuilder resp = new StringBuilder("[");
        for (Pedido pedido : pedidos) {
            resp.append("{")
                    .append("'ID_Pedido':'").append(pedido.getID_Pedido()).append("', ")
                    .append("'Fecha':'").append(pedido.getFecha()).append("', ")
                    .append("'TipoEntrega':'").append(pedido.getTipoEntrega()).append("', ")
                    .append("'Cantidad':'").append(pedido.getCantidad()).append("', ")
                    .append("'ID_Cliente_ped':'").append(pedido.getID_Cliente_ped()).append("', ")
                    .append("'ID_Empleado_ped':'").append(pedido.getID_Empleado_ped()).append("', ")
                    .append("'Detalles':").append(DetallesPedido.fromArrayToJson(pedido.getDetalles())).append(", ")
                    .append("'Pago':").append(pedido.getPago().toJson())
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
