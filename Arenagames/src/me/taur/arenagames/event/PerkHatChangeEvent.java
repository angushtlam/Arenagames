package me.taur.arenagames.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PerkHatChangeEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	 
	private Player p;
	
	public PerkHatChangeEvent(Player p) {
		this.p = p;
	}
	
	public Player getPlayer() {
		return this.p;
	}
	
	@Override
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}
}
