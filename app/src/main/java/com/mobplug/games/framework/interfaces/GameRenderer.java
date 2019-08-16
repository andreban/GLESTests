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
