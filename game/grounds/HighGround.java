package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.JumpAction;
import game.items.Coin;
import game.status.Status;

/**
 * A high ground that actors need to jump into.
 */
public abstract class HighGround extends Ground {

  /**
   * Constructor.
   *
   * @param displayChar character to display for this type of terrain
   */
  public HighGround(char displayChar) {
    super(displayChar);
  }

  /**
   * The success rate for jumping to this high ground.
   */
  public abstract int getSuccessRate();

  /**
   * The fall damage of this high ground if the jump fails.
   */
  public abstract int getFallDamage();

  /**
   * If the current actor standing is Invincible, change this ground to dirt and add Coin
   */
  @Override
  public void tick(Location location) {
    Actor actor = location.getActor();
    if (actor != null && actor.hasCapability(Status.INVINCIBLE)) {
      location.setGround(new Dirt());
      location.addItem(new Coin(5));
    }
  }

  /**
   * Allows other actor to jump to this ground.
   *
   * @see Ground#allowableActions(Actor, Location, String)
   */
  @Override
  public ActionList allowableActions(Actor actor, Location location, String direction) {
    if (location.containsAnActor() || actor.hasCapability(Status.INVINCIBLE)) {
      return new ActionList();
    }
    ActionList actionList = new ActionList();
    actionList.add(new JumpAction(location, direction, this));
    return actionList;
  }

  /**
   * Actor with Invincible/Flying status can move here.
   */
  @Override
  public boolean canActorEnter(Actor actor) {
    return actor.hasCapability(Status.INVINCIBLE) || actor.hasCapability(Status.FLYING);
  }
}
