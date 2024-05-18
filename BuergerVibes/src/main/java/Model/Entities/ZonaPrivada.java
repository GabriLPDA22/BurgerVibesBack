package Model.Entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class ZonaPrivada {
    private String ID_ZonaPrivada;
    private String VerPedidos;
    private String AdministrarReserva;

    public ZonaPrivada() {
    }

    public ZonaPrivada(String ID_ZonaPrivada, String VerPedidos, String AdministrarReserva) {
        this.ID_ZonaPrivada = ID_ZonaPrivada;
        this.VerPedidos = VerPedidos;
        this.AdministrarReserva = AdministrarReserva;
    }

    // GETTERS & SETTERS
    public String getID_ZonaPrivada() {
        return ID_ZonaPrivada;
    }

    public void setID_ZonaPrivada(String ID_ZonaPrivada) {
        this.ID_ZonaPrivada = ID_ZonaPrivada;
    }

    public String getVerPedidos() {
        return VerPedidos;
    }

    public void setVerPedidos(String VerPedidos) {
        this.VerPedidos = VerPedidos;
    }

    public String getAdministrarReserva() {
        return AdministrarReserva;
    }

    public void setAdministrarReserva(String AdministrarReserva) {
        this.AdministrarReserva = AdministrarReserva;
    }

    @Override
    public String toString() {
        return "ZonaPrivada{" +
                "ID_ZonaPrivada='" + ID_ZonaPrivada + '\'' +
                ", VerPedidos='" + VerPedidos + '\'' +
                ", AdministrarReserva='" + AdministrarReserva + '\'' +
                '}';
    }

    public static String fromArrayToJson(ArrayList<ZonaPrivada> zonas) {
        StringBuilder resp = new StringBuilder("[");
        for (ZonaPrivada zona : zonas) {
            resp.append("{")
                    .append("'ID_ZonaPrivada':'").append(zona.getID_ZonaPrivada()).append("', ")
                    .append("'VerPedidos':'").append(zona.getVerPedidos()).append("', ")
                    .append("'AdministrarReserva':'").append(zona.getAdministrarReserva()).append("'")
                    .append("},");
        }
        if (resp.length() > 1) {
            resp = new StringBuilder(resp.substring(0, resp.length() - 1));
        }
        resp.append("]");
        return resp.toString();
    }

    public static String toArrayJSon(ArrayList<ZonaPrivada> zonas) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(zonas);
    }
}
