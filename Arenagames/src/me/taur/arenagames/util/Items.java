package me.taur.arenagames.util;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {
	public static ItemStack convertToItemStack(String blob) {
		Material material = Material.SPONGE; // For error checking
		int dmg = 0;
		int amt = 0;
		String dataBlob = null;
		String enchantmentBlob = null;
		
		if (blob.contains("#")) { // Check for the amount specified first.
			String[] parts = blob.split("#");
			blob = parts[0];
			int p1 = Integer.valueOf(parts[1]);
			
			if (p1 < 1) {
				amt = 1;
				
			} else {
				amt = p1;
				
			}
		}
		
		if (blob.contains("|")) { // Check for enchantments
			String[] parts = blob.split("\\|");
			blob = parts[0];
			enchantmentBlob = parts[1];
			
		}
		
		if (blob.contains(":")) {
			String[] parts = blob.split(":", 2);
			dataBlob = parts[1];
			blob = parts[0];
			
		}
				
		Material mat = Material.getMaterial(blob); // Check if the material is a real material
		if (mat != null) {
			material = mat;
				
		}
		
		if (dataBlob != null) { // Damage value on item.
            dmg = Integer.valueOf(dataBlob);
            
        } else {
        	String name = material.name();
        	
        	if (name.contains("SWORD") || name.contains("HELMET") || name.contains("CHESTPLATE") || name.contains("LEGGINGS") || name.contains("BOOTS") || name.contains("BOW")) {
        		dmg = material.getMaxDurability();
        		
        	}
        	
        }
		
		// Create the ItemStack with basic info.
		ItemStack i = new ItemStack(material, amt, (short) dmg);
		
		if (enchantmentBlob != null) {
            String[] enchantments = enchantmentBlob.split(",");
            for (String enchStr : enchantments) {
                int level = 1;
                if (enchStr.contains(":")) {
                    String[] parts = enchStr.split(":");
                    enchStr = parts[0];
                    try {
                        level = Integer.parseInt(parts[1]);
                    } catch (NumberFormatException ignore) {}
                }

                Enchantment ench = null;
                final String testName = enchStr.toLowerCase().replaceAll("[_\\-]", "");
                for (Enchantment possible : Enchantment.values()) {
                    if (possible.getName().toLowerCase().replaceAll("[_\\-]", "").equals(testName)) {
                        ench = possible;
                        break;
                    }
                }

                if (ench != null) {
                	if (ench.canEnchantItem(i)) {
                		if (ench.getMaxLevel() < level) {
                			level = ench.getMaxLevel();
                			
                		}
                		
                		i.addEnchantment(ench, level);
                	}
                }
            }

        }
		
		return i;
	}
	
	public static ItemStack getKitSelector() {
		ItemStack i = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(ChatColor.GOLD + "Kit Selector");
		im.setLore(Arrays.asList(ChatColor.GRAY + "" + ChatColor.ITALIC + "Right click to select your kit."));
		i.setItemMeta(im);
		
		return i;
		
	}
	
}