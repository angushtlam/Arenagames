package me.taur.arenagames.player;

import me.taur.arenagames.Arenagames;
import me.taur.arenagames.util.InvUtil;

import org.bukkit.Bukkit;
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
		
		ItemStack book = InvUtil.getProfileBook();
		BookMeta b = (BookMeta) book.getItemMeta();
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