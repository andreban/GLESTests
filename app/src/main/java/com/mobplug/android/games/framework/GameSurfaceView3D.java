package com.mobplug.android.games.framework;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.mobplug.games.framework.interfaces.GameRunnable;

public class GameSurfaceView3D extends GLSurfaceView {
	protected GameRunnable gameRunnable;
	
	public GameSurfaceView3D(Context context) {
		super(context);				
	}

	public GameSurfaceView3D(Context context, AttributeSet attrs) {
		super(context, attrs);		
	}	

	public void setGameRunnable(GameRunnable gameRunnable) { this.gameRunnable = gameRunnable; }
	public GameRunnable getGameRunnable() { return this.gameRunnable; }
}
