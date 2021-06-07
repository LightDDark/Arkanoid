import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * @author Or Avraham Yehezkel 314915869.
 * Class BouncingBallAnimation, in charge of animating balls movement.
 */
public class BouncingBallAnimation {

    /**
     * drawAnimation draws a single bouncing ball continuously.
     * @param start ball center point.
     * @param dx ball velocity in x axis.
     * @param dy ball velocity in y axis.
     */
    private void drawAnimation(Point start, double dx, double dy) {

        /* creates a new GUI for animation */
        GUI gui = new GUI("title", 200, 200);
        //create a new ball using point given.
        Ball[] balls = new Ball[1];
        balls[0] = new Ball((int) start.getX(), (int) start.getY(), 30, java.awt.Color.BLACK);
        balls[0].setVelocity(dx, dy);
        //animate ball.
        drawLoop(balls, gui);
    }

    /**
     * drawLoop in charge of endless animation loop.
     * @param balls array of balls to animate.
     * @param gui gui to animate on.
     */
    public void drawLoop(Ball[] balls, GUI gui) {
        //creates sleeper for fps
        Sleeper sleeper = new Sleeper();
        //creates art for next step drawing
        AbstractArtDrawing art = new AbstractArtDrawing();
        DrawSurface d;

        /* run loop endlessly drawing next step */
        while (true) {
            d = gui.getDrawSurface();
            //draws next step
            art.drawStep(d, balls, 0, d.getWidth(), 0, d.getHeight());
            gui.show(d);
            sleeper.sleepFor(50);  // wait for 50 milliseconds.
        }
    }

    /**
     * create an animated bouncing ball according to arguments.
     * @param args arguments from cmd, gets coordinates of point and velocity.
     */
    public static void main(String[] args) {
        // supposed to have 4 arguments
        int length = 4;

        /* checks if there's correct amount of arguments */
        if (args.length != length) {
            throw new RuntimeException("Invalid input.");
        }
        int[] pointCoordinates = new int[2];     /* initialize new int array to later contain arg. */
        int[] velocityCoordinates = new int[2];

        /* converts the arguments into integers and gets into array. */
        for (int i = 0; i < 2; i++) {
            pointCoordinates[i] = Integer.parseInt(args[i]);
            /* if point is out of bounds, randomize it */
            if (pointCoordinates[i] <= 30 || pointCoordinates[i] >= (200 - 30)) {
                pointCoordinates[i] = (int) (Math.random() * 149 + 30);
            }
            velocityCoordinates[i] = Integer.parseInt(args[i + 2]);
        }
        // create animation
        BouncingBallAnimation ballAnime = new BouncingBallAnimation();
        ballAnime.drawAnimation(new Point(pointCoordinates[0], pointCoordinates[1]),
                velocityCoordinates[0], velocityCoordinates[1]);
    }
}
