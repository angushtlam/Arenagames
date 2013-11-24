package me.taur.arenagames.shop;

import me.taur.arenagames.util.ParticleEffect;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ShopSignListener implements Listener {	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void joinRoomSign(PlayerInteractEvent evt) {
		if (evt.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}

		Block b = evt.getClickedBlock();
		if (!b.getType().name().contains("SIGN")) {
			return;
		}

		Sign sign = (Sign) b.getState();
		if (sign.getLine(0).contains("[Perk Shop]")) {
			Player p = evt.getPlayer();
			Shop.shopMenu.open(p);
			ParticleEffect.HAPPY_VILLAGER.display(b.getLocation().add(0.5, 1.0, 0.5), 0.1F, 0.1F, 0.1F, 10, 3);
			
		}
	}
}