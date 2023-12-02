import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import wumpusworld.Menu;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MenuTest {

    @Test
    public void testDisplay() {
        // Standard kimenet (System.out) elfogása
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Metódus hívása
        Menu.display();

        // Elvárt eredmény ellenőrzése
        String expectedOutput = "Wumplusz Menü" + System.lineSeparator() +
                                "1. Pályaszerkesztés" + System.lineSeparator() +
                                "2. Mentés" + System.lineSeparator() +
                                "3. Visszatöltés" + System.lineSeparator() +
                                "4. Játék" + System.lineSeparator() +
                                "5. Kilépés" + System.lineSeparator();
        Assertions.assertEquals(expectedOutput, outContent.toString());

        // Visszaállítjuk a standard kimenetet
        System.setOut(System.out);
    }

    @Test
    public void testGetUserChoice() {
        // Felhasználói bemenet szimulálása
        ByteArrayInputStream inContent = new ByteArrayInputStream("3\n".getBytes());
        System.setIn(inContent);

        // Metódus hívása
        int userChoice = Menu.getUserChoice();

        // Elvárt eredmény ellenőrzése
        Assertions.assertEquals(3, userChoice);

        // Visszaállítjuk a standard bemenetet
        System.setIn(System.in);
    }
}
