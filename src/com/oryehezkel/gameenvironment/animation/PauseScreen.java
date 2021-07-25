package com.oryehezkel.gameenvironment.animation;

import biuoop.DrawSurface;


/**
 * @author Or Avraham Yehezkel.
 * Shows Pause Screen until cancelled.
 */
public class PauseScreen implements Animation {

    /**
     * Draw Pause message on d.
     * @param d surface to draw on.
     */
    public void doOneFrame(DrawSurface d) {
        d.drawText(10, d.getHeight() / 2, "paused -- press space to continue", 32);
    }

    /**
     * @return false.
     */
    public boolean shouldStop() {
        return false;
    }
}
