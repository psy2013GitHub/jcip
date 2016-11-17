package chapt3;

/**
 * Created by momo on 16/11/14.
 */
public class Listing315 {
    private int n;
    public Listing315(int n) {this.n = n;}
    public void assertSanity() {
        if (n != n) {
            throw new AssertionError("This statement is false");
        }
    }



    public static void main(String[] args) {

    }

}
