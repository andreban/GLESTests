package com.mobplug.games.framework.interfaces;

/**
 * A GameListeners listents to game events, like pausing, resuming, 
 * gameover and a new game starting
 * 
 * @author andreban
 *
 */
public interface GameStateListener {
	/**
	 * Called when the game is paused
	 * 
	 * @param game the game on which the event occurred
	 */
	public void onPause(Game game);
	
	/**
	 * Called when the game is resumed
	 * 
	 * @param game the game on which the event occurred
	 */
	public void onResume(Game game);
	
	/**
	 * Called when the game is resumed;
	 *  
	 * @param game the game on which the event occurred
	 */	
	public void onGameOver(Game game, GameResult result);

	/**
	 * Called when the game ends
	 * 
	 * @param game the game on which the event occurred
	 */
	public void onNewGame(Game game);
}
