package me.taur.arenagames.item;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItem {
	public static ConcurrentHashMap<Player, Integer> COMMAND_TREMBLE_TIMER = new ConcurrentHashMap<Player, Integer>();
	public static ConcurrentHashMap<Player, Integer> COMMAND_LOCKDOWN_TIMER = new ConcurrentHashMap<Player, Integer>();
	
	public static void loadCommandTremble() {
		ItemStack i = new ItemStack(Material.IRON_INGOT, 1);
		
		ItemMeta pm = i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "Command: Tremble");
		pm.setLore(Arrays.asList("22 sec Cooldown / Cost 4 Food", "Inflicts Slow II for 2 sec to", "enemies within 12 blocks."));
		
		i.setItemMeta(pm);
		CustomItemUtil.STORE.put("COMMAND_TREMBLE", i);
		
	}
	
	public static void loadCommandLockdown() {
		ItemStack i = new ItemStack(Material.GOLD_INGOT, 1);
		
		ItemMeta pm = i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "Command: Lockdown");
		pm.setLore(Arrays.asList("9 sec Cooldown / Cost 1 Food", "Applies Damage Resistance III and", "Weakness V for 3 sec."));
		
		i.setItemMeta(pm);
		CustomItemUtil.STORE.put("COMMAND_LOCKDOWN", i);
		
	}
	
	public static void loadTasteOfIsolation() {
		ItemStack i = new ItemStack(Material.SNOW_BALL, 1);
		
		ItemMeta pm = i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "Taste of Isolation");
		pm.setLore(Arrays.asList("Pulls in the enemies hit towards", "you by 4 blocks."));
		
		i.setItemMeta(pm);
		CustomItemUtil.STORE.put("TASTE_OF_ISOLATION", i);
		
	}
	
	public static void loadTheStrangler() {
		ItemStack i = new ItemStack(Material.BOW, 1);
		
		ItemMeta pm = i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "The Strangler");
		pm.setLore(Arrays.asList("Basic attacks inflict Wither I and", "Slow I for 1 sec, applies Weakness", "and Slow I for 1 sec."));
		pm.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		
		i.setItemMeta(pm);
		CustomItemUtil.STORE.put("THE_STRANGLER", i);
		
	}
	
	public static void loadBaneOfTheForest() {
		ItemStack i = new ItemStack(Material.BOW, 1);
		
		ItemMeta pm = i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "Bane Of The Forest");
		pm.setLore(Arrays.asList("Cost 3 Hunger each arrow fired."));
		pm.addEnchant(Enchantment.ARROW_FIRE, 1, true);
		pm.addEnchant(Enchantment.ARROW_DAMAGE, 2, true);
		pm.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		
		i.setItemMeta(pm);
		CustomItemUtil.STORE.put("BANE_OF_THE_FOREST", i);
		
	}
	
	public static void clearPlayerTimers(Player p) {
		if (CustomItem.COMMAND_TREMBLE_TIMER.containsKey(p)) {
			CustomItem.COMMAND_TREMBLE_TIMER.remove(p);
			
		}
		
		if (CustomItem.COMMAND_LOCKDOWN_TIMER.containsKey(p)) {
			CustomItem.COMMAND_LOCKDOWN_TIMER.remove(p);
			
		}
		
	}
	
	public static void run() {
		if (!COMMAND_TREMBLE_TIMER.isEmpty()) {
			for (Player p : COMMAND_TREMBLE_TIMER.keySet()) {
				if (p == null) { // Null check.
					continue;
				}
				
				if (!p.isOnline()) { // Remove player to save memory.
					COMMAND_TREMBLE_TIMER.remove(p);
					continue;
					
				}
				
				if (COMMAND_TREMBLE_TIMER.get(p) < 1) { // If the timer is 0 or less;
					COMMAND_TREMBLE_TIMER.remove(p);
					p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "Command: Tremble is now off cooldown.");
					continue;
					
				}
				
				if (COMMAND_TREMBLE_TIMER.get(p) > 0) { // Reduce timer.
					COMMAND_TREMBLE_TIMER.put(p, COMMAND_TREMBLE_TIMER.get(p) - 1); // Subtract 1 from timer.
					continue;
					
				}
			}
		}
		
		if (!COMMAND_LOCKDOWN_TIMER.isEmpty()) {
			for (Player p : COMMAND_LOCKDOWN_TIMER.keySet()) {
				if (p == null) { // Null check.
					continue;
				}
				
				if (!p.isOnline()) { // Remove player to save memory.
					COMMAND_LOCKDOWN_TIMER.remove(p);
					continue;
					
				}
				
				if (COMMAND_LOCKDOWN_TIMER.get(p) < 1) { // If the timer is 0 or less;
					COMMAND_LOCKDOWN_TIMER.remove(p);
					p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "Command: Lockdown is now off cooldown.");
					continue;
					
				}
				
				if (COMMAND_LOCKDOWN_TIMER.get(p) > 0) { // Reduce timer.
					COMMAND_LOCKDOWN_TIMER.put(p, COMMAND_LOCKDOWN_TIMER.get(p) - 1); // Subtract 1 from timer.
					continue;
					
				}
			}
		}
		
	}
}
