package com.oryehezkel.gamelogic;
import com.oryehezkel.gameobject.Block;
import com.oryehezkel.gameobject.Sprite;

import java.util.List;

/**
 * @author Or Avraham Yehezkel.
 * Interface that represents Level informations, includes method to get that various info needed.
 */
public interface LevelInformation {

    /**
     * @return Number of balls that exist in our level.
     */
    int numberOfBalls();

    /**
     * The initial velocity of each ball.
     * Note that initialBallVelocities().size() == numberOfBalls()
     * @return List of velocities of each ball.
     */
    List<Velocity> initialBallVelocities();

    /**
     * @return level paddle speed.
     */
    int paddleSpeed();

    /**
     * @return level paddle width.
     */
    int paddleWidth();

    /**
     * the level name will be displayed at the top of the screen.
     * @return the level name.
     */
    String levelName();

    /**
     * Returns a sprite with the background of the level.
     * @return back ground of the level.
     */
    Sprite getBackground();

    /**
     * The Blocks that make up this level, each block contains
     * its size, color and location.
     * @return level blocks.
     * */
    List<Block> blocks();

    /**
     * Number of blocks that should be removed before the level is considered to be "cleared".
     * This number should be <= blocks.size();
     * @return number of blocks before level cleared.
     */
    int numberOfBlocksToRemove();
}