package com.oryehezkel.gamelogic.level;

import com.oryehezkel.gameenvironment.animation.background.Sniper;
import com.oryehezkel.gamelogic.Velocity;
import com.oryehezkel.gameobject.Block;
import com.oryehezkel.gameobject.Sprite;
import com.oryehezkel.geometry.Point;
import com.oryehezkel.geometry.Rectangle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Or Avraham Yehezkel
 * Level 1 of Arkanoid game.
 */
public class LevelOne extends Level {
    private static final int NUM_OF_BALLS = 1;
    private static final int PADDLE_SPEED = 9;
    private static final int PADDLE_WIDTH = 90;
    private static final int WIN_BLOCKS = 1;
    private static final String LEVEL_NAME = "Direct Hit";
    private static final double BLOCK_WIDTH = 30;
    private static final double BLOCK_HEIGHT = 30;

    /**
     * Constructor, Initialize level with known parameters.
     */
    public LevelOne() {
        super(NUM_OF_BALLS, PADDLE_SPEED, PADDLE_WIDTH, WIN_BLOCKS, LEVEL_NAME);
    }

    /**
     * The initial velocity of each ball.
     * Note that initialBallVelocities().size() == numberOfBalls()
     */
    @Override
    public List<Velocity> initialBallVelocities() {
        // create Velocity for Ball to go straight up.
        Velocity v = new Velocity(0, 5);
        List<Velocity> velocityList = new ArrayList<Velocity>();
        velocityList.add(v);
        return velocityList;
    }

    /**
     * Returns a sprite with the background of the level.
     *
     * @return back ground of the level.
     */
    @Override
    public Sprite getBackground() {
        return new Sniper();
    }

    /**
     * The Blocks that make up this level, each block contains
     * its size, color and location.
     *
     * @return level blocks.
     */
    @Override
    public List<Block> blocks() {
        // create a block in the middle of the screen.
        List<Block> blockList = new ArrayList<>();
        Rectangle shape = new Rectangle(new Point(385, 200), BLOCK_WIDTH, BLOCK_HEIGHT);
        Block b = new Block(shape, Color.RED);
        // adds it to the list and returns it.
        blockList.add(b);
        return blockList;
    }
}
