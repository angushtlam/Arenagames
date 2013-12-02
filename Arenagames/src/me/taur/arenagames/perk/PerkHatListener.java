package me.taur.arenagames.perk;

import java.util.Random;

import me.taur.arenagames.event.PerkHatChangeEvent;
import me.taur.arenagames.item.InvUtil;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PerkHatListener implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void updatePlayerHat(PerkHatChangeEvent evt) {
		if (evt.getPlayer() != null) {
			Player p = evt.getPlayer();
			PlayerInventory inv = p.getInventory();
			
			if (!PerkHat.ACTIVE_HAT_PERK.containsKey(p)) {
				inv.setHelmet(null);
				InvUtil.updatePlayerInv(p);
				return;
				
			}
			
			HatPerkUtil hat = PerkHat.ACTIVE_HAT_PERK.get(p);
			ItemStack is = new ItemStack(Material.LAVA, 1);
			
			switch (hat) {
			case BEDROCK:
				is = new ItemStack(Material.BEDROCK, 1);
				inv.setHelmet(is);
				break;
				
			case BOOKSHELF:
				is = new ItemStack(Material.BOOKSHELF, 1);
				inv.setHelmet(is);
				break;
				
			case BRICK:
				is = new ItemStack(Material.BRICK, 1);
				inv.setHelmet(is);
				break;
				
			case CHEST:
				is = new ItemStack(Material.CHEST, 1);
				inv.setHelmet(is);
				break;
				
			case COAL:
				is = new ItemStack(Material.COAL_BLOCK, 1);
				inv.setHelmet(is);
				break;
				
			case DIAMOND:
				is = new ItemStack(Material.DIAMOND_BLOCK, 1);
				inv.setHelmet(is);
				break;
				
			case EMERALD:
				is = new ItemStack(Material.EMERALD_BLOCK, 1);
				inv.setHelmet(is);
				break;
				
			case ENDER_CHEST:
				is = new ItemStack(Material.ENDER_CHEST, 1);
				inv.setHelmet(is);
				break;
				
			case GLASS:
				is = new ItemStack(Material.STAINED_GLASS, 1, (byte) new Random().nextInt(16));
				inv.setHelmet(is);
				break;
				
			case GOLD:
				is = new ItemStack(Material.GOLD_BLOCK, 1);
				inv.setHelmet(is);
				break;
				
			case ICE:
				is = new ItemStack(Material.ICE, 1);
				inv.setHelmet(is);
				break;
				
			case IRON:
				is = new ItemStack(Material.IRON_BLOCK, 1);
				inv.setHelmet(is);
				break;
				
			case LAPIS:
				is = new ItemStack(Material.LAPIS_BLOCK, 1);
				inv.setHelmet(is);
				break;
				
			case MELON:
				is = new ItemStack(Material.MELON_BLOCK, 1);
				inv.setHelmet(is);
				break;
				
			case NOTE:
				is = new ItemStack(Material.NOTE_BLOCK, 1);
				inv.setHelmet(is);
				break;
				
			case PUMPKIN:
				is = new ItemStack(Material.JACK_O_LANTERN, 1);
				inv.setHelmet(is);
				break;
				
			case REDSTONE:
				is = new ItemStack(Material.REDSTONE_BLOCK, 1);
				inv.setHelmet(is);
				break;
				
			case SOULSAND:
				is = new ItemStack(Material.SOUL_SAND, 1);
				inv.setHelmet(is);
				break;
				
			case SPONGE:
				is = new ItemStack(Material.SPONGE, 1);
				inv.setHelmet(is);
				break;
				
			case TNT:
				is = new ItemStack(Material.TNT, 1);
				inv.setHelmet(is);
				break;
				
			default:
				inv.setHelmet(null);
				break;
				
			}
			
			InvUtil.updatePlayerInv(p);
			
		}
	}
}