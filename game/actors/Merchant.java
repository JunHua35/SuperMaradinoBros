package game.actors;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.items.Item;
import game.actions.BuyAction;
import java.util.Map;

/**
 * A merchant has goods and can return a list of buy actions for what they are selling.
 */
public interface Merchant {

  /**
   * Maps the merchant's sold items to their prices
   */
  Map<Class<? extends Item>, Integer> getGoods();

  /**
   * Returns a list of BuyActions for available goods to be bought.
   *
   * @see BuyAction
   */
  default ActionList getBuyActions() {
    ActionList actionList = new ActionList();
    for (Map.Entry<Class<? extends Item>, Integer> entry : getGoods().entrySet()) {
      actionList.add(new BuyAction(entry));
    }
    return actionList;
  }

}
