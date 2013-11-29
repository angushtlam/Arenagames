package me.taur.arenagames.fix;

import me.taur.arenagames.util.ParticleUtil;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ArrowFix implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void removeArrows(ProjectileHitEvent evt) {
		Entity ent = evt.getEntity();
		
		if (ent instanceof Arrow) {
			ParticleUtil.CRITICAL.sendToLocation(ent.getLocation(), 0.0F, 0.0F, 3);
			ent.remove();
			
		}
	}
}
