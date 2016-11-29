package chapt10;

/**
 * Created by momo on 16/11/23.
 */
public class DollarAmount {
    private int dollars;
    DollarAmount(int dollars) {
        this.dollars = dollars;
    }
    public int getDollars() {
        return dollars;
    }
    public int compareTo(DollarAmount other) {
        return this.dollars - other.getDollars();
    }
}
