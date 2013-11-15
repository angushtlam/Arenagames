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
				data.setMoneySpent(conf.getDouble("user.premium.money-spent"));
				
				data.setModeratorRank(conf.getInt("user.moderation.mod-rank"));
				data.setViolationLevel(conf.getInt("user.moderation.violation"));
				
				data.setCurrency(conf.getInt("user.economy.currency"));
				data.setCurrencyLifetime(conf.getInt("user.economy.currency-lifetime"));
				data.setCash(conf.getInt("user.economy.cash"));
				data.setCashLifetime(conf.getInt("user.economy.cash-lifetime"));
				
				data.setFfaGamesWon(conf.getInt("user.ffa.games-won"));
				data.setFfaGamesPlayed(conf.getInt("user.ffa.games-played"));
				data.setFfaRanking(conf.getInt("user.ffa.ranking"));
				data.setFfaRecord(conf.getInt("user.ffa.record"));
				data.setFfaTotalKills(conf.getInt("user.ffa.kills"));
				data.setFfaTotalDeaths(conf.getInt("user.ffa.deaths"));
				data.setFfaCurrencyEarned(conf.getInt("user.ffa.currency-earned"));
				
				data.setLflGamesWon(conf.getInt("user.lfl.games-won"));
				data.setLflGamesPlayed(conf.getInt("user.lfl.games-played"));
				data.setLflRanking(conf.getInt("user.lfl.ranking"));
				data.setLflRecord(conf.getInt("user.lfl.record"));
				data.setLflTotalKills(conf.getInt("user.lfl.kills"));
				data.setLflTotalDeaths(conf.getInt("user.lfl.deaths"));
				data.setLflCurrencyEarned(conf.getInt("user.lfl.currency-earned"));
				
				data.setPerkTrailWater(conf.getInt("user.perk.trail.water"));
				data.setPerkTrailCloud(conf.getInt("user.perk.trail.cloud"));
				data.setPerkTrailMystic(conf.getInt("user.perk.trail.mystic"));
				data.setPerkTrailStar(conf.getInt("user.perk.trail.star"));
				data.setPerkTrailFlame(conf.getInt("user.perk.trail.flame"));
				data.setPerkTrailBlood(conf.getInt("user.perk.trail.blood"));
				data.setPerkTrailHeart(conf.getInt("user.perk.trail.heart"));
				
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
				conf.set("user.player.name", p.getName());
				conf.set("user.player.uuid", data.getMojangUUID());
				conf.set("user.player.exp", data.getExp());
				
				conf.set("user.time.first-joined", data.getFirstJoined());
				conf.set("user.time.last-login", data.getLastLogin());
				
				conf.set("user.premium.recent-payment", data.getRecentPremiumPayment());
				conf.set("user.premium.for-months", data.getPremiumForMonths());
				conf.set("user.premium.money-spent", data.getMoneySpent());
				
				conf.set("user.moderation.mod-rank", data.getModeratorRank());
				conf.set("user.moderation.violation", data.getViolationLevel());
				
				conf.set("user.economy.currency", data.getCurrency());
				conf.set("user.economy.currency-lifetime", data.getCurrencyLifetime());
				conf.set("user.economy.cash", data.getCash());
				conf.set("user.economy.cash-lifetime", data.getCashLifetime());
				
				conf.set("user.ffa.games-won", data.getFfaGamesWon());
				conf.set("user.ffa.games-played", data.getFfaGamesPlayed());
				conf.set("user.ffa.ranking", data.getFfaRanking());
				conf.set("user.ffa.record", data.getFfaRecord());
				conf.set("user.ffa.kills", data.getFfaTotalKills());
				conf.set("user.ffa.deaths", data.getFfaTotalDeaths());
				conf.set("user.ffa.currency-earned", data.getFfaCurrencyEarned());
				
				conf.set("user.lfl.games-won", data.getLflGamesWon());
				conf.set("user.lfl.games-played", data.getLflGamesPlayed());
				conf.set("user.lfl.ranking", data.getLflRanking());
				conf.set("user.lfl.record", data.getLflRecord());
				conf.set("user.lfl.kills", data.getLflTotalKills());
				conf.set("user.lfl.deaths", data.getLflTotalDeaths());
				conf.set("user.lfl.currency-earned", data.getLflCurrencyEarned());
				
				conf.set("user.perk.trail.water", data.getPerkTrailWater());
				conf.set("user.perk.trail.cloud", data.getPerkTrailCloud());
				conf.set("user.perk.trail.mystic", data.getPerkTrailMystic());
				conf.set("user.perk.trail.star", data.getPerkTrailStar());
				conf.set("user.perk.trail.flame", data.getPerkTrailFlame());
				conf.set("user.perk.trail.blood", data.getPerkTrailBlood());
				conf.set("user.perk.trail.heart", data.getPerkTrailHeart());
				
				conf.save(file);
				return true;
				
			} catch (Exception e) {

			}
		}
		
		return false;
	}
}