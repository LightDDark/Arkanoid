/**
 * Collidables define objects and in-game structures that can be collide with.
 */
public interface Collidable {
    /**
     * @return Rectangle the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();
    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * @param collisionPoint the point where the collision occurred.
     * @param currentVelocity the velocity the object collided with.
     * @param hitter the object we collided with.
     * @return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
