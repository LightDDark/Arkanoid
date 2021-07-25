import biuoop.GUI;
import com.oryehezkel.gamelogic.Velocity;
import com.oryehezkel.gameobject.Ball;

import java.awt.Color;
import java.util.Random;

/**
 * @author Or Avraham Yehezkel 314915869.
 * class in charge of creating multiple balls and animate it.
 */
public class MultipleBouncingBallsAnimation {

    /**
     * create balls with given sizes and random coordinates and velocities.
     * @param sizes sizes of balls.
     * @param length length of balls array.
     * @param xStart start of coordinate border in x axis.
     * @param xBorder end of coordinate border in x axis.
     * @param yStart start of coordinate border in y axis.
     * @param yBorder end of coordinate border in y axis.
     * @return balls array of created balls.
     */
    public Ball[] creatBalls(String[] sizes, int length, int xStart, int xBorder, int yStart, int yBorder) {
        // creates Ball array according to length
        Ball[] balls = new Ball[length];
        for (int i = 0; i < length; i++) {
            Random rand = new Random();
            // get sizes
            double size = Integer.parseInt(sizes[i]);
            // size can't be negative
            if (size < 0) {
                throw new RuntimeException("Invalid input.");
            }
            // gets coordinates within borders.
            int x = (int) (rand.nextInt((int) (xBorder - xStart - 2 * size)) + size + xStart),
                    y = (int) (rand.nextInt((int) (yBorder - yStart - 2 * size)) + size + yStart);
            //set random colour to the ball
            Color colour = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
            balls[i] = new Ball(x, y, (int) size, colour);
            // use size to decide velocity, all balls >=50 are same velocity.
            if (size > 50) {
                size = 50;
            }
            // minimum velocity(size >=50) is 1, the smaller the faster.
            size = (1 / size) * 50;
            Velocity v = Velocity.fromAngleAndSpeed(rand.nextInt(360), size);
            balls[i].setVelocity(v);
        }
        return balls;
    }

    /**
     * receive string of ball sizes, generate them and animate them.
     * @param args arguments from cmd, balls sizes.
     */
    public static void main(String[] args) {
        // gets num of balls
        int length = args.length;
        MultipleBouncingBallsAnimation ballsAnime = new MultipleBouncingBallsAnimation();
        // create GUI
        GUI gui = new GUI("title", 700, 700);
        // create balls within GUI borders
        Ball[] balls = ballsAnime.creatBalls(args, length, 0, 700, 0, 700);
        // creates animation instance
        BouncingBallAnimation ballAnime = new BouncingBallAnimation();
        // runs animation
        ballAnime.drawLoop(balls, gui);
    }
}
