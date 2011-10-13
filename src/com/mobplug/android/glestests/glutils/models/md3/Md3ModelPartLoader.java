package com.mobplug.android.glestests.glutils.models.md3;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author andreban
 */
public class Md3ModelPartLoader {
    //http://en.wikipedia.org/wiki/MD3_(file_format)
    private static final int MD3_START = 1229213747;
    private static final int MD3_VERSION = 15;
    private static final int SURFACE_START = 1229213747;
    private static final float SCALE = 1.0f/64.0f;
    public Md3ModelPart loadModel(InputStream in, boolean invertYZ) throws IOException {
        Md3ModelPart modelPart = new Md3ModelPart();   
        DataInputStream din = new DataInputStream(in);      
        
        //Seek beggining of MD3
        int mark = -1;
        do {
            mark = din.readInt();
        } while (mark != MD3_START);
        
        int version = Integer.reverseBytes(din.readInt());
        if (version != MD3_VERSION) {
            throw new Md3Exception(String.format("Invalid MD3 Version. Found %d, Expected %d", version, MD3_VERSION));
        }
        modelPart.setVersion(version);
        
        String name = readString(din, 64);
        modelPart.setName(name);
        
        int flags = Integer.reverseBytes(din.readInt());
        modelPart.setFlags(flags)
                ;
        int numFrames = Integer.reverseBytes(din.readInt());
        modelPart.setNumFrames(numFrames);
        
        int numTags = Integer.reverseBytes(din.readInt());//tagNum
        modelPart.setNumTags(numTags);        
        
        int numMeshes = Integer.reverseBytes(din.readInt());//meshNum
        modelPart.setNumMeshes(numMeshes);
                        
        int numSkins = Integer.reverseBytes(din.readInt());//maxSkinNum
        modelPart.setNumSkins(numSkins);
        
        //file control vars
        int framesOffset = Integer.reverseBytes(din.readInt());        
        int tagsOffset = Integer.reverseBytes(din.readInt());        
        int meshesOffset = Integer.reverseBytes(din.readInt());        
        int eofOffset = Integer.reverseBytes(din.readInt());
        
        din.reset();
        din.skip(framesOffset);        

        for (int i = 0; i < numFrames; i++) {
            Md3Frame frame = new Md3Frame();
            
            float[] minBounds = new float[3];
            readVertex3(din, minBounds, invertYZ);
            frame.setV3MinBounds(minBounds);

            float[] maxBounds = new float[3];
            readVertex3(din, maxBounds, invertYZ);
            frame.setV3MaxBounds(maxBounds);
            
            float[] localOrigin = new float[3];
            readVertex3(din, localOrigin, invertYZ);
            frame.setV3LocalOrigin(localOrigin);
            
            float radius = readFloat(din);
            frame.setRadius(radius);
            
            String framename = readString(din, 16);
            frame.setName(framename);
            
            frame.setNum(i);
            modelPart.addFrame(frame);
        }
        
        din.reset();
        din.skip(tagsOffset);
        for (int i = 0; i < numTags; i++) {
            Md3Tag tag = new Md3Tag();
            String tagname = readString(din, 64);
            System.out.println(tagname);
            tag.setName(tagname);

            float[] origin = new float[3];
            readVertex3(din, origin, invertYZ);
            tag.setV3Origin(origin);
            
            float[] rotmatrix = new float[9];
            for (int j = 0; j < 3; j++) {
                readVertex3(din, rotmatrix, j, invertYZ);
            }   
            tag.setM33Rotation(rotmatrix);
            
            modelPart.addTag(tag);
        }
        
        din.reset();
        din.skip(meshesOffset);
        for (int i = 0; i < numMeshes; i++) {
            Md3Mesh mesh = new Md3Mesh();
            
            int ident = din.readInt();
            if (ident != SURFACE_START) {
            throw new Md3Exception(String.format("Invalid Surface ID Version. Found %d, Expected %d", ident, SURFACE_START));
            }
            
            String meshName = readString(din, 64);
            mesh.setName(meshName);

            int meshFlags = Integer.reverseBytes(din.readInt());
            mesh.setFlags(meshFlags);            
            
            int numMeshFrames = Integer.reverseBytes(din.readInt());
            mesh.setNumFrames(numMeshFrames);
            
            int numShaders = Integer.reverseBytes(din.readInt());
            mesh.setNumShaders(numShaders);
            
            int numVerts = Integer.reverseBytes(din.readInt());
            mesh.setNumVertices(numVerts);
            
            int numTriangles = Integer.reverseBytes(din.readInt());
            mesh.setNumTriangles(numTriangles);
            
            //file control vars
            int trianglesOffset = Integer.reverseBytes(din.readInt());            
            int shadersOffset = Integer.reverseBytes(din.readInt());            
            int texturesOffset = Integer.reverseBytes(din.readInt());            
            int xyznOffset = Integer.reverseBytes(din.readInt());                        
            int surfaceEndOffset = Integer.reverseBytes(din.readInt());
            
            din.reset();
            din.skip(meshesOffset + shadersOffset);
            for (int j = 0; j < numShaders; j++) {
                readShader(din);
            }
                        
            din.reset();
            din.skip(meshesOffset + trianglesOffset);
            short[] indices = new short[numTriangles * 3];            
            for (int j = 0; j < numTriangles; j++) {
                readTriangle(din, indices, j);
            }            
            
            float[] texCoordData = new float[numVerts * 2];
            din.reset();
            din.skip(meshesOffset + texturesOffset);
            for (int j = 0; j < numVerts; j++) {
                readTexCoord(din, texCoordData, j);
            }
            
            din.reset();
            din.skip(meshesOffset + xyznOffset);
            float[] vertexData = new float[numVerts * 4];
            float[] normalData = new float[numVerts * 3];
            
            for (int k = 0; k < numMeshFrames; k++) {

                for (int j = 0; j < numVerts; j++) {
                    readVertex(din, vertexData, normalData, j, invertYZ);
                }
                Md3MeshFrame frame = new Md3MeshFrame(vertexData, normalData, texCoordData, indices);                
                mesh.addFrame(frame);
            }
            modelPart.addMesh(mesh);         
        }
        return modelPart;
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
    
    private void readVertex3(DataInputStream file, float[] vertex, int num, boolean invertYZ) throws IOException {
        if (invertYZ) {
            vertex[num * 3] = readFloat(file);
            vertex[num * 3 + 2] = readFloat(file);
            vertex[num * 3 + 1] = readFloat(file);        
        } else {
            vertex[num * 3] = readFloat(file);
            vertex[num * 3 + 1] = readFloat(file);
            vertex[num * 3 + 2] = readFloat(file);                    
        }
    }
    
    private void readVertex3(DataInputStream file, float[] vertex, boolean invertYZ) throws IOException  {
        readVertex3(file, vertex, 0, invertYZ);
    }
      
    private void readTriangle(DataInputStream din, short[] indexes, int pos) throws IOException {
        indexes[pos * 3] = (short)Integer.reverseBytes(din.readInt());
        indexes[pos * 3 + 1]  = (short)Integer.reverseBytes(din.readInt());
        indexes[pos * 3 + 2]  = (short)Integer.reverseBytes(din.readInt());                                   
    }


    public int readInt(DataInputStream din) throws IOException {
        int[] res = new int[4];
        for (int i = 3; i >= 0; i--) {
            res[i] = din.read();
        }

        return ((res[0] & 0xff) << 24)
                | ((res[1] & 0xff) << 16)
                | ((res[2] & 0xff) << 8)
                | (res[3] & 0xff);
    }
    
    public float readFloat(DataInputStream din) throws IOException {
        return Float.intBitsToFloat(readInt(din));
    }    
    
    private void readTexCoord(DataInputStream din, float[] texCoordData, int j) throws IOException {
        texCoordData[j * 2] = readFloat(din);//din.readFloat();
        texCoordData[j * 2 + 1] = readFloat(din);//din.readFloat();
    }

    private void readVertex(DataInputStream din, float[] vertices, float[] vNormals, int pos, boolean invertXY) throws IOException {
        if (invertXY) {
            vertices[pos * 4] = Short.reverseBytes(din.readShort()) * SCALE;
            vertices[pos * 4 + 2] = Short.reverseBytes(din.readShort()) * SCALE;            
            vertices[pos * 4 + 1] = Short.reverseBytes(din.readShort()) * SCALE;            
            vertices[pos * 4 + 3] = 1.0f;
        } else {
            vertices[pos * 4] = Short.reverseBytes(din.readShort()) * SCALE;
            vertices[pos * 4 + 1] = Short.reverseBytes(din.readShort()) * SCALE;            
            vertices[pos * 4 + 2] = Short.reverseBytes(din.readShort()) * SCALE;            
            vertices[pos * 4 + 3] = 1.0f;            
        }
        
        int encodedNormals = Integer.reverseBytes(din.readUnsignedShort());                
        float lat = (encodedNormals >> 24 & 255) * (2f * (float)Math.PI ) / 255.0f;
        float lng = (encodedNormals >> 16 & 255) * (2f * (float)Math.PI) / 255.0f;
        float x = ((float)Math.cos( lat ) * (float)Math.sin ( lng ));
        float y = ((float)Math.sin ( lat ) * (float)Math.sin ( lng ));
        float z = (float)Math.cos ( lng );
        
        if (invertXY) {        
            vNormals[pos * 3] = x;
            vNormals[pos * 3 + 1] = z;            
            vNormals[pos * 3 + 2] = y;        
        } else {
            vNormals[pos * 3] = x;
            vNormals[pos * 3 + 1] = y;            
            vNormals[pos * 3 + 2] = z;                    
        }
    }
}
