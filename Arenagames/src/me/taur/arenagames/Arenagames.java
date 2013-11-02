package me.taur.arenagames;

import me.taur.arenagames.ffa.FfaUtil;
import me.taur.arenagames.ffa.FfaListener;
import me.taur.arenagames.util.RoomListener;
import me.taur.arenagames.util.RoomScheduler;
import me.taur.arenagames.util.SignListener;

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
		regEvent(new FfaListener());
		regEvent(new RoomListener());
		
	}
	
}