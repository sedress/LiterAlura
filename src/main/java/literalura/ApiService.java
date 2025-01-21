package com.literalura;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.fluent.Request;

import java.util.ArrayList;
import java.util.List;

public class ApiService {
    private final List<Book> books = new ArrayList<>();

    public void searchBooks(String author) {
        try {
            String response = Request.Get("https://api.example.com/books?author=" + author)
                    .connectTimeout(1000)
                    .socketTimeout(1000)
                    .execute()
                    .returnContent()
                    .asString();

            parseBooks(response);
        } catch (Exception e) {
            System.out.println("Error al buscar libros: " + e.getMessage());
        }
    }

    private void parseBooks(String json) {
        JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();
        books.clear();

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject bookJson = jsonArray.get(i).getAsJsonObject();
            String title = bookJson.get("title").getAsString();
            String author = bookJson.get("author").getAsString();
            books.add(new Book(title, author));
        }

        System.out.println("Libros encontrados:");
        books.forEach(System.out::println);
    }

    public List<Book> getBooks() {
        return books;
    }
}
