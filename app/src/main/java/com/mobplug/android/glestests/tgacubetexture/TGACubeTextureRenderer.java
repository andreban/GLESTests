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

package com.mobplug.android.glestests.tgacubetexture;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;
import android.util.Log;

import com.mobplug.android.games.framework.AndroidGameRenderer3D;
import com.mobplug.android.glestests.glutils.GLBatch;
import com.mobplug.android.glestests.glutils.GLBatchFactory;
import com.mobplug.android.glestests.glutils.GLFrustrum;
import com.mobplug.android.glestests.glutils.GLShader;
import com.mobplug.android.glestests.glutils.GLShaderFactory;
import com.mobplug.android.glestests.glutils.Math3D;
import com.mobplug.android.glestests.glutils.MatrixStack;
import com.mobplug.android.glestests.glutils.TGATexture;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import me.bandarra.android.glestests.R;

public class TGACubeTextureRenderer extends AndroidGameRenderer3D<TGACubeTextureGame> {
    private static String TAG = "GLES20TriangleRenderer";	
    private Context context;
    private float[] mMVPMatrix = new float[16];

    private GLShader shader;
    private GLBatch glBatch;
    private TGATexture texture0;
    private GLFrustrum viewFrustrum;
    private MatrixStack modelViewStack;
    private MatrixStack projectionStack;
    	
	public TGACubeTextureRenderer(Context context, GLSurfaceView glSurfaceView, TGACubeTextureGame game) {
		super(glSurfaceView, game);
		this.context = context;
		glBatch = GLBatchFactory.makeCube(0.5f, 0.5f, 0.5f);		
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
		
		modelViewStack.push();
		
		shader.useShader();		
        checkGlError("glUseProgram");
        
        texture0.useTexture(GLES20.GL_TEXTURE0);      
		   
        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);
        modelViewStack.translate(0.0f, 0.0f, -2.5f);        
        modelViewStack.rotate(angle, 1, 1, 1);
        
        Math3D.matrixMultiply44(mMVPMatrix, projectionStack.getMatrix(), modelViewStack.getMatrix());        
        
        shader.setUniformMatrix4("mvpMatrix", false, mMVPMatrix);

        glBatch.draw(shader.getAttributeLocations());
        modelViewStack.pop();
		
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        viewFrustrum.setPerspective(35f, ratio, 1.0f, 1000f); 
		projectionStack= new MatrixStack(viewFrustrum.getProjectionMatrix());        
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		GLES20.glClearColor(0.0f,0.0f,0.0f,0.0f);
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);		
		shader = GLShaderFactory.getTextureReplaceShader();
		viewFrustrum = new GLFrustrum();
		modelViewStack = new MatrixStack();   
		texture0 = new TGATexture(context, R.raw.brick);				
	}
	
    private void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, op + ": glError " + error);
            throw new RuntimeException(op + ": glError " + error);
        }
    }	
}
