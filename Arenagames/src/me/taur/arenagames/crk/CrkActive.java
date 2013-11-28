package me.taur.arenagames.crk;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import me.taur.arenagames.Config;
import me.taur.arenagames.event.RoomEndEvent;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.RoomEndResult;
import me.taur.arenagames.util.RoomType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class CrkActive {
	public static void run() {
		// Get each room in stored Rooms.
		for (String s : Room.ROOMS.keySet()) {
			Room r = Room.ROOMS.get(s);

			if (r.getRoomType() == RoomType.CRK) {
				CrkRoom room = (CrkRoom) r;
				
				room.updateScoreboard();

				if (room.isGameInProgress()) {
					if (room.getPlayersInRoom() < 2) { // If there are not enough players in the room:
						RoomEndEvent event = new RoomEndEvent(room.getRoomId(), RoomEndResult.NOT_ENOUGH_PLAYERS);
						Bukkit.getPluginManager().callEvent(event);
						continue;
						
					}

					int countdown = room.getCountdownTimer();

					if (countdown > 0) {
						if (room.getPlayers() != null) {
							for (Player p : room.getPlayers()) { // Make the levels the player's own timer.
								if (p != null) {
									if (room.getTimer().containsKey(p)) {
										int ptime = room.getTimer().get(p);
										p.setLevel(ptime);
										
										int timer = ptime - 1;
										if (countdown < 5 || (timer < 5 && timer > -1)) { // If there is only less than 5 seconds left for the player
											p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1F, 0F);
										}
										
										if (timer == 0) {
											p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "You have exploded for timing out.");
											room.killPlayer(p); // Player died from not getting kills.
		
										} else {
											room.getTimer().put(p, timer); // Subtract 1 from the player's timer too.
										}
										
									}
								}
							}
						}
						
						room.setCountdownTimer(countdown - 1);

						if (room.getPlayerAlive() < 2) { // If there are less than 2 players alive.
							RoomEndEvent event = new RoomEndEvent(room.getRoomId(), RoomEndResult.LAST_PERSON_STANDING);
							Bukkit.getPluginManager().callEvent(event);
							continue;
							
						}

						if (countdown == 0) {  // The global timer has ended.
							RoomEndEvent event = new RoomEndEvent(room.getRoomId(), RoomEndResult.TIMER_OVER);
							Bukkit.getPluginManager().callEvent(event);
							continue;
							
						}

						continue;
					}

					// Message players about time remaining every minute
					if (countdown % 60 == 0) {
						if (countdown != 0) { // If the game isn't over
							if (room.getPlayers() != null) {
								for (Player p : room.getPlayers()) {
									if (p != null) {
										int minute = countdown / 60;
										p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + minute + " minute" + (minute == 1 ? "" : "s") + " remaining.");
										
									}
								}
							}
						}
					}
				}

				if (room.isGameInWaiting()) {
					int waitcount = room.getWaitTimer();

					for (Player p : room.getPlayers()) { // Make the levels the timer.
						p.setLevel(waitcount);
					}

					if (waitcount > -1) {
						room.setWaitTimer(waitcount - 1);

						if (waitcount < 5) { // If there is only less than 5 seconds left:
							for (Player p : room.getPlayers()) {
								if (p != null) {
									p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1F, 0F);
								}
							}
						}
						
						if (waitcount == 0) { // Game start
							ConfigurationSection cs = CrkConfig.getData().getConfigurationSection("lfl.maps");
							if (cs != null) {
								Set<String> maps = cs.getKeys(false);

								// Fancy loop to make sure rooms are available and only 1 queue can join 1 arena.
								int tries = 0;
								Set<Integer> alreadyused = new HashSet<Integer>();

								boolean breakloop = false;
								while (!breakloop) {
									if (tries == maps.size()) {
										r.setGameInWaiting(false);
										r.setGameInProgress(false);

										for (Player p : r.getPlayers()) {
											p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "Currently all of the Lifeline arenas are in progress.");
											p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "Please wait until an arena frees up.");

										}

										// Restart the wait timer.
										int needed = room.getPlayersInRoom();
										if (needed > Config.getMinPlayersInWait(RoomType.CRK)) {
											if (!room.isGameInWaiting()) {
												room.waitStartMessage(RoomType.CRK);
												room.setGameInWaiting(true);
												room.setWaitTimer(Config.getWaitTimer(RoomType.CRK));
												room.updateSigns();

											}
										} else {
											r.waitCancelledMessage(RoomType.CRK);
										}

										breakloop = true;

									}

									Random rand = new Random();
									int map = 0;

									boolean tryfornew = true;
									while (tryfornew) {
										map = rand.nextInt(maps.size());

										if (!alreadyused.contains(map)) { // If the number is already checked, loop again.
											boolean premium = r.isPremium();
											String mapname = ((String) maps.toArray()[map]);

											if (premium) { // If the queue is premium
												if (!CrkConfig.canPremiumPlayMap(mapname)) { // If the premium room cannot play the map
													alreadyused.add(map);
													tries++;
													continue;

												}
											} else {
												if (!CrkConfig.canNormalPlayMap(mapname)) { // If the normal room cannot play the map
													alreadyused.add(map);
													tries++;
													continue;

												}
											}

											boolean match = false; // Make sure arenas are not used by more than 1 queue
											for (Room tryroom : Room.ROOMS.values()) {
												if (tryroom.getRoomType() == RoomType.CRK) {
													CrkRoom troom = (CrkRoom) tryroom;
													if (troom.getMapName() == maps.toArray()[map]) {
														match = true;

													}
												}
											}

											if (!match) { // Block of code to run when the game is really starting.
												room.setMapName((String) maps.toArray()[map]);

												room.setGameInWaiting(false);
												room.setGameInProgress(true);
												
												room.startGame();

												breakloop = true;
												tryfornew = false;

											}

											alreadyused.add(map);
											tries++;

										}
									}
								}
							} else {
								Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.ITALIC + "An error has occured in " + room.getRoomId() + ": Maps cannot be loaded.");
								Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.ITALIC + "Lifeline match " + room.getRoomId() + " has ended.");

								room.resetRoom(true);

							}

							continue;
						}
					}

					// Message players about time remaining after 30 seconds every 10 seconds.
					if (waitcount < 31 && waitcount % 10 == 0) {
						if (waitcount != 0) { // If the game isn't over
							for (Player p : room.getPlayers()) {
								if (p != null) {
									p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + waitcount + " second" + (waitcount == 1 ? "" : "s") + " until game starts.");
								}
							}
						}
					}
				}
			}
		}
	}
}