package game.wallet;

import edu.monash.fit2099.engine.actors.Actor;
import java.util.HashMap;
import java.util.Map;

/**
 * A singleton class, useful when exactly one object is needed to coordinate actions across the
 * system.
 */
public class WalletManager {

  private Map<Actor, Wallet> wallets;
  private static WalletManager instance;

  /**
   * Constructor to create a new wallet hashmap
   */
  private WalletManager() {
    wallets = new HashMap<>();
  }

  /**
   * These keeps track of all the wallet in the map, used when there is more than 1 actors on the
   * map
   *
   * @return the instance of the wallet manager
   */
  public static WalletManager getInstance() {
    if (instance == null) {
      instance = new WalletManager();
    }
    return instance;
  }

  /**
   * @param actor
   * @return a boolean if the wallet has already been added to the player; makes sure it doesn't
   * override the old wallet in the process
   */
  public boolean addWallet(Actor actor) {
    if (wallets.containsKey(actor)) {
      return false;
    }
    wallets.put(actor, new Wallet());
    return true;
  }

  /**
   * @param actor
   * @return a boolean if the player has wallet in their inventory
   */
  public boolean hasWallet(Actor actor) {
    return wallets.containsKey(actor);
  }

  /**
   * @param actor
   * @param amount
   * @return transact the coin amount into the player wallet
   */
  public boolean transact(Actor actor, int amount) {
    if (!wallets.containsKey(actor)) {
      return false;
    }
    return wallets.get(actor).transact(amount);
  }

  /**
   * @param actor
   * @return a prompt saying how much has been put into actor wallet
   */
  public String toString(Actor actor) {
    if (!wallets.containsKey(actor)) {
      return null;
    }
    return String.format("%s", wallets.get(actor));
  }

}
