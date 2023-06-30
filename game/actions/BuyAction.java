package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.wallet.WalletManager;
import java.util.Map;

/**
 * Action for buying an item.
 */
public class BuyAction extends Action {

  /**
   * Item to be bought.
   */
  private Item item;
  /**
   * Buying price for an item.
   */
  private int price;

  /**
   * Constructor.
   *
   * @param goods A map entry that maps the class variable to its price.
   */
  public BuyAction(Map.Entry<Class<? extends Item>, Integer> goods) {
    try {
      item = goods.getKey().getConstructor().newInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
    price = goods.getValue();
  }

  /**
   * Attempts to buy the item based on asking price. Actor must have a wallet to buy an item, and it
   * will fail if actor does not have sufficient balance. If successful, item is automatically added
   * to actor inventory.
   *
   * @param actor The actor performing the action.
   * @param map   The map the actor is on.
   * @return a message indicating if item is successfully bought
   * @see Action#execute(Actor, GameMap)
   */
  @Override
  public String execute(Actor actor, GameMap map) {
    WalletManager walletManager = WalletManager.getInstance();
    if (!walletManager.hasWallet(actor)) {
      return actor + " doesn't have a Wallet!";
    }
    if (!walletManager.transact(actor, -price)) {
      return actor + "doesn't have enough coins!";
    }
    actor.addItemToInventory(item);
    //bought item cannot be dropped
    if (checkPortable(actor)) {
      item.togglePortability();
    }
    return actor + " buys " + item;
  }

  @Override
  public String menuDescription(Actor actor) {
    return String.format("%s buys %s ($%d)", actor, item, price);
  }

  private boolean checkPortable(Actor actor) {
    return item.getDropAction(actor) != null;
  }
}
