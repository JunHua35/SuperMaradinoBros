package game.actors;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.EndGameAction;
import game.status.Status;

public class PrincessPeach extends NPC {

  /**
   * Constructor.
   *
   * @param name        the name of the Actor
   * @param displayChar the character that will represent the Actor in the display
   * @param hitPoints   the Actor's starting hit points
   */
  public PrincessPeach() {
    super("Princess Peach", 'P', 100);
  }

  /**
   * Allows the actor to save Princess Peach. Ends the game!
   */
  @Override
  public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
    ActionList actionList = new ActionList();
    if (otherActor.hasCapability(Status.WIN_CONDITION)) {
      actionList.add(new EndGameAction());
    }
    return actionList;
  }
}
