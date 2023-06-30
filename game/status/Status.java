package game.status;

import java.util.Map;

/**
 * Use this enum class to give `buff` or `debuff`. It is also useful to give a `state` to abilities
 * or actions that can be attached-detached.
 */
public enum Status {
  /**
   * Use this status to be considered hostile towards enemy (e.g., to be attacked by enemy)
   */
  HOSTILE_TO_ENEMY,
  /**
   * Use this status to tell that current instance has "grown"
   */
  TALL,
  /**
   * Use this status to tell that current instance has Invincible effect from PowerStar
   */
  INVINCIBLE,
  /**
   * Use this status to tell that current instance is dormant.
   */
  DORMANT,
  /**
   * A blunt weapon can attack dormant enemies.
   */
  BLUNT,
  /**
   * To represent a fertile ground.
   */
  FERTILE,
  /**
   * Use this status to tell that current instance is resetting.
   */
  RESETTING,
  /**
   * Actor has +15 base damage.
   */
  STRONG,
  /**
   * Gives the player/item a win condition, i.e. able to end the game when meeting princess.
   */
  WIN_CONDITION,
  /**
   * Gives the actor's attack to drop fire on the ground by {@link game.actions.AttackAction}
   */
  FLAMING,
  /**
   * Gives the actor the ability to fly over high grounds, e.g. walls and trees.
   */
  FLYING,
  /**
   * Use this status to tell player can teleport to this ground
   */
  TELEPORTABLE;
  public static final Map<Enum<?>, Integer> TEMPORARY_STATUS = Map.of(INVINCIBLE, 10,
      FLAMING, 20);
}
