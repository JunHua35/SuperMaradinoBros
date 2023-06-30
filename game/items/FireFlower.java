package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.status.Status;

/**
 * The fire flower is a consumable item that gives the {@link Status#FLAMING} capability once
 * consumed. FLAMING capability that allows the attacker to drop fire at target's ground for a
 * number of turns.
 */
public class FireFlower extends ConsumableItem {

  public FireFlower() {
    super("Fire Flower", 'f', false);
  }

  /**
   * Gives the actor a Flaming Capability, which will drop fire on the ground during attacks.
   */
  @Override
  public void onUse(Actor actor, GameMap map) {
    actor.addCapability(Status.FLAMING);
  }
}
