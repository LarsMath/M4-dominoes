import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void testDominoNumbers(){
        var dominoes = Board.dominoNumbers();
        assertEquals(28, dominoes.size());
        assertEquals(17, dominoes.get(new Domino(2,5)));
    }

    @Test
    void testConstructorFromInt(){
        var board = new Board(ExampleInput.input2);
        assertEquals(28, board.getTileOptions().keySet().size());
        assertEquals(97, board.getTileOptions().values().stream().mapToInt(Set::size).sum());
        assertTrue(board.getTileOptions().get(new Domino(0,5)).contains(new Position(false, 0, 0)));
        assertFalse(board.getTileOptions().get(new Domino(0,5)).contains(new Position(true, 0, 0)));
    }

    @Test
    void testUnsolvable(){
        var board = new Board(ExampleInput.input4);
        board.getTileOptions().get(new Domino(0,0)).clear();
        assertTrue(board.unsolvable());
    }

    @Test
    void testPutTile(){
        var board = new Board(ExampleInput.input4);
        Domino domino = new Domino(0,0);
        board.putTile(domino, new Position(true,0,0));
        assertFalse(board.getTileOptions().containsKey(domino));
        assertTrue(board.getTilesOnBoard().containsKey(domino));
    }

    @Test
    void TestDeleteNeighbours(){
        var board = new Board(ExampleInput.input1);
        Domino domino = new Domino(6,6);
        assertTrue(board.getTileOptions().get(domino).contains(new Position(true, 0, 0)));
        board.deleteNeighbours(new Position(false, 0, 1));
        assertFalse(board.getTileOptions().get(domino).contains(new Position(true, 0, 0)));
    }

}
