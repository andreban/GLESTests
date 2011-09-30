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
       private float[] txt = new float[2];

       public Vertex(float[] vtx, float[] normal, float[] txtcoords) {
           System.arraycopy(vtx, 0, xyz, 0, 3);
           System.arraycopy(normal, 0, this.nrm, 0, 3);
           System.arraycopy(txtcoords, 0, this.txt, 0, 2);
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
           if (!Arrays.equals(this.txt, other.txt)) {
               return false;
           }
           return true;
       }

       @Override
       public int hashCode() {
           int hash = 7;
           hash = 83 * hash + Arrays.hashCode(this.xyz);
           hash = 83 * hash + Arrays.hashCode(this.nrm);
           hash = 83 * hash + Arrays.hashCode(this.txt);
           return hash;
       }
   }

   private static void addTriangle(List<Vertex> vertices, List<Short> indexes, float[][] triangle, float[][] normals, float[][] textures) {
       for (int k = 0; k < 3; k++) {
           Vertex v = new Vertex(triangle[k], normals[k], textures[k]);
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

               addTriangle(vertices, indexes, vVertex, vNormal, vTexture);


               // Rearrange for next triangle
               System.arraycopy(vVertex[1], 0, vVertex[0], 0, 3);
               System.arraycopy(vNormal[1], 0, vNormal[0], 0, 3);
               System.arraycopy(vTexture[1], 0, vTexture[0], 0, 2);

               System.arraycopy(vVertex[3], 0, vVertex[1], 0, 3);
               System.arraycopy(vNormal[3], 0, vNormal[1], 0, 3);
               System.arraycopy(vTexture[3], 0, vTexture[1], 0, 2);

               addTriangle(vertices, indexes, vVertex, vNormal, vTexture);
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

       float[] ft = new float[vertices.size() * 2];
       for (int i = 0; i < vertices.size(); i++) {
           ft[i * 2 + 0] = vertices.get(i).txt[0];
           ft[i * 2 + 1] = vertices.get(i).txt[1];
       }

       short[] idx = new short[indexes.size()];
       for (int i = 0; i < indexes.size(); i++) {
           idx[i] = indexes.get(i);
       }
       return new SimpleGLBatch(GLES20.GL_TRIANGLES, fv, null, fn, ft, idx);
   }

   public static GLBatch makeSphere(float fRadius, float iSlices, float iStacks) {
       float drho = (float) (3.141592653589) / (float) iStacks;
       float dtheta = 2.0f * (float) (3.141592653589) / (float) iSlices;
       float ds = 1.0f / (float) iSlices;
       float dt = 1.0f / (float) iStacks;
       float t = 1.0f;
       float s = 0.0f;
       int i, j;     // Looping variables

       List<Vertex> vertices = new ArrayList<Vertex>();
       List<Short> indexes = new ArrayList<Short>();
       //sphereBatch.BeginMesh(iSlices * iStacks * 6);
       for (i = 0; i < iStacks; i++) {
           float rho = (float) i * drho;
           float srho = (float) (Math.sin(rho));
           float crho = (float) (Math.cos(rho));
           float srhodrho = (float) (Math.sin(rho + drho));
           float crhodrho = (float) (Math.cos(rho + drho));

           // Many sources of OpenGL sphere drawing code uses a triangle fan
           // for the caps of the sphere. This however introduces texturing 
           // artifacts at the poles on some OpenGL implementations
           s = 0.0f;
           float[][] vVertex = new float[4][3];
           float[][] vNormal = new float[4][3];
           float[][] vTexture = new float[4][2];

           for (j = 0; j < iSlices; j++) {
               float theta = (j == iSlices) ? 0.0f : j * dtheta;
               float stheta = (float) (-Math.sin(theta));
               float ctheta = (float) (Math.cos(theta));

               float x = stheta * srho;
               float y = ctheta * srho;
               float z = crho;

               vTexture[0][0] = s;
               vTexture[0][1] = t;
               vNormal[0][0] = x;
               vNormal[0][1] = y;
               vNormal[0][2] = z;
               vVertex[0][0] = x * fRadius;
               vVertex[0][1] = y * fRadius;
               vVertex[0][2] = z * fRadius;

               x = stheta * srhodrho;
               y = ctheta * srhodrho;
               z = crhodrho;

               vTexture[1][0] = s;
               vTexture[1][1] = t - dt;
               vNormal[1][0] = x;
               vNormal[1][1] = y;
               vNormal[1][2] = z;
               vVertex[1][0] = x * fRadius;
               vVertex[1][1] = y * fRadius;
               vVertex[1][2] = z * fRadius;


               theta = ((j + 1) == iSlices) ? 0.0f : (j + 1) * dtheta;
               stheta = (float) (-Math.sin(theta));
               ctheta = (float) (Math.cos(theta));

               x = stheta * srho;
               y = ctheta * srho;
               z = crho;

               s += ds;
               vTexture[2][0] = s;
               vTexture[2][1] = t;
               vNormal[2][0] = x;
               vNormal[2][1] = y;
               vNormal[2][2] = z;
               vVertex[2][0] = x * fRadius;
               vVertex[2][1] = y * fRadius;
               vVertex[2][2] = z * fRadius;

               x = stheta * srhodrho;
               y = ctheta * srhodrho;
               z = crhodrho;

               vTexture[3][0] = s;
               vTexture[3][1] = t - dt;
               vNormal[3][0] = x;
               vNormal[3][1] = y;
               vNormal[3][2] = z;
               vVertex[3][0] = x * fRadius;
               vVertex[3][1] = y * fRadius;
               vVertex[3][2] = z * fRadius;

               addTriangle(vertices, indexes, vVertex, vNormal, vTexture);

               // Rearrange for next triangle

               System.arraycopy(vVertex[1], 0, vVertex[0], 0, 3);
               System.arraycopy(vNormal[1], 0, vNormal[0], 0, 3);
               System.arraycopy(vTexture[1], 0, vTexture[0], 0, 2);

               System.arraycopy(vVertex[3], 0, vVertex[1], 0, 3);
               System.arraycopy(vNormal[3], 0, vNormal[1], 0, 3);
               System.arraycopy(vTexture[3], 0, vTexture[1], 0, 2);

               addTriangle(vertices, indexes, vVertex, vNormal, vTexture);
           }
           t -= dt;
       }


       float[] fv = new float[vertices.size() * 4];
       for (i = 0; i < vertices.size(); i++) {
           fv[i << 2] = vertices.get(i).xyz[0];
           fv[(i << 2) + 1] = vertices.get(i).xyz[1];
           fv[(i << 2) + 2] = vertices.get(i).xyz[2];
           fv[(i << 2) + 3] = 1.0f;
       }

       float[] fn = new float[vertices.size() * 3];
       for (i = 0; i < vertices.size(); i++) {
           fn[i * 3 + 0] = vertices.get(i).nrm[0];
           fn[i * 3 + 1] = vertices.get(i).nrm[1];
           fn[i * 3 + 2] = vertices.get(i).nrm[2];
       }

       float[] ft = new float[vertices.size() * 2];
       for (i = 0; i < vertices.size(); i++) {
           ft[i * 2 + 0] = vertices.get(i).txt[0];
           ft[i * 2 + 1] = vertices.get(i).txt[1];
       }

       short[] idx = new short[indexes.size()];
       for (i = 0; i < indexes.size(); i++) {
           idx[i] = indexes.get(i);
       }
       return new SimpleGLBatch(GLES20.GL_TRIANGLES, fv, null, fn, ft, idx);
   }

   public static GLBatch makeDisk(float innerRadius, float outerRadius, int nSlices, int nStacks) {
       // How much to step out each stack
       float fStepSizeRadial = outerRadius - innerRadius;
       if (fStepSizeRadial < 0.0f) // Dum dum...
       {
           fStepSizeRadial *= -1.0f;
       }

       fStepSizeRadial /= (float) nStacks;

       float fStepSizeSlice = (3.1415926536f * 2.0f) / (float) nSlices;

       List<Vertex> vertices = new ArrayList<Vertex>();
       List<Short> indexes = new ArrayList<Short>();

       float[][] vVertex = new float[4][3];
       float[][] vNormal = new float[4][3];
       float[][] vTexture = new float[4][2];

       float fRadialScale = 1.0f / outerRadius;

       for (int i = 0; i < nStacks; i++) // Stacks
       {
           float theyta;
           float theytaNext;
           for (int j = 0; j < nSlices; j++) // Slices
           {
               float inner = innerRadius + ((float) i) * fStepSizeRadial;
               float outer = innerRadius + ((float) (i + 1)) * fStepSizeRadial;

               theyta = fStepSizeSlice * (float) (j);
               if (j == (nSlices - 1)) {
                   theytaNext = 0.0f;
               } else {
                   theytaNext = fStepSizeSlice * ((float) (j + 1));
               }

               // Inner First
               vVertex[0][0] = (float) Math.cos(theyta) * inner;	// X	
               vVertex[0][1] = (float) Math.sin(theyta) * inner;	// Y
               vVertex[0][2] = 0.0f;					// Z

               vNormal[0][0] = 0.0f;					// Surface Normal, same for everybody
               vNormal[0][1] = 0.0f;
               vNormal[0][2] = 1.0f;

               vTexture[0][0] = ((vVertex[0][0] * fRadialScale) + 1.0f) * 0.5f;
               vTexture[0][1] = ((vVertex[0][1] * fRadialScale) + 1.0f) * 0.5f;

               // Outer First
               vVertex[1][0] = (float) Math.cos(theyta) * outer;	// X	
               vVertex[1][1] = (float) Math.sin(theyta) * outer;	// Y
               vVertex[1][2] = 0.0f;					// Z

               vNormal[1][0] = 0.0f;					// Surface Normal, same for everybody
               vNormal[1][1] = 0.0f;
               vNormal[1][2] = 1.0f;

               vTexture[1][0] = ((vVertex[1][0] * fRadialScale) + 1.0f) * 0.5f;
               vTexture[1][1] = ((vVertex[1][1] * fRadialScale) + 1.0f) * 0.5f;

               // Inner Second
               vVertex[2][0] = (float) Math.cos(theytaNext) * inner;	// X	
               vVertex[2][1] = (float) Math.sin(theytaNext) * inner;	// Y
               vVertex[2][2] = 0.0f;					// Z

               vNormal[2][0] = 0.0f;					// Surface Normal, same for everybody
               vNormal[2][1] = 0.0f;
               vNormal[2][2] = 1.0f;

               vTexture[2][0] = ((vVertex[2][0] * fRadialScale) + 1.0f) * 0.5f;
               vTexture[2][1] = ((vVertex[2][1] * fRadialScale) + 1.0f) * 0.5f;


               // Outer Second
               vVertex[3][0] = (float) Math.cos(theytaNext) * outer;	// X	
               vVertex[3][1] = (float) Math.sin(theytaNext) * outer;	// Y
               vVertex[3][2] = 0.0f;					// Z

               vNormal[3][0] = 0.0f;					// Surface Normal, same for everybody
               vNormal[3][1] = 0.0f;
               vNormal[3][2] = 1.0f;

               vTexture[3][0] = ((vVertex[3][0] * fRadialScale) + 1.0f) * 0.5f;
               vTexture[3][1] = ((vVertex[3][1] * fRadialScale) + 1.0f) * 0.5f;

               addTriangle(vertices, indexes, vVertex, vNormal, vTexture);

               // Rearrange for next triangle
               System.arraycopy(vVertex[1], 0, vVertex[0], 0, 3);
               System.arraycopy(vNormal[1], 0, vNormal[0], 0, 3);
               System.arraycopy(vTexture[1], 0, vTexture[0], 0, 2);

               System.arraycopy(vVertex[3], 0, vVertex[1], 0, 3);
               System.arraycopy(vNormal[3], 0, vNormal[1], 0, 3);
               System.arraycopy(vTexture[3], 0, vTexture[1], 0, 2);

               addTriangle(vertices, indexes, vVertex, vNormal, vTexture);
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

       float[] ft = new float[vertices.size() * 2];
       for (int i = 0; i < vertices.size(); i++) {
           ft[i * 2 + 0] = vertices.get(i).txt[0];
           ft[i * 2 + 1] = vertices.get(i).txt[1];
       }

       short[] idx = new short[indexes.size()];
       for (int i = 0; i < indexes.size(); i++) {
           idx[i] = indexes.get(i);
       }
       return new SimpleGLBatch(GLES20.GL_TRIANGLES, fv, null, fn, ft, idx);
   }

   public static GLBatch makeCylinder(float baseRadius, float topRadius, float fLength, int numSlices, int numStacks) {
       float fRadiusStep = (float) (topRadius - baseRadius) / (float) (numStacks);

       float fStepSizeSlice = (float) (3.1415926536f * 2.0f) / (float) (numSlices);

       List<Vertex> vertices = new ArrayList<Vertex>();
       List<Short> indexes = new ArrayList<Short>();

       float[][] vVertex = new float[4][3];
       float[][] vNormal = new float[4][3];
       float[][] vTexture = new float[4][2];

       float ds = 1.0f / (float) (numSlices);
       float dt = 1.0f / (float) (numStacks);
       float s;
       float t;

       for (int i = 0; i < numStacks; i++) {
           if (i == 0) {
               t = 0.0f;
           } else {
               t = (float) i * dt;
           }

           float tNext;
           if (i == (numStacks - 1)) {
               tNext = 1.0f;
           } else {
               tNext = (float) (i + 1) * dt;
           }

           float fCurrentRadius = baseRadius + (fRadiusStep * (float) (i));
           float fNextRadius = baseRadius + (fRadiusStep * (float) (i + 1));
           float theyta;
           float theytaNext;

           float fCurrentZ = (float) (i) * (fLength / (float) (numStacks));
           float fNextZ = (float) (i + 1) * (fLength / (float) (numStacks));

           float zNormal = 0.0f;
//		if(!m3dCloseEnough(baseRadius - topRadius, 0.0f, 0.00001f))
//			{
           // Rise over run...
           zNormal = (baseRadius - topRadius);
//			}

           for (int j = 0; j < numSlices; j++) {
               if (j == 0) {
                   s = 0.0f;
               } else {
                   s = (float) (j) * ds;
               }

               float sNext;
               if (j == (numSlices - 1)) {
                   sNext = 1.0f;
               } else {
                   sNext = (float) (j + 1) * ds;
               }

               theyta = fStepSizeSlice * (float) (j);
               if (j == (numSlices - 1)) {
                   theytaNext = 0.0f;
               } else {
                   theytaNext = fStepSizeSlice * ((float) (j + 1));
               }

               // Inner First
               vVertex[1][0] = (float) Math.cos(theyta) * fCurrentRadius;	// X	
               vVertex[1][1] = (float) Math.sin(theyta) * fCurrentRadius;	// Y
               vVertex[1][2] = fCurrentZ;						// Z

               vNormal[1][0] = vVertex[1][0];					// Surface Normal, same for everybody
               vNormal[1][1] = vVertex[1][1];
               vNormal[1][2] = zNormal;
               Math3D.normalizeVector3(vNormal[1]);

               vTexture[1][0] = s;					// Texture Coordinates, I have no idea...
               vTexture[1][1] = t;

               // Outer First
               vVertex[0][0] = (float) Math.cos(theyta) * fNextRadius;	// X	
               vVertex[0][1] = (float) Math.sin(theyta) * fNextRadius;	// Y
               vVertex[0][2] = fNextZ;						// Z

//			if(!m3dCloseEnough(fNextRadius, 0.0f, 0.00001f)) {
               vNormal[0][0] = vVertex[0][0];					// Surface Normal, same for everybody
               vNormal[0][1] = vVertex[0][1];					// For cones, tip is tricky
               vNormal[0][2] = zNormal;
               Math3D.normalizeVector3(vNormal[0]);
//				}
//			else
//				memcpy(vNormal[0], vNormal[1], sizeof(M3DVector3f));


               vTexture[0][0] = s;					// Texture Coordinates, I have no idea...
               vTexture[0][1] = tNext;

               // Inner second
               vVertex[3][0] = (float) Math.cos(theytaNext) * fCurrentRadius;	// X	
               vVertex[3][1] = (float) Math.sin(theytaNext) * fCurrentRadius;	// Y
               vVertex[3][2] = (float) fCurrentZ;						// Z

               vNormal[3][0] = vVertex[3][0];					// Surface Normal, same for everybody
               vNormal[3][1] = vVertex[3][1];
               vNormal[3][2] = zNormal;
               Math3D.normalizeVector3(vNormal[3]);

               vTexture[3][0] = sNext;					// Texture Coordinates, I have no idea...
               vTexture[3][1] = t;

               // Outer second
               vVertex[2][0] = (float) Math.cos(theytaNext) * fNextRadius;	// X	
               vVertex[2][1] = (float) Math.sin(theytaNext) * fNextRadius;	// Y
               vVertex[2][2] = fNextZ;						// Z

//			if(!m3dCloseEnough(fNextRadius, 0.0f, 0.00001f)) {
               vNormal[2][0] = vVertex[2][0];					// Surface Normal, same for everybody
               vNormal[2][1] = vVertex[2][1];
               vNormal[2][2] = zNormal;
               Math3D.normalizeVector3(vNormal[2]);
//				}
//			else
//				memcpy(vNormal[2], vNormal[3], sizeof(M3DVector3f));


               vTexture[2][0] = sNext;					// Texture Coordinates, I have no idea...
               vTexture[2][1] = tNext;

               addTriangle(vertices, indexes, vVertex, vNormal, vTexture);

               // Rearrange for next triangle
               System.arraycopy(vVertex[1], 0, vVertex[0], 0, 3);
               System.arraycopy(vNormal[1], 0, vNormal[0], 0, 3);
               System.arraycopy(vTexture[1], 0, vTexture[0], 0, 2);

               System.arraycopy(vVertex[3], 0, vVertex[1], 0, 3);
               System.arraycopy(vNormal[3], 0, vNormal[1], 0, 3);
               System.arraycopy(vTexture[3], 0, vTexture[1], 0, 2);

               addTriangle(vertices, indexes, vVertex, vNormal, vTexture);
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

       float[] ft = new float[vertices.size() * 2];
       for (int i = 0; i < vertices.size(); i++) {
           ft[i * 2 + 0] = vertices.get(i).txt[0];
           ft[i * 2 + 1] = vertices.get(i).txt[1];
       }

       short[] idx = new short[indexes.size()];
       for (int i = 0; i < indexes.size(); i++) {
           idx[i] = indexes.get(i);
       }
       return new SimpleGLBatch(GLES20.GL_TRIANGLES, fv, null, fn, ft, idx);
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

       float[] textures = new float[]{
		   //front
	       1, 0,
	       1, 1,
	       0, 1,
	       0, 0,            
    		               
           //right
           1, 0,
           1, 1,
           0, 1,
           0, 0,            
           
           //back
           1, 0,
           1, 1,            
           0, 1,
           0, 0,
           
           //left
           0, 0,
           0, 1,
           1, 0,            
           1, 1,            
                                    
           //top
           1, 0,
           0, 0,
           1, 1,            
           0, 1,            
           
           //bottom
           0, 1,
           1, 1,
           0, 0,            
           1, 0,  
       };
       float[] normals = new float[]{
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
