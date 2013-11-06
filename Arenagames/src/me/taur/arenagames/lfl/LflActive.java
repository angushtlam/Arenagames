package me.taur.arenagames.lfl;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import me.taur.arenagames.Config;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.Items;
import me.taur.arenagames.util.Players;
import me.taur.arenagames.util.RoomType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class LflActive {
	public static void run() {
		// Get each room in stored Rooms.
		for (String s : Room.ROOMS.keySet()) {
			Room r = Room.ROOMS.get(s);

			if (r.getRoomType() == RoomType.LFL) {
				LflRoom room = (LflRoom) r;

				if (room.isGameInProgress()) {
					if (room.getPlayersInRoom() == 1) { // If there is only 1 player left.
						room.gameOverMessage(room.getWinningPlayer());

						if (room.getPlayers() != null) {
							for (Player p : room.getPlayers()) {
								if (p != null) {
									p.teleport(LflConfig.getLobby());
									p.setLevel(0);

									Players.respawnEffects(p);
									Items.updatePlayerInv(p);
									Room.PLAYERS.remove(p);
								}
							}

							room.resetRoom(true);

						}
					}


					if (room.getPlayersInRoom() < 1) { // End game w/ no players.
						if (room.getPlayers() != null) {
							for (Player p : room.getPlayers()) {
								if (p != null) {
									p.teleport(LflConfig.getLobby());
									p.setLevel(0);

									Players.respawnEffects(p);
									Items.updatePlayerInv(p);
									Room.PLAYERS.remove(p);
								}
							}
						}

						room.resetRoom(true);
						Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.ITALIC + "Lifeline match " + room.getRoomId() + " has ended.");

					}

					int countdown = room.getCountdownTimer();

					if (room.getPlayers() != null) {
						for (Player p : room.getPlayers()) { // Make the levels the timer.
							if (p != null) {
								if (room.getTimer().get(p) != null) {
									p.setLevel(room.getTimer().get(p));
								}
							}
						}
					}

					if (countdown > -1) {
						room.setCountdownTimer(countdown - 1);

						if (room.getPlayers() != null) {
							for (Player p : room.getPlayers()) {
								if (p != null) {
									int timer = room.getTimer().get(p) - 1;
									if (timer < 1) {
										p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "You have exploded for timing out.");
										p.getWorld().createExplosion(p.getLocation(), 0.0F, false);
										p.setLevel(0);
										
										room.killPlayer(p); // Player died from not getting kills.
	
									} else {
										room.getTimer().put(p, timer); // Subtract 1 from the player's timer too.
	
									}
								}
							}
						}

						if (countdown == 0) {  // End the game
							room.gameOverMessage(room.getWinningPlayer());

							for (Player p : r.getPlayers()) {
								if (p != null) {
									p.teleport(LflConfig.getLobby());
									p.getInventory().setArmorContents(null);
									p.getInventory().clear();
									Items.updatePlayerInv(p);
									Room.PLAYERS.remove(p);
								}
							}

							room.resetRoom(true);

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

						if (waitcount == 0) { // Game start
							ConfigurationSection cs = LflConfig.get().getConfigurationSection("lfl.maps");
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
										if (needed > Config.getMinPlayersInWait(RoomType.LFL)) {
											if (!room.isGameInWaiting()) {
												room.waitStartMessage(RoomType.LFL);
												room.setGameInWaiting(true);
												room.setWaitTimer(Config.getWaitTimer(RoomType.LFL));
												room.updateSigns();

											}
										} else {
											r.waitCancelledMessage(RoomType.LFL);

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
												if (!LflConfig.canPremiumPlayMap(mapname)) { // If the premium room cannot play the map
													alreadyused.add(map);
													tries++;
													continue;

												}
											} else {
												if (!LflConfig.canNormalPlayMap(mapname)) { // If the normal room cannot play the map
													alreadyused.add(map);
													tries++;
													continue;

												}
											}

											boolean match = false; // Make sure arenas are not used by more than 1 queue
											for (Room tryroom : Room.ROOMS.values()) {
												if (tryroom.getRoomType() == RoomType.LFL) {
													LflRoom troom = (LflRoom) tryroom;
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