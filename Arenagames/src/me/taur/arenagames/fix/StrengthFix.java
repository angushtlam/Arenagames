package me.taur.arenagames.fix;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StrengthFix implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDamage(EntityDamageByEntityEvent evt) {
		if (evt.getDamager() instanceof Player) {
			Player p = (Player) evt.getDamager();
			
			if (p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
				for (PotionEffect eff : p.getActivePotionEffects()) {
					if (eff.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
						double div = (eff.getAmplifier() + 1) * 1.3D + 1.0D;
						int dmg;
						
						if (evt.getDamage() / div <= 1.0D) {
							dmg = (eff.getAmplifier() + 1) * 3 + 1;
						} else {
							double flatdmg = 2.0;
							dmg = (int) (evt.getDamage() / div) + (int) ((eff.getAmplifier() + 1) * flatdmg);
						}
						
						evt.setDamage(dmg);
						break;
						
					}
				}
			}
		}
	}
}
