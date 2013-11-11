package me.taur.arenagames.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.taur.arenagames.Arenagames;
import me.taur.arenagames.util.InvUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class PlayerProfileListener implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void playerReadProfile(PlayerInteractEvent evt) {
		ItemStack i = evt.getPlayer().getItemInHand();
		if (i.getType() == Material.AIR) {
			return;
		}
		
		if (!i.hasItemMeta()) {
			return;
		}
		
		String im = i.getItemMeta().getDisplayName();
		String prof = InvUtil.getProfileBook().getItemMeta().getDisplayName();
		
		if (im == null || prof == null) {
			return;
		}
		
		if (!im.equals(prof)) { // Make sure the item they're holding is the kit item.
			return;
		}
		
		final Player p = evt.getPlayer();
		
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta b = (BookMeta) book.getItemMeta();
		
		b.setDisplayName(ChatColor.GOLD + "Profile Book");
		b.setLore(Arrays.asList(ChatColor.GRAY + "" + ChatColor.ITALIC + "Right click to check your profile."));
		
		List<String> page = new ArrayList<String>();
		if (PlayerData.isLoaded(p)) { // Make sure the PlayerData exists.
			PlayerData data = PlayerData.STORE.get(p);
			
			page.add(ChatColor.BLACK + "" + ChatColor.BOLD + "Profile\n" + ChatColor.RESET + "" +
					 ChatColor.DARK_RED + "" + ChatColor.BOLD + " --- \n" + ChatColor.RESET +
					 ChatColor.BLACK + "" + ChatColor.BOLD + "Username: \n" + ChatColor.RESET + "" +
					 ChatColor.BLACK + p.getName() + "\n\n" + ChatColor.RESET + "" +
					 ChatColor.BLACK + "" + ChatColor.BOLD + "Premium: \n" + ChatColor.RESET + "" +
					 ChatColor.BLACK + (data.isPremium() ? "Yes" : "No"));
			
			page.add(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Free-For-All\n" + ChatColor.RESET + "" +
					 ChatColor.DARK_RED + "" + ChatColor.BOLD + " --- \n" + ChatColor.RESET +
					 ChatColor.BLACK + "" + ChatColor.BOLD + "Games Played: \n" + ChatColor.RESET + "" +
					 ChatColor.BLACK + data.getFfaGamesPlayed() + "\n\n" + ChatColor.RESET + "" +
					 ChatColor.BLACK + "" + ChatColor.BOLD + "Elo Ranking: \n" + ChatColor.RESET + "" +
					 ChatColor.BLACK + data.getFfaEloRank() + "\n\n" + ChatColor.RESET + "" +
					 ChatColor.BLACK + "" + ChatColor.BOLD + "Highest Record: \n" + ChatColor.RESET + "" +
					 ChatColor.BLACK + data.getFfaRecord());
			
			page.add(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Lifeline\n" + ChatColor.RESET + "" +
					 ChatColor.DARK_RED + "" + ChatColor.BOLD + " --- \n" + ChatColor.RESET +
					 ChatColor.BLACK + "" + ChatColor.BOLD + "Games Played: \n" + ChatColor.RESET + "" +
					 ChatColor.BLACK + data.getLflGamesPlayed() + "\n\n" + ChatColor.RESET + "" +
					 ChatColor.BLACK + "" + ChatColor.BOLD + "Elo Ranking: \n" + ChatColor.RESET + "" +
					 ChatColor.BLACK + data.getLflEloRank() + "\n\n" + ChatColor.RESET + "" +
					 ChatColor.BLACK + "" + ChatColor.BOLD + "Highest Record: \n" + ChatColor.RESET + "" +
					 ChatColor.BLACK + data.getLflRecord());
			
		} else {
			page.add(ChatColor.ITALIC + "Error loading PlayerData.");
			
		}
		
		b.setPages(PlayerProfile.bookInformation(p));
		book.setItemMeta(b);
		
		p.setItemInHand(book);
		
		if (!PlayerProfile.READ.contains(p)) {
			p.closeInventory(); // Need to update the book.
			PlayerProfile.READ.add(p);
			
			Bukkit.getScheduler().runTaskLater(Arenagames.plugin, new Runnable() { // Player has 6 seconds to read the book until it needs to reset again.
			    public void run() {
			    	PlayerProfile.READ.remove(p);
			    	
			    }
			}, 120L);
			
		} else {
			PlayerProfile.READ.remove(p); // If they are already reading, remove the player so the next time they read, it has to be reset again.
			
		}
		
		
	}
	
}