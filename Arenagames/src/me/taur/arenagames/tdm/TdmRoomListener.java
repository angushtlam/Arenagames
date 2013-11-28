package me.taur.arenagames.tdm;

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

public class TdmRoomListener implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void roomEnd(RoomEndEvent evt) {
		if (evt.getRoom() == null) { // Make sure the room exists.
			return;
		}
		
		Room r = evt.getRoom();
		RoomEndResult result = evt.getResult();
		
		if (r.getRoomType() == RoomType.TDM) {
			TdmRoom room = (TdmRoom) r;
			
			if (result == RoomEndResult.TIMER_OVER) { // If the game ended by timer:
				if (room.getWinningTeam() != null) {
					room.gameOverMessage(room.getWinningTeam().getName()); // Broadcast who won.
				}

				if (Config.isRankedEnabled(RoomType.TDM)) { // Only change the player's Elo if it is enabled.
					for (Player p : room.getPlayers()) {
						if (PlayerData.isLoaded(p)) {
							PlayerData data = PlayerData.get(p);
							int oldelo = data.getFfaRanking();
							int newelo = data.getFfaRanking();
							
							if (room.getPointboard().get(p.getName()) == room.getWinningTeam().getId()) { // If the player's team won
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
							data.setTdmRanking(newelo);
							data.save(p);
							
							p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "Your TDM Elo: " + oldelo + " > " + newelo + " (" + diff + ").");
							
						}
					}
				}
				
				if (Config.isEconomyEnabled(RoomType.TDM)) {
					for (Player p : room.getPlayers()) {
						if (PlayerData.isLoaded(p)) {
							PlayerData data = PlayerData.get(p);
							int currency = data.getCurrency();
							int lifetime = data.getCurrencyLifetime();
							int add = 0;
							
							if (room.getPointboard().get(p.getName()) == room.getWinningTeam().getId()) { // If the player's team won
								add = TdmConfig.getCurrencyWinner();
							} else {
								add = TdmConfig.getCurrencyLoser();
							}
							
							data.setCurrency(currency + add);
							data.setCurrencyLifetime(lifetime + add);
							data.setTdmCurrencyEarned(data.getTdmCurrencyEarned() + add);
							
							data.save(p);
							
							p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "You have gained " + add + (add == 1 ? " nugget." : " nuggets."));
							
						}
					}
				}
				
				for (Player p : room.getPlayers()) {
					if (p != null) {					
						Room.PLAYERS.remove(p);
						
						// Teleport the player to lobby.
						p.teleport(TdmConfig.getLobby());
						InvUtil.setLobbyInventory(p);
						
					}
				}

				Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Team Deathmatch match " + room.getRoomId() + " has ended.");
				room.resetRoom(true);
				
			} else if (result == RoomEndResult.NOT_ENOUGH_PLAYERS) {
				if (Config.isRankedEnabled(RoomType.FFA)) { // Only change the player's Elo if it is enabled.
					for (Player p : room.getPlayers()) {
						if (p != null) {
							p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "Your TDM Elo did not change due to a premature game ending.");
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
							data.setTdmGamesPlayed(data.getTdmGamesPlayed() + 1); // Increase their play count.

							data.save(p);
						}

						Room.PLAYERS.remove(p);

						// Teleport the player to the lobby.
						p.teleport(TdmConfig.getLobby());
						p.setLevel(0); // Change their levels because the countdown is not 0 yet.
						TdmSpawnManager.purgeEffects(p);
						InvUtil.setLobbyInventory(p);
						
					}
				}

				Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Team Deathmatch match " + room.getRoomId() + " has ended.");
				room.resetRoom(true);
				
			} else {
				RoomEndEvent event = new RoomEndEvent(room.getRoomId(), RoomEndResult.NOT_ENOUGH_PLAYERS);
				Bukkit.getPluginManager().callEvent(event);
				
			}
		}
	}
}