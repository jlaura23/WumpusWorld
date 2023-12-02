package wumpusworld;

import java.util.Scanner;

/**
 * Ez az osztály a pályaszerkesztésért felelős
 */
public class Editor {
    private final char[][] board;

    // (Konstansok az elemek reprezentálásához...))
    private static final char WALL = 'F';
    private static final char PIT = 'V';
    private static final char WUMPUS = 'W';
    private static final char GOLD = 'A';
    private static final char HERO = 'H';

    /**
     * Konstruktor, inicializálja a pályát egy adott méretű négyzetrácsos táblával.
     *
     * @param N A pálya mérete.
     */
    public Editor(int N) {

        board = createEmptyBoard(N);
    }

    /**
     * Pályaszerkesztő műveleteket valósít meg. Lehetőséget ad a felhasználónak
     * a pályaelemek hozzáadására és eltávolítására, valamint a pálya megjelenítésére.
     */
    public void editBoard() {
        boolean editing = true;
        Scanner scanner = new Scanner(System.in);

        while (editing) {
            printBoard(board);
            System.out.println("Pályaszerkesztő műveletek:");
            System.out.println("1. Fal hozzáadása");
            System.out.println("2. Verem hozzáadása");
            System.out.println("3. Wumpusz hozzáadása");
            System.out.println("4. Arany hozzáadása");
            System.out.println("5. Hős hozzáadása");
            System.out.println("6. Fal eltávolítása");
            System.out.println("7. Verem eltávolítása");
            System.out.println("8. Wumpusz eltávolítása");
            System.out.println("9. Arany eltávolítása");
            System.out.println("10. Hős eltávolítása");
            System.out.println("11. Pályaszerkesztés befejezése");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addWall();
                    break;
                case 2:
                    addPit();
                    break;
                case 3:
                    addWumpus();
                    break;
                case 4:
                    addGold();
                    break;
                case 5:
                    addHero();
                    break;
                case 6:
                    removeWall();
                    break;
                case 7:
                    removePit();
                    break;
                case 8:
                    removeWumpus();
                    break;
                case 9:
                    removeGold();
                    break;
                case 10:
                    removeHero();
                    break;
                case 11:
                    editing = false;
                    break;
                default:
                    System.out.println("Érvénytelen választás!");
                    break;
            }
        }
    }

    /**
     * Üres pályát hoz létre, ahol a pályaszéleken falak találhatók.
     *
     * @param N A pálya mérete.
     * @return Az inicializált pálya.
     */
    private char[][] createEmptyBoard(int N) {
        char[][] board = new char[N][N];

        // (Első és utolsó sorban falak...)
        for (int i = 0; i < N; i++) {
            board[0][i] = 'F';
            board[N - 1][i] = 'F';
        }


        // (Első és utolsó oszlopban falak...)
        for (int i = 0; i < N; i++) {
            board[i][0] = 'F';
            board[i][N - 1] = 'F';
        }

        // (Üres mezők szóközzel...)
        for (int i = 1; i < N - 1; i++) {
            for (int j = 1; j < N - 1; j++) {
                board[i][j] = ' ';
            }
        }

        return board;
    }

    /**
     * Kiírja a jelenlegi pálya állapotát a konzolra.
     *
     * @param board A megjelenítendő pálya.
     */
    private void printBoard(char[][] board) {
        //
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void addElement(char element) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Add meg a(z) " + getElementName(element) + " pozícióját (pl. 'b3'): ");
        String positionStr = scanner.next().toLowerCase();
        Position position = parsePosition(positionStr);

        if (isValidPosition(position, board) && board[position.getRow()][position.getCol()] == ' ') {
            board[position.getRow()][position.getCol()] = element;
            System.out.println(getElementName(element) + " hozzáadva a pályához.");
        } else {
            System.out.println("Érvénytelen pozíció vagy a mező már foglalt.");
        }
    }

    private void removeElement(char element) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Add meg a(z) " + getElementName(element) + " pozícióját (pl. 'b3'): ");
        String positionStr = scanner.next().toLowerCase();
        Position position = parsePosition(positionStr);

        if (board[position.getRow()][position.getCol()] == element) {
            board[position.getRow()][position.getCol()] = ' ';
            System.out.println(getElementName(element) + " eltávolítva a pályáról.");
        } else {
            System.out.println("Érvénytelen pozíció vagy a mező nem tartalmaz " + getElementName(element) + ".");
        }
    }

    private String getElementName(char element) {
        switch (element) {
            case WALL:
                return "fal";
            case PIT:
                return "verem";
            case WUMPUS:
                return "wumpusz";
            case GOLD:
                return "arany";
            case HERO:
                return "hős";
            default:
                return "ismeretlen";
        }
    }


    /**
     * Fal hozzáadása a pályához a felhasználó által megadott pozíció alapján.
     */
    private void addWall() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Add meg a fal pozícióját (pl. 'b3'): ");
        String positionStr = scanner.next().toLowerCase();

        Position position = parsePosition(positionStr);

        if (isValidPosition(position, board) &&
                board[position.getRow()][position.getCol()] == ' ') {
            board[position.getRow()][position.getCol()] = 'F';
        } else {
            System.out.println("Érvénytelen pozíció vagy a mező már foglalt.");
        }
    }
    /**
     * Verem hozzáadása a pályához a felhasználó által megadott pozíció alapján.
     */
    private void addPit() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Add meg a verem pozícióját (pl. 'b3'): ");
        String positionStr = scanner.next().toLowerCase();

        Position position = parsePosition(positionStr);

        if (isValidPosition(position, board) &&
                board[position.getRow()][position.getCol()] == ' ') {
            board[position.getRow()][position.getCol()] = 'V';
        } else {
            System.out.println("Érvénytelen pozíció vagy a mező már foglalt.");
        }

    }
    /**
     * Wumpus hozzáadása a pályához a felhasználó által megadott pozíció alapján.
     */
    private void addWumpus() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Add meg a wumpusz pozícióját (pl. 'b3'): ");
        String positionStr = scanner.next().toLowerCase();

        Position position = parsePosition(positionStr);

        if (isValidPosition(position, board) &&
                board[position.getRow()][position.getCol()] == ' ') {
            board[position.getRow()][position.getCol()] = 'W';
        } else {
            System.out.println("Érvénytelen pozíció vagy a mező már foglalt.");
        }
    }
    /**
     * Arany hozzáadása a pályához a felhasználó által megadott pozíció alapján.
     */
    private void addGold() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Add meg az arany pozícióját (pl. 'b3'): ");
        String positionStr = scanner.next().toLowerCase();

        Position position = parsePosition(positionStr);

        if (isValidPosition(position, board) &&
                board[position.getRow()][position.getCol()] == ' ') {
            board[position.getRow()][position.getCol()] = 'A';
        } else {
            System.out.println("Érvénytelen pozíció vagy a mező már foglalt.");
        }
    }
    /**
     * Hős hozzáadása a pályához a felhasználó által megadott pozíció alapján.
     */
    private void addHero() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Add meg a hős pozícióját (pl. 'b3'): ");
        String positionStr = scanner.next().toLowerCase();

        Position position = parsePosition(positionStr);

        if (isValidPosition(position, board) &&
                board[position.getRow()][position.getCol()] == ' ') {
            board[position.getRow()][position.getCol()] = 'H';
        } else {
            System.out.println("Érvénytelen pozíció vagy a mező már foglalt.");
        }
    }
    /**
     * Fal eltávolítása a pályáról a felhasználó által megadott pozíció alapján.
     */
    private void removeWall() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Add meg a fal pozícióját (pl. 'b3'): ");
        String positionStr = scanner.next().toLowerCase();

        Position position = parsePosition(positionStr);

        if (board[position.getRow()][position.getCol()] == 'F') {
            board[position.getRow()][position.getCol()] = ' ';
        } else {
            System.out.println("Érvénytelen pozíció vagy a mező üres.");
        }
    }
    /**
     * Verem eltávolítása a pályáról a felhasználó által megadott pozíció alapján.
     */
    private void removePit() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Add meg a verem pozícióját (pl. 'b3'): ");
        String positionStr = scanner.next().toLowerCase();

        Position position = parsePosition(positionStr);

        if (board[position.getRow()][position.getCol()] == 'V') {
            board[position.getRow()][position.getCol()] = ' ';
        } else {
            System.out.println("Érvénytelen pozíció vagy a mező üres.");
        }

    }
    /**
     * Wumpus eltávolítása a pályáról a felhasználó által megadott pozíció alapján.
     */
    private void removeWumpus() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Add meg a wumpusz pozícióját (pl. 'b3'): ");
        String positionStr = scanner.next().toLowerCase();

        Position position = parsePosition(positionStr);

        if (board[position.getRow()][position.getCol()] == 'W') {
            board[position.getRow()][position.getCol()] = ' ';
        } else {
            System.out.println("Érvénytelen pozíció vagy a mező üres.");
        }
    }
    /**
     * Arany eltávolítása a pályáról a felhasználó által megadott pozíció alapján.
     */
    private void removeGold() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Add meg az arany pozícióját (pl. 'b3'): ");
        String positionStr = scanner.next().toLowerCase();

        Position position = parsePosition(positionStr);

        if (board[position.getRow()][position.getCol()] == 'A') {
            board[position.getRow()][position.getCol()] = ' ';
        } else {
            System.out.println("Érvénytelen pozíció vagy a mező üres.");
        }
    }
    /**
     * Hős eltávolítása a pályáról a felhasználó által megadott pozíció alapján.
     */
    private void removeHero() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Add meg a hős pozícióját (pl. 'b3'): ");
        String positionStr = scanner.next().toLowerCase();

        Position position = parsePosition(positionStr);

        if (board[position.getRow()][position.getCol()] == 'H') {
            board[position.getRow()][position.getCol()] = ' ';
        } else {
            System.out.println("Érvénytelen pozíció vagy a mező üres.");
        }
    }

    private static Position parsePosition(String positionStr) {
        int row = positionStr.charAt(1) - '1';
        int col = positionStr.charAt(0) - 'a';
        return new Position(row, (char) col);
    }

    //(A megadott pozíció érvényes-e, nem megy-e túl a pályakereten...)
    public static boolean isValidPosition(Position position, char[][] board) {
        int N = board.length;
        int row = position.getRow();
        int col = position.getCol();
        return row >= 0 && row < N && col >= 0 && col < N;
    }

    public char[][] getBoard() {

        return board;
    }
}
