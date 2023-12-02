package wumpusworld;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

import static wumpusworld.HighScore.connect;

/**
 * A GameLogic osztály felelős a játék logikájának végrehajtásáért
 * Kezeli a játéktervet, a játékos pozícióját, irányát, nyílállományát, arany birtoklását és a játékmenet irányítását.
 */
public class GameLogic implements Serializable {
    private char[][] board;
    private int size;
    private Position heroPosition;
    private int heroDirection;
    private int heroArrows;
    private boolean hasGold;

    /**
     * GameLogic osztály konstruktora.
     * Inicializálja a játéklogika állapotát a megadott pálya és méret alapján.
     * A hős kezdőpozíciója a pálya bal felső sarkában van, és az alapértelmezett irány az észak.
     *
     * @param board A játékterv karaktertömbje.
     * @param size  A játékterv mérete (négyzet alakú).
     */

    public GameLogic(char[][] board, int size) {
        // (Inicializáció és állapotbeállítás...)
        this.board = board;
        this.size = size;
        this.heroPosition = new Position(1, 1);
        this.heroDirection = 0; // (Kezdetben észak...)
        this.heroArrows = calculateNumberOfWumpuses();
        this.hasGold = false;

        System.out.println("Hős kezdőpozíciója: [" + heroPosition.getRow() + ", " + heroPosition.getCol() + "]");
        System.out.println("Hős iránya: " + getHeroDirection());
        System.out.println("Birtokolja az aranyat: " + hasGold);
        System.out.println("Nyilak száma: " + heroArrows);

    }

    /**
     * Az aktuális játékmenet vezérlő függvény, amely a játékos választásait kezeli.
     */

    public void playGame() {
        // (Játékmenet vezérlése...)
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
            System.out.println("6. Mentés");
            System.out.println("7. Betöltés");
            System.out.println("8. Felad/Kilépés");
            System.out.println("9. Halasztás");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // (Lépés...)
                    moveHero();
                    break;
                case 2:
                    // (Jobbra fordul...)
                    /* egy változó értékét módosítja, majd modulo
                    (maradékos osztás) művelettel korlátozza azt
                    0-tól 3-ig

                    % 4: modulo (maradékos osztás) művelet, a számot
                    4-gyel osztja, majd visszaadja a maradékot.

                    növeljük a heroDirection értékét, majd ha 4-nél nagyobb lenne, a
                     maradékos osztás miatt visszakerül
                    0-ra, így a heroDirection értéke mindig 0, 1, 2 vagy 3 lehet
                    */
                    heroDirection = (heroDirection + 1) % 4;
                    break;
                case 3:
                    // (Balra fordul...)
                    heroDirection = (heroDirection + 3) % 4;
                    break;
                case 4:
                    // (Lő...)
                    shootArrow();
                    break;
                case 5:
                    // (Felszedi az aranyat...)
                    pickUpGold();
                    break;
                case 6:
                    // (Mentés...)
                    saveGameStateXML();
                    System.out.println("Játékállás mentve!");
                    break;
                case 7:
                    // (Betöltés...)
                    loadGameStateXML();
                    break;
                case 8:
                    // (Feladás/Kilépés...)
                    gameRunning = false;
                    System.out.println("Feladtad a játékot!/Kiléptél a játékból!");
                    break;
                case 9:
                    // (Halasztás (Pause)...)
                    pauseGame();
                    break;
                default:
                    System.out.println("Érvénytelen választás!");
            }

