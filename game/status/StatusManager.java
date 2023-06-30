package game.status;

import edu.monash.fit2099.engine.capabilities.Capable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A singleton that tracks a Capable's temporary Status. If an added Capability to a Capable is
 * temporary, this class will automatically remove the Capability when the countdown expires.
 * Capable who use this class to track status are expected to tick the StatusManager as well in
 * their playTurn or tick method.
 */
public class StatusManager {

  /**
   * Maps a Capable to its own Map that maps temporary status to turns left.
   */
  private Map<Capable, Map<Enum<?>, Integer>> tracker;
  private static StatusManager instance;

  private StatusManager() {
    tracker = new HashMap<>();
  }

  public static StatusManager getInstance() {
    if (instance == null) {
      instance = new StatusManager();
    }
    return instance;
  }

  /**
   * Begins tracking a status for capable if the status is temporary.
   *
   * @param capable The capable to track
   */
  public void addCapability(Capable capable, Enum<?> capability) {
    tracker.putIfAbsent(capable, new HashMap<>());
    Map<Enum<?>, Integer> capableMap = tracker.get(capable);
    if (Status.TEMPORARY_STATUS.containsKey(capability)) {
      capableMap.put(capability, Status.TEMPORARY_STATUS.get(capability) + 1);
    }
  }

  public void resetTrackedCapabilities(Capable capable) {
    tracker.remove(capable);
  }

  /**
   * Reduces the turns left of each temporary status for the provided Capable. If turns reaches 0,
   * remove the status from capable.
   */
  public void tick(Capable capable) {
    if (!tracker.containsKey(capable)) {
      return;
    }
    Map<Enum<?>, Integer> turnsLeft = tracker.get(capable);
    for (Iterator<Map.Entry<Enum<?>, Integer>> it = turnsLeft.entrySet().iterator();
        it.hasNext(); ) {
      Map.Entry<Enum<?>, Integer> entry = it.next();
      entry.setValue(entry.getValue() - 1);
      if (entry.getValue() == 0) {
        capable.removeCapability(entry.getKey());
        it.remove();
      }
    }
    if (turnsLeft.isEmpty()) {
      tracker.remove(capable);
    }
  }

  public String toString(Capable capable) {
    if (!tracker.containsKey(capable)) {
      return null;
    }
    Map<Enum<?>, Integer> turnsLeft = tracker.get(capable);
    List<String> outString = new ArrayList<>();
    turnsLeft.forEach((k, v) -> outString.add(String.format("%s - %d Turns Left", k, v)));
    return String.join("\n", outString);
  }
}
