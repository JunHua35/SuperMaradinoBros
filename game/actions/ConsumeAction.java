package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.ConsumableItem;

/**
 * Action for consuming an item.
 */
public class ConsumeAction extends Action {

  /**
   * The item to be consumed.
   */
  private ConsumableItem item;
  private String hotkey;
  /**
   * Some ConsumableActions may want a more descriptive action for the player.
   */
  private String description;

  /**
   * Constructor
   *
   * @param item The item to be consumed.
   */
  public ConsumeAction(ConsumableItem item) {
    this(item, null, null);
  }

  /**
   * Constructor
   *
   * @param item   The item to be consumed.
   * @param hotkey The specified hotkey for the user to use this item.
   */
  public ConsumeAction(ConsumableItem item, String hotkey) {
    this(item, hotkey, null);
  }

  /**
   * Constructor
   *
   * @param item        The item to be consumed.
   * @param hotkey      The specified hotkey for the user to use this item.
   * @param description A description for the user in the menu.
   */
  public ConsumeAction(ConsumableItem item, String hotkey, String description) {
    this.item = item;
    this.hotkey = hotkey;
    this.description = description;
  }

  /**
   * Calls {@link ConsumableItem#onUse(Actor, GameMap)} method. Once the item is used, it will be
   * removed from the actor's inventory and prompt a message.
   *
   * @param actor The actor performing the action.
   * @param map   The map the actor is on.
   * @see Action#execute(Actor, GameMap)
   */
  @Override
  public String execute(Actor actor, GameMap map) {
    item.onUse(actor, map);
    if (item.removeOnUse()) {
      actor.removeItemFromInventory(item);
    }
    map.locationOf(actor).removeItem(item);
    return menuDescription(actor);
  }

  @Override
  public String menuDescription(Actor actor) {
    return description == null ? actor + " consumes " + item : description;
  }

  @Override
  public String hotkey() {
    return hotkey;
  }
}
