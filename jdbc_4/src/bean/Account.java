package bean;

/**
 * @Description:
 * @author: lk
 * @create: 2019-12-02   21:21
 */
public class Account {
    private String user;
    private int cardId;
    private int balance;

    public Account() {
    }

    public Account(String user, int cardId, int balance) {
        this.user = user;
        this.cardId = cardId;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "user='" + user + '\'' +
                ", cardId=" + cardId +
                ", balance=" + balance +
                '}';
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
