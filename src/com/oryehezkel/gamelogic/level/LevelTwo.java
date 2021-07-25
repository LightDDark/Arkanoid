package com.oryehezkel.gamelogic.level;

import com.oryehezkel.gameenvironment.animation.background.Sunny;
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
 * Level 2 of Arkanoid game.
 */
public class LevelTwo extends Level {
    private static final int NUM_OF_BALLS = 10;
    private static final int PADDLE_SPEED = 5;
    private static final int PADDLE_WIDTH = 450;
    private static final int WIN_BLOCKS = 15;
    private static final String LEVEL_NAME = "Wide Easy";
    private static final double BLOCK_WIDTH = 50;
    private static final double BLOCK_HEIGHT = 25;

    /**
     * Constructor, Initialize level with known parameters.
     */
    public LevelTwo() {
        super(NUM_OF_BALLS, PADDLE_SPEED, PADDLE_WIDTH, WIN_BLOCKS, LEVEL_NAME);
    }

    /**
     * The initial velocity of each ball.
     * Note that initialBallVelocities().size() == numberOfBalls()
     */
    @Override
    public List<Velocity> initialBallVelocities() {
        double firstAngle = 310;
        List<Velocity> velocityList = new ArrayList<>();
        // construct velocities in various angles using the first one as reference.
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < NUM_OF_BALLS / 2; i++, firstAngle += 10) {
                Velocity v = Velocity.fromAngleAndSpeed(firstAngle, 4);
                velocityList.add(v);
            }
        }
        // returns list.
        return velocityList;
    }

    /**
     * Returns a sprite with the background of the level.
     *
     * @return back ground of the level.
     */
    @Override
    public Sprite getBackground() {
        return new Sunny();
    }

    /**
     * The Blocks that make up this level, each block contains
     * its size, color and location.
     *
     * @return level blocks.
     */
    @Override
    public List<Block> blocks() {
        List<Block> blockList = new ArrayList<>();
        Rectangle shape;
        int j = 0;
        // construct Blocks in a line.
        for (int i = 0; i < 3; i++) {
            // construct Couples Blocks with same Colour
            for (int count = j; j < count + 2; j++) {
                Point startOne = new Point(25 + j * BLOCK_WIDTH, 300);
                Point startTwo = new Point(475 + j * BLOCK_WIDTH, 300);
                shape = new Rectangle(startOne, BLOCK_WIDTH, BLOCK_HEIGHT);
                Block a = new Block(shape, new Color(10582381 / (i + 1)));
                shape = new Rectangle(startTwo, BLOCK_WIDTH, BLOCK_HEIGHT);
                Block b = new Block(shape, new Color(15844185 / (i + 1)));
                blockList.add(a);
                blockList.add(b);
            }
            // construct Triplet Blocks with same Colour in the middle
            Point startThree = new Point(325 + i * BLOCK_WIDTH, 300);
            shape = new Rectangle(startThree, BLOCK_WIDTH, BLOCK_HEIGHT);
            Block c = new Block(shape, Color.GREEN);
            blockList.add(c);
        }
        // returns list
        return blockList;
    }
}
