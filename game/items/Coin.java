package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.reset.Resettable;
import game.status.Status;
import game.wallet.WalletManager;

/**
 * Represents a Coin item on the ground.
 */
public class Coin extends Item implements Resettable {

  private static final String NAME = "Coin";
  private static final char DISPLAY_CHAR = '$';
  /**
   * The value of this coin object.
   */
  private int value;

  public Coin(int value) {
    super(NAME, DISPLAY_CHAR, true);
    this.value = value;
    registerInstance();
  }

  /**
   * Removes coin from actor inventory and add the amount to actor's wallet. <br>
   *
   * @param currentLocation The location of the actor carrying this Item.
   * @param actor           The actor carrying this Item.
   */
  @Override
  public void tick(Location currentLocation, Actor actor) {
    super.tick(currentLocation, actor);
    actor.removeItemFromInventory(this);
    WalletManager.getInstance().transact(actor, value);
    removeInstance();
  }

  /**
   * Removes coin from the Ground when reset
   *
   * @param currentLocation The location of this Item.
   */
  @Override
  public void tick(Location currentLocation) {
    super.tick(currentLocation);
    // Remove coins when reset
    if (hasCapability(Status.RESETTING)) {
      currentLocation.removeItem(this);
      removeInstance();
    }
  }

  /**
   * Adds a capability to the coin so that it can be reset
   */
  @Override
  public void resetInstance() {
    addCapability(Status.RESETTING);
  }

  /**
   * @return the value of the current coin that is being picked up It will show on the console
   */
  @Override
  public String toString() {
    return String.format("%s $%d", NAME, value);
  }
}
