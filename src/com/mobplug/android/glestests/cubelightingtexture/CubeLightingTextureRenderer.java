package com.mobplug.android.glestests.cubelightingtexture;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;

import com.mobplug.android.games.framework.AndroidGameRenderer3D;
import com.mobplug.android.glestests.R;
import com.mobplug.android.glestests.glutils.GLBatch;
import com.mobplug.android.glestests.glutils.GLBatchFactory;
import com.mobplug.android.glestests.glutils.GLFrustrum;
import com.mobplug.android.glestests.glutils.GLShader;
import com.mobplug.android.glestests.glutils.GLShaderFactory;
import com.mobplug.android.glestests.glutils.GLTexture;
import com.mobplug.android.glestests.glutils.MatrixStack;

public class CubeLightingTextureRenderer extends AndroidGameRenderer3D<CubeLightingTextureGame> {
    private Context context;

    private GLShader shader;
    private GLBatch glBatch;
    private GLTexture texture0;
    private GLFrustrum viewFrustrum;
    private MatrixStack modelViewStack;
    private MatrixStack projectionStack;
    	
	public CubeLightingTextureRenderer(Context context, GLSurfaceView glSurfaceView, CubeLightingTextureGame game) {
		super(glSurfaceView, game);
		this.context = context;
		glBatch = GLBatchFactory.makeCube(0.5f, 0.5f, 0.5f);
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
        shader.setUniform4("inColor", 0.0f, 1.0f, 0.0f, 1.0f);        
        
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
		shader = GLShaderFactory.getTexturePointLightDiffuseShader();
		viewFrustrum = new GLFrustrum();
		modelViewStack = new MatrixStack();   
		texture0 = new GLTexture(context, R.raw.robot);				
	}	    	
}
