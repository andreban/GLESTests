package com.mobplug.android.glestests.glutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.opengl.GLES20;

/**
 *
 * @author andreban
 */
public class GLBatchFactory {

    private static class Vertex {

        private float[] xyz = new float[3];
        private float[] nrm = new float[3];

        public Vertex(float[] vtx, float[] normal) {
            System.arraycopy(vtx, 0, xyz, 0, 3);
            System.arraycopy(normal, 0, this.nrm, 0, 3);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Vertex other = (Vertex) obj;
            if (!Arrays.equals(this.xyz, other.xyz)) {
                return false;
            }
            if (!Arrays.equals(this.nrm, other.nrm)) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 83 * hash + Arrays.hashCode(this.xyz);
            hash = 83 * hash + Arrays.hashCode(this.nrm);
            return hash;
        }
        
    }

    private static void addTriangle(List<Vertex> vertices, List<Short> indexes, float[][] triangle, float[][] normals) {
        for (int k = 0; k < 3; k++) {
            Vertex v = new Vertex(triangle[k], normals[k]);
            short index = (short) vertices.indexOf(v);
            if (index < 0) {
                vertices.add(v);
                index = (short) (vertices.size() - 1);
            }
            indexes.add(index);
        }
    }

    public static GLBatch makeTorus(float majorRadius, float minorRadius, int numMajor, int numMinor) {
        double majorStep = 2.0f * Math.PI / numMajor;
        double minorStep = 2.0f * Math.PI / numMinor;

        List<Vertex> vertices = new ArrayList<Vertex>();
        List<Short> indexes = new ArrayList<Short>();

        //torusBatch.BeginMesh(numMajor * (numMinor+1) * 6);
        for (int i = 0; i < numMajor; ++i) {
            double a0 = i * majorStep;
            double a1 = a0 + majorStep;
            float x0 = (float) Math.cos(a0);
            float y0 = (float) Math.sin(a0);
            float x1 = (float) Math.cos(a1);
            float y1 = (float) Math.sin(a1);

            float[][] vVertex = new float[4][3];
            float[][] vNormal = new float[4][3];
            float[][] vTexture = new float[4][2];

            for (int j = 0; j <= numMinor; ++j) {
                double b = j * minorStep;
                float c = (float) Math.cos(b);
                float r = minorRadius * c + majorRadius;
                float z = minorRadius * (float) Math.sin(b);

                // First point
                vTexture[0][0] = (float) (i) / (float) (numMajor);
                vTexture[0][1] = (float) (j) / (float) (numMinor);
                vNormal[0][0] = x0 * c;
                vNormal[0][1] = y0 * c;
                vNormal[0][2] = z / minorRadius;
                Math3D.normalizeVector3(vNormal[0]);
                vVertex[0][0] = x0 * r;
                vVertex[0][1] = y0 * r;
                vVertex[0][2] = z;

                // Second point
                vTexture[1][0] = (float) (i + 1) / (float) (numMajor);
                vTexture[1][1] = (float) (j) / (float) (numMinor);
                vNormal[1][0] = x1 * c;
                vNormal[1][1] = y1 * c;
                vNormal[1][2] = z / minorRadius;
                Math3D.normalizeVector3(vNormal[1]);
                vVertex[1][0] = x1 * r;
                vVertex[1][1] = y1 * r;
                vVertex[1][2] = z;

                // Next one over
                b = (j + 1) * minorStep;
                c = (float) Math.cos(b);
                r = minorRadius * c + majorRadius;
                z = minorRadius * (float) Math.sin(b);

                // Third (based on first)
                vTexture[2][0] = (float) (i) / (float) (numMajor);
                vTexture[2][1] = (float) (j + 1) / (float) (numMinor);
                vNormal[2][0] = x0 * c;
                vNormal[2][1] = y0 * c;
                vNormal[2][2] = z / minorRadius;
                Math3D.normalizeVector3(vNormal[2]);
                vVertex[2][0] = x0 * r;
                vVertex[2][1] = y0 * r;
                vVertex[2][2] = z;

                // Fourth (based on second)
                vTexture[3][0] = (float) (i + 1) / (float) (numMajor);
                vTexture[3][1] = (float) (j + 1) / (float) (numMinor);
                vNormal[3][0] = x1 * c;
                vNormal[3][1] = y1 * c;
                vNormal[3][2] = z / minorRadius;
                Math3D.normalizeVector3(vNormal[3]);
                vVertex[3][0] = x1 * r;
                vVertex[3][1] = y1 * r;
                vVertex[3][2] = z;

                addTriangle(vertices, indexes, vVertex, vNormal);


                // Rearrange for next triangle
                System.arraycopy(vVertex[1], 0, vVertex[0], 0, 3);
                System.arraycopy(vNormal[1], 0, vNormal[0], 0, 3);
                System.arraycopy(vTexture[1], 0, vTexture[0], 0, 2);

                System.arraycopy(vVertex[3], 0, vVertex[1], 0, 3);
                System.arraycopy(vNormal[3], 0, vNormal[1], 0, 3);
                System.arraycopy(vTexture[3], 0, vTexture[1], 0, 2);

                addTriangle(vertices, indexes, vVertex, vNormal);
            }
        }

        float[] fv = new float[vertices.size() * 4];
        for (int i = 0; i < vertices.size(); i++) {
            fv[i << 2] = vertices.get(i).xyz[0];
            fv[(i << 2) + 1] = vertices.get(i).xyz[1];
            fv[(i << 2) + 2] = vertices.get(i).xyz[2];
            fv[(i << 2) + 3] = 1.0f;
        }
        
        float[] fn = new float[vertices.size() * 3];
        for (int i = 0; i < vertices.size(); i++) {
            fn[i * 3 + 0] = vertices.get(i).nrm[0];
            fn[i * 3 + 1] = vertices.get(i).nrm[1];
            fn[i * 3 + 2] = vertices.get(i).nrm[2];            
        }

        short[] idx = new short[indexes.size()];
        for (int i = 0; i < indexes.size(); i++) {
            idx[i] = indexes.get(i);
        }
        return new SimpleGLBatch(GLES20.GL_TRIANGLES, fv, null, fn, null, idx);
    }

