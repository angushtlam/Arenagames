package me.taur.arenagames.ffa;

import java.util.HashMap;

import me.taur.arenagames.Config;
import me.taur.arenagames.util.Room;
import me.taur.arenagames.util.RoomType;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class FfaRoom extends Room {
	private String mapname;
	private HashMap<String, Integer> scoreboard;
	
	public FfaRoom(String roomId) {
		this.scoreboard = new HashMap<String, Integer>();
		this.setRoomId(roomId);
		this.setRoomType(RoomType.FFA);
		
	}

	public String getMapName() {
		return mapname;
	}

	public void setMapName(String mapname) {
		this.mapname = mapname;
	}

	public HashMap<String, Integer> getScoreboard() {
		return scoreboard;
	}

	public void setScoreboard(HashMap<String, Integer> scoreboard) {
		this.scoreboard = scoreboard;
	}
	
	public void setPlayerScore(Player p, int amt) {
		scoreboard.put(p.getName(), amt);
		
	}
	
	public String getMapNameFancy() {
		return FfaConfig.get().getString("ffa.maps." + this.getMapName() + ".info.map-name");
		
	}
	
	public String getMapAuthor() {
		return FfaConfig.get().getString("ffa.maps." + this.getMapName() + ".info.author");
		
	}
	
	public String getWinningPlayer() {
		String player = "";
		boolean firstLoop = true;
		int score = 0;
		
		for (String name : scoreboard.keySet()) {
			int amt = scoreboard.get(name);
			
			if (firstLoop) {
				firstLoop = false;
				
				player = name;
				score = amt;
				
			}
			
			if (score < amt) {
				player = name;
				score = amt;
				
			}
		}
		
		return player;

	}
	
	public void playerDied(Player p, Player killer) {
		int died = scoreboard.get(p.getName());
		int kill = scoreboard.get(killer.getName());
		
		for (Player pl : this.getPlayers()) {
			pl.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + p.getName() + " has been executed by " + killer.getName() + "!");
			
		}
		
		if (died - 1 > 0) {
			p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "You have lost 1 point for dying.");
			scoreboard.put(p.getName(), died - 1);
		} else {
			p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "You have lost " + died + (died == 1 ? " point" : " points") + " for dying.");
			scoreboard.put(p.getName(), 0);
		}
		
		scoreboard.put(killer.getName(), kill + 3);
		killer.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "You have gained 3 points by slaying " + p.getName() + ".");
		
		p.teleport(FfaConfig.getPossibleSpawnLocation(this));
		p.setHealth(p.getMaxHealth());
		
	}
	
	public void playerDied(Player p, EntityType ent) {
		int died = scoreboard.get(p.getName());
		String entity = ent.toString().charAt(0) + ent.toString().toLowerCase().substring(1);
		
		for (Player pl : this.getPlayers()) {
			pl.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + p.getName() + " has been slain by " + entity + ".");
			
		}
		
		if (died - 2 > 0) {
			p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "You have lost 2 points for dying.");
			scoreboard.put(p.getName(), died - 2);
		} else {
			p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "You have lost " + died + (died == 1 ? " point" : " points") + " for dying.");
			scoreboard.put(p.getName(), 0);
		}
		
		p.teleport(FfaConfig.getPossibleSpawnLocation(this));
		p.setHealth(p.getMaxHealth());
		
	}
	
	public void playerDied(Player p, DamageCause cause) {
		int died = scoreboard.get(p.getName());
		String c = "";
		
		if (cause.equals(DamageCause.BLOCK_EXPLOSION)) {
			c = "has been exploded by TNT";
			
		} else if (cause.equals(DamageCause.CONTACT)) {
			c = "is pricked to death by Cactus";
			
		} else if (cause.equals(DamageCause.DROWNING)) {
			c = "drowned in water";
			
		} else if (cause.equals(DamageCause.FALL)) {
			c = "fell too far";
			
		} else if (cause.equals(DamageCause.FALLING_BLOCK)) {
			c = "got severed in the head";
			
		} else if (cause.equals(DamageCause.FIRE)) {
			c = "burnt into ashes";
			
		} else if (cause.equals(DamageCause.LAVA)) {
			c = "swam in lava";
			
		} else if (cause.equals(DamageCause.LIGHTNING)) {
			c = "has been struck by lightning";
			
		} else if (cause.equals(DamageCause.MAGIC)) {
			c = "died by magic";
			
		} else if (cause.equals(DamageCause.POISON)) {
			c = "poisoned to death";
			
		} else if (cause.equals(DamageCause.STARVATION)) {
			c = "starved to death";
			
		} else if (cause.equals(DamageCause.SUFFOCATION)) {
			c = "couldn't breath";
			
		} else if (cause.equals(DamageCause.THORNS)) {
			c = "died by an accident";
			
		} else if (cause.equals(DamageCause.WITHER)) {
			c = "withered away";
			
		} else {
			c = "died";
			
		}
		
		for (Player pl : this.getPlayers()) {
			pl.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + p.getName() + " " + c + ".");
			
		}
		
		if (died - 2 > 0) {
			p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "You have lost 2 points for dying.");
			scoreboard.put(p.getName(), died - 2);
		} else {
			p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "You have lost " + died + (died == 1 ? " point" : " points") + " for dying.");
			scoreboard.put(p.getName(), 0);
		}
		
		p.teleport(FfaConfig.getPossibleSpawnLocation(this));
		p.setHealth(p.getMaxHealth());
		
	}
	

	public void playerDied(Player p) {
		int died = scoreboard.get(p.getName());
		
		for (Player pl : this.getPlayers()) {
			pl.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + p.getName() + " has died.");
			
		}
		
		if (died - 2 > 0) {
			p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "You have lost 2 points for dying.");
			scoreboard.put(p.getName(), died - 2);
		} else {
			p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "You have lost " + died + (died == 1 ? " point" : " points") + " for dying.");
			scoreboard.put(p.getName(), 0);
		}
		
		p.teleport(FfaConfig.getPossibleSpawnLocation(this));
		p.setHealth(p.getMaxHealth());
		
	}
	
	public void startGame() {
		for (Player p : this.getPlayers()) {
			p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " --- THE GAME HAS STARTED! ---");
			p.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "Map: " + this.getMapNameFancy() + " by " + this.getMapAuthor() + ".");
			p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " --- --------------------- ---");
			
			p.teleport(FfaConfig.getPossibleSpawnLocation(this));
			scoreboard.put(p.getName(), 0);
			
		}
		
		this.setCountdownTimer(Config.getCountdown(RoomType.FFA));
		
	}
	
	public void resetRoom(boolean areYouSure) {
		if (areYouSure) {
			scoreboard = null;
			scoreboard = new HashMap<String, Integer>();
			this.setMapName(null);
			this.resetRoomBasics(areYouSure);
			
		}
		
	}
	
}
