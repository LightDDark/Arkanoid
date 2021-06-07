import biuoop.DrawSurface;

import java.awt.Color;

import static java.lang.Double.POSITIVE_INFINITY;

/**
 * @author Or Avraham Yehezkel 314915869
 * Line, compromised of two points and all that between them.
 */
public class Line {
    // start and end point of line
    private  Point start;
    private  Point end;
    private Point imageStart = null;
    private Point imageEnd = null;
    // if line is vertical yes otherwise false
    private final boolean vertical;

    /**
     * Construct new line using two points.
     * @param start start point of line.
     * @param end end point of line.
     */
    public Line(Point start, Point end) {
        //initialize grids.
        this.start = start;
        this.end = end;
        //check if vertical
        this.vertical = this.start.getX() == this.end.getX();
        changeThisPoints();
    }

    /**
     * Construct new line using coordinates.
     * @param x1 x value of first coordinate.
     * @param y1 y value of first coordinate.
     * @param x2 x value of second coordinate.
     * @param y2 y value of second coordinate.
     */
    public Line(double x1, double y1, double x2, double y2) {
        //initialize grids with new points using the coordinates.
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
        //check if vertical
        this.vertical = this.start.getX() == this.end.getX();
        changeThisPoints();
    }

    /**
     * @return the length of the line
     */
    public double length() {
        return start.distance(end);
    }

    /**
     * @return the middle point of the line
     */
    public Point middle() {
        // calculates middle x and y coordinates
        double x = (this.end.getX() + this.start.getX()) / 2;
        double y = (this.end.getY() + this.start.getY()) / 2;
        //returns point with those coordinates
        return new Point(x, y);
    }

    /**
     * @return the start point of the line
     */
    public Point start() {
        return this.start;
    }

    /**
     * @return the end point of the line
     */
    public Point end() {
        return this.end;
    }

    /**
     * @return true if line vertical and false if not.
     */
    public boolean isVertical() {
        return this.vertical;
    }

