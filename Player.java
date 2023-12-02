package wumpusworld;

/**
 * Ez az osztály egy értékobjektumot reprezentál,
 * amely csak az adatok tárolásáért felelős.
 * Immutable, azaz a létrehozás után az
 * állapota nem módosítható.
 */
public class Player {
    // (private - csak az osztályon belül érhető el...)
    private final String name;

    /**
     * Az osztály konstruktora, amely létrehoz
     * egy új játékost a megadott névvel.
     *
     * @param name A játékos neve.
     */
    private int wins;
    public Player(String name) {

        this.name = name;
        this.wins = 0;
    }

    /**
     * Visszaadja a játékos nevét.
     *
     * @return A játékos neve.
     */
    public String getName() {

        return name;
    }

    public int getWins() {
        return wins;
    }

    public void incrementWins() {
        wins++;
    }
}
