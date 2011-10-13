package com.mobplug.android.glestests.glutils;

import java.nio.FloatBuffer;
import static android.opengl.GLES20.*;
import java.nio.ShortBuffer;
import java.util.Map;

import android.opengl.GLES20;


/**
 *
 * @author andreban
 */
public class VBOGLBatch implements GLBatch {
    private int mode = GLES20.GL_TRIANGLES;
    
    private int vertexBufferId = -1;
//    private int colorBufferId = -1;
//    private int normalBufferId = -1;
//    private int textCoordBufferId = -1;
    private int indexBufferId = -1;
    
    private int numElements = -1;
    
    public VBOGLBatch(int mode, float[] vVertexData, short[] vIndexData) {
        this(mode, vVertexData, null, null, null, vIndexData);
    }
    
    public VBOGLBatch(int mode, float[] vVertexData, float[] vColorData,
            float[] vNormalData, float[] vTextureData, short[] vIndexData) {
        this.mode = mode;
        numElements = vIndexData.length;
        int[] buffid = new int[1];
        
        GLES20.glGenBuffers(1, buffid, 0);
        vertexBufferId = buffid[0];        
        FloatBuffer vertexData;                      
        vertexData = BufferUtils.createFloatBuffer(vVertexData.length);
        vertexData.put(vVertexData);
        vertexData.flip();
        glBindBuffer(GL_ARRAY_BUFFER, vertexBufferId);        
        glBufferData(GLES20.GL_ARRAY_BUFFER, vVertexData.length * 4, vertexData, GLES20.GL_STATIC_DRAW);
        
        
        GLES20.glGenBuffers(1, buffid, 0);
        indexBufferId = buffid[0];        
        ShortBuffer indexData;        
        indexData = BufferUtils.createShortBuffer(vIndexData.length);
        indexData.put(vIndexData);
        indexData.flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBufferId);        
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, vIndexData.length * 2, indexData, GLES20.GL_STATIC_DRAW);        
        
        
//        if (vColorData != null && vColorData.length > 0) {
//            FloatBuffer colorData;           	
//            colorData = BufferUtils.createFloatBuffer(vColorData.length);
//            colorData.put(vColorData);
//            colorData.flip();    
//        }
//        
//        if (vNormalData != null && vNormalData.length > 0) {
//            FloatBuffer normalData;          	
//            normalData = BufferUtils.createFloatBuffer(vNormalData.length);
//            normalData.put(vNormalData);
//            normalData.flip();    
//        }
//        
//        if (vTextureData != null && vTextureData.length > 0) {
//            FloatBuffer textureData;        	
//            textureData = BufferUtils.createFloatBuffer(vTextureData.length);
//            textureData.put(vTextureData);
//            textureData.flip();
//        }                        
    }
    
    @Override
    public void draw(Map<String, Integer> params) {
        int inVertexLocation = params.get("inVertex");
    	GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBufferId);        
        GLES20.glVertexAttribPointer(inVertexLocation, 4, GLES20.GL_FLOAT, false, 4 * 4, 0);
        GLES20.glEnableVertexAttribArray(inVertexLocation);
        
//        if (params.containsKey("inNormal")) {
//            int normalLocation = params.get("inNormal");
//            GLES20.glVertexAttribPointer(normalLocation, 3, GLES20.GL_FLOAT, false, 3 * 4, normalData);
//            GLES20.glEnableVertexAttribArray(normalLocation);
//        }
//        
//        if (params.containsKey("inColor")) {
//            int colorLocation = params.get("inColor");
//            GLES20.glVertexAttribPointer(colorLocation, 4, GLES20.GL_FLOAT, false, 4 * 4, colorData);
//            GLES20.glEnableVertexAttribArray(colorLocation);
//        }
//        
//        if (params.containsKey("inTexCoord")) {
//            int textureLocation = params.get("inTexCoord");
//            GLES20.glVertexAttribPointer(textureLocation, 2, GLES20.GL_FLOAT, false, 2 * 4, textureData);
//            GLES20.glEnableVertexAttribArray(textureLocation);
//        }           
        //Draw triangle
    	GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexBufferId);
        GLES20.glDrawElements(mode, numElements, GLES20.GL_UNSIGNED_SHORT, 0);           
    }
}
