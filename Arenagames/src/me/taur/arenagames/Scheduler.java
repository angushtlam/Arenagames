package me.taur.arenagames;

import me.taur.arenagames.ffa.FfaActive;
import me.taur.arenagames.item.CustomItem;
import me.taur.arenagames.lfl.LflActive;

import org.bukkit.Bukkit;

public class Scheduler {
	public static void start() {
		Bukkit.getScheduler().runTaskLater(Arenagames.plugin, new Runnable() {
			public void run() {
				start(); // Loops the scheduler.
			}
		}, 20L);
		
		FfaActive.run();
		LflActive.run();

		CustomItem.startTimer();
		
	}
}