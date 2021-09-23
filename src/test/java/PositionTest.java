import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PositionTest {

    @Test
    void testEqualityAfterGettingNeighbours(){
        var pos1 = new Position(true, 1, 0);
        var pos2 = new Position(true, 1, 0);
        pos1.getNeighbours();
        assertEquals(pos1, pos2);
    }

    @Test
    void testGetNeighbours(){
        var pos1 = new Position(true, 1, 0);
        assertTrue(pos1.neighbours.isEmpty());
        assertEquals(6, pos1.getNeighbours().size());
        assertFalse(pos1.getNeighbours().contains(pos1));
        assertFalse(pos1.neighbours.isEmpty());
    }
}
