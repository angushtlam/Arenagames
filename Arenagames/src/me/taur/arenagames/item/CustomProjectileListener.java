package me.taur.arenagames.item;

import java.util.HashMap;

import me.taur.arenagames.chat.ChatUtil;
import me.taur.arenagames.crk.CrkRoom;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.ParticleUtil;
import me.taur.arenagames.util.RoomType;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
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
	public static HashMap<Entity, String> PROJECTILES = new HashMap<Entity, String>();
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onHitManager(EntityDamageEvent evt) {
		if (evt instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent edbeEvent = (EntityDamageByEntityEvent) evt;
			Entity damager = edbeEvent.getDamager();

			if (damager instanceof Projectile) {
				Projectile proj = (Projectile) damager;
				LivingEntity le = (LivingEntity) evt.getEntity();
				
				if (PROJECTILES.containsKey(proj)) {
					if (PROJECTILES.get(proj).equals("Taste of Isolation")) {
						SpellUtil.forceKnockEntity(proj.getShooter().getLocation(), le, -2.0F, 1.0F, 3.0F, 12.0F);
						ParticleUtil.ANGRY_VILLAGER.sendToLocation(le.getLocation().add(0.0, 2.5, 0.0), 0.0F, 0.0F, 1);

						if (le instanceof Player) {
							((Player) le).playSound(le.getLocation(), Sound.DIG_GRAVEL, 0.5F, 0.5F);
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
			
			if (Room.ROOMS.get(Room.PLAYERS.get(p)).getRoomType() == RoomType.CRK) { // Only apply check when gamemode is Cranked.
				CrkRoom room = (CrkRoom) Room.ROOMS.get(Room.PLAYERS.get(p));
				if (room.isGameInProgress()) {
					if (room.isPlayerDead(p)) { // Make sure the player cannot use custom weapons if they are dead.
						return;
					}
				}
			}
			
			if (ChatColor.stripColor(im).equalsIgnoreCase("Taste of Isolation")) {
				PROJECTILES.put(evt.getEntity(), "Taste of Isolation");
				
			} else if (ChatColor.stripColor(im).equalsIgnoreCase("Bane Of The Forest")) {
				if ((p.getFoodLevel() - 6) < 0) {
					p.sendMessage(ChatUtil.gameErrorMsg("You need 3 Hunger to fire with Bane Of The Forest."));
					ent.remove();
					
				}
				
				p.setFoodLevel(p.getFoodLevel() - 8);
				
			}
			
		}
	}
}