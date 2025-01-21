package com.literalura;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class DatabaseHandler {
    private static final String DB_URL = "jdbc:sqlite:books.db";

    public DatabaseHandler() {
        createDatabase();
    }

    private void createDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            String sql = "CREATE TABLE IF NOT EXISTS books (id INTEGER PRIMARY KEY, title TEXT, author TEXT)";
            connection.createStatement().execute(sql);
        } catch (Exception e) {
            System.out.println("Error al crear la base de datos: " + e.getMessage());
        }
    }

    public void saveBooks(List<Book> books) {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            String sql = "INSERT INTO books (title, author) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            for (Book book : books) {
                statement.setString(1, book.getTitle());
                statement.setString(2, book.getAuthor());
                statement.executeUpdate();
            }

            System.out.println("Libros guardados exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al guardar libros: " + e.getMessage());
        }
    }

    public void displayBooks() {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT * FROM books";
            ResultSet resultSet = connection.createStatement().executeQuery(sql);

            while (resultSet.next()) {
                System.out.println("Título: " + resultSet.getString("title") +
                        ", Autor: " + resultSet.getString("author"));
            }
        } catch (Exception e) {
            System.out.println("Error al mostrar libros: " + e.getMessage());
        }
    }

    public void filterBooks(String keyword) {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT * FROM books WHERE title LIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + keyword + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.println("Título: " + resultSet.getString("title") +
                        ", Autor: " + resultSet.getString("author"));
            }
        } catch (Exception e) {
            System.out.println("Error al filtrar libros: " + e.getMessage());
        }
    }
}
