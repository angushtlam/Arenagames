package me.taur.arenagames.admin;

import me.taur.arenagames.player.PlayerData;
import me.taur.arenagames.util.TimeUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommand implements CommandExecutor {
	public boolean onCommand(CommandSender s, Command c, String l, String[] arg) {
		if (c.getName().equalsIgnoreCase("admin")) {
			if (arg.length < 1) {
				String msg = ChatColor.YELLOW + "Usage: " + ChatColor.ITALIC + "/admin <premium>";
				
				if (!(s instanceof Player)) {
					s.sendMessage(ChatColor.stripColor(msg));
				} else {
					s.sendMessage(msg);
				}
				
				return true;
				
			}
			
			if (arg[0].equalsIgnoreCase("premium")) {
				if (!s.hasPermission("arenagames.admin")) {
					s.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You have no permission.");
					return true;
					
				}
				
				if (arg.length > 5 || arg.length < 4) { // Must have at least 4 or 5 arguments
					s.sendMessage(ChatColor.YELLOW + "Usage: " + ChatColor.ITALIC + "/admin premium <add/set> <username> <month> (money spent)");
					return true;
					
				}
				
				double money = 0.0;
				
				if (arg.length == 5) { // Only if the sender specified an amount.
					try {
						Double.parseDouble(arg[4]);
					} catch (NumberFormatException e) { 
				    	s.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You have entered an invalid money amount.");
						return true;
						
				    }
					
					money = Double.valueOf(arg[4]);
					
				}
				
				try { 
			        Integer.parseInt(arg[3]); 
			    } catch (NumberFormatException e) { 
			    	s.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You have entered an invalid number for month.");
					return true;
					
			    }
				
				int month = Integer.valueOf(arg[3]);
				
				if (arg[1].length() > 16) { // Make sure the length of the player's name is correct.
					s.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You inserted an invalid username.");
					return true;
					
				}
				
				OfflinePlayer op = Bukkit.getOfflinePlayer(arg[2]);
				
				if (arg[1].equalsIgnoreCase("set")) {
					if (op.isOnline()) { // Directly save the player into their live PlayerData instance.
						Player pl = (Player) op;
						if (PlayerData.isLoaded(pl)) {
							PlayerData data = PlayerData.get(pl);
							
							if (month < 1) {
								data.setPremiumForMonths(0);
							} else {
								data.setPremiumForMonths(month);
							}
							
							data.setRecentPremiumPayment(TimeUtil.currentMilliseconds());
							data.setMoneySpent(data.getMoneySpent() + money);
							data.save(pl);
							
						} else {
							PlayerData data = new PlayerData(pl);
							
							if (month < 1) {
								data.setPremiumForMonths(0);
							} else {
								data.setPremiumForMonths(month);
							}
							
							data.setRecentPremiumPayment(TimeUtil.currentMilliseconds());
							data.setMoneySpent(data.getMoneySpent() + money);
							data.save(pl);
							
							if (!pl.isOnline()) { // Make sure once more if the player is online or not.
								PlayerData.STORE.remove(pl);
							}
							
						}
						
					} else {
						PlayerData data = new PlayerData(arg[2]);
						data.setPremiumForMonths(month);
						data.setRecentPremiumPayment(TimeUtil.currentMilliseconds());
						data.setMoneySpent(data.getMoneySpent() + money);
						data.save(arg[2]);
						
						if (!op.isOnline()) { // Make sure once more if the player is online or not.
							PlayerData.STORE.remove(op);
						}
					}
					
					s.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "You have set " + arg[2] + "\'s premium to " + month + (month != 1 ? " months" : " month") + ".");
					return true;
					
				} else if (arg[1].equalsIgnoreCase("add")) {
					if (op.isOnline()) { // Directly save the player into their live PlayerData instance.
						Player pl = (Player) op;
						if (PlayerData.isLoaded(pl)) {
							PlayerData data = PlayerData.get(pl);
							
							if (month < 1) {
								s.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You need to specify at least 1 month.");
								return true;
								
							} else {
								if (data.getPremiumForMonths() > 0) { // Extends the months. No need to change recent pay date.
									data.setPremiumForMonths(data.getPremiumForMonths() + month);
								} else {
									data.setPremiumForMonths(month);
									data.setRecentPremiumPayment(TimeUtil.currentMilliseconds());
									
								}
							}
							
							data.setMoneySpent(data.getMoneySpent() + money);
							data.save(pl);
							
						} else {
							PlayerData data = new PlayerData(pl);
							
							if (month < 1) {
								s.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You need to specify at least 1 month.");
								return true;
								
							} else {
								if (data.getPremiumForMonths() > 0) { // Extends the months. No need to change recent pay date.
									data.setPremiumForMonths(data.getPremiumForMonths() + month);
								} else {
									data.setPremiumForMonths(month);
									data.setRecentPremiumPayment(TimeUtil.currentMilliseconds());
									
								}
							}
							
							data.setMoneySpent(data.getMoneySpent() + money);
							data.save(pl);
							
							if (!pl.isOnline()) { // Make sure once more if the player is online or not.
								PlayerData.STORE.remove(pl);
							}
						}
						
						s.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "You have extended " + arg[2] + "\'s premium by " + month + (month != 1 ? " months" : " month") + ".");
						return true;
						
					} else {
						PlayerData data = new PlayerData(arg[2]);
						data.setPremiumForMonths(month);
						data.setRecentPremiumPayment(TimeUtil.currentMilliseconds());
						data.setMoneySpent(data.getMoneySpent() + money);
						data.save(arg[2]);
						
						if (!op.isOnline()) { // Make sure once more if the player is online or not.
							PlayerData.STORE.remove(op);
						}
						
					}
					
					s.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "You have added " + arg[2] + "\'s premium to a total of " + month + (month != 1 ? " months" : " month") + ".");
					return true;
					
				} else {
					s.sendMessage(ChatColor.YELLOW + "Usage: " + ChatColor.ITALIC + "/admin premium <add/set> <username> <month> (money spent)");
					return true;
					
				}

			}
			
			String msg = ChatColor.YELLOW + "Usage: " + ChatColor.ITALIC + "/admin <premium>";
			
			if (!(s instanceof Player)) {
				s.sendMessage(ChatColor.stripColor(msg));
			} else {
				s.sendMessage(msg);
			}
			
			return true;
		}
		
		return false;
	}
}