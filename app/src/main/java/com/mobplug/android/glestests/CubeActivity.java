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

package com.mobplug.android.glestests;

import android.app.Activity;
import android.os.Bundle;

import com.mobplug.android.games.framework.GameSurfaceView3D;
import com.mobplug.android.glestests.cube.CubeGame;
import com.mobplug.android.glestests.cube.CubeRenderer;
import com.mobplug.games.framework.BaseGameRunnable;
import com.mobplug.games.framework.interfaces.GameRenderer;
import com.mobplug.games.framework.interfaces.GameRunnable;

public class CubeActivity extends Activity {
	private GameRunnable gameRunnable;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameSurfaceView3D surfaceView = new GameSurfaceView3D(this);
        surfaceView.setEGLContextClientVersion(2);
        CubeGame game = new CubeGame();
        GameRenderer<CubeGame> renderer = new CubeRenderer(this, surfaceView, game);
        gameRunnable = new BaseGameRunnable<CubeGame>(renderer, game);
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
