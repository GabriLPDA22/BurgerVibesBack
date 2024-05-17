package model.entities;

public class Empleado {

    private int ID_Empleado;
    private String Nombre;
    private String Apellidos;
    private String Direccion;
    private String Cargo;
    private String Email;
    private String Telefono;
    private int ID_ZonaPrivada;

    public Empleado() {
        // Constructor predeterminado
    }

    public Empleado(int ID_Empleado, String Nombre, String Apellidos, String Direccion, String Cargo, String Email, String Telefono, int ID_ZonaPrivada) {
        this.ID_Empleado = ID_Empleado;
        this.Nombre = Nombre;
        this.Apellidos = Apellidos;
        this.Direccion = Direccion;
        this.Cargo = Cargo;
        this.Email = Email;
        this.Telefono = Telefono;
        this.ID_ZonaPrivada = ID_ZonaPrivada;
    }

    public int getID_Empleado() {
        return ID_Empleado;
    }

    public void setID_Empleado(int ID_Empleado) {
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

    public int getID_ZonaPrivada() {
        return ID_ZonaPrivada;
    }

    public void setID_ZonaPrivada(int ID_ZonaPrivada) {
        this.ID_ZonaPrivada = ID_ZonaPrivada;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "ID_Empleado=" + ID_Empleado +
                ", Nombre='" + Nombre + '\'' +
                ", Apellidos='" + Apellidos + '\'' +
                ", Direccion='" + Direccion + '\'' +
                ", Cargo='" + Cargo + '\'' +
                ", Email='" + Email + '\'' +
                ", Telefono='" + Telefono + '\'' +
                ", ID_ZonaPrivada=" + ID_ZonaPrivada +
                '}';
    }
}
