package com.oryehezkel.gameenvironment.animation.background;

import biuoop.DrawSurface;
import com.oryehezkel.gamelogic.GameLevel;
import com.oryehezkel.gameobject.Ball;
import com.oryehezkel.gameobject.Sprite;
import com.oryehezkel.geometry.Point;

import java.awt.Color;
import java.util.Random;

/**
 * @author Or Avraham Yehezkel.
 * Background of Game level.
 */
public class Winter implements Sprite {
    // array for clouds and rain, represented by palls and points.
    private final Ball[] clouds;
    private final Point[] rain;

    /**
     * Construct Arrays for clouds and rains, randomly generated.
     */
    public Winter() {
        this.rain = new Point[500];
        this.clouds = new Ball[400];
        Random rand = new Random();
        // generate rain above the screen randomly.
        for (int i = 0; i < 500; i++) {
            this.rain[i] = new Point(rand.nextInt(800), rand.nextInt(600) - 600);
        }
        // generate clouds randomly across screen top, half grey and half dark grey.
        for (int i = 0; i < 200; i++) {
            this.clouds[i] = new Ball(rand.nextInt(800), rand.nextInt(15) + 50,
                    rand.nextInt(25), Color.GRAY);
        }
        for (int i = 200; i < 400; i++) {
            this.clouds[i] = new Ball(rand.nextInt(800), rand.nextInt(15) + 50,
                    rand.nextInt(15), Color.GRAY.darker());
        }
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
        // Colour the screen bright grey.
        d.setColor(Color.GRAY.brighter());
        d.fillRectangle(25, 45, 760, 560);
        d.setColor(Color.BLUE);
        // draw rain
        for (int i = 0; i < 100; i++) {
            d.fillOval((int) this.rain[i].getX(), (int) this.rain[i].getY(), 4, 10);
        }
        // draw clouds.
        for (int i = 0; i < 300; i++) {
            d.setColor(clouds[i].getColor());
            d.fillCircle(clouds[i].getX(), clouds[i].getY(), clouds[i].getSize());
        }
    }

    /**
     * notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {
        Random rand = new Random();
        // let the rain fall
        for (int i = 0; i < 100; i++) {
            // if rain crossed the screen then generate it again.
            if (this.rain[i].getY() > 600) {
                this.rain[i].setPoint(new Point(rand.nextInt(800), rand.nextInt(600) - 600));
            } else {
                this.rain[i].setPoint(new Point(this.rain[i].getX(), this.rain[i].getY() + 4));
            }
        }
    }
}
