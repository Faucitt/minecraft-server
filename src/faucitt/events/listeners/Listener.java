package faucitt.events.listeners;

import java.util.EventListener;

import faucitt.events.Event;

public interface Listener extends EventListener {
	public void Dispatch(Event e);
}
