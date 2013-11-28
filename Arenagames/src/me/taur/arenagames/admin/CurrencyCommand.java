package me.taur.arenagames.admin;

import me.taur.arenagames.player.PlayerData;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CurrencyCommand implements CommandExecutor {
	public boolean onCommand(CommandSender s, Command c, String l, String[] arg) {
		if (c.getName().equalsIgnoreCase("curr")) {
			if (arg.length < 1) { // If the sender did not specify any arguments.
				s.sendMessage(ChatColor.YELLOW + "Usage: " + ChatColor.ITALIC + "/curr <add/remove/set> <username> <amount> (money spent)");
				return true;

			}

			if (!s.hasPermission("arenagames.admin")) {
				s.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You have no permission.");
				return true;

			}

			if (arg.length > 4 || arg.length < 3) { // Must have at least 4 or 5 arguments
				s.sendMessage(ChatColor.YELLOW + "Usage: " + ChatColor.ITALIC + "/curr <add/remove/set> <username> <amount> (money spent)");
				return true;

			}
			
			double money = 0.0;

			if (arg.length == 4) { // Only if the sender specified an amount.
				try {
					Double.parseDouble(arg[3]);
				} catch (NumberFormatException e) { 
					s.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You have entered an invalid money amount.");
					return true;

				}

				money = Double.valueOf(arg[3]);

			}

			try { // Check if the amount of months specified by the player is correct.
				Integer.parseInt(arg[2]); 
			} catch (NumberFormatException e) { 
				s.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You have entered an invalid number for amount of currency.");
				return true;

			}

			int currency = Integer.valueOf(arg[2]);

			if (arg[1].length() > 16) { // Make sure the length of the player's name is correct.
				s.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You inserted an invalid username.");
				return true;

			}

			OfflinePlayer op = Bukkit.getOfflinePlayer(arg[1]);

			if (arg[0].equalsIgnoreCase("set")) {
				if (op.isOnline()) { // Directly save the player into their live PlayerData instance.
					Player pl = (Player) op;
					if (PlayerData.isLoaded(pl)) {
						PlayerData data = PlayerData.get(pl);

						if (currency < 1) {
							data.setCurrency(0);
						} else {
							data.setCurrency(currency);
							data.setCurrencyLifetime(currency + data.getFfaCurrencyEarned() + data.getCrkCurrencyEarned());
						}

						data.setMoneySpent(data.getMoneySpent() + money);
						data.save(pl);

					} else {
						PlayerData data = new PlayerData(pl);

						if (currency < 1) {
							data.setCurrency(0);
						} else {
							data.setCurrency(currency);
							data.setCurrencyLifetime(currency + data.getFfaCurrencyEarned() + data.getCrkCurrencyEarned());
						}

						data.setMoneySpent(data.getMoneySpent() + money);
						data.save(pl);

						if (!pl.isOnline()) { // Make sure once more if the player is online or not.
							PlayerData.STORE.remove(pl);
						}

					}

				} else {
					PlayerData data = new PlayerData(arg[1]);
					data.setCurrency(currency);
					data.setCurrencyLifetime(currency + data.getFfaCurrencyEarned() + data.getCrkCurrencyEarned());
					data.setMoneySpent(data.getMoneySpent() + money);
					data.save(arg[1]);

					if (!op.isOnline()) { // Make sure once more if the player is online or not.
						PlayerData.STORE.remove(op);
					}
				}

				s.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "You have set " + arg[1] + " to " + currency + (currency != 1 ? " nuggets" : " nugget") + ".");
				return true;

			} else if (arg[0].equalsIgnoreCase("add")) {
				if (op.isOnline()) { // Directly save the player into their live PlayerData instance.
					Player pl = (Player) op;
					if (PlayerData.isLoaded(pl)) {
						PlayerData data = PlayerData.get(pl);

						if (currency < 1) {
							s.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You need to add at least 1 nugget.");
							return true;

						}
						
						data.setCurrencyLifetime(data.getCurrencyLifetime() + currency);
						data.setCurrency(data.getCurrency() + currency);
						data.setMoneySpent(data.getMoneySpent() + money);
						data.save(pl);

					} else {
						PlayerData data = new PlayerData(pl);

						if (currency < 1) {
							s.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You need to add at least 1 nugget.");
							return true;

						}

						data.setCurrencyLifetime(data.getCurrencyLifetime() + currency);
						data.setCurrency(data.getCurrency() + currency);
						data.setMoneySpent(data.getMoneySpent() + money);
						data.save(pl);

						if (!pl.isOnline()) { // Make sure once more if the player is online or not.
							PlayerData.STORE.remove(pl);
						}						
					}

				} else {
					PlayerData data = new PlayerData(arg[1]);

					data.setCurrencyLifetime(data.getCurrencyLifetime() + currency);
					data.setCurrency(data.getCurrency() + currency);
					data.setMoneySpent(data.getMoneySpent() + money);
					data.save(arg[1]);

					if (!op.isOnline()) { // Make sure once more if the player is online or not.
						PlayerData.STORE.remove(op);
					}

				}

				s.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "You gave " + arg[1] + " " + currency + (currency != 1 ? " nuggets" : " nugget") + ".");
				return true;

			} else if (arg[0].equalsIgnoreCase("remove")) {
				if (op.isOnline()) { // Directly save the player into their live PlayerData instance.
					Player pl = (Player) op;
					if (PlayerData.isLoaded(pl)) {
						PlayerData data = PlayerData.get(pl);

						if (currency < 1) {
							s.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You need to remove at least 1 nugget.");
							return true;

						}
						
						if (data.getCurrencyLifetime() - currency < 0) { // Lifetime subtract has to be done first to make sure the lifetime currency doesn't go below 0.
							data.setCurrencyLifetime(data.getCurrencyLifetime() - data.getCurrency());
						} else {
							data.setCurrencyLifetime(data.getCurrencyLifetime() - currency);
						}
						
						if (data.getCurrency() - currency < 0) { // Make sure currency cannot go below 0
							data.setCurrency(0);
						} else {
							data.setCurrency(data.getCurrency() - currency);
						}
						
						data.setMoneySpent(data.getMoneySpent() + money);
						data.save(pl);

					} else {
						PlayerData data = new PlayerData(pl);

						if (currency < 1) {
							s.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You need to remove at least 1 nugget.");
							return true;

						}

						if (data.getCurrencyLifetime() - currency < 0) { // Lifetime subtract has to be done first to make sure the lifetime currency doesn't go below 0.
							data.setCurrencyLifetime(data.getCurrencyLifetime() - data.getCurrency());
						} else {
							data.setCurrencyLifetime(data.getCurrencyLifetime() - currency);
						}
						
						if (data.getCurrency() - currency < 0) { // Make sure currency cannot go below 0
							data.setCurrency(0);
						} else {
							data.setCurrency(data.getCurrency() - currency);
						}
						
						data.setMoneySpent(data.getMoneySpent() + money);
						data.save(pl);

						if (!pl.isOnline()) { // Make sure once more if the player is online or not.
							PlayerData.STORE.remove(pl);
						}						
					}

				} else {
					PlayerData data = new PlayerData(arg[1]);
					
					if (data.getCurrencyLifetime() - currency < 0) { // Lifetime subtract has to be done first to make sure the lifetime currency doesn't go below 0.
						data.setCurrencyLifetime(data.getCurrencyLifetime() - data.getCurrency());
					} else {
						data.setCurrencyLifetime(data.getCurrencyLifetime() - currency);
					}
					
					if (data.getCurrency() - currency < 0) { // Make sure currency cannot go below 0
						data.setCurrency(0);
					} else {
						data.setCurrency(data.getCurrency() - currency);
					}
					
					data.setMoneySpent(data.getMoneySpent() + money);
					data.save(arg[1]);

					if (!op.isOnline()) { // Make sure once more if the player is online or not.
						PlayerData.STORE.remove(op);
					}

				}

				s.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "You removed " + currency + (currency != 1 ? " nuggets" : " nugget") + " from " + arg[1] + ".");
				return true;

			} else {
				s.sendMessage(ChatColor.YELLOW + "Usage: " + ChatColor.ITALIC + "/curr <add/remove/set> <username> <amount> (money spent)");
				return true;

			}
		}
		
		return false;
		
	}
}
