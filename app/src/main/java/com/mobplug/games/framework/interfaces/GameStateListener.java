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
