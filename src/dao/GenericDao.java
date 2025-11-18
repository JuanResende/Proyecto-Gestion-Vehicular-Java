package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T> {
    void crear(Connection conn, T entidad) throws SQLException;
    T leer(Connection conn, Long id) throws SQLException;
    List<T> leerTodos(Connection conn) throws SQLException;
    void actualizar(Connection conn, T entidad) throws SQLException;
    void eliminar(Connection conn, Long id) throws SQLException;
}
