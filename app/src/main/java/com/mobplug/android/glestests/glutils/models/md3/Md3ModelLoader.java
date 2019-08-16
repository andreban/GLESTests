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

import java.io.DataInputStream;
import java.io.IOException;

import javax.microedition.khronos.opengles.GL11;

import android.content.Context;

import com.mobplug.android.glestests.glutils.GLBatch;
import com.mobplug.android.glestests.glutils.SimpleGLBatch;

import me.bandarra.android.glestests.R;

public class Md3ModelLoader {
    //http://en.wikipedia.org/wiki/MD3_(file_format)
    private static final int MD3_START = 1229213747;
    private static final int MD3_VERSION = 15;
//    private static final int SURFACE_START = 1229213747;
    public GLBatch[] loadModel(Context context) throws IOException {
        
        DataInputStream din = new DataInputStream(context.getResources().openRawResource(R.raw.watercan));      
        
        //Seek beggining of MD3
        int mark = -1;
        do {
            mark = din.readInt();
        } while (mark != MD3_START);
        
        int version = Integer.reverseBytes(din.readInt());
        if (version != MD3_VERSION) {
            //TODO Log error!
        }
        
        String name = readString(din, 64);
        System.out.println(name);
        int flags = Integer.reverseBytes(din.readInt());
        System.out.println(flags);
        int numFrames = Integer.reverseBytes(din.readInt());
        System.out.println("Num frames: " + numFrames);
        int numTags = Integer.reverseBytes(din.readInt());
        System.out.println("Num Tags: " + numTags);
        
        int numSurfaces = Integer.reverseBytes(din.readInt());
        System.out.println("Num Surfaces: " + numSurfaces);
        GLBatch[] surfaces = new GLBatch[numSurfaces];
        int numSkins = Integer.reverseBytes(din.readInt());
        System.out.println("Num Skins: " + numSkins);
        
        int framesOffset = Integer.reverseBytes(din.readInt());
        System.out.println("Frames Offset: " + framesOffset);
        
        int tagsOffset = Integer.reverseBytes(din.readInt());
        System.out.println("Tags Offset: " + tagsOffset);
        
        int surfacesOffset = Integer.reverseBytes(din.readInt());
        System.out.println("Surfaces Offset: " + surfacesOffset);
        
        int eofOffset = Integer.reverseBytes(din.readInt());
        System.out.println("EOF offset: " + eofOffset);
        
        System.out.println("reading frames");
        din.reset();
        din.skip(framesOffset);
        
        for (int i = 0; i < numFrames; i++) {
            float[] minBounds = new float[3];
            readVertex3(din, minBounds);
            System.out.println(String.format("Min Bounds: %f, %f, %f",minBounds[0],minBounds[1], minBounds[2]));

            float[] maxBounds = new float[3];
            readVertex3(din, maxBounds);
            System.out.println(String.format("Max Bounds: %f, %f, %f",maxBounds[0],maxBounds[1], maxBounds[2]));  
            
            float[] localOrigin = new float[3];
            readVertex3(din, localOrigin);
            System.out.println(String.format("localOrigin: %f, %f, %f",localOrigin[0],localOrigin[1], localOrigin[2]));              
            
            float radius = din.readFloat();
            System.out.println(String.format("Radius: %f", radius));
            
            String framename = readString(din, 16);
            System.out.println("Frame name: " + framename);            
        }
        
        din.reset();
        din.skip(tagsOffset);
        for (int i = 0; i < numTags; i++) {
            String tagname = readString(din, 64);
            System.out.println(tagname);
            float[] origin = new float[3];
            readVertex3(din, origin);
            float[][] rotmatrix = new float[3][3];
            for (int j = 0; j < 3; j++) {
                readVertex3(din, rotmatrix[j]);
            }            
        }
        
        din.reset();
        din.skip(surfacesOffset);
        for (int i = 0; i < numSurfaces; i++) {
            System.out.println("Surface " + i);
            
            int ident = din.readInt();//TOOD check ident            
            System.out.println(ident);
            
            String surfacename = readString(din, 64);
            System.out.println(surfacename);
            
            int surfaceflags = Integer.reverseBytes(din.readInt());
            System.out.println("Surface Flags " + surfaceflags);
            
            int numSurfaceFrames = Integer.reverseBytes(din.readInt());
            System.out.println("Surface NUmFrames " + numSurfaceFrames);
            
            int numShaders = Integer.reverseBytes(din.readInt());
            System.out.println("Num Shaders " + numShaders);
            
            int numVerts = Integer.reverseBytes(din.readInt());
            System.out.println("Num Verts " + numVerts);       
            
            int numTriangles = Integer.reverseBytes(din.readInt());
            System.out.println("Num Triangles " + numTriangles);  
            
            int trianglesOffset = Integer.reverseBytes(din.readInt());
            System.out.println("Triangles Offset " + trianglesOffset);
            
            int shadersOffset = Integer.reverseBytes(din.readInt());
            System.out.println("Shader Offset " + shadersOffset);
            
            int texturesOffset = Integer.reverseBytes(din.readInt());
            System.out.println("ST Offset " + texturesOffset);
            
            int xyznOffset = Integer.reverseBytes(din.readInt());
            System.out.println("xyzn offset " + xyznOffset);
            
            int surfaceEndOffset = Integer.reverseBytes(din.readInt());
            System.out.println("surface end offset " + surfaceEndOffset);
            
            din.reset();
            din.skip(surfacesOffset + shadersOffset);
            for (int j = 0; j < numShaders; j++) {
                readShader(din);
            }
            
            din.reset();          
            din.skip(surfacesOffset + trianglesOffset);
            short[] indices = new short[numTriangles * 3];            
            for (int j = 0; j < numTriangles; j++) {
                readTriangle(din, indices, j);
            }            
            
            din.reset();            
            din.skip(surfacesOffset + texturesOffset);
            float[] texCoords = new float[numVerts * 2];
            for (int j = 0; j < numVerts; j++) {
                readTexCoord(din, texCoords, j);
            }

            din.reset();            
            din.skip(surfacesOffset + xyznOffset);
            float[] vertexData = new float[numVerts * 4];
            float[] normalData = new float[numVerts * 3];
            //new SimpleGLBatch
            for (int j = 0; j < numVerts; j++) {
                readVertex(din, vertexData, normalData, j);
            }
            surfaces[i] = new SimpleGLBatch(GL11.GL_TRIANGLES, vertexData, null, normalData, texCoords, indices);
         
        }
        return surfaces;

    } 
    
