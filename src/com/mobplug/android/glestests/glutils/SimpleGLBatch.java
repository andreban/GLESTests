package com.mobplug.android.glestests.glutils;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Map;

import android.opengl.GLES20;


/**
 *
 * @author andreban
 */
public class SimpleGLBatch implements GLBatch {
    private int mode = GLES20.GL_TRIANGLES;
    
    private int vertexBuffer = -1;
    private FloatBuffer vertexData;
    
    private int colorBuffer = -1;
    private FloatBuffer colorData;
    
    private int normalBuffer = -1;
    private FloatBuffer normalData;
    
    private int textureBuffer = - 1;
    private FloatBuffer textureData;
    
    private int indexBuffer = -1;
    private ShortBuffer indexData;
    
    private int numElements = -1;
    
    public SimpleGLBatch(int mode, float[] vVertexData, short[] vIndexData) {
        this(mode, vVertexData, null, null, null, vIndexData);
    }
    
    public SimpleGLBatch(int mode, float[] vVertexData, float[] vColorData,
            float[] vNormalData, float[] vTextureData, short[] vIndexData) {
        this.mode = mode;
        numElements = vIndexData.length;
        vertexData = BufferUtils.createFloatBuffer(vVertexData.length);
        vertexData.put(vVertexData);
        vertexData.flip();
        
        indexData = BufferUtils.createShortBuffer(vIndexData.length);
        indexData.put(vIndexData);
        indexData.flip();
        
        int[] tmpBuffers = new int[1];
        GLES20.glGenBuffers(1, tmpBuffers, 0);        
        vertexBuffer = tmpBuffers[0];
        
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBuffer);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vVertexData.length * 4, vertexData, GLES20.GL_STATIC_DRAW);
 
        if (vColorData != null && vColorData.length > 0) {
            colorData = BufferUtils.createFloatBuffer(vColorData.length);
            colorData.put(vColorData);
            colorData.flip();    
            GLES20.glGenBuffers(1, tmpBuffers, 0);              
            colorBuffer = tmpBuffers[0];
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, colorBuffer);
            GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vColorData.length * 4, colorData, GLES20.GL_STATIC_DRAW);
        }
        
        if (vNormalData != null && vNormalData.length > 0) {
            normalData = BufferUtils.createFloatBuffer(vNormalData.length);
            normalData.put(vNormalData);
            normalData.flip();    
            GLES20.glGenBuffers(1, tmpBuffers, 0); 
            normalBuffer = tmpBuffers[0];
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, normalBuffer);
            GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vNormalData.length * 4, normalData, GLES20.GL_STATIC_DRAW);
        }
        
        if (vTextureData != null && vTextureData.length > 0) {
            textureData = BufferUtils.createFloatBuffer(vTextureData.length);
            textureData.put(vTextureData);
            textureData.flip();
            GLES20.glGenBuffers(1, tmpBuffers, 0);             
            textureBuffer = tmpBuffers[0];
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, textureBuffer);
            GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vTextureData.length *  4, textureData, GLES20.GL_STATIC_DRAW);
        }
        
        GLES20.glGenBuffers(1, tmpBuffers, 0);         
        indexBuffer = tmpBuffers[0];
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, vIndexData.length * 2, indexData, GLES20.GL_STATIC_DRAW); 
                
    }
    
    @Override
    public void draw(Map<String, Integer> params) {
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBuffer);
        int inVertexLocation = params.get("inVertex");
        vertexData.position(0);
        GLES20.glVertexAttribPointer(inVertexLocation, 4, GLES20.GL_FLOAT, false, 4 * 4, vertexData);
        GLES20.glEnableVertexAttribArray(inVertexLocation);
        
        if (params.containsKey("inNormal") && normalBuffer >= 0) {
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, normalBuffer);
            int normalLocation = params.get("inNormal");
            GLES20.glVertexAttribPointer(normalLocation, 3, GLES20.GL_FLOAT, false, 3 * 4, normalData);
            GLES20.glEnableVertexAttribArray(normalLocation);
        }
        
        if (params.containsKey("inColor") && colorBuffer >= 0) {
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, colorBuffer);
            int colorLocation = params.get("inColor");
            GLES20.glVertexAttribPointer(colorLocation, 4, GLES20.GL_FLOAT, false, 4 * 4, colorData);
            GLES20.glEnableVertexAttribArray(colorLocation);
        }
        
        if (params.containsKey("inTexCoord") && textureBuffer >= 0) {
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, textureBuffer);
            int textureLocation = params.get("inTexCoord");
            GLES20.glVertexAttribPointer(textureLocation, 2, GLES20.GL_FLOAT, false, 2 * 4, textureData);
            GLES20.glEnableVertexAttribArray(textureBuffer);
        }

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexBuffer);      
        
        //Draw triangle
        indexData.position(0);
        GLES20.glDrawElements(mode, numElements, GLES20.GL_UNSIGNED_SHORT, indexData);           
    }
}
