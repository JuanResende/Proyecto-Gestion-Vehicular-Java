
# Sistema de Gestión de Pólizas Vehiculares

## Trabajo Práctico Integrador - Programación 2

### Descripción del Proyecto

Este Trabajo Práctico Integrador (TFI) tiene como objetivo demostrar la aplicación de los conceptos de POO y Persistencia de Datos, enfocándose en la **implementación de una relación uno a uno (1:1)** entre las entidades `Vehiculo` y `SeguroVehicular`. El proyecto consiste en desarrollar un sistema de gestión que permita la **creación transaccional** de estas entidades y la consulta de los registros, empleando una arquitectura por capas robusta (Modelo, DAO, Service, Main).

### Objetivos Académicos

El desarrollo de este sistema permite aplicar y consolidar los siguientes conceptos clave de la materia:

**1. Arquitectura en Capas (Layered Architecture)**
- Implementación de separación de responsabilidades en 4 capas diferenciadas (`main`, `service`, `dao`, `entities`).
- Capa de Lógica de Negocio (Service): Control de la **transacción 1:1**.
- Capa de Acceso a Datos (DAO): Implementación de consultas **JOIN** y métodos de vinculación.

**2. Persistencia de Datos con JDBC**
- Implementación del patrón **DAO (Data Access Object)**.
- **Gestión de Transacciones CRÍTICA:** Uso de `commit` y `rollback` en `VehiculoService` para la creación 1:1.
- Consultas con **JOIN** para mapear la relación 1:1 en `VehiculoDao.leerTodos()`.

**3. Integridad Referencial (Relación 1:1)**
- Implementación de la FK como **`UNIQUE`** (`vehiculo_id` en `SeguroVehicular`) para forzar la unicidad de la relación.
- Flujo de creación en 3 pasos (CREATE Seguro, CREATE Vehículo, **UPDATE Vinculación**) garantizado por la transacción.

**4. Patrones de Diseño**
- **Service Layer Pattern** (control transaccional).
- **Soft Delete Pattern** (eliminación lógica en `SeguroVehicularService`).

### Funcionalidades Implementadas

El sistema permite gestionar la relación 1:1 con las siguientes operaciones:

## Características Principales

- **Creación Transaccional 1:1**: Crea un `Vehiculo` y su `SeguroVehicular` de forma atómica.
- **Consulta con JOIN**: Muestra todos los vehículos incluyendo los datos de su póliza asociada.
- **Validación de Entradas**: Validación de formato de fecha, tipo de dato (Año), y Cobertura (RC/TERCEROS/TODO_RIESGO).
- **Soft Delete**: Eliminación lógica de registros (implementada en `SeguroVehicularService`).

## Requisitos del Sistema

| Componente | Versión Requerida |
|------------|-------------------|
| Java JDK | 17 o superior |
| MySQL | 8.0 o superior |
| Driver | MySQL Connector/J 8.x |

## Instalación

### 1. Configurar Base de Datos

Ejecute los siguientes scripts SQL en su cliente MySQL (Workbench, línea de comandos, etc.) en el orden indicado:

#### a. Crear Esquema y Tablas (`src/resources/01_create_schema.sql`)

```sql
SOURCE ruta/a/tu/proyecto/src/resources/01_create_schema.sql;
````

Este script crea la base de datos `db_tfi_vehicular` y define la restricción **`UNIQUE`** en la Clave Foránea `vehiculo_id` para garantizar la relación 1:1.

#### b. Cargar Datos de Prueba (`src/resources/02_insert_data.sql`)

```sql
SOURCE ruta/a/tu/proyecto/src/resources/02_insert_data.sql;
```

Carga tres vehículos y sus seguros asociados iniciales.

### 2\. Configurar Conexión (JDBC)

Verifique y ajuste las credenciales de conexión en la clase `src/config/databaseConnection.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/db_tfi_vehicular";
private static final String USER = "root"; // <-- AJUSTAR
private static final String PASS = "1902"; // <-- AJUSTAR
```

## Ejecución

### Opción 1: Desde IDE

1.  Abrir proyecto en IntelliJ IDEA o Eclipse
2.  Ejecutar clase `main.AppMenu`

### Flujo de Uso (Menú de Consola)

Asegúrese de que la base de datos esté configurada y el servidor MySQL esté corriendo. El programa se inicia con:

```
--- MENÚ ---
1. Crear Vehículo con Seguro
2. Listar Vehículos
3. Salir
Opción: 
```

| Opción | Descripción | Comportamiento |
| :--- | :--- | :--- |
| **1. Crear Vehículo** | Permite ingresar datos de un nuevo vehículo y su seguro. | **Transaccional:** Si falla la inserción o la vinculación, toda la operación se revierte (`ROLLBACK`). |
| **2. Listar Vehículos** | Muestra todos los vehículos activos en el sistema. | Ejecuta un `JOIN` para traer el Vehículo y los detalles de su Seguro en una sola consulta. |

#### Credenciales de Prueba (Ejemplo de uso de Opción 1)

| Campo | Ejemplo de Entrada | Validación/Regla de Negocio |
| :--- | :--- | :--- |
| **Dominio** | XYZ001 | `UNIQUE` en la BD. |
| **Año** | 2021 | Debe ser un entero válido (captura `NumberFormatException`). |
| **Cobertura** | TODO\_RIESGO | Solo acepta: RC, TERCEROS, TODO\_RIESGO (validación en Java). |
| **Vencimiento** | 2026-10-30 | Debe tener formato `yyyy-mm-dd` (captura `DateTimeParseException`). |

## Arquitectura

### Estructura en Capas

```
┌─────────────────────────────────────┐
│     Main / UI Layer                 │
│  (Interacción con usuario)          │
│  AppMenu                           │
└───────────┬─────────────────────────┘
            │
┌───────────▼─────────────────────────┐
│     Service Layer                   │
│  (Lógica de negocio y transacciones)│
│  VehiculoService (Control Transacc.)│
│  SeguroVehicularService             │
└───────────┬─────────────────────────┘
            │
┌───────────▼─────────────────────────┐
│     DAO Layer                       │
│  (Acceso a datos)                   │
│  VehiculoDao, SeguroVehicularDao   │
└───────────┬─────────────────────────┘
            │
┌───────────▼─────────────────────────┐
│     Models Layer                    │
│  (Entidades de dominio)             │
│  Vehiculo, SeguroVehicular         │
└─────────────────────────────────────┘

## Modelo de Datos (Relación 1:1)



```

┌────────────────────┐          ┌──────────────────┐
│      Vehiculo       │          │ SeguroVehicular  │
├────────────────────┤          ├──────────────────┤
│ id\_vehiculo (PK)   │          │ id\_seguro (PK)   │
│ dominio (UNIQUE)   │          │ nro\_poliza (UNIQUE)│
│ eliminado          │          │ eliminado        │
└────────────────────┘          │ vehiculo\_id (FK, UNIQUE)│
                                  └──────────────────┘
                                        ▲
                                        └── Relación 1:1

📹 Enlace al informe en pdf, carpeta drive publica:

https://drive.google.com/drive/folders/1oj9mIR9VRuQrwshBnfoqQNAGBbm0f1IK?usp=sharing

📹 Se adjunta link del video explicativo: 

https://youtu.be/cY5gZAHgOjc?si=ZAYQZIJDzzkgt-aC

📌 Autores

- Alejandro Saavedra

- Juan Ignacio Resende de Moura

- Leandro Torres

- Juan Ignacio Rouge
