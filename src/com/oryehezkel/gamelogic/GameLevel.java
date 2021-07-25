package com.oryehezkel.gamelogic;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import com.oryehezkel.gameenvironment.GameEnvironment;
import com.oryehezkel.gameenvironment.ScoreIndicator;
import com.oryehezkel.gameenvironment.animation.Animation;
import com.oryehezkel.gameenvironment.animation.AnimationRunner;
import com.oryehezkel.gameenvironment.animation.CountdownAnimation;
import com.oryehezkel.gameenvironment.animation.KeyPressStoppableAnimation;
import com.oryehezkel.gameenvironment.animation.PauseScreen;
import com.oryehezkel.gameobject.Ball;
import com.oryehezkel.gameobject.Block;
import com.oryehezkel.gameobject.Collidable;
import com.oryehezkel.gameobject.Paddle;
import com.oryehezkel.gameobject.Sprite;
import com.oryehezkel.gameobject.SpriteCollection;
import com.oryehezkel.geometry.Point;
import com.oryehezkel.geometry.Rectangle;
import com.oryehezkel.listeners.BallRemover;
import com.oryehezkel.listeners.BlockRemover;
import com.oryehezkel.listeners.HitListener;
import com.oryehezkel.listeners.ScoreTrackingListener;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Or Avraham Yehezkel 314915869
 * GameLevel represent an Arkanoid game level that can be played with already known parameters.
 */
public class GameLevel implements Animation {
    // collection for our "active" sprites and environment for the game
    private final SpriteCollection sprites = new SpriteCollection();
    private final LevelInformation levelInfo;
    private GameEnvironment environment;
    // counter to keep track of obstacles/balls/score
    private final Counter obstacleCounter = new Counter();
    private final Counter ballCounter = new Counter();
    private final Counter score;
    // animation specific run logic and condition.
    private AnimationRunner runner;
    private boolean running;
    private KeyboardSensor keyboard;
    // static known parameters we use to create the game and it's rules
    public static final double BORDER_THICKNESS = 25;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int LEVEL_FINISHED = 100;

    /**
     * Constructor to game levels, uses level info and animation to runs the level.
     * @param levelInfo parameters of current levels.
     * @param runner the runner that will run the animation.
     * @param score the score of the player.
     */
    public GameLevel(LevelInformation levelInfo, AnimationRunner runner, Counter score) {
        // assign the relevant fields
        this.levelInfo = levelInfo;
        this.runner = runner;
        this.keyboard = runner.getGui().getKeyboardSensor();
        this.environment = new GameEnvironment();
        this.score = score;
    }

