package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Utils;
import game.actors.enemies.Koopa.KoopaBuilder;
import game.grounds.Dirt;
import game.status.Status;
import java.util.ArrayList;
import java.util.List;

/**
 * A tree's mature stage.
 */
public class Mature extends Tree {

  private static final int FALL_DAMAGE = 30;
  private static final int SUCCESS_RATE = 70;
  private static final char DISPLAY_CHAR = 'T';
  private int turnsToSpawnSprout;

  public Mature() {
    super(DISPLAY_CHAR);
    turnsToSpawnSprout = 5;
  }

  /**
   * Mature transforms into Dirt if a roll succeeds.
   * */
  @Override
  public Ground transformGround() {
    return new Dirt();
  }

  /**
   * Mature transforms into Dirt if a roll succeeds.
   * */
  @Override
  public boolean transformCondition() {
    return Utils.roll(20);
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
   * Reduces countdown to spawn the next sprout by one. Has a 15% chance of spawning
   * Koopa/FlyingKoopa.
   */
  @Override
  public void tick(Location location) {
    super.tick(location);
    turnsToSpawnSprout--;
    // Mature has a chance of spawning Koopa
    if (Utils.roll(15) && !location.containsAnActor()) {
      KoopaBuilder koopaBuilder = new KoopaBuilder();
      if (Utils.roll(50)) { // 50% chance to spawn Koopa or Flying Koopa
        koopaBuilder.setFlying();
      }
      location.map().addActor(koopaBuilder.build(), location);
    }
    // Mature grows sprout nearby every 5 turns
    if (turnsToSpawnSprout == 0) {
      spawnSprout(location);
      turnsToSpawnSprout = 5;
    }
  }

  /**
   * Spawns a sprout at one of the nearby fertile ground from given location.
   *
   * @param location the location's exit to be spawned sprout
   */
  private void spawnSprout(Location location) {
    List<Location> possible = new ArrayList<>();
    for (Exit exit : location.getExits()) {
      Location there = exit.getDestination();
      if (there.getGround().hasCapability(Status.FERTILE)) {
        possible.add(there);
      }
    }
    if (!possible.isEmpty()) {
      possible.get(Utils.nextInt(possible.size())).setGround(new Sprout());
    }
  }

  @Override
  public String toString() {
    return "Mature";
  }
}
