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

package com.mobplug.android.glestests.tunnels1;

import static android.opengl.GLES20.GL_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_ELEMENT_ARRAY_BUFFER;
import static android.opengl.GLES20.glBindBuffer;
import static android.opengl.GLES20.glBufferData;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Random;

import android.opengl.GLES20;

import com.mobplug.android.glestests.glutils.BufferUtils;
import com.mobplug.android.glestests.glutils.GLBatch;
import com.mobplug.android.glestests.glutils.GLShader;
import com.mobplug.android.glestests.glutils.Math3D;
import com.mobplug.android.glestests.glutils.VBOGLBatch;

/**
 *
 * @author andreban
 */
public class Map {

    private int numSections = 1000;
    private GLBatch[][] sections = new GLBatch[numSections][4];
    
    float[] normal = new float[3];    
    float[] v0 = new float[3];  
    float[] v1 = new float[3]; 
    float[] v2 = new float[3];    
    float[] u = new float[3];
    float[] v = new float[3];    
    private void fillNormals(float[] vertices, float[] normals, short[] indices, boolean ble) {            
            for (int j = 0; j < indices.length; j += 3) {
                v0[0] = vertices[indices[j + 0] * 4];
                v0[1] = vertices[indices[j + 0] * 4 + 1];
                v0[2] = vertices[indices[j + 0] * 4 + 2];

                v1[0] = vertices[indices[j + 1] * 4];
                v1[1] = vertices[indices[j + 1] * 4 + 1];
                v1[2] = vertices[indices[j + 1] * 4 + 2];

                v2[0] = vertices[indices[j + 2] * 4];
                v2[1] = vertices[indices[j + 2] * 4 + 1];
                v2[2] = vertices[indices[j + 2] * 4 + 2];

                u[0] = v1[0] - v0[0];
                v[0] = v2[0] - v0[0];
                u[1] = v1[1] - v0[1];
                v[1] = v2[1] - v0[1];
                u[2] = v1[2] - v0[2];
                v[2] = v2[2] - v0[2];

                if (ble)
                    Math3D.crossProduct3(normal, u, v);
                else
                    Math3D.crossProduct3(normal, v, u);                    

                Math3D.normalizeVector3(normal);
                
                System.arraycopy(normal, 0, normals, indices[j + 0] * 3, 3);
                System.arraycopy(normal, 0, normals, indices[j + 1] * 3, 3);                
                System.arraycopy(normal, 0, normals, indices[j + 2] * 3, 3);                                
            }        
    }
    private int[] genBuf = new int[1];    
    private int glGenBuffers() {
    	GLES20.glGenBuffers(1, genBuf, 0);
    	return genBuf[0];
    }
    public Map() {
        float x1array[] = new float[numSections];
        float x2array[] = new float[numSections];
        float[] vertices = new float[16];
        float[] normals = new float[12];
        
        int vertexBufferid = -1;
        int normalBufferId = -1;
        int indexBufferId = -1;
        
        FloatBuffer vertexData = BufferUtils.createFloatBuffer(16);
        FloatBuffer normalData = BufferUtils.createFloatBuffer(12);
        ShortBuffer indexData = BufferUtils.createShortBuffer(6);
        
        short[] indices = {
            0, 1, 2,
            2, 3, 0
        };        

        indexBufferId = glGenBuffers();
        indexData.put(indices);
        indexData.flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBufferId);        
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices.length * 2, indexData, GLES20.GL_STATIC_DRAW);        
        
        Random random = new Random();
        for (int i = 0; i < numSections; i++) {
            x1array[i] = (float)random.nextFloat() * -2 - 0.5f;
            x2array[i] = x1array[i] + 3f;            
        }
        for (int i = 0; i < numSections - 1; i++) {
            int z1 = -i;
            int z2 = -i - 1;
            float l1 = x1array[i];
            float l2 = x1array[i + 1];
            float r1 = x2array[i];
            float r2 = x2array[i + 1];
//            float x1 = (float)Math.random() * 5;
//            float x2 = x1 + (float)Math.random() * 2 + 1.0f;

            //left
            vertices[0] = l1;
            vertices[1] = 1;
            vertices[2] = z1;
            vertices[3] = 1;
            
            vertices[4] = l1; 
            vertices[5] = -1;
            vertices[6] = z1;
            vertices[7] = 1;
            
            vertices[8] = l2;
            vertices[9] = -1;
            vertices[10] = z2;
            vertices[11] = 1;
            
            vertices[12] = l2; 
            vertices[13] = 1;
            vertices[14] = z2;
            vertices[15] = 1;


            fillNormals(vertices, normals, indices, true);

            normalBufferId = glGenBuffers();            
            normalData.position(0);
            normalData.put(normals);
            normalData.flip();
            glBindBuffer(GL_ARRAY_BUFFER, normalBufferId);        
            glBufferData(GLES20.GL_ARRAY_BUFFER, normals.length * 4, normalData, GLES20.GL_STATIC_DRAW);
//            
            vertexBufferid = glGenBuffers();            
            vertexData.position(0);
            vertexData.put(vertices);
            vertexData.flip();
            glBindBuffer(GL_ARRAY_BUFFER, vertexBufferid);
            glBufferData(GLES20.GL_ARRAY_BUFFER, vertices.length * 4, vertexData, GLES20.GL_STATIC_DRAW);            
//                                    
            sections[i][0] = new VBOGLBatch(GLES20.GL_TRIANGLES, 6, vertexBufferid, -1, normalBufferId, -1, indexBufferId);

            vertices[0] = l1;
            vertices[1] = -1;
            vertices[2] = z1;
            vertices[3] = 1;
            
            vertices[4] = r1; 
            vertices[5] = -1;
            vertices[6] = z1;
            vertices[7] = 1;
            
            vertices[8] = r2;
            vertices[9] = -1;
            vertices[10] = z2;
            vertices[11] = 1;
            
            vertices[12] = l2; 
            vertices[13] = -1;
            vertices[14] = z2;
            vertices[15] = 1;            

            fillNormals(vertices, normals, indices, true);
            
            normalBufferId = glGenBuffers();            
            normalData.position(0);
            normalData.put(normals);
            normalData.flip();
            glBindBuffer(GL_ARRAY_BUFFER, normalBufferId);        
            glBufferData(GLES20.GL_ARRAY_BUFFER, normals.length * 4, normalData, GLES20.GL_STATIC_DRAW);
//            
            vertexBufferid = glGenBuffers();            
            vertexData.position(0);
            vertexData.put(vertices);
            vertexData.flip();
            glBindBuffer(GL_ARRAY_BUFFER, vertexBufferid);
            glBufferData(GLES20.GL_ARRAY_BUFFER, vertices.length * 4, vertexData, GLES20.GL_STATIC_DRAW);
            
            sections[i][1] = new VBOGLBatch(GLES20.GL_TRIANGLES, 6, vertexBufferid, -1, normalBufferId, -1, indexBufferId);            

            vertices[0] = r1;
            vertices[1] = 1;
            vertices[2] = z1;
            vertices[3] = 1;
            
            vertices[4] = r1; 
            vertices[5] = -1;
            vertices[6] = z1;
            vertices[7] = 1;
            
            vertices[8] = r2;
            vertices[9] = -1;
            vertices[10] = z2;
            vertices[11] = 1;
            
            vertices[12] = r2; 
            vertices[13] = 1;
            vertices[14] = z2;
            vertices[15] = 1;              
            fillNormals(vertices, normals, indices, false);
            
            normalBufferId = glGenBuffers();            
            normalData.position(0);
            normalData.put(normals);
            normalData.flip();
            glBindBuffer(GL_ARRAY_BUFFER, normalBufferId);        
            glBufferData(GLES20.GL_ARRAY_BUFFER, normals.length * 4, normalData, GLES20.GL_STATIC_DRAW);
//            
            vertexBufferid = glGenBuffers();            
            vertexData.position(0);
            vertexData.put(vertices);
            vertexData.flip();
            glBindBuffer(GL_ARRAY_BUFFER, vertexBufferid);
            glBufferData(GLES20.GL_ARRAY_BUFFER, vertices.length * 4, vertexData, GLES20.GL_STATIC_DRAW);
            sections[i][2] = new VBOGLBatch(GLES20.GL_TRIANGLES, 6, vertexBufferid, -1, normalBufferId, -1, indexBufferId);            
            
            vertices[0] = l1;
            vertices[1] = 1;
            vertices[2] = z1;
            vertices[3] = 1;
            
            vertices[4] = r1; 
            vertices[5] = 1;
            vertices[6] = z1;
            vertices[7] = 1;
            
            vertices[8] = r2;
            vertices[9] = 1;
            vertices[10] = z2;
            vertices[11] = 1;
            
            vertices[12] = l2; 
            vertices[13] = 1;
            vertices[14] = z2;
            vertices[15] = 1;              
            fillNormals(vertices, normals, indices, false);
            normalBufferId = glGenBuffers();            
            normalData.position(0);
            normalData.put(normals);
            normalData.flip();
            glBindBuffer(GL_ARRAY_BUFFER, normalBufferId);        
            glBufferData(GLES20.GL_ARRAY_BUFFER, normals.length * 4, normalData, GLES20.GL_STATIC_DRAW);
//            
            vertexBufferid = glGenBuffers();            
            vertexData.position(0);
            vertexData.put(vertices);
            vertexData.flip();
            glBindBuffer(GL_ARRAY_BUFFER, vertexBufferid);
            glBufferData(GLES20.GL_ARRAY_BUFFER, vertices.length * 4, vertexData, GLES20.GL_STATIC_DRAW);            
            sections[i][3] = new VBOGLBatch(GLES20.GL_TRIANGLES, 6, vertexBufferid, -1, normalBufferId, -1, indexBufferId);            
        }
    }

    public void render(GLShader shader, float pos) {
    	int start = (int)pos;
    	int end = start + 100;
    	if (end > numSections - 1) end = numSections - 1;
        for (int i = start; i < end; i++) {
            sections[i][0].draw(shader.getAttributeLocations());
            sections[i][1].draw(shader.getAttributeLocations());
            sections[i][2].draw(shader.getAttributeLocations());
            sections[i][3].draw(shader.getAttributeLocations());
        }
    }
}