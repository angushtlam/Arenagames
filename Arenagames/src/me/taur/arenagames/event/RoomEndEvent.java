package me.taur.arenagames.event;

import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.RoomEndResult;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RoomEndEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	 
	private String roomId;
	private RoomEndResult result;
	
	public RoomEndEvent(String roomId, RoomEndResult result) {
		this.roomId = roomId;
		this.result = result;
		
	}
	
	public String getRoomId() {
		return this.roomId;
	}
	
	public Room getRoom() throws NullPointerException {
		return Room.ROOMS.get(roomId);
	}
	
	public RoomEndResult getResult() {
		return this.result;		
	}
	
	@Override
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}
}
