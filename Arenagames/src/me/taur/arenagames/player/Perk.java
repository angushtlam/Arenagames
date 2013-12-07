package me.taur.arenagames.player;

import me.taur.arenagames.perk.EffectPerkUtil;
import me.taur.arenagames.perk.HatPerkUtil;

import org.bukkit.entity.Player;

public class Perk {
	public static int getPerk(Player p, EffectPerkUtil perk) {
		PlayerData data = null;
		if (!PlayerData.isLoaded(p)) {
			data = new PlayerData(p);
		} else {
			data = PlayerData.get(p);
		}
		
		Integer[] serial = deserialize(data.getPerkEffect());
		int id = perk.getId();
		
		if (serial.length < id) {
			return 0;
		}
		
		if (serial[id] == null) {
			return 0;
		}
		
		return serial[perk.getId()];
		
	}
	
	public static void setPerk(Player p, EffectPerkUtil perk, int amt) {
		PlayerData data = null;
		if (!PlayerData.isLoaded(p)) {
			data = new PlayerData(p);
		} else {
			data = PlayerData.get(p);
		}
		
		Integer[] serial = updateEffectArray(deserialize(data.getPerkEffect()));
		serial[perk.getId()] = amt; // Change the amount
		data.setPerkEffect(serialize(serial));
		data.save(p);
		
	}
	
	public static void givePerk(Player p, EffectPerkUtil perk) {
		setPerk(p, perk, 1);	
	}
	
	
	public static boolean hasPerk(Player p, EffectPerkUtil perk) {
		if (getPerk(p, perk) > 0) {
			return true;
		}
		
		return false;
		
	}
	
	public static Integer[] updateEffectArray(Integer[] ints) {
		int length = EffectPerkUtil.values().length;
		if (ints.length == length) {
			return ints;
		}
		
		Integer[] newints = new Integer[length];
		
		for (int i = 0; i < ints.length - 1; i++) {
			newints[i] = ints[i];
		}
		
		return newints;
		
	}
	
	public static int getPerk(Player p, HatPerkUtil perk) {
		PlayerData data = null;
		if (!PlayerData.isLoaded(p)) {
			data = new PlayerData(p);
		} else {
			data = PlayerData.get(p);
		}
		
		Integer[] serial = updateHatArray(deserialize(data.getPerkHat()));
		int id = perk.getId();
		
		if (serial.length < id) {
			return 0;
		}
		
		if (serial[id] == null) {
			return 0;
		}
		
		return serial[perk.getId()];
		
	}
	
	public static void setPerk(Player p, HatPerkUtil perk, int amt) {
		PlayerData data = null;
		if (!PlayerData.isLoaded(p)) {
			data = new PlayerData(p);
		} else {
			data = PlayerData.get(p);
		}
		
		Integer[] serial = updateHatArray(deserialize(data.getPerkHat()));
		serial[perk.getId()] = amt; // Change the amount
		data.setPerkHat(serialize(serial));
		data.save(p);
		
	}
	
	public static void givePerk(Player p, HatPerkUtil perk) {
		setPerk(p, perk, 1);	
	}
	
	public static boolean hasPerk(Player p, HatPerkUtil perk) {
		if (getPerk(p, perk) > 0) {
			return true;
		}
		
		return false;
		
	}
	
	public static Integer[] updateHatArray(Integer[] ints) {
		int length = HatPerkUtil.values().length;
		if (ints.length == length) {
			return ints;
		}
		
		Integer[] newints = new Integer[length];
		
		for (int i = 0; i < ints.length - 1; i++) {
			newints[i] = ints[i];
		}
		
		return newints;
		
	}
	
	public static Integer[] deserialize(String str) {
		String[] arr = str.split("@");
		Integer[] ints = new Integer[arr.length];
		
		for (int i = 0; i < arr.length; i++) {
			try { // Make sure the data is a number.
				Integer.parseInt(arr[i]);
			} catch (NumberFormatException e) {
				continue; // Ignore the number then.
			}
			
			if (arr[i] == null) {
				ints[i] = 0;
				continue;
			}
			
			ints[i] = Integer.valueOf(arr[i]);
			
		}
		
		return ints;
	}
	
	public static String serialize(Integer[] arr) {
		String str = "";
		
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == null) {
				str = str + "0";
			} else {
				str = str + arr[i];
			}
				
			if (i != arr.length - 1) {
				str = str + "@";
			}
		}
		
		return str;
	}
	
}
