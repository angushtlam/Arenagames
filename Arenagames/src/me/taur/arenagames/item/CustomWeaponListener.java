package me.taur.arenagames.item;

import java.util.HashMap;
import java.util.List;

import me.taur.arenagames.chat.ChatUtil;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.ParticleUtil;
import me.taur.arenagames.util.Sym;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CustomWeaponListener implements Listener {
	public static HashMap<Entity, String> PROJECTILES = new HashMap<Entity, String>();
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onHitManager(EntityDamageEvent evt) {
		if (evt instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent edbeEvent = (EntityDamageByEntityEvent) evt;
			Entity damager = edbeEvent.getDamager();

			if (damager instanceof Player) {
				Player p = (Player) damager;
				if (Room.PLAYERS.containsKey(p)) { // If the player who killed the player is playing
					if (!(evt.getEntity() instanceof LivingEntity)) {
						return;
					}
					
					LivingEntity le = (LivingEntity) evt.getEntity();
					if ((le.getHealth() - evt.getDamage()) < 1) { // Check if player was killed.
						return;
					}
					
					ItemStack i = p.getItemInHand();
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
					
					if (ChatColor.stripColor(im).equalsIgnoreCase("The Strangler")) {
						if ((p.getFoodLevel() - 1) < 0) {
							p.sendMessage(ChatUtil.gameErrorMsg("You need " + Sym.HALF + " " + Sym.HUNGER + " to attack with The Strangler (melee)."));
							evt.setCancelled(true);
							
						}
						
						p.setFoodLevel(p.getFoodLevel() - 1);
						
						p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 1, 0, true));
						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 1, 0, true));
						
						le.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 1, 2, true));
						le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 1, 0, true));
						ParticleUtil.ANGRY_VILLAGER.sendToLocation(le.getLocation().add(0.0, 2.5, 0.0), 0.0F, 0.0F, 1);

						if (le instanceof Player) {
							((Player) le).playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 0.5F, 0.5F);
						}
						
					} else if (ChatColor.stripColor(im).equalsIgnoreCase("Sick Scalpel")) {
						if ((p.getFoodLevel() - 2) > 0) { // Only apply if more than 1 Hunger
							p.setFoodLevel(p.getFoodLevel() - 2);
							
							le.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 5, 1), true);
							ParticleUtil.ANGRY_VILLAGER.sendToLocation(le.getLocation().add(0.0, 2.5, 0.0), 0.0F, 0.0F, 1);
							
						}
						
					} else if (ChatColor.stripColor(im).equalsIgnoreCase("Blade Noir")) {
						List<Entity> ents = SpellUtil.getNearbyEntities(p, 10);
						
						if (!ents.contains(evt.getEntity())) {
							return;
						}
						
						if (ents.size() > 1) { // If there are more players within 10 blocks.
							return;
						}
						
						evt.setDamage(evt.getDamage() + 3.0); // Deals 3 bonus hearts of damage.
						ParticleUtil.CRITICAL.sendToLocation(le.getLocation().add(0.0, 1.0, 0.0), 0.3F, 0.3F, 30);
						ParticleUtil.ANGRY_VILLAGER.sendToLocation(le.getLocation().add(0.0, 2.5, 0.0), 0.0F, 0.0F, 1);
						
					}
					
				}
				
			}
		}
	}
}