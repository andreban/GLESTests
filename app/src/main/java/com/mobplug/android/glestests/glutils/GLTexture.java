package com.mobplug.android.glestests.glutils;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

public class GLTexture {
	private int mTextureID = -1;
	public GLTexture(Context context, int resourceid) {
		int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, 0);

        mTextureID = textures[0];
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureID);

        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_LINEAR);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
                GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
                GLES20.GL_REPEAT);

        InputStream is = context.getResources()
            .openRawResource(resourceid);
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(is);
        } finally {
            try {
                is.close();
            } catch(IOException e) {
                // Ignore.
            }
        }

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();		
	}
	
	public void useTexture(int textureid) {
		if (mTextureID == -1) throw new IllegalStateException("Illegal Texture. Already deleted?");
		GLES20.glActiveTexture(textureid);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureID);  		
	}
	
	private void delete() {
		int[] textures = new int[1];
		textures[0] = mTextureID;
		GLES20.glDeleteTextures(1, textures, 0);
		mTextureID = -1;
	}
	
	@Override
	protected void finalize() throws Throwable {
		try {
			delete();
		} catch(RuntimeException ex) {
			Log.e("GLTexture", "Error deleting Texture", ex);
		}
		super.finalize();
	}
}
