package com.oryehezkel.gamelogic.level;

import com.oryehezkel.gamelogic.LevelInformation;
import com.oryehezkel.gamelogic.Velocity;
import com.oryehezkel.gameobject.Block;
import com.oryehezkel.gameobject.Sprite;

import java.util.List;

/**
 * @author Or Avraham Yehezkel.
 * Abstract level class that includes all methods and fields levels should have.
 */
public abstract class Level implements LevelInformation {
    // Level parameters
    private final int numOfBalls;
    private final int paddleSpeed;
    private final int paddleWidth;
    private final int winBlocks;
    private final String levelName;

    /**
     * Construct Level info using given parameters.
     * @param numOfBalls number of balls in level.
     * @param paddleSpeed paddle speed.
     * @param paddleWidth paddle width.
     * @param winBlocks number of blocks player needs to remove in order to win.
     * @param levelName name f level.
     */
    public Level(int numOfBalls, int paddleSpeed, int paddleWidth, int winBlocks, String levelName) {
        this.numOfBalls = numOfBalls;
        this.paddleSpeed = paddleSpeed;
        this.paddleWidth = paddleWidth;
        this.winBlocks = winBlocks;
        this.levelName = levelName;
    }

    /**
     * @return Number of balls that exist in our level.
     */
    @Override
    public int numberOfBalls() {
        return numOfBalls;
    }

    /**
     * The initial velocity of each ball.
     * Note that initialBallVelocities().size() == numberOfBalls()
     */
    @Override
    public abstract List<Velocity> initialBallVelocities();

    /**
     * @return level paddle speed.
     */
    @Override
    public int paddleSpeed() {
        return paddleSpeed;
    }

    /**
     * @return level paddle width.
     */
    @Override
    public int paddleWidth() {
        return paddleWidth;
    }

    /**
     * the level name will be displayed at the top of the screen.
     *
     * @return the level name.
     */
    @Override
    public String levelName() {
        return levelName;
    }

    /**
     * Returns a sprite with the background of the level.
     *
     * @return back ground of the level.
     */
    @Override
    public abstract Sprite getBackground();

    /**
     * The Blocks that make up this level, each block contains
     * its size, color and location.
     *
     * @return level blocks.
     */
    @Override
    public abstract List<Block> blocks();

    /**
     * Number of blocks that should be removed before the level is considered to be "cleared".
     * This number should be <= blocks.size();
     *
     * @return number of blocks before level cleared.
     */
    @Override
    public int numberOfBlocksToRemove() {
        return winBlocks;
    }
}
