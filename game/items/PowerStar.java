package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.status.Status;

/**
 * A Power Star item. Gives the actor immunity to damage, able to walk to high ground and instantly
 * kill enemies.
 */
public class PowerStar extends ConsumableItem {

  private static final String NAME = "Power Star";
  private static final char DISPLAY_CHAR = '*';
  private static final int HEAL_AMOUNT = 200;
  private static final int FADE_TURNS = 11; //extra one turn due to ticking before interaction
  private int turnsToFade;

  public PowerStar() {
    super(NAME, DISPLAY_CHAR, true);
    turnsToFade = FADE_TURNS;
  }

  /**
   * Add invincible capability and heals actor when consumed
   *
   * @param actor the actor using the ConsumableItem
   * @param map   The map actor is on.
   */
  @Override
  public void onUse(Actor actor, GameMap map) {
    actor.addCapability(Status.INVINCIBLE);
    actor.heal(HEAL_AMOUNT);
  }

  /**
   * Ticks power star's countdown. If countdown expires, remove it from the game.
   */
  @Override
  public void tick(Location currentLocation, Actor actor) {
    // fade from inventory if countdown expires
    super.tick(currentLocation, actor);
    turnsToFade--;
    if (turnsToFade == 0) {
      fade(actor);
    }
  }

  /**
   * Ticks power star's countdown. If countdown expires, remove it from the game.
   */
  @Override
  public void tick(Location currentLocation) {
    // fade from location if countdown expires
    super.tick(currentLocation);
    turnsToFade--;
    if (turnsToFade == 0) {
      fade(currentLocation);
    }
  }

  /**
   * Remove power star from this location.
   */
  private void fade(Location location) {
    location.removeItem(this);
  }

  /**
   * Remove power star from actor inventory.
   */
  private void fade(Actor actor) {
    actor.removeItemFromInventory(this);
  }

  @Override
  public String toString() {
    return turnsToFade == FADE_TURNS ? super.toString() :
        String.format("%s - %d turns remaining", NAME, turnsToFade);
  }
}
