package me.taur.arenagames.perk;

import java.util.Random;

import me.taur.arenagames.item.SpellUtil;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.EffectPerk;
import me.taur.arenagames.util.ParticleEffect;

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
			
			EffectPerk fx = PerkEffect.ACTIVE_EFFECT_PERK.get(p);
			
			if (fx.equals(EffectPerk.STORM)) {
				Random rand = new Random();
				int r = rand.nextInt(4); 
				if (r == 0) {
					ParticleEffect.CRIT.display(p.getLocation().add(0.0, 3.0, 0.0), 0.4F, 0.2F, 0.4F, 0, 6); // Random thunder.
				}
				
			}
			
			if (fx.equals(EffectPerk.SUPERCHICKEN)) {
				Random rand = new Random();
				int r = rand.nextInt(8); 
				if (r == 0) {
					SpellUtil.itemSpray(p.getLocation(), new ItemStack(Material.EGG, 0), 1, 0.1F, 80);
				}
				
				SpellUtil.itemSpray(p.getLocation(), new ItemStack(Material.FEATHER, 0), 3, 1.0F, 10); // Feather falls out of player
				
			}
			
			if (fx.equals(EffectPerk.KNOWLEDGE)) {
				Random rand = new Random();
				int r = rand.nextInt(8); 
				if (r == 0) {
					SpellUtil.itemSpray(p.getLocation(), new ItemStack(Material.BOOK, 0), 3, 0.1F, 80);
				}
				
			}

			if (fx.equals(EffectPerk.BLOOD)) {
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
			EffectPerk fx = PerkEffect.ACTIVE_EFFECT_PERK.get(p);
			
			if (fx.equals(EffectPerk.STORM)) {
				ParticleEffect.DRIP_WATER.display(p.getLocation().add(0.0, 3.0, 0.0), 0.2F, 0.1F, 0.2F, 1, 8); // Constant rain
				ParticleEffect.LARGE_SMOKE.display(p.getLocation().add(0.0, 3.0, 0.0), 0.2F, 0.1F, 0.2F, 0, 8); // Constant smoke
				
			}
			
			if (fx.equals(EffectPerk.BLOOM)) {
				ParticleEffect.MOB_SPELL.display(p.getLocation().add(0.0, 0.3, 0.0), 0.4F, 0.2F, 0.4F, 1, 12); // Bright potion particles
			}
			
			if (fx.equals(EffectPerk.MUSIC)) {
				ParticleEffect.NOTE.display(p.getLocation().add(0.0, 0.3, 0.0), 0.4F, 0.2F, 0.4F, 1, 16); // Bright note particles
			}
			
			if (fx.equals(EffectPerk.CLOUD)) {
				ParticleEffect.CLOUD.display(p.getLocation().add(0.0, 0.3, 0.0), 0.4F, 0.1F, 0.4F, 0, 16); // Constant cloud
				ParticleEffect.SPELL.display(p.getLocation().add(0.0, 0.3, 0.0), 0.4F, 0.1F, 0.4F, 0, 8); // Constant white swirl
				ParticleEffect.EXPLODE.display(p.getLocation().add(0.0, 0.3, 0.0), 0.4F, 0.1F, 0.4F, 0, 4); // Constant larger cloud
				
			}
			
			if (fx.equals(EffectPerk.SOIL)) {
				ParticleEffect.LARGE_SMOKE.display(p.getLocation().add(0.0, -0.5, 0.0), 1.0F, 0.0F, 1.0F, 0, 24); // Constant cloud
			}
			
			if (fx.equals(EffectPerk.ENDER)) {
				ParticleEffect.PORTAL.display(p.getLocation().add(0.0, -0.5, 0.0), 0.5F, 0.0F, 0.5F, 0, 140); // Constant purple	
			}
			
			if (fx.equals(EffectPerk.FIRESCARF)) {
				ParticleEffect.FLAME.display(p.getLocation().add(0.0, 1.4, 0.0), 0.2F, 0.0F, 0.2F, 0, 8); // Constant fire around neck				
			}
			
			if (fx.equals(EffectPerk.FIRETRAIL)) {
				ParticleEffect.LAVA.display(p.getLocation().add(0.0, -0.5, 0.0), 0.5F, 0.0F, 0.5F, 0, 3); // Constant lava around legs				
			}
			
			if (fx.equals(EffectPerk.HEARTSCARF)) {
				ParticleEffect.HEART.display(p.getLocation().add(0.0, 0.7, 0.0), 0.12F, 0.0F, 0.12F, 0, 1); // Constant hearts around neck			
			}
			
			if (fx.equals(EffectPerk.HEARTTRAIL)) {
				ParticleEffect.HEART.display(p.getLocation().add(0.0, -0.5, 0.0), 0.5F, 0.0F, 0.5F, 0, 6); // Constant hearts around legs				
			}
			
			if (fx.equals(EffectPerk.KNOWLEDGE)) {
				ParticleEffect.ENCHANTMENT_TABLE.display(p.getLocation().add(0.0, 2.0, 0.0), 0.2F, 0.1F, 0.2F, 0, 12); // Constant text above head
			}
			
			if (fx.equals(EffectPerk.SUPERCHICKEN)) {
				ParticleEffect.CLOUD.display(p.getLocation().add(0.0, 1.2, 0.0), 0.2F, 0.1F, 0.2F, 0, 1); // Constant cloud
			}
			
			if (fx.equals(EffectPerk.BLOOD)) {
				ParticleEffect.RED_DUST.display(p.getLocation().add(0.0, 0.2, 0.0), 0.2F, 0.1F, 0.1F, 0, 8); // Constant red particles
				ParticleEffect.DRIP_LAVA.display(p.getLocation().add(0.0, 0.2, 0.0), 0.2F, 0.0F, 0.2F, 0, 6); // Constant red particles
				
			}
			
		}
	}
	
}
