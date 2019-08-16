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

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class GLStringTexture {
	private int mTextureID;
	
	public GLStringTexture(Context context) {
		// Create an empty, mutable bitmap
		Bitmap bitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_4444);
		// get a canvas to paint over the bitmap
		Canvas canvas = new Canvas(bitmap);
		bitmap.eraseColor(0);

		// get a background image from resources
		// note the image format must match the bitmap format
//		Drawable background = context.getResources().getDrawable(R.drawable.background);
//		background.setBounds(0, 0, 256, 256);
//		background.draw(canvas); // draw the background to our bitmap
		Paint bgPaint = new Paint();
		bgPaint.setARGB(255, 255, 255, 255);
		canvas.drawRect(0, 0, 255, 255, bgPaint);
		
		// Draw the text
		Paint textPaint = new Paint();
		textPaint.setTextSize(32);
		textPaint.setAntiAlias(true);
		textPaint.setARGB(0xff, 0x00, 0x00, 0x00);
		// draw the text centered
		canvas.drawText("Hello World", 16,112, textPaint);

		int[] textures = new int[1];		
		//Generate one texture pointer...
		GLES20.glGenTextures(1, textures, 0);
		mTextureID = textures[0];		
		//...and bind it to our array
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureID);

		//Create Nearest Filtered Texture
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

		//Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

		//Use the Android GLUtils to specify a two-dimensional texture image from our bitmap
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

		//Clean up
		bitmap.recycle();		
	}
	
	public void useTexture(int textureid) {
		GLES20.glActiveTexture(textureid);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureID);  		
	}	
}
