package com.oryehezkel.gameenvironment.animation;
import biuoop.DrawSurface;

/**
 * Represents Animated Objects.
 */
public interface Animation {
    /**
     * game-specific logic of the animation.
     * @param d surface to draw on.
     */
    void doOneFrame(DrawSurface d);

    /**
     * stopping conditions to the animation.
     * @return true if should stop and false otherwise.
     */
    boolean shouldStop();
}
