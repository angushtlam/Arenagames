package me.taur.arenagames.item;

import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.ParticleUtil;

import org.bukkit.ChatColor;
import org.bukkit.Material;
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
	public void customItemInteract(PlayerInteractEvent evt) {
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

		if (ChatColor.stripColor(im).equalsIgnoreCase("Command: Lockdown")) {
			if (CustomItem.COMMAND_LOCKDOWN_TIMER.containsKey(p)) {
				int sec = CustomItem.COMMAND_LOCKDOWN_TIMER.get(p);
				p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "Command: Lockdown is on cooldown. (" + sec + " sec. left)");
				return;
				
			}
			
			if (p.getFoodLevel() < 2) {
				p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "Command: Lockdown needs 1 Food to use.");
				return;
				
			}
			
			p.setFoodLevel(p.getFoodLevel() - 2);
			
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 3, 2, true));
			p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 3, 4, true));
			
			ParticleUtil.SPARKLE.sendToLocation(p.getLocation().add(0.0, 2.5, 0.0), 0.2F, 0.2F, 8);
			ParticleUtil.CRAZY_SMOKE.sendToLocation(p.getLocation(), 0.3F, 2.0F, 8);
			ParticleUtil.EXPLODE.sendToLocation(p.getLocation(), 0.6F, 2.0F, 30);
			
			p.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.ITALIC + "You used Command: Lockdown. (9 sec. cooldown)");
			CustomItem.COMMAND_LOCKDOWN_TIMER.put(p, 9);
			return;
			
		} else {
			return;
			
		}
	}
}