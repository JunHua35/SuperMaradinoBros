package game.actors.enemies;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.BaseWeaponManager;
import game.actions.DestroyAction;
import game.items.SuperMushroom;
import game.reset.Resettable;
import game.status.Status;

/**
 * A class that represents all variations of Koopa, i.e. (Normal) Koopa and Flying Koopa. Koopas
 * enter dormant status when defeated and must use a Wrench to destroy it. They will drop a
 * SuperMushroom on death. Use {@link KoopaBuilder} to instantiate the desired version of Koopa.
 */
public class Koopa extends Enemy implements Resettable {

  /**
   * Spawns a normal Koopa for lazy initialization.
   */
  private Koopa() {
    this(new KoopaBuilder());
  }

  /**
   * Instantiates the desired variation of Koopa.
   */
  private Koopa(KoopaBuilder builder) {
    super(builder);
    addItemToInventory(new SuperMushroom()); // Koopa drops Mushroom on death.
    registerInstance();
    BaseWeaponManager.getInstance().setBaseWeapon(this, 30, "punches");
  }

  /**
   * If Koopa is unconscious, it enters a dormant status and will not move.
   */
  @Override
  public void hurt(int points) {
    super.hurt(points);
    if (!isConscious()) {
      resetMaxHp(1);
      resetBehaviours();
      addCapability(Status.DORMANT);
    }
  }

  /**
   * If the Koopa is currently Dormant, the other actor must have a Blunt weapon to destroy it. If
   * the Koopa isn't Dormant, it behaves like a normal WanderEnemy.
   */
  @Override
  public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
    // Checks If Koopa is Dormant and can be attacked
    if (hasCapability(Status.DORMANT)) {
      ActionList actionList = new ActionList();
      if (otherActor.hasCapability(Status.BLUNT)) {
        actionList.add(new DestroyAction(this, direction));
      }
      return actionList;
    }
    return super.allowableActions(otherActor, direction, map);

  }

  /**
   * If the Koopa is resetting, remove it from the game. Otherwise, it behaves like a normal
   * WanderEnemy.
   *
   * @see Enemy
   */
  @Override
  public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
    if (hasCapability(Status.RESETTING)) {
      map.removeActor(this);
      removeInstance();
      return new DoNothingAction();
    }
    return super.playTurn(actions, lastAction, map, display);
  }

  @Override
  public void resetInstance() {
    addCapability(Status.RESETTING);
  }

  @Override
  public String toString() {
    return hasCapability(Status.DORMANT) ? "Koopa(Dormant)" : super.toString();
  }

  @Override
  public char getDisplayChar() {
    return hasCapability(Status.DORMANT) ? 'D' : super.getDisplayChar();
  }

  /**
   * Koopa builder to spawn the desired variation of a Koopa. By default, {@link
   * KoopaBuilder#build()} returns a normal Koopa. The user can use methods of this class to set
   * {@code build} to spawn the desired version.
   */
  public static class KoopaBuilder extends EnemyBuilder {

    private boolean flying;

    /**
     * Sets the default attributes for Koopa
     */
    public KoopaBuilder() {
      super("Koopa", 'K', 100);
      flying = false;
    }

    /**
     * Spawns a Flying Koopa when build is called.
     */
    public KoopaBuilder setFlying() {
      name("Flying Koopa")
          .displayChar('F')
          .hitPoints(150);
      flying = true;
      return this;
    }

    /**
     * Builds the Koopa with given settings. By default, this method returns a normal Koopa. Use
     * methods of this class to build the desired Koopa variation.
     *
     * @return A newly instantiated Koopa based on state of the Koopa Builder
     */
    public Koopa build() {
      Koopa koopa = new Koopa(this);
      if (flying) {
        koopa.addCapability(Status.FLYING);
      }
      return koopa;
    }
  }
}
