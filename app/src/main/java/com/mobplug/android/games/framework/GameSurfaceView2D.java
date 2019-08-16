package com.mobplug.android.games.framework;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.mobplug.games.framework.interfaces.GameRunnable;

public class GameSurfaceView2D extends SurfaceView implements SurfaceHolder.Callback {
	protected GameRunnable gameRunnable;
	
	public GameSurfaceView2D(Context context) {
		super(context);		
		getHolder().addCallback(this);
	}

	public GameSurfaceView2D(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		getHolder().addCallback(this);		
	}

	public GameSurfaceView2D(Context context, AttributeSet attrs) {
		super(context, attrs);		
		getHolder().addCallback(this);		
	}
	
	public void init(GameRunnable gameRunnable) {
		this.gameRunnable = gameRunnable;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int type, int width, int height) {
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		gameRunnable.start();
		setFocusable(true);
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		gameRunnable.stop();
		setFocusable(false);
	}
		
}
