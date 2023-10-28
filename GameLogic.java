package wumpusworld;

import java.util.Scanner;

public class GameLogic {
    private final char[][] board;
    private final int size;
    private Position heroPosition;
    private int heroDirection;
    private int heroArrows;
    private boolean hasGold;

    public GameLogic(char[][] board, int size) {
        this.board = board;
        this.size = size;
        this.heroPosition = new Position(1, 1);
        this.heroDirection = 0; // Kezdetben észak
        this.heroArrows = countWumpuses();
        this.hasGold = false;
    }

    public void playGame() {
        int steps = 0;
        boolean gameRunning = true;

        while (gameRunning) {
            printGameBoard();

            System.out.println("Játék műveletek:");
            System.out.println("1. Lép");
            System.out.println("2. Fordul jobbra");
            System.out.println("3. Fordul balra");
            System.out.println("4. Lő");
            System.out.println("5. Aranyat felszed");
            System.out.println("6. Felad");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Lépés
                    moveHero();
                    break;
                case 2:
                    // Fordul jobbra
                    heroDirection = (heroDirection + 1) % 4;
                    break;
                case 3:
                    // Fordul balra
                    heroDirection = (heroDirection + 3) % 4;
                    break;
                case 4:
                    // Lövés
                    shootArrow();
                    break;
                case 5:
                    // Aranyat felszed
                    pickUpGold();
                    break;
                case 6:
                    // Feladás
                    gameRunning = false;
                    break;
                default:
                    System.out.println("Érvénytelen választás!");
            }

            if (gameOver()) {
                if (hasGold) {
                    System.out.println("Gratulálok, megtaláltad az aranyat!");
                } else {
                    System.out.println("Vesztettél! A wumpusz megölt.");
                }
                System.out.println("Lépések száma: " + steps);
                gameRunning = false;
            }
            steps++;
        }
    }

    public int countWumpuses() {
        int wumpusCount = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 'W') {
                    wumpusCount++;
                }
            }
        }
        return wumpusCount;
    }

    private void moveHero() {
        Position newHeroPosition = new Position(heroPosition.getRow(), heroPosition.getCol());

        switch (heroDirection) {
            case 0:
                newHeroPosition = new Position(heroPosition.getRow() - 1, heroPosition.getCol()); // Észak
                break;
            case 1:
                newHeroPosition = new Position(heroPosition.getRow(), heroPosition.getCol() + 1); // Kelet
                break;
            case 2:
                newHeroPosition = new Position(heroPosition.getRow() + 1, heroPosition.getCol()); // Dél
                break;
            case 3:
                newHeroPosition = new Position(heroPosition.getRow(), heroPosition.getCol() - 1); // Nyugat
                break;
        }
        if (isValidPosition(newHeroPosition) && board[newHeroPosition.getRow()][newHeroPosition.getCol()] != 'F') {
            heroPosition = newHeroPosition;
        }
    }

    private void shootArrow() {
        if (heroArrows > 0) {
            Position arrowPosition = new Position(heroPosition.getRow(), heroPosition.getCol());
            while (true) {
                switch (heroDirection) {
                    case 0:
                        arrowPosition = new Position(arrowPosition.getRow() - 1, arrowPosition.getCol()); //Észak
                        break;
                    case 1:
                        arrowPosition = new Position(arrowPosition.getRow(), arrowPosition.getCol() + 1); //Kelet
                        break;
                    case 2:
                        arrowPosition = new Position(arrowPosition.getRow() + 1, arrowPosition.getCol()); //Dél
                        break;
                    case 3:
                        arrowPosition = new Position(arrowPosition.getRow(), arrowPosition.getCol() - 1); //Nyugat
                        break;
                }
                if (!isValidPosition(arrowPosition) || board[arrowPosition.getRow()][arrowPosition.getCol()] == 'F') {
                    break;
                }
                if (board[arrowPosition.getRow()][arrowPosition.getCol()] == 'W') {
                    board[arrowPosition.getRow()][arrowPosition.getCol()] = ' ';
                    heroArrows--;
                    break;
                }
            }
        }
    }

    private boolean isValidPosition(Position position) {
        int row = position.getRow();
        int col = position.getCol();
        return row >= 0 && row < size && col >= 0 && col < size;
    }
    private void pickUpGold() {
        if (board[heroPosition.getRow()][heroPosition.getCol()] == 'A') {
            hasGold = true;
            board[heroPosition.getRow()][heroPosition.getCol()] = ' ';
        }
    }

    private void printGameBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (i == heroPosition.getRow() && j == heroPosition.getCol()) {
                    System.out.print("H");
                } else if (heroDirection == 0 && i - heroPosition.getRow() < 0 && Math.abs(j - heroPosition.getCol()) <= 1) {
                    // Hős irányában lévő mezők, ha észak
                    System.out.print(" ");
                } else if (heroDirection == 1 && i - heroPosition.getRow() < 0 && Math.abs(j - heroPosition.getCol()) <= 1) {
                    // Hős irányában lévő mezők, ha kelet
                    System.out.print(" ");
                } else if (heroDirection == 2 && i - heroPosition.getRow() < 0 && Math.abs(j - heroPosition.getCol()) <= 1) {
                    // Hős irányában lévő mezők, ha dél
                    System.out.print(" ");
                } else if (heroDirection == 3 && i - heroPosition.getRow() < 0 && Math.abs(j - heroPosition.getCol()) <= 1) {
                    // Hős irányában lévő mezők, ha nyugat
                    System.out.print(" ");
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    private boolean gameOver() {
        if (hasGold && heroPosition.getRow() == 1 && heroPosition.getCol() == 1) {
            return true;
        }
        if (board[heroPosition.getRow()][heroPosition.getCol()] == 'W') {
            return true;
        }
        return false;
    }
}