    /**
     * Adds a collidable to the Game Environment.
     * @param c colliddable to add.
     */
    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }

    /**
     * Adds Sprite to the game.
     * @param s Sprite to add.
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    /**
     * Initialize a new game: create the Blocks and Balls (and Paddle)
     * and add them to the game.
     */
    public void initialize() {
        this.levelInfo.getBackground().addToGame(this);
        // adds different game objects.
        addBorders();
        addDeathZone();
        for (int i = 0; i < this.levelInfo.numberOfBalls(); i++) {
            addBall(this.levelInfo.initialBallVelocities().get(i));
        }
        addObstacles();
        addPaddle();
        addScoreIndicator();
    }

    /**
     * Adds score indicator Sprite to the game.
     */
    private void addScoreIndicator() {
        // creates the shape of the indicator
        Rectangle shape = new Rectangle(new Point(0, 0), runner.getGui().getDrawSurface().getWidth(), 20);
        // create indicator
        ScoreIndicator scoreIndicator = new ScoreIndicator(shape, this.score, this.levelInfo.levelName());
        // adds it to the game
        scoreIndicator.addToGame(this);
    }

    /**
     * Adds Death zone to the game, if ball touches it, it'll get removed.
     */
    private void addDeathZone() {
        // gets borders of gui
        double width = runner.getGui().getDrawSurface().getWidth();
        double height = runner.getGui().getDrawSurface().getHeight();
        Rectangle shape = new Rectangle(new Point(BORDER_THICKNESS, height),
                width - 2 * BORDER_THICKNESS, BORDER_THICKNESS);
        Block death = new Block(shape, Color.GRAY);
        // create Hit Listener for balls
        HitListener ballRemover = new BallRemover(this, ballCounter);
        death.addHitListener(ballRemover);
        death.addToGame(this);
    }

    /**
     * Adds paddle to the game.
     */
    private void addPaddle() {
        // creates new paddle
        Paddle paddle = new Paddle(runner.getGui().getDrawSurface().getHeight(), this.keyboard, WIDTH,
                (int) BORDER_THICKNESS, this.levelInfo.paddleSpeed(), this.levelInfo.paddleWidth());
        paddle.addToGame(this);
    }

    /**
     * Adds obstacles to the game in agreed upon pattern.
     */
    private void addObstacles() {
        // Create listener for block removal
        HitListener blockRemover = new BlockRemover(this, this.obstacleCounter);
        HitListener scoreCounter = new ScoreTrackingListener(this.score);
        List<Block> blockList = this.levelInfo.blocks();
        // adds blocks to the game
        for (Block b : blockList) {
            b.addToGame(this);
            b.addHitListener(blockRemover);
            b.addHitListener(scoreCounter);
        }
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

    /**
     * adds ball into the game.
     * @param velocity velocity og ball being created.
     */
    private void addBall(Velocity velocity) {
        Random rand = new Random();
        // create new ball at game center.
        Ball ball = new Ball(new Point(WIDTH / 2, HEIGHT - 28), 5,
                new Color(rand.nextInt() * rand.nextInt()));
        ball.setVelocity(velocity);
        // adds to the game
        ball.addToGame(this);
        // update counter
        this.ballCounter.increase();
    }

    /**
     * add blocks to act as borders.
     */
    private void addBorders() {
        // gets borders of gui
        double width = runner.getGui().getDrawSurface().getWidth();
        double height = runner.getGui().getDrawSurface().getHeight();
        // create blocks on all sides according to pre defined parameters
        Block[] borders = new Block[3];
        Rectangle shape = new Rectangle(new Point(0, 20), width, BORDER_THICKNESS);
        borders[0] = new Block(shape, Color.GRAY);
        shape = new Rectangle(new Point(width - BORDER_THICKNESS, BORDER_THICKNESS + 20),
                BORDER_THICKNESS, height - BORDER_THICKNESS - 20);
        borders[1] = new Block(shape, Color.GRAY);
        shape = new Rectangle(new Point(0, BORDER_THICKNESS + 20), BORDER_THICKNESS,
                height - BORDER_THICKNESS - 20);
        borders[2] = new Block(shape, Color.GRAY);
        // adds all to game
        for (Block b : borders) {
            b.setBorder(true);
            b.addToGame(this);
        }
    }

    /**
     * Runs the game, start the animation loop.
     */
    public void run() {
        // countdown before turn starts.
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        this.running = true;
        // use our runner to run the current animation -- which is one turn of
        // the game.
        this.runner.run(this);
    }

    /**
     * @return game environment.
     */
    public GameEnvironment getEnvironment() {
        return this.environment;
    }

    /**
     * Removes collidable given from Game Environment.
     * @param c collidable to remove.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Removes Sprite given from this GameLevel.
     * @param s Sprite to remove.
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }

    /**
     * game-specific logic of the animation.
     *
     * @param d surface to draw on.
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        //pause game condition
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, "space", new PauseScreen()));
        }
        // draw all
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();
        // if game finished close it
        if (this.obstacleCounter.getValue() == this.levelInfo.numberOfBlocksToRemove()) {
            this.score.increase(LEVEL_FINISHED);
            this.running = false;
        } else if (this.ballCounter.isZero()) {
            this.running = false;
        }
    }

    /**
     * stopping conditions to the animation.
     *
     * @return true if should stop and false otherwise.
     */
    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * @return true if there's no more balls left and false otherwise.
     */
    public boolean isBallEmpty() {
        return this.ballCounter.isZero();
    }
}
