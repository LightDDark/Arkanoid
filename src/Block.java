import biuoop.DrawSurface;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Or Avraham Yehezkel 314915869.
 * Block implements collidable and sprite,
 * which means it can be addd as part of game.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle shape;
    private Color colour;
    // if Block plays as a border ingame.
    private boolean border = false;
    // listeners list to this block actions
    private List<HitListener> hitListeners = new ArrayList<>();

    @Override
    /**
     * returns the shape of the block.
     */
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }

    @Override
    /**
     * returns new velocity after being hit by object., based on it current velocity and collision Point.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // get lines compromising shape
        Line[] rectLines = this.shape.getLines();
        Velocity newVelocity = null;
        for (Line line : rectLines) {
            // for every line, checks if it contains collision point.
            if (line.containPoint(collisionPoint)) {
                // if he collision point is shared between two objects and this is a border.
                if (collisionPoint.isShared() && this.isBorder()) {
                    newVelocity = new Velocity(-currentVelocity.getDx(), -currentVelocity.getDy());
                    // if point is shared between two objects.
                } else if (collisionPoint.isShared()) {
                    newVelocity = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
                    // if point is vertex
                } else if (collisionPoint.isVertex()) {
                    newVelocity = new Velocity(-currentVelocity.getDx(), -currentVelocity.getDy());
                    //if line of collision is vertical
                } else if (line.isVertical()) {
                    newVelocity = new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
                    // if line of collision is horizontal
                } else {
                    newVelocity = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
                }
            }
        }
        this.notifyHit(hitter);
        return newVelocity;
    }

    /**
     * constructor of block.
     * @param shape shape of the Block.
     * @param colour Color of the block.
     */
    public Block(Rectangle shape, Color colour) {
        // assign parameters.
        this.colour = colour;
        this.shape = shape;
    }

    @Override
    /**
     * draw the block on given DrawSurface.
     */
    public void drawOn(DrawSurface d) {
        //set color to block color.
        d.setColor(this.colour);
        // fill it
        d.fillRectangle((int) shape.getUpperLeft().getX(), (int) shape.getUpperLeft().getY(),
                (int) shape.getWidth(), (int) shape.getHeight());
        //draw borders in black
        d.setColor(Color.BLACK);
        d.drawRectangle((int) shape.getUpperLeft().getX(), (int) shape.getUpperLeft().getY(),
                (int) shape.getWidth(), (int) shape.getHeight());
    }

    @Override
    /**
     * pass time for block, currently it does nothing as it's not a moving object for now.
     */
    public void timePassed() {
        // does nothing
    }

    @Override
    /**
     * add Block to given game.
     */
    public void addToGame(Game g) {
        // add block as sprite and collidable.
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * @return if block is border or not.
     */
    public boolean isBorder() {
        return border;
    }

    /**
     * set block as border/not border.
     * @param b true or false if border.
     */
    public void setBorder(boolean b) {
        this.border = b;
    }

    /**
     * Removes this block from given game.
     * @param game to remove this block from.
     */
    public void removeFromGame(Game game) {
        game.removeSprite(this);
        game.removeCollidable(this);
    }

    /**
     * Add hl as a listener to hit events.
     * @param hl
     */
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Remove hl from the list of listeners to hit events.
     * @param hl hitListener to add.
     */
    @Override
    public void removeHitListener(HitListener hl) {
        if (!this.hitListeners.remove(hl)) {
            System.err.println("HitListener to remove isn't found in list");
        }
    }

    /**
     * notify all of the registered HitListener objects that a hit occured.
     * @param hitter the object that hit this block.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
}
