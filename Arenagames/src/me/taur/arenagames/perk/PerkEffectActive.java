package me.taur.arenagames.perk;

import java.util.Random;

import me.taur.arenagames.item.SpellUtil;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.ParticleUtil;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PerkEffectActive {
	public static void tick20() {
		for (Player p : PerkEffect.ACTIVE_EFFECT_PERK.keySet()) {
			if (Room.PLAYERS.containsKey(p)) {
				PerkEffect.ACTIVE_EFFECT_PERK.remove(p);
				p.sendMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Your effect perk has been turned off as you joined the queue.");
				continue;
				
			}
			
			EffectPerkUtil fx = PerkEffect.ACTIVE_EFFECT_PERK.get(p);
			
			if (fx.equals(EffectPerkUtil.STORM)) {
				Random rand = new Random();
				int r = rand.nextInt(4); 
				if (r == 0) {
					ParticleUtil.CRITICAL.sendToLocation(p.getLocation().add(0.0, 3.0, 0.0), 0.4F, 0.2F, 6);
				}
				
			}
			
			if (fx.equals(EffectPerkUtil.SUPERCHICKEN)) {
				Random rand = new Random();
				int r = rand.nextInt(8); 
				if (r == 0) {
					SpellUtil.itemSpray(p.getLocation(), new ItemStack(Material.EGG, 0), 1, 0.1F, 80);
				}
				
				SpellUtil.itemSpray(p.getLocation(), new ItemStack(Material.FEATHER, 0), 3, 1.0F, 10); // Feather falls out of player
				
			}
			
			if (fx.equals(EffectPerkUtil.KNOWLEDGE)) {
				Random rand = new Random();
				int r = rand.nextInt(8); 
				if (r == 0) {
					SpellUtil.itemSpray(p.getLocation(), new ItemStack(Material.BOOK, 0), 3, 0.1F, 80);
				}
				
			}

			if (fx.equals(EffectPerkUtil.BLOOD)) {
				Random rand = new Random();
				int r = rand.nextInt(4); 
				if (r == 0) {
					SpellUtil.itemSpray(p.getLocation(), new ItemStack(Material.ROTTEN_FLESH, 0), 1, 0.1F, 80);
				}
				
				SpellUtil.itemSpray(p.getLocation().add(0.0, -0.5, 0.0), new ItemStack(Material.BONE, 0), 2, 1.0F, 10); // Bones falls out of player
				
			}
			
		}
	}
	
	public static void tick5() {
		for (Player p : PerkEffect.ACTIVE_EFFECT_PERK.keySet()) {
			EffectPerkUtil fx = PerkEffect.ACTIVE_EFFECT_PERK.get(p);
			
			if (fx.equals(EffectPerkUtil.STORM)) {
				ParticleUtil.WATER_DRIP.sendToLocation(p.getLocation().add(0.0, 3.0, 0.0), 0.2F, 0.2F, 8);
				ParticleUtil.RISING_SMOKE.sendToLocation(p.getLocation().add(0.0, 3.0, 0.0), 0.2F, 0.1F, 16);
				
			}
			
			if (fx.equals(EffectPerkUtil.BLOOM)) {
				ParticleUtil.MOB_SPELL.sendToLocation(p.getLocation().add(0.0, 0.3, 0.0), 0.4F, 0.2F, 12);
			}
			
			if (fx.equals(EffectPerkUtil.MUSIC)) {
				ParticleUtil.NOTE.sendToLocation(p.getLocation().add(0.0, 0.3, 0.0), 0.4F, 0.2F, 16);
			}
			
			if (fx.equals(EffectPerkUtil.CLOUD)) {
				ParticleUtil.EXPLODE.sendToLocation(p.getLocation().add(0.0, 0.3, 0.0), 0.2F, 0.0F, 2);
				ParticleUtil.CLOUD.sendToLocation(p.getLocation().add(0.0, 0.3, 0.0), 0.4F, 0.1F, 12);
				ParticleUtil.SPELL.sendToLocation(p.getLocation().add(0.0, 0.3, 0.0), 0.4F, 0.2F, 8);
				
			}
			
			if (fx.equals(EffectPerkUtil.SOIL)) {
				ParticleUtil.VOID.sendToLocation(p.getLocation().add(0.0, 0.0, 0.0), 0.3F, 0.0F, 180);
			}
			
			if (fx.equals(EffectPerkUtil.ENDER)) {
				ParticleUtil.PORTAL_FUNNEL.sendToLocation(p.getLocation().add(0.0, 1.8, 0.0), 0.0F, 0.0F, 20);
				ParticleUtil.PORTAL_STILL.sendToLocation(p.getLocation().add(0.0, 0.5, 0.0), 0.2F, 0.1F, 80);
			}
			
			if (fx.equals(EffectPerkUtil.FIRESCARF)) {
				ParticleUtil.FIRE_STILL.sendToLocation(p.getLocation().add(0.0, 1.4, 0.0), 0.2F, 0.0F, 8);		
			}
			
			if (fx.equals(EffectPerkUtil.FIRETRAIL)) {
				ParticleUtil.LAVA_SPARK.sendToLocation(p.getLocation().add(0.0, -0.5, 0.0), 0.5F, 0.0F, 2);	
			}
			
			if (fx.equals(EffectPerkUtil.HEARTSCARF)) {
				ParticleUtil.HEART.sendToLocation(p.getLocation().add(0.0, 0.7, 0.0), 0.12F, 0.0F, 1);
			}
			
			if (fx.equals(EffectPerkUtil.HEARTTRAIL)) {
				ParticleUtil.HEART.sendToLocation(p.getLocation().add(0.0, -0.5, 0.0), 0.3F, 0.2F, 4);			
			}
			
			if (fx.equals(EffectPerkUtil.KNOWLEDGE)) {
				ParticleUtil.FALLING_RUNES.sendToLocation(p.getLocation().add(0.0, 2.0, 0.0), 0.3F, 0.2F, 12);
			}
			
			if (fx.equals(EffectPerkUtil.SUPERCHICKEN)) {
				ParticleUtil.EXPLODE.sendToLocation(p.getLocation().add(0.0, -0.5, 0.0), 0.3F, 0.5F, 3);
			}
			
			if (fx.equals(EffectPerkUtil.BLOOD)) {
				ParticleUtil.RED_SMOKE.sendToLocation(p.getLocation().add(0.0, 0.2, 0.0), 0.3F, 0.2F, 12);
				ParticleUtil.LAVA_DRIP.sendToLocation(p.getLocation().add(0.0, 0.2, 0.0), 0.2F, 0.0F, 3);
				
			}
			
		}
	}
	
}
