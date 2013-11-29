package me.taur.arenagames.ffa;

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

public class FfaRoomListener implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void roomEnd(RoomEndEvent evt) {
		if (evt.getRoom() == null) { // Make sure the room exists.
			return;
		}
		
		Room r = evt.getRoom();
		RoomEndResult result = evt.getResult();
		
		if (r.getRoomType() == RoomType.FFA) {
			FfaRoom room = (FfaRoom) r;
			
			if (result == RoomEndResult.TIMER_OVER) { // If the game ended by timer:
				if (room.getWinningPlayer() != null) {
					room.gameOverMessage(room.getWinningPlayer()); // Broadcast who won.
				}

				if (Config.isRankedEnabled(RoomType.FFA)) { // Only change the player's Elo if it is enabled.
					for (Player p : room.getPlayers()) {
						if (PlayerData.isLoaded(p)) {
							PlayerData data = PlayerData.get(p);
							int oldelo = data.getFfaRanking();
							int newelo = data.getFfaRanking();
							
							if (room.getPointboard().get(p.getName()) > room.getPointMedian() || room.getWinningPlayer() == p.getName()) { // If the player won
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
							data.setFfaRanking(newelo);
							data.save(p);
							
							p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "Your FFA Elo: " + oldelo + " > " + newelo + " (" + diff + ").");
							
						}
					}
				}
				
				if (Config.isEconomyEnabled(RoomType.FFA)) {
					for (Player p : room.getPlayers()) {
						if (PlayerData.isLoaded(p)) {
							PlayerData data = PlayerData.get(p);
							int currency = data.getCurrency();
							int lifetime = data.getCurrencyLifetime();
							int add = 0;
							
							if (room.getWinningPlayer() == p.getName()) { // If the player won
								add = FfaConfig.getCurrencyFirst();
							} else if (room.getPointboard().get(p.getName()) > room.getPointMedian()) {
								add = FfaConfig.getCurrencyWinner();
							} else {
								add = FfaConfig.getCurrencyEveryone();
							}
							
							data.setCurrency(currency + add);
							data.setCurrencyLifetime(lifetime + add);
							data.setFfaCurrencyEarned(data.getFfaCurrencyEarned() + add);
							
							data.save(p);
							
							p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "You have gained " + add + (add == 1 ? " nugget." : " nuggets."));
							
						}
					}
				}
				
				for (Player p : room.getPlayers()) {
					if (p != null) {					
						if (PlayerData.isLoaded(p)) {
							PlayerData data = PlayerData.get(p);
							data.setFfaGamesPlayed(data.getFfaGamesPlayed() + 1); // Increase their play count.
							
							if (room.getWinningPlayer().equals(p.getName())) {
								data.setFfaGamesWon(data.getFfaGamesWon() + 1); // If the player won the game.
							}
							
							int add = 0;
							if (room.getWinningPlayer() == p.getName()) { // Give the player EXP.
								add = FfaConfig.getExpFirst();
							} else if (room.getPointboard().get(p.getName()) > room.getPointMedian()) {
								add = FfaConfig.getExpWinner();
							} else {
								add = FfaConfig.getExpEveryone();
							}
							
							data.setExp(data.getExp() + add);

							int points = room.getPointboard().get(p.getName());
							if (points > data.getFfaRecord()) { // If the player has set a new record:
								data.setFfaRecord(points);
								p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "You have set a new personal record: " + points + "!");
								
							}
							
							data.save(p);
						}
						
						Room.PLAYERS.remove(p);
						
						// Teleport the player to lobby.
						p.teleport(FfaConfig.getLobby());
						InvUtil.setLobbyInventory(p);
						
					}
				}

				Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Free For All match " + room.getRoomId() + " has ended.");
				room.resetRoom(true);
				
			} else if (result == RoomEndResult.NOT_ENOUGH_PLAYERS) {
				if (Config.isRankedEnabled(RoomType.FFA)) { // Only change the player's Elo if it is enabled.
					for (Player p : room.getPlayers()) {
						if (p != null) {
							p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "Your FFA Elo did not change due to a premature game ending.");
						}
					}
				}
				
				if (Config.isEconomyEnabled(RoomType.FFA)) { // Only change the player's Elo if it is enabled.
					for (Player p : room.getPlayers()) {
						if (p != null) {
							p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "You did not gain any nuggets due to a premature game ending.");
						}
					}
				}

				for (Player p : room.getPlayers()) {
					if (p != null) {
						if (PlayerData.isLoaded(p)) {
							PlayerData data = PlayerData.get(p);
							data.setFfaGamesPlayed(data.getFfaGamesPlayed() + 1); // Increase their play count.

							int points = room.getPointboard().get(p.getName());
							if (points > data.getFfaRecord()) { // If the player has set a new record:
								data.setFfaRecord(points);
								p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "You have set a new personal record: " + points + "!");

							}

							data.save(p);
						}

						Room.PLAYERS.remove(p);

						// Teleport the player to the lobby.
						p.teleport(FfaConfig.getLobby());
						p.setLevel(0); // Change their levels because the countdown is not 0 yet.
						FfaSpawnManager.purgeEffects(p);
						InvUtil.setLobbyInventory(p);
						
					}
				}

				Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Free For All match " + room.getRoomId() + " has ended.");
				room.resetRoom(true);
				
			} else {
				RoomEndEvent event = new RoomEndEvent(room.getRoomId(), RoomEndResult.NOT_ENOUGH_PLAYERS);
				Bukkit.getPluginManager().callEvent(event);
				
			}
		}
	}
}