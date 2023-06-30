package game.fountain;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Fountains provide Water, which grants bonuses to the actor when drunk. Each Fountain has a
 * RefillAction that adds Water to the Actor's Bottle, so that it can be drunk later on in the
 * actor's turn.
 */
public abstract class Fountain extends Ground {

  /**
   * Constructor.
   *
   * @param displayChar character to display for this type of terrain
   */
  public Fountain(char displayChar) {
    super(displayChar);
  }

  @Override
  public ActionList allowableActions(Actor actor, Location location, String direction) {
    ActionList actionList = new ActionList();
    if (actor == location.getActor()) {
      actionList.add(new RefillAction(getWater()));
    }
    return actionList;
  }

  /**
   * The water this fountain supplies.
   */
  public abstract Water getWater();

  /**
   * Water has an effect on the actor when drunk.
   */
  public interface Water {

    /**
     * The actor drinks the water and gain bonuses, e.g. increased base damage, heal, etc.
     */
    void drink(Actor actor);
  }
}
