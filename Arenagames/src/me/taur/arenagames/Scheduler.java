package me.taur.arenagames;

import me.taur.arenagames.ffa.FfaActive;
import me.taur.arenagames.item.CustomItem;
import me.taur.arenagames.perk.PerkEffectActive;
import me.taur.arenagames.tdm.TdmActive;

import org.bukkit.Bukkit;

public class Scheduler {
	public static int tick = 0;
	
	public static void start() {
		Bukkit.getScheduler().runTaskLater(Arenagames.plugin, new Runnable() {
			public void run() {
				start(); // Loops the scheduler.
			}
		}, 5L);
		
		if (tick + 5 > 100) { // Counts ticks up to 5 seconds.
			tick = 0;
		} else {
			tick = tick + 5;
		}
		
		if (checkTick(20)) {
			FfaActive.run();
			TdmActive.run();

			CustomItem.run();
			PerkEffectActive.tick20();
		}
		
		if (checkTick(5)) {
			PerkEffectActive.tick5();
			
		}
	}
	
	public static boolean checkTick(int num) {
		if (tick % num == 0) { // If the current tick is a factor of the int passed.
			return true;
		}
		
		return false;
	}
}