package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Utils;
import game.grounds.HighGround;
import game.status.Status;
import java.util.Map;

/**
 * For an actor to jump to a {@link HighGround}
 */
public class JumpAction extends Action {

  /**
   * A hotkey map so that user can choose which direction to jump more naturally from menu.
   */
  private static final Map<String, String> HOTKEYS =
      Map.of("North", "8",
          "North-East", "9",
          "East", "6",
          "South-East", "3",
          "South", "2",
          "South-West", "1",
          "West", "4",
          "North-West", "7");
  private Location jumpToLocation;
  /**
   * One of the 8-d navigation
   */
  private String direction;
  /**
   * Or the command key
   */
  private String hotKey;
  /**
   * The high ground to jump into.
   */
  private HighGround highGround;

  /**
   * Constructor to create an Action that will move the Actor to a location that has a {@link
   * HighGround}.
   *
   * @param jumpToLocation Location to jump to
   * @param direction      String describing the direction to move in, e.g. "north"
   * @param highGround     The high ground to jump to.
   */
  public JumpAction(Location jumpToLocation, String direction, HighGround highGround) {
    this.jumpToLocation = jumpToLocation;
    this.direction = direction;
    this.highGround = highGround;
    this.hotKey = JumpAction.HOTKEYS.get(direction);
  }

  /**
   * Allow the Actor to jump to location
   * <p>
   * Overrides Action.execute()
   *
   * @param actor The actor performing the action.
   * @param map   The map the actor is on.
   * @see Action#execute(Actor, GameMap)
   */
  @Override
  public String execute(Actor actor, GameMap map) {
    int chance = actor.hasCapability(Status.TALL) ? 100 :
        highGround.getSuccessRate(),
        damage = highGround.getFallDamage();
    if (!Utils.roll(chance)) {
      Utils.hurtActor(actor, map, damage);
      if (!actor.isConscious()) {
        return actor + " jumps to his death!";
      }
      return "Failed jump";
    }
    map.moveActor(actor, jumpToLocation);
    return String.format("%s jumped and is on top of %s", actor, highGround);
  }

  /**
   * Returns a description of this movement suitable to display in the menu.
   *
   * @param actor The actor performing the action.
   * @return a String, e.g. "Player jumps east"
   */
  @Override
  public String menuDescription(Actor actor) {
    return String.format("%s jumps to %s %s", actor, direction, highGround);
  }

  /**
   * Returns this Action's hotkey.
   *
   * @return the hotkey
   */
  @Override
  public String hotkey() {
    return hotKey;
  }
}
