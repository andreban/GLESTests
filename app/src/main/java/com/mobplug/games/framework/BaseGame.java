package com.mobplug.games.framework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.mobplug.games.framework.interfaces.Game;
import com.mobplug.games.framework.interfaces.GameEvent;
import com.mobplug.games.framework.interfaces.GameEventListener;
import com.mobplug.games.framework.interfaces.GameResult;
import com.mobplug.games.framework.interfaces.GameStateListener;

/**
 * An Abstract Game Implementation. Knows how to handle Listeners,
 *  Game Events and paused and gameOver state
 * 
 * @author andreban
 *
 */
public abstract class BaseGame implements Game {
	private static final long serialVersionUID = 6763460535149353391L;
	
	private transient Collection<GameStateListener> stateListeners;
	private transient Collection<GameEventListener> eventListeners;
	private volatile boolean gameOver = false;
	private volatile boolean paused = false;
	
    private static final int DEFAULT_TARGET_FREQUENCY = 85;	
    private int targetFrequency = DEFAULT_TARGET_FREQUENCY;
    private int period = 1000 / targetFrequency;
    protected long gameTime = 0;	

    public BaseGame(int frequency) {
		stateListeners = Collections.synchronizedCollection(new ArrayList<GameStateListener>());
		eventListeners = Collections.synchronizedCollection(new ArrayList<GameEventListener>());
		targetFrequency = frequency;
		period = 1000 / frequency;
    }
    
	public BaseGame() {
		stateListeners = Collections.synchronizedCollection(new ArrayList<GameStateListener>());
		eventListeners = Collections.synchronizedCollection(new ArrayList<GameEventListener>());
	}	
	
	@Override
	public boolean isGameOver() {
		return gameOver;
	}

	@Override
	public boolean isPaused() {
		return paused;
	}

	@Override
	public void pause() {
		paused = true;
		synchronized (stateListeners) {
			for (GameStateListener listener: stateListeners)
				listener.onPause(this);
		}		
	}
	
	@Override	
	public void resume() {
		paused = false;
		synchronized (stateListeners) {
			for (GameStateListener listener: stateListeners)
				listener.onResume(this);			
		}		
	}
	@Override
	public void update() {
		gameTime += period;
		update(gameTime);		
	}
	
	@Override
	public int getPeriod() {
		return this.period;
	}
	public abstract void update(long gameTime);
	public void togglePause() {
		if (isPaused()) resume();
		else pause();
	}
	
	protected void gameOver() {
		gameOver(GameResult.LOSE);
	}
	
	protected void gameOver(GameResult result) {
		gameOver = true;
		synchronized (stateListeners) {
			for (GameStateListener listener: stateListeners)
				listener.onGameOver(this, result);
		}
	}
	
	@Override	
	public void newGame() {
		gameTime = 0;
		gameOver = false;
		paused = false;
		synchronized (stateListeners) {
			for (GameStateListener listener: stateListeners)
				listener.onNewGame(this);
		}				
	}
	
	
	@Override
	public void addGameStateListener(GameStateListener gameListener) {
		stateListeners.add(gameListener);
	}
	
	@Override
	public void removeGameStateListener(GameStateListener gameListener) {
		stateListeners.remove(gameListener);
	}
	
	
	@Override
	public void addGameEventListener(GameEventListener gameEventListener) {
		eventListeners.add(gameEventListener);
	}
	
	@Override
	public void removeGameEventListener(GameEventListener gameEventListener) {
		eventListeners.remove(gameEventListener);
	}
	
	protected void fireGameEvent(GameEvent e) {
		synchronized (eventListeners) {
			for (GameEventListener listener: eventListeners)
				listener.onEvent(this, e);			
		}
	}
}
