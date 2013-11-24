package me.taur.arenagames.item;

import java.util.Arrays;

import me.taur.arenagames.player.PlayerProfile;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class InvUtil {
	public static ItemStack convertToItemStack(String blob) {
		Material material = Material.SPONGE; // If Material is invalid, the material will show up as Sponge.
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
				
		Material mat = Material.getMaterial(blob);
		if (mat != null) { // Check if the material is a real material.
			material = mat;
		}
		
		if (dataBlob != null) { // Damage value on item.
            dmg = Integer.valueOf(dataBlob);
            
            String name = material.name();
            if (dmg < 0) {
            	dmg = 0;
            }
            
            if (dmg == 0) {
            	if (name.contains("_SWORD")) {
            		dmg = material.getMaxDurability();
            	} else if (name.contains("_HELMET")) {
            		dmg = material.getMaxDurability();
            	} else if (name.contains("_CHESTPLATE")) {
            		dmg = material.getMaxDurability();
            	} else if (name.contains("_LEGGINGS")) {
            		dmg = material.getMaxDurability();
            	} else if (name.contains("_BOOTS")) {
            		dmg = material.getMaxDurability();
            	} else if (name.equals("BOW")) {
            		dmg = material.getMaxDurability();
            	}
            	
            }
        } else {
        	dmg = 0;
        }
		
		ItemStack i = null;
		
		if (CustomItemUtil.STORE.containsKey(blob)) { // TODO: Check if item is a custom item.
			i = CustomItemUtil.STORE.get(blob);
			i.setAmount(amt);
			
		} else {
			i = new ItemStack(material, amt, (short) dmg);
		}
		
		if (enchantmentBlob != null) {
            String[] enchantments = enchantmentBlob.split(",");
            for (String enchStr : enchantments) {
                int level = 1;
                if (enchStr.contains(":")) {
                    String[] parts = enchStr.split(":");
                    enchStr = parts[0];
                    try {
                        level = Integer.parseInt(parts[1]);
                    } catch (NumberFormatException ignore) {
                    	
                    }
                    
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

                		i.addUnsafeEnchantment(ench, level);
                		
                	}
                }
            }
        }
		
		return i;
		
	}
	
	public static ItemStack getKitSelector() {
		ItemStack i = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(ChatColor.GREEN + "Kit Selector");
		im.setLore(Arrays.asList(ChatColor.GRAY + "" + ChatColor.ITALIC + "Right click to select your kit."));
		i.setItemMeta(im);
		return i;
		
	}

	public static ItemStack getProfileItem() {
		ItemStack i = new ItemStack(Material.WRITTEN_BOOK, 1);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(ChatColor.GOLD + "Profile");
		im.setLore(Arrays.asList(ChatColor.GRAY + "" + ChatColor.ITALIC + "Right click to check your profile."));
		i.setItemMeta(im);
		return i;
		
	}
	
	public static ItemStack getPerkItem() {
		ItemStack i = new ItemStack(Material.ENCHANTED_BOOK, 1);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(ChatColor.BLUE + "Perks");
		im.setLore(Arrays.asList(ChatColor.GRAY + "" + ChatColor.ITALIC + "Right click to check your perks."));
		i.setItemMeta(im);
		return i;
		
	}
	
	public static void setLobbyInventory(Player p) {
		PlayerInventory inv = p.getInventory();
		
		inv.setArmorContents(null);
		inv.clear();
		
		ItemStack i0 = InvUtil.getProfileItem();
		BookMeta bm0 = (BookMeta) i0.getItemMeta();
		bm0.setPages(PlayerProfile.bookInformation(p));
		i0.setItemMeta(bm0);
		
		inv.setItem(0, i0);
		
		ItemStack i1 = InvUtil.getPerkItem();
		inv.setItem(1, i1);
		updatePlayerInv(p);
		
	}
	
	@SuppressWarnings("deprecation")
	public static void updatePlayerInv(Player p) {
		p.updateInventory();
	}
	
	public static void clearPlayerInv(Player p) {
		p.getInventory().setArmorContents(null);
		p.getInventory().clear();
		updatePlayerInv(p);
		
	}
}