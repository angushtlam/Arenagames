package me.taur.arenagames.item;

import java.util.HashMap;

import me.taur.arenagames.chat.ChatUtil;
import me.taur.arenagames.util.ParticleUtil;
import me.taur.arenagames.util.Sym;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

public class CustomProjectileListener implements Listener {
	public static HashMap<Projectile, String> PROJECTILES = new HashMap<Projectile, String>();
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onHitManager(EntityDamageEvent evt) {
		if (evt instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent edbeEvent = (EntityDamageByEntityEvent) evt;
			Entity damager = edbeEvent.getDamager();

			if (damager instanceof Projectile) {
				Projectile proj = (Projectile) damager;
				LivingEntity le = (LivingEntity) evt.getEntity();
				
				if (proj.getType() == EntityType.SNOWBALL) {
					evt.setCancelled(true); // Stop snowball knockback
				}
				
				if (PROJECTILES.containsKey(proj)) {
					if (PROJECTILES.get(proj).equals("Taste of Isolation")) {
						Location loc = proj.getShooter().getLocation();
						double distance = 5.0;
						
						if (loc.distance(le.getLocation()) < distance) {
							distance = loc.distance(le.getLocation());
						}
						
						SpellUtil.forceKnockEntity(proj.getShooter().getLocation(), le, -2.0F, 2.0F, 2.0F, (float) distance);
						ParticleUtil.ANGRY_VILLAGER.sendToLocation(le.getLocation().add(0.0, 2.5, 0.0), 0.0F, 0.0F, 1);

						if (le instanceof Player) {
							((Player) le).playSound(le.getLocation(), Sound.DIG_GRAVEL, 1.0F, 0.2F);
							((Player) le).playSound(le.getLocation(), Sound.DIG_GRAVEL, 1.0F, 0.4F);
						}
						
					} else if (PROJECTILES.get(proj).equals("Wondershot")) {
						if (proj.getShooter() instanceof Player) {
							Player p = (Player) proj.getShooter();
							p.setFoodLevel(Math.min(20, p.getFoodLevel() + 6)); // Refund 3 Hunger
							
						}
						
					}
					
					PROJECTILES.remove(proj);
					
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void projectileLaunchManager(ProjectileLaunchEvent evt) {
		Projectile ent = evt.getEntity(); // The entity launched.
		
		if (ent.getShooter() instanceof Player) {
			Player p = (Player) ent.getShooter();
			
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
			
			if (ChatColor.stripColor(im).equalsIgnoreCase("Taste of Isolation")) {
				i.setAmount(i.getAmount() + 1); // Return the item to the player
				InvUtil.updatePlayerInv(p);
				
				if ((p.getFoodLevel() - 4) < 0) {
					p.sendMessage(ChatUtil.gameErrorMsg("You need 2 " + Sym.HUNGER + " to fire Taste of Isolation."));
					evt.setCancelled(true);
					return;
					
				}
				
				p.setFoodLevel(p.getFoodLevel() - 4);
				PROJECTILES.put(evt.getEntity(), "Taste of Isolation");
				
			} else if (ChatColor.stripColor(im).equalsIgnoreCase("Wondershot")) {
				if ((p.getFoodLevel() - 14) < 0) {
					p.sendMessage(ChatUtil.gameErrorMsg("You need 7 " + Sym.HUNGER + " to fire with Wondershot."));
					evt.setCancelled(true);
					return;
					
				}
				
				p.setFoodLevel(p.getFoodLevel() - 14);
				PROJECTILES.put(evt.getEntity(), "Wondershot");
				
			}
			
		}
	}
}