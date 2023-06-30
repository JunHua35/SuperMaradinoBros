package game.grounds.trees;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Utils;
import game.grounds.Dirt;
import game.grounds.HighGround;
import game.items.FireFlower;
import game.reset.Resettable;
import game.status.Status;

/**
 * A Tree's different growth stages is represented by transforming a location's ground when a
 * certain condition holds.
 */
public abstract class Tree extends HighGround implements Resettable {

  /**
   * Constructor.
   *
   * @param displayChar character to display for this type of terrain
   */
  public Tree(char displayChar) {
    super(displayChar);
    registerInstance();
  }

  @Override
  public void resetInstance() {
    addCapability(Status.RESETTING);
  }

  /**
   * The Ground this Tree will transform into.
   */
  public abstract Ground transformGround();

  /**
   * The condition for transforming the ground. True represents the ground should be transformed
   * this turn.
   */
  public abstract boolean transformCondition();

  /**
   * Transform the current location's ground into another when {@link Tree#transformCondition()}
   * holds.
   *
   * @param location The location of the ground to be transformed.
   */
  public void tick(Location location) {
    if (transformCondition()) {
      location.setGround(transformGround());
      if (Utils.roll(50)) {
        location.addItem(new FireFlower());
      }
    }
    // Trees reset into dirt.
    if (hasCapability(Status.RESETTING) && Utils.roll(50)) {
      location.setGround(new Dirt());
      removeInstance();
    }
    super.tick(location);
  }
}
