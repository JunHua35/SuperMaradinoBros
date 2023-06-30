package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.ConsumeAction;

/**
 * A ConsumableItem has a {@link ConsumeAction} that triggers the {@link ConsumableItem#onUse(Actor,
 * GameMap)} method of the item that is able to effect the Map or Actor in a defined manner.
 */
public abstract class ConsumableItem extends Item {

  /**
   * Constructor.
   *
   * @param name        Name for the ConsumableItem
   * @param displayChar Display character for the ConsumableItem
   * @param portable    Is the ConsumableItem portable?
   */
  public ConsumableItem(String name, char displayChar, boolean portable) {
    this(name, displayChar, portable, null, null);
  }

  /**
   * Constructor.
   *
   * @param name        Name for the ConsumableItem
   * @param displayChar Display character for the ConsumableItem
   * @param portable    Is the ConsumableItem portable?
   * @param hotkey      A specified hotkey for the ConsumableItem in the menu
   */
  public ConsumableItem(String name, char displayChar, boolean portable, String hotkey) {
    this(name, displayChar, portable, hotkey, null);
  }

  /**
   * Constructor.
   *
   * @param name        Name for the ConsumableItem
   * @param displayChar Display character for the ConsumableItem
   * @param portable    Is the ConsumableItem portable?
   * @param hotkey      A specified hotkey for the ConsumableItem in the menu
   * @param description A specified description for the ConsumableItem in the menu
   */
  public ConsumableItem(String name, char displayChar, boolean portable, String hotkey,
      String description) {
    super(name, displayChar, portable);
    addAction(new ConsumeAction(this, hotkey, description));
  }

  /**
   * Defines what will happen upon using this ConsumableItem
   *
   * @param actor the actor using the ConsumableItem
   * @param map   The map actor is on.
   */
  public abstract void onUse(Actor actor, GameMap map);

  /**
   * Override this method to define when to remove the consumable item
   *
   * @return true to remove the consumable item from the actor after use at this turn.
   */
  public boolean removeOnUse() {
    return true;
  }
}
