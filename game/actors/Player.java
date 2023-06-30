package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.BaseWeaponManager;
import game.fountain.BottleManager;
import game.reset.Resettable;
import game.reset.Resetter;
import game.status.Status;
import game.status.StatusManager;
import game.wallet.WalletManager;
import java.util.Arrays;
import java.util.List;

/**
 * Class representing the Player.
 */
public class Player extends Actor implements Resettable {

  private final Menu menu = new Menu();

  /**
   * Constructor.
   *
   * @param name        Name to call the player in the UI
   * @param displayChar Character to represent the player in the UI
   * @param hitPoints   Player's starting number of hitpoints
   */
  public Player(String name, char displayChar, int hitPoints) {
    super(name, displayChar, hitPoints);
    this.addCapability(Status.HOSTILE_TO_ENEMY);
    addItemToInventory(new Resetter());
    BottleManager.getInstance().addBottle(this);
    WalletManager.getInstance().addWallet(this);
    registerInstance();
    BaseWeaponManager.getInstance().setBaseWeapon(this, 5, "punches");
  }

  public Player() {
    this("Mario", 'm', 500);
  }

  /**
   * Prompts the user to choose the next action to be executed. Will also tick the status manager to
   * track temporary status.
   *
   * @see StatusManager
   */
  @Override
  public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
    StatusManager.getInstance().tick(this);
    if (!isConscious()) {
      map.removeActor(this);
      return new DoNothingAction();
    }
    // Handle multi-turn Actions
    if (lastAction.getNextAction() != null) {
      return lastAction.getNextAction();
    }

    printStatus(display, map.locationOf(this));
    // return/print the console menu
    return menu.showMenu(this, actions, display);
  }

  @Override
  public char getDisplayChar() {
    return this.hasCapability(Status.TALL) ? Character.toUpperCase(super.getDisplayChar())
        : super.getDisplayChar();
  }

  /**
   * Resets the player by removing capabilities and reset max health.
   */
  @Override
  public void resetInstance() {
    this.resetMaxHp(this.getMaxHp());
    this.removeCapability(Status.INVINCIBLE);
    this.removeCapability(Status.TALL);
    StatusManager.getInstance().resetTrackedCapabilities(this);
  }

  /**
   * Prints the current status of Player to the user like a simple UI.
   */
  private void printStatus(Display display, Location location) {
    display.println(
        String.format("%s's HP:%s at (%d, %d)", name, printHp(), location.x(), location.y()));
    List<String> outString = Arrays.asList(
        WalletManager.getInstance().toString(this),
        StatusManager.getInstance().toString(this)
    );
    for (String s : outString) {
      if (s != null) {
        display.println(s);
      }
    }
    if (hasCapability(Status.INVINCIBLE)) {
      display.println(String.format("%s IS INVINCIBLE!", name));
    }
    if (hasCapability(Status.FLAMING)) {
      display.println(String.format("%s HAS FIRE ATTACK!", name));
    }
  }

  /**
   * Adds Capability to the actor. Additionally passes the capability to Status Manager to track
   * temporary capabilities.
   */
  @Override
  public void addCapability(Enum<?> capability) {
    super.addCapability(capability);
    StatusManager.getInstance().addCapability(this, capability);
  }

  @Override
  protected IntrinsicWeapon getIntrinsicWeapon() {
    return BaseWeaponManager.getInstance().getBaseWeapon(this);
  }
}
