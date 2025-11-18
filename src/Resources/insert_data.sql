-- ************************************************************
-- ARCHIVO 02_insert_data.sql
-- Datos de prueba iniciales para el TFI
-- ************************************************************

USE db_tfi_vehicular;

-- Vehículos
INSERT INTO Vehiculo (dominio, marca, modelo, anio, nro_chasis)
VALUES
    ('AB123CD', 'Ford', 'Fiesta', 2020, 'CHS001'),
    ('AC456BD', 'Toyota', 'Corolla', 2018, 'CHS002'),
    ('AE789FG', 'Renault', 'Kwid', 2022, 'CHS003');

-- Seguros vinculados 1:1
INSERT INTO SeguroVehicular (aseguradora, nro_poliza, cobertura, vencimiento, vehiculo_id)
VALUES
    ('Seguros Río', 'POL-1001', 'TODO_RIESGO', '2026-05-10', 1),
    ('Aseguradora Andina', 'POL-1002', 'TERCEROS', '2025-09-15', 2),
    ('Mercantil Seguros', 'POL-1003', 'RC', '2025-12-20', 3);
