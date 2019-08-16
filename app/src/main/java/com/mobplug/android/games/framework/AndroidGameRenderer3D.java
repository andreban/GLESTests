package com.mobplug.android.games.framework;

import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;

import com.mobplug.games.framework.interfaces.Game;
import com.mobplug.games.framework.interfaces.GameRenderer;

public abstract class AndroidGameRenderer3D<G extends Game> implements GameRenderer<G>, Renderer {

	private GLSurfaceView glSurfaceView;
	protected G game;
	
	public AndroidGameRenderer3D(GLSurfaceView glSurfaceView, G game) {
		this.game = game;
		this.glSurfaceView = glSurfaceView;
        glSurfaceView.setRenderer(this);		
		glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}
	
	@Override
	public void render() {
		glSurfaceView.requestRender();
	}
	

}
