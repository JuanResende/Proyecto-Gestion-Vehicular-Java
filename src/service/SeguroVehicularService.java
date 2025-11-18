package service;

import config.databaseConnection;
import dao.SeguroVehicularDao;
import entities.SeguroVehicular;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SeguroVehicularService implements GenericService<SeguroVehicular> {

    private SeguroVehicularDao seguroDao;

    public SeguroVehicularService() {
        this.seguroDao = new SeguroVehicularDao();
    }

    @Override
    public void insertar(SeguroVehicular seguro) throws SQLException {
        Connection conn = null;
        try {
            conn = databaseConnection.getConnection();
            seguroDao.crear(conn, seguro);
        } finally {
            if (conn != null) conn.close();
        }
    }

    @Override
    public SeguroVehicular getById(Long id) throws SQLException {
        Connection conn = null;
        try {
            conn = databaseConnection.getConnection();
            return seguroDao.leer(conn, id);
        } finally {
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<SeguroVehicular> getAll() throws SQLException {
        Connection conn = null;
        try {
            conn = databaseConnection.getConnection();
            return seguroDao.leerTodos(conn);
        } finally {
            if (conn != null) conn.close();
        }
    }

    @Override
    public void actualizar(SeguroVehicular seguro) throws SQLException {
        Connection conn = null;
        try {
            conn = databaseConnection.getConnection();
            conn.setAutoCommit(false);

            seguroDao.actualizar(conn, seguro);

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        Connection conn = null;
        try {
            conn = databaseConnection.getConnection();
            conn.setAutoCommit(false);

            SeguroVehicular seguro = seguroDao.leer(conn, id);
            if (seguro != null) {
                seguro.setEliminado(true);
                seguroDao.actualizar(conn, seguro);
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}