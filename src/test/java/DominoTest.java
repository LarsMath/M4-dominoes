import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

class DominoTest {

    @Test
    void testConstructor (){
        assertEquals(new Domino(2, 4), new Domino(4,2));
        assertEquals(new Domino(1, 6), new Domino(6,1));
        assertNotEquals(new Domino(4,3), new Domino(2,6));
    }
}
