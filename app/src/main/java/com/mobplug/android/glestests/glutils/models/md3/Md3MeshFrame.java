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
