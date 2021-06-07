/**
 * Objects that want to be notified of hit events.
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit.
     * @param beingHit object being hit
     * @param hitter the Ball that's doing the hitting.
     */
    void hitEvent(Block beingHit, Ball hitter);
}