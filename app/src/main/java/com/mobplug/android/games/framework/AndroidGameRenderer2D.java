package com.mobplug.android.games.framework;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.mobplug.games.framework.interfaces.Game;
import com.mobplug.games.framework.interfaces.GameRenderer;

public abstract class AndroidGameRenderer2D<G extends Game> implements GameRenderer<G>, SurfaceHolder.Callback {
	private SurfaceHolder holder;
	protected G game;
	
	public AndroidGameRenderer2D(SurfaceHolder holder, G game) {
		this.game = game;
		this.holder = holder;
		holder.addCallback(this);
	}
	
	@Override
	public void render() {
		Canvas canvas = null;
		try {			
			canvas = holder.lockCanvas(null);
			synchronized(holder) {
				if (canvas != null) render(canvas);
			}
		} finally {
			if (canvas != null) holder.unlockCanvasAndPost(canvas);
		}
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		setSize(width, height);
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	}
	
	public abstract void setSize(int width, int height);
	public abstract void render(Canvas canvas);
}
