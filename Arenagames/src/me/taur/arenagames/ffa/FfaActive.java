package me.taur.arenagames.ffa;

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

public class FfaActive {
	public static void run() {
		for (String s : Room.ROOMS.keySet()) { // Get each room in stored Rooms.
			Room r = Room.ROOMS.get(s);

			if (r.getRoomType() == RoomType.FFA) {
				FfaRoom room = (FfaRoom) r;
				room.updateScoreboard();
				
				if (room.isGameInProgress()) {
					if (room.getPlayersInRoom() < 2) { // If there are not enough players in the room:
						RoomEndEvent event = new RoomEndEvent(room.getRoomId(), RoomEndResult.NOT_ENOUGH_PLAYERS);
						Bukkit.getPluginManager().callEvent(event);
						continue;
						
					}

					int countdown = room.getCountdownTimer();
					if (countdown > 0) {
						room.setCountdownTimer(countdown - 1);
						
						for (Player ptimer : FfaSpawnManager.RESPAWN_TIMER.keySet()) { // Update the player's death timers.
							int time = FfaSpawnManager.RESPAWN_TIMER.get(ptimer).intValue() - 1;
							
							if (time < 1) {
								FfaSpawnManager.RESPAWN_TIMER.remove(ptimer);
								continue;
								
							}
							
							FfaSpawnManager.RESPAWN_TIMER.put(ptimer, time);
							
						}
						
						for (Player p : room.getPlayers()) {
							if (p != null) {
								if (Config.isHungerRegenEnabled(RoomType.FFA)) {
									if (countdown % Config.getHungerRegen(RoomType.FFA) == 0) {
										p.setFoodLevel(Math.min(20, p.getFoodLevel() + 1));
									}
								}
								
								int points = room.getPointboard().get(p.getName()).intValue();
								p.setLevel(points);
								
								if (countdown < 5) { // If there is only less than 5 seconds left:
									p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1F, 0F);
								}
							}
						}
						
					}
					
					if (countdown == 0) { // If the game is over
						RoomEndEvent event = new RoomEndEvent(room.getRoomId(), RoomEndResult.TIMER_OVER);
						Bukkit.getPluginManager().callEvent(event);
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
							ConfigurationSection cs = FfaConfig.getData().getConfigurationSection("ffa.maps");
							if (cs != null) {
								Set<String> maps = cs.getKeys(false);

								// Fancy loop to make sure rooms are available and only 1 queue can join 1 arena.
								int tries = 0;
								Set<Integer> alreadyused = new HashSet<Integer>();

								boolean breakloop = false;
								while (!breakloop) {
									if (tries == maps.size()) {
										room.setGameInWaiting(false);
										room.setGameInProgress(false);

										for (Player p : r.getPlayers()) {
											p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "Currently all of the Free For All arenas are in progress.");
											p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "Please wait until an arena frees up.");

										}

										// Restart the wait timer.
										int needed = room.getPlayersInRoom();
										if (needed > Config.getMinPlayersInWait(RoomType.FFA)) {
											if (!room.isGameInWaiting()) {
												room.waitStartMessage(RoomType.FFA);
												room.setGameInWaiting(true);
												room.setWaitTimer(Config.getWaitTimer(RoomType.FFA));
												room.updateSigns();
												room.updateScoreboard(); // Update scoreboard

											}
										} else {
											room.waitCancelledMessage(RoomType.FFA);

										}

										breakloop = true;

									}

									Random rand = new Random();
									int map = 0;

									boolean tryfornew = true;
									while (tryfornew) {
										map = rand.nextInt(maps.size());

										if (!alreadyused.contains(map)) { // If the number is already checked, loop again.
											boolean premium = room.isPremium();
											String mapname = ((String) maps.toArray()[map]);

											if (premium) { // If the queue is premium
												if (!FfaConfig.canPremiumPlayMap(mapname)) { // If the premium room cannot play the map
													alreadyused.add(map);
													tries++;
													continue;

												}
											} else {
												if (!FfaConfig.canNormalPlayMap(mapname)) { // If the normal room cannot play the map
													alreadyused.add(map);
													tries++;
													continue;

												}
											}

											boolean match = false; // Make sure arenas are not used by more than 1 queue
											for (Room tryroom : Room.ROOMS.values()) {
												if (tryroom.getRoomType() == RoomType.FFA) {
													FfaRoom troom = (FfaRoom) tryroom;
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
								Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.ITALIC + "Free For All match " + room.getRoomId() + " has ended.");

								room.resetRoom(true);

							}
						}

						continue;

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