package me.taur.arenagames.shop;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.taur.arenagames.Arenagames;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.IconMenu;

public class Shop {
	public static IconMenu shopMenu = null;

	public static void enable() {
		shopMenu = new IconMenu("Shop Menu", 3 * 9, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent menuevt) {
				Player p = menuevt.getPlayer();

				if (Room.PLAYERS.containsKey(p)) { // Players cannot use perks while in game.
					menuevt.setWillClose(true);
					return;

				}

				String name = ChatColor.stripColor(menuevt.getName()); // Clear colors because we add colors in the menu name.

				if (name.equalsIgnoreCase("Shop Effects")) {
					menuevt.setWillClose(true);
					ShopEffectPerk.openMenu(p);
					return;

				}

				if (name.equalsIgnoreCase("Shop Pets")) {
					p.sendMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Pets are coming soon.");
					menuevt.setWillClose(true);
					return;

				}
				
				if (name.equalsIgnoreCase("Shop Hats")) {
					menuevt.setWillClose(true);
					ShopHatPerk.openMenu(p);
					return;

				}

				p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "The selection you made is invalid.");
				menuevt.setWillClose(true);
				return;

			}
		}, Arenagames.plugin);

		shopMenu.setOption(11, new ItemStack(Material.MAP, 1), ChatColor.RESET + "" + ChatColor.BOLD + "Shop Effects");
		shopMenu.setOption(13, new ItemStack(Material.EGG, 1), ChatColor.RESET + "" + ChatColor.BOLD + "Shop Pets");
		shopMenu.setOption(15, new ItemStack(Material.IRON_HELMET, 1), ChatColor.RESET + "" + ChatColor.BOLD + "Shop Hats");

	}
}