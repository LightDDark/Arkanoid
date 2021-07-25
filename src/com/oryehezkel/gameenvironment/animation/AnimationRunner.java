package com.oryehezkel.gameenvironment.animation;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * @author Or Avraham Yehezkel.
 * Runs different Animations.
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private final Sleeper sleeper = new Sleeper();

    /**
     * Constructor, gets the Gui and FPS we'll be using.
     * @param framesPerSecond FPS of animation.
     */
    public AnimationRunner(int framesPerSecond) {
        this.gui = new GUI("Arkanoid", 800, 600);
        this.framesPerSecond = framesPerSecond;
    }

    /**
     * Runs the game, start the animation loop.
     * @param animation the animation to run.
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / this.framesPerSecond;
        // start animation
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();

            animation.doOneFrame(d);

            gui.show(d);
            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    /**
     * @return this GUI.
     */
    public GUI getGui() {
        return gui;
    }
}
