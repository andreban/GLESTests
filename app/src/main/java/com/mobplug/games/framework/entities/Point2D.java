package com.mobplug.games.framework.entities;

import java.io.Serializable;

public class Point2D implements Serializable {
	private static final long serialVersionUID = 1L;
	private float x;
	private float y;
	
	public Point2D(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setX(float x) {
		this.x = x;		
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	@Override
	public Point2D clone() {
		return new Point2D(x, y);
	}

}
