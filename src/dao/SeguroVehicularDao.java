package dao;

import entities.SeguroVehicular;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List; // Importar List

public class SeguroVehicularDao implements GenericDao<SeguroVehicular> {

    @Override
    public void crear(Connection conn, SeguroVehicular s) throws SQLException {
        // La consulta SQL ya NO incluye vehiculo_id ni id_seguro (AUTO_INCREMENT)
        String sql = "INSERT INTO SeguroVehicular (aseguradora, nro_poliza, cobertura, vencimiento, eliminado) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getAseguradora());
            ps.setString(2, s.getNroPoliza());
            ps.setString(3, s.getCobertura());
            ps.setDate(4, Date.valueOf(s.getVencimiento()));
            ps.setBoolean(5, s.isEliminado());
            ps.executeUpdate();

            // Recupera el ID generado por la base de datos y lo guarda en el objeto 's'
            var rs = ps.getGeneratedKeys();
            if (rs.next()) s.setId(rs.getLong(1));
        }
    }

    /**
     * Método nuevo y CRÍTICO para la relación 1:1.
     * Vincula el Seguro (usando su ID) al Vehículo (usando el ID del Vehículo).
     */
    public void vincularVehiculo(Connection conn, SeguroVehicular s, long idVehiculo) throws SQLException {
        // Actualiza el campo vehiculo_id en la fila del Seguro usando el id_seguro
        String sql = "UPDATE SeguroVehicular SET vehiculo_id = ? WHERE id_seguro = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idVehiculo); // El ID del Vehículo
            ps.setLong(2, s.getId());    // El ID del Seguro (PK)
            ps.executeUpdate();
        }
    }

    @Override
    public SeguroVehicular leer(Connection conn, Long id) throws SQLException {
        // TODO: implementar
        return null;
    }

    @Override
    public List<SeguroVehicular> leerTodos(Connection conn) throws SQLException {
        // TODO: implementar
        return null;
    }

    @Override
    public void actualizar(Connection conn, SeguroVehicular entidad) throws SQLException {
        // TODO: implementar
    }

    @Override
    public void eliminar(Connection conn, Long id) throws SQLException {
        // TODO: implementar (baja lógica)
    }
}