package game.grounds;

import edu.monash.fit2099.engine.positions.Ground;
import game.status.Status;

/**
 * A class that represents bare dirt.
 */
public class Dirt extends Ground {

  public Dirt() {
    super('.');
    addCapability(Status.FERTILE);
  }
}
