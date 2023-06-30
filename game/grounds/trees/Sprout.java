package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Utils;
import game.actors.enemies.Goomba;

/**
 * A tree's sprout stage.
 */
public class Sprout extends Tree {

  private static final int FALL_DAMAGE = 30;
  private static final int SUCCESS_RATE = 70;
  private static final char DISPLAY_CHAR = '+';
  private int turnsToSapling;

  /**
   * A sprout represents the second stage of a tree.
   */
  public Sprout() {
    super(DISPLAY_CHAR);
    turnsToSapling = 10;
  }

  @Override
  public int getSuccessRate() {
    return SUCCESS_RATE;
  }

  @Override
  public int getFallDamage() {
    return FALL_DAMAGE;
  }

  /**
   * Sprout transforms into Sapling if countdown expires.
   * */
  @Override
  public Ground transformGround() {
    return new Sapling();
  }

  /**
   * Sprout transforms into Sapling if countdown expires.
   * */
  @Override
  public boolean transformCondition() {
    return turnsToSapling == 0;
  }

  /**
   * Reduces countdown to next stage by one. Has a 10% chance of spawning Goomba.
   */
  @Override
  public void tick(Location location) {
    super.tick(location);
    turnsToSapling--;
    // Sprout has a chance of spawning Goomba
    if (Utils.roll(10) && !location.containsAnActor()) {
      location.map().addActor(new Goomba(), location);
    }
  }

  @Override
  public String toString() {
    return "Sprout";
  }
}
