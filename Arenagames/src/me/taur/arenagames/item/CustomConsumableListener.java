package me.taur.arenagames.item;

import java.util.Random;

import me.taur.arenagames.chat.ChatUtil;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.ParticleUtil;
import me.taur.arenagames.util.Sym;

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
			evt.setCancelled(true); // The pot is returned to the player.
			
			if (p.getFoodLevel() != 20) {
				p.sendMessage(ChatUtil.gameErrorMsg("You need 10 " + Sym.HUNGER + " to activate this ability."));
				return;
				
			}
			
			p.setHealth(p.getHealth() / 2);
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 10, 0, true));
			p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 10, 2, true));
			
			ParticleUtil.LAVA_SPARK.sendToLocation(p.getLocation(), 2.0F, 1.0F, 20);
			ParticleUtil.FIRE.sendToLocation(p.getLocation(), 1.0F, 1.0F, 30);
			p.playSound(p.getLocation(), Sound.WOLF_BARK, 1.0F, 0.3F);
			
			p.setFoodLevel(6); // 3.0 Hunger
			
			p.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.ITALIC + "You activated Berserker for 10 sec.");
			
			return;

		} else if (ChatColor.stripColor(im).equalsIgnoreCase("Elixir Of Sacrifice")) {
			evt.setCancelled(true); // The pot is returned to the player.
			
			if (p.getFoodLevel() != 20) {
				p.sendMessage(ChatUtil.gameErrorMsg("You need 10 " + Sym.HUNGER + " to activate this ability."));
				return;
				
			}
			
			Random rand = new Random();
			int r = rand.nextInt(4);
			
			if (r == 0) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 3, 0, true));
				p.sendMessage(ChatUtil.gameInfoMsg("You sacrificed and is inflicted Slow I for 3 sec."));
				ParticleUtil.ANGRY_VILLAGER.sendToLocation(p.getLocation().add(0.0, 2.5, 0.0), 0.0F, 0.0F, 1);
				p.playSound(p.getLocation(), Sound.GHAST_SCREAM, 0.5F, 0.7F);
				
			} else if (r == 1) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 6, 0, true));
				p.sendMessage(ChatUtil.gameInfoMsg("You sacrificed and is granted Speed II for 6 sec."));
				ParticleUtil.SPARKLE.sendToLocation(p.getLocation().add(0.0, 2.5, 0.0), 0.3F, 0.3F, 8);
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 0.7F);
				
			} else if (r == 2) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 12, 0, true));
				p.sendMessage(ChatUtil.gameInfoMsg("You sacrificed and is granted Damage Resistance I for 12 sec."));
				ParticleUtil.SPARKLE.sendToLocation(p.getLocation().add(0.0, 2.5, 0.0), 0.3F, 0.3F, 8);
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 0.7F);
				
			} else {
				p.getWorld().strikeLightningEffect(p.getLocation());
				p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 4, 0, true));
				p.sendMessage(ChatUtil.gameInfoMsg("You sacrificed and is punished."));
				ParticleUtil.ANGRY_VILLAGER.sendToLocation(p.getLocation().add(0.0, 2.5, 0.0), 0.0F, 0.0F, 1);
				p.playSound(p.getLocation(), Sound.GHAST_SCREAM, 0.5F, 0.7F);
				
			}
			
			p.setFoodLevel(6); // 3.0 Hunger
			
			return;

		}
	}
}