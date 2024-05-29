package Model.Entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class DetallesPago {
    private String ID_DetallesPago;
    private double TotalPagado;
    private String FechaCaducidad;
    private String CVV;
    private String NombreTitular;
    private String ID_Pago_detpag;

    public DetallesPago() {
    }

    public DetallesPago(String ID_DetallesPago, double TotalPagado, String FechaCaducidad, String CVV, String NombreTitular, String ID_Pago_detpag) {
        this.ID_DetallesPago = ID_DetallesPago;
        this.TotalPagado = TotalPagado;
        this.FechaCaducidad = FechaCaducidad;
        this.CVV = CVV;
        this.NombreTitular = NombreTitular;
        this.ID_Pago_detpag = ID_Pago_detpag;
    }

    // GETTERS & SETTERS
    public String getID_DetallesPago() {
        return ID_DetallesPago;
    }

    public void setID_DetallesPago(String ID_DetallesPago) {
        this.ID_DetallesPago = ID_DetallesPago;
    }

    public double getTotalPagado() {
        return TotalPagado;
    }

    public void setTotalPagado(double TotalPagado) {
        this.TotalPagado = TotalPagado;
    }

    public String getFechaCaducidad() {
        return FechaCaducidad;
    }

    public void setFechaCaducidad(String FechaCaducidad) {
        this.FechaCaducidad = FechaCaducidad;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public String getNombreTitular() {
        return NombreTitular;
    }

    public void setNombreTitular(String NombreTitular) {
        this.NombreTitular = NombreTitular;
    }

    public String getID_Pago_detpag() {
        return ID_Pago_detpag;
    }

    public void setID_Pago_detpag(String ID_Pago_detpag) {
        this.ID_Pago_detpag = ID_Pago_detpag;
    }

    @Override
    public String toString() {
        return "DetallesPago{" +
                "ID_DetallesPago='" + ID_DetallesPago + '\'' +
                ", TotalPagado=" + TotalPagado +
                ", FechaCaducidad='" + FechaCaducidad + '\'' +
                ", CVV='" + CVV + '\'' +
                ", NombreTitular='" + NombreTitular + '\'' +
                ", ID_Pago_detpag='" + ID_Pago_detpag + '\'' +
                '}';
    }

    public static String fromArrayToJson(ArrayList<DetallesPago> detallesPagos) {
        StringBuilder resp = new StringBuilder("[");
        for (DetallesPago detallesPago : detallesPagos) {
            resp.append("{")
                    .append("'ID_DetallesPago':'").append(detallesPago.getID_DetallesPago()).append("', ")
                    .append("'TotalPagado':").append(detallesPago.getTotalPagado()).append(", ")
                    .append("'FechaCaducidad':'").append(detallesPago.getFechaCaducidad()).append("', ")
                    .append("'CVV':'").append(detallesPago.getCVV()).append("', ")
                    .append("'NombreTitular':'").append(detallesPago.getNombreTitular()).append("', ")
                    .append("'ID_Pago_detpag':'").append(detallesPago.getID_Pago_detpag()).append("'")
                    .append("},");
        }
        if (resp.length() > 1) {
            resp = new StringBuilder(resp.substring(0, resp.length() - 1));
        }
        resp.append("]");
        return resp.toString();
    }

    public static String toArrayJSon(ArrayList<DetallesPago> detallesPagos) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(detallesPagos);
    }
}
