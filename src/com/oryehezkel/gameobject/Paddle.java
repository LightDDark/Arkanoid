package com.oryehezkel.gameobject;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import com.oryehezkel.gamelogic.GameLevel;
import com.oryehezkel.gamelogic.Velocity;
import com.oryehezkel.geometry.Point;
import com.oryehezkel.geometry.Rectangle;

import java.awt.Color;

/**
 * Paddle objects implements both Sprite and Colloidal, represents of the Player in the game.
 * can be moved, and collide with.
 */
public class Paddle implements Sprite, Collidable {
    // fields for type and user input.
    private final biuoop.KeyboardSensor keyboard;
    private Block paddle;
    // borders that can be moved within
    private final int borderLeft;
    private final int borderRight;
    // all parameters of paddle
    private double width;
    private static final double HEIGHT = 13;
    private static final Color COLOUR = Color.ORANGE;
    private static final double[] ANGLES = {-60, -30, 0, 30, 60};
    private int step;
    private static final int REGIONS = 5;

    /**
     * moves the paddle one STEP to the left.
     */
    public void moveLeft() {
        // gets the paddle upper left.
        double upperLeftX = this.paddle.getCollisionRectangle().getUpperLeft().getX();
        // if can move step without reaching border.
        if ((upperLeftX - step) >= borderLeft) {
            move(-step);
            // else moves till border.
        } else if (upperLeftX - borderLeft != 0) {
            move(-((int) upperLeftX - borderLeft));
        }
    }

    /**
     * moves the paddle one STEP to the right.
     */
    public void moveRight() {
        // gets the paddle upper left.
        double upperLeftX = this.paddle.getCollisionRectangle().getUpperLeft().getX();
        // if can move step without reaching border.
        if ((upperLeftX + step + width) <= borderRight) {
            move(step);
            // else moves till border.
        } else if (borderRight - (upperLeftX + width) != 0) {
            move((int) (borderRight - (upperLeftX + width)));
        }
    }

    /**
     * move steps in x axis.
     * @param steps steps to move.
     */
    private void move(int steps) {
        // get current x.
        Point formerPoint = this.paddle.getCollisionRectangle().getUpperLeft();
        // adds steps to it.
        Rectangle shape = new Rectangle(new Point(formerPoint.getX() + steps, formerPoint.getY()), width, HEIGHT);
        // get it to the paddle.
        this.paddle = new Block(shape, COLOUR);
    }

    /**
     * Constructor, construct paddle.
     * @param floor GUI floor.
     * @param keyboard keyboard to get user input.
     * @param borderRight border of screen(x).
     * @param borderExtension border thickness.
     * @param step speed of paddle.
     * @param width width of paddle.
     */
    public Paddle(double floor, KeyboardSensor keyboard, int borderRight, int borderExtension, int step, int width) {
        // assign variables.
        this.keyboard = keyboard;
        this.width = width;
        Rectangle shape = new Rectangle(new Point(borderRight / 2 - width / 2,
                floor - HEIGHT - 10), width, HEIGHT);
        this.paddle = new Block(shape, COLOUR);
        this.borderLeft = borderExtension;
        this.borderRight = borderRight - borderExtension;
        this.step = step;
    }

    /**
     * inform time has passed.
     */
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        } else if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    /**
     * draws on surface.
     * @param d surface.
     */
    public void drawOn(DrawSurface d) {
        paddle.drawOn(d);
    }

    /**
     * @return the shape of paddle.
     */
    public Rectangle getCollisionRectangle() {
        return paddle.getCollisionRectangle();
    }

    /**
     * inform of being hit and return new velocity.
     * @param collisionPoint the point where the collision occurred.
     * @param currentVelocity the velocity the object collided with.
     * @param hitter Ball hitting the paddle.
     * @return new velocity for the object it hit.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // get the collision point X.
        double collisionX = collisionPoint.getX();
        // gets the different areas in paddle.
        double[] areas = getAreas();
        // gets velocity according to area.
        for (int i = 0; i < REGIONS; i++) {
            if (collisionX <= areas[i]) {
                return calcVelocity(currentVelocity, i);
            }
        }
        // else keep current velocity.
        return currentVelocity;
    }

    /**
     * calculate the velocity needed to be returned.
     * @param currentVelocity current velocity.
     * @param i position of area.
     * @return new velocity.
     */
    private Velocity calcVelocity(Velocity currentVelocity, int i) {
        // gets current speed
        double speed = currentVelocity.getSpeed();
        // if in middle
        if (ANGLES[i] == 0) {
            return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        }
        //returns new velocity according to i.
        return Velocity.fromAngleAndSpeed(ANGLES[i], speed);
    }

    /**
     * @return get areas of differenet hits.
     */
    private double[] getAreas() {
        // gets the start x of paddle.
        double startX = paddle.getCollisionRectangle().getUpperLeft().getX();
        // divide the with to different regions
        double difference = width / REGIONS;
        double[] areas = new double[REGIONS];
        //gets the areas of each region
        for (int i = 0; i < REGIONS; i++) {
            areas[i] = startX + difference * (i + 1);
        }
        return areas;
    }

    /**
     * Add this paddle to the game.
     * @param g game to add to.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * @return returns paddle height.
     */
    public static double getHEIGHT() {
        return HEIGHT;
    }
}