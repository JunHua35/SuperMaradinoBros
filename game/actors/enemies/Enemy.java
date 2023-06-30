package game.actors.enemies;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.AttackAction;
import game.actors.NPC;
import game.behaviors.AttackBehaviour;
import game.behaviors.FollowBehaviour;
import game.behaviors.WanderBehaviour;
import game.status.Status;

/**
 * Enemies are targetable actors that can be attacked by the player. If the enemy is movable, it
 * wanders around the map at first, and when the player engages in combat with enemy, enemy will try
 * to attack the player if in range otherwise follow the player if it can move. By default, an Enemy
 * is movable.
 */
public abstract class Enemy extends NPC {

  /**
   * Sets to true if this Enemy can move.
   */
  private boolean movable;

  /**
   * Constructor for Enemy class. Use the nested {@link EnemyBuilder} class to initialize enemy
   * attributes, and pass the Builder object to the constructor.
   *
   * @param builder Builder object containing the attributes for Enemy class.
   */
  public Enemy(EnemyBuilder builder) {
    super(builder.name, builder.displayChar, builder.hitPoints);
    movable = builder.movable;
    if (movable) {
      addBehaviour(10, new WanderBehaviour());
    }
  }

  /**
   * At the moment, we only make it can be attacked by Player. As soon as Player can attack the
   * Enemy, Enemy will be aggregated and will either attack the player if possible or follow the
   * player if it can move
   *
   * @param otherActor the Actor that might perform an action.
   * @param direction  String representing the direction of the other Actor
   * @param map        current GameMap
   * @return list of actions
   * @see Status#HOSTILE_TO_ENEMY
   */
  @Override
  public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
    ActionList actions = new ActionList();
    // it can be attacked only by the HOSTILE opponent, and this action will not attack the HOSTILE enemy back.
    if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
      addBehaviour(1, new AttackBehaviour(otherActor));
      if (movable) {
        addBehaviour(5, new FollowBehaviour(otherActor));
      }
      actions.add(new AttackAction(this, direction));
    }
    return actions;
  }

  /**
   * Builder class for enemy. Stores enemy attributes in this class, and instantiate them
   * accordingly when this object is passed to the constructor.
   */
  public static class EnemyBuilder {

    private String name;
    private char displayChar;
    private int hitPoints;
    private boolean movable;

    /**
     * Constructor to store Enemy attributes when instantiated.
     *
     * @param name        The name of the Enemy
     * @param displayChar The display character when printed
     * @param hitPoints   The enemy hitpoints
     */
    public EnemyBuilder(String name, char displayChar, int hitPoints) {
      this.name = name;
      this.displayChar = displayChar;
      this.hitPoints = hitPoints;
      movable = true;
    }

    /**
     * Change the name of instantiated enemy when this object is passed to {@link
     * Enemy#Enemy(EnemyBuilder)}
     *
     * @param name A new name for the enemy
     */
    public EnemyBuilder name(String name) {
      this.name = name;
      return this;
    }

    /**
     * Change the display character of instantiated enemy when this object is passed to {@link
     * Enemy#Enemy(EnemyBuilder)}
     *
     * @param displayChar The new display character for the enemy.
     */
    public EnemyBuilder displayChar(char displayChar) {
      this.displayChar = displayChar;
      return this;
    }

    /**
     * Change the hit points of instantiated enemy when this object is passed to {@link
     * Enemy#Enemy(EnemyBuilder)}
     *
     * @param hitPoints The new maximum hit points for the Enemy.
     */
    public EnemyBuilder hitPoints(int hitPoints) {
      this.hitPoints = hitPoints;
      return this;
    }

    /**
     * Sets the instantiated enemy to immovable when this object is passed to {@link
     * Enemy#Enemy(EnemyBuilder)}
     */
    public EnemyBuilder immovable() {
      this.movable = false;
      return this;
    }
  }
}
