import java.util.HashSet;
import java.util.Set;

public class DominoEffect {

    /**
     * Finds the solutions of a given board. Assumed is that each domino is a key in either tilesOnBoard or tileOptions in the variable board.
     * @return the list of all solutions of the given board state.
     */
    public static Set<Board> findSolutions(Board board){
        var solutions = new HashSet<Board>();
        // Small optimization step
        Domino domino = board.leastOptions();
        for(Position position : board.getPositions(domino)){
            Board copyBoard = board.copyOf();
            copyBoard.putTile(domino, position);
            copyBoard.deleteNeighbours(position);
            if(copyBoard.solved()){
                solutions.add(copyBoard);
            } else if(!copyBoard.unsolvable()){
                // Recursion
                solutions.addAll(findSolutions(copyBoard));
            }
        }
        return solutions;
    }

    public static void main(String[] args){
        // Choose either input1, input2, input3 or input4
        Board exampleBoard = new Board(ExampleInput.input1);
        long tic = System.currentTimeMillis();
        var solutions = findSolutions(exampleBoard);
        long toc = System.currentTimeMillis();
        System.out.println("\nSolutions: " + solutions.size() + "\nThis took " + (toc-tic) + " milliseconds.\n");


        // Print solutions
        printSolutions(solutions);
    }

    /**
     * Prints a set of boards in pretty form. Note that only the tiles that are layed down will be printed (which should be all in a solution anyway).
     */
    public static void printSolutions(Set<Board> solutions){
        for(Board board : solutions){
            var grid = board.makeGrid();
            StringBuilder gridBuilder = new StringBuilder();
            for(int[] row : grid){
                for(int point : row){
                    if(point < 10){
                        gridBuilder.append(" ");
                    }
                    gridBuilder.append(point).append(" ");
                }
                gridBuilder.append("\n");
            }
            gridBuilder.append("\n");
            System.out.println(gridBuilder);
        }
    }
}
