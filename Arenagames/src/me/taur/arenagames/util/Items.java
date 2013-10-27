package me.taur.arenagames.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class Items {
	@SuppressWarnings("deprecation")
	public static ItemStack convertToItemStack(String blob) {
		int id = 0;
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
				
		try { // Make sure the id is a valid material.
			id = Integer.parseInt(blob);
			
		} catch (NumberFormatException e) {
			Material mat = Material.getMaterial(id); // I don't care if it's deprecated. It's important.
			
			if (mat == null) {
				id = Material.SPONGE.getId(); // Lol sponge. Error checking at it's finest.
				
			}
		}
		
		if (dataBlob != null) { // Damage value on item.
            dmg = Integer.valueOf(dataBlob);
            
        }
		
		// Create the ItemStack with basic info.
		ItemStack i = new ItemStack(id, amt, (short) dmg);
		
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
	
}