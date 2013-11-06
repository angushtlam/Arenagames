package me.taur.arenagames.lfl;

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

public class LflConfig {
	private static FileConfiguration config = null;
	private static File file = null;
	
	private static String filename = "lfl.yml";

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
		}
		catch (IOException ex) {
			Logger.getLogger(JavaPlugin.class.getName()).log(java.util.logging.Level.SEVERE, "Could not save config to " + file, ex);
		}
	}
	
	public static int getCrankedTimer() {
		return get().getInt("lfl.settings.cranked-timer");
		
	}
	
	public static Location getPossibleSpawnLocation(LflRoom room) {
		String map = room.getMapName();
		int spawns = get().getConfigurationSection("lfl.maps." + map + ".spawns").getKeys(false).size();
		
		String w;
		double x, y, z;
		
		if (spawns == 1) {
			w = get().getString("lfl.maps." + map + ".spawns.loc-0.spawn.world");
			x = get().getInt("lfl.maps." + map + ".spawns.loc-0.spawn.x");
			y = get().getInt("lfl.maps." + map + ".spawns.loc-0.spawn.y");
			z = get().getInt("lfl.maps." + map + ".spawns.loc-0.spawn.z");
			
		} else {
			Random rand = new Random();
			int r = rand.nextInt(spawns);
			
			w = get().getString("lfl.maps." + map + ".spawns.loc-" + r + ".spawn.world");
			x = get().getInt("lfl.maps." + map + ".spawns.loc-" + r + ".spawn.x");
			y = get().getInt("lfl.maps." + map + ".spawns.loc-" + r + ".spawn.y");
			z = get().getInt("lfl.maps." + map + ".spawns.loc-" + r + ".spawn.z");
			
		}
		
		return new Location(Bukkit.getWorld(w), x + 0.5, y, z + 0.5);
	}
	
	public static boolean canPremiumPlayMap(String map) {
		return get().getBoolean("lfl.maps." + map + ".info.premium-mode-pool", false);
		
	}
	
	public static boolean canNormalPlayMap(String map) {
		return get().getBoolean("lfl.maps." + map + ".info.normal-mode-pool", false);
		
	}
	
	public static ConfigurationSection getSigns(String queue) {
		return get().getConfigurationSection("lfl.queue." + queue + ".signs");
		
	}
	
	public static Location[] getSignsStored(String queue) {
		ConfigurationSection sign = get().getConfigurationSection("lfl.queue." + queue + ".signs");
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
		get().set("lfl.queue." + queue + ".signs.sign-" + signnum + ".world", loc.getWorld().getName());
		get().set("lfl.queue." + queue + ".signs.sign-" + signnum + ".x", loc.getBlockX());
		get().set("lfl.queue." + queue + ".signs.sign-" + signnum + ".y", loc.getBlockY());
		get().set("lfl.queue." + queue + ".signs.sign-" + signnum + ".z", loc.getBlockZ());
		save();
		
	}
	
	public static void clearSignLocations(String queue) {
		get().set("lfl.queue." + queue + ".signs", null);
		save();
		
	}
	
	public static ConfigurationSection getKits() {
		return get().getConfigurationSection("lfl.items");
		
	}
	
	public static List<String> getKitItems(int kit) {
		return get().getStringList("lfl.items.kit-" + kit + ".items");
		
	}
	
	public static List<String> getKitRefill(int kit) {
		return get().getStringList("lfl.items.kit-" + kit + ".refill");
		
	}
	
	public static String getKitName(int kit) {
		return get().getString("lfl.items.kit-" + kit + ".kit-name");
		
	}
	
	public static String getKitDescription(int kit) {
		return get().getString("lfl.items.kit-" + kit + ".kit-description");
		
	}
	
	public static Material getKitMenuIcon(int kit) {
		String mat = get().getString("lfl.items.kit-" + kit + ".kit-menu-icon");
		Material material = Material.getMaterial(mat);
		
		if (material != null) { // Make sure the material is a valid material.
			return material;
			
		}
		
		return Material.SPONGE; // Debug check
		
	}
	
	public static boolean isKitPremium(int kit) {
		return get().getBoolean("lfl.items.kit-" + kit + ".premium-only");
		
	}
	
	public static Location getLobby() {
		String w = get().getString("lfl.lobby.world");
		double x = get().getInt("lfl.lobby.x");
		double y = get().getInt("lfl.lobby.y");
		double z = get().getInt("lfl.lobby.z");
		
		return new Location(Bukkit.getWorld(w), x + 0.5, y, z + 0.5);
		
	}
	
	public static void defaultConf() {
		if (get().getBoolean("generate-default-config")) {
			get().addDefault("lfl.settings.cranked-timer", 30);
			
			get().addDefault("lfl.maps.edit.info.map-name", "Llamarena");
			get().addDefault("lfl.maps.edit.info.author", "Taur and the Animals");
			
			get().addDefault("lfl.maps.edit.info.premium-mode-pool", false);
			get().addDefault("lfl.maps.edit.info.normal-mode-pool", true);
			
			get().addDefault("lfl.maps.edit.spawns.loc-0.spawn.world", "world");
			get().addDefault("lfl.maps.edit.spawns.loc-0.spawn.x", -50.0);
			get().addDefault("lfl.maps.edit.spawns.loc-0.spawn.y", 70.0);
			get().addDefault("lfl.maps.edit.spawns.loc-0.spawn.z", -50.0);
			
			get().addDefault("lfl.maps.edit.spawns.loc-1.spawn.world", "world");
			get().addDefault("lfl.maps.edit.spawns.loc-1.spawn.x", -50.0);
			get().addDefault("lfl.maps.edit.spawns.loc-1.spawn.y", 70.0);
			get().addDefault("lfl.maps.edit.spawns.loc-1.spawn.z", 50.0);
			
			get().addDefault("lfl.maps.edit.spawns.loc-2.spawn.world", "world");
			get().addDefault("lfl.maps.edit.spawns.loc-2.spawn.x", 50.0);
			get().addDefault("lfl.maps.edit.spawns.loc-2.spawn.y", 70.0);
			get().addDefault("lfl.maps.edit.spawns.loc-2.spawn.z", 50.0);
			
			get().addDefault("lfl.maps.edit.spawns.loc-3.spawn.world", "world");
			get().addDefault("lfl.maps.edit.spawns.loc-3.spawn.x", 50.0);
			get().addDefault("lfl.maps.edit.spawns.loc-3.spawn.y", 70.0);
			get().addDefault("lfl.maps.edit.spawns.loc-3.spawn.z", -50.0);
			
			get().addDefault("lfl.items.kit-0.kit-name", "Fighter");
			get().addDefault("lfl.items.kit-0.kit-description", "Fight enemies toe to toe!");
			get().addDefault("lfl.items.kit-0.kit-menu-icon", "IRON_SWORD");
			get().addDefault("lfl.items.kit-0.premium-only", false);
			List<String> fighterItems = Arrays.asList("IRON_SWORD|KNOCKBACK:1,DAMAGE_ALL:1#1", "STONE_SWORD|FIRE_ASPECT:1#1", "IRON_CHESTPLATE:-1|DURABILITY:10#1", "COOKIE#16", "GOLDEN_APPLE:1#1");
			get().addDefault("lfl.items.kit-0.items", fighterItems);
			List<String> fighterRefill = Arrays.asList("COOKIE#16", "GOLDEN_APPLE:1#1");
			get().addDefault("lfl.items.kit-0.refill", fighterRefill);
			
			get().addDefault("lfl.items.kit-1.kit-name", "Archer");
			get().addDefault("lfl.items.kit-1.kit-description", "Kill enemies from afar!");
			get().addDefault("lfl.items.kit-1.kit-menu-icon", "BOW");
			get().addDefault("lfl.items.kit-1.premium-only", true);
			List<String> archerItems = Arrays.asList("BOW|ARROW_INFINITE:1#1", "BOW:40|ARROW_KNOCKBACK:5#1", "ARROW#40", "CHAINMAIL_CHESTPLATE|DURABILITY:10#1", "COOKIE#16", "GOLDEN_APPLE:1#1");
			get().addDefault("lfl.items.kit-1.items", archerItems);
			List<String> archerRefill = Arrays.asList("COOKIE#16", "ARROW#40", "GOLDEN_APPLE:1#1");
			get().addDefault("lfl.items.kit-1.refill", archerRefill);
			
			get().addDefault("lfl.lobby.world", "world");
			get().addDefault("lfl.lobby.x", 5.0);
			get().addDefault("lfl.lobby.y", 70.0);
			get().addDefault("lfl.lobby.z", 0.0);
			
		}
		
		get().options().copyDefaults(true);
		
		get().set("generate-default-config", false);
		save();
		
	}
	
}
