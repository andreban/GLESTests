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
