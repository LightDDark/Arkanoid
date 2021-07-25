package com.oryehezkel.geometry;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Or Avraham Yehezkel 314915869.
 * Rectangle represtnted with width heigth and point.
 */
public class Rectangle {
    // fields
    private Point upperLeft;
    private double width;
    private double height;

    /**
     * Create a new rectangle with location and width/height.
     * @param upperLeft upper left point.
     * @param width width of rectangle.
     * @param height height of rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        // assign parameters.
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Return a (possibly empty) List of intersection points
     * with the specified line.
     * @param line to check
     * @return intersection points.
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        // get lines of shape.
        Line[] lines = this.getLines();
        // creates a list of points
        java.util.List<Point> intersections = new ArrayList<>();
        // check intersections with each line and adds to list
        for (Line ln : lines) {
            Point temp = ln.intersectionWith(line);
            if (temp != null) {
                intersections.add(temp);
            }
        }
        // remove duplicates and returns list.
        return findAndRemoveDuplicates(intersections);
    }

    /**
     * remove and mark duplicates in ArrayList.
     * @param list to go over.
     * @return List with no duplicates.
     */
    private java.util.List<Point> findAndRemoveDuplicates(java.util.List<Point> list) {
        // Create a new LinkedHashSet
        // Add the elements to set
        Set<Point> set = new LinkedHashSet<>(list);
        // Clear the list
        list.clear();
        //detect and mark duplicates
        for (Point p : list) {
            if (!set.add(p)) {
                set.remove(p);
                p.setVertex(true);
                set.add(p);
            }
        }
        // add the elements of set
        // with no duplicates to the list
        list.addAll(set);
        // return the list
        return list;
    }

    /**
     * @return Return the width of the rectangle.
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return height of the rectangle.
     */
    public double getHeight() {
        return height;
    }

    /**
     * @return Returns the upper-left point of the rectangle.
     */
    public Point getUpperLeft() {
        return upperLeft;
    }

    /**
     * gets all the lines compromising the rectangle.
     * @return list of lines of Rectangle.
     */
    public Line[] getLines() {
        // calculates all lines according to upper left width and height
        Line line1 = new Line(this.getUpperLeft(), new Point(this.getUpperLeft().getX() + this.getWidth(),
                this.getUpperLeft().getY()));
        Line line2 = new Line(this.getUpperLeft().getX(), this.getUpperLeft().getY() + this.getHeight(),
                this.getUpperLeft().getX() + this.getWidth(), this.getUpperLeft().getY() + this.getHeight());
        Line line3 = new Line(this.getUpperLeft(), new Point(this.getUpperLeft().getX(),
                this.getUpperLeft().getY() + this.getHeight()));
        Line line4 = new Line(this.getUpperLeft().getX() + this.getWidth(), this.getUpperLeft().getY(),
                this.getUpperLeft().getX() + this.getWidth(), this.getUpperLeft().getY() + this.getHeight());
        Line[] lines = {line1, line2, line3, line4};
        return lines;
    }

    /**
     * check if contains point.
     * @param p point to check.
     * @return true if contain false if not.
     */
    public boolean containsPoint(Point p) {
        // get the bounds of x and y.
        double lowX = getUpperLeft().getX(), highX = lowX + getWidth(),
                lowY = getUpperLeft().getY(), highY = lowY + getHeight(),
                pointX = p.getX(), pointY = p.getY();
        // checks that point is within bounds.
        if (pointX >= lowX && pointX <= highX && pointY >= lowY && pointY <= highY) {
            return true;
        }
        return false;
    }
}
