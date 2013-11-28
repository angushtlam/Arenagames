package me.taur.arenagames.crk;

import me.taur.arenagames.Config;
import me.taur.arenagames.event.RoomEndEvent;
import me.taur.arenagames.item.InvUtil;
import me.taur.arenagames.player.PlayerData;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.GameMathUtil;
import me.taur.arenagames.util.RoomEndResult;
import me.taur.arenagames.util.RoomType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class CrkRoomListener implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void roomEnd(RoomEndEvent evt) {
		if (evt.getRoom() == null) { // Make sure the room exists.
			return;
		}
		
		Room r = evt.getRoom();
		RoomEndResult result = evt.getResult();
		
		if (r.getRoomType() == RoomType.CRK) {
			CrkRoom room = (CrkRoom) r;
			
			if (result == RoomEndResult.TIMER_OVER) { // If the game ended by timer:
				if (room.getWinningPlayer() != null) {
					room.gameOverMessage(room.getWinningPlayer()); // Broadcast who won.
				}

				if (Config.isRankedEnabled(RoomType.CRK)) { // Only change the player's Elo if it is enabled.
					for (Player p : room.getPlayers()) {
						if (PlayerData.isLoaded(p)) {
							PlayerData data = PlayerData.get(p);
							int oldelo = data.getCrkRanking();
							int newelo = data.getCrkRanking();
							
							if (room.getPointboard().get(p.getName()) > room.getPointMedian() - 1 || room.getWinningPlayer() == p.getName()) { // If the player won
								try {
									newelo = GameMathUtil.addElo(oldelo, room.getAvgElo());
								} catch (Exception e) {
									
								}
							} else {
								try {
									newelo = GameMathUtil.removeElo(oldelo, room.getAvgElo());
								} catch (Exception e) {
									
								}
							}
							
							int diff = newelo - oldelo;
							data.setCrkRanking(newelo);
							data.save(p);
							
							p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "Your Cranked Elo: " + oldelo + " > " + newelo + " (" + diff + ").");
							
						}
					}
				}
				
				if (Config.isEconomyEnabled(RoomType.CRK)) {
					for (Player p : room.getPlayers()) {
						if (PlayerData.isLoaded(p)) {
							PlayerData data = PlayerData.get(p);
							int currency = data.getCurrency();
							int lifetime = data.getCurrencyLifetime();
							int add = 0;
							
							if (room.getWinningPlayer() == p.getName()) { // If the player won
								add = CrkConfig.getCurrencyFirst();
							} else if (room.getPointboard().get(p.getName()) > room.getPointMedian()) {
								add = CrkConfig.getCurrencyWinner();
							} else {
								add = CrkConfig.getCurrencyEveryone();
							}
							
							data.setCurrency(currency + add);
							data.setCurrencyLifetime(lifetime + add);
							data.setCrkCurrencyEarned(data.getCrkCurrencyEarned() + add);
							
							data.save(p);
							
							p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "You have gained " + add + (add == 1 ? " nugget." : " nuggets."));
							
						}
					}
				}
				
				for (Player p : room.getPlayers()) {
					if (p != null) {
						p.teleport(CrkConfig.getLobby());
						InvUtil.setLobbyInventory(p);
						
						if (PlayerData.isLoaded(p)) {
							PlayerData data = PlayerData.get(p);
							data.setCrkGamesPlayed(data.getCrkGamesPlayed() + 1); // Increase their play count.

							int points = room.getPointboard().get(p.getName());
							if (points > data.getCrkRecord()) { // If the player has set a new record:
								data.setCrkRecord(points);
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
							data.setCrkGamesPlayed(data.getCrkGamesPlayed() + 1); // Increase their play count.

							int points = room.getPointboard().get(p.getName());
							if (points > data.getCrkRecord()) { // If the player has set a new record:
								data.setCrkRecord(points);
								p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "You have set a new personal record: " + points + "!");
								
							}
							
							data.save(p);
							
						}
						
						Room.PLAYERS.remove(p);
						
						// Teleport the player to lobby.
						p.teleport(CrkConfig.getLobby());
						InvUtil.setLobbyInventory(p);
						
					}
				}

				Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Cranked match " + room.getRoomId() + " has ended.");
				room.resetRoom(true);
				
			} else if (result == RoomEndResult.NOT_ENOUGH_PLAYERS) {
				if (room.getWinningPlayer() != null) {
					room.gameOverMessage(room.getWinningPlayer()); // Broadcast who won.
				}

				if (Config.isRankedEnabled(RoomType.CRK)) { // Only change the player's Elo if it is enabled.
					for (Player p : room.getPlayers()) {
						if (PlayerData.isLoaded(p)) {
							PlayerData data = PlayerData.get(p);
							int oldelo = data.getCrkRanking();
							int newelo = data.getCrkRanking();
							
							if (room.getPointboard().get(p.getName()) > room.getPointMedian() - 1 || room.getWinningPlayer() == p.getName()) { // If the player won
								try {
									newelo = GameMathUtil.addElo(oldelo, room.getAvgElo());
								} catch (Exception e) {
									
								}
							} else {
								try {
									newelo = GameMathUtil.removeElo(oldelo, room.getAvgElo());
								} catch (Exception e) {
									
								}
							}
							
							int diff = newelo - oldelo;
							data.setCrkRanking(newelo);
							data.save(p);
							
							p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "Your Cranked Elo: " + oldelo + " > " + newelo + " (" + diff + ").");
							
						}
					}
				}
				
				for (Player p : room.getPlayers()) {
					if (p != null) {
						if (PlayerData.isLoaded(p)) {
							PlayerData data = PlayerData.get(p);
							data.setCrkGamesPlayed(data.getCrkGamesPlayed() + 1); // Increase their play count.

							int points = room.getPointboard().get(p.getName());
							if (points > data.getCrkRecord()) { // If the player has set a new record:
								data.setCrkRecord(points);
								p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "You have set a new personal record: " + points + "!");
								
							}
							
							data.save(p);
							
						}
						
						Room.PLAYERS.remove(p);
						
						// Teleport the player to the lobby.
						p.teleport(CrkConfig.getLobby());
						p.setLevel(0); // Change their levels because the countdown is not 0 yet.

						CrkSpawnManager.purgeEffects(p);
						InvUtil.setLobbyInventory(p);
						
					}
				}

				Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Cranked match " + room.getRoomId() + " has ended.");
				room.resetRoom(true);
				
			} else if (result == RoomEndResult.LAST_PERSON_STANDING) {
				if (room.getWinningPlayer() != null) {
					room.gameOverMessage(room.getWinningPlayer()); // Broadcast who won.
				}

				if (Config.isRankedEnabled(RoomType.CRK)) { // Only change the player's Elo if it is enabled.
					for (Player p : room.getPlayers()) {
						if (PlayerData.isLoaded(p)) {
							PlayerData data = PlayerData.get(p);
							int oldelo = data.getCrkRanking();
							int newelo = data.getCrkRanking();
							
							if (room.getPointboard().get(p.getName()) > room.getPointMedian() - 1 || room.getWinningPlayer() == p.getName()) { // If the player won
								try {
									newelo = GameMathUtil.addElo(oldelo, room.getAvgElo());
								} catch (Exception e) {
									
								}
							} else {
								try {
									newelo = GameMathUtil.removeElo(oldelo, room.getAvgElo());
								} catch (Exception e) {
									
								}
							}
							
							int diff = newelo - oldelo;
							data.setCrkRanking(newelo);
							data.save(p);
							
							p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "Your Cranked Elo: " + oldelo + " > " + newelo + " (" + diff + ").");
							
						}
					}
				}
				
				if (Config.isEconomyEnabled(RoomType.CRK)) {
					for (Player p : room.getPlayers()) {
						if (PlayerData.isLoaded(p)) {
							PlayerData data = PlayerData.get(p);
							int currency = data.getCurrency();
							int lifetime = data.getCurrencyLifetime();
							int add = 0;
							
							if (room.getWinningPlayer() == p.getName()) { // If the player won
								add = CrkConfig.getCurrencyFirst();
							} else if (room.getPointboard().get(p.getName()) > room.getPointMedian()) {
								add = CrkConfig.getCurrencyWinner();
							} else {
								add = CrkConfig.getCurrencyEveryone();
							}
							
							data.setCurrency(currency + add);
							data.setCurrencyLifetime(lifetime + add);
							data.setCrkCurrencyEarned(data.getCrkCurrencyEarned() + add);
							
							data.save(p);
							
							p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "You have gained " + add + (add == 1 ? " nugget." : " nuggets."));
							
						}
					}
				}
				
				for (Player p : room.getPlayers()) {
					if (p != null) {
						if (PlayerData.isLoaded(p)) {
							PlayerData data = PlayerData.get(p);
							data.setCrkGamesPlayed(data.getCrkGamesPlayed() + 1); // Increase their play count.

							int points = room.getPointboard().get(p.getName());
							if (points > data.getCrkRecord()) { // If the player has set a new record:
								data.setCrkRecord(points);
								p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "You have set a new personal record: " + points + "!");
								
							}
							
							data.save(p);
						}
						
						Room.PLAYERS.remove(p);
						
						// Teleport the player to the lobby.
						p.teleport(CrkConfig.getLobby());
						p.setLevel(0); // Change their levels because the countdown is not 0 yet.

						CrkSpawnManager.purgeEffects(p);
						InvUtil.setLobbyInventory(p);
						
					}
				}

				Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Cranked match " + room.getRoomId() + " has ended.");
				room.resetRoom(true);
				
			} else {
				RoomEndEvent event = new RoomEndEvent(room.getRoomId(), RoomEndResult.NOT_ENOUGH_PLAYERS);
				Bukkit.getPluginManager().callEvent(event);
				
			}
		}
	}
}