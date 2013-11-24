package me.taur.arenagames.shop;

import java.util.HashMap;

import me.taur.arenagames.Arenagames;
import me.taur.arenagames.player.PlayerEconomy;
import me.taur.arenagames.player.PlayerPerk;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.EffectPerk;
import me.taur.arenagames.util.IconMenu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShopEffectPerkConfirm {
	public static HashMap<Player, IconMenu> MENU_STORE = new HashMap<Player, IconMenu>();

	public static void openMenu(final Player p, final EffectPerk fx) {
		Bukkit.getScheduler().runTaskLater(Arenagames.plugin, new Runnable() {
			public void run() {
				if (MENU_STORE.containsKey(p)) { // Destroy previous opened item.
					MENU_STORE.get(p).destroy();
				}

				generateMenu(p, fx);
				MENU_STORE.get(p).open(p);

			}
		}, 2L);
	}

	public static void generateMenu(Player p, final EffectPerk fx) {
		IconMenu menu = new IconMenu("Confirm: " + fx.getName(), 9, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent menuevt) {
				menuevt.setWillDestroy(true); // Destroy this object after it is used.

				Player p = menuevt.getPlayer();

				if (Room.PLAYERS.containsKey(p)) { // Cannot buy in game.
					menuevt.setWillClose(true);
					return;

				}

				String name = ChatColor.stripColor(menuevt.getName()); // Clear colors because we add colors in the menu name.

				if (!PlayerPerk.isPerkOwned(p, fx)) {
					if (name.contains("Purchase")) {
						boolean hasCash = PlayerEconomy.hasEnoughCash(p, fx.getCashCost());
						boolean hasCurrency = PlayerEconomy.hasEnoughCurrency(p, fx.getCurrencyCost());

						if (hasCash && hasCurrency) {
							PlayerEconomy.changeCash(p, 0 - fx.getCashCost()); // Subtract cost from player's account.
							PlayerEconomy.changeCurrency(p, 0 - fx.getCurrencyCost()); // Subtract cost from player's account.

							PlayerPerk.grantPerk(p, fx);
							
							p.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "You have purchased the " + fx.getName() + "perk.");

						} else {
							p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You don\'t have enough to purchase this perk.");
						}

					} else {
						p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You have cancelled the purchase of this perk.");
					}

				} else { // If the player already owns the effect.
					p.sendMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + "You already own this effect perk.");
				}

				menuevt.setWillClose(true);
				return;

			}
		}, Arenagames.plugin);

		String buy = ChatColor.RESET + "" + ChatColor.GREEN + ChatColor.BOLD + "Purchase";
		String cost = ChatColor.YELLOW + "Cost: " + ChatColor.ITALIC;
		if (fx.getCurrencyCost() > 0 && fx.getCashCost() < 1) {
			cost = cost + fx.getCurrencyCost() + " Ngt";
		} else if (fx.getCashCost() > 0 && fx.getCurrencyCost() < 1) {
			cost = cost + fx.getCashCost() + " AC";
		} else if (fx.getCurrencyCost() > 0 && fx.getCashCost() > 0) {
			cost = cost + fx.getCashCost() + " AC & " + fx.getCurrencyCost() + " Ngt";
		} else {
			cost = cost + "Free";
		}

		menu.setOption(6, new ItemStack(Material.EMERALD_BLOCK, 1), buy, cost);

		String cancel = ChatColor.RESET + "" + ChatColor.RED + ChatColor.BOLD + "Cancel";
		String info = ChatColor.RESET + "" + ChatColor.YELLOW + "I don\'t want to buy anymore.";
		menu.setOption(8, new ItemStack(Material.REDSTONE_BLOCK, 1), cancel, info);

		MENU_STORE.put(p, menu);

	}
}
