package me.taur.arenagames.item;

import me.taur.arenagames.Arenagames;
import me.taur.arenagames.Config;
import me.taur.arenagames.chat.ChatUtil;
import me.taur.arenagames.ffa.FfaConfig;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.tdm.TdmConfig;
import me.taur.arenagames.util.IconMenu;
import me.taur.arenagames.util.RoomType;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Warp {
	public static IconMenu menu = null;

	public static void enable() {
		int enabledmode = 1;
		for (RoomType rt : RoomType.values()) {
			if (Config.isEnabled(rt)) {
				enabledmode++;
			}
		}
		
		int box = ((enabledmode / 9) + 1) * 9; // Gets how many box the plugin needs.
		
		menu = new IconMenu(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Warps", box, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent menuevt) {
				Player p = menuevt.getPlayer();

				if (Room.PLAYERS.containsKey(p)) { // Players cannot use perks while in game.
					menuevt.setWillClose(true);
					return;

				}

				String name = ChatColor.stripColor(menuevt.getName()); // Clear colors because we add colors in the menu name.

				if (name.equalsIgnoreCase("Spawn")) {
					p.teleport(Config.getGlobalLobby());
					p.sendMessage(ChatUtil.basicSuccessMsg("You have been teleported to " + name + "."));
					return;

				} else if (name.equalsIgnoreCase(RoomType.FFA.getName())) {
					p.teleport(FfaConfig.getLobby());
					p.sendMessage(ChatUtil.basicSuccessMsg("You have been teleported to " + name + "."));
					return;

				} else if (name.equalsIgnoreCase(RoomType.TDM.getName())) {
					p.teleport(TdmConfig.getLobby());
					p.sendMessage(ChatUtil.basicSuccessMsg("You have been teleported to " + name + "."));
					return;

				}
				
				p.sendMessage(ChatUtil.basicErrorMsg("The selection you made is invalid."));
				menuevt.setWillClose(true);
				return;

			}
		}, Arenagames.plugin);
		
		menu.setOption(0, new ItemStack(Material.BED, 1), ChatColor.RESET + "" + ChatColor.BOLD + "Spawn");

		int id = 1;
		for (RoomType rt : RoomType.values()) {
			if (Config.isEnabled(rt)) {
				menu.setOption(id, new ItemStack(Material.PORTAL, 1), ChatColor.RESET + "" + ChatColor.BOLD + rt.getName());
				id++;
				
			}
		}

	}
}
