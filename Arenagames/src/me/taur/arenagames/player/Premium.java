package me.taur.arenagames.player;

import me.taur.arenagames.util.TimeUtil;

import org.bukkit.entity.Player;

public class Premium {
	public static boolean isPremium(Player p) {
		PlayerData data = null;
		if (!PlayerData.isLoaded(p)) { // Check 
			data = new PlayerData(p);
		}
		
		data = PlayerData.get(p);
		long expire = data.getRecentPremiumPayment() + TimeUtil.monthToMilliseconds(data.getPremiumForMonths());
		
		if (TimeUtil.currentMilliseconds() > expire) {
			return false;
		}
		
		return true;
		
	}
	
	public static int daysLeft(Player p) {
		PlayerData data = null;
		if (!PlayerData.isLoaded(p)) { // Check 
			data = new PlayerData(p);
		}
		
		data = PlayerData.get(p);
		long expire = data.getRecentPremiumPayment() + TimeUtil.monthToMilliseconds(data.getPremiumForMonths());
		long resultInMillis = expire - TimeUtil.currentMilliseconds();
		return (int) TimeUtil.millisecondsToDay(resultInMillis);
		
	}
}
