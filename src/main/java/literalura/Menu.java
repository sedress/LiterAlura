package com.literalura;

import java.util.Scanner;

public class Menu {
    private final ApiService apiService = new ApiService();
    private final DatabaseHandler databaseHandler = new DatabaseHandler();

    public void show() {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Buscar libros por autor");
            System.out.println("2. Guardar libros en la base de datos");
            System.out.println("3. Mostrar libros guardados");
            System.out.println("4. Filtrar libros por título");
            System.out.println("5. Salir");
            System.out.print("Opción: ");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> searchBooks();
                case 2 -> saveBooks();
                case 3 -> displayBooks();
                case 4 -> filterBooks();
                case 5 -> System.out.println("¡Gracias por usar LiterAlura!");
                default -> System.out.println("Opción no válida.");
            }
        } while (option != 5);

        scanner.close();
    }

    private void searchBooks() {
        System.out.print("Ingrese el nombre del autor: ");
        Scanner scanner = new Scanner(System.in);
        String author = scanner.nextLine();
        apiService.searchBooks(author);
    }

    private void saveBooks() {
        databaseHandler.saveBooks(apiService.getBooks());
    }

    private void displayBooks() {
        databaseHandler.displayBooks();
    }

    private void filterBooks() {
        System.out.print("Ingrese el título o palabra clave: ");
        Scanner scanner = new Scanner(System.in);
        String keyword = scanner.nextLine();
        databaseHandler.filterBooks(keyword);
    }
}
