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

public class GeometryTransform {
    private MatrixStack modelViewStack;
    private MatrixStack projectionStack;

    private float[] mvpMatrix = new float[16];
    private float[] normalMatrix = new float[9];
    
    public GeometryTransform(MatrixStack modelViewStack, MatrixStack projectionStack) {
        this.modelViewStack = modelViewStack;
        this.projectionStack = projectionStack;
    }

    public MatrixStack getModelViewStack() {
        return modelViewStack;
    }

    public void setModelViewStack(MatrixStack modelViewStack) {
        this.modelViewStack = modelViewStack;
    }

    public MatrixStack getProjectionStack() {
        return projectionStack;
    }

    public void setProjectionStack(MatrixStack projectionStack) {
        this.projectionStack = projectionStack;
    }
    
    public float[] getModelViewProjectionMatrix() {
        Math3D.matrixMultiply44(mvpMatrix, getProjectionMatrix(), getModelViewMatrix());        
        return mvpMatrix;
    }
    
    public float[] getModelViewMatrix() {
        return getModelViewStack().getMatrix();
    }
    
    public float[] getProjectionMatrix() {
        return getProjectionStack().getMatrix();
    }
    
    public float[] getNormalMatrix(boolean normalize) {
        Math3D.extractRotationMatrix33(normalMatrix, getModelViewMatrix());
        if (normalize) {
            Math3D.normalizeVector3(normalMatrix, 0);
            Math3D.normalizeVector3(normalMatrix, 3);            
            Math3D.normalizeVector3(normalMatrix, 6);            
        }
        return normalMatrix;        
    }
    
    public float[] getNormalMatrix() {
        return getNormalMatrix(false);
    }
}
