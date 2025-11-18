package main;

import entities.Vehiculo;
import entities.SeguroVehicular;
import service.VehiculoService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class AppMenu {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        VehiculoService service = new VehiculoService();

        int opcion = 0;
        do {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Crear Vehículo con Seguro");
            System.out.println("2. Listar Vehículos");
            System.out.println("3. Salir");
            System.out.print("Opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Debe ser un número.");
                continue;
            }

            switch (opcion) {
                case 1:
                    try {
                        System.out.print("Dominio: ");
                        String dominio = sc.nextLine();

                        System.out.print("Marca: ");
                        String marca = sc.nextLine();

                        System.out.print("Modelo: ");
                        String modelo = sc.nextLine();

                        System.out.print("Año: ");
                        // Manejo de excepción para el Año
                        int anio = Integer.parseInt(sc.nextLine());

                        System.out.print("Nro Chasis: ");
                        String chasis = sc.nextLine();

                        // --- Datos del Seguro ---
                        System.out.print("Aseguradora: ");
                        String aseguradora = sc.nextLine();

                        System.out.print("Nro Póliza: ");
                        String poliza = sc.nextLine();

                        String cobertura;
                        while (true) {
                            System.out.print("Cobertura (RC/TERCEROS/TODO_RIESGO): ");
                            cobertura = sc.nextLine().toUpperCase();
                            if (cobertura.equals("RC") || cobertura.equals("TERCEROS") || cobertura.equals("TODO_RIESGO")) {
                                break;
                            }
                            System.out.println("Cobertura inválida. Ingrese RC, TERCEROS o TODO_RIESGO.");
                        }

                        LocalDate vencimiento;
                        while (true) {
                            try {
                                System.out.print("Vencimiento (yyyy-mm-dd): ");
                                vencimiento = LocalDate.parse(sc.nextLine());
                                break;
                            } catch (DateTimeParseException e) {
                                System.out.println("Fecha inválida. Formato correcto: yyyy-mm-dd");
                            }
                        }

                        // Crear objetos (ID nulo, la base de datos lo genera)
                        SeguroVehicular seguro = new SeguroVehicular(null, aseguradora, poliza, cobertura, vencimiento);
                        Vehiculo vehiculo = new Vehiculo(null, dominio, marca, modelo, anio, chasis, seguro);

                        // Guardar en BD (Flujo transaccional)
                        service.crearVehiculoConSeguro(vehiculo, seguro);
                        System.out.println("\nVehículo y Seguro creados con éxito!");

                    } catch (NumberFormatException e) {
                        System.out.println("\nError: El año debe ser un número entero.");
                    } catch (Exception e) {
                        System.out.println("\nError al crear vehículo: " + e.getMessage());
                        // e.printStackTrace(); // Comentar o dejar para ver detalles del error.
                    }
                    break;

                case 2:
                    try {
                        List<Vehiculo> vehiculos = service.getAll();
                        if (vehiculos.isEmpty()) {
                            System.out.println("\nNo hay vehículos registrados.");
                        } else {
                            System.out.println("\n--- LISTA DE VEHÍCULOS ---");
                            for (Vehiculo v : vehiculos) {
                                System.out.println(v);
                            }
                            System.out.println("--------------------------");
                        }
                    } catch (Exception e) {
                        System.out.println("Error al listar vehículos: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;

                case 3:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 3);

        sc.close();
    }
}