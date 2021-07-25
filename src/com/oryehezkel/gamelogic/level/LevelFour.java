package com.oryehezkel.gamelogic.level;

import com.oryehezkel.gameenvironment.animation.background.Winter;
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
 * Level 4 of Arkanoid game.
 */
public class LevelFour extends Level {
    // various parameters of this level
    private static final int NUM_OF_BALLS = 3;
    private static final int PADDLE_SPEED = 11;
    private static final int PADDLE_WIDTH = 90;
    private static final int WIN_BLOCKS = 105;
    private static final String LEVEL_NAME = "Final Four";
    private static final double BLOCK_WIDTH = 50;
    private static final double BLOCK_HEIGHT = 25;

    /**
     * Constructor, Calls super with level parameters.
     */
    public LevelFour() {
        super(NUM_OF_BALLS, PADDLE_SPEED, PADDLE_WIDTH, WIN_BLOCKS, LEVEL_NAME);
    }

    /**
     * The initial velocity of each ball.
     * Note that initialBallVelocities().size() == numberOfBalls().
     */
    @Override
    public List<Velocity> initialBallVelocities() {
        // determine default parameters
        double firstAngle = 315;
        double speed = 4;
        // construct array of velocities
        List<Velocity> velocityList = new ArrayList<>();
        // construct velocities for each ball
        for (int i = 0; i < NUM_OF_BALLS; i++, firstAngle += 45) {
            Velocity v = Velocity.fromAngleAndSpeed(firstAngle, speed);
            velocityList.add(v);
        }
        return velocityList;
    }

    /**
     * Returns a sprite with the background of the level.
     *
     * @return back ground of the level.
     */
    @Override
    public Sprite getBackground() {
        return new Winter();
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
        int numOfObstacles = WIN_BLOCKS, maxInRow = 15;
        // creates a list to add obstacles to
        List<Block> obstacles = new ArrayList<>(numOfObstacles);
        // calculate the start x according to borders
        double startX = 800 - 25 - (BLOCK_WIDTH * maxInRow);
        Random rad = new Random();
        // creates 7 rows of obstacles
        for (int i = 0; i < 7; i++) {
            // choose random color
            Color colour = new Color(i + rad.nextInt() * rad.nextInt());
            // change position with each iteration
            Point start = new Point(startX, 100 + i * BLOCK_HEIGHT);
            // create blocks and adds them to list
            obstacles.addAll(createObstacles(maxInRow, start, colour));
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
