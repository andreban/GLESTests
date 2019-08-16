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

package com.mobplug.android.glestests.torustoonshader;

import java.nio.ByteBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;
import android.util.Log;

import com.mobplug.android.games.framework.AndroidGameRenderer3D;
import com.mobplug.android.glestests.glutils.BufferUtils;
import com.mobplug.android.glestests.glutils.GLBatch;
import com.mobplug.android.glestests.glutils.GLBatchFactory;
import com.mobplug.android.glestests.glutils.GLFrustrum;
import com.mobplug.android.glestests.glutils.GLShader;
import com.mobplug.android.glestests.glutils.GeometryTransform;
import com.mobplug.android.glestests.glutils.MatrixStack;

public class TorusToonShaderRenderer extends AndroidGameRenderer3D<TorusToonShaderGame> {
    private static String TAG = "TorusToonShaderRenderer";	
    private static final String VERTEX_PROGRAM = 
    	//uniforms
        "uniform mat4 mvMatrix;\n"
        + "uniform mat4 mvpMatrix;\n"
        + "uniform vec3 vLightPos;\n"
        + "uniform vec4 inColor\n;"
        + "uniform mat3 vNormalMatrix;\n"
        //attributes
        + "attribute vec4 inVertex;\n"
        + "attribute vec3 inNormal;\n"
        //varyings
        + "varying float texcoord;\n"
        + "void main(void) { "
        + "   vec3 vNorm = normalize(vNormalMatrix * inNormal);"
        + "   vec4 ecPosition;"
        + "   vec3 ecPosition3;"
        + "   ecPosition = mvMatrix * inVertex;"
        + "   ecPosition3 = ecPosition.xyz /ecPosition.w;"
        + "   vec3 vLightDir = normalize(vLightPos - ecPosition3);"
        + "   texcoord = max(0.1, dot(vNorm, vLightDir)); "
        + "   gl_Position = mvpMatrix * inVertex; "
        + "}";

    private static final String FRAGMENT_PROGRAM =
	   "precision mediump float;"	
	    + "uniform sampler2D colorTable;"
        + "varying float texcoord; "
        + "void main(void) { "
        + " gl_FragColor = texture2D(colorTable, vec2(texcoord,1)); "
        + "}";
    private GLShader shader;
    private GLBatch glBatch;
    private GLFrustrum viewFrustrum;
    private MatrixStack modelViewStack;
    private MatrixStack projectionStack;
    private GeometryTransform transformPipeline;
    
    private int textureid;
	
	public TorusToonShaderRenderer(Context context, GLSurfaceView glSurfaceView, TorusToonShaderGame game) {
		super(glSurfaceView, game);
		glBatch = GLBatchFactory.makeTorus(0.4f, 0.15f, 30, 30);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		modelViewStack.push();
		shader.useShader();
		
        checkGlError("glUseProgram");
		
		GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

        glBatch.draw(shader.getAttributeLocations());      

        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);
        modelViewStack.translate(0.0f, 0.0f, -2.5f);        
        modelViewStack.rotate(angle, 1, 1, 1.0f);
                
        
        shader.setUniformMatrix4("mvMatrix", false, transformPipeline.getModelViewMatrix());
        shader.setUniformMatrix4("mvpMatrix", false, transformPipeline.getModelViewProjectionMatrix());
        shader.setUniformMatrix3("vNormalMatrix", false, transformPipeline.getNormalMatrix());
        shader.setUniform3("vLightPos", -10.0f, -10.0f, 10.0f);        
        shader.setUniform1i("colorTable", 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
        checkGlError("glDrawArrays");	
        modelViewStack.pop();
		
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        viewFrustrum.setPerspective(35f, ratio, 1.0f, 1000f); 
		projectionStack= new MatrixStack(viewFrustrum.getProjectionMatrix()); 
		transformPipeline = new GeometryTransform(modelViewStack, projectionStack);		
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		GLES20.glClearColor(0.0f,0.0f,0.0f,0.0f);
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		shader = new GLShader(VERTEX_PROGRAM, FRAGMENT_PROGRAM);
		viewFrustrum = new GLFrustrum();
		modelViewStack = new MatrixStack();    
		
		int[] textures = new int[1];
		GLES20.glGenTextures(1, textures, 0);
		textureid = textures[0];
		
		byte[] colorTable = {
				32, 0, 0,
				64, 0, 0,
				(byte)128, 0, 0,
				(byte)255, 0, 0
		};
		ByteBuffer buff = BufferUtils.createByteBuffer(colorTable.length);
		buff.put(colorTable);
		buff.flip();
		
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureid);
		GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGB, 4, 1, 0, GLES20.GL_RGB, GLES20.GL_UNSIGNED_BYTE, buff);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);		
	}
	
    private void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, op + ": glError " + error);
            throw new RuntimeException(op + ": glError " + error);
        }
    }	
}
