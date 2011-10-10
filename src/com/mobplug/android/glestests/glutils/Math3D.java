package com.mobplug.android.glestests.glutils;

import android.opengl.Matrix;

/**
*
* @author andreban
*/
public class Math3D {
   private static final float[] IDENTITY_3x3_F = { 1.0f, 0.0f, 0.0f,
                                                   0.0f, 1.0f, 0.0f,
                                                   0.0f, 0.0f, 1.0f};
   
   private static final double[] IDENTITY_3x3_D = {1.0, 0.0, 0.0,
                                                   0.0, 1.0, 0.0,
                                                   0.0, 0.0, 1.0};    
   
   
   private static final float[] IDENTITY_4x4_F = { 1.0f, 0.0f, 0.0f, 0.0f,
                                                   0.0f, 1.0f, 0.0f, 0.0f,
                                                   0.0f, 0.0f, 1.0f, 0.0f,
                                                   0.0f, 0.0f, 0.0f, 1.0f};
   
   private static final double[] IDENTITY_4x4_D = {1.0, 0.0, 0.0, 0.0,
                                                   0.0, 1.0, 0.0, 0.0,
                                                   0.0, 0.0, 1.0, 0.0,
                                                   0.0, 0.0, 0.0, 1.0};       
   
   public static void loadIdentity33(float[] matrix) {
       if (matrix.length != 9) throw new IllegalArgumentException("Matrix must be at least 3x3");
       System.arraycopy(IDENTITY_3x3_F, 0, matrix, 0, 9);
   }
   
   public static void loadIdentity33(double[] matrix) {
       if (matrix.length != 9) throw new IllegalArgumentException("Matrix must be at least 3x3");
       System.arraycopy(IDENTITY_3x3_D, 0, matrix, 0, 9);
   } 
   
   public static void loadIdentity44(float[] matrix) {
       if (matrix.length != 16) throw new IllegalArgumentException("Matrix must be at least 4x4");
       System.arraycopy(IDENTITY_4x4_F, 0, matrix, 0, 16);
   }
   
   public static void loadIdentity44(double[] matrix) {
       if (matrix.length != 16) throw new IllegalArgumentException("Matrix must be at least 4x4");
       System.arraycopy(IDENTITY_4x4_D, 0, matrix, 0, 16);
   }  
   
   /**
    * returns the squared distance between two points
    * @param u
    * @param v
    * @return 
    */
   public static float getDistanceSquared3(float[] u, float[] v) {
       if (u.length != 3) {
           throw new IllegalArgumentException("vector must have 3 pos");
       }
       if (v.length != 3) {
           throw new IllegalArgumentException("vector must have 3 pos");
       }

       float x = u[0] - v[0];
       x = x * x;

       float y = u[1] - v[1];
       y = y * y;

       float z = u[2] - v[2];
       z = z * z;

       return (x + y + z);
   }
   
   /**
    * returns the squared distance between two points
    * @param u
    * @param v
    * @return 
    */    
   public static double getDistanceSquared3(double[] u, double[] v) {
       if (u.length != 3) {
           throw new IllegalArgumentException("vector must have 3 pos");
       }
       if (v.length != 3) {
           throw new IllegalArgumentException("vector must have 3 pos");
       }

       double x = u[0] - v[0];
       x = x * x;

       double y = u[1] - v[1];
       y = y * y;

       double z = u[2] - v[2];
       z = z * z;

       return (x + y + z);
   }    
   
   public static void translationMatrix44f(float[] matrix, float x, float y, float z) {
       loadIdentity44(matrix);
       matrix[12] = x;
       matrix[13] = y;
       matrix[14] = z;
   }
   
   public static void makeOrthographicMatrix(float[] mProjection, float xMin, float xMax, float yMin, float yMax, float zMin, float zMax) {
       loadIdentity44(mProjection);
       
       mProjection[0] = 2.0f / (xMax - xMin);
       mProjection[5] = 2.0f / (yMax - yMin);
       mProjection[10] = -2.0f / (zMax - zMin);
       mProjection[12] = -((xMax + xMin)/(xMax - xMin));
       mProjection[13] = -((yMax + yMin)/(yMax - yMin));
       mProjection[14] = -((zMax + zMin)/(zMax - zMin));
       mProjection[15] = 1.0f;
	}    
   
