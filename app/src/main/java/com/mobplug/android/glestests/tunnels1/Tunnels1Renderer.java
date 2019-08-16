package com.mobplug.android.glestests.tunnels1;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.mobplug.android.games.framework.AndroidGameRenderer3D;
import com.mobplug.android.glestests.glutils.GLFrustrum;
import com.mobplug.android.glestests.glutils.GLShader;
import com.mobplug.android.glestests.glutils.GLShaderFactory;
import com.mobplug.android.glestests.glutils.MatrixStack;

public class Tunnels1Renderer extends AndroidGameRenderer3D<Tunnels1Game> {
    private static String TAG = "GLES20TriangleRenderer";	

    private GLShader shader;
    private Map glBatch;
    private GLFrustrum viewFrustrum;
    private MatrixStack modelViewStack;
    private MatrixStack projectionStack;
    private float pos = 0;
	
	public Tunnels1Renderer(Context context, GLSurfaceView glSurfaceView, Tunnels1Game game) {
		super(glSurfaceView, game);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		modelViewStack.push();
		shader.useShader();
		
        checkGlError("glUseProgram");
		
		GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

        glBatch.render(shader, pos);      

//        long time = SystemClock.uptimeMillis() % 4000L;
//        float angle = 0.090f * ((int) time);
        modelViewStack.translate(0.0f, 0.0f, pos);  

//        modelViewStack.rotate(angle, 1, 1, 1.0f);
                
        
        shader.setUniformMatrix4("mvMatrix", false, modelViewStack.getMatrix());
        shader.setUniformMatrix4("pMatrix", false, projectionStack.getMatrix());
        shader.setUniform3("vLightPos", 0.0f, 0.0f, 0.0f);        
        pos = pos + 0.1f;
        
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
		glBatch = new Map();		
	}
	
    private void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, op + ": glError " + error);
            throw new RuntimeException(op + ": glError " + error);
        }
    }	
}
