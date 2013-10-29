package me.taur.arenagames.ffa;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import me.taur.arenagames.Arenagames;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class FfaConfig {

	private static FileConfiguration ffaConfig = null;
	private static File ffaFile = null;

	public static void reload() {
		if (ffaFile == null) {
			ffaFile = new File(Arenagames.plugin.getDataFolder(), "ffa.yml");
		}
		ffaConfig = YamlConfiguration.loadConfiguration(ffaFile);

		InputStream defConfigStream = Arenagames.plugin.getResource("ffa.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			ffaConfig.setDefaults(defConfig);
		}
	}

	public static FileConfiguration get() {
		if (ffaConfig == null) {
			reload();
		}
		return ffaConfig;
	}

	public static void save() {
		if ((ffaConfig == null) || (ffaFile == null)) {
			return;
		}
		try {
			ffaConfig.save(ffaFile);
		}
		catch (IOException ex) {
			Logger.getLogger(JavaPlugin.class.getName()).log(java.util.logging.Level.SEVERE, "Could not save config to " + ffaFile, ex);
		}
	}
	
	public static Location getPossibleSpawnLocation(FfaRoom room) {
		String map = room.getMapName();
		int spawns = get().getConfigurationSection("ffa.maps." + map + ".spawns").getKeys(false).size();
		
		String w;
		double x, y, z;
		
		if (spawns == 1) {
			w = get().getString("ffa.maps." + map + ".spawns.loc-0.spawn.world");
			x = get().getInt("ffa.maps." + map + ".spawns.loc-0.spawn.x");
			y = get().getInt("ffa.maps." + map + ".spawns.loc-0.spawn.y");
			z = get().getInt("ffa.maps." + map + ".spawns.loc-0.spawn.z");
			
		} else {
			Random rand = new Random();
			int r = rand.nextInt(spawns);
			
			w = get().getString("ffa.maps." + map + ".spawns.loc-" + r + ".spawn.world");
			x = get().getInt("ffa.maps." + map + ".spawns.loc-" + r + ".spawn.x");
			y = get().getInt("ffa.maps." + map + ".spawns.loc-" + r + ".spawn.y");
			z = get().getInt("ffa.maps." + map + ".spawns.loc-" + r + ".spawn.z");
			
		}
		
		return new Location(Bukkit.getWorld(w), x + 0.5, y, z + 0.5);
	}
	
	public static boolean canPremiumPlayMap(String map) {
		return get().getBoolean("ffa.maps." + map + ".info.premium-mode-pool", false);
		
	}
	
	public static boolean canNormalPlayMap(String map) {
		return get().getBoolean("ffa.maps." + map + ".info.normal-mode-pool", false);
		
	}
	
	public static ConfigurationSection getKits() {
		return get().getConfigurationSection("ffa.items");
		
	}
	
	public static List<String> getKitItems(int kit) {
		return get().getStringList("ffa.items.kit-" + kit + ".items");
		
	}
	
	public static String getKitName(int kit) {
		return get().getString("ffa.items.kit-" + kit + ".kit-name");
		
	}
	
	public static String getKitDescription(int kit) {
		return get().getString("ffa.items.kit-" + kit + ".kit-description");
		
	}
	
	public static Material getKitMenuIcon(int kit) {
		String mat = get().getString("ffa.items.kit-" + kit + ".kit-menu-icon");
		Material material = Material.getMaterial(mat);
		
		if (material != null) { // Make sure the material is a valid material.
			return material;
			
		}
		
		return Material.SPONGE; // Debug check
		
	}
	
	public static boolean isKitPremium(int kit) {
		return get().getBoolean("ffa.items.kit-" + kit + ".premium-only");
		
	}
	
	public static Location getLobby() {
		String w = get().getString("ffa.lobby.world");
		double x = get().getInt("ffa.lobby.x");
		double y = get().getInt("ffa.lobby.y");
		double z = get().getInt("ffa.lobby.z");
		
		return new Location(Bukkit.getWorld(w), x + 0.5, y, z + 0.5);
		
	}
	
}
