import org.junit.jupiter.api.Test;
import wumpusworld.Player;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void testGetName() {
        Player player = new Player("Janos");
        assertEquals("Janos", player.getName());
    }

    @Test
    public void testEquals() {
        Player player1 = new Player("Janos");
        Player player2 = new Player("Jakab");
        Player player3 = new Player("Anna");

        assertFalse(player1.equals(player2));
        assertFalse(player1.equals(player3));
    }

    @Test
    public void testHashCode() {
        Player player1 = new Player("Janos");
        Player player2 = new Player("Jakab");
        Player player3 = new Player("Anna");

        assertNotEquals(player1.hashCode(), player2.hashCode());
        assertNotEquals(player1.hashCode(), player3.hashCode());
    }
}
