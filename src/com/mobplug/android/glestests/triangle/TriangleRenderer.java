package com.mobplug.android.glestests.triangle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;
import android.util.Log;

import com.mobplug.android.games.framework.AndroidGameRenderer3D;
import com.mobplug.android.glestests.glutils.GLBatch;
import com.mobplug.android.glestests.glutils.GLFrustrum;
import com.mobplug.android.glestests.glutils.GLShader;
import com.mobplug.android.glestests.glutils.GLShaderFactory;
import com.mobplug.android.glestests.glutils.Math3D;
import com.mobplug.android.glestests.glutils.MatrixStack;
import com.mobplug.android.glestests.glutils.SimpleGLBatch;

public class TriangleRenderer extends AndroidGameRenderer3D<TriangleGame> {
    private static String TAG = "GLES20TriangleRenderer";	

    private final float[] mTriangleVerticesData = {
            // X, Y, Z, U, V
            -1.0f, -0.5f, 0, 1.0f,
            1.0f, -0.5f, 0, 1.0f,
            0.0f,  1.11803399f, 0, 1.0f };
    private final short[] mIndices = {0, 1, 2};

    private float[] mMVPMatrix = new float[16];

    private GLShader shader;
    private GLBatch glBatch;
    private GLFrustrum viewFrustrum;
    private MatrixStack modelViewStack;
    private MatrixStack projectionStack;
    
	
	public TriangleRenderer(Context context, GLSurfaceView glSurfaceView, TriangleGame game) {
		super(glSurfaceView, game);		
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
//        modelViewStack.translate(0.0f, 0.0f, -2.5f);        
        modelViewStack.rotate(angle, 1, 1, 1.0f);
        
        Math3D.matrixMultiply44(mMVPMatrix, projectionStack.getMatrix(), modelViewStack.getMatrix());        
        
        shader.setUniformMatrix4("mvpMatrix", false, mMVPMatrix);
        shader.setUniform4("vColor", 0.0f, 1.0f, 0.0f, 1.0f);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
        checkGlError("glDrawArrays");	
        modelViewStack.pop();
		
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
//        float ratio = (float) width / height;
//        viewFrustrum.setPerspective(35f, ratio, 1.0f, 1000f); 
		projectionStack= new MatrixStack(viewFrustrum.getProjectionMatrix());        
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		glBatch = new SimpleGLBatch(GLES20.GL_TRIANGLES, mTriangleVerticesData, mIndices);		
		GLES20.glClearColor(0.0f,0.0f,0.0f,0.0f);
		shader = GLShaderFactory.getFlatShader();//new GLShader(mVertexShader, mFragmentShader);
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
