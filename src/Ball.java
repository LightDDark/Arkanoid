import biuoop.DrawSurface;


/**
 * @author Or AVraham Yehezkel 314915869
 * represents ball(circle) with size(radius), center point and colour.
 * can be further added with velocity state and game enironment it can be playd at.
 * implements sprite as part of it.
 */
public class Ball implements Sprite {

    /* center, size, colour and velocity of the ball */
    private Point center;
    private final int size;
    private final java.awt.Color colour;
    // ball velocity is optional and defaulted to null
    private Velocity v = null;
    // in case ball is part of game
    private GameEnvironment gameEnvironment = null;

    /**
     * construct a ball with given center point, radius and colour.
     * @param center center point of ball.
     * @param r radius of ball.
     * @param color colour of ball.
     */
    public Ball(Point center, int r, java.awt.Color color) {
        //assign fields.
        this.center = center;
        this.size = r;
        this.colour = color;
    }

    /**
     * construc ball using center coordinates, radius and colour.
     * @param x x axis coordinate
     * @param y y axis coordinate
     * @param r radius size
     * @param color colour
     */
    public Ball(int x, int y, int r, java.awt.Color color) {
        //creat a point using given parameters and assign fields.
        this.center = new Point(x, y);
        this.size = r;
        this.colour = color;
    }

    /**
     * @return the x coordinate of the center
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * @return the y coordinate of the center
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * @return the radius size
     */
    public int getSize() {
        return this.size;
    }

    /**
     * @return ball colour.
     */
    public java.awt.Color getColor() {
        return this.colour;
    }

    @Override
    /**
     * draw the ball on the given DrawSurface.
     * @param d to draw on.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.colour);
        d.fillCircle(getX(), getY(), this.size);
        /*Line l = this.getTrajectory();
        d.drawLine((int) l.start().getX(), (int) l.start().getY(), (int) l.end().getX(), (int) l.end().getY());*/
    }

    /**
     * set velocity of ball using Velocity.
     * @param velocity velocity.
     */
    public void setVelocity(Velocity velocity) {
        this.v = velocity;
    }

    /**
     * set velocity of ball using dx and dy.
     * @param dx difference in x axis.
     * @param dy difference in y axis.
     */
    public void setVelocity(double dx, double dy) {
        //creates Velocity and set it.
        this.v = new Velocity(dx, dy);
    }

    /**
     * @return velocity of ball.
     */
    public Velocity getVelocity() {
        return this.v;
    }

    /**
     * move ball steps inside given borders.
     * @param widthBorderA width border(x axis)
     * @param widthBorderB width border(x axis)
     * @param heightBorderA height border(y axis)
     * @param heightBorderB height border(y axis)
     */
    public void moveOneStep(int widthBorderA, int widthBorderB, int heightBorderA, int heightBorderB) {
        // if ball reached borders inverse velocity
        if (this.center.getX() - this.size <= widthBorderA || this.center.getX() + this.size >= widthBorderB) {
            setVelocity(-this.v.getDx(), this.v.getDy());
        }
        if (this.center.getY() - this.size <= heightBorderA || this.center.getY() + this.size >= heightBorderB) {
            setVelocity(this.v.getDx(), -this.v.getDy());
        }
        //apply new velocity
        this.center = this.getVelocity().applyToPoint(this.center);
    }

    /**
     * Moves ball one step according to th game environment.
     */
    public void moveOneStep() {
        // gets ball trajectory to predict collisions
        Line trajectory = this.getTrajectory();
        // get the closest from the game environment
        CollisionInfo collisionInfo = this.gameEnvironment.getClosestCollision(trajectory);
        // move till point of collision
        moveTillPoint(collisionInfo);
    }

    /**
     * move point until it reaches collision point and then get new velocity.
     * @param collisionInfo info about collision.
     */
    private void moveTillPoint(CollisionInfo collisionInfo) {
        // check to see where the point will be if it moved
        Point testCenter = this.getVelocity().applyToPoint(this.center);
        // if collisioninfo is null, then there's no obstacles and it can be moved
        if (collisionInfo == null) {
            this.center = testCenter;
            return;
            // if testcenter doesn't reach inside the obstacle then likewise it can be moved
        } else if (!collisionInfo.collisionObject().getCollisionRectangle().containsPoint(testCenter)) {
            this.center = testCenter;
        }
        // check to see if ball touches collision point
        if (touchPoint(collisionInfo.collisionPoint())) {
            // if it is, inform the obstacle it was hit and get new velocity from it
            this.setVelocity(collisionInfo.collisionObject().hit(this, collisionInfo.collisionPoint(),
                    this.getVelocity()));
        }
    }

    /**
     * checks if ball going to touch point.
     * @param p point to check.
     * @return either true that it touches or false.
     */
    private boolean touchPoint(Point p) {
        // check ball position in future steps
        Point futureCenter = this.getVelocity().applyToPoint(this.center);
        Point doubleFutureCenter = this.getVelocity().applyToPoint(futureCenter);
        // calculate future positions from point
        double distance = p.distance(futureCenter);
        double distanceTwo = p.distance(doubleFutureCenter);
        /*
         * if distance is <= radius it means point is on/inside the ball,
         * and if distanceTwo > distance it means the ball passed it.
         * */
        if (distance <= this.size || distanceTwo > distance) {
            return true;
        }
        return false;
    }

    /**
     * get ball trajectory line using current position and velocity.
     * @return trajectory Line
     */
    private Line getTrajectory() {
        // initialize ball future coordinates to calculate it's route.
        double x2 = this.center.getX() + this.v.getDx();
        double y2 = this.center.getY() + this.v.getDy();
        Line trajectory = null;
        // calculate trajectory curvature based on two points
        double curvature = (this.center.getY() - y2) / (this.center.getX() - x2);
        // if horizontal
        if (curvature == 0 && this.center.getX() > x2) {
            trajectory = new Line(this.getX(), this.getY(), 0, this.getY());
        } else if (curvature == 0 && this.center.getX() < x2) {
            trajectory = new Line(this.getX(), this.getY(), gameEnvironment.getWidth(), this.getY());
            //if vertical
        } else if (this.center.getX() == x2 && y2 > this.center.getY()) {
            trajectory = new Line(this.getX(), this.getY(), this.getX(), gameEnvironment.getHeight());
        } else if (this.center.getX() == x2 && y2 < this.center.getY()) {
            trajectory = new Line(this.getX(), this.getY(), this.getX(), 0);
            // normal line, checks if going downX or upX and go to the limit of x
        } else if (x2 > this.center.getX()) {
            x2 = gameEnvironment.getWidth();
            y2 = x2 * curvature + (this.getY() - (this.getX() * curvature));
            trajectory = new Line(this.getX(), this.getY(), x2, y2);
        } else {
            x2 = 0;
            y2 = this.getY() - (this.getX() * curvature);
            trajectory = new Line(this.getX(), this.getY(), x2, y2);
        }

        return trajectory;
    }

    /**
     * set game environment.
     * @param environment to set.
     */
    public void setGameEnvironment(GameEnvironment environment) {
        this.gameEnvironment = environment;
    }

    @Override
    /**
     * move step
     */
    public void timePassed() {
        this.moveOneStep();
    }

    @Override
    /**
     * add ball to given game.
     */
    public void addToGame(Game g) {
        // ads ball to game sprites and get the game environment
        g.addSprite(this);
        this.setGameEnvironment(g.getEnvironment());
    }

    /**
     * removes ball to given game.
     * @param g game to remove from.
     */
    public void removeFromGame(Game g) {
        g.removeSprite(this);
        this.setGameEnvironment(null);
    }
}
