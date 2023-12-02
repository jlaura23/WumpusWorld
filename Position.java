package wumpusworld;

import java.io.Serializable;

/**
 * Egy pozíciót reprezentál sor és oszlop koordinátákkal
 * Immutable, nem módosítható
 * The class provides equals and hashCode methods for proper object comparison.
 */

public class Position implements Serializable {
    /** Sor, egész szám, amely a pozíció sorát jelöli. */
    private final int row;
    /** Oszlop, egész szám, amely a pozíció oszlopát jelöli. */
    private final int col;

    /**
     * Az osztály konstruktorában inicializálják ezeket a változókat.
     *
     * @param row Pozíció sorának értéke.
     * @param col Pozíció oszlopának értéke.
     * @throws IllegalArgumentException ha sor vagy oszlop negatív.
     */
    public Position(int row, int col) {
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException("Sor és oszlop nem lehet nem-negatív");
        }
        this.row = row;
        this.col = col;
    }

    /**
     * Visszaadja a pozíció sorának értékét.
     *
     * @return A pozíció sorának értéke.
     */
    public int getRow() {

        return row;
    }

    /**
     * Visszaadja a pozíció oszlopának értékét.
     *
     * @return A pozíció oszlopának értéke.
     */
    public int getCol() {

        return col;
    }

    /**
     * Az osztály felülírja az equals metódust,
     * amely összehasonlítja a két Position objektumot,
     * hogy ugyanazon a pozíción vannak-e.
     * @param o Az összehasonlítandó objektum.
     * @return Igaz, ha a két objektum ugyanazon a
     * pozíción van, különben hamis.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        return row == position.row && col == position.col;
    }

    /**
     * A hash-kód egy egész szám, amelyet az
     * objektum tartalmának alapján generálnak.
     * @return Az objektum hash-kódja.
     */

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + col;
        return result;
    }
}
