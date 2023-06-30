package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.Utils;
import game.items.Fire;
import game.status.Status;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

  /**
   * The Actor that is to be attacked
   */
  private Actor target;

  /**
   * The direction of incoming attack.
   */
  private String direction;

  /**
   * Constructor.
   *
   * @param target the Actor to attack
   */
  public AttackAction(Actor target, String direction) {
    this.target = target;
    this.direction = direction;
  }

  /**
   * The input actor attacks the target previously given in constructor. The actor has a certain
   * chance of failing the attack based on weapon hit rate. Additionally, does the following checks
   * in order:
   * <ul>
   *   <li> If actor is INVINCIBLE, deal high amount of damage </li>
   *   <li> If target is INVINCIBLE, deal 0 damage. </li>
   *   <li> If actor has FLAMING status, add Fire on the target's ground. </li>
   *   <li> Remove TALL capability from the target </li>
   *   <li> If the target is unconscious after the attack, drop everything from the target. </li>
   *   </ul>
   */
  @Override
  public String execute(Actor actor, GameMap map) {

    Weapon weapon = actor.getWeapon();

    if (!Utils.roll(weapon.chanceToHit())) {
      return actor + " misses " + target + ".";
    }
    //this is for Mario when he is invincible after consuming Power Star
    int damage = weapon.damage();
    if (actor.hasCapability(Status.INVINCIBLE)) {
      damage = 999;
    }
    if (target.hasCapability(Status.INVINCIBLE)) {
      damage = 0;
    }
    String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";
    target.hurt(damage);
    target.removeCapability(Status.TALL);
    // flaming actors drop fire on the ground
    if (actor.hasCapability(Status.FLAMING)) {
      map.locationOf(target).addItem(new Fire());
    }
    // Invincible skips dormant
    if (!target.isConscious() || actor.hasCapability(Status.INVINCIBLE)) {
      Utils.dropAll(target, map);
      // remove actor
      map.removeActor(target);
      result += System.lineSeparator() + target + " is killed.";
    }
    return result;
  }

  @Override
  public String menuDescription(Actor actor) {
    String description = String.format("%s attacks %s at %s", actor, target, direction);
    if (actor.hasCapability(Status.FLAMING)) {
      description += " with fire!";
    }
    return description;
  }
}
