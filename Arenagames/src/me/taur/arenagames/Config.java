package me.taur.arenagames;

import java.util.Arrays;
import java.util.List;

import me.taur.arenagames.ffa.FfaConfig;
import me.taur.arenagames.util.RoomType;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

	public static String[] gamemode = {"ffa"};
	
	public static void startCheck() {
		FileConfiguration config = Arenagames.plugin.getConfig();
		
		// Change every time a new gamemode is added.
		int latestVer = 1;
		
		if (config.getInt("do-not-change.current-version") != latestVer) {
			config.addDefault("do-not-change.current-version", latestVer);
			
			for (int i = 0; i < gamemode.length; i++) {
				String gm = gamemode[i];
				config.addDefault("gamemode." + gm + ".enabled", true);
				config.addDefault("gamemode." + gm + ".countdown.round-in-seconds", 240);
				config.addDefault("gamemode." + gm + ".countdown.wait-in-seconds", 45);
				config.addDefault("gamemode." + gm + ".countdown.min-players-to-start-wait", 2);
				config.addDefault("gamemode." + gm + ".rooms.normal", 2);
				config.addDefault("gamemode." + gm + ".rooms.premium", 1);
				config.addDefault("gamemode." + gm + ".player-limit", 18);
				
			}
			
			config.addDefault("global.lobby.world", "world");
			config.addDefault("global.lobby.x", 5.0);
			config.addDefault("global.lobby.y", 70.0);
			config.addDefault("global.lobby.z", 0.0);
			
			config.options().copyDefaults(true);
		    Arenagames.plugin.saveConfig();
		    
		}
		
		FileConfiguration ffaConfig = FfaConfig.get();
		ffaConfig.addDefault("generate-default-config", false);
		
		if (ffaConfig.getBoolean("generate-default-config")) {
			ffaConfig.set("generate-default-config", false);
			
			ffaConfig.addDefault("ffa.maps.edit.info.map-name", "Llamarena");
			ffaConfig.addDefault("ffa.maps.edit.info.author", "Taur and the Animals");
			
			ffaConfig.addDefault("ffa.maps.edit.info.premium-mode-pool", false);
			ffaConfig.addDefault("ffa.maps.edit.info.normal-mode-pool", true);
			
			ffaConfig.addDefault("ffa.maps.edit.spawns.loc-0.spawn.world", "world");
			ffaConfig.addDefault("ffa.maps.edit.spawns.loc-0.spawn.x", -50.0);
			ffaConfig.addDefault("ffa.maps.edit.spawns.loc-0.spawn.y", 70.0);
			ffaConfig.addDefault("ffa.maps.edit.spawns.loc-0.spawn.z", -50.0);
			
			ffaConfig.addDefault("ffa.maps.edit.spawns.loc-1.spawn.world", "world");
			ffaConfig.addDefault("ffa.maps.edit.spawns.loc-1.spawn.x", -50.0);
			ffaConfig.addDefault("ffa.maps.edit.spawns.loc-1.spawn.y", 70.0);
			ffaConfig.addDefault("ffa.maps.edit.spawns.loc-1.spawn.z", 50.0);
			
			ffaConfig.addDefault("ffa.maps.edit.spawns.loc-2.spawn.world", "world");
			ffaConfig.addDefault("ffa.maps.edit.spawns.loc-2.spawn.x", 50.0);
			ffaConfig.addDefault("ffa.maps.edit.spawns.loc-2.spawn.y", 70.0);
			ffaConfig.addDefault("ffa.maps.edit.spawns.loc-2.spawn.z", 50.0);
			
			ffaConfig.addDefault("ffa.maps.edit.spawns.loc-3.spawn.world", "world");
			ffaConfig.addDefault("ffa.maps.edit.spawns.loc-3.spawn.x", 50.0);
			ffaConfig.addDefault("ffa.maps.edit.spawns.loc-3.spawn.y", 70.0);
			ffaConfig.addDefault("ffa.maps.edit.spawns.loc-3.spawn.z", -50.0);
			
			ffaConfig.addDefault("ffa.items.kit-0.kit-name", "Fighter");
			List<String> fighterItems = Arrays.asList("IRON_SWORD|KNOCKBACK:1,DAMAGE_ALL:1#1", "STONE_SWORD|FIRE_ASPECT:1#1", "IRON_CHESTPLATE:241|DURABILITY:10#1", "COOKIE#32", "GOLDEN_APPLE:1#1");
			ffaConfig.addDefault("ffa.items.kit-0.items", fighterItems);
			
			ffaConfig.addDefault("ffa.items.kit-1.kit-name", "Archer");
			List<String> archerItems = Arrays.asList("BOW|ARROW_KNOCKBACK:2,ARROW_INFINITE:1#1", "BOW:40|ARROW_KNOCKBACK:5#1", "CHAINMAIL_CHESTPLATE:241|DURABILITY:10#1", "COOKIE#32", "GOLDEN_APPLE:1#1");
			ffaConfig.addDefault("ffa.items.kit-1.items", archerItems);
			
			ffaConfig.addDefault("ffa.maps.edit.spawns.loc-3.spawn.x", 50.0);
			ffaConfig.addDefault("ffa.maps.edit.spawns.loc-3.spawn.y", 70.0);
			ffaConfig.addDefault("ffa.maps.edit.spawns.loc-3.spawn.z", -50.0);
			
			ffaConfig.addDefault("ffa.lobby.world", "world");
			ffaConfig.addDefault("ffa.lobby.x", 0.0);
			ffaConfig.addDefault("ffa.lobby.y", 70.0);
			ffaConfig.addDefault("ffa.lobby.z", 0.0);
			
		}
		
		ffaConfig.options().copyDefaults(true);
		FfaConfig.save();
	    
	}
	
	public static boolean isEnabled(RoomType type) {
		String gm = type.toString().toLowerCase();
		return Arenagames.plugin.getConfig().getBoolean("gamemode." + gm + ".enabled");
		
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
		
		return new Location(Bukkit.getWorld(w), x + 0.5, y, z + 0.5);
		
	}
	
}
