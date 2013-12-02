package me.taur.arenagames.item;

import me.taur.arenagames.chat.ChatUtil;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.ParticleUtil;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CustomConsumableListener implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void customItem(PlayerItemConsumeEvent evt) {
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
		
		if (ChatColor.stripColor(im).equalsIgnoreCase("Elixir Of Berserkers")) {
			if (p.getFoodLevel() != 20) {
				p.sendMessage(ChatUtil.gameErrorMsg("You need 10 Hunger to activate this ability."));
				return;
				
			}
			
			p.setHealth(p.getHealth() / 2);
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 7, 1, true));
			p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 7, 0, true));
			
			ParticleUtil.LAVA_SPARK.sendToLocation(p.getLocation(), 3.0F, 1.0F, 20);
			ParticleUtil.FIRE.sendToLocation(p.getLocation(), 2.0F, 1.0F, 30);
			p.playSound(p.getLocation(), Sound.WOLF_BARK, 2.0F, 0.5F);
			
			p.setFoodLevel(0);
			
			p.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.ITALIC + "You activated Berserker for 7 sec.");

			evt.setCancelled(true); // The pot is returned to the player.
			return;

		} else {
			return;
			
		}
	}
}