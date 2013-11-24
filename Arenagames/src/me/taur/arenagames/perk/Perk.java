package me.taur.arenagames.perk;

import me.taur.arenagames.Arenagames;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.IconMenu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Perk {
	public static IconMenu perkMenu = null;

	public static void enable() {
		perkMenu = new IconMenu("Perk Menu", 3 * 9, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent menuevt) {
				Player p = menuevt.getPlayer();

				if (Room.PLAYERS.containsKey(p)) { // Players cannot use perks while in game.
					menuevt.setWillClose(true);
					return;

				}

				String name = ChatColor.stripColor(menuevt.getName()); // Clear colors because we add colors in the menu name.

				if (name.equalsIgnoreCase("My Effect Perks")) {
					menuevt.setWillClose(true);
					PerkEffect.openMenu(p);
					return;

				}

				if (name.equalsIgnoreCase("My Hat Perks")) {
					p.sendMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Hat Perks are coming soon.");
					menuevt.setWillClose(true);
					return;

				}

				p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "The selection you made is invalid.");
				menuevt.setWillClose(true);
				return;

			}
		}, Arenagames.plugin);


		perkMenu.setOption(11, new ItemStack(Material.MAP, 1), ChatColor.RESET + "" + ChatColor.BOLD + "My Effect Perks");
		perkMenu.setOption(15, new ItemStack(Material.IRON_HELMET, 1), ChatColor.RESET + "" + ChatColor.BOLD + "My Hat Perks");

	}
}
