import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import com.oryehezkel.gameobject.Ball;

import java.awt.Color;
import java.util.Arrays;

/**
 * @author Or Avraham Yehezkel 314915869
 * class in charge of creating animiated balls with two diff frames.
 */
public class MultipleFramesBouncingBallsAnimation {

    /**
     * runs animation on two balls arrays each in a diff frame.
     * @param ballsOne array of ball in grey frame.
     * @param ballsTwo array of ball in yellow frame.
     * @param gui GUI to use.
     */
    public void multipleFramesAnimation(Ball[] ballsOne, Ball[] ballsTwo, GUI gui) {
        // creates sleeper for fps and surface for drawing
        Sleeper sleeper = new Sleeper();
        DrawSurface d;
        // new Abstract art for drawing continually
        AbstractArtDrawing art = new AbstractArtDrawing();

        /* runs endlessly, drawing the next step */
        while (true) {
            // makes grey and yellow frames
            d = gui.getDrawSurface();
            d.setColor(Color.GRAY);
            d.fillRectangle(50, 50, 450, 450);
            d.setColor(Color.YELLOW);
            d.fillRectangle(450, 450, 150, 150);
            // draws balls in frames borders
            art.drawStep(d, ballsOne, 50, 500, 50, 500);
            art.drawStep(d, ballsTwo, 450, 600, 450, 600);
            gui.show(d);
            sleeper.sleepFor(50);  // wait for 50 milliseconds.
        }
    }

    /**
     * runs animations of two sets of balls in two different frames, sizes of balls according to arguments.
     * @param args sizes of balls.
     */
    public static void main(String[] args) {
        // gets number of balls.
        int length = args.length;
        // crete two Ball arrays for diff frames
        Ball[] ballsOne = new Ball[(int) length / 2];
        Ball[] ballsTwo;
        // get late half the ball sizes
        String[] sizesTwo = Arrays.copyOfRange(args, length / 2, length);
        // if not even then is bigger in one
        if (length % 2 == 1) {
            ballsTwo = new Ball[((int) length / 2) + 1];
        } else {
            ballsTwo = new Ball[(int) length / 2];
        }
        // creates new GUI to draw on
        GUI gui = new GUI("title", 700, 700);
        // creates new animation types
        MultipleBouncingBallsAnimation multiBallAnime = new MultipleBouncingBallsAnimation();
        MultipleFramesBouncingBallsAnimation multiFrameAnime = new MultipleFramesBouncingBallsAnimation();
        //create balls
        ballsOne = multiBallAnime.creatBalls(args, ballsOne.length, 50, 500, 50, 500);
        ballsTwo = multiBallAnime.creatBalls(sizesTwo, ballsTwo.length, 450, 600, 450, 600);
        //animate balls
        multiFrameAnime.multipleFramesAnimation(ballsOne, ballsTwo, gui);
    }
}
