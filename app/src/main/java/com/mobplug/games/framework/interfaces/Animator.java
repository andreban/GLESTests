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

import java.io.Serializable;

public interface Animator<T> extends Serializable {
	/**
	 * updates the Animator with current gametime
	 * @param gameTime time elapsed in game
	 */
	public void update(long gameTime);
	
	/**
	 * gets animator current image
	 * @return an image
	 */
	public T getImage();
	
	/**
	 * Adds an image to the animation
	 * 
	 * @param image the image to be shown
	 * @param delay how long the image is displayed
	 */
	public void addImage(T image, long delay);
	
	/**
	 * starts the animation
	 * @param loop if animation should loop
	 */
	public void start(long gameTime, boolean loop);
	
	/**
	 * starts animation without loop
	 */
	public void start(long gameTime);
}
