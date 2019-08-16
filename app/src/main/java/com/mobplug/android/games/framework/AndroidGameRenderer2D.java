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
