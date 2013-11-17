package me.taur.arenagames.lfl;

import java.util.HashMap;

import me.taur.arenagames.Arenagames;
import me.taur.arenagames.Config;
import me.taur.arenagames.player.Premium;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.IconMenu;
import me.taur.arenagames.util.RoomType;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LflUtil {
	public static IconMenu lflKitMenu = null;
	
	public static void enable() {
		for (int i = 0; i < Config.getNormalRooms(RoomType.LFL); i++) {
			String roomId = "lfl-n" + i;
			Room.ROOMS.put(roomId, new LflRoom(roomId));
			
		}
		
		for (int i = 0; i < Config.getPremiumRooms(RoomType.LFL); i++) {
			String roomId = "lfl-p" + i;
			Room.ROOMS.put(roomId, new LflRoom(roomId));
			Room room = Room.ROOMS.get(roomId);
			room.setPremium(true);
			
		}
		
		final int kitamt = LflConfig.getKits().getKeys(false).size();
		int lines = ((kitamt / 9) + 1) * 9; // Gets how many lines the plugin needs.
		
		lflKitMenu = new IconMenu("Select A Kit", lines, new IconMenu.OptionClickEventHandler() {
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
                
                if (r.getRoomType() != RoomType.LFL) {
                	menuevt.setWillClose(true);
                	return;
                	
                }
                
                LflRoom room = (LflRoom) r;
                String kitname = ChatColor.stripColor(menuevt.getName()); // Clear colors because we add colors in the menu name.
                int id = menuevt.getPosition();
                
                if (LflConfig.isKitPremium(id)) {
        			if (!Premium.isPremium(p)) {
        				p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You don't have permission to use this kit.");
        				menuevt.setWillClose(true);
        				return;
        				
        			}
        		}
                
                if (LflConfig.getKitName(id).equals(kitname)) {
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
			if (LflConfig.getKitName(i) != null) { // Make sure the kit exists. It'll leave spaces for the kits that are nameless.
				String info = LflConfig.getKitDescription(i);
				String premium = ChatColor.GREEN + "Normal Kit";
				
				if (LflConfig.isKitPremium(i)) {
					premium = ChatColor.GOLD + "Premium Kit";
					
				}
				
				String name = ChatColor.RESET + "" + ChatColor.BOLD + LflConfig.getKitName(i);
				String lore = ChatColor.GRAY + "" + ChatColor.ITALIC + info;
				lflKitMenu.setOption(i, new ItemStack(LflConfig.getKitMenuIcon(i), 1), name, premium, lore);
				
			}
		}
	}
	
	public static void disable() {
		
	}
}
