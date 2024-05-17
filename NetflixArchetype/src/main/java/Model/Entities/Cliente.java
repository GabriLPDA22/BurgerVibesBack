package Model.Entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;

public class Cliente {

    private String ID_Cliente;
    private String Nombre;
    private String Direccion;
    private String Email;
    private String Telefono;
    private String FechaRegistro;

    public Cliente() {
        // Constructor predeterminado
    }

    public Cliente(String ID_Cliente, String Nombre, String Direccion, String Email, String Telefono, String FechaRegistro) {
        this.ID_Cliente = ID_Cliente;
        this.Nombre = Nombre;
        this.Direccion = Direccion;
        this.Email = Email;
        this.Telefono = Telefono;
        this.FechaRegistro = FechaRegistro;
    }

    public static String toArrayJSon(ArrayList<Cliente> clientes) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(clientes);
    }

    // GETTERS & SETTERS
    public String getID_Cliente() {
        return ID_Cliente;
    }

    public void setID_Cliente(String ID_Cliente) {
        this.ID_Cliente = ID_Cliente;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getFechaRegistro() {
        return FechaRegistro;
    }

    public void setFechaRegistro(String FechaRegistro) {
        this.FechaRegistro = FechaRegistro;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "ID_Cliente='" + ID_Cliente + '\'' +
                ", Nombre='" + Nombre + '\'' +
                ", Direccion='" + Direccion + '\'' +
                ", Email='" + Email + '\'' +
                ", Telefono='" + Telefono + '\'' +
                ", FechaRegistro='" + FechaRegistro + '\'' +
                '}';
    }

    }


   /* public static String toArrayJSon(ArrayList<Cliente> clientes) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        return gson.toJson(clientes);*/
