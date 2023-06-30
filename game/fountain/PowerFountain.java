package game.fountain;

import edu.monash.fit2099.engine.actors.Actor;
import game.BaseWeaponManager;

/**
 * PowerFountain supplies PowerWater that grants the actor +15 base damage.
 */
public class PowerFountain extends Fountain {

  /**
   * Constructor.
   */
  public PowerFountain() {
    super('A');
  }

  @Override
  public Water getWater() {
    return new PowerWater();
  }

  /**
   * PowerWater increases the actor's base damage by 15.
   */
  public static class PowerWater implements Water {

    @Override
    public void drink(Actor actor) {
      BaseWeaponManager.getInstance().increaseBaseDamage(actor, 15);
    }

    @Override
    public String toString() {
      return "Power Water";
    }
  }
}
