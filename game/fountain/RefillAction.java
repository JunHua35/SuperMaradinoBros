package game.fountain;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.fountain.Fountain.Water;

/**
 * An action to add Water to the actor's bottle.
 */
public class RefillAction extends Action {

  /**
   * The bottle refills with given water.
   */
  private final Water water;

  public RefillAction(Water water) {
    this.water = water;
  }

  /**
   * Actor refills the bottle with given water. If the actor does not have a bottle, this method
   * won't do anything.
   */
  @Override
  public String execute(Actor actor, GameMap map) {
    return BottleManager.getInstance().addWater(actor, water);
  }

  @Override
  public String menuDescription(Actor actor) {
    return actor + " refills " + water;
  }
}
