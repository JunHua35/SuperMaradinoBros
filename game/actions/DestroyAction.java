package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Utils;

/**
 * Destroys a target and drops all their items.
 */
public class DestroyAction extends Action {

  /**
   * The target to be destroyed
   */
  private final Actor target;
  /**
   * The direction of the target
   */
  private final String direction;

  public DestroyAction(Actor target, String direction) {
    this.target = target;
    this.direction = direction;
  }

  /**
   * Destroys the target and drops all the target's items.
   */
  @Override
  public String execute(Actor actor, GameMap map) {
    Utils.dropAll(target, map);
    // remove actor
    map.removeActor(target);
    return menuDescription(actor);
  }

  @Override
  public String menuDescription(Actor actor) {
    return String.format("%s destroys %s at %s", actor, target, direction);
  }
}
