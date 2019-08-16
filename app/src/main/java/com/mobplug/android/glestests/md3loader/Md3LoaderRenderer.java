package com.mobplug.android.glestests.md3loader;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.mobplug.android.games.framework.AndroidGameRenderer3D;
import com.mobplug.android.glestests.glutils.GLFrustrum;
import com.mobplug.android.glestests.glutils.GLShader;
import com.mobplug.android.glestests.glutils.MatrixStack;
import com.mobplug.android.glestests.glutils.models.md3.Md3Model;

public class Md3LoaderRenderer extends AndroidGameRenderer3D<Md3LoaderGame> {
    private static String TAG = "Md3LoaderRenderer";	
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
	       + " float fDot = max(0.5, dot(vNorm, vLightDir)); "
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
//           + " gl_FragColor = vFragColor;"
           + "}";	
    private GLShader shader;
    private Md3Model md3Model;
    private GLFrustrum viewFrustrum;
    private MatrixStack modelViewStack;
    private MatrixStack projectionStack;
    private Context context;
    
	
	public Md3LoaderRenderer(Context context, GLSurfaceView glSurfaceView, Md3LoaderGame game) {
		super(glSurfaceView, game);
		md3Model = new Md3Model();
		this.context = context;
		
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		modelViewStack.push();
		shader.useShader();
		
        checkGlError("glUseProgram");
		
		GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

      
//        long time = SystemClock.uptimeMillis() % 4000L;
//        float angle = 0.090f * ((int) time);
        modelViewStack.translate(0.0f, 0.0f, -300.5f);        
        modelViewStack.rotate(game.getXRotation(), 0.0f, 1.0f, 0.0f);
        modelViewStack.rotate(game.getYRotation(), 1.0f, 0.0f, 0.0f);
        modelViewStack.translate(0.0f, -50.0f, 0.0f);        
                
        
//        shader.setUniformMatrix4("mvMatrix", false, modelViewStack.getMatrix());
        shader.setUniformMatrix4("pMatrix", false, projectionStack.getMatrix());
        shader.setUniform3("vLightPos", -400.0f, -400.0f, 400.0f);        
        
        shader.setUniform4("inColor", 1.0f, 1.0f, 1.0f, 1.0f);
		md3Model.render(shader, modelViewStack);
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
		shader = new GLShader(VERTEX_SHADER, FRAGMENT_SHADER);
		viewFrustrum = new GLFrustrum();
		modelViewStack = new MatrixStack();  
		md3Model.loadModels(context);		
	}
	
    private void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, op + ": glError " + error);
            throw new RuntimeException(op + ": glError " + error);
        }
    }	
}
