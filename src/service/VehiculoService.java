package service;

import config.databaseConnection;
import dao.VehiculoDao;
import dao.SeguroVehicularDao;
import entities.Vehiculo;
import entities.SeguroVehicular;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehiculoService {

    private VehiculoDao vehiculoDao;
    private SeguroVehicularDao seguroDao;

    public VehiculoService() {
        this.vehiculoDao = new VehiculoDao();
        this.seguroDao = new SeguroVehicularDao();
    }

    /**
     * Implementa la lógica transaccional para crear un Vehículo y su Seguro asociado.
     * Incluye la vinculación final (UPDATE) requerida por el modelo 1:1.
     */
    public void crearVehiculoConSeguro(Vehiculo vehiculo, SeguroVehicular seguro) throws Exception {
        Connection conn = null;
        try {
            conn = databaseConnection.getConnection();
            conn.setAutoCommit(false); // Inicia la transacción

            // 1. Crear el Seguro (recupera id_seguro)
            seguroDao.crear(conn, seguro);

            // 2. Crear el Vehículo (recupera id_vehiculo)
            vehiculoDao.crear(conn, vehiculo);

            // 3. PASO CRÍTICO: Vincular el Seguro con el Vehículo
            // Llama al método que hace el UPDATE en SeguroVehicular.vehiculo_id
            seguroDao.vincularVehiculo(conn, seguro, vehiculo.getId());

            conn.commit(); // Confirma la transacción

        } catch (SQLException e) {
            if (conn != null) conn.rollback(); // Revierte si hay error
            throw new Exception("Error en la transacción al crear Vehículo y Seguro.", e);
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    /**
     * Implementación del método para el Case 2 del menú.
     * Obtiene todos los Vehículos y sus Seguros asociados desde la BD.
     */
    public List<Vehiculo> getAll() throws Exception {
        try (Connection conn = databaseConnection.getConnection()) {
            // Llama al método leerTodos/getAll que debes implementar en VehiculoDao
            return vehiculoDao.leerTodos(conn);
        } catch (SQLException e) {
            // Manejo de errores de conexión o consulta
            throw new Exception("Error al obtener la lista de vehículos desde la base de datos.", e);
        }
    }

    // Otros métodos opcionales, si los tenés
}