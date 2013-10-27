package me.taur.arenagames.tdm;

import java.util.Random;
import java.util.Set;

import me.taur.arenagames.Arenagames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class TdmScheduler {
	
	public static void startCounter() {
		Bukkit.getScheduler().runTaskLater(Arenagames.plugin, new Runnable() {
			public void run() {
				startCounter();
				
				Set<String> rooms = TdmModeration.TDM_ROOM_TIMER.keySet();
				
				// Get each key in TDM_ROOMS
				for (String room : rooms) {
					int timer = TdmModeration.TDM_ROOM_TIMER.get(room);
					
					// If timer less than 0, game is not in progress.
					if (timer < 0) {
						continue;
						
					}
					
					if (timer == 1) {
						TdmModeration.TDM_ROOM_TIMER.put(room, 0);
						gameOverFor(room);
						continue;
						
					}
					
					if (timer > 1) {
						TdmModeration.TDM_ROOM_TIMER.put(room, timer - 1);
						
					}
					
				}
				
				for (String room : rooms) {
					int timer = TdmModeration.TDM_WAIT_TIMER.get(room);
					
					// If timer less than 0, game is no longer waiting.
					if (timer < 0) {
						TdmModeration.TDM_WAIT_TIMER.remove(room);
						continue;
						
					}
					
					if (timer == 1) {
						TdmModeration.TDM_WAIT_TIMER.remove(room);
						Bukkit.broadcastMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "Team Deathmatch Room " + room + " has started.");
						startGameFor(room);
						continue;
						
					}
					
					if (timer > 1) {
						TdmModeration.TDM_ROOM_TIMER.put(room, timer - 1);
						
					}
					
				}
				
			}
			
		}, 20L);
	}
	
	public static int startGameFor(String room) {
		int maps = TdmConfig.get().getConfigurationSection("tdm.maps").getKeys(false).size();
		
		Random r = new Random();
		int rand = r.nextInt(maps);
		
		TdmModeration.TDM_ROOM_MAP.put(room, rand);
		return rand;
		
	}
	
	public static void gameOverFor(String key) {
		Bukkit.broadcastMessage(ChatColor.AQUA + "Team Deathmatch Room " + key + " has finished its game.");
		
		// Put the game back into 
		TdmModeration.TDM_ROOM_TIMER.put(key, -1);
		
		int red = TdmModeration.TDM_RED_TEAM.get(key);
		int blue = TdmModeration.TDM_BLUE_TEAM.get(key);
		
		if (red > blue) {
			Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + "TDM " + key + ": Red won " + ChatColor.RED + red + ChatColor.YELLOW + " to " + ChatColor.BLUE + blue + ".");
			
		} else if (blue > red) {
			Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + "TDM " + key + ": Blue won " + ChatColor.BLUE + blue + ChatColor.YELLOW + " to " + ChatColor.RED + red + ".");
			
		} else {
			Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + "TDM " + key + ": Game tied at " + ChatColor.AQUA + "" + red + ".");
			
		}
		
	}

}
