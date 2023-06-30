package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.status.Status;

/**
 * A Super Mushroom item. Gives the actor {@link Status#TALL} to jump over walls with 100% success
 * rate and heals the actor for 50hp.
 */
public class SuperMushroom extends ConsumableItem {

  private static final String NAME = "Super Mushroom";
  private static final char DISPLAY_CHAR = '^';

  public SuperMushroom() {
    super(NAME, DISPLAY_CHAR, true);
  }

  /**
   * increases Mario's HP and also gives him the TALL capability to move normally over high grounds
   * and also appears as uppercase 'M' in game map
   *
   * @param actor the actor using the ConsumableItem
   * @param map   The map actor is on.
   */
  @Override
  public void onUse(Actor actor, GameMap map) {
    actor.addCapability(Status.TALL);
    actor.increaseMaxHp(50);
  }
}
