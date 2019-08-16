package com.mobplug.android.games.framework.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class ImageLoader {
	private Context context;
	
	public ImageLoader(Context context) {
		this.context = context;
	}
	
	public Bitmap load(int resid) {
		return BitmapFactory.decodeResource(context.getResources(), resid);
	}
	
	public Bitmap load(int resid, int width, int height) {
		Bitmap src = BitmapFactory.decodeResource(context.getResources(), resid);
		Bitmap resized = Bitmap.createScaledBitmap(src, width, height, false);
		src.recycle();
		return resized;
	}
	
	public Bitmap flipImage(Bitmap source) {
		Matrix m = new Matrix();		
		m.postScale(-1, 1);
		return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), m, true);		
	}	
	
	public Bitmap rotateImage(Bitmap source, float degrees) {
		Matrix m = new Matrix();
		m.postRotate(degrees);
		return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), m, true);		
	}
}
