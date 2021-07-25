package com.oryehezkel.gameenvironment.animation;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * @author Or Avraham Yehezkel.
 * Screen that displays game won with final score.
 */
public class WonGame implements Animation {
    // final score
    private final int score;

    /**
     * Construct a Game won screen with given score.
     * @param score score given to display.
     */
    public WonGame(int score) {
        this.score = score;
    }

    /**
     * game-specific logic of the animation.
     *
     * @param d surface to draw on.
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        // cover screen wit pink
        d.setColor(Color.PINK);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        // display game won
        d.setColor(Color.ORANGE.darker());
        d.drawText(100, d.getHeight() / 2, "You Win!", 70);
        d.setColor(Color.white);
        d.drawText(100, d.getHeight() / 2 + 50, "Your score is " + score, 25);
    }

    /**
     * stopping conditions to the animation.
     *
     * @return true if should stop and false otherwise.
     */
    @Override
    public boolean shouldStop() {
        return false;
    }
}
