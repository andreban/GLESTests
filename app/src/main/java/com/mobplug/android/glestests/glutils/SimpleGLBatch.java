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
    
    private FloatBuffer vertexData;   
    private FloatBuffer colorData;   
    private FloatBuffer normalData;  
    private FloatBuffer textureData;   
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
        
 
        if (vColorData != null && vColorData.length > 0) {
            colorData = BufferUtils.createFloatBuffer(vColorData.length);
            colorData.put(vColorData);
            colorData.flip();    
        }
        
        if (vNormalData != null && vNormalData.length > 0) {
            normalData = BufferUtils.createFloatBuffer(vNormalData.length);
            normalData.put(vNormalData);
            normalData.flip();    
        }
        
        if (vTextureData != null && vTextureData.length > 0) {
            textureData = BufferUtils.createFloatBuffer(vTextureData.length);
            textureData.put(vTextureData);
            textureData.flip();
        }                        
    }
    
    @Override
    public void draw(Map<String, Integer> params) {
    	GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    	GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);    	
        int inVertexLocation = params.get("inVertex");
        vertexData.position(0);
        GLES20.glVertexAttribPointer(inVertexLocation, 4, GLES20.GL_FLOAT, false, 4 * 4, vertexData);
        GLES20.glEnableVertexAttribArray(inVertexLocation);
        
        if (params.containsKey("inNormal")) {
            int normalLocation = params.get("inNormal");
            GLES20.glVertexAttribPointer(normalLocation, 3, GLES20.GL_FLOAT, false, 3 * 4, normalData);
            GLES20.glEnableVertexAttribArray(normalLocation);
        }
        
        if (params.containsKey("inColor")) {
            int colorLocation = params.get("inColor");
            GLES20.glVertexAttribPointer(colorLocation, 4, GLES20.GL_FLOAT, false, 4 * 4, colorData);
            GLES20.glEnableVertexAttribArray(colorLocation);
        }
        
        if (params.containsKey("inTexCoord")) {
            int textureLocation = params.get("inTexCoord");
            GLES20.glVertexAttribPointer(textureLocation, 2, GLES20.GL_FLOAT, false, 2 * 4, textureData);
            GLES20.glEnableVertexAttribArray(textureLocation);
        }           
        //Draw triangle
        indexData.position(0);
        GLES20.glDrawElements(mode, numElements, GLES20.GL_UNSIGNED_SHORT, indexData);           
    }
}
