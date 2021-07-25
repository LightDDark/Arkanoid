package com.oryehezkel.gameenvironment.animation;

import biuoop.DrawSurface;
import biuoop.Sleeper;
import com.oryehezkel.gameobject.SpriteCollection;

/**
 * The CountdownAnimation will display the given gameScreen,
 * for numOfSeconds seconds, and on top of them it will show
 * a countdown from countFrom back to 1, where each number will
 * appear on the screen for (numOfSeconds / countFrom) seconds, before
 * it is replaced with the next one.
 */
public class CountdownAnimation implements Animation {
    private final double numOfSeconds;
    private int countFrom;
    private final SpriteCollection gameScreen;
    private long interval;
    private int originCount;

    /**
     * Constructor, gets seconds that will last and where to count from.
     * @param numOfSeconds seconds this will last.
     * @param countFrom number to count from.
     * @param gameScreen screen to act as background.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.originCount = countFrom;
        this.gameScreen = gameScreen;
        this.interval = (long) ((this.numOfSeconds / this.countFrom) * 1000);
    }

    /**
     *
     * @param d surface to draw on.
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        Sleeper sleep = new Sleeper();
        gameScreen.drawAllOn(d);
        d.drawText(375, 350, "" + this.countFrom, 100);
        if (this.countFrom != this.originCount) {
            sleep.sleepFor(interval);
        }
        this.countFrom--;
    }

    /**
     * Check if countdown passed zero.
     * @return true if countdown reached below zero.
     */
    public boolean shouldStop() {
        return this.countFrom == -1;
    }
}