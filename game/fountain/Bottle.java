package game.fountain;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.fountain.Fountain.Water;
import game.items.ConsumableItem;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * A bottle stores Water in a Stack fashion. It has infinite capacity.
 */
public class Bottle extends ConsumableItem {

  private static final String NAME = "Bottle";
  private static final char DISPLAY_CHAR = 'b';
  /**
   * Bottle contains water.
   */
  private final Deque<Water> container;

  public Bottle() {
    super(NAME, DISPLAY_CHAR, false);
    container = new ArrayDeque<>();
  }

  /**
   * Gives the actor the {@code drink} effect of the last added Water.
   *
   * @see Water#drink(Actor)
   */
  @Override
  public void onUse(Actor actor, GameMap map) {
    if (!container.isEmpty()) {
      Water water = container.removeLast();
      water.drink(actor);
    }
  }

  /**
   * Adds the water that Mario gets from the fountain
   *
   * @param water Power / Health water depending on which fountain the Actor get the water from
   */
  public void addWater(Water water) {
    container.addLast(water);
  }

  @Override
  public boolean removeOnUse() {
    return false;
  }


  @Override
  public String toString() {
    return NAME + container;
  }
}