    public static GLBatch makeSphere(float radius, float slices, float stacks) {
        return null;
    }

    public static GLBatch makeDisk(float innerRadius, float outerRadius, int slices, int stacks) {
        return null;
    }

    public static GLBatch makeCylinder(float baseRadius, float topRadius, float length, int numSlices, int numStacks) {
        return null;
    }

    public static GLBatch makeCube(float width, float height, float depth) {
        float xmax = width / 2;
        float xmin = -xmax;
        float ymax = height / 2;
        float ymin = -ymax;
        float zmax = depth / 2;
        float zmin = -zmax;

        float[] vertex = new float[]{
            // front face
            xmin, ymax, zmin, 1.0f,//0 - 0
            xmin, ymin, zmin, 1.0f,//1 - 1
            xmax, ymin, zmin, 1.0f,//2 - 2
            xmax, ymax, zmin, 1.0f,//3 - 3 
            
            //right face
            xmax, ymax, zmin, 1.0f,//3 - 4             
            xmax, ymin, zmin, 1.0f,//2 - 5           
            xmax, ymin, zmax, 1.0f,//6 - 6           
            xmax, ymax, zmax, 1.0f,//7 - 7                                       
            
            //back face
            xmin, ymax, zmax, 1.0f,//4 - 8
            xmin, ymin, zmax, 1.0f,//5 - 9
            xmax, ymin, zmax, 1.0f,//6 - 10
            xmax, ymax, zmax, 1.0f,//7 - 11  
            
            //left face
            xmin, ymax, zmin, 1.0f,//0 - 12
            xmin, ymin, zmin, 1.0f,//1 - 13
            xmin, ymax, zmax, 1.0f,//4 - 14
            xmin, ymin, zmax, 1.0f,//5 - 15  
            
            //top
            xmin, ymax, zmin, 1.0f,//0 - 16  
            xmax, ymax, zmin, 1.0f,//3 - 17 
            xmin, ymax, zmax, 1.0f,//4 - 18     
            xmax, ymax, zmax, 1.0f,//7 - 19    
            
            //bottom
            xmin, ymin, zmin, 1.0f,//1 - 20
            xmax, ymin, zmin, 1.0f,//2 - 21 
            xmin, ymin, zmax, 1.0f,//5 - 22
            xmax, ymin, zmax, 1.0f,//6 - 23           
            
        };

        float[] textures = new float[] {
            0, 1,
            0, 0,
            1, 1,
            1, 1,
            
            0, 1,
            0, 0,
            1, 1,
            1, 1,
            
            0, 1,
            0, 0,
            1, 1,
            1, 1,
            
            0, 1,
            0, 0,
            1, 1,
            1, 1,
            
            0, 1,
            0, 0,
            1, 1,
            1, 1,
            
            0, 1,
            0, 0,
            1, 1,
            1, 1,                                                   
        };
        float[] normals = new float[] {
            0, 0, -1,
            0, 0, -1,
            0, 0, -1,
            0, 0, -1,
            
            1, 0, 0,
            1, 0, 0,
            1, 0, 0,
            1, 0, 0,
            
            0, 0, 1,
            0, 0, 1,
            0, 0, 1,
            0, 0, 1,
            
            -1, 0, 0,
            -1, 0, 0,
            -1, 0, 0,
            -1, 0, 0,
            
            0, 1, 0,
            0, 1, 0,
            0, 1, 0,
            0, 1, 0,
            
            0, -1, 0,
            0, -1, 0,
            0, -1, 0,
            0, -1, 0                
        };
        
        short[] index = new short[]{
            //front face
            0, 1, 2,
            0, 2, 3,
            //right face
            4, 5, 6,
            4, 6, 7,
            //back face
            11, 10, 9,
            11, 9, 8,
            //left face
            14, 15, 13,
            14, 13, 12,
            //top face
            18, 16, 17,
            18, 17, 19,
            //bottom face
            22, 23, 21,
            22, 21, 20
        };

        return new SimpleGLBatch(GLES20.GL_TRIANGLES, vertex, null, normals, textures, index);
    }
}
