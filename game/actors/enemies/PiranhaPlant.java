package game.actors.enemies;

import game.BaseWeaponManager;

/**
 * An enemy that spawns on warp pipes only. It does not move and only attacks Mario
 */
public class PiranhaPlant extends Enemy {

  public static final int HIT_POINTS = 150;

  public PiranhaPlant() {
    super(new EnemyBuilder("Piranha Plant", 'Y', HIT_POINTS).immovable());
    resetBehaviours();
    BaseWeaponManager.getInstance().setBaseWeapon(this, 150, "chomps");
  }

}
