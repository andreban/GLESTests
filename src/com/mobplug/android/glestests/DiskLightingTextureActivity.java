package com.mobplug.android.glestests;

import android.app.Activity;
import android.os.Bundle;

import com.mobplug.android.games.framework.GameSurfaceView3D;
import com.mobplug.android.glestests.disklightingtexture.DiskLightingTextureGame;
import com.mobplug.android.glestests.disklightingtexture.DiskLightingTextureRenderer;
import com.mobplug.games.framework.BaseGameRunnable;
import com.mobplug.games.framework.interfaces.GameRenderer;
import com.mobplug.games.framework.interfaces.GameRunnable;

public class DiskLightingTextureActivity extends Activity {
	private GameRunnable gameRunnable;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameSurfaceView3D surfaceView = new GameSurfaceView3D(this);
        surfaceView.setEGLContextClientVersion(2);
        DiskLightingTextureGame game = new DiskLightingTextureGame();
        GameRenderer<DiskLightingTextureGame> renderer = new DiskLightingTextureRenderer(this, surfaceView, game);
        gameRunnable = new BaseGameRunnable<DiskLightingTextureGame>(renderer, game);
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
