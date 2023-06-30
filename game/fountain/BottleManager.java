package game.fountain;

import edu.monash.fit2099.engine.actors.Actor;
import game.fountain.Fountain.Water;
import java.util.HashMap;
import java.util.Map;

/**
 * BottleManager is a singleton that stores actors' bottles. Each actor can only have a single
 * bottle to drink from.
 */
public class BottleManager {

  private static BottleManager instance;
  /**
   * Maps actors to their own bottle
   */
  private final Map<Actor, Bottle> bottles;

  private BottleManager() {
    bottles = new HashMap<>();
  }

  public static BottleManager getInstance() {
    if (instance == null) {
      instance = new BottleManager();
    }
    return instance;
  }

  /**
   * Creates a new bottle for actor. If actor already has a bottle, this method won't create a new
   * one so that it wouldn't be overwritten.
   *
   * @return true if actor doesn't have a bottle, false otherwise.
   */
  public boolean addBottle(Actor actor) {
    if (bottles.containsKey(actor)) {
      return false;
    }
    Bottle b = new Bottle();
    bottles.put(actor, b);
    actor.addItemToInventory(b);
    return true;
  }

  /**
   * Returns true if actor has a bottle.
   */
  public boolean hasBottle(Actor actor) {
    return bottles.containsKey(actor);
  }

  /**
   * Adds water for actor. If the actor does not have bottle, this method does nothing.
   *
   * @param actor The actor who wants to add water to own bottle
   * @param water The water to be added
   * @return A string describing if this action is successful.
   */
  public String addWater(Actor actor, Water water) {
    Bottle bottle = bottles.get(actor);
    if (bottle == null) {
      return actor + " fails to refill " + water;
    }
    bottle.addWater(water);
    return actor + " refills " + water;
  }
}
