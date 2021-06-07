/**
 * a ScoreTrackingListener is in charge of keeping count of the game score.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;
    private static final int SCORE_INCREASE = 5;

    /**
     * Construct listener to keep check on game score.
     * @param scoreCounter current score of game.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * increases score on hit event.
     * @param beingHit object being hit
     * @param hitter the Ball that's doing the hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        this.currentScore.increase(SCORE_INCREASE);
    }
}