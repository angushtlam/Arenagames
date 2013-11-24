package me.taur.arenagames.player;

import org.bukkit.entity.Player;

public class PlayerEconomy {
	public static boolean hasEnoughCurrency(Player p, int amt) {
		PlayerData data = null;
		if (PlayerData.isLoaded(p)) {
			data = PlayerData.get(p);
		} else {
			data = new PlayerData(p);
		}
		
		if ((data.getCurrency() - amt) > 0) {
			return true;
		}
		
		return false;
		
	}
	
	public static boolean hasEnoughCash(Player p, int amt) {
		PlayerData data = null;
		if (PlayerData.isLoaded(p)) {
			data = PlayerData.get(p);
		} else {
			data = new PlayerData(p);
		}
		
		if ((data.getCash() - amt) > 0) {
			return true;
		}
		
		return false;
		
	}
	
	public static boolean changeCurrency(Player p, int amt) {
		if (!hasEnoughCurrency(p, amt)) {
			return false;
		}
		
		PlayerData data = null;
		if (PlayerData.isLoaded(p)) {
			data = PlayerData.get(p);
		} else {
			data = new PlayerData(p);
		}
		
		data.setCurrency(data.getCurrency() + amt);
		
		if (amt > 0) {
			data.setCurrencyLifetime(data.getCurrencyLifetime() + amt);
		}
		
		return true;
		
	}
	
	public static boolean changeCash(Player p, int amt) {
		if (!hasEnoughCash(p, amt)) {
			return false;
		}
		
		PlayerData data = null;
		if (PlayerData.isLoaded(p)) {
			data = PlayerData.get(p);
		} else {
			data = new PlayerData(p);
		}
		
		data.setCash(data.getCash() + amt);
		
		if (amt > 0) {
			data.setCashLifetime(data.getCashLifetime() + amt);
		}
		
		return true;
		
	}
	
}
