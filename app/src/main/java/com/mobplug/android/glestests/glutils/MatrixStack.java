package com.mobplug.android.glestests.glutils;

import java.nio.FloatBuffer;

/**
 *
 * @author andreban
 */
public class MatrixStack {
	private static final int MATRIX_SIZE = 16;
	private static final int DEFAULT_DEPTH = 64;
	
	private float[][] matrixStack;
	private int topIndex = 0;
    
    //temp matrix. Avoiding garbage collection on operations!
    private float[] temp = new float[16];
    private float[] temp2 = new float[16];
    
    public MatrixStack() {
    	matrixStack = new float[DEFAULT_DEPTH][MATRIX_SIZE];
        Math3D.loadIdentity44(matrixStack[topIndex]);
    }
    
    public MatrixStack(float[] matrix) {
    	matrixStack = new float[DEFAULT_DEPTH][MATRIX_SIZE];  
    	System.arraycopy(matrix, 0, matrixStack[topIndex], 0, MATRIX_SIZE);
    }
    
    public void push() {
        System.arraycopy(matrixStack[topIndex], 0, matrixStack[++topIndex], 0, MATRIX_SIZE);
    }
    
    public void pop() {
        topIndex--;
    }
    
    public void translate(float x, float y, float z) {
        System.arraycopy(matrixStack[topIndex], 0, temp, 0, MATRIX_SIZE);
        Math3D.translationMatrix44f(temp2, x, y, z);
        Math3D.matrixMultiply44(matrixStack[topIndex], temp, temp2);
    }
    
    public void rotate(float angdeg, float x, float y, float z) {
        System.arraycopy(matrixStack[topIndex], 0, temp, 0, MATRIX_SIZE);//save current matrix
        Math3D.rotationMatrix44(temp2, (float)Math.toRadians(angdeg), x, y, z); //calculate rotation
        Math3D.matrixMultiply44(matrixStack[topIndex], temp, temp2); //multiply and put result.
    }
    
    public void multiply3(float[] op) {
        System.arraycopy(matrixStack[topIndex], 0, temp, 0, 16);//save current matrix       
        Math3D.loadIdentity44(temp2);
        for (int i = 0; i < 3; i++) {
            temp2[i * 4] = op[i * 3]; 
            temp2[i * 4 + 1] = op[i * 3 + 1]; 
            temp2[i * 4 + 2] = op[i * 3 + 2]; 
        }
        Math3D.matrixMultiply44(matrixStack[topIndex], temp, temp2);
    }
    
    public void multiply4(float[] op) {
        System.arraycopy(matrixStack[topIndex], 0, temp, 0, 16);//save current matrix                
        Math3D.matrixMultiply44(matrixStack[topIndex], temp, op);        
    }    
    public float[] getMatrix() {
        return matrixStack[topIndex];
    }
    
    public void fillBuffer(FloatBuffer buffer) {
        buffer.position(0);
        buffer.put(matrixStack[topIndex]);
        buffer.flip();
    }
}