    private void readShader(DataInputStream file) throws IOException {
        String name = readString(file, 64);
        System.out.println("Shader Name " + name);
        
        int index = Integer.reverseBytes(file.readInt());
        System.out.println("Shader index " + index);
    }
    private String readString(DataInputStream file, int maxsize) throws IOException {
        byte[] bName = new byte[maxsize];                   
        file.read(bName);
        int nameLength = bName.length;
        for (int i = 0; i<  bName.length; i++) {
            if (bName[i] == 0) {
                nameLength = i;
                break;                
            }
        }
        String name = new String(bName, 0, nameLength);
        return name;
    }
    private void readVertex3(DataInputStream file, float[] vertex) throws IOException  {
        vertex[0] = file.readFloat();
        vertex[1] = file.readFloat();
        vertex[2] = file.readFloat();
    }  

    private void readTriangle(DataInputStream din, short[] indexes, int pos) throws IOException {
        indexes[pos * 3] = (short)Integer.reverseBytes(din.readInt());
        indexes[pos * 3 + 1]  = (short)Integer.reverseBytes(din.readInt());
        indexes[pos * 3 + 2]  = (short)Integer.reverseBytes(din.readInt());                                      
    }
    
    private float readFloat(DataInputStream din) throws IOException {
        return Float.intBitsToFloat(Integer.reverseBytes(din.readInt()));
    }
    
    private void readTexCoord(DataInputStream din, float[] texCoords, int pos) throws IOException {
    	texCoords[pos * 2] = readFloat(din);
    	texCoords[pos * 2 + 1] = readFloat(din);    	        
    }

    private void readVertex(DataInputStream din, float[] vertices, float[] vNormals, int pos) throws IOException {
        vertices[pos * 4] = Short.reverseBytes(din.readShort()) * 1.0f/64.0f;
        vertices[pos * 4 + 1] = Short.reverseBytes(din.readShort()) * 1.0f/64.0f;
        vertices[pos * 4 + 2] = Short.reverseBytes(din.readShort()) * 1.0f/64.0f;
        vertices[pos * 4 + 3] = 1.0f;
        
        int encodedNormals = Integer.reverseBytes(din.readUnsignedShort());                
        float lat = (encodedNormals >> 24 & 255) * (2f * (float)Math.PI ) / 255.0f;
        float lng = (encodedNormals >> 16 & 255) * (2f * (float)Math.PI) / 255.0f;
        float x = ((float)Math.cos( lat ) * (float)Math.sin ( lng ));
        float y = ((float)Math.sin ( lat ) * (float)Math.sin ( lng ));
        float z = (float)Math.cos ( lng );
        vNormals[pos * 3] = x;
        vNormals[pos * 3 + 1] = y;
        vNormals[pos * 3 + 2] = z;        
    }
}
