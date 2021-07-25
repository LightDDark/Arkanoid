package com.oryehezkel.gameenvironment.animation.background;

import biuoop.DrawSurface;
import com.oryehezkel.gamelogic.GameLevel;
import com.oryehezkel.gameobject.Sprite;
import com.oryehezkel.geometry.Point;

import java.awt.Color;

/**
 * @author Or Avraham Yehezkel.
 * Background of Game level.
 */
public class Sniper implements Sprite {
    // have center of "sniper" as parameter.
    private final Point center;

    /**
     * Construct a center for the sniper.
     */
    public Sniper() {
        this.center = new Point(400, 215);
    }
    /**
     * adds object to the game.
     *
     * @param g game to add to.
     */
    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d drawsurface to draw on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        // determine Circles parameters
        int radius = 30;
        int centerY = (int) this.center.getY();
        int centerX = (int) this.center.getX();
        // fill screen with black.
        d.setColor(Color.BLACK);
        d.fillRectangle(25, 45, 760, 560);
        // draw circles on it.
        d.setColor(Color.BLUE);
        for (int i = 0; i < 3; i++) {
            d.drawCircle(centerX, centerY, radius * (i + 1));
        }
        // draw sniper lines.
        d.drawLine(centerX, centerY, centerX + 100, centerY);
        d.drawLine(centerX, centerY, centerX - 100, centerY);
        d.drawLine(centerX, centerY, centerX, centerY + 100);
        d.drawLine(centerX, centerY, centerX, centerY - 100);
    }

    /**
     * notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {
        // do nothing
    }
}
