package com.mobplug.android.glestests.glutils;

import android.opengl.GLES20;
import android.util.Log;

public class GLUtils {
	private static final String TAG = "GLUtils";
    public static void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, op + ": glError " + error);
            throw new RuntimeException(op + ": glError " + error);
        }
    }
}
