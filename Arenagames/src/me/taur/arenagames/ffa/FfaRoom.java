package me.taur.arenagames.ffa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import me.taur.arenagames.Config;
import me.taur.arenagames.item.CustomItem;
import me.taur.arenagames.item.InvUtil;
import me.taur.arenagames.player.Permission;
import me.taur.arenagames.player.PlayerData;
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

public class FfaRoom extends Room {
	private String mapname;
	private HashMap<String, Integer> pointboard;
	private HashMap<Player, Integer> kit;
	
	public FfaRoom(String roomId) {
		this.pointboard = new HashMap<String, Integer>();
		this.kit = new HashMap<Player, Integer>();
		
		this.setRoomId(roomId);
		this.setRoomType(RoomType.FFA);
		this.createScoreboard();
		
	}
	
	public void updateScoreboard() {
		if (Room.SCOREBOARDS.get(this.getRoomId()) != null) {
			this.setScoreboardTitle(this.scoreboardTimer());
			
			if (this.isGameInProgress()) {
				String winner = this.getWinningPlayer();
				this.setScoreboardSideField("Highest Score", this.getPointboard().get(winner).intValue());
				
			} else {
				this.setScoreboardSideField("Highest Score", 0);
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
	
	public void giveKit(Player p, int kitnum) {
		String kitname = FfaConfig.getKitName(this.kit.get(p));
		p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "You have been given a " + kitname + " kit.");
		
		PlayerInventory inv = p.getInventory();
		inv.setItem(8, InvUtil.getKitSelector()); // Give the player a kit selector first
		
		int playerkit = this.kit.get(p); // Get what kit the player has.
		
		for (String item : FfaConfig.getKitItems(playerkit)) {
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
	
	public void resetKit(Player p) {
		if (this.isPlayerInRoom(p)) {
			int kitnum = this.kit.get(p); // Get what kit the player has.
			p.getInventory().setArmorContents(null);
			p.getInventory().clear();
			this.giveKit(p, kitnum);
			
		}
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
				if (p != null && PlayerData.isLoaded(p)) {
					PlayerData data = PlayerData.get(p);
					total = total + data.getFfaRanking();
					
				}
			}
		}
		
		int pl = this.getPlayersInRoom();
		
		if (pl == 0) {
			pl = 1;
		}
		
		return total / pl;
		
	}
	
	public void playerKilled(Player p, String msg) {
		PlayerData data = null;
		if (PlayerData.isLoaded(p)) {
			data = PlayerData.get(p);
		} else {
			data = new PlayerData(p);
		}
		
		data.setFfaTotalDeaths(data.getFfaTotalDeaths() + 1); // Increase death count
		
		int died = this.pointboard.get(p.getName());
		int subtract = 2;
		
		if (died - subtract > 0) {
			p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "You have lost " + subtract + " point for dying.");
			
			int newscore = died - subtract;
			p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "You now have " + newscore + (newscore == 1 ? " point" : " points") + ".");
			p.setLevel(died - subtract);
			this.pointboard.put(p.getName(), died - subtract);
			
		} else {
			p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "You have lost " + died + (died == 1 ? " point" : " points") + " for dying.");
			p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "You now have 0 points.");
			p.setLevel(0);
			this.pointboard.put(p.getName(), 0);
			
		}
		
		if (this.getPlayers() != null) {
			for (Player pl : this.getPlayers()) {
				if (pl != null) {
					pl.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + msg);
				}
			}	
		}
		
		FfaSpawnManager.respawnTimer(p);
		
	}
	
	public void playerKilled(Player p, Player killer) { // Player loses 1 point for being executed by another player.
		PlayerData data = null;
		if (PlayerData.isLoaded(killer)) {
			data = PlayerData.get(killer);
		} else {
			data = new PlayerData(killer);
		}
		
		data.setFfaTotalKills(data.getFfaTotalKills() + 1); // Increase kill count
		
		int add = 3;
		int kill = this.pointboard.get(killer.getName());
		killer.setLevel(kill + 3);
		this.pointboard.put(killer.getName(), kill + add);
		killer.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "You have gained " + add + " points by slaying " + p.getName() + ".");
		killer.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "You now have " + (kill + add) + " points.");
		
		this.playerKilled(p, p.getName() + " has been executed by " + killer.getName() + "!");
		
	}
	
	public void playerKilled(Player p, EntityType ent) {
		String entity = ent.toString().charAt(0) + ent.toString().toLowerCase().substring(1);
		this.playerKilled(p, p.getName() + " has been slain by " + entity + ".");
		
	}
	
	public void playerKilled(Player p, DamageCause cause) {
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
		} else if (cause.equals(DamageCause.VOID)) {
			c = "fell into the void";
		} else {
			c = "died";
		}
		
		this.playerKilled(p, p.getName() + " " + c + ".");
	}

	public void playerKilled(Player p) {
		this.playerKilled(p, p.getName() + " died.");
	}

	public void startGame() {
		for (Player p : this.getPlayers()) {
			if (p != null) {
				p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " --- THE GAME HAS STARTED! ---");
				p.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "Map: " + this.getMapNameFancy() + " by " + this.getMapAuthor() + ".");
				p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " --- -------------------- ---");
				
				if (!this.kit.containsKey(p)) { // If the player didn't pick a kit, give them a random one.
					ConfigurationSection cs = FfaConfig.getKits();
					int kits = cs.getKeys(false).size();
					
					Random rand = new Random();
					boolean kitloop = true;
					int r = 0;
					
					while (kitloop) { // Make sure non-Premiums cannot random into Premium kits. 
						r = rand.nextInt(kits);
						
						if (FfaConfig.isKitPremium(r) && !(Permission.isPremium(p))) {
							continue;
						}
						
						kitloop = false;
						
					}
					
					this.kit.put(p, r);
					p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "You forgot to set your kit while in queue. Here's a random one.");
					
				}
				
				InvUtil.clearPlayerInv(p);
				int playerkit = this.kit.get(p); 
				this.giveKit(p, playerkit);
				
				FfaSpawnManager.spawn(p, FfaConfig.getPossibleSpawnLocation(this));
				this.pointboard.put(p.getName(), 0);
			}
		}
		
		this.updateSigns();
		this.setCountdownTimer(Config.getCountdown(RoomType.FFA));
		
	}
	
	public void updateSigns() {
		Location[] locs = FfaConfig.getSignsStored(this.getRoomId());
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
				sign.setLine(0, RoomType.FFA.getColor() + "" + ChatColor.BOLD + "[FFA]");
				sign.setLine(1, (this.isPremium() ? ChatColor.GOLD + "" : "") + this.getRoomId());
				sign.setLine(2, ChatColor.ITALIC + "" + this.getPlayersInRoom() + "/" + Config.getPlayerLimit(RoomType.FFA));
				
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
				
				FfaConfig.clearSignLocations(this.getRoomId());
				
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
							
						FfaConfig.setSignLocation(this.getRoomId(), i, l);
						i++;
							
					}
				}
			}
		}
	}
	
	public void resetRoom(boolean areYouSure) {
		if (areYouSure) {
			this.pointboard = new HashMap<String, Integer>();
			this.kit = new HashMap<Player, Integer>();
			
			this.removeAllPlayerScoreboard();
			
			for (Player p : this.getPlayers()) {
				CustomItem.clearPlayerTimers(p);
				p.setLevel(0);
				
			}
			
			this.setMapName(null);
			this.resetRoomBasics(areYouSure);
			this.updateSigns();
			this.updateScoreboard(); // Update scoreboard
			
		}
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

	public void setPointboard(HashMap<String, Integer> scoreboard) {
		this.pointboard = scoreboard;
	}
	
	public void setPointboard(Player p, int amt) {
		this.pointboard.put(p.getName(), amt);
	}
	
	public HashMap<Player, Integer> getKit() {
		return this.kit;
	}

	public void setKit(HashMap<Player, Integer> kit) {
		this.kit = kit;
	}
	
	public String getMapNameFancy() {
		return FfaConfig.getData().getString("ffa.maps." + this.getMapName() + ".info.map-name");
	}
	
	public String getMapAuthor() {
		return FfaConfig.getData().getString("ffa.maps." + this.getMapName() + ".info.author");
	}
	
}