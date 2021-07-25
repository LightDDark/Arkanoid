package com.oryehezkel.gamelogic;

import biuoop.GUI;
import biuoop.KeyboardSensor;
import com.oryehezkel.gameenvironment.animation.AnimationRunner;
import com.oryehezkel.gameenvironment.animation.GameOver;
import com.oryehezkel.gameenvironment.animation.KeyPressStoppableAnimation;
import com.oryehezkel.gameenvironment.animation.WonGame;

import java.util.List;

/**
 * @author Or Avraham Yehezkel
 * Game flow controls the level change and flow of the game.
 */
public class GameFlow {
    // keep game score
    private final Counter score = new Counter();
    // animation to run the game with and GUI to display it
    private final AnimationRunner animationRunner;
    private final KeyboardSensor keyboardSensor;
    private final GUI gui;
    // tell if player is lost
    private boolean lost = false;

    /**
     * Constructor, Get the Animation to the game.
     * @param ar the animation runner to run the game.
     */
    public GameFlow(AnimationRunner ar) {
        this.animationRunner = ar;
        this.keyboardSensor = ar.getGui().getKeyboardSensor();
        this.gui = ar.getGui();
    }

    /**
     * Runs the game and switch between levels as the game progresses.
     * @param levels levels info list to progress through.
     */
    public void runLevels(List<LevelInformation> levels) {
        // iterate the levels
        for (LevelInformation levelInfo : levels) {
            // create new game level
            GameLevel level = new GameLevel(levelInfo, this.animationRunner, this.score);
            // runs game
            level.initialize();
            level.run();
            // if there's no balls left, player lost.
            if (level.isBallEmpty()) {
                // runs game over screen.
                animationRunner.run(new KeyPressStoppableAnimation(keyboardSensor,
                        "space", new GameOver(score.getValue())));
                // flags the player has lost and exit the game loop
                this.lost = true;
                break;
            }
        }
        // if player didn't lose, display the Winning screen.
        if (!this.lost) {
            animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor,
                    "space", new WonGame(score.getValue())));
        }
        // close game window.
        gui.close();
    }
}