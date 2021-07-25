package com.oryehezkel.gameenvironment.animation.background;

import biuoop.DrawSurface;
import com.oryehezkel.gamelogic.GameLevel;
import com.oryehezkel.gameobject.Sprite;

import java.awt.Color;

/**
 * @author Or Avraham Yehezkel.
 * Background of Game level.
 */
public class Tower implements Sprite {
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
        // Colour the screen green.
        d.setColor(Color.GREEN.darker().darker());
        d.fillRectangle(25, 45, 760, 560);
        // draw Tower Base.
        d.setColor(Color.BLACK);
        d.fillRectangle(75, 400, 100, 200);
        // Draw the tower roof
        d.setColor(Color.DARK_GRAY);
        d.fillRectangle(110, 350, 30, 50);
        // draw the tower pole
        d.setColor(Color.GRAY);
        d.fillRectangle(120, 100, 10, 250);
        // draw tower light
        for (int i = 0; i < 3; i++) {
            d.setColor(new Color(Color.RED.getRGB() + i * 45421));
            d.fillCircle(125, 90, 10 - i * 3);
        }
        // draw tower windows
        d.setColor(Color.WHITE);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                d.fillRectangle(80 + j * 20, 410 + i * 40, 12, 30);
            }
        }
    }

    /**
     * notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {
        // do nothing
    }
}
