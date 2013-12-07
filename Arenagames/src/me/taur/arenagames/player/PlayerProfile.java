package me.taur.arenagames.player;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import me.taur.arenagames.util.GameMathUtil;
import me.taur.arenagames.util.RoomType;
import me.taur.arenagames.util.Sym;
import me.taur.arenagames.util.TimeUtil;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerProfile {
	public static Set<Player> READ = new HashSet<Player>();
	public static String FANCY_HEADING = ChatColor.BLACK + "\u2523\u2542 \u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501 \u2542\u252b" + ChatColor.RESET + "\n";
	
	public static List<String> bookInformation(Player p) {
		List<String> page = new ArrayList<String>();
		if (PlayerData.isLoaded(p)) { // Make sure the PlayerData exists.
			PlayerData data = PlayerData.get(p);
			
			String name = p.getName();
			
			if (Permission.isModerator(p)) {
				name = ChatColor.DARK_RED + "" + ChatColor.BOLD + Sym.ADMIN + name;
			} else if (Permission.isPremium(p)){
				name = ChatColor.GOLD + Sym.PREMIUM + ChatColor.UNDERLINE + name;
			}
			
			page.add(FANCY_HEADING +
					 ChatColor.GOLD + "" + ChatColor.BOLD + "       Profile" + ChatColor.RESET + "\n" +
					 FANCY_HEADING +
					 Sym.TAB + name + ChatColor.RESET + "\n" + ChatColor.BLACK +
					 Sym.SUB + data.getExp() + ChatColor.ITALIC + " EXP" + ChatColor.RESET + "\n" +
					 "\n" +
					 Sym.TAB + "Nuggets: " + data.getCurrency() + ChatColor.RESET + "\n" +
					 Sym.SUB + "Total Ngts.: " + data.getCurrencyLifetime() + ChatColor.RESET + "\n" +
					 "\n" +
					 Sym.TAB + "Violations: " + data.getViolationLevel());
			
			NumberFormat df = NumberFormat.getCurrencyInstance(Locale.US); // Formats the user's money spent double into USD.
			String moneyspent = df.format(data.getMoneySpent());
			
			if (Permission.isPermaPremium(p)) {
				page.add(FANCY_HEADING +
						 ChatColor.GOLD + "" + ChatColor.BOLD + "       Profile" + ChatColor.RESET + "\n" +
						 FANCY_HEADING +
						 Sym.TAB + "You have " + Sym.INFINITE + ChatColor.RESET + "\n" +
						 Sym.SUB + "days of Premium left.\n" +
						 "\n" +
						 Sym.TAB + "Alphacash: " + data.getCash() + ChatColor.RESET + "\n" +
						 Sym.SUB + "Total AC.: " + data.getCashLifetime() + ChatColor.RESET + "\n" +
						 "\n" +
						 Sym.TAB + "USD Spent: " + moneyspent);
				
			} else if (Permission.isPremium(p)) {
				page.add(FANCY_HEADING +
						 ChatColor.GOLD + "" + ChatColor.BOLD + "       Profile" + ChatColor.RESET + "\n" +
						 FANCY_HEADING +
						 Sym.TAB + "You have " + (Permission.premiumDaysLeft(p) + 1) + ChatColor.RESET + "\n" +
						 Sym.SUB + "days of Premium left.\n" +
						 "\n" +
						 Sym.TAB + "Alphacash: " + data.getCash() + ChatColor.RESET + "\n" +
						 Sym.SUB + "Total AC.: " + data.getCashLifetime() + ChatColor.RESET + "\n" +
						 "\n" +
						 Sym.TAB + "USD Spent: " + moneyspent);
				
			} else {
				page.add(FANCY_HEADING +
						 ChatColor.GOLD + "" + ChatColor.BOLD + "       Profile" + ChatColor.RESET + "\n" +
						 FANCY_HEADING +
						 " Support the server\n" + ChatColor.RESET +
						 "by purchasing Premium!\n" + ChatColor.RESET +
						 "\n" +
						 Sym.TAB + "Alphacash: " + data.getCash() + ChatColor.RESET + "\n" +
						 Sym.SUB + "Lifetime AC.: " + data.getCashLifetime() + ChatColor.RESET + "\n" +
						 "\n" +
						 Sym.TAB + "USD Spent: " + moneyspent);
				
			}	
			
			page.add(FANCY_HEADING +
					 RoomType.FFA.getColor() + "" + ChatColor.BOLD + "   Free-For-All" + ChatColor.RESET + ChatColor.RESET + "\n" +
					 FANCY_HEADING +
					 Sym.TAB + "1st Place: " + data.getFfaGamesWon() + ChatColor.RESET + "\n" +
					 Sym.SUB + "Games: " + data.getFfaGamesPlayed() + ChatColor.RESET + "\n" +
					 Sym.SUB + "Elo: " + data.getFfaRanking() + ChatColor.RESET + "\n" +
					 Sym.SUB + "Record: " + data.getFfaRecord() + ChatColor.RESET + "\n" +
					 Sym.SUB + "KDR: " + GameMathUtil.kdrCalculator(data.getFfaTotalKills(), data.getFfaTotalDeaths()) + ChatColor.RESET + "\n" +
					 "\n" +
					 Sym.TAB + "Ngts Obt'd.: " + data.getFfaCurrencyEarned());
			
			page.add(FANCY_HEADING +
					 RoomType.TDM.getColor() + "" + ChatColor.BOLD + " Team Deathmatch\n" + ChatColor.RESET + "" +
					 FANCY_HEADING +
					 Sym.TAB + "Wins: " + data.getTdmGamesWon() + ChatColor.RESET + "\n" +
					 Sym.SUB + "Games: " + data.getTdmGamesPlayed() + ChatColor.RESET + "\n" +
					 Sym.SUB + "Elo: " + data.getTdmRanking() + ChatColor.RESET + "\n" +
					 Sym.SUB + "Record: " + data.getTdmRecord() + ChatColor.RESET + "\n" +
					 Sym.SUB + "KDR: " + GameMathUtil.kdrCalculator(data.getTdmTotalKills(), data.getTdmTotalDeaths()) + ChatColor.RESET + "\n" +
					 "\n" +
					 Sym.TAB + "Ngts Obt'd.: " + data.getTdmCurrencyEarned());
			
			page.add(FANCY_HEADING +
					 ChatColor.RED + "" + ChatColor.BOLD + "        Rules\n" + ChatColor.RESET + "" +
					 FANCY_HEADING +
					 Sym.TAB + "No cheating/hacking." + ChatColor.RESET + "\n" +
					 Sym.SUB + "No harassment." + ChatColor.RESET + "\n" +
					 Sym.SUB + "Don\'t beg. Please." + ChatColor.RESET + "\n" +
					 "\n" +
					 Sym.TAB + "Your actions will be" + ChatColor.RESET + "\n" +
					 Sym.SUB + "deemed appropriate" + ChatColor.RESET + "\n" +
					 Sym.SUB + "or not by a mod." + ChatColor.RESET + "\n");
			
			page.add(FANCY_HEADING +
					 ChatColor.YELLOW + "" + ChatColor.BOLD + "      Commands\n" + ChatColor.RESET + "" +
					 FANCY_HEADING +
					 Sym.TAB + "/queue <info/leave>" + ChatColor.RESET + "\n" +
					 Sym.SUB + ChatColor.ITALIC + " Queue info&leave" + ChatColor.RESET + "\n" +
					 "\n" +
					 Sym.TAB + "/chat <g/q/m/off>" + ChatColor.RESET + "\n" +
					 Sym.SUB + ChatColor.ITALIC + " Chat channels" + ChatColor.RESET + "\n" +
					 "\n" +
					 Sym.TAB + "/player <save>" + ChatColor.RESET + "\n" +
					 Sym.SUB + ChatColor.ITALIC + " Save your data" + ChatColor.RESET + "\n");
			
			String[] date = TimeUtil.millisecondsToDate(p.getFirstPlayed()).split("/");
			String month = date[1];
			String day = date[0];
			String year = date[2];
			
			page.add(FANCY_HEADING +
					 ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "      Statistics\n" + ChatColor.RESET + "" +
					 FANCY_HEADING +
					 Sym.TAB + "First Join Date:" + ChatColor.RESET + "\n" +
					 Sym.SUB + ChatColor.ITALIC + year + "/" + month + "/" + day + ChatColor.RESET + "\n");
			
		} else {
			page.add("\n\n\n\n" + ChatColor.ITALIC + " Loading PlayerData...");
		}
		
		return page;
		
	}
}
