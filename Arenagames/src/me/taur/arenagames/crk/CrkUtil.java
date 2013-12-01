package me.taur.arenagames.crk;

import java.util.HashMap;

import me.taur.arenagames.Arenagames;
import me.taur.arenagames.Config;
import me.taur.arenagames.player.Permission;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.IconMenu;
import me.taur.arenagames.util.RoomType;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CrkUtil {
	public static IconMenu kitMenu = null;
	
	public static void enable() {
		for (int i = 1; i < Config.getNormalRooms(RoomType.CRK) + 1; i++) {
			String roomId = "crk-n" + i;
			Room.ROOMS.put(roomId, new CrkRoom(roomId));
			
		}
		
		for (int i = 1; i < Config.getPremiumRooms(RoomType.CRK) + 1; i++) {
			String roomId = "crk-p" + i;
			Room.ROOMS.put(roomId, new CrkRoom(roomId));
			Room room = Room.ROOMS.get(roomId);
			room.setPremium(true);
			
		}
		
		final int kitamt = CrkConfig.getKits().getKeys(false).size();
		int lines = ((kitamt / 9) + 1) * 9; // Gets how many lines the plugin needs.
		
		kitMenu = new IconMenu("Select A Kit", lines, new IconMenu.OptionClickEventHandler() {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent menuevt) {
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
                
                if (r.getRoomType() != RoomType.CRK) {
                	menuevt.setWillClose(true);
                	return;
                	
                }
                
                CrkRoom room = (CrkRoom) r;
                String kitname = ChatColor.stripColor(menuevt.getName()); // Clear colors because we add colors in the menu name.
                int id = menuevt.getPosition();
                
                if (CrkConfig.isKitPremium(id)) {
        			if (!Permission.isPremium(p)) {
        				p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You don't have permission to use this kit.");
        				menuevt.setWillClose(true);
        				return;
        				
        			}
        		}
                
                if (CrkConfig.getKitName(id).equals(kitname)) {
                	p.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "You have selected the " + kitname + " kit for this queue round.");
        			
        			HashMap<Player, Integer> kit = room.getKit();
        			kit.put(p, id);
        			room.setKit(kit);
        			menuevt.setWillClose(true);
    				return;
                	
                }
                
                p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "The kit you selected is invalid.");
                menuevt.setWillClose(true);
				return;
                
            }
        }, Arenagames.plugin);
		
		for (int i = 0; i < kitamt; i++) {
			if (CrkConfig.getKitName(i) != null) { // Make sure the kit exists. It'll leave spaces for the kits that are nameless.
				String info = CrkConfig.getKitDescription(i);
				String premium = ChatColor.GREEN + "Normal Kit";
				
				if (CrkConfig.isKitPremium(i)) {
					premium = ChatColor.GOLD + "Premium Kit";
					
				}
				
				String name = ChatColor.RESET + "" + ChatColor.BOLD + CrkConfig.getKitName(i);
				String lore = ChatColor.GRAY + "" + ChatColor.ITALIC + info;
				kitMenu.setOption(i, new ItemStack(CrkConfig.getKitMenuIcon(i), 1), name, premium, lore);
				
			}
		}
	}
	
	public static void disable() {
		
	}
}
