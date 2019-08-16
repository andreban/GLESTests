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
