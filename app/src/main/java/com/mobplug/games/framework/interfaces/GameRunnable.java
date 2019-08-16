package com.mobplug.games.framework.interfaces;

/**
 * The GameRunnable is responsible for handle the base game loop, 
 * starting, stopping the game loop and updating and rendering the game
 *
 * @author andreban
 */
public interface GameRunnable extends Runnable {
    /**
     * Starts the game.
     */
    public void start();

    /**
     * Stops the game.
     */
    public void stop();
}
