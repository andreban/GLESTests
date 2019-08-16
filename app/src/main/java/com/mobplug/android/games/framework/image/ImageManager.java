package com.mobplug.android.games.framework.image;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;

import com.mobplug.android.games.framework.util.ImageLoader;

public class ImageManager {
	private Context context;
	private ImageLoader imageLoader;
	private Map<String, Integer> nameIdMap = new HashMap<String, Integer>();
	
	public ImageManager(Context context) {
		this.context = context;
		imageLoader = new ImageLoader(this.context);
	}
	
	public Bitmap getImage(String name) {
		int id = nameIdMap.get(name);
		if (id >= 0) return imageLoader.load(id);
		return null;
	}
	
	public Bitmap getImage(String name, int width, int height) {
		int id = nameIdMap.get(name);
		if (id >= 0) return imageLoader.load(id, width, height);
		return null;
	}	
	

}
