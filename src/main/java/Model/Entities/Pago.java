package Model.Entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class Pago {
    private String ID_Pago;
    private String MetodoPago;
    private String ID_Pedido_pag;
    private String Pais; // Añadido para guardar el país del pago

    public Pago() {
    }

    public Pago(String ID_Pago, String MetodoPago, String ID_Pedido_pag, String Pais) {
        this.ID_Pago = ID_Pago;
        this.MetodoPago = MetodoPago;
        this.ID_Pedido_pag = ID_Pedido_pag;
        this.Pais = Pais;
    }

    // GETTERS & SETTERS
    public String getID_Pago() {
        return ID_Pago;
    }

    public void setID_Pago(String ID_Pago) {
        this.ID_Pago = ID_Pago;
    }

    public String getMetodoPago() {
        return MetodoPago;
    }

    public void setMetodoPago(String MetodoPago) {
        this.MetodoPago = MetodoPago;
    }

    public String getID_Pedido_pag() {
        return ID_Pedido_pag;
    }

    public void setID_Pedido_pag(String ID_Pedido_pag) {
        this.ID_Pedido_pag = ID_Pedido_pag;
    }

    public String getPais() {
        return Pais;
    }

    public void setPais(String Pais) {
        this.Pais = Pais;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "ID_Pago='" + ID_Pago + '\'' +
                ", MetodoPago='" + MetodoPago + '\'' +
                ", ID_Pedido_pag='" + ID_Pedido_pag + '\'' +
                ", Pais='" + Pais + '\'' +
                '}';
    }

    public String toJson() {
        return "{" +
                "'ID_Pago':'" + ID_Pago + '\'' +
                ", 'MetodoPago':'" + MetodoPago + '\'' +
                ", 'ID_Pedido_pag':'" + ID_Pedido_pag + '\'' +
                ", 'Pais':'" + Pais + '\'' +
                '}';
    }

    public static String fromArrayToJson(ArrayList<Pago> pagos) {
        StringBuilder resp = new StringBuilder("[");
        for (Pago pago : pagos) {
            resp.append("{")
                    .append("'ID_Pago':'").append(pago.getID_Pago()).append("', ")
                    .append("'MetodoPago':'").append(pago.getMetodoPago()).append("', ")
                    .append("'ID_Pedido_pag':'").append(pago.getID_Pedido_pag()).append("', ")
                    .append("'Pais':'").append(pago.getPais()).append("'")
                    .append("},");
        }
        if (resp.length() > 1) {
            resp = new StringBuilder(resp.substring(0, resp.length() - 1));
        }
        resp.append("]");
        return resp.toString();
    }

    public static String toArrayJSon(ArrayList<Pago> pagos) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(pagos);
    }
}
