package com.mobplug.games.framework.interfaces;

/**
 * A GameRenderer can read the game state and draw its state in the 
 * specific platform
 * @author andreban
 *
 * @param <G> is the Game whose state the renderer represents
 */
public interface GameRenderer<G extends Game> {
	/**
	 * Renders a single game frame
	 */
	public void render();
}
