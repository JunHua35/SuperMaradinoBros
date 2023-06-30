package game.items;

import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.status.Status;

/**
 * A Wrench weapon. Wrench has a {@link Status#BLUNT} capability which can destroy Dormant enemies.
 */
public class Wrench extends WeaponItem {

  public static final String NAME = "Wrench";
  public static final char DISPLAY_CHAR = 'w';
  public static final int DAMAGE = 50;
  public static final String VERB = "smacks";
  public static final int HIT_RATE = 80;

  public Wrench() {
    super(NAME, DISPLAY_CHAR, DAMAGE, VERB, HIT_RATE);
    addCapability(Status.BLUNT);
  }
}
