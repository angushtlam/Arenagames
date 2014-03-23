package me.taur.arenagames.mastery;

import me.taur.arenagames.Arenagames;
import me.taur.arenagames.chat.ChatUtil;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.IconMenu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MasteryMenu {
	public static IconMenu menu = null;

	public static void enable() {
		menu = new IconMenu(ChatColor.BLUE + "" + ChatColor.BOLD + "Masteries Menu", 5 * 9, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent menuevt) {
				Player p = menuevt.getPlayer();
				menuevt.setWillClose(true);

				if (Room.PLAYERS.containsKey(p)) { // Players cannot use perks while in game.
					return;
				}
				
				String name = ChatColor.stripColor(menuevt.getName()); // Clear colors because we add colors in the menu name.

				for (Masteries fx : Masteries.values()) {
					if (fx.getName().equalsIgnoreCase(name)) { // If the name of the item matched the name of the perk.
						p.sendMessage(ChatUtil.basicInfoMsg("Masteries are coming soon."));
						return;
					}
				}

				p.sendMessage(ChatUtil.basicErrorMsg("You have selected an invalid Mastery."));
				return;

			}
		}, Arenagames.plugin);

		menu.setOption(0, new ItemStack(Material.STAINED_CLAY, 1, (byte) 14), ChatColor.RED + "" + ChatColor.BOLD + ChatColor.UNDERLINE + "Alpha Masteries",
				ChatColor.GRAY + "" + ChatColor.ITALIC + "You can only have 1 of",
				ChatColor.GRAY + "" + ChatColor.ITALIC + "these masteries enabled.");
		
		menu.setOption(2, new ItemStack(Material.IRON_AXE, 1), ChatColor.RED + "" + ChatColor.BOLD + Masteries.BLOODTHIRSTY.getName(),
				ChatColor.YELLOW + "" + ChatColor.ITALIC + "Active In: TDM, FFA",
				ChatColor.GRAY + "After landing the killing blow on",
				ChatColor.GRAY + "an enemy player, applies Speed for",
				ChatColor.GRAY + "a short duration.");
		
		menu.setOption(3, new ItemStack(Material.INK_SACK, 1), ChatColor.RED + "" + ChatColor.BOLD + Masteries.LAST_RESORT.getName(),
				ChatColor.YELLOW + "" + ChatColor.ITALIC + "Active In: TDM, FFA, The Mob",
				ChatColor.GRAY + "Player deals bonus damage based",
				ChatColor.GRAY + "on missing hearts.");
		
		menu.setOption(4, new ItemStack(Material.SKULL_ITEM, 1), ChatColor.RED + "" + ChatColor.BOLD + Masteries.FOCUS.getName(),
				ChatColor.YELLOW + "" + ChatColor.ITALIC + "Active In: TDM, FFA, The Mob",
				ChatColor.GRAY + "Player deals bonus damage to",
				ChatColor.GRAY + "isolated enemies.");
		
		menu.setOption(9, new ItemStack(Material.STAINED_CLAY, 1, (byte) 1), ChatColor.GOLD + "" + ChatColor.BOLD + ChatColor.UNDERLINE + "Beta Masteries",
				ChatColor.YELLOW + "" + ChatColor.ITALIC + "You can only have 1 of",
				ChatColor.YELLOW + "" + ChatColor.ITALIC + "these masteries enabled.");
		
		menu.setOption(11, new ItemStack(Material.COOKED_CHICKEN, 1), ChatColor.GOLD + "" + ChatColor.BOLD + Masteries.REPOSE.getName(),
				ChatColor.YELLOW + "" + ChatColor.ITALIC + "Active In: TDM, FFA, The Mob",
				ChatColor.GRAY + "Sneaking will regenerate your Hunger",
				ChatColor.GRAY + "meter over time.");
		
		menu.setOption(12, new ItemStack(Material.GOLDEN_APPLE, 1), ChatColor.GOLD + "" + ChatColor.BOLD + Masteries.HEART_OF_GOLD.getName(),
				ChatColor.YELLOW + "" + ChatColor.ITALIC + "Active In: TDM, FFA, The Mob",
				ChatColor.GRAY + "Grants a temporary shield that grants",
				ChatColor.GRAY + "bonus Hearts on spawn.");
		
		menu.setOption(18, new ItemStack(Material.STAINED_CLAY, 1, (byte) 4), ChatColor.YELLOW + "" + ChatColor.BOLD + ChatColor.UNDERLINE + "Gamma Masteries",
				ChatColor.YELLOW + "" + ChatColor.ITALIC + "You can only have 1 of",
				ChatColor.YELLOW + "" + ChatColor.ITALIC + "these masteries enabled.");
		
		menu.setOption(20, new ItemStack(Material.MAGMA_CREAM, 1), ChatColor.YELLOW + "" + ChatColor.BOLD + Masteries.TENACIOUS.getName(),
				ChatColor.YELLOW + "" + ChatColor.ITALIC + "Active In: TDM, FFA, The Mob",
				ChatColor.GRAY + "Grants player a “Cleanse Cream” item on",
				ChatColor.GRAY + "spawn that consumes itself and removes",
				ChatColor.GRAY + "all effects applied and slightly",
				ChatColor.GRAY + "knocks away enemies around player.");
		
		menu.setOption(21, new ItemStack(Material.WATER_BUCKET, 1), ChatColor.YELLOW + "" + ChatColor.BOLD + Masteries.FIREFIGHTER.getName(),
				ChatColor.YELLOW + "" + ChatColor.ITALIC + "Active In: TDM, FFA, The Mob",
				ChatColor.GRAY + "Grants player an \"Emergency Bottled",
				ChatColor.GRAY + "Aqueous Substance\" potion on spawn",
				ChatColor.GRAY + "that applies Fire Resistance.");
		
		menu.setOption(27, new ItemStack(Material.STAINED_CLAY, 1, (byte) 5), ChatColor.DARK_AQUA + "" + ChatColor.BOLD + ChatColor.UNDERLINE + "Delta Masteries",
				ChatColor.YELLOW + "" + ChatColor.ITALIC + "You can only have 1 of",
				ChatColor.YELLOW + "" + ChatColor.ITALIC + "these masteries enabled.");
		
		menu.setOption(29, new ItemStack(Material.IRON_HOE, 1), ChatColor.DARK_AQUA + "" + ChatColor.BOLD + Masteries.SPEED_SIPHON.getName(),
				ChatColor.YELLOW + "" + ChatColor.ITALIC + "Active In: The Mob",
				ChatColor.GRAY + "After landing the killing blow on a",
				ChatColor.GRAY + "monster, applies Speed for a short",
				ChatColor.GRAY + "duration.");
		
		menu.setOption(36, new ItemStack(Material.STAINED_CLAY, 1, (byte) 3), ChatColor.BLUE + "" + ChatColor.BOLD + ChatColor.UNDERLINE + "Zeta Masteries",
				ChatColor.YELLOW + "" + ChatColor.ITALIC + "You can only have 1 of",
				ChatColor.YELLOW + "" + ChatColor.ITALIC + "these masteries enabled.");

		menu.setOption(38, new ItemStack(Material.IRON_BOOTS, 1), ChatColor.DARK_AQUA + "" + ChatColor.BOLD + Masteries.FEATHER_FEET.getName(),
				ChatColor.YELLOW + "" + ChatColor.ITALIC + "Active In: Spawn",
				ChatColor.GRAY + "Grants player Jump Boost.");
		
	}
}
