package me.taur.arenagames.player;

import me.taur.arenagames.util.TimeUtil;

import org.bukkit.entity.Player;

public class Permission {
	public static boolean isPremium(Player p) {
		PlayerData data = null;
		if (!PlayerData.isLoaded(p)) { // Check 
			new PlayerData(p);
		}
		
		data = PlayerData.get(p);
		long expire = data.getRecentPremiumPayment() + TimeUtil.monthToMilliseconds(data.getPremiumForMonths());
		
		if (TimeUtil.currentMilliseconds() < expire || isPermaPremium(p)) {
			return true;
		}
		
		return false;
		
	}
	
	public static int premiumDaysLeft(Player p) {
		PlayerData data = null;
		if (!PlayerData.isLoaded(p)) { // Check 
			data = new PlayerData(p);
		}
		
		data = PlayerData.get(p);
		long expire = data.getRecentPremiumPayment() + TimeUtil.monthToMilliseconds(data.getPremiumForMonths());
		long resultInMillis = expire - TimeUtil.currentMilliseconds();
		return (int) TimeUtil.millisecondsToDay(resultInMillis);
		
	}
	
	public static boolean isBanned(Player p) {
		PlayerData data = null;
		if (!PlayerData.isLoaded(p)) { // Check 
			new PlayerData(p);
		}
		
		data = PlayerData.get(p);
		
		if (data.getPlayerRank() <= PlayerRank.BANNED.getRankNumber()) {
			return true;
		}
		
		return false;
		
	}
	
	public static boolean isMember(Player p) {
		PlayerData data = null;
		if (!PlayerData.isLoaded(p)) { // Check 
			new PlayerData(p);
		}
		
		data = PlayerData.get(p);
		
		if (data.getPlayerRank() >= PlayerRank.MEMBER.getRankNumber() || p.isOp()) {
			return true;
		}
		
		return false;
		
	}
	
	
	public static boolean isPermaPremium(Player p) {
		PlayerData data = null;
		if (!PlayerData.isLoaded(p)) { // Check 
			new PlayerData(p);
		}
		
		data = PlayerData.get(p);
		
		if (data.getPlayerRank() >= PlayerRank.PERMA_PREMIUM.getRankNumber() || p.isOp()) {
			return true;
		}
		
		return false;
		
	}
	
	public static boolean isModerator(Player p) {
		PlayerData data = null;
		if (!PlayerData.isLoaded(p)) { // Check 
			new PlayerData(p);
		}
		
		data = PlayerData.get(p);
		
		if (data.getPlayerRank() >= PlayerRank.MOD.getRankNumber() || p.isOp()) {
			return true;
		}
		
		return false;
		
	}
	
	public static boolean isAdmin(Player p) {
		PlayerData data = null;
		if (!PlayerData.isLoaded(p)) { // Check 
			new PlayerData(p);
		}
		
		data = PlayerData.get(p);
		
		if (data.getPlayerRank() >= PlayerRank.ADMIN.getRankNumber() || p.isOp()) {
			return true;
		}
		
		return false;
		
	}
}
