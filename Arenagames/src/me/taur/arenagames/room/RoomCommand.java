package me.taur.arenagames.room;

import me.taur.arenagames.Config;
import me.taur.arenagames.crk.CrkConfig;
import me.taur.arenagames.crk.CrkRoom;
import me.taur.arenagames.ffa.FfaConfig;
import me.taur.arenagames.ffa.FfaRoom;
import me.taur.arenagames.item.InvUtil;
import me.taur.arenagames.tdm.TdmConfig;
import me.taur.arenagames.tdm.TdmRoom;
import me.taur.arenagames.tdm.TdmTeams;
import me.taur.arenagames.util.ParticleUtil;
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
					
					if (Room.PLAYERS.containsKey(p)) {
						Room room = Room.ROOMS.get(Room.PLAYERS.get(p));
						
						if (room.isGameInProgress()) {
							p.teleport(Config.getGlobalLobby());
						}
						
						p.sendMessage(ChatColor.GREEN + "You have left " + ChatColor.ITALIC + Room.PLAYERS.get(p) + ".");

						Room.PLAYERS.remove(p);
						room.removePlayer(p);
						
						InvUtil.setLobbyInventory(p);
						p.setLevel(0);
						
						if (room.getPlayers() != null) {
							for (Player other : room.getPlayers()) {
								if (other != null) { // Null check
									other.sendMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + p.getName() + " has left this queue.");
								}
							}
						}
						
						// Only applies if the room is an FFA room.
						if (room.getRoomType() == RoomType.FFA) {
							if (!room.isGameInProgress()) { // Check if there are enough people in the room.
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
								ParticleUtil.ANGRY_VILLAGER.sendToLocation(bloc.add(0.5, 1.0, 0.5), 0F, 0F, 1);
							}
						}
						
						// Only applies if the room is an LFL room.
						if (room.getRoomType() == RoomType.CRK) {
							if (!room.isGameInProgress()) {
								// Check if there are enough people in the room.
								int needed = room.getPlayersInRoom();
								if (needed > Config.getMinPlayersInWait(RoomType.CRK) - 1) {
									if (!room.isGameInWaiting()) {
										room.waitStartMessage(RoomType.CRK);
										room.setGameInWaiting(true);
										room.setWaitTimer(Config.getWaitTimer(RoomType.CRK));
										
										for (Player pl : room.getPlayers()) {
											pl.setLevel(0);
											pl.setExp((float) 0.0);
											
										}
									
									} else {
										room.waitStartMessage(p, RoomType.CRK);
									}
									
								} else {
									room.waitCancelledMessage(RoomType.CRK);
									room.setGameInWaiting(false);
									
								}
							}
							
							CrkRoom r = (CrkRoom) room;
							r.updateSigns(); // Update signs.
							r.updateScoreboard(); // Update scoreboard
							r.removePlayerScoreboard(p);
							
							Location[] blocs = CrkConfig.getSignsStored(room.getRoomId());
							for (Location bloc : blocs) {
								ParticleUtil.ANGRY_VILLAGER.sendToLocation(bloc.add(0.5, 1.0, 0.5), 0F, 0F, 1);
							}
						}
						
						// Only applies if the room is a TDM room.
						if (room.getRoomType() == RoomType.TDM) {
							TdmRoom r = (TdmRoom) room;
							
							if (!room.isGameInProgress()) { // Check if there are enough people in the room.
								int needed = room.getPlayersInRoom();
								if (needed > Config.getMinPlayersInWait(RoomType.TDM) - 1) {
									if (!room.isGameInWaiting()) {
										room.waitStartMessage(RoomType.TDM);
										room.setGameInWaiting(true);
										room.setWaitTimer(Config.getWaitTimer(RoomType.TDM));
										
										for (Player pl : room.getPlayers()) {
											pl.setLevel(0);
											pl.setExp((float) 0.0);
											
										}
									
									} else {
										room.waitStartMessage(p, RoomType.TDM);
									}
								} else {
									room.waitCancelledMessage(RoomType.TDM);
									room.setGameInWaiting(false);
									
								}
							} else {
								if (r.getTeamtrackboard().get(p.getName()).intValue() == TdmTeams.RED.getId()) {
									r.removePlayerFromRed(p);
								} else if (r.getTeamtrackboard().get(p.getName()).intValue() == TdmTeams.BLUE.getId()) {
									r.removePlayerFromBlue(p);
								}
								
							}
							
							r.updateSigns(); // Update signs.
							r.updateScoreboard(); // Update scoreboard
							r.removePlayerScoreboard(p);
							
							Location[] blocs = TdmConfig.getSignsStored(room.getRoomId());
							for (Location bloc : blocs) {
								ParticleUtil.ANGRY_VILLAGER.sendToLocation(bloc.add(0.5, 1.0, 0.5), 0F, 0F, 1);
							}
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
					
					Room room = Room.ROOMS.get(Room.PLAYERS.get(p));
					if (room == null) {
						p.sendMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + "You are currently not in queue.");
						return true;
						
					}
					
					p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + " --- QUEUE INFO --- ");					
					p.sendMessage(ChatColor.YELLOW + "Queue: " + ChatColor.ITALIC + room.getRoomId());
					p.sendMessage(ChatColor.YELLOW + "Type: " + ChatColor.ITALIC + room.getRoomType());
					p.sendMessage(ChatColor.YELLOW + "Premium?: " + ChatColor.ITALIC + (room.isPremium() ? "Yes" : "No"));
					
					if (room.isGameInProgress()) {
						p.sendMessage(ChatColor.YELLOW + "Game In Progress?: " + ChatColor.ITALIC + "Yes");
						if (room.getRoomType() == RoomType.FFA) {
							p.sendMessage(ChatColor.YELLOW + "Map: " + ChatColor.ITALIC + ((FfaRoom) room).getMapName());
						} else if (room.getRoomType() == RoomType.CRK) {
							p.sendMessage(ChatColor.YELLOW + "Map: " + ChatColor.ITALIC + ((CrkRoom) room).getMapName());
						}
						
					} else {
						p.sendMessage(ChatColor.YELLOW + "Starting Soon?: " + ChatColor.ITALIC + (room.isGameInWaiting() ? "Yes" : "No"));
						
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