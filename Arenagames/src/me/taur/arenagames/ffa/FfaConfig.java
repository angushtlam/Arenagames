package me.taur.arenagames.ffa;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
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
	private static FileConfiguration config = null;
	private static File file = null;
	
	private static String filename = "ffa.yml";

	public static void reload() {
		if (file == null) {
			file = new File(Arenagames.plugin.getDataFolder(), filename);
		}
		
		config = YamlConfiguration.loadConfiguration(file);

		InputStream defConfigStream = Arenagames.plugin.getResource(filename);
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			config.setDefaults(defConfig);
		}
	}

	public static FileConfiguration get() {
		if (config == null) {
			reload();
		}
		
		return config;
	}

	public static void save() {
		if ((config == null) || (file == null)) {
			return;
		}
		
		try {
			config.save(file);
		} catch (IOException ex) {
			Logger.getLogger(JavaPlugin.class.getName()).log(java.util.logging.Level.SEVERE, "Could not save config to " + file, ex);
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
	
	public static ConfigurationSection getSigns(String queue) {
		return get().getConfigurationSection("ffa.queue." + queue + ".signs");
	}
	
	public static Location[] getSignsStored(String queue) {
		ConfigurationSection sign = get().getConfigurationSection("ffa.queue." + queue + ".signs");
		if (sign != null) {
			int size = sign.getKeys(false).size();
			Location[] locs = new Location[size];
			for (int i = 0; i < size; i++) {
				if (sign.get("sign-" + i) != null) {
					String w = sign.getString("sign-" + i + ".world");
					double x = sign.getDouble("sign-" + i + ".x");
					double y = sign.getDouble("sign-" + i + ".y");
					double z = sign.getDouble("sign-" + i + ".z");
					
					Location loc = new Location(Bukkit.getWorld(w), x, y, z);
					locs[i] = loc;
					
				}
			}
			
			return locs;
		}
		
		return null;
	}
	
	public static void setSignLocation(String queue, int signnum, Location loc) {
		get().set("ffa.queue." + queue + ".signs.sign-" + signnum + ".world", loc.getWorld().getName());
		get().set("ffa.queue." + queue + ".signs.sign-" + signnum + ".x", loc.getBlockX());
		get().set("ffa.queue." + queue + ".signs.sign-" + signnum + ".y", loc.getBlockY());
		get().set("ffa.queue." + queue + ".signs.sign-" + signnum + ".z", loc.getBlockZ());
		save();
		
	}
	
	public static void clearSignLocations(String queue) {
		get().set("ffa.queue." + queue + ".signs", null);
		save();
		
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
	
	public static void defaultConf() {
		if (get().getBoolean("generate-default-config")) {

			get().addDefault("ffa.maps.edit.info.map-name", "Llamarena");
			get().addDefault("ffa.maps.edit.info.author", "Taur and the Animals");
			
			get().addDefault("ffa.maps.edit.info.premium-mode-pool", false);
			get().addDefault("ffa.maps.edit.info.normal-mode-pool", true);
			
			get().addDefault("ffa.maps.edit.spawns.loc-0.spawn.world", "world");
			get().addDefault("ffa.maps.edit.spawns.loc-0.spawn.x", -50.0);
			get().addDefault("ffa.maps.edit.spawns.loc-0.spawn.y", 70.0);
			get().addDefault("ffa.maps.edit.spawns.loc-0.spawn.z", -50.0);
			
			get().addDefault("ffa.maps.edit.spawns.loc-1.spawn.world", "world");
			get().addDefault("ffa.maps.edit.spawns.loc-1.spawn.x", -50.0);
			get().addDefault("ffa.maps.edit.spawns.loc-1.spawn.y", 70.0);
			get().addDefault("ffa.maps.edit.spawns.loc-1.spawn.z", 50.0);
			
			get().addDefault("ffa.maps.edit.spawns.loc-2.spawn.world", "world");
			get().addDefault("ffa.maps.edit.spawns.loc-2.spawn.x", 50.0);
			get().addDefault("ffa.maps.edit.spawns.loc-2.spawn.y", 70.0);
			get().addDefault("ffa.maps.edit.spawns.loc-2.spawn.z", 50.0);
			
			get().addDefault("ffa.maps.edit.spawns.loc-3.spawn.world", "world");
			get().addDefault("ffa.maps.edit.spawns.loc-3.spawn.x", 50.0);
			get().addDefault("ffa.maps.edit.spawns.loc-3.spawn.y", 70.0);
			get().addDefault("ffa.maps.edit.spawns.loc-3.spawn.z", -50.0);
			
			get().addDefault("ffa.items.kit-0.kit-name", "Fighter");
			get().addDefault("ffa.items.kit-0.kit-description", "Fight enemies toe to toe!");
			get().addDefault("ffa.items.kit-0.kit-menu-icon", "IRON_SWORD");
			get().addDefault("ffa.items.kit-0.premium-only", false);
			List<String> fighterItems = Arrays.asList("IRON_SWORD|KNOCKBACK:1,DAMAGE_ALL:1#1", "STONE_SWORD|FIRE_ASPECT:1#1", "IRON_CHESTPLATE:-1|DURABILITY:10#1", "COOKIE#32", "GOLDEN_APPLE:1#1");
			get().addDefault("ffa.items.kit-0.items", fighterItems);
			
			get().addDefault("ffa.items.kit-1.kit-name", "Archer");
			get().addDefault("ffa.items.kit-1.kit-description", "Kill enemies from afar!");
			get().addDefault("ffa.items.kit-1.kit-menu-icon", "BOW");
			get().addDefault("ffa.items.kit-1.premium-only", true);
			List<String> archerItems = Arrays.asList("BOW|ARROW_INFINITE:1#1", "BOW:40|ARROW_KNOCKBACK:5#1", "CHAINMAIL_CHESTPLATE|DURABILITY:10#1", "COOKIE#32", "GOLDEN_APPLE:1#1");
			get().addDefault("ffa.items.kit-1.items", archerItems);
			
			get().addDefault("ffa.lobby.world", "world");
			get().addDefault("ffa.lobby.x", 5.0);
			get().addDefault("ffa.lobby.y", 70.0);
			get().addDefault("ffa.lobby.z", 0.0);
			
		}
		
		get().options().copyDefaults(true);
		get().set("generate-default-config", false);
		save();
		
	}
}
