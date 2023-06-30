package game.actors.enemies;


import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.BaseWeaponManager;
import game.Utils;
import game.actions.SuicideAction;
import game.reset.Resettable;
import game.status.Status;

/**
 * A little fungus guy.
 */
public class Goomba extends Enemy implements Resettable {

  private static final int SUICIDE_CHANCE = 10;

  /**
   * Constructor.
   */
  public Goomba() {
    super(new EnemyBuilder("Goomba", 'g', 50));
    BaseWeaponManager.getInstance().setBaseWeapon(this, 10, "kicks");
    registerInstance();
  }

  /**
   * Removes Goomba from the map when player resets game. Goomba also has a chance of suiciding this
   * turn.
   */
  @Override
  public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
    if (hasCapability(Status.RESETTING)) {
      map.removeActor(this);
      removeInstance();
      return new DoNothingAction();
    }
    if (Utils.roll(SUICIDE_CHANCE)) {
      return new SuicideAction();
    }
    return super.playTurn(actions, lastAction, map, display);
  }

  /**
   * Adds the reset capability to Goomba, so that it can be reset at the next turn. Capability will
   * be removed in playTurn() after player resets
   */
  @Override
  public void resetInstance() {
    addCapability(Status.RESETTING);
  }
}
