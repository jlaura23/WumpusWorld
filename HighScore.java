package wumpusworld;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HighScore {

    public static void main(String[] args) {
        try (Connection connection = connect()) {
            displayHighScore(connection);

            // (Játék vége után mentés a high score-ba...)
            saveHighScore(connection, "TesztJatekos", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection connect() throws Exception {
        String url = "jdbc:h2:tcp://localhost/C:/Users/Laura/WumpusWorld/wumpusworld";
        String user = "admin";
        String password = "admin";
        return DriverManager.getConnection(url, user, password);
    }


    public static void displayHighScore(Connection connection) throws Exception {
        String query = "SELECT * FROM high_score ORDER BY nyertes_jatszmak DESC";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.println("High Score Táblázat:");
            System.out.println("------------------------------");
            System.out.printf("%-20s %-10s%n", "Játékos", "Nyertes Játékok");
            System.out.println("------------------------------");

            while (resultSet.next()) {
                String playerName = resultSet.getString("nev");
                int wins = resultSet.getInt("nyertes_jatszmak");
                System.out.printf("%-20s %-10s%n", playerName, wins);
            }
        }
    }

    // (Metódus a high score mentésére...)
    public static void saveHighScore(Connection connection, String playerName, int wins) {
        try {
            String query = "INSERT INTO high_score (nev, nyertes_jatszmak) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, playerName);
                preparedStatement.setInt(2, wins);
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
