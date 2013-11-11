package me.taur.arenagames.player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerProfile {
	public static Set<Player> READ = new HashSet<Player>();
	public static String LINE_DIVIDER = ChatColor.GRAY + "" + ChatColor.BOLD + "\u2598\u2598\u2598\u2598\u2598\u2598\u2598\u2598\u2598\u2598\u2598\u2598\u2598\u2598\u2598\u2598\u2598\u2598\n" + ChatColor.RESET;
	
	public static List<String> bookInformation(Player p) {
		List<String> page = new ArrayList<String>();
		if (PlayerData.isLoaded(p)) { // Make sure the PlayerData exists.
			PlayerData data = PlayerData.STORE.get(p);
			
			page.add(ChatColor.BLACK + "" + ChatColor.BOLD + "Profile\n" + ChatColor.RESET + "" +
					 LINE_DIVIDER +
					 ChatColor.BLACK + "" + ChatColor.BOLD + "Username: \n" + ChatColor.RESET + "" +
					 ChatColor.BLACK + p.getName() + "\n\n" + ChatColor.RESET + "" +
					 ChatColor.BLACK + "" + ChatColor.BOLD + "Premium: " + ChatColor.RESET + (data.isPremium() ? "\u2714" : "\u3128"));
			
			page.add(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Free-For-All\n" + ChatColor.RESET + "" +
					 LINE_DIVIDER +
					 ChatColor.BLACK + "" + ChatColor.BOLD + "Games Played: \n" + ChatColor.RESET + "" +
					 ChatColor.BLACK + data.getFfaGamesPlayed() + "\n\n" + ChatColor.RESET + "" +
					 ChatColor.BLACK + "" + ChatColor.BOLD + "Elo Ranking: \n" + ChatColor.RESET + "" +
					 ChatColor.BLACK + data.getFfaEloRank() + "\n\n" + ChatColor.RESET + "" +
					 ChatColor.BLACK + "" + ChatColor.BOLD + "Highest Record: \n" + ChatColor.RESET + "" +
					 ChatColor.BLACK + data.getFfaRecord());
			
			page.add(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Lifeline\n" + ChatColor.RESET + "" +
					 LINE_DIVIDER +
					 ChatColor.BLACK + "" + ChatColor.BOLD + "Games Played: \n" + ChatColor.RESET + "" +
					 ChatColor.BLACK + data.getLflGamesPlayed() + "\n\n" + ChatColor.RESET + "" +
					 ChatColor.BLACK + "" + ChatColor.BOLD + "Elo Ranking: \n" + ChatColor.RESET + "" +
					 ChatColor.BLACK + data.getLflEloRank() + "\n\n" + ChatColor.RESET + "" +
					 ChatColor.BLACK + "" + ChatColor.BOLD + "Highest Record: \n" + ChatColor.RESET + "" +
					 ChatColor.BLACK + data.getLflRecord());
			
		} else {
			page.add(ChatColor.ITALIC + "Error loading PlayerData.");
			
		}
		
		return page;
		
	}
}
