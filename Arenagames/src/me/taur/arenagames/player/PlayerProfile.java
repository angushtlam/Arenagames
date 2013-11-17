package me.taur.arenagames.player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.taur.arenagames.util.GameMathUtil;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerProfile {
	public static Set<Player> READ = new HashSet<Player>();
	public static String FANCY_HEADING = ChatColor.BLACK + "\u2523\u2542 \u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501 \u2542\u252b" + ChatColor.RESET + "\n";
	
	public static List<String> bookInformation(Player p) {
		List<String> page = new ArrayList<String>();
		if (PlayerData.isLoaded(p)) { // Make sure the PlayerData exists.
			PlayerData data = PlayerData.get(p);
			
			page.add(FANCY_HEADING +
					 ChatColor.GOLD + "" + ChatColor.BOLD + "       Profile" + ChatColor.RESET + "\n" +
					 FANCY_HEADING +
					 "\u258b " + (Premium.isPremium(p) ? ChatColor.GOLD + "" : "") + ChatColor.BOLD + "" + p.getName() + (Premium.isPremium(p) ? " /P" : "") + ChatColor.RESET + "\n" + 
					 ChatColor.BLACK + "\u258f  " + data.getExp() + ChatColor.ITALIC + " EXP" + ChatColor.RESET + "\n" +
					 "\n" +
					 "\u258b " + "Violations: " + data.getViolationLevel());
			
			if (Premium.isPremium(p)) {
				page.add(FANCY_HEADING +
						 ChatColor.GOLD + "" + ChatColor.BOLD + "       Profile" + ChatColor.RESET + "\n" +
						 FANCY_HEADING +
						 "\u258b You have " + (Premium.daysLeft(p) + 1) + ChatColor.RESET + "\n" +
						 "\u258f  days of Premium left." +
						 "\n\n" +
						 "\u258b Nuggets: " + data.getCurrency() + ChatColor.RESET + "\n" +
						 "\u258f  Alphacash: " + data.getCash());
			} else {
				page.add(FANCY_HEADING +
						 ChatColor.GOLD + "" + ChatColor.BOLD + "       Profile" + ChatColor.RESET + "\n" +
						 FANCY_HEADING +
						 " Support the server\n" + ChatColor.RESET +
						 "by purchasing Premium!\n" + ChatColor.RESET +
						 "\n\n" +
						 "\u258b Nuggets: " + data.getCurrency() + ChatColor.RESET + "\n" +
						 "\u258f  Alphacash: " + data.getCash());
			}	
			
			page.add(FANCY_HEADING +
					 ChatColor.DARK_RED + "" + ChatColor.BOLD + "   Free-For-All" + ChatColor.RESET + ChatColor.RESET + "\n" +
					 FANCY_HEADING +
					 "\u258b " + "1st Place: " + data.getFfaGamesWon() + ChatColor.RESET + "\n" +
					 "\u258f  " + "Games: " + data.getFfaGamesPlayed() + ChatColor.RESET + "\n" +
					 "\u258f  " + "Elo: " + data.getFfaRanking() + ChatColor.RESET + "\n" +
					 "\u258f  " + "Record: " + data.getFfaRecord() + ChatColor.RESET + "\n" +
					 "\u258f  " + "KDR: " + GameMathUtil.kdrCalculator(data.getFfaTotalKills(), data.getFfaTotalDeaths()) + ChatColor.RESET + "\n" +
					 "\n" +
					 "\u258b " + "Ngts Obt'd.: " + data.getFfaCurrencyEarned());
			
			page.add(FANCY_HEADING +
					 ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "       Lifeline\n" + ChatColor.RESET + "" +
					 FANCY_HEADING +
					 "\u258b " + "1st Place: " + data.getLflGamesWon() + ChatColor.RESET + "\n" +
					 "\u258f  " + "Games: " + data.getLflGamesPlayed() + ChatColor.RESET + "\n" +
					 "\u258f  " + "Elo: " + data.getLflRanking() + ChatColor.RESET + "\n" +
					 "\u258f  " + "Record: " + data.getLflRecord() + ChatColor.RESET + "\n" +
					 "\u258f  " + "KDR: " + GameMathUtil.kdrCalculator(data.getLflTotalKills(), data.getLflTotalDeaths()) + ChatColor.RESET + "\n" +
					 "\n" +
					 "\u258b " + "Ngts Obt'd.: " + data.getLflCurrencyEarned());
			
		} else {
			page.add(ChatColor.ITALIC + "Error loading PlayerData.");
		}
		
		return page;
		
	}
}
