package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.status.Status;
import java.util.Random;

/**
 * Class containing utility functions.
 */
public class Utils {

  private static final Random random = new Random();

  /**
   * Returns a random integer between 0 (inclusive) to bound(exclusive)
   */
  public static int nextInt(int bound) {
    return random.nextInt(bound);
  }

  /**
   * Returns true with given probability.
   */
  public static boolean roll(int chance) {
    return nextInt(100) <= chance;
  }

  /**
   * Drops everything from an actor
   */
  public static void dropAll(Actor actor, GameMap map) {
    ActionList dropActions = new ActionList();
    // drop all items
    for (Item item : actor.getInventory()) {
      dropActions.add(item.getDropAction(actor));
    }
    for (Action drop : dropActions) {
      drop.execute(actor, map);
    }
  }

  /**
   * Allows the actor to be hurt from external factors (jump, environmental damage, etc.). If the
   * actor is unconscious after the attack, this method will drop actor's inventory on the ground
   * and remove actor from the map.
   */
  public static void hurtActor(Actor actor, GameMap map, int damage) {
    if (actor.hasCapability(Status.INVINCIBLE)) {
      return;
    }
    actor.hurt(damage);
    if (!actor.isConscious()) {
      dropAll(actor, map);
    }
  }
}
