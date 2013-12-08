package me.taur.arenagames.item;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import me.taur.arenagames.util.Sym;

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
		pm.setLore(Arrays.asList(ChatColor.GOLD + "Cost: 4 " + Sym.HUNGER,
				ChatColor.GOLD + "Cooldown: 22 sec.",
				ChatColor.YELLOW + "Inflicts Slow II for 2 sec. to",
				ChatColor.YELLOW + "enemies within 12 blocks."));
		
		i.setItemMeta(pm);
		
		CustomItemUtil.STORE.put("COMMAND_TREMBLE", i);
		
	}
	
	public static void loadCommandLockdown() {
		ItemStack i = new ItemStack(Material.GOLD_INGOT, 1);
		
		ItemMeta pm = i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "Command: Lockdown");
		pm.setLore(Arrays.asList(ChatColor.GOLD + "Cost: 1 " + Sym.HUNGER,
				ChatColor.GOLD + "Cooldown: 9 sec.",
				ChatColor.YELLOW + "Applies Damage Resistance III and",
				ChatColor.YELLOW + "Weakness V for 3 sec."));
		
		i.setItemMeta(pm);
		
		CustomItemUtil.STORE.put("COMMAND_LOCKDOWN", i);
		
	}
	
	public static void loadSickScalpel() {
		ItemStack i = new ItemStack(Material.IRON_HOE, 1);
		
		ItemMeta pm = i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "Sick Scalpel");
		pm.setLore(Arrays.asList(ChatColor.GOLD + "Cost: 1 " + Sym.HUNGER,
				ChatColor.YELLOW + "Inflicts Poison II for 5 sec."));
		
		i.setItemMeta(pm);
		
		CustomItemUtil.STORE.put("SICK_SCALPEL", i);
		
	}
	
	public static void loadMachete() {
		ItemStack i = new ItemStack(Material.IRON_AXE, 1);
		
		ItemMeta pm = i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "Machete");
		pm.setLore(Arrays.asList(ChatColor.BLUE + "The Bloodhunter uses the Machete to",
				ChatColor.BLUE + "slice through their enemies."));
		
		i.setItemMeta(pm);
		
		CustomItemUtil.STORE.put("MACHETE", i);
		
	}
	
	public static void loadBladeNoir() {
		ItemStack i = new ItemStack(Material.STONE_SWORD, 1);
		
		ItemMeta pm = i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "Blade Noir");
		pm.setLore(Arrays.asList(ChatColor.YELLOW + "Inflicts 3 bonus " + Sym.HEART + " of",
				ChatColor.YELLOW + "damage to isolated players.",
				ChatColor.YELLOW + "(10 blocks from other players)"));
		
		i.setItemMeta(pm);
		
		CustomItemUtil.STORE.put("BLADE_NOIR", i);
		
	}
	
	public static void loadTasteOfIsolation() {
		ItemStack i = new ItemStack(Material.SNOW_BALL, 1);
		
		ItemMeta pm = i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "Taste of Isolation");
		pm.setLore(Arrays.asList(ChatColor.GOLD + "Cost: 2 " + Sym.HUNGER,
				ChatColor.YELLOW + "Pulls in the enemies hit towards",
				ChatColor.YELLOW + "you by a maximum of 5 blocks."));
		
		i.setItemMeta(pm);
		
		CustomItemUtil.STORE.put("TASTE_OF_ISOLATION", i);
		
	}
	
	public static void loadTheStrangler() {
		ItemStack i = new ItemStack(Material.BOW, 1);
		
		ItemMeta pm = i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "The Strangler");
		pm.setLore(Arrays.asList(ChatColor.GOLD + "Melee Cost: " + Sym.HALF + " " + Sym.HUNGER,
				ChatColor.YELLOW + "Basic attacks inflict Wither I and",
				ChatColor.YELLOW + "Slow I for 1 sec., applies Weakness I",
				ChatColor.YELLOW + "and Slow I for 1 sec."));
		
		pm.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		i.setItemMeta(pm);
		
		CustomItemUtil.STORE.put("THE_STRANGLER", i);
		
	}
	
	public static void loadWondershot() {
		ItemStack i = new ItemStack(Material.BOW, 1);
		
		ItemMeta pm = i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "Wondershot");
		pm.setLore(Arrays.asList(ChatColor.GOLD + "Ranged Cost: 6 " + Sym.HUNGER,
				ChatColor.YELLOW + "Refunds 3 " + Sym.HUNGER + " if shot hits."));
		
		pm.addEnchant(Enchantment.ARROW_DAMAGE, 4, true);
		pm.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		i.setItemMeta(pm);
		
		CustomItemUtil.STORE.put("WONDERSHOT", i);
		
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
