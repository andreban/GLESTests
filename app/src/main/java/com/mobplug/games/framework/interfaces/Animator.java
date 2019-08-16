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
