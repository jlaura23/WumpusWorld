package wumpusworld;

import java.sql.*;

public class H2DatabaseConnection {
    public static void main(String[] args) {
        Connection connection = null;

        try {
            connection = connect();
            System.out.println("Sikeres kapcsolat a H2 adatbázishoz!");
            // (Kapcsolódott az adatbázishoz adatbázisműveletek elvégezhetők...)

            // Példa: connection.createStatement() stb.
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Sikertelen kapcsolat a H2 adatbázishoz!");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                    System.out.println("Kapcsolat bezárva.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection connect() throws SQLException {
        try {
            Class.forName("org.h2.Driver");
            String url = "jdbc:h2:tcp://localhost/C:/Users/Laura/WumpusWorld/wumpusworld";
            String user = "admin";
            String password = "admin";

            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("H2 driver not found.", e);
        }
        }

    public static void displayHighScore(Connection connection) {

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
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Hiba a high score megjelenítése közben.");
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
