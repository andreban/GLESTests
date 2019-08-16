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
