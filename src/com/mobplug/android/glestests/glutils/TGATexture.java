package com.mobplug.android.glestests.glutils;

import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.opengl.GLES20;

public class TGATexture {
    private int mTextureID;
    private int width;
    private int height;
    private byte[] data;
	public TGATexture(Context context, int resourceid) {
		int[] textures = new int[1];
		GLES20.glGenBuffers(1, textures, 0);
        mTextureID = textures[0];

        GLES20.glBindTexture(GL11.GL_TEXTURE_2D, mTextureID);

        GLES20.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
                GL11.GL_NEAREST);
        GLES20.glTexParameterf(GL11.GL_TEXTURE_2D,
                GL11.GL_TEXTURE_MAG_FILTER,
                GL11.GL_LINEAR);

        GLES20.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
                GL11.GL_REPEAT);
        GLES20.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                GL11.GL_REPEAT);      
        GLES20.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);  
        
        InputStream in = context.getResources().openRawResource(resourceid);
        try {
        	TGATextureLoader.loadTGA(this, in);
        } catch(Exception ex) {
        	ex.printStackTrace();
        }
	}
	
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
        ByteBuffer bf = BufferUtils.createByteBuffer(data.length);        
        bf.put(data);
        bf.flip();
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, bf);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getTextureID() {
        return mTextureID;
    }	
    
    public void useTexture(int textureid) {
		GLES20.glActiveTexture(textureid);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureID);      	
    }
}
