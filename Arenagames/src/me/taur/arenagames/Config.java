package me.taur.arenagames;

import me.taur.arenagames.ffa.FfaConfig;
import me.taur.arenagames.tdm.TdmConfig;
import me.taur.arenagames.util.RoomType;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

	public static String[] gamemode = {"FFA", "TDM"};
	
	public static void startCheck() {
		FileConfiguration config = Arenagames.plugin.getConfig();
		
		// Change every time a new gamemode is added.
		int latestVer = 3;
		
		if (config.getInt("do-not-change.current-version") != latestVer) {
			config.set("do-not-change.current-version", latestVer);
			
			config.addDefault("global.lobby.world", "world");
			config.addDefault("global.lobby.x", 0.0);
			config.addDefault("global.lobby.y", 70.0);
			config.addDefault("global.lobby.z", 0.0);
			
			for (int i = 0; i < gamemode.length; i++) {
				String gm = gamemode[i].toLowerCase();
				config.addDefault("gamemode." + gm + ".enabled", true);
				config.addDefault("gamemode." + gm + ".gameplay.hunger-regen-enabled", true);
				config.addDefault("gamemode." + gm + ".gameplay.hunger-regen-amt", 3);
				config.addDefault("gamemode." + gm + ".countdown.round-in-seconds", 240);
				config.addDefault("gamemode." + gm + ".countdown.wait-in-seconds", 45);
				config.addDefault("gamemode." + gm + ".countdown.min-players-to-start-wait", 2);
				config.addDefault("gamemode." + gm + ".ranked.elo-enabled", true);
				config.addDefault("gamemode." + gm + ".economy.currency-enabled", true);
				config.addDefault("gamemode." + gm + ".rooms.normal", 2);
				config.addDefault("gamemode." + gm + ".rooms.premium", 1);
				config.addDefault("gamemode." + gm + ".player-limit", 18);
				
			}
			
			config.options().copyDefaults(true);
		    Arenagames.plugin.saveConfig();
		    
		}
		
		FfaConfig.defaultConf();
		TdmConfig.defaultConf();
	    
	}
	
	public static boolean isEnabled(RoomType type) {
		String gm = type.toString().toLowerCase();
		return Arenagames.plugin.getConfig().getBoolean("gamemode." + gm + ".enabled");
		
	}
	
	public static boolean isHungerRegenEnabled(RoomType type) {
		String gm = type.toString().toLowerCase();
		return Arenagames.plugin.getConfig().getBoolean("gamemode." + gm + ".gameplay.hunger-regen-enabled");
		
	}
	
	public static int getHungerRegen(RoomType type) {
		String gm = type.toString().toLowerCase();
		return Arenagames.plugin.getConfig().getInt("gamemode." + gm + ".gameplay.hunger-regen-amt");
		
	}
	
	public static int getCountdown(RoomType type) {
		String gm = type.toString().toLowerCase();
		return Arenagames.plugin.getConfig().getInt("gamemode." + gm + ".countdown.round-in-seconds");
		
	}
	
	public static int getWaitTimer(RoomType type) {
		String gm = type.toString().toLowerCase();
		return Arenagames.plugin.getConfig().getInt("gamemode." + gm + ".countdown.wait-in-seconds");
		
	}
	
	public static int getMinPlayersInWait(RoomType type) {
		String gm = type.toString().toLowerCase();
		return Arenagames.plugin.getConfig().getInt("gamemode." + gm + ".countdown.min-players-to-start-wait");
		
	}
	
	
	public static boolean isRankedEnabled(RoomType type) {
		String gm = type.toString().toLowerCase();
		return Arenagames.plugin.getConfig().getBoolean("gamemode." + gm + ".ranked.elo-enabled");
		
	}
	
	public static boolean isEconomyEnabled(RoomType type) {
		String gm = type.toString().toLowerCase();
		return Arenagames.plugin.getConfig().getBoolean("gamemode." + gm + ".economy.currency-enabled");
		
	}
	
	public static int getNormalRooms(RoomType type) {
		String gm = type.toString().toLowerCase();
		return Arenagames.plugin.getConfig().getInt("gamemode." + gm + ".rooms.normal");
		
	}
	
	
	public static int getPremiumRooms(RoomType type) {
		String gm = type.toString().toLowerCase();
		return Arenagames.plugin.getConfig().getInt("gamemode." + gm + ".rooms.premium");
		
	}
	
	
	public static int getPlayerLimit(RoomType type) {
		String gm = type.toString().toLowerCase();
		return Arenagames.plugin.getConfig().getInt("gamemode." + gm + ".player-limit");
		
	}
	
	public static Location getGlobalLobby() {
		String w = Arenagames.plugin.getConfig().getString("global.lobby.world");
		double x = Arenagames.plugin.getConfig().getInt("global.lobby.x");
		double y = Arenagames.plugin.getConfig().getInt("global.lobby.y");
		double z = Arenagames.plugin.getConfig().getInt("global.lobby.z");
		
		return new Location(Bukkit.getWorld(w), x, y, z);
		
	}
}