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
