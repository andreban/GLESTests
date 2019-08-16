package com.mobplug.games.framework;

import com.mobplug.games.framework.interfaces.Animator;

public class StaticAnimator<T> implements Animator<T>{
	private static final long serialVersionUID = 4623043415695910922L;
	private T image;
	@Override
	public void addImage(T image, long delay) {
		this.image = image;
		
	}

	@Override
	public T getImage() {
		return this.image;
	}

	@Override
	public void start(long gameTime, boolean loop) {}

	@Override
	public void start(long gameTime) {}

	@Override
	public void update(long gameTime) {}

}
