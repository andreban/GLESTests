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

import java.util.HashMap;
import java.util.Map;

import android.opengl.GLES20;
import android.util.Log;

/**
 *
 * @author andreban
 */
public class GLShader {
	private static final String TAG = "GLShader";
    private Map<String, Integer> attributeLocations = new HashMap<String, Integer>();
    private Map<String, Integer> uniformLocations = new HashMap<String, Integer>();
    
    private int program = -1;
    private int vertexShader = -1;
    private int fragmentShader = -1;
        
    public GLShader(String vertexShaderSource, String fragmentShaderSource) {
        //Vertex shader
        vertexShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(vertexShader, vertexShaderSource);
        GLES20.glCompileShader(vertexShader);
 
        String vertexShaderErrorLog = GLES20.glGetShaderInfoLog(vertexShader);
        if (vertexShaderErrorLog.length() != 0) {
            Log.e(TAG, "Vertex shader compile log: \n" + vertexShaderErrorLog);
        }
  
        //Fragment shader
        fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(fragmentShader, fragmentShaderSource);
        GLES20.glCompileShader(fragmentShader);
 
        String fragmentShaderErrorLog = GLES20.glGetShaderInfoLog(fragmentShader);
        if (fragmentShaderErrorLog.length() != 0) {
            Log.e(TAG, "Fragment shader compile log: \n" + fragmentShaderErrorLog);
        }
 
        //Shader program
        program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);
            
        GLES20.glLinkProgram(program);
        String log = GLES20.glGetProgramInfoLog(program);
        if (log.length() != 0) {
            Log.e(TAG, "Program link log:\n" + log);
        }

        int[] params = new int[2];        
        int[] length = new int[1];
        int[] size = new int[1];
        int[] type = new int[1];           

        GLES20.glGetProgramiv(program, GLES20.GL_ACTIVE_ATTRIBUTES, params, 0);
        GLES20.glGetProgramiv(program, GLES20.GL_ACTIVE_ATTRIBUTE_MAX_LENGTH, params, 1);
        int numAttributes = params[0];
        int maxAttributeLength = params[1];
        byte[] namebytes = new byte[maxAttributeLength];
        
        for (int i = 0; i < numAttributes; i++) {
        	GLES20.glGetActiveAttrib(program, i, maxAttributeLength, length, 0, size, 0, type, 0, namebytes, 0);
            Log.i(TAG, length[0] + ": " + size[0] + ": " + type[0]);        	
            String name = new String(namebytes, 0, length[0]);
            int location = GLES20.glGetAttribLocation(program, name);
            Log.i(TAG, name + ":" + location);
            attributeLocations.put(name, location);
        }
                 
        GLES20.glGetProgramiv(program, GLES20.GL_ACTIVE_UNIFORMS, params, 0);
        GLES20.glGetProgramiv(program, GLES20.GL_ACTIVE_UNIFORM_MAX_LENGTH, params, 1);

        int numUniforms = params[0];
        int maxUniformLength = params[1];
        namebytes = new byte[maxUniformLength];        
        for (int i = 0; i < numUniforms; i++) {
            GLES20.glGetActiveUniform(program, i, maxUniformLength, length, 0, size, 0, type, 0, namebytes, 0);
            Log.i(TAG, length[0] + ": " + size[0] + ": " + type[0]);
            String name = new String(namebytes, 0, length[0]);            
            int location = GLES20.glGetUniformLocation(program, name);
            uniformLocations.put(name, location);
            Log.i(TAG, name + ":" + location);            
        }                      
    }
    
    public Map<String, Integer> getAttributeLocations() {
        return attributeLocations;
    }
    
    public Map<String, Integer> getUniformLocations() {
        return uniformLocations;
    }
    
    public void setUniformMatrix4(String uniformName, boolean traverse, float[] matrixdata) {
        int location = getUniformLocation(uniformName);     
        GLES20.glUniformMatrix4fv(location, 1, traverse, matrixdata, 0);        
    }
    
	public void setUniformMatrix3(String uniformName, boolean traverse, float[] matrixdata) {
        int location = getUniformLocation(uniformName);     
        GLES20.glUniformMatrix3fv(location, 1, traverse, matrixdata, 0);
		
	}    
    public void setUniform3(String uniformName, float v1, float v2, float v3) {
        int location = getUniformLocation(uniformName);
        GLES20.glUniform3f(location, v1, v2, v3);
    }
    public void setUniform4(String uniformName, float v1, float v2, float v3, float v4) {
    	int location = getUniformLocation(uniformName);
    	GLES20.glUniform4f(location, v1, v2, v3, v4);
    }
    
    public void setUniform1i(String uniformName, int value) {
    	int location = getUniformLocation(uniformName);
    	GLES20.glUniform1i(location, value);
    }
    
    public int getUniformLocation(String uniformName) {
    	if (uniformLocations.containsKey(uniformName))
    		return uniformLocations.get(uniformName);
    	int location = GLES20.glGetUniformLocation(program, uniformName);
    	if (location >= 0) uniformLocations.put(uniformName, location);
    	return location;
    	
    }
    public void useShader() {
        //Enable shader
        GLES20.glUseProgram(program);        
    }
      
    public int getProgram() {
        return program;
    }
    
    private void delete() {
    	GLES20.glDeleteProgram(program);
    	GLES20.glDeleteShader(vertexShader);
    	GLES20.glDeleteShader(fragmentShader);    	
    }
    
    @Override
    protected void finalize() throws Throwable {
    	try {
    		delete();
    	} catch(RuntimeException ex) {
    		Log.e(TAG, "Error deleting shaders", ex);
    	}
    	super.finalize();
    }
}
