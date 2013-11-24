package me.taur.arenagames.item;

import me.taur.arenagames.lfl.LflRoom;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.ParticleEffect;
import me.taur.arenagames.util.RoomType;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CustomItemListener implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void customItem(PlayerInteractEvent evt) {
		ItemStack i = evt.getPlayer().getItemInHand();
		if (i.getType() == Material.AIR) {
			return;
		}
		
		if (!i.hasItemMeta()) {
			return;
		}
		
		String im = i.getItemMeta().getDisplayName();
		
		if (im == null) {
			return;
		}
		
		Player p = evt.getPlayer();
		
		if (!Room.PLAYERS.containsKey(p)) { // This only applies in gamemodes.
			return;
		}
		
		if (Room.ROOMS.get(Room.PLAYERS.get(p)).getRoomType() == RoomType.LFL) { // Only apply check when gamemode is Lifeline.
			LflRoom room = (LflRoom) Room.ROOMS.get(Room.PLAYERS.get(p));
			if (room.isGameInProgress()) {
				if (room.isPlayerDead(p)) { // Make sure the player cannot cast spells if they are dead.
					p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You cannot use skills while dead.");
					return;
					
				}
			}
		}
		
		if (ChatColor.stripColor(im).equalsIgnoreCase("Command: Vacuum")) {
			if (CustomItem.COMMAND_VACUUM_TIMER.containsKey(p)) {
				int sec = CustomItem.COMMAND_VACUUM_TIMER.get(p);
				p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "Command: Vacuum is on cooldown. (" + sec + "s left)");
				return;
				
			}
			
			if (p.getFoodLevel() < 8) {
				p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "Command: Vacuum needs 4 Food to use.");
				return;
				
			}
			
			p.setFoodLevel(p.getFoodLevel() - 8);
			SpellUtil.forceKnockbackZone(p, Sound.CLICK, 8, -6.0F, 4.0F, 2.0F, 1.0F);
			p.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.ITALIC + "You used Command: Vacuum. (22s cooldown)");
			CustomItem.COMMAND_VACUUM_TIMER.put(p, 22);
			return;
			
		} else if (ChatColor.stripColor(im).equalsIgnoreCase("Command: Endure")) {
			if (CustomItem.COMMAND_ENDURE_TIMER.containsKey(p)) {
				int sec = CustomItem.COMMAND_ENDURE_TIMER.get(p);
				p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "Command: Endure is on cooldown. (" + sec + "s left)");
				return;
				
			}
			
			if (p.getFoodLevel() < 2) {
				p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "Command: Vacuum needs 1 Food to use.");
				return;
				
			}
			
			p.setFoodLevel(p.getFoodLevel() - 2);
			
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 3, 1, true));
			ParticleEffect.HAPPY_VILLAGER.display(p.getLocation(), 0.5F, 2.0F, 0.5F, 20, 20);
			
			p.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.ITALIC + "You used Command: Endure. (8s cooldown)");
			CustomItem.COMMAND_ENDURE_TIMER.put(p, 8);
			return;
			
			
		} else {
			return;
			
		}
	}
}