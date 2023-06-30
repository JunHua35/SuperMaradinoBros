package game.items;

import edu.monash.fit2099.engine.items.Item;
import game.status.Status;

/**
 * Bowser will have the key at the start of the game, and only drop it when Mario destroys him After
 * getting the key, the WIN_CONDITION capability will be added to Mario, and only then Mario can
 * interact with Princess Peach to win the game
 */
public class Key extends Item {

  /***
   * Create a new Key.
   */
  public Key() {
    super("Key", 'k', true);
    addCapability(Status.WIN_CONDITION);
  }
}
