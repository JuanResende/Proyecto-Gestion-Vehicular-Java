package service;

import java.sql.SQLException;
import java.util.List;

public interface GenericService<T> {
    void insertar(T t) throws SQLException;
    T getById(Long id) throws SQLException;
    List<T> getAll() throws SQLException;
    void actualizar(T t) throws SQLException;
    void eliminar(Long id) throws SQLException;
}