package me.taur.arenagames.perk;

import me.taur.arenagames.Arenagames;
import me.taur.arenagames.chat.ChatUtil;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.IconMenu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PerkMenu {
	public static IconMenu menu = null;

	public static void enable() {
		menu = new IconMenu(ChatColor.BLUE + "" + ChatColor.BOLD + "Perk Menu", 3 * 9, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent menuevt) {
				Player p = menuevt.getPlayer();

				if (Room.PLAYERS.containsKey(p)) { // Players cannot use perks while in game.
					menuevt.setWillClose(true);
					return;

				}

				String name = ChatColor.stripColor(menuevt.getName()); // Clear colors because we add colors in the menu name.

				if (name.equalsIgnoreCase("My Effects")) {
					menuevt.setWillClose(true);
					PerkEffect.openMenu(p);
					return;

				}

				if (name.equalsIgnoreCase("My Pets")) {
					p.sendMessage(ChatUtil.basicInfoMsg("Pets are coming soon."));
					menuevt.setWillClose(true);
					return;

				}
				
				if (name.equalsIgnoreCase("My Hats")) {
					menuevt.setWillClose(true);
					PerkHat.openMenu(p);
					return;

				}

				p.sendMessage(ChatUtil.basicErrorMsg("The selection you made is invalid."));
				menuevt.setWillClose(true);
				return;

			}
		}, Arenagames.plugin);


		menu.setOption(11, new ItemStack(Material.MAP, 1), ChatColor.RESET + "" + ChatColor.BOLD + "My Effects");
		menu.setOption(13, new ItemStack(Material.EGG, 1), ChatColor.RESET + "" + ChatColor.BOLD + "My Pets");
		menu.setOption(15, new ItemStack(Material.IRON_HELMET, 1), ChatColor.RESET + "" + ChatColor.BOLD + "My Hats");

	}
}
