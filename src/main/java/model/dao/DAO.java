package model.dao;
import model.entities.Empleado;
import java.util.ArrayList;
/*
 * Interfaz base para los DAOs
 */
public interface DAO<E, I> {
    ArrayList<E> findAll();
    boolean add(E bean);
    boolean delete(I id);
    int update(E bean);
    // Eliminado el método duplicado findAll(E bean)
}