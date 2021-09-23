import java.util.HashSet;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
public class Position {
    final boolean vertical;
    final int x;
    final int y;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Set<Position> neighbours;

    public Position(boolean vertical, int x, int y) {
        this.vertical = vertical;
        this.x = x;
        this.y = y;
        this.neighbours = new HashSet<>();
    }

    /**
     * Returns the neighbours of this position. It does not matter if these neighbours are part of the board.
     */
    public Set<Position> getNeighbours(){
        if(neighbours.isEmpty()){
            if(vertical){
                neighbours.add(new Position(true, x, y + 1));
                neighbours.add(new Position(true, x, y - 1));
                neighbours.add(new Position(false, x - 1, y));
                neighbours.add(new Position(false, x - 1, y + 1));
                neighbours.add(new Position(false, x, y));
                neighbours.add(new Position(false, x, y + 1));
            } else {
                neighbours.add(new Position(false, x - 1, y));
                neighbours.add(new Position(false, x + 1, y));
                neighbours.add(new Position(true, x, y - 1));
                neighbours.add(new Position(true, x + 1, y - 1));
                neighbours.add(new Position(true, x, y));
                neighbours.add(new Position(true, x + 1, y));
            }
        }
        return neighbours;
    }
}
