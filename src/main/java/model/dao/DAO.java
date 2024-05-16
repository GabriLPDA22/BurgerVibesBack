package model.dao;
import model.entities.Empleado;
import java.util.ArrayList;
/*
 * Interfaz base para los DAOs
*/
public interface DAO<E,I>{
    ArrayList<Empleado> findAll();
    public boolean add(E bean);
    public boolean delete(Integer e);
    public int update(E bean);
    public ArrayList<E> findAll(E bean);
}


