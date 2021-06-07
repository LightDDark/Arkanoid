import biuoop.DrawSurface;

/**
 * Sprite for "active" objects in game.
 */
public interface Sprite extends GameObject {
    /**
     * draw the sprite to the screen.
     * @param d drawsurface to draw on.
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     */
    void timePassed();
}
