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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

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
        final Md3LoaderGame game = new Md3LoaderGame();        
        surfaceView.setOnTouchListener(new OnTouchListener() {	
        	float originX = Float.NaN;
        	float originY = Float.NaN;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					originX = event.getX();
					originY = event.getY();
				} else if (event.getAction() == MotionEvent.ACTION_MOVE) {				
					float amountx = event.getX() - originX;
					float amounty = event.getY() - originY;
					originX = event.getX();
					originY = event.getY();					
					game.addXRotation(amountx);
					game.addYRotation(amounty);
				} 
				
				return true;
			}
		});
        surfaceView.setEGLContextClientVersion(2);

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
