package me.taur.arenagames.tdm;

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

public class TdmUtil {
	public static IconMenu kitMenu = null;
	
	public static void enable() {
		for (int i = 1; i < Config.getNormalRooms(RoomType.TDM) + 1; i++) {
			String roomId = "tdm-n" + i;
			Room.ROOMS.put(roomId, new TdmRoom(roomId));
			
		}
		
		for (int i = 1; i < Config.getPremiumRooms(RoomType.TDM) + 1; i++) {
			String roomId = "tdm-p" + i;
			Room.ROOMS.put(roomId, new TdmRoom(roomId));
			Room room = Room.ROOMS.get(roomId);
			room.setPremium(true);
			
		}
		
		final int kitamt = TdmConfig.getKits().getKeys(false).size();
		int box = ((kitamt / 9) + 1) * 9; // Gets how many box the plugin needs.
		
		kitMenu = new IconMenu(RoomType.TDM.getColor() + "" + ChatColor.BOLD + "TDM: Kit Selection", box, new IconMenu.OptionClickEventHandler() {
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
                
                if (r.getRoomType() != RoomType.TDM) {
                	menuevt.setWillClose(true);
                	return;
                	
                }
                
                TdmRoom room = (TdmRoom) r;
                String kitname = ChatColor.stripColor(menuevt.getName()); // Clear colors because we add colors in the menu name.
                int id = menuevt.getPosition();
                
                if (TdmConfig.isKitPremium(id)) {
        			if (!Permission.isPremium(p)) {
        				p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "This kit requires a Premium subscription.");
        				menuevt.setWillClose(true);
        				return;
        			}
        		}
                
                if (TdmConfig.getKitName(id).equals(kitname)) {
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
			if (TdmConfig.getKitName(i) != null) { // Make sure the kit exists. It'll leave spaces for the kits that are nameless.
				String info = TdmConfig.getKitDescription(i);
				String premium = ChatColor.GREEN + "Normal Kit";
				
				if (TdmConfig.isKitPremium(i)) {
					premium = ChatColor.GOLD + "Premium Kit";
				}
				
				String name = ChatColor.RESET + "" + ChatColor.BOLD + TdmConfig.getKitName(i);
				String lore = ChatColor.GRAY + "" + ChatColor.ITALIC + info;
				kitMenu.setOption(i, new ItemStack(TdmConfig.getKitMenuIcon(i), 1), name, lore, premium);
				
			}
		}
	}
	
	public static void disable() {
		
	}
}
