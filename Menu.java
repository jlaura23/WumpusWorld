package wumpusworld;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Ez az osztály felelős a Wumplusz játék
 * menüjének kezeléséért és megjelenítéséért.
 * A menü két funkciót kínál: pályaszerkesztés
 * és játék indítása.
 */
public class Menu {
    /**
     * Megjeleníti a Wumplusz játék menüjét a konzolon.
     */
    public static void display() {
        System.out.println("Wumplusz Menü");
        System.out.println("1. Pályaszerkesztés");
        System.out.println("2. Játék");
        System.out.println("3. Kilépés");
        System.out.println("4. High Score megjelenítése");
    }

    /**
     * Felhasználótól bekéri a kiválasztott menüpont sorszámát.
     *
     * @return A felhasználó által választott menüpont sorszáma.
     */
    public static int getUserChoice() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Válassz menüpontot: ");
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Érvénytelen bemenet. Kérlek, adj meg egy számot.");
            return getUserChoice();
        }
    }
}
