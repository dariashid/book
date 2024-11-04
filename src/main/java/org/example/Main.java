package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/book";
        String username = "dariashid";
        String password = "1488228228";
        String authorName = "Гоголь";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT b.name "
                    + "FROM book b "
                    + "JOIN author a ON b.author_id = a.id "
                    + "WHERE a.name = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, authorName);

                try (ResultSet resultSet = statement.executeQuery()) {
                    List<String> books = new ArrayList<>();

                    while (resultSet.next()) {
                        String bookName = resultSet.getString("Мёртвые души");
                        books.add(bookName);
                    }

                    if (books.isEmpty()) {
                        System.out.println("Книги данного автора не найдены.");
                    } else {
                        System.out.printf("Найдено %d книг автора '%s':\n", books.size(), authorName);
                        for (String book : books) {
                            System.out.println(book);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

