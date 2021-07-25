package com.oryehezkel.gamelogic.level;

import com.oryehezkel.gameenvironment.animation.background.Tower;
import com.oryehezkel.gamelogic.Velocity;
import com.oryehezkel.gameobject.Block;
import com.oryehezkel.gameobject.Sprite;
import com.oryehezkel.geometry.Point;
import com.oryehezkel.geometry.Rectangle;

import java.awt.Color;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Or Avraham Yehezkel
 * Level 3 of Arkanoid game.
 */
public class LevelThree extends Level {
    private static final int NUM_OF_BALLS = 2;
    private static final int PADDLE_SPEED = 9;
    private static final int PADDLE_WIDTH = 90;
    private static final int WIN_BLOCKS = 40;
    private static final String LEVEL_NAME = "Green 3";
    private static final double BLOCK_WIDTH = 50;
    private static final double BLOCK_HEIGHT = 25;

    /**
     * Constructor, Initialize level with known parameters.
     */
    public LevelThree() {
        super(NUM_OF_BALLS, PADDLE_SPEED, PADDLE_WIDTH, WIN_BLOCKS, LEVEL_NAME);
    }
    /**
     * The initial velocity of each ball.
     * Note that initialBallVelocities().size() == numberOfBalls()
     */
    @Override
    public List<Velocity> initialBallVelocities() {
        double firstAngle = 315;
        // creating velcoties in various angles using the first one as reference.
        List<Velocity> velocityList = new ArrayList<>();
        for (int i = 0; i < NUM_OF_BALLS; i++, firstAngle += 90) {
            Velocity v = Velocity.fromAngleAndSpeed(firstAngle, 4);
            velocityList.add(v);
        }
        // returns the list.
        return velocityList;
    }

    /**
     * Returns a sprite with the background of the level.
     *
     * @return back ground of the level.
     */
    @Override
    public Sprite getBackground() {
        return new Tower();
    }

    /**
     * The Blocks that make up this level, each block contains
     * its size, color and location.
     *
     * @return level blocks.
     */
    @Override
    public List<Block> blocks() {
        // known parameters
        int numOfObstacles = WIN_BLOCKS, maxInRow = 10;
        // creates a list to add obstacles to
        List<Block> obstacles = new ArrayList<>(numOfObstacles);
        // calculate the start x according to borders
        double startX = 800 - 25 - (BLOCK_WIDTH * maxInRow);
        Random rad = new Random();
        // creates 6 rows of obstacles
        for (int i = 0; i < 5; i++) {
            // choose random color
            Color colour = new Color(i + rad.nextInt() * rad.nextInt());
            // change position with each iteration
            Point start = new Point(startX + i * BLOCK_WIDTH, 150 + i * BLOCK_HEIGHT);
            // create blocks and adds them to list
            obstacles.addAll(createObstacles(maxInRow - i, start, colour));
        }
        return obstacles;
    }

    /**
     * Creates Obstacles.
     * @param numOfObstacles number of obstacles to create.
     * @param startPoint the start of the first obstacle.
     * @param colour the color of the obstacles.
     * @return list of all obstacles.
     */
    private List<Block> createObstacles(int numOfObstacles, Point startPoint, Color colour) {
        // creates a list of block to add to it.
        List<Block> obstacles = new ArrayList<>(numOfObstacles);
        // pre determined parameters
        double width = 50, height = 25;
        // get start from point
        double startX = startPoint.getX(), startY = startPoint.getY();
        Rectangle shape = null;
        // creates obstacles according to given num
        for (int pos = 0; pos < numOfObstacles; pos++) {
            double newStart = startX + (int) (width * pos);
            Point start = new Point(newStart, startY);
            shape = new Rectangle(start, width, height);
            Block block = new Block(shape, colour);
            obstacles.add(block);
        }
        return obstacles;
    }
}
