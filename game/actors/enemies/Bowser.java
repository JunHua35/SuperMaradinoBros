package game.actors.enemies;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.BaseWeaponManager;
import game.items.Key;
import game.reset.Resettable;
import game.status.Status;

/**
 * Bowser is an enemy that will stay in one place until a hostile actor is nearby. Then, it will
 * attack the hostile actor and follow it. It has a flaming attack that leaves fire on the ground
 * whenever he attacks - damaging any actor over time if they stay on the same spot. When Bowser
 * dies, it will drop a key that ends the game.
 */
public class Bowser extends Enemy implements Resettable {

  private static final int BASE_HP = 500;

  /**
   * Bowser has the FLAMING capability as default that makes him shoot fire He also has the Key item
   * that Mario needs to obtain to win BaseWeapon for Bowser is the punch attack action
   */
  public Bowser() {
    super(new EnemyBuilder("Bowser", 'B', BASE_HP));
    addCapability(Status.FLAMING);
    addItemToInventory(new Key());
    registerInstance();
    resetBehaviours(); // Bowser doesn't wander around at first.
    BaseWeaponManager.getInstance().setBaseWeapon(this, 80, "punch");
  }

  /**
   * Resets Bowser to its original position if the Player decides to reset the game. Otherwise,
   * functions like a normal Enemy.
   */
  @Override
  public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
    if (hasCapability(Status.RESETTING)) {
      resetMaxHp(BASE_HP);
      int resetX = (map.getXRange().max() + map.getXRange().min()) / 2,
          resetY = (map.getYRange().max() + map.getYRange().min()) / 2;
      // make sure reset location is clear
      if (map.at(resetX, resetY).containsAnActor()) {
        map.removeActor(map.at(resetX, resetY).getActor());
      }
      map.moveActor(this, map.at(resetX, resetY));
      removeCapability(Status.RESETTING);
    }
    return super.playTurn(actions, lastAction, map, display);
  }

  /**
   * Adds the reset capability to Bowser. Capability will be removed in playTurn() after player
   * resets game
   */
  @Override
  public void resetInstance() {
    addCapability(Status.RESETTING);
    resetBehaviours();
  }
}
