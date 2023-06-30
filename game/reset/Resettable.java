package game.reset;

public interface Resettable {

  /**
   * Allows any classes that use this interface to reset abilities, attributes, and/or items.
   */
  void resetInstance();

  /**
   * a default interface method that register current instance to the Singleton manager. It allows
   * corresponding class uses to be affected by global reset
   */
  default void registerInstance() {
    ResetManager.getInstance().appendResetInstance(this);
  }

  /**
   * Removes this instance from ResetManager
   */
  default void removeInstance() {
    ResetManager.getInstance().cleanUp(this);
  }
}
