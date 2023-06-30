package game.actors;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.PowerStar;
import game.items.SuperMushroom;
import game.items.Wrench;
import java.util.Map;

public class Toad extends NPC implements Merchant {

  /**
   * The goods this merchant is selling. <br> Wrench, 200 <br> SuperMushroom, 400 <br> PowerStar,
   * 600
   */
  private Map<Class<? extends Item>, Integer> goods;

  /**
   * Constructor.
   */
  public Toad() {
    super("Toad", 'O', 9999999);
    goods = Map.of(Wrench.class, 200,
        SuperMushroom.class, 400,
        PowerStar.class, 600);
  }

  /**
   * Returns a new BuyAction for every item in price <br>
   *
   * @param otherActor the Actor interested in buying items
   * @param direction  String representing the direction of the other Actor
   * @param map        current GameMap
   * @return
   */
  @Override
  public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
    return getBuyActions();
  }

  @Override
  public Map<Class<? extends Item>, Integer> getGoods() {
    return goods;
  }

}
