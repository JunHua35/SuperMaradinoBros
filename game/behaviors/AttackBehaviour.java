package game.behaviors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.AttackAction;

public class AttackBehaviour implements Behaviour {

  private final Actor target;

  /**
   * Tries to attack a specified target if target is in range.
   *
   * @param target The target to be attacked
   */
  public AttackBehaviour(Actor target) {
    this.target = target;
  }

  /**
   * Factory method for generating actions of actor attacking target.
   *
   * @see Behaviour#getAction(Actor, GameMap)
   */
  @Override
  public Action getAction(Actor actor, GameMap map) {
    // if actor's exit contains target
    Location there = map.locationOf(target),
        here = map.locationOf(actor);
    for (Exit exit : here.getExits()) {
      if (exit.getDestination().equals(there)) {
        return new AttackAction(target, exit.getName());
      }
    }
    return null;
  }
}
