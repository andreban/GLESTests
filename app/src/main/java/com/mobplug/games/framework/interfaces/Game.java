/*
 * Copyright 2019 André Cipriani Bandarra
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.mobplug.games.framework.interfaces;

import java.io.Serializable;

/**
 * Defines Methods every game should implement, to handle its states and
 * events
 * 
 * @author andreban
 *
 */
public interface Game extends Serializable {
	public int getPeriod();
	/**
	 * Called by the GameRunnable to update the game state.
	 */
	public void update();
	
	/**
	 * Retrieves how many types the game was updated
	 * 
	 * @return the number of times the game was updated since start.
	 */
	public int getUpdateCount();
	
	/**
	 * Verifies is the game is over
	 * 
	 * @return true if the game is over
	 */
	public boolean isGameOver();
	
	/**
	 * Verifies if the game is paused
	 * 
	 * @return true if the game is paused.
	 */
	public boolean isPaused();
	
	/**
	 * Pauses the game
	 */
	public void pause();
	
	/**
	 * Resumes the game
	 */
	public void resume();
	
	/**
	 * Starts a new Game.
	 */
	public void newGame();
	
	/**
	 * Adds a new GameStateListener to the Game
	 * 
	 * @param gameListener the listener to be added
	 */
	public void addGameStateListener(GameStateListener gameListener);
	
	/**
	 * Removes a GameStateListener from this Game
	 * 
	 * @param gameListener the listener to be removed
	 */
	public void removeGameStateListener(GameStateListener gameListener);
	
	/**
	 * Adds a GameStateListener to the Game
	 * 
	 * @param gameEventListener the listener to be added
	 */
	public void addGameEventListener(GameEventListener gameEventListener);

	/**
	 * Removes a GameStateListener from the Game
	 * 
	 * @param gameEventListener the listener to be Removed
	 */	
	public void removeGameEventListener(GameEventListener gameEventListener);
}
