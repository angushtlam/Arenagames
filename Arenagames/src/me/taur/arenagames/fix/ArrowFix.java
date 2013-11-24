package me.taur.arenagames.fix;

import me.taur.arenagames.util.ParticleEffect;

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
			ParticleEffect.SNOWBALL_POOF.display(ent.getLocation(), 0.2F, 0.2F, 0.2F, 1, 4);
			ent.remove();
			
		}
	}
}
