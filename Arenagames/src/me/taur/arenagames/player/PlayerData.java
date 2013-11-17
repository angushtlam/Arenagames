package me.taur.arenagames.player;

import java.io.File;
import java.util.HashMap;

import me.taur.arenagames.Arenagames;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PlayerData {
	public static HashMap<String, PlayerData> STORE = new HashMap<String, PlayerData>();
	public static boolean USE_FLATFILE = true;
	
	private String playerName, mojangUUID;
	
	private long firstJoined, lastLogin, recentPremiumPayment;
	private int premiumForMonths;
	private double moneySpent;
	
	private int moderatorRank, violationLevel;
	private int currency, currencyLifetime, cash, cashLifetime, exp;
	
	private int ffaGamesWon, ffaGamesPlayed, ffaRanking, ffaRecord;
	private int ffaTotalKills, ffaTotalDeaths;
	private int ffaCurrencyEarned;
	
	private int lflGamesWon, lflGamesPlayed, lflRanking, lflRecord;
	private int lflTotalKills, lflTotalDeaths;
	private int lflCurrencyEarned;
	
	private int perkFfaFireworks, perkFfaSpawnSpeed, perkFfaSpawnInvis;
	private int perkLflFireworks, perkLflSpawnSpeed, perkLflSpawnInvis;
	
	private int perkTrailWater, perkTrailCloud, perkTrailMystic, perkTrailStar, perkTrailFlame, perkTrailBlood,
				perkTrailHeart;
	
	public PlayerData(Player p) {
		new PlayerData(p.getName());
		
	}
	
	public PlayerData(String name) {
		setPlayerName(name);
		this.createFile(name);
		this.loadData(name);
		STORE.put(name, this);
		
	}
	
	public static boolean isLoaded(Player p) {
		if (STORE.get(p.getName()) != null) {
			return true;
		}
		
		return false;
		
	}
	
	public static PlayerData get(Player p) {
		return get(p.getName());
	}
	
	public static PlayerData get(String name) {
		return STORE.get(name);
	}
	
	public static void remove(Player p) {
		STORE.remove(p.getName());
	}
	
	public static void remove(String name) {
		STORE.remove(name);
	}
	
	public boolean createFile(Player p) {
		return createFile(p.getName());
	}
	
	public boolean createFile(String name) { // Also loads the file and adds new unwritten values.
		if (USE_FLATFILE) {
			File dir = new File(Arenagames.plugin.getDataFolder(), "players");
			if (!dir.exists()) {
				dir.mkdir();
			}

			File file = new File(Arenagames.plugin.getDataFolder(), "players/" + name.toLowerCase() + ".yml");
			if (!file.exists()) {
				try {
					file.createNewFile();
					FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
					conf.addDefault("user.player.name", name);
					conf.addDefault("user.player.uuid", "-1");
					conf.addDefault("user.player.exp", 0);
					
					conf.addDefault("user.time.first-joined", 0);
					conf.addDefault("user.time.last-login", 0);
					
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

					conf.addDefault("user.ffa.perk.firework", 0);
					conf.addDefault("user.ffa.perk.spawn-speed", 0);
					conf.addDefault("user.ffa.perk.spawn-invisibility", 0);
					
					conf.addDefault("user.lfl.perk.firework", 0);
					conf.addDefault("user.lfl.perk.spawn-speed", 0);
					conf.addDefault("user.lfl.perk.spawn-invisibility", 0);
					
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
	
	public boolean loadData(Player p) {
		return loadData(p.getName());
	}
	
	public boolean loadData(String name) {
		if (USE_FLATFILE) {
			File dir = new File(Arenagames.plugin.getDataFolder(), "players");
			if (!dir.exists()) {
				createFile(name);
			}

			File file = new File(Arenagames.plugin.getDataFolder(), "players/" + name.toLowerCase() + ".yml");
			if (!file.exists()) {
				createFile(name);

			} else {
				PlayerData data = this;
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
				
				data.setPerkFfaFireworks(conf.getInt("user.perk.ffa.firework"));
				data.setPerkFfaSpawnSpeed(conf.getInt("user.perk.ffa.spawn-speed"));
				data.setPerkFfaSpawnInvis(conf.getInt("user.perk.ffa.spawn-invisibility"));
				
				data.setPerkLflFireworks(conf.getInt("user.perk.lfl.firework"));
				data.setPerkLflSpawnSpeed(conf.getInt("user.perk.lfl.spawn-speed"));
				data.setPerkLflSpawnInvis(conf.getInt("user.perk.lfl.spawn-invisibility"));
				
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
	
	public boolean save(Player p) {
		return save(p.getName());
	}
	
	public boolean save(String name) {
		if (USE_FLATFILE) {
			File dir = new File(Arenagames.plugin.getDataFolder(), "players");
			if (!dir.exists()) {
				dir.mkdir();
			}

			File file = new File(Arenagames.plugin.getDataFolder(), "players/" + name.toLowerCase() + ".yml");
			try {
				file.createNewFile();

				PlayerData data = this;
				FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
				conf.set("user.player.name", name);
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
				
				conf.set("user.perk.ffa.firework", data.getPerkFfaFireworks());
				conf.set("user.perk.ffa.spawn-speed", data.getPerkFfaSpawnSpeed());
				conf.set("user.perk.ffa.spawn-invisibility", data.getPerkFfaSpawnInvis());
				
				conf.set("user.perk.lfl.firework", data.getPerkLflFireworks());
				conf.set("user.perk.lfl.spawn-speed", data.getPerkLflSpawnSpeed());
				conf.set("user.perk.lfl.spawn-invisibility", data.getPerkLflSpawnInvis());
				
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

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getMojangUUID() {
		return mojangUUID;
	}

	public void setMojangUUID(String mojangUUID) {
		this.mojangUUID = mojangUUID;
	}

	public long getFirstJoined() {
		return firstJoined;
	}

	public void setFirstJoined(long firstJoined) {
		this.firstJoined = firstJoined;
	}

	public long getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(long lastLogin) {
		this.lastLogin = lastLogin;
	}

	public long getRecentPremiumPayment() {
		return recentPremiumPayment;
	}

	public void setRecentPremiumPayment(long recentPremiumPayment) {
		this.recentPremiumPayment = recentPremiumPayment;
	}

	public int getPremiumForMonths() {
		return premiumForMonths;
	}

	public void setPremiumForMonths(int premiumForMonths) {
		this.premiumForMonths = premiumForMonths;
	}

	public double getMoneySpent() {
		return moneySpent;
	}

	public void setMoneySpent(double moneySpent) {
		this.moneySpent = moneySpent;
	}

	public int getModeratorRank() {
		return moderatorRank;
	}

	public void setModeratorRank(int moderatorRank) {
		this.moderatorRank = moderatorRank;
	}

	public int getViolationLevel() {
		return violationLevel;
	}

	public void setViolationLevel(int violationLevel) {
		this.violationLevel = violationLevel;
	}

	public int getCurrency() {
		return currency;
	}

	public void setCurrency(int currency) {
		this.currency = currency;
	}

	public int getCurrencyLifetime() {
		return currencyLifetime;
	}

	public void setCurrencyLifetime(int currencyLifetime) {
		this.currencyLifetime = currencyLifetime;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	public int getCashLifetime() {
		return cashLifetime;
	}

	public void setCashLifetime(int cashLifetime) {
		this.cashLifetime = cashLifetime;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getFfaGamesWon() {
		return ffaGamesWon;
	}

	public void setFfaGamesWon(int ffaGamesWon) {
		this.ffaGamesWon = ffaGamesWon;
	}

	public int getFfaGamesPlayed() {
		return ffaGamesPlayed;
	}

	public void setFfaGamesPlayed(int ffaGamesPlayed) {
		this.ffaGamesPlayed = ffaGamesPlayed;
	}

	public int getFfaRanking() {
		return ffaRanking;
	}

	public void setFfaRanking(int ffaRanking) {
		this.ffaRanking = ffaRanking;
	}

	public int getFfaRecord() {
		return ffaRecord;
	}

	public void setFfaRecord(int ffaRecord) {
		this.ffaRecord = ffaRecord;
	}

	public int getFfaTotalKills() {
		return ffaTotalKills;
	}

	public void setFfaTotalKills(int ffaTotalKills) {
		this.ffaTotalKills = ffaTotalKills;
	}

	public int getFfaTotalDeaths() {
		return ffaTotalDeaths;
	}

	public void setFfaTotalDeaths(int ffaTotalDeaths) {
		this.ffaTotalDeaths = ffaTotalDeaths;
	}

	public int getFfaCurrencyEarned() {
		return ffaCurrencyEarned;
	}

	public void setFfaCurrencyEarned(int ffaCurrencyEarned) {
		this.ffaCurrencyEarned = ffaCurrencyEarned;
	}

	public int getLflGamesWon() {
		return lflGamesWon;
	}

	public void setLflGamesWon(int lflGamesWon) {
		this.lflGamesWon = lflGamesWon;
	}

	public int getLflGamesPlayed() {
		return lflGamesPlayed;
	}

	public void setLflGamesPlayed(int lflGamesPlayed) {
		this.lflGamesPlayed = lflGamesPlayed;
	}

	public int getLflRanking() {
		return lflRanking;
	}

	public void setLflRanking(int lflRanking) {
		this.lflRanking = lflRanking;
	}

	public int getLflRecord() {
		return lflRecord;
	}

	public void setLflRecord(int lflRecord) {
		this.lflRecord = lflRecord;
	}

	public int getLflTotalKills() {
		return lflTotalKills;
	}

	public void setLflTotalKills(int lflTotalKills) {
		this.lflTotalKills = lflTotalKills;
	}

	public int getLflTotalDeaths() {
		return lflTotalDeaths;
	}

	public void setLflTotalDeaths(int lflTotalDeaths) {
		this.lflTotalDeaths = lflTotalDeaths;
	}

	public int getLflCurrencyEarned() {
		return lflCurrencyEarned;
	}

	public void setLflCurrencyEarned(int lflCurrencyEarned) {
		this.lflCurrencyEarned = lflCurrencyEarned;
	}

	public int getPerkFfaFireworks() {
		return perkFfaFireworks;
	}

	public void setPerkFfaFireworks(int perkFfaFireworks) {
		this.perkFfaFireworks = perkFfaFireworks;
	}

	public int getPerkFfaSpawnSpeed() {
		return perkFfaSpawnSpeed;
	}

	public void setPerkFfaSpawnSpeed(int perkFfaSpawnSpeed) {
		this.perkFfaSpawnSpeed = perkFfaSpawnSpeed;
	}

	public int getPerkFfaSpawnInvis() {
		return perkFfaSpawnInvis;
	}

	public void setPerkFfaSpawnInvis(int perkFfaSpawnInvis) {
		this.perkFfaSpawnInvis = perkFfaSpawnInvis;
	}

	public int getPerkLflFireworks() {
		return perkLflFireworks;
	}

	public void setPerkLflFireworks(int perkLflFireworks) {
		this.perkLflFireworks = perkLflFireworks;
	}

	public int getPerkLflSpawnSpeed() {
		return perkLflSpawnSpeed;
	}

	public void setPerkLflSpawnSpeed(int perkLflSpawnSpeed) {
		this.perkLflSpawnSpeed = perkLflSpawnSpeed;
	}

	public int getPerkLflSpawnInvis() {
		return perkLflSpawnInvis;
	}

	public void setPerkLflSpawnInvis(int perkLflSpawnInvis) {
		this.perkLflSpawnInvis = perkLflSpawnInvis;
	}

	public int getPerkTrailWater() {
		return perkTrailWater;
	}

	public void setPerkTrailWater(int perkTrailWater) {
		this.perkTrailWater = perkTrailWater;
	}

	public int getPerkTrailCloud() {
		return perkTrailCloud;
	}

	public void setPerkTrailCloud(int perkTrailCloud) {
		this.perkTrailCloud = perkTrailCloud;
	}

	public int getPerkTrailMystic() {
		return perkTrailMystic;
	}

	public void setPerkTrailMystic(int perkTrailMystic) {
		this.perkTrailMystic = perkTrailMystic;
	}

	public int getPerkTrailStar() {
		return perkTrailStar;
	}

	public void setPerkTrailStar(int perkTrailStar) {
		this.perkTrailStar = perkTrailStar;
	}

	public int getPerkTrailFlame() {
		return perkTrailFlame;
	}

	public void setPerkTrailFlame(int perkTrailFlame) {
		this.perkTrailFlame = perkTrailFlame;
	}

	public int getPerkTrailBlood() {
		return perkTrailBlood;
	}

	public void setPerkTrailBlood(int perkTrailBlood) {
		this.perkTrailBlood = perkTrailBlood;
	}

	public int getPerkTrailHeart() {
		return perkTrailHeart;
	}

	public void setPerkTrailHeart(int perkTrailHeart) {
		this.perkTrailHeart = perkTrailHeart;
	}
}