   /**
    * Multiply two 4x4 matricies
    */
   public static void matrixMultiply44(float[] product, final float[] a, final float[] b) {
	   Matrix.multiplyMM(product, 0, a, 0, b, 0);	   
//       for (int i = 0; i < 4; i++) {            
//           float ai0 = a[(0 << 2) + i], ai1 = a[(1 << 2) + i], ai2 = a[(2 << 2) + i], ai3 = a[(3 << 2) + i];
//           product[(0 << 2) + i] = ai0 * b[(0 << 2) + 0] + ai1 * b[(0 << 2) + 1] + ai2 * b[(0 << 2) + 2] + ai3 * b[(0 << 2) + 3];
//           product[(1 << 2) + i] = ai0 * b[(1 << 2) + 0] + ai1 * b[(1 << 2) + 1] + ai2 * b[(1 << 2) + 2] + ai3 * b[(1 << 2) + 3];            
//           product[(2 << 2) + i] = ai0 * b[(2 << 2) + 0] + ai1 * b[(2 << 2) + 1] + ai2 * b[(2 << 2) + 2] + ai3 * b[(2 << 2) + 3];            
//           product[(3 << 2) + i] = ai0 * b[(3 << 2) + 0] + ai1 * b[(3 << 2) + 1] + ai2 * b[(3 << 2) + 2] + ai3 * b[(3 << 2) + 3];
//       }
   }      
   
   public static void rotationMatrix44(float[] m, float angle, float x, float y, float z) {
       float mag, s, c;
       float xx, yy, zz, xy, yz, zx, xs, ys, zs, one_c;

       s = (float) Math.sin(angle);
       c = (float) Math.cos(angle);

       mag = (float) Math.sqrt(x * x + y * y + z * z);

       // Identity matrix
       if (mag == 0.0f) {
           loadIdentity44(m);
           return;
       }

       // Rotation matrix is normalized
       x /= mag;
       y /= mag;
       z /= mag;

       xx = x * x;
       yy = y * y;
       zz = z * z;
       xy = x * y;
       yz = y * z;
       zx = z * x;
       xs = x * s;
       ys = y * s;
       zs = z * s;
       one_c = 1.0f - c;

       //m[col*4+row]        
       m[0 * 4 + 0] = (one_c * xx) + c;
       m[0 * 4 + 1] = (one_c * xy) - zs;
       m[0 * 4 + 2] = (one_c * zx) + ys;
       m[0 * 4 + 3] = 0.0f;

       m[1 * 4 + 0] = (one_c * xy) + zs;
       m[1 * 4 + 1] = (one_c * yy) + c;
       m[1 * 4 + 2] = (one_c * yz) - xs;
       m[1 * 4 + 3] = 0.0f;

       m[2 * 4 + 0] = (one_c * zx) - ys;
       m[2 * 4 + 1] = (one_c * yz) + xs;
       m[2 * 4 + 2] = (one_c * zz) + c;
       m[2 * 4 + 3] = 0.0f;

       m[3 * 4 + 0] = 0.0f;
       m[3 * 4 + 1] = 0.0f;
       m[3 * 4 + 2] = 0.0f;
       m[3 * 4 + 3] = 1.0f;
   }
   
   public static void printMatrix44(float[] product) {
       for (int i = 0; i < 4; i++) {
           System.out.println(product[(0 << 2) + i] + ", " + product[(1 << 2) + i] + ", " + product[(2 << 2) + i] + ", " + product[(3 << 2) + i]);
       }
   }
   
   public static float getVectorLengthSquared3(float[] vec) {
       return getVectorLengthSquared3(vec, 0);
   }    
   
   public static float getVectorLengthSquared3(float[] vec, int start) {
       return (vec[start] * vec[start]) + (vec[start + 1] * vec[start + 1]) + (vec[start + 2] * vec[start + 2]);
   }   
   
   public static void scaleVector3(float[] vec, float scale, int start) {
       vec[start] *= scale;
       vec[start + 1] *= scale;
       vec[start + 2] *= scale;
   }
   
   public static void scaleVector3(float[] vec, float scale) {
       scaleVector3(vec, scale, 0);
   }
   
   public static float getVectorLength3(float[] vec, int start) {
       return (float)Math.sqrt(getVectorLengthSquared3(vec, start));        
   }
   
   public static float getVectorLength3(float[] vec) {
       return (float)Math.sqrt(getVectorLengthSquared3(vec));
   }
   
   public static void normalizeVector3(float[] vec, int start) {
       scaleVector3(vec, 1.0f / getVectorLength3(vec, start), start);      
   }
   
   public static void normalizeVector3(float[] vec) {
       normalizeVector3(vec, 0);
   }
   
   public static void extractRotationMatrix33(float[] result, float[] matrix) {
       System.arraycopy(matrix, 0, result, 0, 3);
       System.arraycopy(matrix, 4, result, 3, 3);        
       System.arraycopy(matrix, 8, result, 6, 3);                
   }

   public static void extractRotationMatrix33(double[] result, double[] matrix) {
       System.arraycopy(matrix, 0, result, 0, 3);
       System.arraycopy(matrix, 4, result, 3, 3);        
       System.arraycopy(matrix, 8, result, 6, 3);                   
   }    
}