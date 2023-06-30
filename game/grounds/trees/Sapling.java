package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Utils;
import game.items.Coin;

/**
 * A tree's Sapling stage.
 */
public class Sapling extends Tree {

  private static final int FALL_DAMAGE = 10;
  private static final int SUCCESS_RATE = 90;
  private static final char DISPLAY_CHAR = 't';
  private int turnsToMature;

  public Sapling() {
    super(DISPLAY_CHAR);
    turnsToMature = 10;
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
   * Sapling transforms into Mature if countdown expires.
   * */
  @Override
  public Ground transformGround() {
    return new Mature();
  }

  /**
   * Sapling transforms into Mature if countdown expires.
   * */
  @Override
  public boolean transformCondition() {
    return turnsToMature == 0;
  }

  /**
   * Reduces countdown to next stage by one. Has a 10% chance of spawning Coin.
   */
  @Override
  public void tick(Location location) {
    super.tick(location);
    turnsToMature--;
    // Sapling has a chance of spawning coin
    if (Utils.roll(10)) {
      location.addItem(new Coin(20));
    }
  }

  @Override
  public String toString() {
    return "Sapling";
  }
}
