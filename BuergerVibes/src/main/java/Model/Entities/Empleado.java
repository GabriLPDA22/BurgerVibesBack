package Model.Entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;


public class Empleado {

    private String ID_Empleado;  // Cambiado a String
    private String Nombre;
    private String Apellidos;
    private String Direccion;
    private String Cargo;
    private String Email;
    private String Telefono;
    private String ID_ZonaPrivada;  // Cambiado a String

    public Empleado() {
        // Constructor predeterminado
    }

    public Empleado(String ID_Empleado, String Nombre, String Apellidos, String Direccion, String Cargo, String Email, String Telefono, String ID_ZonaPrivada) {
        this.ID_Empleado = ID_Empleado;
        this.Nombre = Nombre;
        this.Apellidos = Apellidos;
        this.Direccion = Direccion;
        this.Cargo = Cargo;
        this.Email = Email;
        this.Telefono = Telefono;
        this.ID_ZonaPrivada = ID_ZonaPrivada;
    }

    public static String toArrayJSon(ArrayList<Empleado> Empleado) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(Empleado);
    }

    public String getID_Empleado() {
        return ID_Empleado;
    }

    public void setID_Empleado(String ID_Empleado) {
        this.ID_Empleado = ID_Empleado;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String Apellidos) {
        this.Apellidos = Apellidos;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public String getCargo() {
        return Cargo;
    }

    public void setCargo(String Cargo) {
        this.Cargo = Cargo;
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

    public String getID_ZonaPrivada() {
        return ID_ZonaPrivada;
    }

    public void setID_ZonaPrivada(String ID_ZonaPrivada) {
        this.ID_ZonaPrivada = ID_ZonaPrivada;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "ID_Empleado='" + ID_Empleado + "'" +
        ", Nombre='" + Nombre + "'" +
        ", Apellidos='" + Apellidos + "'" +
        ", Direccion='" + Direccion + "'" +
        ", Cargo='" + Cargo + "'" +
        ", Email='" + Email + "'" +
        ", Telefono='" + Telefono + "'" +
        ", ID_ZonaPrivada='" + ID_ZonaPrivada + "'" +
        '}';
    }
}