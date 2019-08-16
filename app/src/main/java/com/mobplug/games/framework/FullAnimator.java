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

package com.mobplug.games.framework;

import java.util.TreeMap;

import com.mobplug.games.framework.interfaces.Animator;

public class FullAnimator<T> implements Animator<T> {
	private static final long serialVersionUID = -6394039548538889954L;
	
	private TreeMap<Long, T> timings = new TreeMap<Long, T>();
	
	//private boolean started = false;
	private boolean loop = false;
	private long startTime = 0;
	private long elapsed = 0;
	
	@Override
	public void addImage(T image, long delay) {
		timings.put(delay, image);		
	}

	@Override
	public T getImage() {
		return null;
//		if (!started) return timings.firstEntry().getValue();
//		Entry<Long, T> entry = timings.ceilingEntry(elapsed);
//		if (entry != null) return entry.getValue();
//		else return timings.lastEntry().getValue();
	}

	@Override
	public void start(long gameTime, boolean loop) {
		//started = true;
		startTime = gameTime;
		this.loop = loop;		
	}

	@Override
	public void start(long gameTime) {		
		start(gameTime, false);
	}

	@Override
	public void update(long gameTime) {		
		elapsed = gameTime - startTime;
		if (loop && elapsed > timings.lastKey()) {
			elapsed = 0;
		}
	}	
}
