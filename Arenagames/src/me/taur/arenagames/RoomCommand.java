package me.taur.arenagames;

import me.taur.arenagames.ffa.FfaConfig;
import me.taur.arenagames.ffa.FfaRoom;
import me.taur.arenagames.lfl.LflConfig;
import me.taur.arenagames.lfl.LflRoom;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.ParticleEffect;
import me.taur.arenagames.util.RoomType;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RoomCommand implements CommandExecutor {
	public boolean onCommand(CommandSender s, Command c, String l, String[] arg) {
		if (c.getName().equalsIgnoreCase("queue") || c.getName().equalsIgnoreCase("q")) {
			if (arg.length < 1) {
				String msg = ChatColor.YELLOW + "Usage: " + ChatColor.ITALIC + "/queue <info/list/leave>";
				
				if (!(s instanceof Player)) {
					s.sendMessage(ChatColor.stripColor(msg));

				} else {
					s.sendMessage(msg);
					
				}
				
				return true;
				
			}
			
			if (arg[0].equalsIgnoreCase("list")) {
				if (!s.hasPermission("arenagames.list")) {
					s.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You have no permission.");
					return true;
					
				}
				
				String rooms = "";
				
				for (String r : Room.ROOMS.keySet()) {
					rooms = rooms + " " + r;
					
				}
				
				if (!(s instanceof Player)) {
					s.sendMessage("List of queues:" + rooms);

				} else {
					s.sendMessage(ChatColor.YELLOW + "List of queues: " + ChatColor.ITALIC + rooms);
					
				}
				
				return true;
				
			}
			
			if (arg[0].equalsIgnoreCase("leave")) {
				if (!(s instanceof Player)) {
					s.sendMessage("This command can only be run by a player.");
					return true;
					
				} else {
					Player p = (Player) s;
					
					if (!p.hasPermission("arenagames.join")) {
						p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You have no permission.");
						return true;
					}
					
					if (Room.PLAYERS.containsKey(p)) {
						Room room = Room.ROOMS.get(Room.PLAYERS.get(p));
						
						if (room.isGameInProgress()) {
							p.teleport(Config.getGlobalLobby());
							// p.sendMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + "The game is currently in progress.");
							// return true;
							
						}
						
						p.sendMessage(ChatColor.GREEN + "You have left " + ChatColor.ITALIC + Room.PLAYERS.get(p) + ".");

						Room.PLAYERS.remove(p);
						room.removePlayer(p);
						
						p.getInventory().setArmorContents(null);
						p.getInventory().clear();
						
						p.setLevel(0);
						p.setExp((float) 0.0);
						
						if (room.getPlayers() != null) {
							for (Player other : room.getPlayers()) {
								if (other != null) { // Null check
									other.sendMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + p.getName() + " has left this queue.");
								}
							}
						}
						
						// Only applies if the room is an FFA room.
						if (room.getRoomType() == RoomType.FFA) {
							if (!room.isGameInProgress()) {
								// Check if there are enough people in the room.
								int needed = room.getPlayersInRoom();
								if (needed > Config.getMinPlayersInWait(RoomType.FFA) - 1) {
									if (!room.isGameInWaiting()) {
										room.waitStartMessage(RoomType.FFA);
										room.setGameInWaiting(true);
										room.setWaitTimer(Config.getWaitTimer(RoomType.FFA));
										
										for (Player pl : room.getPlayers()) {
											pl.setLevel(0);
											pl.setExp((float) 0.0);
											
										}
									
									} else {
										room.waitStartMessage(p, RoomType.FFA);
										
									}
								} else {
									room.waitCancelledMessage(RoomType.FFA);
									room.setGameInWaiting(false);
									
								}
							}
							
							FfaRoom r = (FfaRoom) room;
							r.updateSigns(); // Update signs.
							r.updateScoreboard(); // Update scoreboard
							r.removePlayerScoreboard(p);
							
							Location[] blocs = FfaConfig.getSignsStored(room.getRoomId());
							for (Location bloc : blocs) {
								ParticleEffect.ANGRY_VILLAGER.display(bloc.add(0.5, 1.0, 0.5), 0.1F, 0.1F, 0.1F, 10, 1);
								
							}
						}
						
						// Only applies if the room is an LFL room.
						if (room.getRoomType() == RoomType.LFL) {
							if (!room.isGameInProgress()) {
								// Check if there are enough people in the room.
								int needed = room.getPlayersInRoom();
								if (needed > Config.getMinPlayersInWait(RoomType.LFL) - 1) {
									if (!room.isGameInWaiting()) {
										room.waitStartMessage(RoomType.LFL);
										room.setGameInWaiting(true);
										room.setWaitTimer(Config.getWaitTimer(RoomType.LFL));
										
										for (Player pl : room.getPlayers()) {
											pl.setLevel(0);
											pl.setExp((float) 0.0);
											
										}
									
									} else {
										room.waitStartMessage(p, RoomType.LFL);
										
									}
								} else {
									room.waitCancelledMessage(RoomType.LFL);
									room.setGameInWaiting(false);
									
								}
							}
							
							LflRoom r = (LflRoom) room;
							r.updateSigns(); // Update signs.
							
							Location[] blocs = LflConfig.getSignsStored(room.getRoomId());
							for (Location bloc : blocs) {
								ParticleEffect.ANGRY_VILLAGER.display(bloc.add(0.5, 1.0, 0.5), 0.1F, 0.1F, 0.1F, 10, 1);
								
							}
							
							r.removePlayerScoreboard(p);
						}
						
						return true;
						
					}
				
					p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You are currently not in a queue.");
					return true;
					
				}
				
			}
			
			if (arg[0].equalsIgnoreCase("info")) {
				if (!(s instanceof Player)) {
					s.sendMessage("This command can only be run by a player.");
					return true;
					
				} else {
					Player p = (Player) s;
					
					if (!p.hasPermission("arenagames.info")) {
						p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You have no permission.");
						return true;
						
					}
					
					Room room = Room.ROOMS.get(Room.PLAYERS.get(p));
					if (room == null) {
						p.sendMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + "You are currently not in queue.");
						return true;
						
					}
					
					p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + " --- QUEUE INFO --- ");					
					p.sendMessage(ChatColor.YELLOW + "Queue: " + ChatColor.ITALIC + room.getRoomId());
					p.sendMessage(ChatColor.YELLOW + "Type: " + ChatColor.ITALIC + room.getRoomType());
					p.sendMessage(ChatColor.YELLOW + "Premium?: " + ChatColor.ITALIC + (room.isPremium() ? "Yes" : "No"));
					
					if (room.isGameInProgress()) { // Why did I even bother lmao
						// p.sendMessage(ChatColor.YELLOW + "Game In Progress?: " + ChatColor.ITALIC + "Yes");
						if (room.getRoomType() == RoomType.FFA) {
							p.sendMessage(ChatColor.YELLOW + "Map: " + ChatColor.ITALIC + ((FfaRoom) room).getMapName());
						}
						
					} else {
						p.sendMessage(ChatColor.YELLOW + "Game Starting Soon?: " + ChatColor.ITALIC + (room.isGameInWaiting() ? "Yes" : "No"));
						
					}
					
					String players = "";
					for (Player pl : room.getPlayers()) {
						if (pl != null) {
							players = players + " " + pl.getName();
						}
					}
					
					p.sendMessage(ChatColor.YELLOW + "Players:" + ChatColor.ITALIC + players);
					return true;
					
				}
				
			}
			
			String msg = ChatColor.YELLOW + "Usage: " + ChatColor.ITALIC + "/queue <info/list/leave>";
			
			if (!(s instanceof Player)) {
				s.sendMessage(ChatColor.stripColor(msg));

			} else {
				s.sendMessage(msg);
				
			}
			
			return true;
			
		}
		
		return false;
		
	}
	
}