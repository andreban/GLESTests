package com.mobplug.android.glestests.glutils.models.md3;

import java.util.Map;

import android.opengl.GLES20;

import com.mobplug.android.glestests.glutils.GLBatch;
import com.mobplug.android.glestests.glutils.VBOGLBatch;

/**
 *
 * @author andreban
 */
public class Md3MeshFrame {
    private GLBatch glBatch;
    public Md3MeshFrame(int numElements, int vertexData, int normalData, int textCoordData, int triangleIndexes) {
    	glBatch = new VBOGLBatch(GLES20.GL_TRIANGLES, numElements, vertexData, -1, normalData, textCoordData, triangleIndexes);
    	
    }
    public Md3MeshFrame(float[] vertexData, float[] normalData, float[] texCoordData, short[] triangleIndexes) {
        glBatch = new VBOGLBatch(GLES20.GL_TRIANGLES, vertexData, null, normalData, texCoordData, triangleIndexes);
    }
    
    public void render(Map<String, Integer> shaderParams) {
        glBatch.draw(shaderParams);
    }
}
