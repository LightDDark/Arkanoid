package com.oryehezkel.gameobject;

import com.oryehezkel.gamelogic.GameLevel;

/**
 * Interface for in-game Objects.
 */
public interface GameObject {
    /**
     * adds object to the game.
     * @param g game to add to.
     */
    void addToGame(GameLevel g);
}
