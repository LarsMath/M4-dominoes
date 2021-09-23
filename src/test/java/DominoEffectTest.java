import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DominoEffectTest {

    @Test
    void testSolutionGridExample3(){
        var solutions = DominoEffect.findSolutions(new Board(ExampleInput.input2));
        int[][] solutionGrid = null;
        for(Board sol : solutions){
            solutionGrid = sol.makeGrid();
        }
        assertArrayEquals(ExampleOutput.output2, solutionGrid);
    }

    @Test
    void testExampleInputs(){
        assertEquals(1, DominoEffect.findSolutions(new Board(ExampleInput.input2)).size());
        assertEquals(2, DominoEffect.findSolutions(new Board(ExampleInput.input3)).size());
    }

    //Note that this is not really a valid test as this is an output from this program (and the haskell program).
    @Test
    void notReallyATestInput4(){
        assertEquals(70004, DominoEffect.findSolutions(new Board(ExampleInput.input4)).size());
    }
}
