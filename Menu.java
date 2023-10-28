package wumpusworld;

import java.util.Scanner;
public class Menu {
    public static void display() {
        System.out.println("Wumplusz Menü");
        System.out.println("1. Pályaszerkesztés");
        System.out.println("2. Mentés");
        System.out.println("3. Visszatöltés");
        System.out.println("4. Játék");
        System.out.println("5. Kilépés");
    }

    public static int getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Válassz menüpontot: ");
        return scanner.nextInt();
    }
}
