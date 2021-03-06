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

package com.mobplug.android.glestests.cylinderlightingtexture;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;

import com.mobplug.android.games.framework.AndroidGameRenderer3D;
import com.mobplug.android.glestests.glutils.GLBatch;
import com.mobplug.android.glestests.glutils.GLBatchFactory;
import com.mobplug.android.glestests.glutils.GLFrustrum;
import com.mobplug.android.glestests.glutils.GLShader;
import com.mobplug.android.glestests.glutils.GLTexture;
import com.mobplug.android.glestests.glutils.MatrixStack;

import me.bandarra.android.glestests.R;

public class CylinderLightingTextureRenderer extends AndroidGameRenderer3D<CylinderLightingTextureGame> {
	   private static final String VERTEX_SHADER =
           "uniform mat4 mvMatrix;"
           + "uniform mat4 pMatrix;"
           + "uniform vec3 vLightPos;"
           + "uniform vec4 inColor;"
           + "attribute vec4 inVertex;"
           + "attribute vec3 inNormal;"
           + "varying vec4 vFragColor;"
           + "attribute vec2 inTexCoord;"
           + "varying vec2 vTex;"
           + "void main(void) { "
           + " mat3 mNormalMatrix;"
           + " mNormalMatrix[0] = normalize(mvMatrix[0].xyz);"
           + " mNormalMatrix[1] = normalize(mvMatrix[1].xyz);"
           + " mNormalMatrix[2] = normalize(mvMatrix[2].xyz);"
           + " vec3 vNorm = normalize(mNormalMatrix * inNormal);"
           + " vec4 ecPosition;"
           + " vec3 ecPosition3;"
           + " ecPosition = mvMatrix * inVertex;"
           + " ecPosition3 = ecPosition.xyz /ecPosition.w;"
           + " vec3 vLightDir = normalize(vLightPos - ecPosition3);"
           + " float fDot = max(0.1, dot(vNorm, vLightDir)); "
           + " vFragColor.rgb = inColor.rgb * fDot;"
           + " vFragColor.a = inColor.a;"
           + " vTex = inTexCoord;"
           + " mat4 mvpMatrix;"
           + " mvpMatrix = pMatrix * mvMatrix;"
           + " gl_Position = mvpMatrix * inVertex; "
           + "}";
   private static final String FRAGMENT_SHADER =
   	   "precision mediump float;"+	   
           "varying vec4 vFragColor;" +
            "varying vec2 vTex;"
           + "uniform sampler2D textureUnit0;"
           + "void main(void) { "
           + " gl_FragColor = vFragColor * texture2D(textureUnit0, vTex);"
           + "}";	
    private Context context;

    private GLShader shader;
    private GLBatch glBatch;
    private GLTexture texture0;
    private GLFrustrum viewFrustrum;
    private MatrixStack modelViewStack;
    private MatrixStack projectionStack;
    	
	public CylinderLightingTextureRenderer(Context context, GLSurfaceView glSurfaceView, CylinderLightingTextureGame game) {
		super(glSurfaceView, game);
		this.context = context;
		glBatch = GLBatchFactory.makeCylinder(0.1f, 0.1f, 0.5f, 26, 13);		
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);		
		modelViewStack.push();
		
		shader.useShader();		
        texture0.useTexture(GLES20.GL_TEXTURE0);      
		     
        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);
        modelViewStack.translate(0.0f, 0.0f, -2.5f);        
        modelViewStack.rotate(angle, 1, 1, 1);
        
        shader.setUniformMatrix4("mvMatrix", false, modelViewStack.getMatrix());
        shader.setUniformMatrix4("pMatrix", false, projectionStack.getMatrix());
        shader.setUniform3("vLightPos", -10.0f, -10.0f, 10.0f);             
        shader.setUniform4("inColor", 1.0f, 1.0f, 1.0f, 1.0f);        
        
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
		shader = new GLShader(VERTEX_SHADER, FRAGMENT_SHADER);
		viewFrustrum = new GLFrustrum();
		modelViewStack = new MatrixStack();   
		texture0 = new GLTexture(context, R.raw.robot);
	}	    	
}
