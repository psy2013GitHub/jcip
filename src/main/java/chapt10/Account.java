package chapt10;

/**
 * Created by momo on 16/11/23.
 */
public class Account {
    private int money = 0;
    public DollarAmount getBalance() {
        return new DollarAmount(this.money);
    }
    public void debit(DollarAmount out) {
        money -= out.getDollars();
    }
    public void credit(DollarAmount in) {
        money += in.getDollars();
    }

}
