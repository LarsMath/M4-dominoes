import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.Getter;

@Getter
public class Board {

    //Could not think of a better way to do this.
    static final Map<Domino, Integer> dominoNumbers = dominoNumbers();

    private Map<Domino, Position> tilesOnBoard;
    private Map<Domino, Set<Position>> tileOptions;

    /**
     * Instantiates a board from a grid of pip numbers. No tiles will be on the board after this. The tileOptions will be filled with all options for a given domino.
     * @param grid grid of the pip numbers
     */
    public Board(int[][] grid){
        tileOptions = new HashMap<>();
        for(Domino domino : dominoNumbers.keySet()){
            tileOptions.put(domino, new HashSet<>());
        }
        fillPossibilities(grid);
        this.tilesOnBoard = new HashMap<>();
    }

    /**
     * Helper function for filling tileOptions.
     */
    private void fillPossibilities(int[][] grid) {
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                if(j != grid[i].length - 1){
                    tileOptions.get(new Domino(grid[i][j], grid[i][j+1])).add(new Position(true, i, j));
                }
                if (i != grid.length - 1){
                    tileOptions.get(new Domino(grid[i][j], grid[i+1][j])).add(new Position(false, i, j));
                }
            }
        }
    }

    public Board(Map<Domino, Position> tilesOnBoard, Map<Domino, Set<Position>> tileOptions){
        this.tileOptions = tileOptions;
        this.tilesOnBoard = tilesOnBoard;
    }

    /**
     * Returns whether the board is in a solved state.
     */
    public boolean solved(){
        return tileOptions.isEmpty();
    }

    /**
     * Returns if the board in this state is unsolvable (when a tile has no options anymore).
     */
    public boolean unsolvable(){
        for(Set<Position> positionSet : tileOptions.values()){
            if (positionSet.isEmpty()){
                return true;
            }
        }
        return false;
    }

    /**
     * @return all positions where this domino can still lie.
     */
    public Set<Position> getPositions(Domino domino){
        return tileOptions.get(domino);
    }

    /**
     * Puts a tile in the specified positions and removes it from the leftover tiles.
     */
    public void putTile(Domino domino, Position position){
        tilesOnBoard.put(domino, position);
        tileOptions.remove(domino);
    }

    /**
     * Removes the tile options of other dominoes that are neighbours of the given position.
     */
    public void deleteNeighbours(Position position){
        Set<Position> neighbours = position.getNeighbours();
        for(Set<Position> set : tileOptions.values()){
            set.removeAll(neighbours);
        }
    }

    /**
     * Returns one of the dominoes that have the least options where it can still go in this state.
     */
    public Domino leastOptions(){
        return tileOptions.entrySet().stream()
                .min(Comparator.comparing(entry -> entry.getValue().size()))
                .orElseThrow()
                .getKey();
    }

    /**
     * Makes a (semi)-deep copy of this board. Note that Domino and Position are immutable and are therefore shallow-copied.
     */
    public Board copyOf(){
        var newTilePossibilities = new HashMap<Domino, Set<Position>>();
        for(Map.Entry<Domino, Set<Position>> entry : tileOptions.entrySet()){
            newTilePossibilities.put(entry.getKey(), new HashSet<>(entry.getValue()));
        }
        return new Board(new HashMap<>(tilesOnBoard), newTilePossibilities);
    }

    /**
     * Generate all the dominoes and their index from the assignment.
     */
    public static Map<Domino, Integer> dominoNumbers(){
        var dominoNumbers = new HashMap<Domino, Integer>();
        int count = 1;
        for(int i = 0; i < 7; i++){
            for(int j = i; j < 7; j++){
                dominoNumbers.put(new Domino(i,j), count);
                count++;
            }
        }
        return dominoNumbers;
    }

    // The code below is for printing and testing purposes

    /**
     * Makes a grid of all the tiles that are already on the board.
     */
    public int[][] makeGrid(){
        int[][] grid = new int[7][8];
        for(Map.Entry<Domino, Position> entry : tilesOnBoard.entrySet()){
            int dominoNumber = dominoNumbers.get(entry.getKey());
            int x = entry.getValue().x;
            int y = entry.getValue().y;
            grid[x][y] = dominoNumber;
            if(entry.getValue().vertical){
                grid[x][y+1] = dominoNumber;
            } else {
                grid[x+1][y] = dominoNumber;
            }
        }
        return grid;
    }


}
