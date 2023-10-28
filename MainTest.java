import wumpusworld.Editor;
import wumpusworld.Player;
import wumpusworld.Position;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    private static Player player;
    private static Editor editor;

    @BeforeAll
    public static void setUp() {
        player = new Player("TesztUser");
        editor = new Editor(10);
    }

    @Test
    public void testPlayerGetName() {
        assertEquals("TesztUser", player.getName());
    }

    @Test
    public void testEditorIsValidPosition() {
        assertTrue(editor.isValidPosition(new Position(0, 0), editor.getBoard()));
        assertTrue(editor.isValidPosition(new Position(5, 5), editor.getBoard()));
        assertTrue(editor.isValidPosition(new Position(10, 0), editor.getBoard()));
        assertTrue(editor.isValidPosition(new Position(0, 10), editor.getBoard()));
    }
}
