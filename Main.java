package wumpusworld;

import java.sql.Connection;
import java.util.Scanner;

/**
 * A fő osztály, amely tartalmazza a main metódust,
 * az alkalmazás belépési pontját.
 */
public class Main {
    private static GameLogic game;

    /**
     * A main metódus, ahol az alkalmazás elindul.
     */
    public static void main(String[] args) {

        //(A getPlayerName() metódus létrehoz egy Player objektumot...)
        Player player = getPlayerName();

        boolean isRunning = true;

        int N = 10;

        //(Editor objektum, a pályaszerkesztést végzi...)
        Editor editor = new Editor(N);

        //(Játékvezérlés...)
        while (isRunning) {
            Menu.display();
            int choice = Menu.getUserChoice();

            switch (choice) {
                case 1:
                    //(Pályaszerkesztő inicializálása...)
                    editor.editBoard();
                    break;
                case 2:
                    //(Játék...)
                    game = new GameLogic(editor.getBoard(), N);
                    int wumpusCount = game.calculateNumberOfWumpuses();
                    System.out.println("A pályán lévő Wumpusok száma: " + wumpusCount);

                    game.playGame();

                    boolean playerWon = game.playerHasGold();

                    if (playerWon) {
                        player.incrementWins();
                        System.out.println("Gratulálok! Nyertél " + player.getWins() + " játékot!");

                        // (High Score mentése...)
                        try (Connection connection = H2DatabaseConnection.connect()) {
                            H2DatabaseConnection.saveHighScore(connection, player.getName(), player.getWins());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Vesztettél! A játék befejeződött.");
                    }

                    break;
                case 3:
                    isRunning = false;
                    System.out.println("Kiléptél a játékból!");
                    break;
                case 4:
                    // (High Score megjelenítése...)
                    try (Connection connection = H2DatabaseConnection.connect()) {
                        H2DatabaseConnection.displayHighScore(connection);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("Érvénytelen választás. Kérlek, válassz egy érvényes menüpontot.");
                    break;
            }
        }
    }
    /**
     * Beolvassa a felhasználó nevét a standard bemenetről.
     *
     * @return A létrehozott Player objektum a megadott névvel.
     */
        private static Player getPlayerName () {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Wumplusz játék\n");
            System.out.print("Kérlek, add meg a felhasználóneved: ");
            String playerName = scanner.nextLine();
            return new Player(playerName);
        }
}
