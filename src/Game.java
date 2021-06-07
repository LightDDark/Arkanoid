import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Or Avraham Yehezkel 314915869
 * Game represent an Arkanoid game that can be played with already known parameters.
 */
public class Game {
    // collection for our "active" sprites and environment for the game
    private final SpriteCollection sprites = new SpriteCollection();
    private GameEnvironment environment;
    // counter to keep track of obstacles/balls/score
    private final Counter obstacleCounter = new Counter();
    private final Counter ballCounter = new Counter();
    private final Counter score = new Counter();
    // static known parameters we use to create the game and it's rules
    public static final double BORDER_THICKNESS = 25;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int LEVEL_FINISHED = 100;

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
        // intitalize a new GUI to play the game in it
        GUI gui = new GUI("Game", WIDTH, HEIGHT);
        // game the environment fro the GUI
        this.environment = new GameEnvironment(gui);
        // adds different game objects.
        addBorders();
        addDeathZone();
        for (int i = 0; i < 3; i++) {
            addBall();
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
        Rectangle shape = new Rectangle(new Point(0, 0), this.environment.getWidth(), 20);
        // create indicator
        ScoreIndicator scoreIndicator = new ScoreIndicator(shape, score);
        // adds it to the game
        scoreIndicator.addToGame(this);
    }

    /**
     * Adds Death zone to the game, if ball touches it, it'll get removed.
     */
    private void addDeathZone() {
        // gets borders of gui
        double width = environment.getWidth();
        double height = environment.getHeight();
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
        // initialize a new point for upper left paddle in a way it'll be in the middle of the screen and on ground
        Point upperLeft = new Point(WIDTH / 2,
                getEnvironment().getHeight() - Paddle.getHEIGHT());
        // get keyboard from our environment
        KeyboardSensor keyboardSensor = this.environment.getGui().getKeyboardSensor();
        // creates new paddle
        Paddle paddle = new Paddle(upperLeft, keyboardSensor, WIDTH, (int) BORDER_THICKNESS);
        paddle.addToGame(this);
    }

    /**
     * Adds obstacles to the game in agreed upon pattern.
     */
    private void addObstacles() {
        // known parameters
        int numOfObstacles = 57, maxInRow = 12;
        double width = 50, height = 25;
        // creates a list to add obstacles to
        List<Block> obstacles = new ArrayList<>(numOfObstacles);
        // calculate the start x according to borders
        double startX = environment.getWidth() - BORDER_THICKNESS - (width * maxInRow);
        Random rad = new Random();
        // creates 6 rows of obstacles
        for (int i = 0; i < 6; i++) {
            // choose random color
            Color colour = new Color(i + rad.nextInt() * rad.nextInt());
            // change position with each iteration
            Point start = new Point(startX + i * width, 100 + i * height);
            // create blocks and adds them to list
            obstacles.addAll(createObstacles(maxInRow - i, start, colour));
        }
        // adds numOfObstacles tou our obstacle counter
        this.obstacleCounter.increase(numOfObstacles);
        // Create listener for block removal
        HitListener blockRemover = new BlockRemover(this, this.obstacleCounter);
        HitListener scoreCounter = new ScoreTrackingListener(score);
        // adds blocks to the game
        for (Block b : obstacles) {
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
     */
    private void addBall() {
        Random rand = new Random();
        // create new ball within borders with random place.
        Ball ball = new Ball(new Point(rand.nextInt((int) ((int) WIDTH - (BORDER_THICKNESS * 2)))
                + BORDER_THICKNESS, 450), 5, new Color(rand.nextInt() * rand.nextInt()));
        // gets velocity
        Velocity v = new Velocity(-7, -3);
        ball.setVelocity(v);
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
        double width = environment.getWidth();
        double height = environment.getHeight();
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
        // define parameters
        Sleeper sleeper = new Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        // start animation
        while (true) {
            long startTime = System.currentTimeMillis(); // timing
            // draw all
            DrawSurface d = this.environment.getSurface();
            this.sprites.drawAllOn(d);
            this.environment.getGui().show(d);
            this.sprites.notifyAllTimePassed();
            // if game finished close it
            if (this.obstacleCounter.isZero()) {
                this.score.increase(LEVEL_FINISHED);
                return;
            } else if (this.ballCounter.isZero()) {
                return;
            }

            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
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
     * Removes Sprite given from this Game.
     * @param s Sprite to remove.
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }
}
