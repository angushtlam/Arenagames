package me.taur.arenagames.player;

import java.io.File;

import me.taur.arenagames.Arenagames;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PlayerDataUtil {
	public static boolean USE_FLATFILE = true;
	
	public static boolean createFile(Player p) { // Also loads the file and adds new unwritten values.
		if (USE_FLATFILE) {
			File dir = new File(Arenagames.plugin.getDataFolder(), "players");
			if (!dir.exists()) {
				dir.mkdir();

			}

			File file = new File(Arenagames.plugin.getDataFolder(), "players/" + p.getName().toLowerCase() + ".yml");
			if (!file.exists()) {
				try {
					file.createNewFile();
					FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
					conf.addDefault("user.player.name", p.getName());
					conf.addDefault("user.player.uuid", "-1");
					conf.addDefault("user.player.exp", 0);
					
					conf.addDefault("user.time.first-joined", p.getFirstPlayed());
					conf.addDefault("user.time.last-login", p.getFirstPlayed());
					
					conf.addDefault("user.premium.recent-payment", 0);
					conf.addDefault("user.premium.for-months", 0);
					conf.addDefault("user.premium.money-spent", 0.0);
					
					conf.addDefault("user.moderation.mod-rank", 0);
					conf.addDefault("user.moderation.violation", 0);
					
					conf.addDefault("user.economy.currency", 0);
					conf.addDefault("user.economy.currency-lifetime", 0);
					conf.addDefault("user.economy.cash", 0);
					conf.addDefault("user.economy.cash-lifetime", 0);
					
					conf.addDefault("user.ffa.games-won", 0);
					conf.addDefault("user.ffa.games-played", 0);
					conf.addDefault("user.ffa.ranking", 1000);
					conf.addDefault("user.ffa.record", 0);
					conf.addDefault("user.ffa.kills", 0);
					conf.addDefault("user.ffa.deaths", 0);
					conf.addDefault("user.ffa.currency-earned", 0);
					
					conf.addDefault("user.lfl.games-won", 0);
					conf.addDefault("user.lfl.games-played", 0);
					conf.addDefault("user.lfl.ranking", 1000);
					conf.addDefault("user.lfl.record", 0);
					conf.addDefault("user.lfl.kills", 0);
					conf.addDefault("user.lfl.deaths", 0);
					conf.addDefault("user.lfl.currency-earned", 0);
					
					conf.addDefault("user.perk.pet.chicken", 0);
					conf.addDefault("user.perk.pet.cow", 0);
					conf.addDefault("user.perk.pet.ocelot", 0);
					conf.addDefault("user.perk.pet.pig", 0);
					conf.addDefault("user.perk.pet.sheep", 0);
					conf.addDefault("user.perk.pet.horse", 0);
					conf.addDefault("user.perk.pet.bat", 0);
					conf.addDefault("user.perk.pet.mooshroom", 0);
					conf.addDefault("user.perk.pet.wolf", 0);
					conf.addDefault("user.perk.pet.slime", 0);
					conf.addDefault("user.perk.pet.zombie", 0);
					conf.addDefault("user.perk.pet.silverfish", 0);
					conf.addDefault("user.perk.pet.magma-cube", 0);
					conf.addDefault("user.perk.pet.iron-golem", 0);
					
					conf.addDefault("user.perk.trail.water", 0);
					conf.addDefault("user.perk.trail.cloud", 0);
					conf.addDefault("user.perk.trail.mystic", 0);
					conf.addDefault("user.perk.trail.star", 0);
					conf.addDefault("user.perk.trail.flame", 0);
					conf.addDefault("user.perk.trail.blood", 0);
					conf.addDefault("user.perk.trail.heart", 0);
					
					conf.options().copyDefaults(true);
					conf.save(file);

					return true;
					
				} catch (Exception e) {

				}
				
			}
		}
		
		return false;
		
	}
	
	public static boolean loadData(Player p) {
		if (USE_FLATFILE) {
			File dir = new File(Arenagames.plugin.getDataFolder(), "players");
			if (!dir.exists()) {
				createFile(p);

			}

			File file = new File(Arenagames.plugin.getDataFolder(), "players/" + p.getName().toLowerCase() + ".yml");
			if (!file.exists()) {
				createFile(p);

			} else {
				PlayerData data = null;
				if (!PlayerData.isLoaded(p)) { // Check 
					data = new PlayerData(p);
				}
				
				data = PlayerData.get(p);
				
				FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
				data.setPlayerName(conf.getString("user.player.name"));
				data.setMojangUUID(conf.getString("user.player.uuid"));
				data.setExp(conf.getInt("user.player.exp"));
				
				data.setFirstJoined(conf.getLong("user.time.first-joined"));
				data.setLastLogin(conf.getLong("user.time.last-login"));
				
				data.setRecentPremiumPayment(conf.getLong("user.premium.recent-payment"));
				data.setPremiumForMonths(conf.getInt("user.premium.for-months"));
				data.setMoneySpentInUSD(conf.getDouble("user.premium.money-spent"));
				
				data.setModeratorRank(conf.getInt("user.moderation.mod-rank"));
				data.setViolationLevel(conf.getInt("user.moderation.violation"));
				
				data.setCurrency(conf.getInt("user.economy.currency"));
				data.setCurrencyLifetime(conf.getInt("user.economy.currency-lifetime"));
				data.setCash(conf.getInt("user.economy.cash"));
				data.setCashLifetime(conf.getInt("user.economy.cash-lifetime"));
				
				conf.getInt("user.ffa.games-won");
				conf.getInt("user.ffa.games-played");
				conf.getInt("user.ffa.ranking");
				conf.getInt("user.ffa.record");
				conf.getInt("user.ffa.kills");
				conf.getInt("user.ffa.deaths");
				conf.getInt("user.ffa.currency-earned");
				
				conf.getInt("user.lfl.games-won");
				conf.getInt("user.lfl.games-played");
				conf.getInt("user.lfl.ranking");
				conf.getInt("user.lfl.record");
				conf.getInt("user.lfl.kills");
				conf.getInt("user.lfl.deaths");
				conf.getInt("user.lfl.currency-earned");
				
				conf.getInt("user.perk.pet.chicken");
				conf.getInt("user.perk.pet.cow");
				conf.getInt("user.perk.pet.ocelot");
				conf.getInt("user.perk.pet.pig");
				conf.getInt("user.perk.pet.sheep");
				conf.getInt("user.perk.pet.horse");
				conf.getInt("user.perk.pet.bat");
				conf.getInt("user.perk.pet.mooshroom");
				conf.getInt("user.perk.pet.wolf");
				conf.getInt("user.perk.pet.slime");
				conf.getInt("user.perk.pet.zombie");
				conf.getInt("user.perk.pet.silverfish");
				conf.getInt("user.perk.pet.magma-cube");
				conf.getInt("user.perk.pet.iron-golem");
				
				conf.getInt("user.perk.trail.water");
				conf.getInt("user.perk.trail.cloud");
				conf.getInt("user.perk.trail.mystic");
				conf.getInt("user.perk.trail.star");
				conf.getInt("user.perk.trail.flame");
				conf.getInt("user.perk.trail.blood");
				conf.getInt("user.perk.trail.heart");
				
				return true;
			}
		}
		
		return false;
		
	}
	
	public static boolean save(Player p) {
		if (USE_FLATFILE) {
			File dir = new File(Arenagames.plugin.getDataFolder(), "players");
			if (!dir.exists()) {
				dir.mkdir();

			}

			File file = new File(Arenagames.plugin.getDataFolder(), "players/" + p.getName().toLowerCase() + ".yml");
			try {
				file.createNewFile();
				
				PlayerData data = null;
				if (!PlayerData.isLoaded(p)) { // Check 
					data = new PlayerData(p);
				}
				
				data = PlayerData.get(p);
				
				FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
//				conf.set("user.name", p.getName());
//				conf.set("user.premium", this.isPremium());
//
//				conf.set("user.ffa.games", this.getFfaGamesPlayed());
//				conf.set("user.ffa.elo", this.getFfaEloRank());
//				conf.set("user.ffa.record", this.getFfaRecord());
//				
//				conf.set("user.lfl.games", this.getLflGamesPlayed());
//				conf.set("user.lfl.elo", this.getLflEloRank());
//				conf.set("user.lfl.record", this.getLflRecord());
				conf.save(file);

				return true;
				
			} catch (Exception e) {

			}
		}
		
		return false;
		
	}
}