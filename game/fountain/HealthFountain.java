package game.fountain;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * HealthFountain supplies Healing Water that heals the actor for 50hp.
 */
public class HealthFountain extends Fountain {

  public HealthFountain() {
    super('H');
  }

  @Override
  public Water getWater() {
    return new HealingWater();
  }

  /**
   * Healing water heals the actor for 50hp.
   */
  public static class HealingWater implements Water {

    /**
     * The input actor drinks the water and gain its bonus.
     */
    @Override
    public void drink(Actor actor) {
      actor.heal(50);
    }

    @Override
    public String toString() {
      return "Healing Water";
    }
  }
}
