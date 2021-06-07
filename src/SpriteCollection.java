import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Or Avraham Yehezkel 3146915869.
 * Sprite reprsents "active" objects in game.
 */
public class SpriteCollection {
    // fields of sprites collection.
    private List<Sprite> sprites = new ArrayList<>();

    /**
     * adds new sprite to collection.
     * @param s sprite to add.
     */
    public void addSprite(Sprite s) {
        sprites.add(s);
    }

    /**
     * call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        List<Sprite> spritesCopy = new ArrayList<>(this.sprites);
        for (Sprite s : spritesCopy) {
            s.timePassed();
        }
    }

    /**
     * call drawOn(d) on all sprites.
     * @param d surface to draw on.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : sprites) {
            s.drawOn(d);
        }
    }

    /**
     * Removes Sprite given from this Game.
     * @param s Sprite to remove.
     */
    public void remove(Sprite s) {
        if (!this.sprites.remove(s)) {
            System.err.println("Sprite given to remove does not exist");
        }
    }
}
