package com.mobplug.android.glestests.glutils;

/**
*
* @author andreban
*/
public class GLFrustrum {
   private float[] projMatrix = new float[16];
   
   // Untransformed corners of the frustum
   private float[] nearUL = new float[4], nearLL = new float[4], nearUR = new float[4], nearLR = new float[4];
   private float[] farUL = new float[4],  farLL = new float[4],  farUR = new float[4],  farLR = new float[4];

//   // Transformed corners of Frustum
//   private float[] nearULT = new float[4], nearLLT = new float[4], nearURT = new float[4], nearLRT = new float[4];
//   private float[] farULT = new float[4],  farLLT = new float[4],  farURT = new float[4],  farLRT = new float[4];
//
//   // Base and Transformed plane equations
//   private float[] nearPlane = new float[4], farPlane = new float[4], leftPlane = new float[4], rightPlane = new float[4];
//   private float[] topPlane = new float[4], bottomPlane = new float[4];    
   
   public GLFrustrum() {
       setOrthographic(-1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f);
   }
   
   public final void setOrthographic(float xMin, float xMax, float yMin, float yMax, float zMin, float zMax) {
       Math3D.makeOrthographicMatrix(projMatrix, xMin, xMax, yMin, yMax, zMin, zMax);
       projMatrix[15] = 1.0f;

       // Fill in values for untransformed Frustum corners
       // Near Upper Left
       nearUL[0] = xMin; nearUL[1] = yMax; nearUL[2] = zMin; nearUL[3] = 1.0f;

       // Near Lower Left
       nearLL[0] = xMin; nearLL[1] = yMin; nearLL[2] = zMin; nearLL[3] = 1.0f;

       // Near Upper Right
       nearUR[0] = xMax; nearUR[1] = yMax; nearUR[2] = zMin; nearUR[3] = 1.0f;

       // Near Lower Right
       nearLR[0] = xMax; nearLR[1] = yMin; nearLR[2] = zMin; nearLR[3] = 1.0f;

       // Far Upper Left
       farUL[0] = xMin; farUL[1] = yMax; farUL[2] = zMax; farUL[3] = 1.0f;

       // Far Lower Left
       farLL[0] = xMin; farLL[1] = yMin; farLL[2] = zMax; farLL[3] = 1.0f;

       // Far Upper Right
       farUR[0] = xMax; farUR[1] = yMax; farUR[2] = zMax; farUR[3] = 1.0f;

       // Far Lower Right
       farLR[0] = xMax; farLR[1] = yMin; farLR[2] = zMax; farLR[3] = 1.0f;       
   }
   
   public final void setPerspective(float fFov, float fAspect, float fNear, float fFar) {
       float xmin, xmax, ymin, ymax;       // Dimensions of near clipping plane
       float xFmin, xFmax, yFmin, yFmax;   // Dimensions of far clipping plane

       // Do the Math for the near clipping plane
       ymax = fNear * (float)(Math.tan( fFov * Math.PI / 360.0 ));
       ymin = -ymax;
       xmin = ymin * fAspect;
       xmax = -xmin;

       // Construct the projection matrix
       Math3D.loadIdentity44(projMatrix);
       projMatrix[0] = (2.0f * fNear)/(xmax - xmin);
       projMatrix[5] = (2.0f * fNear)/(ymax - ymin);
       projMatrix[8] = (xmax + xmin) / (xmax - xmin);
       projMatrix[9] = (ymax + ymin) / (ymax - ymin);
       projMatrix[10] = -((fFar + fNear)/(fFar - fNear));
       projMatrix[11] = -1.0f;
       projMatrix[14] = -((2.0f * fFar * fNear)/(fFar - fNear));
       projMatrix[15] = 0.0f;

       // Do the Math for the far clipping plane
       yFmax = fFar * (float)(Math.tan(fFov * Math.PI / 360.0));
       yFmin = -yFmax;
       xFmin = yFmin * fAspect;
       xFmax = -xFmin;


       // Fill in values for untransformed Frustum corners
       // Near Upper Left
       nearUL[0] = xmin; nearUL[1] = ymax; nearUL[2] = -fNear; nearUL[3] = 1.0f;

       // Near Lower Left
       nearLL[0] = xmin; nearLL[1] = ymin; nearLL[2] = -fNear; nearLL[3] = 1.0f;

       // Near Upper Right
       nearUR[0] = xmax; nearUR[1] = ymax; nearUR[2] = -fNear; nearUR[3] = 1.0f;

       // Near Lower Right
       nearLR[0] = xmax; nearLR[1] = ymin; nearLR[2] = -fNear; nearLR[3] = 1.0f;

       // Far Upper Left
       farUL[0] = xFmin; farUL[1] = yFmax; farUL[2] = -fFar; farUL[3] = 1.0f;

       // Far Lower Left
       farLL[0] = xFmin; farLL[1] = yFmin; farLL[2] = -fFar; farLL[3] = 1.0f;

       // Far Upper Right
       farUR[0] = xFmax; farUR[1] = yFmax; farUR[2] = -fFar; farUR[3] = 1.0f;

       // Far Lower Right
       farLR[0] = xFmax; farLR[1] = yFmin; farLR[2] = -fFar; farLR[3] = 1.0f;   
       
//       Math3D.printMatrix44(projMatrix);
   }
   
   public float[] getProjectionMatrix() {
       return projMatrix;
   }
}
