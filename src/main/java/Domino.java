import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Domino {

    // Note that a given sum and product have a unique solution up to symmetry in the case of two terms (left as exercise to the reader)
    @EqualsAndHashCode.Include
    final int sum;
    @EqualsAndHashCode.Include
    final int product;

    final int x;
    final int y;

    public Domino(int x, int y){
        this.x = x;
        this.y = y;
        this.sum = x + y;
        this.product = x * y;
    }

}