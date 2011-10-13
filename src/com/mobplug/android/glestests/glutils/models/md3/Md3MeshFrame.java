package com.mobplug.android.glestests.glutils.models.md3;

import java.util.Map;

import javax.microedition.khronos.opengles.GL11;

import com.mobplug.android.glestests.glutils.GLBatch;
import com.mobplug.android.glestests.glutils.SimpleGLBatch;

/**
 *
 * @author andreban
 */
public class Md3MeshFrame {
//    private float[] vertexData;
//    private float[] normalData;
//    private float[] texCoordData;
//    private short[] triangleIndexes;

    private GLBatch glBatch;
    public Md3MeshFrame(float[] vertexData, float[] normalData, float[] texCoordData, short[] triangleIndexes) {
//        this.vertexData = vertexData;
//        this.normalData = normalData;
//        this.texCoordData = texCoordData;
//        this.triangleIndexes = triangleIndexes;
        glBatch = new SimpleGLBatch(GL11.GL_TRIANGLES, vertexData, null, normalData, texCoordData, triangleIndexes);
    }
    
//    public float[] getNormalData() {
//        return normalData;
//    }
//
//    public float[] getTexCoordData() {
//        return texCoordData;
//    }
//
//    public short[] getTriangleIndexes() {
//        return triangleIndexes;
//    }
//
//    public float[] getVertexData() {
//        return vertexData;
//    }

    public void render(Map<String, Integer> shaderParams) {
        glBatch.draw(shaderParams);
    }
}
