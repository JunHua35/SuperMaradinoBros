package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * An action to end the game by removing the given actor from the map.
 */
public class EndGameAction extends Action {

  /**
   * Ends the game for given actor by removing it from the map.
   */
  @Override
  public String execute(Actor actor, GameMap map) {
    map.removeActor(actor);
    return "Victory";
  }

  @Override
  public String menuDescription(Actor actor) {
    return "Save Princess Peach";
  }
}
