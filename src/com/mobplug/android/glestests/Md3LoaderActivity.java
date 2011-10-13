package com.mobplug.android.glestests;

import android.app.Activity;
import android.os.Bundle;

import com.mobplug.android.games.framework.GameSurfaceView3D;
import com.mobplug.android.glestests.md3loader.Md3LoaderGame;
import com.mobplug.android.glestests.md3loader.Md3LoaderRenderer;
import com.mobplug.games.framework.BaseGameRunnable;
import com.mobplug.games.framework.interfaces.GameRenderer;
import com.mobplug.games.framework.interfaces.GameRunnable;

public class Md3LoaderActivity extends Activity {
	private GameRunnable gameRunnable;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameSurfaceView3D surfaceView = new GameSurfaceView3D(this);
        surfaceView.setEGLContextClientVersion(2);
        Md3LoaderGame game = new Md3LoaderGame();
        GameRenderer<Md3LoaderGame> renderer = new Md3LoaderRenderer(this, surfaceView, game);
        gameRunnable = new BaseGameRunnable<Md3LoaderGame>(renderer, game);
        setContentView(surfaceView);     
    }
    
    @Override
    protected void onStart() {    	
    	super.onStart();
    	gameRunnable.start();
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	gameRunnable.stop();
    }
}
