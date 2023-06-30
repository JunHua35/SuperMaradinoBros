package game.grounds;

/**
 * A class that represents a wall high ground. Actors need to jump here.
 */
public class Wall extends HighGround {

  public static final int FALL_DAMAGE = 20;
  public static final int SUCCESS_RATE = 80;

  public Wall() {
    super('#');
  }

  public Wall(char displayChar) {
    super(displayChar);
  }

  @Override
  public int getSuccessRate() {
    return SUCCESS_RATE;
  }

  @Override
  public int getFallDamage() {
    return FALL_DAMAGE;
  }

  @Override
  public boolean blocksThrownObjects() {
    return true;
  }

  @Override
  public String toString() {
    return "Wall";
  }
}
