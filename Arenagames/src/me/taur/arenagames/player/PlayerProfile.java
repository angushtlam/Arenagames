package me.taur.arenagames.player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerProfile {
	public static Set<Player> READ = new HashSet<Player>();
	public static String FANCY_HEADING = "\u2523\u2542 \u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501 \u2542\u252b\n" + ChatColor.RESET;
	
	public static List<String> bookInformation(Player p) {
		List<String> page = new ArrayList<String>();
		if (PlayerData.isLoaded(p)) { // Make sure the PlayerData exists.
			PlayerData data = PlayerData.STORE.get(p);
			
			page.add(FANCY_HEADING +
					 ChatColor.GOLD + "" + ChatColor.BOLD + "       Profile" + ChatColor.RESET + "\n" +
					 FANCY_HEADING +
					 "\u258b " + (Premium.isPremium(p) ? ChatColor.GOLD + "" : "") + ChatColor.BOLD + "" + p.getName() + (Premium.isPremium(p) ? "[P]" : "") + ChatColor.RESET + "\n" +
					 "   " + data.getExp() + ChatColor.ITALIC + " EXP" + ChatColor.RESET + "\n" +
					 "\n" +
					 "\u258b " + "Violations: " + data.getViolationLevel());
			
			if (Premium.isPremium(p)) {
				page.add(FANCY_HEADING +
						 ChatColor.GOLD + "" + ChatColor.BOLD + "       Profile" + ChatColor.RESET + "\n" +
						 FANCY_HEADING +
						 "\u258b " + Premium.daysLeft(p) + " Premium Days Left" + ChatColor.RESET + "\n" +
						 "\n\n" +
						 "\u258b " + data.getCash() + " Alphacash" + ChatColor.RESET + "\n" +
						 "   " + data.getCurrency() + " \u20a6" + "uggets");
			} else {
				page.add(FANCY_HEADING +
						 ChatColor.GOLD + "" + ChatColor.BOLD + "       Profile" + ChatColor.RESET + "\n" +
						 FANCY_HEADING +
						 " Support the server" + ChatColor.RESET +
						 "by purchasing Premium!" + ChatColor.RESET +
						 "\n\n" +
						 "\u258b " + data.getCurrency() + " Nuggets" + ChatColor.RESET + "\n" +
						 "   " + data.getCash() + " Alphacash");
			}	
			
			page.add(FANCY_HEADING +
					 ChatColor.DARK_RED + "" + ChatColor.BOLD + "   Free-For-All" + ChatColor.RESET + ChatColor.RESET + "\n" +
					 FANCY_HEADING +
					 "\u258b " + "1st Place: " + data.getFfaGamesWon() + ChatColor.RESET + "\n" +
					 "   " + "Games: " + data.getFfaGamesPlayed() + ChatColor.RESET + "\n" +
					 "   " + "Elo: " + data.getFfaRanking() + ChatColor.RESET + "\n" +
					 "   " + "Record: " + data.getFfaRecord() + ChatColor.RESET + "\n" +
					 "   " + "KDR: " + GameMathUtil.kdrCalculator(data.getFfaTotalKills(), data.getFfaTotalDeaths()) + ChatColor.RESET + "\n" +
					 "\n" +
					 "\u258b " + "Nuggets Earned: " + data.getFfaCurrencyEarned());
			
			page.add(FANCY_HEADING +
					 ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "   Lifeline\n" + ChatColor.RESET + "" +
					 FANCY_HEADING +
					 "\u258b " + "1st Place: " + data.getLflGamesWon() + " Times" + "\n"+ ChatColor.RESET +
					 "   " + "Games Played: " + data.getLflGamesPlayed() + "\n"+ ChatColor.RESET +
					 "   " + "Elo Rank: " + data.getLflRanking() + " " + "\n"+ ChatColor.RESET +
					 "   " + "Personal Record: " + data.getLflRecord() + " " + "\n"+ ChatColor.RESET +
					 "   " + "KDR: " + GameMathUtil.kdrCalculator(data.getLflTotalKills(), data.getLflTotalDeaths()) + " " + "\n"+ ChatColor.RESET +
					 "\n" +
					 "\u258b " + "Earned in total " + data.getLflCurrencyEarned() + "\n20a6");
			
		} else {
			page.add(ChatColor.ITALIC + "Error loading PlayerData.");
		}
		
		return page;
		
	}
}
