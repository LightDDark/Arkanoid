
/**
 * @author Or Avraham Yehezkel 314915869
 * Class Point, represents a point in a two dim world.
 */
public class Point {
    //coordinates of point
    private double x;
    private double y;
    private boolean vertex = false;
    private boolean shared = false;

    /**
     * Constructor, creates new point with the given coordinates.
     * @param x coordinate in x axis.
     * @param y coordinate in y axis.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Distance - return the distance of this point to the other point.
     * @param other , other point to measure distance between.
     * @return distance between given point and current one.
     */
    public double distance(Point other) {
        return Math.sqrt(Math.pow(other.getX() - this.x, 2) + Math.pow(other.getY() - this.y, 2));
    }

    /**
     * equals -- return true is the points are equal, false otherwise.
     * @param other , other point to check if equal to current one.
     * @return true if points equal and false if not.
     */
    public boolean equals(Point other) {
        if (other.getX() == this.x && other.getY() == this.y) {
            return true;
        }
        return false;
    }

    /**
     * Return the x value of this point.
     * @return this.x.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Return the y value of this point.
     * @return this.y.
     */
    public double getY() {
        return this.y;
    }

    /**
     * set Point new coordinates of other point.
     * @param p new Point.
     */
    public void setPoint(Point p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    /**
     * check if vertex.
     * @return true if vertex else false.
     */
    public boolean isVertex() {
        return vertex;
    }

    /**
     * set point to be vertex/not vertex.
     * @param bool true or false.
     */
    public void setVertex(boolean bool) {
        this.vertex = bool;
    }

    /**
     * @return if point is shared with other object.
     */
    public boolean isShared() {
        return shared;
    }

    /**
     * set point to be shared/not.
     * @param share true or false.
     */
    public void setShared(boolean share) {
        this.shared = share;
    }
}
