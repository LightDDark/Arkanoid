package com.oryehezkel.gamelogic;

import com.oryehezkel.gameobject.Collidable;
import com.oryehezkel.geometry.Point;

/**
 * Collision info holds inside of it the info about collision occurring inside game.
 */
public class CollisionInfo {
    // Fields are point of collision and th object collided with.
    private final Collidable collObject;
    private final Point collPoint;

    /**
     * Builder, assign collison point and object.
     * @param collPoint the point the collision occurred.
     * @param collObject the object collided with.
     */
    public CollisionInfo(Point collPoint, Collidable collObject) {
        this.collPoint = collPoint;
        this.collObject = collObject;
    }

    /**
     * @return the point in which collision occurred.
     */
    public Point collisionPoint() {
        return this.collPoint;
    }

    /**
     * @return the collidable object involved in the collision..
     */
    public Collidable collisionObject() {
        return this.collObject;
    }
}
