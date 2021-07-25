import com.oryehezkel.gameenvironment.animation.AnimationRunner;
import com.oryehezkel.gamelogic.GameFlow;
import com.oryehezkel.gamelogic.LevelInformation;
import com.oryehezkel.gamelogic.level.LevelFour;
import com.oryehezkel.gamelogic.level.LevelOne;
import com.oryehezkel.gamelogic.level.LevelThree;
import com.oryehezkel.gamelogic.level.LevelTwo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Or Avraham Yehezkel.
 * Ass6Game runs the game with different levels according to args.
 */
public class Ass6Game {
    // Game basic info.
    private static final int NUM_LEVELS = 4;
    private static final int NO_ARGS = 0;

    /**
     * Main class, runs the game according to args.
     * @param args cmd arguments.
     */
    public static void main(String[] args) {
        // maps the number strings to their perspective numbers
        Map<String, LevelInformation> levelInformationMap = new HashMap<>(NUM_LEVELS);
        levelInformationMap.put("1", new LevelOne());
        levelInformationMap.put("2", new LevelTwo());
        levelInformationMap.put("3", new LevelThree());
        levelInformationMap.put("4", new LevelFour());
        List<LevelInformation> gameInfos = new ArrayList<>();
        // if there's args then fill out info acc to it
        if (args.length != NO_ARGS) {
            for (int i = 0; i < args.length; i++) {
                LevelInformation tempInfo = levelInformationMap.get(args[i]);
                // if null, don't add it
                if (tempInfo != null) {
                    gameInfos.add(tempInfo);
                }
            }
        }
        // else add 4 levels one after the another.
        if (gameInfos.isEmpty()) {
            for (int i = 1; i < 5; i++) {
                gameInfos.add(levelInformationMap.get("" + i));
            }
        }
        AnimationRunner anime = new AnimationRunner(60);
        // create a new game
        GameFlow game = new GameFlow(anime);
        // runs it
        game.runLevels(gameInfos);
    }
}
