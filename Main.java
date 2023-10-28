package wumpusworld;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Player player = getPlayerName();

        boolean isRunning = true;

        int N = 10; // Kezdeti pályaméret (10x10)

        Editor editor = new Editor(N);

        while (isRunning) {
            Menu.display();
            int choice = Menu.getUserChoice();

            switch (choice) {
                case 1:
                    //Pályaszerkesztő
                    editor.editBoard();
                    break;
                case 2:
                    //Mentés
                case 3:
                    //Visszatöltés
                case 4:
                    //Játék
                    GameLogic game = new GameLogic(editor.getBoard(), N);
                    int wumpusCount = game.countWumpuses();
                    System.out.println("A pályán lévő Wumpusok száma: " + wumpusCount);

                    game.playGame();
                    break;
                case 5:
                    //Kilépés
                    isRunning = false;
                    break;
                default:
                    System.out.println("Hiba. Válassz egy érvényes menüpontot.");
                    break;
            }
        }
    }
        private static Player getPlayerName () {
            Scanner scanner = new Scanner(System.in);
            System.out.print("WumpluszWorld Játék\n");
            System.out.print("Add meg a felhasználóneved: ");
            String playerName = scanner.nextLine();
            return new Player(playerName);
        }
}
