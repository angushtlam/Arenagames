package me.taur.arenagames.room;

import java.util.HashMap;

import me.taur.arenagames.Config;
import me.taur.arenagames.ffa.FfaConfig;
import me.taur.arenagames.util.RoomType;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Room {
	public static HashMap<String, Room> ROOMS = new HashMap<String, Room>();
	public static HashMap<Player, String> PLAYERS = new HashMap<Player, String>();
	
	private String roomId;
	private RoomType roomType;
	private boolean gameInProgress, gameInWaiting, premium;
	private int waiting, countdown;
	private Player[] players;

	public Room() {
		
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}
	
	public boolean isGameInProgress() {
		return gameInProgress;
	}

	public void setGameInProgress(boolean gameInProgress) {
		this.gameInProgress = gameInProgress;
	}
	
	public boolean isGameInWaiting() {
		return gameInWaiting;
	}

	public void setGameInWaiting(boolean gameInWaiting) {
		this.gameInWaiting = gameInWaiting;
	}
	
	public int getPlayersInRoom() {
		if (players == null) {
			return 0;
		}
		
		return players.length;
	}
	
	public int getWaitTimer() {
		return waiting;
	}

	public void setWaitTimer(int waiting) {
		this.waiting = waiting;
	}
	
	public void resetWaitTimer() {
		this.setWaitTimer(Config.getWaitTimer(roomType));
		gameInWaiting = false;
		
	}

	public boolean isPremium() {
		return premium;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	public int getCountdownTimer() {
		return countdown;
	}

	public void setCountdownTimer(int countdown) {
		this.countdown = countdown;
	}

	public Player[] getPlayers() {
		return players;
	}

	public void addPlayer(Player p) {
		int length = getPlayersInRoom();
		Player[] set = new Player[length + 1];
		
		for (int i = 0; i < length; i++) {
			set[i] = players[i];
			
		}
		
		set[length] = p; 
		players = set;
		
	}
	
	public void setPlayers(Player[] players) {
		this.players = players;
	}
	
	public void removePlayer(Player p) {
		Player[] roompl = this.getPlayers();
		
		int index = -1;
		
		for (int i = 0; (i < roompl.length) && (index == -1); i++) {
			if (roompl[i] == p) {
				index = i;
			}
		}
		
		this.setPlayers((Player[]) ArrayUtils.remove(roompl, index));

	}
	
	public void removeAllPlayers() {
		this.players = null;
		
	}
	
	public boolean isPlayerInRoom(Player p) {
		if (getPlayers() == null) {
			return false;
			
		}
		
		for (Player pl : getPlayers()) {
			if (pl == p) {
				return true;
				
			}
		}
		
		return false;
		
	}
	
	public void teleportPlayersToLobby() {
		if (this.getPlayers() != null) {
			for (Player p : this.getPlayers()) {
				p.teleport(FfaConfig.getLobby());
				
			}
		}
		
	}
	
	public void gameOverMessage(String winner) {
		for (Player p : this.getPlayers()) {
			if (p != null) {
				p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + " --- GAME OVER! ---");
				p.sendMessage(ChatColor.YELLOW + winner + ChatColor.ITALIC + " is the winner of this round!");
				p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + " -----------------");
			}
		}
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (!isPlayerInRoom(p)) {
				p.sendMessage(ChatColor.AQUA + winner + ChatColor.ITALIC + " won the match in " + this.getRoomId() + ".");
			}
		}
		
	}
	
	public void waitStartMessage(RoomType type) {
		int waitcount = Config.getWaitTimer(type);
		int minute = waitcount % 60;
		
		String plural = minute == 1 ? "minute" : "minutes";
		String gameStartIn = minute == 0 ? "in " + minute + " " + plural : "soon";
		
		for (Player p : this.getPlayers()) {
			if (p != null) {
				p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "Wait timer has started. Game will start " + gameStartIn + ".");
			}
		}
		
	}
	
	public void waitStartMessage(Player p, RoomType type) {
		int waitcount = Config.getWaitTimer(type);
		int minute = waitcount % 60;
		
		String plural = minute == 1 ? "minute" : "minutes";
		String gameStartIn = minute == 0 ? "in " + minute + " " + plural : "soon";
		
		p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "Wait timer has started. Game will start " + gameStartIn + ".");
		
	}
	
	public void waitCancelledMessage(RoomType type) {
		int wait = Config.getMinPlayersInWait(type);
		
		if (getPlayers() != null) {
			for (Player p : this.getPlayers()) {
				if (p != null) {
					p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "Wait timer is stopped due to a lack of players.");
					p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "The game will start " + Config.getWaitTimer(RoomType.FFA) + " after " + wait + (wait == 1 ? " person " : " people ") + (wait == 1 ? "has" : "have") + " joined.");
				}
			}
		}
		
	}
	
	public void resetRoomBasics(boolean areYouSure) {
		if (areYouSure) {
			removeAllPlayers();
			resetWaitTimer();
			
			gameInProgress = false;
			// Set countdown timer on start, not here.
		}
	}
	
}