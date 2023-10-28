import wumpusworld.GameLogic;
import wumpusworld.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameLogicTest {
    private GameLogic gameLogic;

    @BeforeEach
    public void setUp() {
        char[][] board = {
                {'F', 'F', 'F', 'F', 'F'},
                {'F', 'H', 'F', 'A', 'F'},
                {'F', 'F', 'F', 'F', 'F'},
                {'F', 'W', 'F', 'F', 'F'},
                {'F', 'F', 'F', 'F', 'F'}
        };

        gameLogic = new GameLogic(board, 5);
    }

    @Test
    public void testMoveHero() {
        Position initialPosition = gameLogic.getHeroPosition();
        gameLogic.moveHero();
        Position newPosition = gameLogic.getHeroPosition();

        assertEquals(initialPosition.getRow() - 1, newPosition.getRow());
        assertEquals(initialPosition.getCol(), newPosition.getCol());
    }

    @Test
    public void testShootArrow() {
        int initialArrows = gameLogic.getHeroArrows();
        gameLogic.shootArrow();
        int newArrows = gameLogic.getHeroArrows();

        assertEquals(initialArrows - 1, newArrows);
    }
}
