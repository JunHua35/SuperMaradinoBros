package game.reset;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.ConsumableItem;

/**
 * Resetter is an item that allows a player to reset the game based on the implemented Resettable
 * interface.
 */
public class Resetter extends ConsumableItem {

  public static final String NAME = "Resetter";
  public static final char DISPLAY_CHAR = '!';
  private static final String DESCRIPTION = "Reset the game";

  /**
   * constructor for resetter
   */
  public Resetter() {
    super(NAME, DISPLAY_CHAR, false, "r", DESCRIPTION);
  }

  /**
   * A consumable item that allows the player to reset the game. Resets all resettable instances
   * registered under ResetManager.
   */
  @Override
  public void onUse(Actor actor, GameMap map) {
    ResetManager.getInstance().run();
  }

}
