package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.enemies.PiranhaPlant;
import game.items.TeleportationManager;
import game.reset.Resettable;
import game.status.Status;

/**
 * A warp pipe high ground spawns a Piranha Plant on its Location, and serves as a place to teleport
 * to other Location after the enemy is defeated.
 */
public class WarpPipe extends HighGround implements Resettable {

  private PiranhaPlant piranhaPlant;
  public static final char DISPLAY_CHAR = 'C';

  /**
   * Constructor.
   */
  public WarpPipe() {
    super(DISPLAY_CHAR);
    piranhaPlant = new PiranhaPlant();
    addCapability(Status.TELEPORTABLE);
    registerInstance();
  }

  /**
   * allow actors to jump on this ground w/o any problems
   *
   * @return 100(%)
   */
  @Override
  public int getSuccessRate() {
    return 100;
  }

  /**
   * no fall damage when jumping onto the pipe
   *
   * @return 0 damage
   */
  @Override
  public int getFallDamage() {
    return 0;
  }

  /**
   * Spawns the piranha plant if it is still conscious and no actor is on top.
   */
  @Override
  public void tick(Location currentLocation) {
    super.tick(currentLocation);
    if (piranhaPlant.isConscious() && !currentLocation.containsAnActor()) {
      currentLocation.addActor(piranhaPlant);
    }
  }

  /**
   * Allows the actor on this ground to teleport.
   */
  @Override
  public ActionList allowableActions(Actor actor, Location location, String direction) {
    ActionList actionList = new ActionList();
    if (actor == location.getActor() && actor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
      // Only actor on top of this ground & allies are allowed to teleport
      actionList.add(TeleportationManager.getInstance().getTeleportAction(location));
    }
    actionList.add(super.allowableActions(actor, location, direction));
    return actionList;
  }

  /**
   * Adds 50 hp to Piranha Plant if it is still alive, otherwise just revive it.
   */
  @Override
  public void resetInstance() {
    addCapability(Status.RESETTING);
    if (piranhaPlant.isConscious()) {
      piranhaPlant.increaseMaxHp(50);
    } else {
      piranhaPlant.resetMaxHp(PiranhaPlant.HIT_POINTS);
    }
  }

  @Override
  public String toString() {
    return "Warp Pipe";
  }
}
