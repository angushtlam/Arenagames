package me.taur.arenagames.player;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class PlayerData {
	public static HashMap<Player, PlayerData> STORE = new HashMap<Player, PlayerData>();
	
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
		setPlayerName(p.getName());
		PlayerDataUtil.createFile(p);
		PlayerDataUtil.loadData(p);
		STORE.put(p, this);
		
	}
	
	public static boolean isLoaded(Player p) {
		if (STORE.get(p) != null) {
			return true;
		}
		
		return false;
		
	}
	
	public static PlayerData get(Player p) {
		return STORE.get(p);
	}
	
	public void save(Player p) {
		PlayerDataUtil.save(p);
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