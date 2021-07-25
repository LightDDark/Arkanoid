import biuoop.GUI;
import biuoop.DrawSurface;
import com.oryehezkel.gameobject.Ball;
import com.oryehezkel.geometry.Line;
import com.oryehezkel.geometry.Point;
import java.util.Random;
import java.awt.Color;

/**
 * @author Or Avraham Yehezekl 314915869
 * draws lines and relations between them.
 */
public class AbstractArtDrawing {

    /**
     * draws 10 random lines on screen.
     */
    public void drawRandomLines() {
        // Create a window with the title "Random Circles Example"
        // which is 400 pixels wide and 300 pixels high.
        GUI gui = new GUI("Random Lines", 400, 300);
        DrawSurface d = gui.getDrawSurface();
        // creates array for all lines created.
        Line[] ln = new Line[10];
        // generate random lines
        for (int i = 0; i < 10; ++i) {
            ln[i] = generateRandomLine();
            // draw line
            drawLine(ln[i], d);
            // draw line mid point
            drawPoint(ln[i].middle(), d, Color.BLUE);
        }
        // gets all intersection points between lines
        Point[] interPoints = getIntersections(ln);
        //draw points
        for (int i = 0; interPoints[i] != null; i++) {
            drawPoint(interPoints[i], d, Color.RED);
        }
        //show surface
        gui.show(d);
    }

    /**
     * generates random lines in bounds of gui.
     * @return line generated
     */
    private Line generateRandomLine() {
        Random rand = new Random(); // create a random-number generator
        Point[] points = new Point[2];
        for (int i = 0; i < 2; i++) {
            int x = rand.nextInt(400) + 1; // get integer in range 1-400
            int y = rand.nextInt(300) + 1; // get integer in range 1-300
            points[i] = new Point(x, y);
        }
        return new Line(points[0], points[1]);
    }

    /**
     * draws line in given surface.
     * @param l line to draw
     * @param d surface to draw at
     */
    private void drawLine(Line l, DrawSurface d) {
        d.setColor(Color.BLACK);
        // get coordinates of start and end point
        int x1 = (int) l.start().getX(), y1 = (int) l.start().getY(),
                x2 = (int) l.end().getX(), y2 = (int) l.end().getY();
        //draws them
        d.drawLine(x1, y1, x2, y2);
    }

    /**
     * draws point int given surface and with given colour.
     * @param p point to draw
     * @param d surface to draw at
     * @param c colour to draw with
     */
    private void drawPoint(Point p, DrawSurface d, java.awt.Color c) {
        // gets coordinates of point
        int x = (int) p.getX(), y = (int) p.getY();
        d.setColor(c);
        d.fillCircle(x, y, 3);
    }

    /**
     * get intersections points of all lines.
     * @param lines lines to get intersection points of
     * @return points of intersection
     */
    private Point[] getIntersections(Line[] lines) {
        // get maximum number of intersections
        int pos = 0, intersections = (int) (Math.pow(lines.length, 2) - lines.length) / 2;
        // create maximum array
        Point[] points = new Point[intersections];
        // checks intersections of all lines.
        for (int i = 0; i < 10; i++) {
            for (int j = i + 1; j < 10; j++) {
                Point point = lines[i].intersectionWith(lines[j]);
                //if null than there's no intersection.
                if (point != null) {
                    points[pos] = point;
                    pos++;
                }
            }
        }
        return points;
    }

    /**
     * draws the next step of balls in array.
     * @param d drawsurface to draw at.
     * @param balls balls to draw.
     * @param minWidth minimum width of frame.
     * @param maxWidth maximum width of frame.
     * @param minHeight minimum height of frame.
     * @param maxHeight maimum height of frame.
     */
    public void drawStep(DrawSurface d, Ball[] balls, int minWidth, int maxWidth, int minHeight, int maxHeight) {
        /* move each ball one step and draws it */
        for (Ball ball : balls) {
            ball.moveOneStep(minWidth, maxWidth, minHeight, maxHeight);
            ball.drawOn(d);
        }
    }

    /**
     * draw ten random lines on the screen with intersection and mid points.
     * @param args cmd arguments, get nothing.
     */
    public static void main(String[] args) {
        // create new art instance.
        AbstractArtDrawing lineArt = new AbstractArtDrawing();
        // draw random lines.
        lineArt.drawRandomLines();
    }
}
