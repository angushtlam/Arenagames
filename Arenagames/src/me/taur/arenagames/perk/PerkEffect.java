package me.taur.arenagames.perk;

import java.util.HashMap;

import me.taur.arenagames.Arenagames;
import me.taur.arenagames.player.PlayerPerk;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.IconMenu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PerkEffect {
	public static HashMap<Player, EffectPerkUtil> ACTIVE_EFFECT_PERK = new HashMap<Player, EffectPerkUtil>();
	public static HashMap<Player, IconMenu> MENU_STORE = new HashMap<Player, IconMenu>();
	
	public static void openMenu(final Player p) {
		Bukkit.getScheduler().runTaskLater(Arenagames.plugin, new Runnable() {
			public void run() {
				if (MENU_STORE.containsKey(p)) { // Destroy previous opened item.
					MENU_STORE.get(p).destroy();
				}
				
				generateMenu(p);
				MENU_STORE.get(p).open(p);
				
			}
		}, 2L);

	}

	public static void generateMenu(Player p) {
		int perkamt = EffectPerkUtil.values().length;
		int lines = ((perkamt / 9) + 1) * 9; // Gets how many lines the plugin needs.

		IconMenu menu = new IconMenu(p.getName() + "\'s Effect Perks", lines, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent menuevt) {
				menuevt.setWillDestroy(true); // Destroy this object after it is used.
				
				Player p = menuevt.getPlayer();

				if (Room.PLAYERS.containsKey(p)) { // Cannot use effects in game.
					menuevt.setWillClose(true);
					return;

				}

				String name = ChatColor.stripColor(menuevt.getName()); // Clear colors because we add colors in the menu name.

				for (EffectPerkUtil fx : EffectPerkUtil.values()) {
					if (fx.getName().equalsIgnoreCase(name)) { // If the name of the item matched the name of the perk.
						if (PlayerPerk.isPerkOwned(p, fx)) {
							if (ACTIVE_EFFECT_PERK.containsKey(p)) { // If the player already has an active effect perk.
								if (ACTIVE_EFFECT_PERK.get(p).equals(fx)) {
									p.sendMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + "You have disabled the " + name + " effect perk.");
									ACTIVE_EFFECT_PERK.remove(p);

								} else {
									p.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "You have activated the " + name + " effect perk.");
									ACTIVE_EFFECT_PERK.put(p, fx);

								}

							} else { // If this player just selected a new perk.
								p.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "You have activated the " + name + " effect perk.");
								ACTIVE_EFFECT_PERK.put(p, fx);

							}

						} else { // If the player has no permission for this perk.
							p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You do not own this effect perk.");
						}

						menuevt.setWillClose(true);
						return;

					}
				}

				// The effect name did not match.
				p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "The perk you selected does not exist.");
				menuevt.setWillClose(true);
				return;

			}
		}, Arenagames.plugin);

		for (int i = 0; i < perkamt; i++) { // Checks which are owned by the player.
			EffectPerkUtil fx = EffectPerkUtil.values()[i];
			String owned = ChatColor.GREEN + "Owned";

			if (!PlayerPerk.isPerkOwned(p, fx)) {
				owned = ChatColor.RED + "Not Owned";
			}

			String name = ChatColor.RESET + "" + ChatColor.BOLD + fx.getName();
			menu.setOption(i, new ItemStack(fx.getMaterial(), 1), name, owned);

		}

		MENU_STORE.put(p, menu);

	}
}
