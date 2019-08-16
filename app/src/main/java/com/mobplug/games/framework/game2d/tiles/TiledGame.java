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

package com.mobplug.games.framework.game2d.tiles;

import com.mobplug.games.framework.BaseGame;

public abstract class TiledGame extends BaseGame {
	private static final long serialVersionUID = -9073321546862001321L;
	public abstract int getNumTilesX();
	public abstract int getNumTilesY();
	public abstract float getTileWidth();
	public abstract float getTileHeight();
	public abstract Tile[][] getTiles();
}
