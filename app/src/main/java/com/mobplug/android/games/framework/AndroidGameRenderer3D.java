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
