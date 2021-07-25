package com.oryehezkel.gamelogic;

import com.oryehezkel.geometry.Point;

/**
 * @author Or Avraham Yehezkel 314915869
 * Velocity specifies the change in position on the `x` and the `y` axes.
 */
public class Velocity {
    //fields represents difference in place
    private final double dx;
    private final double dy;

    /**
     * assign given parameters to fields.
     * @param dx diff in x.
     * @param dy diff in y.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * creates new velocity according to angle and "speed"(length).
     * @param angle direction of movement.
     * @param speed speed of movement.
     * @return new velocity with angle and speed.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        // set the angle such that 0 is x direction.
        angle = Math.toRadians(angle + 270);
        // calculates x and y and create
        double dx = Math.cos(angle) * speed;
        double dy = Math.sin(angle) * speed;
        return new Velocity(dx, dy);
    }

    /**
     * Take a point with position (x,y) and change it.
     * @param p point to change position of.
     * @return new Point with position (x+dx, y+dy).
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }

    /**
     * @return dx
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * @return dy
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * @return speed (vector length) from dx and dy.
     */
    public double getSpeed() {
        // calculates speed.
        return Math.sqrt(Math.pow(this.dx, 2) + Math.pow(this.dy, 2));
    }
}