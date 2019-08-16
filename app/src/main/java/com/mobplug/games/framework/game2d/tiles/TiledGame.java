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
