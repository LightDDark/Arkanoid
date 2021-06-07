import biuoop.DrawSurface;
import biuoop.GUI;


import static java.lang.Double.POSITIVE_INFINITY;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Or Avraham Yehezkel 314905869.
 * Enviorment to game inteface with obstacles.
 */
public class GameEnvironment {
    // gui of game and list of obstacles.
    private GUI gui;
    private List<Collidable> collidables = new LinkedList<>();

    /**
     * Builder.
     *
     * @param gui of game.
     */
    public GameEnvironment(GUI gui) {
        this.gui = gui;
    }

    /**
     * add the given collidable to the environment.
     *
     * @param c collidable to add.
     */
    public void addCollidable(Collidable c) {
        // gets shape of collidable
        Rectangle shape = c.getCollisionRectangle();
        // if shape isn't in GUI bounds.
        if (shape.getUpperLeft().getY() > this.getSurface().getHeight()
                || shape.getUpperLeft().getX() > this.getSurface().getWidth()) {
            throw new RuntimeException("The Block you're trying to add is out of bounds");
        }
        // adds collidable.
        this.collidables.add(c);
    }

    /**
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     * @param trajectory of moving objects.
     * @return collsion info if it occur or null.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        // declare variables
        double distance = POSITIVE_INFINITY;
        Point collPoint = null;
        Collidable collObject = null;
        // check for every collidable
        for (Collidable c : collidables) {
            // gets closest intersections.
            Point temPoint = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
            if (temPoint == null) {
                continue;
            }
            // saves point with shorter distance.
            double tempDistance = temPoint.distance(trajectory.start());
            if (tempDistance < distance) {
                distance = tempDistance;
                collPoint = temPoint;
                collObject = c;
            } else if (tempDistance == distance) {
                collPoint.setShared(true);
            }
        }
        // if there's no collision.
        if (collPoint == null) {
            return null;
        }
        return new CollisionInfo(collPoint, collObject);
    }

    /**
     * @return Height.
     */
    public double getHeight() {
        return this.gui.getDrawSurface().getHeight();
    }

    /**
     * @return Width.
     */
    public double getWidth() {
        return this.gui.getDrawSurface().getWidth();
    }

    /**
     * @return DrawSurface to draw on.
     */
    public DrawSurface getSurface() {
        return this.gui.getDrawSurface();
    }

    /**
     * @return Environment GUI.
     */
    public GUI getGui() {
        return this.gui;
    }

    /**
     * Removes collidable given from Game Environment.
     * @param c collidable to remove.
     */
    public void removeCollidable(Collidable c) {
        if (!this.collidables.remove(c)) {
            System.err.println("Collidable given to remove does not exist");
        }
    }

}
