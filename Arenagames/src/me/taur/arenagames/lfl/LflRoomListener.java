package me.taur.arenagames.lfl;

import me.taur.arenagames.Config;
import me.taur.arenagames.event.RoomEndEvent;
import me.taur.arenagames.player.EloUtil;
import me.taur.arenagames.player.PlayerData;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.InvUtil;
import me.taur.arenagames.util.RoomEndResult;
import me.taur.arenagames.util.RoomType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class LflRoomListener implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void roomEnd(RoomEndEvent evt) {
		if (evt.getRoom() == null) { // Make sure the room exists.
			return;
		}
		
		Room r = evt.getRoom();
		RoomEndResult result = evt.getResult();
		
		if (r.getRoomType() == RoomType.LFL) {
			LflRoom room = (LflRoom) r;
			
			if (result == RoomEndResult.TIMER_OVER) { // If the game ended by timer:
				if (room.getWinningPlayer() != null) {
					room.gameOverMessage(room.getWinningPlayer()); // Broadcast who won.
				}

				if (Config.isEloEnabled(RoomType.LFL)) { // Only change the player's Elo if it is enabled.
					for (Player p : room.getPlayers()) {
						if (PlayerData.isLoaded(p)) {
							PlayerData data = PlayerData.get(p);
							int oldelo = data.getLflRanking();
							int newelo = data.getLflRanking();
							
							if (room.getPointboard().get(p.getName()) > room.getPointMedian() - 1 || room.getWinningPlayer() == p.getName()) { // If the player won
								try {
									newelo = EloUtil.addElo(oldelo, room.getAvgElo());
								} catch (Exception e) {
									
								}
							} else {
								try {
									newelo = EloUtil.removeElo(oldelo, room.getAvgElo());
								} catch (Exception e) {
									
								}
							}
							
							int diff = newelo - oldelo;
							data.setLflRanking(newelo);
							data.save(p);
							
							p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "Your Lifeline Elo: " + oldelo + " > " + newelo + " (" + diff + ").");
							
						}
					}
				}
				
				for (Player p : room.getPlayers()) {
					if (p != null) {
						p.teleport(LflConfig.getLobby());
						InvUtil.setLobbyInventory(p);
						
						if (PlayerData.isLoaded(p)) {
							PlayerData data = PlayerData.get(p);
							data.setLflGamesPlayed(data.getLflGamesPlayed() + 1); // Increase their play count.

							int points = room.getPointboard().get(p.getName());
							if (points > data.getLflRecord()) { // If the player has set a new record:
								data.setLflRecord(points);
								p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "You have set a new personal record: " + points + "!");
								
							}
							
							data.save(p);
							
						}
						
						Room.PLAYERS.remove(p);
						
					}
				}
				
				for (Player p : room.getPlayers()) {
					if (p != null) {
						if (PlayerData.isLoaded(p)) {
							PlayerData data = PlayerData.get(p);
							data.setLflGamesPlayed(data.getLflGamesPlayed() + 1); // Increase their play count.

							int points = room.getPointboard().get(p.getName());
							if (points > data.getLflRecord()) { // If the player has set a new record:
								data.setLflRecord(points);
								p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "You have set a new personal record: " + points + "!");
								
							}
							
							data.save(p);
							
						}
						
						Room.PLAYERS.remove(p);
						
						// Teleport the player to lobby.
						p.teleport(LflConfig.getLobby());
						InvUtil.setLobbyInventory(p);
						
					}
				}

				Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Free For All match " + room.getRoomId() + " has ended.");
				room.resetRoom(true);
				
			} else if (result == RoomEndResult.NOT_ENOUGH_PLAYERS) {
				if (room.getWinningPlayer() != null) {
					room.gameOverMessage(room.getWinningPlayer()); // Broadcast who won.
				}

				if (Config.isEloEnabled(RoomType.LFL)) { // Only change the player's Elo if it is enabled.
					for (Player p : room.getPlayers()) {
						if (PlayerData.isLoaded(p)) {
							PlayerData data = PlayerData.get(p);
							int oldelo = data.getLflRanking();
							int newelo = data.getLflRanking();
							
							if (room.getPointboard().get(p.getName()) > room.getPointMedian() - 1 || room.getWinningPlayer() == p.getName()) { // If the player won
								try {
									newelo = EloUtil.addElo(oldelo, room.getAvgElo());
								} catch (Exception e) {
									
								}
							} else {
								try {
									newelo = EloUtil.removeElo(oldelo, room.getAvgElo());
								} catch (Exception e) {
									
								}
							}
							
							int diff = newelo - oldelo;
							data.setLflRanking(newelo);
							data.save(p);
							
							p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "Your Lifeline Elo: " + oldelo + " > " + newelo + " (" + diff + ").");
							
						}
					}
				}
				
				for (Player p : room.getPlayers()) {
					if (p != null) {
						if (PlayerData.isLoaded(p)) {
							PlayerData data = PlayerData.get(p);
							data.setLflGamesPlayed(data.getLflGamesPlayed() + 1); // Increase their play count.

							int points = room.getPointboard().get(p.getName());
							if (points > data.getLflRecord()) { // If the player has set a new record:
								data.setLflRecord(points);
								p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "You have set a new personal record: " + points + "!");
								
							}
							
							data.save(p);
							
						}
						
						Room.PLAYERS.remove(p);
						
						// Teleport the player to the lobby.
						p.teleport(LflConfig.getLobby());
						p.setLevel(0); // Change their levels because the countdown is not 0 yet.

						LflSpawnManager.purgeEffects(p);
						InvUtil.setLobbyInventory(p);
						
					}
				}

				Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Lifeline match " + room.getRoomId() + " has ended.");
				room.resetRoom(true);
				
			} else if (result == RoomEndResult.LAST_PERSON_STANDING) {
				if (room.getWinningPlayer() != null) {
					room.gameOverMessage(room.getWinningPlayer()); // Broadcast who won.
				}

				if (Config.isEloEnabled(RoomType.LFL)) { // Only change the player's Elo if it is enabled.
					for (Player p : room.getPlayers()) {
						if (PlayerData.isLoaded(p)) {
							PlayerData data = PlayerData.get(p);
							int oldelo = data.getLflRanking();
							int newelo = data.getLflRanking();
							
							if (room.getPointboard().get(p.getName()) > room.getPointMedian() - 1 || room.getWinningPlayer() == p.getName()) { // If the player won
								try {
									newelo = EloUtil.addElo(oldelo, room.getAvgElo());
								} catch (Exception e) {
									
								}
							} else {
								try {
									newelo = EloUtil.removeElo(oldelo, room.getAvgElo());
								} catch (Exception e) {
									
								}
							}
							
							int diff = newelo - oldelo;
							data.setLflRanking(newelo);
							data.save(p);
							
							p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "Your Lifeline Elo: " + oldelo + " > " + newelo + " (" + diff + ").");
							
						}
					}
				}
				
				for (Player p : room.getPlayers()) {
					if (p != null) {
						if (PlayerData.isLoaded(p)) {
							PlayerData data = PlayerData.get(p);
							data.setLflGamesPlayed(data.getLflGamesPlayed() + 1); // Increase their play count.

							int points = room.getPointboard().get(p.getName());
							if (points > data.getLflRecord()) { // If the player has set a new record:
								data.setLflRecord(points);
								p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "You have set a new personal record: " + points + "!");
								
							}
							
							data.save(p);
						}
						
						Room.PLAYERS.remove(p);
						
						// Teleport the player to the lobby.
						p.teleport(LflConfig.getLobby());
						p.setLevel(0); // Change their levels because the countdown is not 0 yet.

						LflSpawnManager.purgeEffects(p);
						InvUtil.setLobbyInventory(p);
						
					}
				}

				Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Lifeline match " + room.getRoomId() + " has ended.");
				room.resetRoom(true);
				
			} else {
				RoomEndEvent event = new RoomEndEvent(room.getRoomId(), RoomEndResult.NOT_ENOUGH_PLAYERS);
				Bukkit.getPluginManager().callEvent(event);
				
			}
		}
	}
}