package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import java.util.HashMap;
import java.util.Map;

/**
 * A base damage singleton that stores base damage of every actor. If an actor tries to retrieve
 * base weapon without registering it prior, returns  a default base weapon with 5 damage and 50%
 * hit rate.
 *
 * @see IntrinsicWeapon
 */
public class BaseWeaponManager {

  private final Map<Actor, IntrinsicWeapon> entry;
  private static BaseWeaponManager instance;
  private static final String DEFAULT_VERB = "punches";
  private static final int DEFAULT_DAMAGE = 5;

  private BaseWeaponManager() {
    entry = new HashMap<>();
  }

  public static BaseWeaponManager getInstance() {
    if (instance == null) {
      instance = new BaseWeaponManager();
    }
    return instance;
  }

  /**
   * Registers the base weapon for actor. Base weapons are {@link IntrinsicWeapon} with 50% hit rate
   * and given base damage. Will overwrite old base weapon if available.
   *
   * @param actor  The input actor to set base weapon
   * @param damage The base damage of actor
   * @param verb   A verb to use when displaying the results of attacking with this Weapon
   */
  public void setBaseWeapon(Actor actor, int damage, String verb) {
    entry.put(actor, new IntrinsicWeapon(damage, verb));
  }

  /**
   * Increase the actor's base damage. If the actor have not registered prior, give the actor a
   * default base weapon and increase its damage by given amount.
   *
   * @param actor  The actor's damage to be increased
   * @param damage The amount of base damage to increase
   */
  public void increaseBaseDamage(Actor actor, int damage) {
    if (entry.containsKey(actor)) {
      IntrinsicWeapon weapon = entry.get(actor);
      entry.put(actor, new IntrinsicWeapon(weapon.damage() + damage, weapon.verb()));
    } else {
      entry.put(actor, new IntrinsicWeapon(DEFAULT_DAMAGE + damage, DEFAULT_VERB));
    }
  }

  /**
   * Gets the base weapon for the input actor. If the actor did not register a base weapon prior,
   * returns a default base weapon.
   */
  public IntrinsicWeapon getBaseWeapon(Actor actor) {
    if (!entry.containsKey(actor)) {
      return new IntrinsicWeapon(DEFAULT_DAMAGE, DEFAULT_VERB);
    }
    return entry.get(actor);
  }
}
