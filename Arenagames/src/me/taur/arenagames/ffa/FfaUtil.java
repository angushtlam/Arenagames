package me.taur.arenagames.ffa;

import java.util.HashMap;

import me.taur.arenagames.Arenagames;
import me.taur.arenagames.Config;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.IconMenu;
import me.taur.arenagames.util.RoomType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class FfaUtil {
	public static IconMenu ffaKitMenu = null;
	
	public static void enable() {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		
		for (int i = 0; i < Config.getNormalRooms(RoomType.FFA); i++) {
			String roomId = "ffa-n" + i;
			Room.ROOMS.put(roomId, new FfaRoom(roomId));
			
			Scoreboard board = manager.getNewScoreboard();
			Objective objective = board.registerNewObjective(roomId, "dummy");
			objective.setDisplaySlot(DisplaySlot.SIDEBAR);
			objective.setDisplayName("Queue " + roomId);
			Room.SCOREBOARDS.put(roomId, objective);
			
		}
		
		for (int i = 0; i < Config.getPremiumRooms(RoomType.FFA); i++) {
			String roomId = "ffa-p" + i;
			Room.ROOMS.put(roomId, new FfaRoom(roomId));
			Room room = Room.ROOMS.get(roomId);
			room.setPremium(true);
			
			Scoreboard board = manager.getNewScoreboard();
			Objective objective = board.registerNewObjective(roomId, "dummy");
			objective.setDisplaySlot(DisplaySlot.SIDEBAR);
			objective.setDisplayName("Queue " + roomId);
			Room.SCOREBOARDS.put(roomId, objective);
			
		}
		
		final int kitamt = FfaConfig.getKits().getKeys(false).size();
		int lines = ((kitamt / 9) + 1) * 9; // Gets how many lines the plugin needs.
		
		ffaKitMenu = new IconMenu("Select A Kit", lines, new IconMenu.OptionClickEventHandler() {
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
                
                if (r.getRoomType() != RoomType.FFA) {
                	menuevt.setWillClose(true);
                	return;
                	
                }
                
                FfaRoom room = (FfaRoom) r;
                String kitname = ChatColor.stripColor(menuevt.getName()); // Clear colors because we add colors in the menu name.
                int id = menuevt.getPosition();
                
                if (FfaConfig.isKitPremium(id)) {
        			if (!p.hasPermission("arenagames.premium")) {
        				p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You don't have permission to use this kit.");
        				menuevt.setWillClose(true);
        				return;
        			}
        		}
                
                if (FfaConfig.getKitName(id).equals(kitname)) {
                	p.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "You have selected the " + kitname + " kit for your next round of this queue.");
        			
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
			if (FfaConfig.getKitName(i) != null) { // Make sure the kit exists. It'll leave spaces for the kits that are nameless.
				String info = FfaConfig.getKitDescription(i);
				String premium = ChatColor.GREEN + "Normal Kit";
				
				if (FfaConfig.isKitPremium(i)) {
					premium = ChatColor.GOLD + "Premium Kit";
					
				}
				
				String name = ChatColor.RESET + "" + ChatColor.BOLD + FfaConfig.getKitName(i);
				String lore = ChatColor.GRAY + "" + ChatColor.ITALIC + info;
				ffaKitMenu.setOption(i, new ItemStack(FfaConfig.getKitMenuIcon(i), 1), name, premium, lore);
				
			}
		}
	}
	
	public static void disable() {
		
	}
}
