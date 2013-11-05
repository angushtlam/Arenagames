package me.taur.arenagames.room;

import me.taur.arenagames.Arenagames;
import me.taur.arenagames.ffa.FfaActive;

import org.bukkit.Bukkit;

public class RoomScheduler {
	public static void start() {
		Bukkit.getScheduler().runTaskLater(Arenagames.plugin, new Runnable() {
			public void run() {
				start(); // Loops the scheduler.
				
			}
		}, 20L);
		
		FfaActive.run();
		
	}
}