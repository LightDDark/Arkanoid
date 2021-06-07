import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Sprite that indicate the game score.
 */
public class ScoreIndicator implements Sprite {
    private final Rectangle shape;
    private final Counter score;
    private static final Color DEFAULT_COLOR = Color.LIGHT_GRAY;

    /**
     * Construct indicator with shape of rectangle and counter of score.
     * @param shape rectangle shape to print on the score.
     * @param score score to draw.
     */
    public ScoreIndicator(Rectangle shape, Counter score) {
        this.shape = shape;
        this.score = score;
    }
    /**
     * adds this to the game.
     *
     * @param g game to add to.
     */
    @Override
    public void addToGame(Game g) {
        g.addSprite(this);
    }

    /**
     * draw this to the screen.
     *
     * @param d drawsurface to draw on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        //set color to default color.
        d.setColor(DEFAULT_COLOR);
        // fill it
        d.fillRectangle((int) shape.getUpperLeft().getX(), (int) shape.getUpperLeft().getY(),
                (int) shape.getWidth(), (int) shape.getHeight());
        //draw borders in black
        d.setColor(Color.BLACK);
        d.drawRectangle((int) shape.getUpperLeft().getX(), (int) shape.getUpperLeft().getY(),
                (int) shape.getWidth(), (int) shape.getHeight());
        d.drawText((int) shape.getWidth() / 2, (int) shape.getHeight() / 2 + 2, "Score: " + this.score,
                (int) 10);
    }

    /**
     * notify the sprite that time has passed.
     * currently does nothing.
     */
    @Override
    public void timePassed() {
        //does nothing
    }
}
