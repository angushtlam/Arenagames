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
	
	private int playerRank, violationLevel;
	private int currency, currencyLifetime, cash, cashLifetime, exp;
	
	private int ffaGamesWon, ffaGamesPlayed, ffaRanking, ffaRecord;
	private int ffaTotalKills, ffaTotalDeaths;
	private int ffaCurrencyEarned;
	
	private int tdmGamesWon, tdmGamesPlayed, tdmRanking, tdmRecord;
	private int tdmTotalKills, tdmTotalDeaths;
	private int tdmCurrencyEarned;
	
	private String perkEffect, perkHat;
	
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
					
					conf.addDefault("user.moderation.player-rank", 0);
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
					
					conf.addDefault("user.tdm.games-won", 0);
					conf.addDefault("user.tdm.games-played", 0);
					conf.addDefault("user.tdm.ranking", 1000);
					conf.addDefault("user.tdm.record", 0);
					conf.addDefault("user.tdm.kills", 0);
					conf.addDefault("user.tdm.deaths", 0);
					conf.addDefault("user.tdm.currency-earned", 0);
					
					conf.addDefault("user.perk.effect", "@");
					conf.addDefault("user.perk.hat", "@");
					
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
				
				data.setPlayerRank(conf.getInt("user.moderation.player-rank"));
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
				
				data.setTdmGamesWon(conf.getInt("user.tdm.games-won"));
				data.setTdmGamesPlayed(conf.getInt("user.tdm.games-played"));
				data.setTdmRanking(conf.getInt("user.tdm.ranking"));
				data.setTdmRecord(conf.getInt("user.tdm.record"));
				data.setTdmTotalKills(conf.getInt("user.tdm.kills"));
				data.setTdmTotalDeaths(conf.getInt("user.tdm.deaths"));
				data.setTdmCurrencyEarned(conf.getInt("user.tdm.currency-earned"));
				
				data.setPerkEffect(conf.getString("user.perk.effect"));
				data.setPerkHat(conf.getString("user.perk.hat"));
				
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
				
				conf.set("user.moderation.player-rank", data.getPlayerRank());
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
				
				conf.set("user.tdm.games-won", data.getTdmGamesWon());
				conf.set("user.tdm.games-played", data.getTdmGamesPlayed());
				conf.set("user.tdm.ranking", data.getTdmRanking());
				conf.set("user.tdm.record", data.getTdmRecord());
				conf.set("user.tdm.kills", data.getTdmTotalKills());
				conf.set("user.tdm.deaths", data.getTdmTotalDeaths());
				conf.set("user.tdm.currency-earned", data.getTdmCurrencyEarned());
				
				conf.set("user.perk.effect", data.getPerkEffect());
				conf.set("user.perk.hat", data.getPerkHat());
				
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

	public int getPlayerRank() {
		return playerRank;
	}

	public void setPlayerRank(int playerRank) {
		this.playerRank = playerRank;
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

	public int getTdmGamesWon() {
		return tdmGamesWon;
	}

	public void setTdmGamesWon(int tdmGamesWon) {
		this.tdmGamesWon = tdmGamesWon;
	}

	public int getTdmGamesPlayed() {
		return tdmGamesPlayed;
	}

	public void setTdmGamesPlayed(int tdmGamesPlayed) {
		this.tdmGamesPlayed = tdmGamesPlayed;
	}

	public int getTdmRanking() {
		return tdmRanking;
	}

	public void setTdmRanking(int tdmRanking) {
		this.tdmRanking = tdmRanking;
	}

	public int getTdmRecord() {
		return tdmRecord;
	}

	public void setTdmRecord(int tdmRecord) {
		this.tdmRecord = tdmRecord;
	}

	public int getTdmTotalKills() {
		return tdmTotalKills;
	}

	public void setTdmTotalKills(int tdmTotalKills) {
		this.tdmTotalKills = tdmTotalKills;
	}

	public int getTdmTotalDeaths() {
		return tdmTotalDeaths;
	}

	public void setTdmTotalDeaths(int tdmTotalDeaths) {
		this.tdmTotalDeaths = tdmTotalDeaths;
	}

	public int getTdmCurrencyEarned() {
		return tdmCurrencyEarned;
	}

	public void setTdmCurrencyEarned(int tdmCurrencyEarned) {
		this.tdmCurrencyEarned = tdmCurrencyEarned;
	}

	public String getPerkEffect() {
		return perkEffect;
	}

	public void setPerkEffect(String perkEffect) {
		this.perkEffect = perkEffect;
	}

	public String getPerkHat() {
		return perkHat;
	}

	public void setPerkHat(String perkHat) {
		this.perkHat = perkHat;
	}
	
}