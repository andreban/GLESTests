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

package com.mobplug.games.framework.entities;

public class Vector2D {
	private Point2D position;
	private float speedX;
	private float speedY;
	
	public Vector2D() {
		position = new Point2D(0.0f, 0.0f);
	}
	
	public Vector2D(float posx, float posy) {
		position = new Point2D(posx, posy);
	}
	
	public Vector2D(float posx, float posy, float speedx, float speedy) {
		position = new Point2D(posx, posy);
		this.speedX = speedx;
		this.speedY = speedy;
	}
	
	public Vector2D(Point2D position) {
		this.position = position;
	}
	
	public Vector2D(Point2D position, float speedx, float speedy) {
		this.position = position;
		this.speedX = speedx;
		this.speedY = speedy;
	}
	
	public Point2D getPosition() {
		return position;
	}
	
	public float getPositionX() {
		return position.getX();
	}
	
	public float getPositionY() {
		return position.getY();
	}
	
	public float getSpeedX() {
		return this.speedX;		
	}
	
	public float getSpeedY() {
		return this.speedY;
	}
	
	public void setPosition(Point2D position) {
		if (position == null) throw new IllegalArgumentException("Position cant be null");
		this.position = position;
	}
	
	public void setPosition(float x, float y) {
		position.setX(x);
		position.setY(y);
	}
	
	public void setSpeed(float speedx, float speedy) {
		this.speedX = speedx;
		this.speedY = speedy;
	}
	
	public void setVelocity(float velocity) {
		//TODO implement
		throw new UnsupportedOperationException("Not Implemented yet");
	}
	
	public double getVelocity() {
		return Math.sqrt(Math.pow(Math.abs(speedX), 2)
				+ Math.pow(Math.abs(speedY), 2));
	}
	
	/**
	 * Changes the vector bearing
	 * @param bearing in degrees
	 */
	public void setBearing(float bearing) {
		//TODO implement
		throw new UnsupportedOperationException("Not implemented yet");		
	}
	
	public float getBearing() {		
		if (speedX == 0.0 && speedY == 0.0) return Float.NaN;
		
		if (speedX == 0.0) {
			if (speedY > 0) return 90f;
			else return 270f;
		}
		
		if (speedY == 0.0) {
			if (speedX > 0) return 0f;
			else return 180f;
		}
		
		if (speedX >= 0 && speedY >= 0) {
			return (float)Math.toDegrees(Math.atan(speedY/speedX));
		} else if (speedX < 0 && speedY < 0) {
			return (float)(90 + Math.toDegrees(Math.atan(speedY/speedX)));
		} else if (speedX > 0) {
			return (float)(270 - Math.toDegrees(Math.atan(speedY/speedX)));
		} else {	
			return (float)(90 - Math.toDegrees(Math.atan(speedY/speedX)));
		}
	}	
}
