package me.taur.arenagames.ffa;

import java.util.HashMap;

import me.taur.arenagames.Arenagames;
import me.taur.arenagames.Config;
import me.taur.arenagames.util.IconMenu;
import me.taur.arenagames.util.Room;
import me.taur.arenagames.util.RoomType;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FfaUtil {
	public static IconMenu ffaKitMenu;
	
	public static void enable() {
		for (int i = 0; i < Config.getNormalRooms(RoomType.FFA); i++) {
			String roomId = "ffa-n" + i;
			Room.ROOMS.put(roomId, new FfaRoom(roomId));
			
		}
		
		for (int i = 0; i < Config.getPremiumRooms(RoomType.FFA); i++) {
			String roomId = "ffa-p" + i;
			Room.ROOMS.put(roomId, new FfaRoom(roomId));
			Room room = Room.ROOMS.get(roomId);
			room.setPremium(true);
			
		}
		
		final int kitamt = FfaConfig.getKits().getKeys(false).size();
		
		// Gets how many lines the plugin needs.
		int lines = ((kitamt / 9) + 1) * 9;
		
		ffaKitMenu = new IconMenu("Select A Kit", lines, new IconMenu.OptionClickEventHandler() {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent menuevt) {
            	menuevt.setWillDestroy(true); // Destroy the object so it doesn't get used again
            	
                Player p = menuevt.getPlayer();
                
                if (!Room.PLAYERS.containsKey(p)) {
                	menuevt.setWillClose(true);
                	return;
                	
                }
                
                Room r = Room.ROOMS.get(Room.PLAYERS.get(p));
                
                if (r == null) { // Null check 
                	menuevt.setWillClose(true);
                	return;
                	
                }
                
                if (r.getRoomType() != RoomType.FFA) {
                	menuevt.setWillClose(true);
                	return;
                	
                }
                
                FfaRoom room = (FfaRoom) r;
                String kitname = ChatColor.stripColor(menuevt.getName()); // Clear colors because we add colors in the menu name.
                
                for (String kits : FfaConfig.getKits().getKeys(false)) {
                	int i = Integer.valueOf(kits.split("-")[1]);
                	
                	String check = FfaConfig.getKitName(i);
                	if (check != null) { // Make sure the kit exists
                		if (check.equalsIgnoreCase(kitname)) {
                			p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You have selected the " + check + " kit for your next round.");
                			
                			HashMap<Player, Integer> kit = room.getKit();
                			kit.put(p, i);
                			room.setKit(kit);
                        	break;
                			
                		}
                	}
                	
                	p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "The kit you selected is invalid.");
                	break;
                	
                }
                
                menuevt.setWillClose(true);
            }
        }, Arenagames.plugin);
		
		
		
		for (int i = 0; i < kitamt; i++) {
			String info = FfaConfig.getKitDescription(i);
			String premium = ChatColor.GREEN + "Normal Kit";
			
			if (FfaConfig.isKitPremium(i)) {
				premium = ChatColor.GOLD + "Premium Kit";
				
			}
			
			String lore = premium + "\n" + ChatColor.RESET + ChatColor.GRAY + "" + ChatColor.ITALIC + info;
			ffaKitMenu.setOption(i, new ItemStack(FfaConfig.getKitMenuIcon(i), 1), FfaConfig.getKitName(i), lore);
			
		}
		
	}
	
	public static void disable() {
		
		
	}
	
}
