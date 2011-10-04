package com.mobplug.android.glestests.toruslighting;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

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
import com.mobplug.android.glestests.glutils.MatrixStack;

public class TorusLightingRenderer extends AndroidGameRenderer3D<TorusLightingGame> {
    private static String TAG = "GLES20TriangleRenderer";	

    private GLShader shader;
    private GLBatch glBatch;
    private GLFrustrum viewFrustrum;
    private MatrixStack modelViewStack;
    private MatrixStack projectionStack;
    
	
	public TorusLightingRenderer(Context context, GLSurfaceView glSurfaceView, TorusLightingGame game) {
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
                
        
        shader.setUniformMatrix4("mvMatrix", false, modelViewStack.getMatrix());
        shader.setUniformMatrix4("pMatrix", false, projectionStack.getMatrix());
        shader.setUniform3("vLightPos", -10.0f, -10.0f, 10.0f);        
        
        shader.setUniform4("inColor", 0.0f, 1.0f, 0.0f, 1.0f);

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
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		GLES20.glClearColor(0.0f,0.0f,0.0f,0.0f);
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		shader = GLShaderFactory.getPointLightDiffuseShader();
		viewFrustrum = new GLFrustrum();
		modelViewStack = new MatrixStack();       	
	}
	
    private void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, op + ": glError " + error);
            throw new RuntimeException(op + ": glError " + error);
        }
    }	
}