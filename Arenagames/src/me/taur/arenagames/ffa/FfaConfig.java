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
	private static FileConfiguration dataConfig = null;
	private static File dataFile = null;
	
	private static String dataFilename = "ffa-data.yml";

	public static void reloadData() {
		if (dataFile == null) {
			dataFile = new File(Arenagames.plugin.getDataFolder(), dataFilename);
		}
		
		dataConfig = YamlConfiguration.loadConfiguration(dataFile);

		InputStream defConfigStream = Arenagames.plugin.getResource(dataFilename);
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			dataConfig.setDefaults(defConfig);
		}
	}

	public static FileConfiguration getData() {
		if (dataConfig == null) {
			reloadData();
		}
		
		return dataConfig;
	}

	public static void saveData() {
		if ((dataConfig == null) || (dataFile == null)) {
			return;
		}
		
		try {
			dataConfig.save(dataFile);
		} catch (IOException ex) {
			Logger.getLogger(JavaPlugin.class.getName()).log(java.util.logging.Level.SEVERE, "Could not save config to " + dataFile, ex);
		}
	}
	
	private static FileConfiguration kitConfig = null;
	private static File kitFile = null;
	
	private static String kitFilename = "ffa-kit.yml";

	public static void reloadKit() {
		if (kitFile == null) {
			kitFile = new File(Arenagames.plugin.getDataFolder(), kitFilename);
		}
		
		kitConfig = YamlConfiguration.loadConfiguration(kitFile);

		InputStream defConfigStream = Arenagames.plugin.getResource(kitFilename);
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			kitConfig.setDefaults(defConfig);
		}
	}

	public static FileConfiguration getKit() {
		if (kitConfig == null) {
			reloadKit();
		}
		
		return kitConfig;
	}

	public static void saveKit() {
		if ((kitConfig == null) || (kitFile == null)) {
			return;
		}
		
		try {
			kitConfig.save(kitFile);
		} catch (IOException ex) {
			Logger.getLogger(JavaPlugin.class.getName()).log(java.util.logging.Level.SEVERE, "Could not save config to " + kitFile, ex);
		}
	}
	
	public static Location getPossibleSpawnLocation(FfaRoom room) {
		String map = room.getMapName();
		int spawns = getData().getConfigurationSection("ffa.maps." + map + ".spawns").getKeys(false).size();
		
		String w;
		double x, y, z;
		
		if (spawns == 1) {
			w = getData().getString("ffa.maps." + map + ".spawns.loc-0.spawn.world");
			x = getData().getInt("ffa.maps." + map + ".spawns.loc-0.spawn.x");
			y = getData().getInt("ffa.maps." + map + ".spawns.loc-0.spawn.y");
			z = getData().getInt("ffa.maps." + map + ".spawns.loc-0.spawn.z");
			
		} else {
			Random rand = new Random();
			int r = rand.nextInt(spawns);
			
			w = getData().getString("ffa.maps." + map + ".spawns.loc-" + r + ".spawn.world");
			x = getData().getInt("ffa.maps." + map + ".spawns.loc-" + r + ".spawn.x");
			y = getData().getInt("ffa.maps." + map + ".spawns.loc-" + r + ".spawn.y");
			z = getData().getInt("ffa.maps." + map + ".spawns.loc-" + r + ".spawn.z");
			
		}
		
		return new Location(Bukkit.getWorld(w), x + 0.5, y, z + 0.5);
	}
	
	public static boolean canPremiumPlayMap(String map) {
		return getData().getBoolean("ffa.maps." + map + ".info.premium-mode-pool", false);
	}
	
	public static boolean canNormalPlayMap(String map) {
		return getData().getBoolean("ffa.maps." + map + ".info.normal-mode-pool", false);
	}
	
	public static ConfigurationSection getSigns(String queue) {
		return getData().getConfigurationSection("ffa.queue." + queue + ".signs");
	}
	
	public static Location[] getSignsStored(String queue) {
		ConfigurationSection sign = getData().getConfigurationSection("ffa.queue." + queue + ".signs");
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
		getData().set("ffa.queue." + queue + ".signs.sign-" + signnum + ".world", loc.getWorld().getName());
		getData().set("ffa.queue." + queue + ".signs.sign-" + signnum + ".x", loc.getBlockX());
		getData().set("ffa.queue." + queue + ".signs.sign-" + signnum + ".y", loc.getBlockY());
		getData().set("ffa.queue." + queue + ".signs.sign-" + signnum + ".z", loc.getBlockZ());
		saveData();
		
	}
	
	public static void clearSignLocations(String queue) {
		getData().set("ffa.queue." + queue + ".signs", null);
		saveData();
		
	}
	
	public static ConfigurationSection getKits() {
		return getKit().getConfigurationSection("ffa.items");
	}
	
	public static List<String> getKitItems(int kit) {
		return getKit().getStringList("ffa.items.kit-" + kit + ".items");
	}
	
	public static String getKitName(int kit) {
		return getKit().getString("ffa.items.kit-" + kit + ".kit-name");
	}
	
	public static String getKitDescription(int kit) {
		return getKit().getString("ffa.items.kit-" + kit + ".kit-description");
	}
	
	public static Material getKitMenuIcon(int kit) {
		String mat = getKit().getString("ffa.items.kit-" + kit + ".kit-menu-icon");
		Material material = Material.getMaterial(mat);
		
		if (material != null) { // Make sure the material is a valid material.
			return material;
		}
		
		return Material.SPONGE; // Debug check
		
	}
	
	public static boolean isKitPremium(int kit) {
		return getKit().getBoolean("ffa.items.kit-" + kit + ".premium-only");
	}
	
	public static Location getLobby() {
		String w = getData().getString("ffa.lobby.world");
		double x = getData().getInt("ffa.lobby.x");
		double y = getData().getInt("ffa.lobby.y");
		double z = getData().getInt("ffa.lobby.z");
		
		return new Location(Bukkit.getWorld(w), x + 0.5, y, z + 0.5);
		
	}
	
	public static int getCurrencyFirst() {
		return getData().getInt("ffa.currency.first");
	}
	
	public static int getCurrencyWinner() {
		return getData().getInt("ffa.currency.winner");
	}
	
	public static int getCurrencyEveryone() {
		return getData().getInt("ffa.currency.everyone");
	}
	
	public static int getExpFirst() {
		return getData().getInt("ffa.exp.first");
	}
	
	public static int getExpWinner() {
		return getData().getInt("ffa.exp.winner");
	}
	
	public static int getExpEveryone() {
		return getData().getInt("ffa.exp.everyone");
	}
	
	public static void defaultConf() {
		if (getData().getBoolean("generate-default-config")) {
			getData().addDefault("ffa.lobby.world", "world");
			getData().addDefault("ffa.lobby.x", 5.0);
			getData().addDefault("ffa.lobby.y", 70.0);
			getData().addDefault("ffa.lobby.z", 0.0);
			
			getData().addDefault("ffa.currency.first", 40);
			getData().addDefault("ffa.currency.winner", 30);
			getData().addDefault("ffa.currency.everyone", 15);
			
			getData().addDefault("ffa.exp.first", 40);
			getData().addDefault("ffa.exp.winner", 30);
			getData().addDefault("ffa.exp.everyone", 15);
			
			getData().addDefault("ffa.maps.edit.info.map-name", "Llamarena");
			getData().addDefault("ffa.maps.edit.info.author", "Taur and the Animals");
			
			getData().addDefault("ffa.maps.edit.info.premium-mode-pool", false);
			getData().addDefault("ffa.maps.edit.info.normal-mode-pool", true);
			
			getData().addDefault("ffa.maps.edit.spawns.loc-0.spawn.world", "world");
			getData().addDefault("ffa.maps.edit.spawns.loc-0.spawn.x", -50.0);
			getData().addDefault("ffa.maps.edit.spawns.loc-0.spawn.y", 70.0);
			getData().addDefault("ffa.maps.edit.spawns.loc-0.spawn.z", -50.0);
			
			getData().addDefault("ffa.maps.edit.spawns.loc-1.spawn.world", "world");
			getData().addDefault("ffa.maps.edit.spawns.loc-1.spawn.x", -50.0);
			getData().addDefault("ffa.maps.edit.spawns.loc-1.spawn.y", 70.0);
			getData().addDefault("ffa.maps.edit.spawns.loc-1.spawn.z", 50.0);
			
			getData().addDefault("ffa.maps.edit.spawns.loc-2.spawn.world", "world");
			getData().addDefault("ffa.maps.edit.spawns.loc-2.spawn.x", 50.0);
			getData().addDefault("ffa.maps.edit.spawns.loc-2.spawn.y", 70.0);
			getData().addDefault("ffa.maps.edit.spawns.loc-2.spawn.z", 50.0);
			
			getData().addDefault("ffa.maps.edit.spawns.loc-3.spawn.world", "world");
			getData().addDefault("ffa.maps.edit.spawns.loc-3.spawn.x", 50.0);
			getData().addDefault("ffa.maps.edit.spawns.loc-3.spawn.y", 70.0);
			getData().addDefault("ffa.maps.edit.spawns.loc-3.spawn.z", -50.0);
			
		}
		
		getData().options().copyDefaults(true);
		getData().set("generate-default-config", false);
		saveData();
		
		if (getKit().getBoolean("generate-default-config")) {
			getKit().addDefault("ffa.items.kit-0.kit-name", "Fighter");
			getKit().addDefault("ffa.items.kit-0.kit-description", "Fight enemies toe to toe!");
			getKit().addDefault("ffa.items.kit-0.kit-menu-icon", "IRON_SWORD");
			getKit().addDefault("ffa.items.kit-0.premium-only", false);
			List<String> fighterItems = Arrays.asList("IRON_SWORD|KNOCKBACK:1,DAMAGE_ALL:1#1", "STONE_SWORD|FIRE_ASPECT:1#1", "IRON_CHESTPLATE:-1|DURABILITY:10#1", "COOKIE#32", "GOLDEN_APPLE:1#1");
			getKit().addDefault("ffa.items.kit-0.items", fighterItems);
			
			getKit().addDefault("ffa.items.kit-1.kit-name", "Archer");
			getKit().addDefault("ffa.items.kit-1.kit-description", "Kill enemies from afar!");
			getKit().addDefault("ffa.items.kit-1.kit-menu-icon", "BOW");
			getKit().addDefault("ffa.items.kit-1.premium-only", true);
			List<String> archerItems = Arrays.asList("BOW|ARROW_INFINITE:1#1", "BOW:40|ARROW_KNOCKBACK:5#1", "CHAINMAIL_CHESTPLATE|DURABILITY:10#1", "COOKIE#32", "GOLDEN_APPLE:1#1");
			getKit().addDefault("ffa.items.kit-1.items", archerItems);
			
		}
		
		getKit().options().copyDefaults(true);
		getKit().set("generate-default-config", false);
		saveKit();
		
	}
}
