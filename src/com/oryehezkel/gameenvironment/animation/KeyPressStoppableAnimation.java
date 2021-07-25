package com.oryehezkel.gameenvironment.animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * @author Or Avraham Yehezkel
 * Wrapper to animation that needs to be stopped using a key press.
 */
public class KeyPressStoppableAnimation implements Animation {
    // key that need to be pressed to stop and animation to play
    private final String key;
    private final KeyboardSensor sensor;
    private final Animation animation;
    // checks to see if this key was pressed before entering this animation.
    private boolean isAlreadyPressed = true;

    /**
     * Construct using Animation and key, and sensor to since press.
     * @param sensor Keyboard sensor to sense press.
     * @param key to recognize.
     * @param animation to run.
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.key = key;
        this.sensor = sensor;
        this.animation = animation;
    }
    /**
     * game-specific logic of the animation.
     *
     * @param d surface to draw on.
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        // checks to see if key is already pressed
        if (!sensor.isPressed(key)) {
            this.isAlreadyPressed = false;
        }
        // runs the animation
        this.animation.doOneFrame(d);
    }

    /**
     * stopping conditions to the animation.
     *
     * @return true if should stop and false otherwise.
     */
    @Override
    public boolean shouldStop() {
        // if this wasn't pressed and now is, should stop.
        return sensor.isPressed(key) && !this.isAlreadyPressed;
    }
}
