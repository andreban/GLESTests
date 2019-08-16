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
