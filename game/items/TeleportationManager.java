package game.items;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.status.Status;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class that connects a GameMap to a single Location. This allows actors to transition
 * between maps to teleport to and fro.
 */
public class TeleportationManager {

  /**
   * Maps locations to their teleport destinations.
   */
  private final Map<Location, Location> teleportLocations;
  /**
   * Maps locations to its map name so that it can be retrieved when instantiating teleport
   * actions.
   */
  private final Map<Location, String> mapName;
  private static TeleportationManager instance;

  private TeleportationManager() {
    teleportLocations = new HashMap<>();
    mapName = new HashMap<>();
  }

  public static TeleportationManager getInstance() {
    if (instance == null) {
      instance = new TeleportationManager();
    }
    return instance;
  }

  /**
   * Registers the teleport from/to locations so that it can be retrieved at runtime later.
   *
   * @param from The teleport origin
   * @param to   The destination of the teleport
   * @see TeleportationManager#getTeleportAction(Location)
   */
  public void registerLocation(Location from, Location to) {
    teleportLocations.put(from, to);
  }

  /**
   * Retrieves the action of teleporting to destination from the input location. The retrieved
   * location now sets its teleport destination as the input location. If there is an actor at the
   * teleport destination, instantly kill the actor there.
   *
   * @param from The departure location of teleport
   * @return null if input location is not previously registered, otherwise the teleport action to
   * move actor to registered destination from input location
   */
  public Action getTeleportAction(Location from) {
    if (!teleportLocations.containsKey(from)) {
      return null;
    }
    Location target = teleportLocations.get(from);
    teleportLocations.put(target, from);
    // if there is an actor on destination, kill it.
    if (target.getActor() != null) {
      target.getActor().hurt(999);
      target.map().removeActor(target.getActor());
    }
    String direction = "to other map"; // default map name
    if (mapName.containsKey(target)) {
      direction = "to " + mapName.get(target);
    }
    return new MoveActorAction(target, direction);
  }

  /**
   * Gives the map a name so player knows where they teleport to.
   *
   * @param map  The map to be given a name
   * @param name The new name of the map
   */
  public void setMapName(GameMap map, String name) {
    for (int x : map.getXRange()) {
      for (int y : map.getYRange()) {
        mapName.put(map.at(x, y), name);
      }
    }
  }

  /**
   * Connects all {@link Status#TELEPORTABLE} locations' grounds in map to destination, such that
   * those location can teleport actors there later on. Teleportable locations in map and
   * destination can retrieve their destinations when teleporting.
   *
   * @param map         The GameMap containing locations with Teleportable grounds  to be connected
   *                    with destination
   * @param destination The teleport destination
   */
  public void setTeleportLocation(GameMap map, Location destination) {
    for (int x : map.getXRange()) {
      for (int y : map.getYRange()) {
        if (map.at(x, y).getGround().hasCapability(Status.TELEPORTABLE)) {
          registerLocation(map.at(x, y), destination);
        }
      }
    }
  }
}
