package com.mobplug.games.framework;

import com.mobplug.games.framework.interfaces.GameEvent;

public class SimpleGameEvent implements GameEvent {

	private int eventId;
	
	public SimpleGameEvent(int eventId) {
		this.eventId = eventId;
	}
	
	@Override
	public int getEventId() {
		return eventId;
	}

}
