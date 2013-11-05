package me.taur.arenagames;

import me.taur.arenagames.ffa.FfaKitSelectorListener;
import me.taur.arenagames.ffa.FfaPlayerListener;
import me.taur.arenagames.ffa.FfaUtil;
import me.taur.arenagames.ffa.FfaSignListener;
import me.taur.arenagames.room.RoomPlayerActiveListener;
import me.taur.arenagames.room.RoomPlayerDiedListener;
import me.taur.arenagames.room.RoomScheduler;
import me.taur.arenagames.room.SignListener;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Arenagames extends JavaPlugin {
	
	public static Arenagames plugin;

	@Override
	public void onEnable() {
		plugin = this;
		
		Config.startCheck();
		
		regCmd("queue", new RoomCommand());
		loadGamemodes();
		
		regEvent(new RoomPlayerActiveListener());
		regEvent(new RoomPlayerDiedListener());
		regEvent(new SignListener());
		
		RoomScheduler.start();
		
	}

	@Override
	public void onDisable() {
		
	}
	
	public static void loadGamemodes() {
		String[] gm = Config.gamemode;
		
		for (int i = 0; i < gm.length; i++) {
			if (gm[i].contains("ffa")) {
				FfaUtil.enable();
				regFfa();
				
			}
		}
	}
	
	public static void regEvent(Listener file) {
		Bukkit.getServer().getPluginManager().registerEvents(file, Arenagames.plugin);
		
	}

	public static void regCmd(String cmd, CommandExecutor exe) {
		Bukkit.getServer().getPluginCommand(cmd).setExecutor(exe);
		
	}

	private static void regFfa() {
		regEvent(new FfaSignListener());
		regEvent(new FfaPlayerListener());
		regEvent(new FfaKitSelectorListener());
		
	}
}