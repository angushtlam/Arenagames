package me.taur.arenagames.item;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItem {
	public static ConcurrentHashMap<Player, Integer> COMMAND_VACUUM_TIMER = new ConcurrentHashMap<Player, Integer>();
	public static ConcurrentHashMap<Player, Integer> COMMAND_ENDURE_TIMER = new ConcurrentHashMap<Player, Integer>();
	
	public static void loadCommandVacuum() {
		ItemStack i = new ItemStack(Material.IRON_INGOT, 1);
		
		ItemMeta pm = i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "Command: Vacuum");
		pm.setLore(Arrays.asList("22s Cooldown / Cost 4 Food", "Pulls in enemies 8 blocks away."));
		
		i.setItemMeta(pm);
		CustomItemUtil.STORE.put("COMMAND_VACUUM", i);
		
	}
	
	public static void loadCommandEndure() {
		ItemStack i = new ItemStack(Material.IRON_INGOT, 1);
		
		ItemMeta pm = i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "Command: Endure");
		pm.setLore(Arrays.asList("8s Cooldown / Cost 1 Food", "Applies Damage Resistance II for 3s."));
		
		i.setItemMeta(pm);
		CustomItemUtil.STORE.put("COMMAND_ENDURE", i);
		
	}
	
	public static void clearPlayerTimers(Player p) {
		if (CustomItem.COMMAND_VACUUM_TIMER.containsKey(p)) {
			CustomItem.COMMAND_VACUUM_TIMER.remove(p);
			
		}
		
		if (CustomItem.COMMAND_ENDURE_TIMER.containsKey(p)) {
			CustomItem.COMMAND_ENDURE_TIMER.remove(p);
			
		}
		
	}
	
	public static void run() {
		if (!COMMAND_VACUUM_TIMER.isEmpty()) {
			for (Player p : COMMAND_VACUUM_TIMER.keySet()) {
				if (p == null) { // Null check.
					continue;
				}
				
				if (!p.isOnline()) { // Remove player to save memory.
					COMMAND_VACUUM_TIMER.remove(p);
					continue;
					
				}
				
				if (COMMAND_VACUUM_TIMER.get(p) < 1) { // If the timer is 0 or less;
					COMMAND_VACUUM_TIMER.remove(p);
					p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "Command: Vacuum is now off cooldown.");
					continue;
					
				}
				
				if (COMMAND_VACUUM_TIMER.get(p) > 0) { // Reduce timer.
					COMMAND_VACUUM_TIMER.put(p, COMMAND_VACUUM_TIMER.get(p) - 1); // Subtract 1 from timer.
					continue;
					
				}
			}
		}
		
		if (!COMMAND_ENDURE_TIMER.isEmpty()) {
			for (Player p : COMMAND_ENDURE_TIMER.keySet()) {
				if (p == null) { // Null check.
					continue;
				}
				
				if (!p.isOnline()) { // Remove player to save memory.
					COMMAND_ENDURE_TIMER.remove(p);
					continue;
					
				}
				
				if (COMMAND_ENDURE_TIMER.get(p) < 1) { // If the timer is 0 or less;
					COMMAND_ENDURE_TIMER.remove(p);
					p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "Command: Endure is now off cooldown.");
					continue;
					
				}
				
				if (COMMAND_ENDURE_TIMER.get(p) > 0) { // Reduce timer.
					COMMAND_ENDURE_TIMER.put(p, COMMAND_ENDURE_TIMER.get(p) - 1); // Subtract 1 from timer.
					continue;
					
				}
			}
		}
		
	}
}
