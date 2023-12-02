import org.junit.jupiter.api.Test;
import wumpusworld.Position;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    @Test
    public void testEquals() {
        Position position1 = new Position(1, 2);
        Position position2 = new Position(1, 2);
        Position position3 = new Position(3, 4);

        assertTrue(position1.equals(position2));
        assertFalse(position1.equals(position3));
    }

    @Test
    public void testHashCode() {
        Position position1 = new Position(1, 2);
        Position position2 = new Position(1, 2);
        Position position3 = new Position(3, 4);

        assertEquals(position1.hashCode(), position2.hashCode());
        assertNotEquals(position1.hashCode(), position3.hashCode());
    }
}