    /**
     * checks if given line intersects with this one.
     * @param other line to check if intersects with current line
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        /* if equal than it also intersects.
         * otherwise if it's parallel(same curvature), it never intersects.
         * otherwise checks if intersection point is within bounds.
         */
        Point otherStart = other.getImageStart(),
                otherEnd = other.getImageEnd();
        changePoints(otherStart, otherEnd);
        if (isEquals(other)) {
            return true;
        } else if (this.curvature() == other.curvature()) {
            return false;
        } else if ((this.vertical && other.curvature() == 0) && ((otherStart.getY() <= imageStart.getY())
                && (otherEnd.getY() >= imageStart.getY()))) {
            return true;
        } else if ((other.vertical && this.curvature() == 0) && ((imageStart.getX() <= otherStart.getX())
                && (imageStart.getX() >= otherStart.getX()))) {
            return true;
        } else if (other.vertical && this.getY(otherStart.getX()) != POSITIVE_INFINITY
                && ((this.getY(otherStart.getX()) >= otherStart.getY())
                && (this.getY(otherStart.getX()) <= otherEnd.getY()))) {
            return true;
        } else if (this.vertical && other.getY(imageStart.getX()) != POSITIVE_INFINITY
                && ((other.getY(imageStart.getX()) >= imageStart.getY())
                && (other.getY(imageStart.getX()) <= imageEnd.getY()))) {
            return true;
        } else {
            Point interP = getIntersection(other);
            if (this.vertical || other.isVertical() || interP.getX() <= imageStart.getX()
                    || interP.getX() >= imageEnd.getX() || interP.getX() <= otherStart.getX()
                    || interP.getX() >= otherEnd.getX()) {
                return false;
            }
        }
        return true;
    }

    /**
     * gets the point of intersection between this line and a given one, assuming they're infinite.
     * @param other line to check intersection with.
     * @return point of intersection.
     */
    private Point getIntersection(Line other) {
        // If equals return null
        if (isEquals(other)) {
            return null;
        }
        // get the curvature of each line.
        double m1 = other.curvature(), m2 = this.curvature();
        // declare coordinates for intersection point.
        double x, y;
        /*
         *if one of lines are vertical get the x from that line and calculates the y with that.
         */
        if (this.vertical && m1 == 0) {
            x = this.start.getX();
            y = other.start.getY();
        } else if (other.vertical && m2 == 0) {
            x = other.start.getX();
            y = this.start.getY();
        } else if (this.vertical) {
            x = this.start.getX();
            y = m1 * x + yInter(m1, other.start());
        } else if (other.isVertical()) {
            x = other.start().getX();
            y = m2 * x + yInter(m2, this.start);
        } else {
            /* if one of the curvatures equals zero, y equals line intersections with y axis of that line,
             * while x equals y minus the different line intersection divided by m of different line.
             * else, x equals the diff in y-axis intersections divided by the opposite difference between m.
             */
            if (m1 == 0) {
                y = yInter(m1, other.start());
                x = (y - yInter(m2, this.start)) / m2;
            } else if (m2 == 0) {
                y = yInter(m2, this.start);
                x = (y - yInter(m1, other.start())) / m1;
            } else {
                x = (yInter(m2, this.start) - yInter(m1, other.start())) / (m1 - m2);
                y = m1 * x + yInter(m1, other.start());
            }
        }
        // return point of intersection.
        return new Point(x, y);
    }

    /**
     * calculate curvature of line.
     * @return curvature of line.
     */
    public double curvature() {
        //get the start and end point of ln
        Point a = this.start(), b = this.end();
        // return the calculated curvature
        return (a.getY() - b.getY()) / (a.getX() - b.getX());
    }

    /**
     * calculate y-axis intersection assuming it's infinite.
     * @param curvat curvature of line.
     * @param p point in line.
     * @return y-axis intersection assuming it's infinite line.
     */
    private double yInter(double curvat, Point p) {
        return p.getY() - (curvat * p.getX());
    }

    /**
     * Returns the intersection point if the lines intersect,
     * and null otherwise.
     * @param other line to give intersection with.
     * @return point of intersection if exist or null.
     */
    public Point intersectionWith(Line other) {
        if (!isIntersecting(other)) {
            return null;
        }
        return getIntersection(other);
    }

    /**
     * checks if lines are equal.
     * @param other other line to compare against.
     * @return true is the lines are equal, false otherwise
     */
    public boolean isEquals(Line other) {
        // compare start and end point of lines.
        return other.start() == this.start && other.end() == this.end;
    }

    /**
     * get Y value given x.
     * @param x x value
     * @return y value.
     */
    private double getY(double x) {
        // if out of bounds.
        if (x < imageStart.getX() || x > imageEnd.getX()) {
            return POSITIVE_INFINITY;
        }
        // calculate y value
        double m = this.curvature();
        return this.curvature() * x + yInter(m, imageStart);
    }

    /**
     * get x value inside line given y.
     * @param y value given
     * @return x value.
     */
    private double getX(double y) {
        // calculate x
        double m = this.curvature();
        double x = (y - this.yInter(m, imageStart)) / m;
        // check if out of bounds
        if (x < imageStart.getX() || x > imageEnd.getX()) {
            return POSITIVE_INFINITY;
        }
        return x;
    }

    /**
     * check if line contain given point.
     * @param p point to check.
     * @return true if contains else false.
     */
    public boolean containPoint(Point p) {
        // if vertical wit same x, then true
        if (this.vertical && p.getX() == this.start.getX()) {
            return true;
            // same for horizontal.
        } else if (this.curvature() == 0 && p.getY() == this.start.getY()) {
            return true;
            // else calculate.
        } else if (this.getY(p.getX()) != POSITIVE_INFINITY && (this.getX(p.getY()) == p.getX())) {
            return true;
        }
        return false;
    }

    /**
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the
     * start of the line.
     * @param rect to check intersections with.
     * @return closest intersecting point.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        // declare distance and point
        double distance = POSITIVE_INFINITY;
        Point point = null;
        // get intersections
        java.util.List<Point> intersections = rect.intersectionPoints(this);
        // if no intersections return null
        if (intersections.isEmpty()) {
            return null;
        }
        // else go over them and get the lower distance
        for (Point p : intersections) {
            double tempDistance = this.start().distance(p);
            if (tempDistance < distance) {
                distance = tempDistance;
                point = p;
            }
        }
        return point;
    }

    /**
     * exchange points to be ordered by their y and x values.
     * @param starts point A.
     * @param ends point B.
     */
    private void changePoints(Point starts, Point ends) {
        // checks if the points are ordered
        if (starts.getX() > ends.getX() || (starts.getX() == ends.getX() && starts.getY() > ends.getY())) {
            // if not exchange between the points.
            Point temp = new Point(starts.getX(), starts.getY());
            starts.setPoint(ends);
            ends.setPoint(temp);
        }
    }

    /**
     * create new points from this point start and end in ordred way.
     */
    private void changeThisPoints() {
        // checks if ordered already
        if (this.start.getX() > this.end.getX()
                || (this.start.getX() == this.end.getX() && this.start.getY() > this.end.getY())) {
            // if not exchange between the points.
            imageStart = new Point(this.end.getX(), this.end.getY());
            imageEnd = new Point(this.start.getX(), this.start.getY());
        } else {
            imageStart = new Point(this.start.getX(), this.start.getY());
            imageEnd = new Point(this.end.getX(), this.end.getY());
        }
    }

    /**
     * draws the line on given drawsurface.
     * @param surface to draw on.
     */
    public void drawOn(DrawSurface surface) {
        // set color and draw.
        surface.setColor(Color.BLACK);
        surface.drawLine((int) this.start.getX(), (int) this.start.getY(),
                (int) this.end.getX(), (int) this.end.getY());
    }

    /**
     * @return ordered start point.
     */
    public Point getImageStart() {
        return imageStart;
    }

    /**
     * @return ordered end point.
     */
    public Point getImageEnd() {
        return imageEnd;
    }
}
