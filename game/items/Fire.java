package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.Utils;

/**
 * Fire damages any actor that stands on top of it. It will stay on the map for 3 turns before it
 * disappears.
 */
public class Fire extends Item {

  private static final int DEFAULT_TURNS = 4;
  private static final int TICK_DAMAGE = 20;
  private int turns;

  /***
   * Create a new FIre.
   */
  public Fire() {
    super("Fire", 'v', false);
    this.turns = DEFAULT_TURNS;
  }

  /**
   * Damages the actor on currentLocation.
   * Also, removes the fire from the map after 3 turns.
   *
   * @param currentLocation The location of the Fire.
   */
  @Override
  public void tick(Location currentLocation) {
    super.tick(currentLocation);
    Actor actor = currentLocation.getActor();
    if (actor != null) {
      Utils.hurtActor(actor, currentLocation.map(), TICK_DAMAGE);
    }
    turns--;
    if (turns == 0) {
      currentLocation.removeItem(this);
    }
  }
}
