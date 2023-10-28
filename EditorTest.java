import wumpusworld.Editor;
import wumpusworld.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditorTest {
    private Editor editor;

    @BeforeEach
    public void setUp() {
        editor = new Editor(5);
    }

    @Test
    public void testAddWall() {
        char[][] board = editor.getBoard();
        Position position = new Position(1, 1);

        editor.addWall(position);

        assertEquals('F', board[1][1]);
    }

    @Test
    public void testRemoveWall() {
        char[][] board = editor.getBoard();
        Position position = new Position(2, 2);

        editor.addWall(position);
        editor.removeWall(position);

        assertEquals(' ', board[2][2]);
    }
}
