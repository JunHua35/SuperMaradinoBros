package game.wallet;

/**
 * Represents a wallet that is able to store credit.
 */
public class Wallet {

  /**
   * The Wallet's current balance.
   */
  private int balance;

  /**
   * @param balance The starting balance of this wallet.
   */
  public Wallet(int balance) {
    this.balance = balance;
  }

  /**
   * initializes the balance of the wallet here
   */
  public Wallet() {
    this(1300);
  }

  public int getBalance() {
    return balance;
  }

  /**
   * Makes a transaction for a specified amount. Transaction will fail if attempting to subtract
   * larger than current balance.
   *
   * @param amount the amount to be added/subtracted to this Wallet.
   * @return true if transaction is successful, false otherwise.
   */
  public boolean transact(int amount) {
    if (balance + amount >= 0) {
      balance += amount;
      return true;
    }
    return false;
  }

  /**
   * @return shows the wallet balance in the console
   */
  @Override
  public String toString() {
    return String.format("Current Balance: $%d", balance);
  }
}
