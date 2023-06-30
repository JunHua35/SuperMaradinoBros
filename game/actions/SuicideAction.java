package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Utils;

/**
 * Action to commit suicide.
 */
public class SuicideAction extends Action {

  /**
   * The actor is removed from the map and leave their items behind.
   *
   * @param actor The actor performing the action.
   * @param map   The map the actor is on.
   * @see Action#execute(Actor, GameMap)
   */
  @Override
  public String execute(Actor actor, GameMap map) {
    map.removeActor(actor);
    Utils.dropAll(actor, map);
    return menuDescription(actor);
  }

  @Override
  public String menuDescription(Actor actor) {
    return actor + " commits suicide!";
  }
}
