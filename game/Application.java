package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.actors.NPC;
import game.actors.Player;
import game.actors.PrincessPeach;
import game.actors.Toad;
import game.actors.enemies.Bowser;
import game.fountain.HealthFountain;
import game.fountain.PowerFountain;
import game.grounds.Dirt;
import game.grounds.Floor;
import game.grounds.Lava;
import game.grounds.Wall;
import game.grounds.WarpPipe;
import game.grounds.trees.Mature;
import game.grounds.trees.Sapling;
import game.grounds.trees.Sprout;
import game.items.SuperMushroom;
import game.items.TeleportationManager;
import game.items.Wrench;
import java.util.Arrays;
import java.util.List;

/**
 * The main class for the Mario World game.
 */
public class Application {

  public static void main(String[] args) {

    World world = new World(new Display());

    FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(),
        new Sprout(), new Sapling(), new Mature(), new WarpPipe(), new PowerFountain(),
        new HealthFountain(), new Lava());

    List<String> map = Arrays.asList(
        "..........................................##..........+.........................",
        "............+...............................#...................................",
        "............................................#..........C........................",
        "....A.......................t................##......................+..........",
        "...............................................#................................",
        "........................#####.............C.....#......................H........",
        ".................+......#...#.....................#......C......................",
        "........................#...#....................##.............................",
        "........................#...#.............CHA...##..............................",
        ".........+..............##.##...........+#___####..................+............",
        ".............t.........................+#_____###++.............................",
        "..........................T............+#______###..........H...................",
        "........................................+#_____###..............................",
        "..............A.........+........................##.............+...............",
        "......................................C............#............................",
        "......t.............................................#...........................",
        "...................+...........t.....................#..........................",
        "......................................................#............t............",
        "...............T..........................H............##.......................");

    List<String> newMap = Arrays.asList(
        "C..AH............+...............+..........LLLL.................",
        ".........................+.................LLL...................",
        "...........................................LLLL..................",
        "..........t...........t...............................+..........",
        "...........................H...........+.........................",
        "........LLLLL........................................H...........",
        ".......LLLLLLL...+...............t...............................",
        ".......LLLLLLL...................................................",
        "................................................##...............",
        "............................A...........+#____####...............",
        ".....+.................................+#_____###++..............",
        "........................+..................................+.....",
        "............LLLL......................t..........................",
        ".............LLL.................................................",
        "..+..................+.........H...................t.............");

    GameMap gameMap = new GameMap(groundFactory, map), gameMap2 = new GameMap(groundFactory,
        newMap);
    world.addGameMap(gameMap);
    world.addGameMap(gameMap2);

    Actor mario = new Player();
    mario.increaseMaxHp(10000000);
    world.addPlayer(mario, gameMap.at(43, 9));
    gameMap.at(43, 12).addItem(new SuperMushroom());
//    gameMap.at(43, 11).addItem(new PowerStar());
    gameMap.at(42, 10).addItem(new Wrench());

    NPC toad = new Toad();
    gameMap.at(26, 7).addActor(toad);

    Actor bowser = new Bowser();
    int x = (gameMap2.getXRange().max() + gameMap2.getXRange().min()) / 2;
    int y = (gameMap2.getYRange().max() + gameMap2.getYRange().min()) / 2;
    gameMap2.at(x, y)
        .addActor(bowser); //set as middle of the map so that it resets location properly

    Actor peach = new PrincessPeach();
    gameMap2.at(x + 1, y + 1).addActor(peach);

    TeleportationManager.getInstance().setTeleportLocation(gameMap, gameMap2.at(0, 0));
    TeleportationManager.getInstance().setMapName(gameMap, "First Map");
    TeleportationManager.getInstance().setMapName(gameMap2, "Lava zone");
    world.run();

  }
}
