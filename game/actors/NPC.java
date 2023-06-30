package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.BaseWeaponManager;
import game.behaviors.Behaviour;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * A Non-Playable Character (NPC) automatically executes an action based on defined behaviours.
 */
public abstract class NPC extends Actor {

  /**
   * Maps priority to the action to be executed. The lower the number, the higher the priority.
   */
  private final SortedMap<Integer, Behaviour> behaviours;

  /**
   * Constructor.
   *
   * @param name        the name of the Actor
   * @param displayChar the character that will represent the Actor in the display
   * @param hitPoints   the Actor's starting hit points
   */
  protected NPC(String name, char displayChar, int hitPoints) {
    super(name, displayChar, hitPoints);
    behaviours = new TreeMap<>();
  }

  /**
   * Adds a behaviour to NPC's behaviour list. See {@link NPC#playTurn(ActionList, Action, GameMap,
   * Display)}} to understand how priority and behaviour affects the returned action.
   *
   * @param priority  The priority of the behaviour. The lower the number, the higher the priority.
   * @param behaviour The behaviour that the NPC wants to exhibit.
   */
  public void addBehaviour(Integer priority, Behaviour behaviour) {
    behaviours.put(priority, behaviour);
  }

  /**
   * Removes all defined behaviours
   */
  public void resetBehaviours() {
    behaviours.clear();
  }

  public void removeBehaviour(int priority) {
    behaviours.remove(priority);
  }

  @Override
  protected IntrinsicWeapon getIntrinsicWeapon() {
    return BaseWeaponManager.getInstance().getBaseWeapon(this);
  }

  /**
   * Iterates through behaviours from the highest priority and chooses the first action returned by
   * defined behaviours. If no behaviours returns an action, NPC will do nothing this turn.
   *
   * @see Actor#playTurn(ActionList, Action, GameMap, Display)
   */
  @Override
  public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
    for (Map.Entry<Integer, Behaviour> entry : behaviours.entrySet()) {
      Behaviour behaviour = entry.getValue();
      Action action = behaviour.getAction(this, map);
      if (action != null) {
        return action;
      }
    }
    return new DoNothingAction();
  }
}
