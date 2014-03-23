package me.taur.arenagames.perk;

import java.util.HashMap;

import me.taur.arenagames.Arenagames;
import me.taur.arenagames.chat.ChatUtil;
import me.taur.arenagames.player.Perk;
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

		IconMenu menu = new IconMenu(ChatColor.AQUA + "" + ChatColor.BOLD + p.getName() + "\'s Effects", lines, new IconMenu.OptionClickEventHandler() {
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
						if (Perk.hasPerk(p, fx)) {
							if (ACTIVE_EFFECT_PERK.containsKey(p)) { // If the player already has an active effect perk.
								if (ACTIVE_EFFECT_PERK.get(p).equals(fx)) {
									p.sendMessage(ChatUtil.basicInfoMsg("You have disabled the " + name + " effect."));
									ACTIVE_EFFECT_PERK.remove(p);

								} else {
									p.sendMessage(ChatUtil.basicSuccessMsg("You have activated the " + name + " effect."));
									ACTIVE_EFFECT_PERK.put(p, fx);

								}

							} else { // If this player just selected a new perk.
								p.sendMessage(ChatUtil.basicSuccessMsg("You have activated the " + name + " effect."));
								ACTIVE_EFFECT_PERK.put(p, fx);

							}

						} else { // If the player has no permission for this perk.
							p.sendMessage(ChatUtil.basicErrorMsg("You do not own this effect."));
						}

						menuevt.setWillClose(true);
						return;

					}
				}

				// The effect name did not match.
				p.sendMessage(ChatUtil.basicErrorMsg("The effect you selected does not exist."));
				menuevt.setWillClose(true);
				return;

			}
		}, Arenagames.plugin);

		for (int i = 0; i < perkamt; i++) { // Checks which are owned by the player.
			EffectPerkUtil fx = EffectPerkUtil.values()[i];
			String owned = ChatColor.GREEN + "Owned";

			if (!Perk.hasPerk(p, fx)) {
				owned = ChatColor.RED + "Not Owned";
			}

			String name = ChatColor.RESET + "" + ChatColor.BOLD + fx.getName();
			menu.setOption(i, new ItemStack(fx.getMaterial(), 1), name, owned);

		}

		MENU_STORE.put(p, menu);

	}
}
