package com.oryehezkel.listeners;

import com.oryehezkel.gamelogic.Counter;
import com.oryehezkel.gamelogic.GameLevel;
import com.oryehezkel.gameobject.Ball;
import com.oryehezkel.gameobject.Block;

/**
 * a BlockRemover is in charge of removing blocks from the game, as well as keeping count
 * of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
    private GameLevel game;
    private Counter blocksRemoved;

    /**
     * Construct BlockRemover with a game and counter of current removed blocks.
     * @param game game to add.
     * @param blocksRemoved number of blocks remaining in the game.
     */
    public BlockRemover(GameLevel game, Counter blocksRemoved) {
        this.game = game;
        this.blocksRemoved = blocksRemoved;
    }

    /**
     * remove object being hit from the game.
     * @param beingHit object being hit.
     * @param hitter the Ball that's doing the hitting.
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.removeHitListener(this);
        beingHit.removeFromGame(this.game);
        blocksRemoved.increase();
    }
}