package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Utils;
import game.status.Status;

/**
 * Lava ground damages any actor that stands on it.
 */
public class Lava extends Ground {

  private static final int TICK_DAMAGE = 15;

  /**
   * Constructor.
   */
  public Lava() {
    super('L');
  }

  /**
   * Damage the current actor standing on lava ground.
   */
  @Override
  public void tick(Location location) {
    Actor actor = location.getActor();
    if (actor != null) {
      Utils.hurtActor(location.getActor(), location.map(), TICK_DAMAGE);
    }
  }

  /**
   * Enemies are not allowed to enter here.
   */
  @Override
  public boolean canActorEnter(Actor actor) {
    return actor.hasCapability(Status.HOSTILE_TO_ENEMY);
  }
}
