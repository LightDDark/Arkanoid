package com.oryehezkel.listeners;

import com.oryehezkel.gamelogic.Counter;
import com.oryehezkel.gamelogic.GameLevel;
import com.oryehezkel.gameobject.Ball;
import com.oryehezkel.gameobject.Block;

/**
 * Listnes to ball and removes it if touches dead zone.
 */
public class BallRemover implements HitListener {
    private final GameLevel game;
    private final Counter ballCounter;

    /**
     * Construct ballRemover with game to remove balls from and ball counter.
     * @param game to remove balls from.
     * @param ballCounter current amount of balls.
     */
    public BallRemover(GameLevel game, Counter ballCounter) {
        this.game = game;
        this.ballCounter = ballCounter;
    }

    /**
     * This method is called whenever the beingHit object is hit.
     * remove the object that hitting from the game.
     * @param beingHit object being hit
     * @param hitter   the Ball that's doing the hitting.
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        ballCounter.decrease();
    }
}
