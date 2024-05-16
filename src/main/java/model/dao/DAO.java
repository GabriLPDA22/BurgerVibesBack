package model.dao;

import java.util.ArrayList;

/*
 * Interfaz base para los DAOs
 */
public interface DAO<E,I>{
    public int add(E bean);
    public int delete(Integer e);
    public int update(E bean);
    public ArrayList<E> findAll(E bean);
}


