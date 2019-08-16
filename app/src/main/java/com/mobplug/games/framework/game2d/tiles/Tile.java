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

import java.io.Serializable;

import com.mobplug.games.framework.entities.Point2D;

public class Tile implements Serializable {
	private static final long serialVersionUID = 173930522207753665L;
	
	private float width;
	private float height;
	
	public Tile(float size) {
		this(size, size);
	}
	
	public Tile(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	private Point2D position;;
	/**
	 * @return the position
	 */
	public Point2D getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(Point2D position) {
		this.position = position;
	}
	/**
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}	
}
