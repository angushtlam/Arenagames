package me.taur.arenagames.crk;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import me.taur.arenagames.Config;
import me.taur.arenagames.ffa.FfaConfig;
import me.taur.arenagames.item.InvUtil;
import me.taur.arenagames.player.PlayerData;
import me.taur.arenagames.player.Premium;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.RoomType;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CrkRoom extends Room {
	private String mapname;
	private HashMap<String, Integer> pointboard;
	private HashMap<Player, Integer> playertimer;
	private HashMap<Player, Integer> kit;
	
	private int playerAlive;
	
	public CrkRoom(String roomId) {
		this.pointboard = new HashMap<String, Integer>();
		this.kit = new HashMap<Player, Integer>();
		this.playertimer = new HashMap<Player, Integer>();
		
		this.setRoomId(roomId);
		this.setRoomType(RoomType.CRK);
		this.createScoreboard();
		
	}
	
	public void updateScoreboard() {
		if (Room.SCOREBOARDS.get(this.getRoomId()) != null) {
			this.setScoreboardTitle(this.scoreboardTimer());
			
			if (this.isGameInProgress() && this.getWinningPlayer() != null) {
				String winner = this.getWinningPlayer();
				this.setScoreboardSideField("Most Kills", this.getPointboard().get(winner).intValue());
				
			} else {
				this.setScoreboardSideField("Most Kills", 0);
			}
			
			this.setScoreboardSideField("Elo Average", this.getAvgElo());
			this.setScoreboardSideField("Players", this.getPlayersInRoom());
			
		}
	}
	
	public String scoreboardTimer() {
		String str = "";
		
		if (this.isGameInProgress()) {
			str = ChatColor.GOLD + "" + ChatColor.BOLD + "Game: " + ChatColor.YELLOW;
			
			int timer = this.getCountdownTimer();
			int minute = timer / 60;
			int seconds = timer % 60;
				
			if (minute == 0) {
				str = str + "0";
			} else {
				str = str + minute;
			}
			
			if (seconds % 2 != 0) {
				str = str + ChatColor.GRAY + ":" + ChatColor.YELLOW;
			} else {
				str = str + ChatColor.DARK_GRAY + ":" + ChatColor.YELLOW;
			}
				
			if (seconds < 10) {
				str = str + "0" + seconds;
			} else {
				str = str + seconds;
			}
			
		} else if (this.isGameInWaiting()) {
			str = ChatColor.AQUA + "" + ChatColor.BOLD + "Wait: " + ChatColor.YELLOW;
			
			int timer = this.getWaitTimer();
			int minute = timer / 60;
			int seconds = timer % 60;
				
			if (minute == 0) {
				str = str + "0";
					
			} else {
				str = str + minute;
					
			}
			
			if (seconds % 2 != 0) {
				str = str + ChatColor.GRAY + ":" + ChatColor.YELLOW;
			} else {
				str = str + ChatColor.DARK_GRAY + ":" + ChatColor.YELLOW;
			}
				
			if (seconds < 10) {
				str = str + "0" + seconds;
			} else {
				str = str + seconds;
			}
			
		} else {
			str = ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Waiting";
		}
		
		return str;
		
	}
	
	public void giveKit(Player p) {
		String kitname = CrkConfig.getKitName(this.kit.get(p));
		p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "You have been given a " + kitname + " kit.");
		
		PlayerInventory inv = p.getInventory();
		int playerkit = this.kit.get(p); // Get what kit the player has.
		
		for (String item : CrkConfig.getKitItems(playerkit)) {
			ItemStack i = InvUtil.convertToItemStack(item); 

			// Automatically set the player's armor if the item is an armor, and they don't have the armor on.
			if (inv.getHelmet() == null && i.getType().name().contains("HELMET")) {
				inv.setHelmet(i);
				continue;
				
			}
			
			if (inv.getHelmet() == null && i.getType().name().equals(Material.PUMPKIN)) {
				inv.setHelmet(i);
				continue;
				
			}
			
			if (inv.getHelmet() == null && i.getType().name().equals(Material.JACK_O_LANTERN)) {
				inv.setHelmet(i);
				continue;
				
			}
			
			if (inv.getChestplate() == null && i.getType().name().contains("CHESTPLATE")) {
				inv.setChestplate(i);
				continue;
				
			}
			
			if (inv.getLeggings() == null && i.getType().name().contains("LEGGINGS")) {
				inv.setLeggings(i);
				continue;
				
			}
			
			if (inv.getBoots() == null && i.getType().name().contains("BOOTS")) {
				inv.setBoots(i);
				continue;
				
			}
			
			inv.addItem(i);
			
		}
		
		InvUtil.updatePlayerInv(p);
		
	}
	
	public void addRefill(Player p) {
		p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "You have been given refills for the kill.");
		
		PlayerInventory inv = p.getInventory();
		int playerkit = this.kit.get(p); // Get what kit the player has.
		
		for (String item : CrkConfig.getKitRefill(playerkit)) {
			ItemStack i = InvUtil.convertToItemStack(item); 

			// Automatically set the player's armor if the item is an armor, and they don't have the armor on.
			if (inv.getHelmet() == null && i.getType().name().contains("HELMET")) {
				inv.setHelmet(i);
				continue;
				
			}
			
			if (inv.getHelmet() == null && i.getType().name().equals(Material.PUMPKIN)) {
				inv.setHelmet(i);
				continue;
				
			}
			
			if (inv.getHelmet() == null && i.getType().name().equals(Material.JACK_O_LANTERN)) {
				inv.setHelmet(i);
				continue;
				
			}
			
			if (inv.getChestplate() == null && i.getType().name().contains("CHESTPLATE")) {
				inv.setChestplate(i);
				continue;
				
			}
			
			if (inv.getLeggings() == null && i.getType().name().contains("LEGGINGS")) {
				inv.setLeggings(i);
				continue;
				
			}
			
			if (inv.getBoots() == null && i.getType().name().contains("BOOTS")) {
				inv.setBoots(i);
				continue;
				
			}
			
			inv.addItem(i);
			
		}
		
		InvUtil.updatePlayerInv(p);
		
	}
	
	public String getWinningPlayer() {
		String player = "";
		boolean firstLoop = true;
		int score = 0;
		
		for (String name : this.pointboard.keySet()) {
			int amt = this.pointboard.get(name);
			
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
	
	public int getPointMedian() {
		TreeSet<Integer> set = new TreeSet<Integer>();
		
		for (int i : this.getPointboard().values()) {
			set.add(i);
		}
		
		int half = set.size() / 2;
		return (int) set.toArray()[half];
		
	}
	
	public int getAvgElo() {
		int total = 0;
		
		if (this.getPlayers() != null) {
			for (Player p : this.getPlayers()) {
				if (p != null) {
					if (PlayerData.isLoaded(p)) {
						PlayerData data = PlayerData.get(p);
						total = total + data.getCrkRanking();
					}
				}
			}
		}
		
		int pl = this.getPlayersInRoom();
		
		if (pl == 0) {
			pl = 1;
		}
		
		return total / pl;
		
	}
	
	public void crankPlayer(Player p) {
		int reset = CrkConfig.getCrankedTimer();
		int kill = 0;
		
		if (this.pointboard.get(p.getName()) != null) {
			kill = this.pointboard.get(p.getName());
		}
		
		this.pointboard.put(p.getName(), kill + 1);
		
		this.playertimer.put(p, reset); // Reset the player's timer.
		p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "Get another kill within " + reset + " seconds or you'll die!");
		
		int potionstr = (kill / 3); // Perks increases every 3 kills
		if (potionstr > 0) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, CrkConfig.getCrankedTimer() * 20, potionstr));
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, CrkConfig.getCrankedTimer() * 20, potionstr));
			
		}
		
		addRefill(p);
		
	}
	
	public void killPlayer(Player p) {
		p.setLevel(0);
		p.setHealth(10.0);
		p.setFoodLevel(19);
		
		this.getTimer().put(p, -1);
		this.setPlayerAlive(this.getPlayerAlive() - 1);
		
		CrkSpawnManager.kill(p, CrkConfig.getPossibleSpawnLocation(this));
		InvUtil.clearPlayerInv(p);
		
	}
	
	public boolean isPlayerDead(Player p) {
		if (p == null) {
			return true;
		}

		if (!this.getTimer().containsKey(p)) {
			return true;
		}
		
		if (this.getTimer().get(p) < 0) {
			return true;
		}
		
		return false;
		
	}
	
	public void playerDied(Player p, String msg) {
		PlayerData data = null;
		if (PlayerData.isLoaded(p)) {
			data = PlayerData.get(p);
		} else {
			data = new PlayerData(p);
		}
		
		data.setCrkTotalDeaths(data.getCrkTotalDeaths() + 1); // Increase death count
		
		int kills = this.pointboard.get(p.getName());
		
		if (this.getPlayers() != null) {
			for (Player pl : this.getPlayers()) {
				if (pl != null) {
					pl.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + msg);
				}
			}
		}
		
		p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "You died with " + kills + (kills == 1 ? " kill" : " kills") + ".");
		p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "You have been removed from this arena.");
		
		this.killPlayer(p);
	}
	
	public void playerDied(Player p, Player killer) { // Player loses 1 point for being executed by another player.
		PlayerData data = null;
		if (PlayerData.isLoaded(killer)) {
			data = PlayerData.get(killer);
		} else {
			data = new PlayerData(killer);
		}
		
		data.setCrkTotalKills(data.getCrkTotalKills() + 1); // Increase kill count
		
		int kill = this.pointboard.get(killer.getName()) + 1;
		killer.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "You are on a " + kill + " kill kill-streak.");
		this.crankPlayer(killer);
		this.playerDied(p, p.getName() + " has been executed by " + killer.getName() + "!");
		
	}
	
	public void playerDied(Player p, EntityType ent) {
		String entity = ent.toString().charAt(0) + ent.toString().toLowerCase().substring(1);
		this.playerDied(p, p.getName() + " has been slain by " + entity + ".");
		
	}
	
	public void playerDied(Player p, DamageCause cause) {
		String c = "";
		
		if (cause.equals(DamageCause.BLOCK_EXPLOSION)) {
			c = "has been exploded by TNT";
		} else if (cause.equals(DamageCause.CONTACT)) {
			c = "is pricked to death by cactus";
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
			c = "died by parried damage";
		} else if (cause.equals(DamageCause.WITHER)) {
			c = "withered away";
		} else {
			c = "died";
		}
		
		this.playerDied(p, p.getName() + " " + c + ".");
		
	}

	public void playerDied(Player p) {
		this.playerDied(p, p.getName() + " died.");
	}
	
	public void startGame() {
		for (Player p : this.getPlayers()) {
			if (p != null) {
				p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " --- THE GAME HAS STARTED! ---");
				p.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "Map: " + this.getMapNameFancy() + " by " + this.getMapAuthor() + ".");
				p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " --- -------------------- ---");
				p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "Get a kill within " + CrkConfig.getCrankedTimer() + " seconds or you'll die!");
				
				if (!this.kit.containsKey(p)) { // If the player didn't pick a kit, give them a random one.
					ConfigurationSection cs = CrkConfig.getKits();
					int kits = cs.getKeys(false).size();
					
					Random rand = new Random();
					boolean kitloop = true;
					int r = 0;
					
					while (kitloop) { // Make sure non-Premiums cannot random into Premium kits. 
						r = rand.nextInt(kits);
						
						if (FfaConfig.isKitPremium(r) && !(Premium.isPremium(p))) {
							continue;
						}
						
						kitloop = false;
						
					}
					
					this.kit.put(p, r);
					p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "You forgot to set your kit while in queue. Here's a random one.");
					
				}
				
				InvUtil.clearPlayerInv(p);
				
				CrkSpawnManager.spawn(p, CrkConfig.getPossibleSpawnLocation(this));
				this.giveKit(p);
				
				this.pointboard.put(p.getName(), 0);
				this.playertimer.put(p, CrkConfig.getCrankedTimer());
				this.setPlayerAlive(this.getPlayersInRoom());
				
			}
		}
		
		this.updateSigns();
		this.setCountdownTimer(Config.getCountdown(RoomType.CRK));
		
	}
	
	public void updateSigns() {
		Location[] locs = CrkConfig.getSignsStored(this.getRoomId());
		Set<Location> signloc = new HashSet<Location>(); // Make a hashmap for convienence.
		
		if (locs != null) { // Make the Array a Set for now cuz it's convenient.
			for (Location loc : locs) {
				signloc.add(loc);
			}
		}
		
		boolean fix = false;
		
		if (signloc != null) {
			for (Location l : signloc) {
				if (l == null) {
					fix = true;
					continue;
					
				}
				
				Block b = l.getBlock();
				if (!b.getType().name().contains("SIGN")) {
					b.breakNaturally();
					fix = true;
					continue;
					
				}
				
				BlockState state = b.getState();
				if (!(state instanceof Sign)) {
					b.breakNaturally();
					fix = true;
					continue;
					
				}
				
				Sign sign = (Sign) b.getState();
				sign.setLine(0, RoomType.CRK.getColor() + "" + ChatColor.BOLD + "[" + RoomType.CRK.getSign() + "]");
				sign.setLine(1, (this.isPremium() ? ChatColor.GOLD + "" : "") + this.getRoomId());
				sign.setLine(2, ChatColor.ITALIC + "" + this.getPlayersInRoom() + "/" + Config.getPlayerLimit(RoomType.CRK));
				
				String setl3 = ChatColor.GREEN + "Queue Open";
				if (this.isGameInProgress()) {
					setl3 = ChatColor.YELLOW + "In Progress";
				} else if (this.isGameInWaiting()) {
					setl3 = ChatColor.AQUA + "Starting Soon";
				}
				
				sign.setLine(3, setl3);
				sign.update();
			}
			
			if (fix) { // Fix signs.
				Set<Location> signlocs = new HashSet<Location>();
				
				for (Location l : locs) { // Copy the list first
					signlocs.add(l);
					
				}
				
				CrkConfig.clearSignLocations(this.getRoomId());
				
				int i = 0;
				if (locs != null) {
					for (Location l : signlocs) {
						if (l == null) {
							continue;
						}
						
						Block b = l.getBlock();
						if (!b.getType().name().contains("SIGN")) {
							continue;
						}
						
						BlockState state = b.getState();
						if (!(state instanceof Sign)) {
							continue;
						}
							
						CrkConfig.setSignLocation(this.getRoomId(), i, l);
						i++;
							
					}
				}
			}
		}
	}
	
	public void resetRoom(boolean areYouSure) {
		if (areYouSure) {
			this.pointboard = null;
			this.pointboard = new HashMap<String, Integer>();
			
			this.playertimer = null;
			this.playertimer = new HashMap<Player, Integer>();
			
			this.kit = null;
			this.kit = new HashMap<Player, Integer>();
			
			this.removeAllPlayerScoreboard();
			
			for (Player p : this.getPlayers()) {
				p.setLevel(0);
			}
			
			this.setPlayerAlive(0);
			this.setMapName(null);
			this.resetRoomBasics(areYouSure);
			this.updateSigns();
			
		}
	}
	
	public String getMapNameFancy() {
		return CrkConfig.getData().getString("crk.maps." + this.getMapName() + ".info.map-name");
	}
	
	public String getMapAuthor() {
		return CrkConfig.getData().getString("crk.maps." + this.getMapName() + ".info.author");
	}
	
	public String getMapName() {
		return this.mapname;
	}

	public void setMapName(String mapname) {
		this.mapname = mapname;
	}

	public HashMap<String, Integer> getPointboard() {
		return this.pointboard;
	}

	public void setPointboard(HashMap<String, Integer> pointboard) {
		this.pointboard = pointboard;
	}
	
	public HashMap<Player, Integer> getTimer() {
		return this.playertimer;
	}

	public void setTimer(HashMap<Player, Integer> timer) {
		this.playertimer = timer;
	}
	
	public void setPlayerScore(Player p, int amt) {
		this.pointboard.put(p.getName(), amt);
	}
	
	public HashMap<Player, Integer> getKit() {
		return this.kit;
	}

	public void setKit(HashMap<Player, Integer> kit) {
		this.kit = kit;
	}

	public int getPlayerAlive() {
		return playerAlive;
	}

	public void setPlayerAlive(int playerAlive) {
		this.playerAlive = playerAlive;
	}
}