            if (gameOver()) {
                if (hasGold) {
                    System.out.println("Gratulálok! Visszavitted az aranyat!");
                } else {
                    System.out.println("Vesztettél! A Wumpusz megölt.");
                }
                System.out.println("Lépések száma: " + steps);
                gameRunning = false;
            }
            steps++;
        }
    }

    /**
     * Kiszámolja a Wumpusok számát a pálya mérete alapján.
     *
     * @return A Wumpusok száma a pályán.
     */

    public int calculateNumberOfWumpuses() {
        // (Kiszámolja a wumpusok számát...)
        if (size <= 8) {
            return 1;
        } else if (size <= 14) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * Lépteti a hőst a jelenlegi irányába, ellenőrzi a falba ütközést és verembe lépést.
     */

    private void moveHero() {
        // (Lépés és ellenőrzések...)
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
        else {
            System.out.println("Falba ütköztél, nem léphetsz tovább!");
        }

        // (Ellenőrizzük, hogy verembe lépett-e...)
        if (board[heroPosition.getRow()][heroPosition.getCol()] == 'V' && heroArrows > 0) {
            heroArrows--; // (A verembe lépve elveszít egy nyilat...)
        }
    }

    /**
     * Lövés a hős aktuális irányába. Ellenőrzi, hogy sikeres-e a lövés.
     */

    private void shootArrow() {
        // (Lövés és eredmény ellenőrzése...)
        if (heroArrows > 0) {
            Position arrowPosition = new Position(heroPosition.getRow(), heroPosition.getCol());
            boolean hitWumpus = false;

            while (true) {
                switch (heroDirection) {
                    case 0:
                        arrowPosition = new Position(arrowPosition.getRow() - 1,
                                arrowPosition.getCol()); //Észak
                        break;
                    case 1:
                        arrowPosition = new Position(arrowPosition.getRow(),
                                arrowPosition.getCol() + 1); //Kelet
                        break;
                    case 2:
                        arrowPosition = new Position(arrowPosition.getRow() + 1,
                                arrowPosition.getCol()); //Dél
                        break;
                    case 3:
                        arrowPosition = new Position(arrowPosition.getRow(),
                                arrowPosition.getCol() - 1); //Nyugat
                        break;
                }
                if (!isValidPosition(arrowPosition) || board[arrowPosition.getRow()]
                        [arrowPosition.getCol()] == 'F') {
                    break;
                }
                if (board[arrowPosition.getRow()]
                        [arrowPosition.getCol()] == 'W') {
                    board[arrowPosition.getRow()]
                            [arrowPosition.getCol()] = ' ';
                    hitWumpus = true;
                    break;
                }
            }

            heroArrows--;

            if (hitWumpus) {
                System.out.println("AAAAAA!");
                System.out.println("Sikeres lövés! A Wumpus meghalt!");
            } else {
                System.out.println("A lövés sikertelen. A nyíl kettétört a falnak ütközve.");
            }
        }
    }

    /**
     * Ellenőrzi, hogy a megadott pozíció érvényes-e a pályán.
     *
     * @param position Ellenőrizendő pozíció.
     * @return Igaz, ha az adott pozíció érvényes, különben hamis.
     */

    private boolean isValidPosition(Position position) {
        // (Pozíció érvényességének ellenőrzése és visszatérés...)
        int row = position.getRow();
        int col = position.getCol();
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    /**
     * Felvétel aranyat a hős aktuális pozícióján, ha van az adott cellán arany.
     */

    private void pickUpGold() {
        // (Arany felvétele és állapot frissítése...)
        if (board[heroPosition.getRow()][heroPosition.getCol()] == 'A') {
            hasGold = true;
            board[heroPosition.getRow()][heroPosition.getCol()] = ' ';

        }
    }

    /**
     * Kirajzolja a játéktervet a konzolra a jelenlegi állapot alapján.
     */

    private void printGameBoard() {
        // (Játékmezők kirajzolása a konzolra...)
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (i == 0 || i == size - 1 || j == 0 || j == size - 1) {
                    // (Ha a mező a pálya szélén van, akkor falat jelenítünk meg...)
                    System.out.print("F ");
                } else if (i == heroPosition.getRow() &&
                        j == heroPosition.getCol()) {
                    System.out.print("H ");
                } else if (heroDirection == 0 &&
                        i - heroPosition.getRow() < 0 && Math.abs(j -
                        heroPosition.getCol()) <= 1) {
                    System.out.print(" ");
                } else if (heroDirection == 1 &&
                        i - heroPosition.getRow() < 0 && Math.abs(j -
                        heroPosition.getCol()) <= 1) {
                    System.out.print(" ");
                } else if (heroDirection == 2 &&
                        i - heroPosition.getRow() < 0 && Math.abs(j -
                        heroPosition.getCol()) <= 1) {
                    System.out.print(" ");
                } else if (heroDirection == 3 &&
                        i - heroPosition.getRow() < 0 && Math.abs(j -
                        heroPosition.getCol()) <= 1) {
                    System.out.print(" ");
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println("Hős pozíciója: [" +
                heroPosition.getRow() + ", " +
                heroPosition.getCol() + "]");
        System.out.println("Hős iránya: " + getHeroDirection());
        System.out.println("Nyilak száma: " + heroArrows);
        System.out.println("Birtokolja az aranyat: " + hasGold);
    }

    /**
     * Visszaadja a hős irányát szöveges formában.
     *
     * @return A hős iránya szövegesen.
     */

    private String getHeroDirection() {
        // (Irány lekérdezése és visszaadás...)
        switch (heroDirection) {
            case 0:
                return "Észak";
            case 1:
                return "Kelet";
            case 2:
                return "Dél";
            case 3:
                return "Nyugat";
            default:
                return "Ismeretlen irány";
        }
    }

    public boolean playerHasGold() {
        return hasGold = true;
    }

    /**
     * A játékállapotot XML fájlba menti.
     */
    private void saveGameStateXML() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("gameState.xml"))) {
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A játékállapotot betölti XML fájlból.
     */
    private void loadGameStateXML() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("gameState.xml"))) {
            GameLogic loadedGameState = (GameLogic) objectInputStream.readObject();
            // (Frissítjük a jelenlegi game state-et a betöltöttel...)
            this.board = loadedGameState.board;
            this.size = loadedGameState.size;
            this.heroPosition = loadedGameState.heroPosition;
            this.heroDirection = loadedGameState.heroDirection;
            this.heroArrows = loadedGameState.heroArrows;
            this.hasGold = loadedGameState.hasGold;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * A játékállapotot menti XML fájlba és az adatbázisba.
     */
    private void pauseGame() {
        // (XML fájlba mentés...)
        saveGameStateXML();

        // (H2 adatbázisba mentés...)
        try (Connection connection = connect()) {
            saveGameState(connection, "TesztJatekos", this);
            System.out.println("Játékállás mentve és folytatva!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // (Game State mentése...)
    private static void saveGameState(Connection connection, String playerName, GameLogic gameState) {
        try {
            String query = "INSERT INTO saved_games (player_name, game_state) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, playerName);

                try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                     ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                    oos.writeObject(gameState);

                    // (Az objektum bájtokká konvertálása manuálisan...)
                    byte[] bytes = baos.toByteArray();
                    ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                    preparedStatement.setBinaryStream(2, bais, bytes.length);

                    preparedStatement.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ellenőrzi, hogy a játék véget ért-e.
     *
     * @return Igaz, ha a játék véget ért, különben hamis.
     */

    private boolean gameOver() {
        // (Játék vége ellenőrzése...)
        if (hasGold && heroPosition.getRow() ==
                1 && heroPosition.getCol() == 1) {
            return true;
        }
        if (board[heroPosition.getRow()]
                [heroPosition.getCol()] == 'W') {
            return true;
        }
        return false;
    }

    /**
     * A program belépési pontja.
     */

    public static void main(String[] args) {
        // (Játék inicializálása és indítása...)
        int boardSize = 10;
        char[][] initialBoard = generateInitialBoard(boardSize);
        GameLogic game = new GameLogic(initialBoard, boardSize);

        game.playGame();

    }

    /**
     * Generál egy kezdeti pályát a megadott méret alapján.
     *
     * @param size A pálya mérete.
     * @return Az inicializált pálya karaktertömbje.
     */

    private static char[][] generateInitialBoard(int size) {
        // (Pálya inicializálása és visszaadása...)
        char[][] initialBoard = new char[size][size];

        // (Üres mezők szóközzel...)
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                initialBoard[i][j] = ' ';
            }
        }

        return initialBoard;
    }
}
