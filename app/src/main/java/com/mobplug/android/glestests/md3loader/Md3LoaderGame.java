package com.mobplug.android.glestests.md3loader;

import com.mobplug.games.framework.BaseGame;

public class Md3LoaderGame extends BaseGame {
	private static final long serialVersionUID = 1L;
	private float xRotation;
	private float yRotation;
	
	@Override
	public int getUpdateCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(long gameTime) {
		// TODO Auto-generated method stub

	}
	
	public void addXRotation(float rotation) {
		xRotation += rotation;
	}
	
	public void addYRotation(float rotation) {
		yRotation += rotation;
	}
	public float getXRotation() {
		return xRotation;
	}
	
	public float getYRotation() {
		return yRotation;
	}

}
