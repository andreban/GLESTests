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

import com.mobplug.games.framework.entities.Point2D;
import com.mobplug.games.framework.entities.Sprite2D;

public abstract class TiledSprite<T, G extends TiledGame> extends Sprite2D<T> {
	private static final long serialVersionUID = -7620887375067407996L;
	protected Point2D startingPosition;
	protected G game;
	
	public TiledSprite(G game, Point2D startingPosition) {
		this.game = game;
		this.startingPosition = startingPosition;
		this.vector.setPosition(startingPosition.clone());
	}
	
	public void reset() {
		this.vector.setPosition(startingPosition.clone());
	}
	
	protected void adjustPosition(Point2D point) {
		float newX, newY = 0.0f;
		
		if (vector.getSpeedX() > 0) {
			newX = point.getX() - getWidth() - 1.0f;
		} else if (vector.getSpeedX() < 0) {
			newX = point.getX() + game.getTileWidth();
		} else {
			newX = getPosition().getX();
		}
//		System.out.println("Setting newX to: " + newX);
		if (vector.getSpeedY() > 0) {
			newY = point.getY() - getHeight() - 1.0f;
		} else if (vector.getSpeedY() < 0) {
			newY = point.getY() + game.getTileHeight();
		} else {
			newY = getPosition().getY();
		}
		vector.setPosition(newX, newY);		
	}
	
	protected abstract boolean collidesWith(Tile tile);
	protected Point2D checkWallCollision(float newX, float newY) {
		float fromX = Math.min(getPosition().getX(), newX);
		float toX = Math.max(getPosition().getX() + getWidth(), newX + getWidth());
		
		float fromY = Math.min(getPosition().getY(), newY);
		float toY = Math.max(getPosition().getY() + getHeight(), newY + getHeight());
		
		int fromTileX = (int)(fromX / game.getTileWidth());
		if (fromTileX < 0) fromTileX = 0;
		int toTileX = (int)(toX / game.getTileWidth());
		
		int fromTileY = (int)(fromY / game.getTileHeight());
		if (fromTileY < 0) fromTileY = 0;
		int toTileY = (int)(toY / game.getTileHeight());

		Tile[][] walls = game.getTiles();		
		for (int x = fromTileX; x <= toTileX && x < game.getNumTilesX(); x++) {
			for (int y = fromTileY; y <= toTileY && y < game.getNumTilesY(); y++) {
				Tile tile = walls[x][y];
				if (tile != null && collidesWith(tile)) {
//					System.out.println("Detected Wall Collision");
//					System.out.println("CurrPos: " + getPosition().getX() + ":" + getPosition().getY());					
//					System.out.println("NewPos: " + newX + ":" + newY);
//					System.out.println("fromTileX: " + fromTileX + " toTileX: " + toTileX);
//					System.out.println("fromTileY: " + fromTileY + " toTileY: " + toTileY);
//					System.out.println("Collided with tile at: " + x + ":" + y);
//					System.out.println("Tile pixel posx " + x * Wall.SIZE + " width: " + Wall.SIZE);
//					System.out.println("Tile pixel posy " + y * Wall.SIZE + " height: " + Wall.SIZE);					
					return tile.getPosition();				
				}
			}
		}
		return null;
	}	
}
