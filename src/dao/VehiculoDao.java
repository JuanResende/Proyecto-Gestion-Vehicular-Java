package dao;

import entities.SeguroVehicular; // Necesario para mapear el seguro
import entities.Vehiculo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.ZoneId; // Necesario si mapeas fechas a LocalDate

// Asumo que tu VehiculoDao implementa una interfaz Genérica o similar
public class VehiculoDao {

    /**
     * Inserta un nuevo Vehículo y recupera el ID generado (id_vehiculo).
     */
    public void crear(Connection conn, Vehiculo v) throws SQLException {
        // La consulta asume que el Seguro (FK) se vinculará después (en el servicio)
        String sql = "INSERT INTO Vehiculo (dominio, marca, modelo, anio, nro_chasis, eliminado) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, v.getDominio());
            ps.setString(2, v.getMarca());
            ps.setString(3, v.getModelo());
            ps.setInt(4, v.getAnio());
            ps.setString(5, v.getNroChasis());
            // Asumo que 'eliminado' se puede obtener del objeto o es FALSE por defecto
            ps.setBoolean(6, v.isEliminado());

            ps.executeUpdate();

            // Recupera el ID generado para el Vehículo (id_vehiculo)
            var rs = ps.getGeneratedKeys();
            if (rs.next()) {
                v.setId(rs.getLong(1));
            }
        }
    }

    /**
     * Recupera todos los Vehículos y sus Seguros asociados (JOIN).
     * @return Una lista de objetos Vehiculo.
     */
    public List<Vehiculo> leerTodos(Connection conn) throws SQLException {
        List<Vehiculo> vehiculos = new ArrayList<>();

        // Consulta JOIN que une Vehiculo (v) con SeguroVehicular (s)
        String sql = "SELECT v.*, s.id_seguro, s.aseguradora, s.nro_poliza, s.cobertura, s.vencimiento " +
                "FROM Vehiculo v JOIN SeguroVehicular s ON v.id_vehiculo = s.vehiculo_id " +
                "WHERE v.eliminado = FALSE";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // 1. Mapeo del Seguro (Necesario para el objeto Vehiculo)
                SeguroVehicular seguro = new SeguroVehicular(
                        rs.getLong("id_seguro"),
                        rs.getString("aseguradora"),
                        rs.getString("nro_poliza"),
                        rs.getString("cobertura"),
                        rs.getDate("vencimiento").toLocalDate() // Conversión de Date a LocalDate
                );

                // 2. Mapeo del Vehículo
                Vehiculo vehiculo = new Vehiculo(
                        rs.getLong("id_vehiculo"),
                        rs.getString("dominio"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("anio"),
                        rs.getString("nro_chasis"),
                        seguro // Asocia el objeto Seguro mapeado
                );
                // Mapear otros campos si existen
                vehiculo.setEliminado(rs.getBoolean("eliminado"));

                vehiculos.add(vehiculo);
            }
        }
        return vehiculos;
    }

    // Aquí puedes agregar otros métodos del DAO (leer, actualizar, eliminar, etc.)
}