package me.taur.arenagames.tdm;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.taur.arenagames.Arenagames;

public class TdmConfig {

	private static FileConfiguration tdmConfig = null;
	private static File tdmFile = null;

	public static void reload() {
		if (tdmFile == null) {
			tdmFile = new File(Arenagames.plugin.getDataFolder(), "tdm.yml");
		}
		tdmConfig = YamlConfiguration.loadConfiguration(tdmFile);

		InputStream defConfigStream = Arenagames.plugin.getResource("tdm.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			tdmConfig.setDefaults(defConfig);
		}
	}

	public static FileConfiguration get() {
		if (tdmConfig == null) {
			reload();
		}
		return tdmConfig;
	}

	public static void save() {
		if ((tdmConfig == null) || (tdmFile == null)) {
			return;
		}
		try {
			tdmConfig.save(tdmFile);
		}
		catch (IOException ex) {
			Logger.getLogger(JavaPlugin.class.getName()).log(java.util.logging.Level.SEVERE, "Could not save config to " + tdmFile, ex);
		}
	}
	
	public static boolean modeEnabled() {
		return Arenagames.plugin.getConfig().getBoolean("gamemode.tdm.enabled");
		
	}
	
	public static int normalRoomCount() {
		return Arenagames.plugin.getConfig().getInt("gamemode.tdm.rooms.normal");
		
	}
	
	public static int vipRoomCount() {
		return Arenagames.plugin.getConfig().getInt("gamemode.tdm.rooms.vip");
		
	}
	
	public static int maxPlayers() {
		return Arenagames.plugin.getConfig().getInt("gamemode.tdm.player-limit");
		
	}
	
	public static void setRedSpawn(World w, double x, double y, double z) {
		
		
	}
	
	public static Location getRedSpawn(int arenanum) {
		String w = get().getString("tdm.maps." + arenanum + ".world");
		double x = get().getInt("tdm.maps." + arenanum + ".red-spawn.x");
		double y = get().getInt("tdm.maps." + arenanum + ".red-spawn.y");
		double z = get().getInt("tdm.maps." + arenanum + ".red-spawn.z");
		
		return new Location(Bukkit.getWorld(w), x, y, z);
		
	}
	
	public static Location getBlueSpawn(int arenanum) {
		String w = get().getString("tdm.maps." + arenanum + ".world");
		double x = get().getInt("tdm.maps." + arenanum + ".blue-spawn.x");
		double y = get().getInt("tdm.maps." + arenanum + ".blue-spawn.y");
		double z = get().getInt("tdm.maps." + arenanum + ".blue-spawn.z");
		
		return new Location(Bukkit.getWorld(w), x, y, z);
		
	}
	
	public static String getMapName(int arenanum) {
		return get().getString("tdm.maps." + arenanum + ".name");
		
	}
	
	public static String getAuthor(int arenanum) {
		return get().getString("tdm.maps." + arenanum + ".author");
		
	}
	
	public static Location getLobby() {
		String w = get().getString("tdm.lobby.world");
		double x = get().getInt("tdm.lobby.spawn.x");
		double y = get().getInt("tdm.lobby.spawn.y");
		double z = get().getInt("tdm.lobby.spawn.z");
		
		return new Location(Bukkit.getWorld(w), x, y, z);
		
	}
	
}
