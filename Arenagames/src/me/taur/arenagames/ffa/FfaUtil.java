package me.taur.arenagames.ffa;

import me.taur.arenagames.Config;
import me.taur.arenagames.util.Room;
import me.taur.arenagames.util.RoomType;

public class FfaUtil {
	public static void enable() {
		for (int i = 0; i < Config.getNormalRooms(RoomType.FFA); i++) {
			String roomId = "ffa-n" + i;
			Room.ROOMS.put(roomId, new FfaRoom(roomId));
			
		}
		
		for (int i = 0; i < Config.getPremiumRooms(RoomType.FFA); i++) {
			String roomId = "ffa-p" + i;
			Room.ROOMS.put(roomId, new FfaRoom(roomId));
			Room room = Room.ROOMS.get(roomId);
			room.setPremium(true);
			
		}
		
	}
	
	public static void disable() {
		
		
	}
	
	
	
}
