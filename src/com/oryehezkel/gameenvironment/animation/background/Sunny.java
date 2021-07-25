package com.oryehezkel.gameenvironment.animation.background;

import biuoop.DrawSurface;
import com.oryehezkel.gamelogic.GameLevel;
import com.oryehezkel.gameobject.Sprite;

import java.awt.Color;

/**
 * @author Or Avraham Yehezkel.
 * Background of Game level.
 */
public class Sunny implements Sprite {
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
        // color the screen cyan.
        d.setColor(Color.CYAN);
        d.fillRectangle(25, 45, 760, 560);
        // draw circle for sun.
        d.setColor(Color.ORANGE);
        d.fillCircle(140, 175, 60);
        // draw sun rays with lines.
        for (int i = 0; i < 80; i++) {
            d.drawLine(140, 175, 770 - (i * 10), 300);
        }
        // draw circle for sun core.
        d.setColor(Color.YELLOW);
        d.fillCircle(140, 175, 50);
    }

    /**
     * notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {
        //does nothing
    }
}
