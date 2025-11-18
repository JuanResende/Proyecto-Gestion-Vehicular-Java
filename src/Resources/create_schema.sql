-- ************************************************************
-- ARCHIVO 01_create_schema.sql (CORREGIDO)
-- Crea el esquema y las tablas del dominio Vehiculo → SeguroVehicular (1:1)
-- ************************************************************

DROP DATABASE IF EXISTS db_tfi_vehicular;
CREATE DATABASE db_tfi_vehicular;
USE db_tfi_vehicular;

-- ===============================
--  Tabla Vehiculo (Entidad A)
-- ===============================

CREATE TABLE Vehiculo (
                          id_vehiculo BIGINT PRIMARY KEY AUTO_INCREMENT,
                          dominio VARCHAR(10) NOT NULL UNIQUE,
                          marca VARCHAR(50) NOT NULL,
                          modelo VARCHAR(50) NOT NULL,
                          anio INT NOT NULL CHECK (anio >= 1900),
                          nro_chasis VARCHAR(50) UNIQUE,
                          eliminado BOOLEAN NOT NULL DEFAULT FALSE,
                          ultima_renovacion DATE
);

-- ===============================
--  Tabla SeguroVehicular (Entidad B)
--  Relación 1:1 → vehiculo_id es UNIQUE + FK
--  *** SE ELIMINÓ NOT NULL DE vehiculo_id PARA PERMITIR LA TRANSACCIÓN ***
-- ===============================

CREATE TABLE SeguroVehicular (
                                 id_seguro BIGINT PRIMARY KEY AUTO_INCREMENT,
                                 aseguradora VARCHAR(80) NOT NULL,
                                 nro_poliza VARCHAR(50) NOT NULL UNIQUE,
                                 cobertura VARCHAR(15) NOT NULL CHECK (cobertura IN ('RC', 'TERCEROS', 'TODO_RIESGO')),
                                 vencimiento DATE NOT NULL,
                                 eliminado BOOLEAN NOT NULL DEFAULT FALSE,
                                 vehiculo_id BIGINT UNIQUE,  -- <--- ¡CORREGIDO! YA NO ES NOT NULL

                                 FOREIGN KEY (vehiculo_id)
                                     REFERENCES Vehiculo(id_vehiculo)
                                     ON DELETE CASCADE
);

-- Índice opcional
CREATE INDEX idx_vehiculo_anio ON Vehiculo(anio);