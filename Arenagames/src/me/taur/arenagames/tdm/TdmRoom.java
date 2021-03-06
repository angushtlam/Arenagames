package me.taur.arenagames.tdm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import me.taur.arenagames.Config;
import me.taur.arenagames.item.CustomItem;
import me.taur.arenagames.item.InvUtil;
import me.taur.arenagames.player.Permission;
import me.taur.arenagames.player.PlayerData;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.RoomType;

import org.bukkit.Bukkit;
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
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TdmRoom extends Room {
	private String mapname;
	private HashMap<String, Integer> teamtrackboard;
	private HashMap<String, Integer> pointboard;
	private HashMap<Player, Integer> kit;
	
	public TdmRoom(String roomId) {
		this.teamtrackboard = new HashMap<String, Integer>();
		this.setPointboard(new HashMap<String, Integer>());
		this.kit = new HashMap<Player, Integer>();
		
		this.setRoomId(roomId);
		this.setRoomType(RoomType.TDM);
		this.createScoreboard();
		this.createTeamboard();
		
	}
	
	public void updateScoreboard() {
		if (Room.SCOREBOARDS.get(this.getRoomId()) != null) {
			this.setScoreboardTitle(this.scoreboardTimer());
			
			if (this.isGameInProgress()) {
				int redscore = this.getRedScore();
				int bluescore = this.getBlueScore();
				this.setScoreboardSideField(ChatColor.RED + "Redd Score", redscore);
				this.setScoreboardSideField(ChatColor.BLUE + "Blue Score", bluescore);
				
			} else {
				this.setScoreboardSideField(ChatColor.RED + "Redd Score", 0);
				this.setScoreboardSideField(ChatColor.BLUE + "Blue Score", 0);
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
	
	public void createTeamboard() {
		Scoreboard board = Room.SCOREBOARDS.get(this.getRoomId());
		Team red = board.registerNewTeam(this.getRoomId() + "-red");
		red.setDisplayName("Redd");
		red.setPrefix(ChatColor.RED + "");
		red.setSuffix(ChatColor.RESET + "");
		red.setCanSeeFriendlyInvisibles(true);
		red.setAllowFriendlyFire(false);
		
		Team blue = board.registerNewTeam(this.getRoomId() + "-blue");
		blue.setDisplayName("Blue");
		blue.setPrefix(ChatColor.BLUE + "");
		blue.setSuffix(ChatColor.RESET + "");
		blue.setCanSeeFriendlyInvisibles(true);
		blue.setAllowFriendlyFire(false);
		
	}
	
	public void wipeTeamboard() {
		for (String name : this.getPointboard().keySet()) {
			if (name.startsWith("&")) { // Filter out team scores.
				continue;
			}
			
			for (int i = 0; i < this.getPlayers().length; i++) {
				if (this.getPlayers()[i].getName().equalsIgnoreCase(name)) {
					removePlayerFromTeam(this.getPlayers()[i]);
					continue;
					
				}
			}
		}
		
	}
	
	public void playerTeamboardCheck() {
		if (this.isGameInProgress()) {
			return;
		}
		
		Scoreboard board = Room.SCOREBOARDS.get(this.getRoomId());
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p != null && board.getPlayers().contains(p)) {
				board.getPlayerTeam(p).removePlayer(p);
			}
		}
		
	}
	
	public void addPlayerToRed(Player p) {
		Scoreboard board = Room.SCOREBOARDS.get(this.getRoomId());
		if (board.getTeam(this.getRoomId() + "-red") != null) {
			Team t = board.getTeam(this.getRoomId() + "-red");
			t.addPlayer(p);
			
		}
	}
	
	public void addPlayerToBlue(Player p) {
		Scoreboard board = Room.SCOREBOARDS.get(this.getRoomId());
		if (board.getTeam(this.getRoomId() + "-blue") != null) {
			Team t = board.getTeam(this.getRoomId() + "-blue");
			t.addPlayer(p);
			
		}
	}
	
	public void removePlayerFromTeam(Player p) {
		Scoreboard board = Room.SCOREBOARDS.get(this.getRoomId());
		if (board.getPlayerTeam(p) != null) {
			board.getPlayerTeam(p).removePlayer(p);
		}
	}
	
	public void giveKit(Player p, int kitnum) {
		String kitname = TdmConfig.getKitName(this.kit.get(p));
		p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "You have been given a " + kitname + " kit.");
		
		PlayerInventory inv = p.getInventory();
		inv.setItem(8, InvUtil.getKitSelector()); // Give the player a kit selector first
		
		int playerkit = this.kit.get(p); // Get what kit the player has.
		
		for (String item : TdmConfig.getKitItems(playerkit)) {
			ItemStack i = InvUtil.convertToItemStack(item); 

			// Automatically set the player's armor if the item is an armor, and they don't have the armor on.
			if (inv.getHelmet() == null && i.getType().name().contains("HELMET")) {
				inv.setHelmet(i);
				continue;
				
			}
			
			if (inv.getHelmet() == null && i.getType().name().equals(Material.GLASS)) {
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
	
	public TdmTeams getWinningTeam() {
		int red = this.getTeamtrackboard().get("&red").intValue();
		int blue = this.getTeamtrackboard().get("&blue").intValue();
		
		if (red > blue) {
			return TdmTeams.RED;
		} else if (blue > red) {
			return TdmTeams.BLUE;
		} else {
			return TdmTeams.TIED;
		}
		
	}
	
	public String getMVP() {
		String player = "";
		boolean firstLoop = true;
		int score = 0;
		
		for (String name : this.getPointboard().keySet()) {
			int amt = this.getPointboard().get(name);
			
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
	
	public int getRedScore() {
		if (this.getTeamtrackboard().get("&red") == null) {
			return 0;
		}
		
		return this.getTeamtrackboard().get("&red").intValue();
	}
	
	public int getBlueScore() {
		if (this.getTeamtrackboard().get("&red") == null) {
			return 0;
		}
		
		return this.getTeamtrackboard().get("&blue").intValue();
	}
	
	public int getPlayersOnBlue() {
		int i = 0;
		
		for (String s : this.getTeamtrackboard().keySet()) {
			if (this.getTeamtrackboard().get(s).intValue() == TdmTeams.BLUE.getId()) {
				i++;
			}
		}
		
		return i;
		
	}
	
	public int getPlayersOnRed() {
		int i = 0;
		
		for (String s : this.getTeamtrackboard().keySet()) {
			if (this.getTeamtrackboard().get(s).intValue() == TdmTeams.RED.getId()) {
				i++;
			}
		}
		
		return i;
		
	}
	
	public int getAvgElo() {
		int total = 0;
		
		if (this.getPlayers() != null) {
			for (Player p : this.getPlayers()) {
				if (p != null && PlayerData.isLoaded(p)) {
					PlayerData data = PlayerData.get(p);
					total = total + data.getTdmRanking();
					
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
		
		data.setTdmTotalDeaths(data.getTdmTotalDeaths() + 1); // Increase death count
		
		if (this.getPlayers() != null) {
			for (Player pl : this.getPlayers()) {
				if (pl != null) {
					pl.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + msg);
				}
			}	
		}
		
		TdmSpawnManager.respawnTimer(p);
		
	}
	
	public void playerKilled(Player p, Player killer) { // Player loses 1 point for being executed by another player.
		PlayerData data = null;
		if (PlayerData.isLoaded(killer)) {
			data = PlayerData.get(killer);
		} else {
			data = new PlayerData(killer);
		}
		
		data.setTdmTotalKills(data.getTdmTotalKills() + 1); // Increase kill count
		
		int pteam = this.getTeamtrackboard().get(p.getName());
		if (pteam == TdmTeams.BLUE.getId()) { // Add points to the team who killed the player
			this.teamtrackboard.put("&red", this.teamtrackboard.get("&red") + 1);
		} else if (pteam == TdmTeams.RED.getId()) {
			this.teamtrackboard.put("&blue", this.teamtrackboard.get("&blue") + 1);
		}
		
		this.pointboard.put(killer.getName(), this.pointboard.get(killer.getName()).intValue() + 1);
		
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
					ConfigurationSection cs = TdmConfig.getKits();
					int kits = cs.getKeys(false).size();
					
					Random rand = new Random();
					boolean kitloop = true;
					int r = 0;
					
					while (kitloop) { // Make sure non-Premiums cannot random into Premium kits. 
						r = rand.nextInt(kits);
						
						if (TdmConfig.isKitPremium(r) && !(Permission.isPremium(p))) {
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
				
			}
		}
		
		for (int i = 0; i < this.getPlayersInRoom(); i++) {
			Player p = this.getPlayers()[i];
			
			TdmTeams team = (i % 2 == 0 ? TdmTeams.RED : TdmTeams.BLUE);
			this.teamtrackboard.put(p.getName(), team.getId());
			this.pointboard.put(p.getName(), 0);
			
			if (team == TdmTeams.RED) {
				p.sendMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + "You have been placed on the Redd team.");
				TdmSpawnManager.spawn(p, TdmConfig.getPossibleRedSpawnLocation(this));
				this.addPlayerToRed(p);
				
			} else if (team == TdmTeams.BLUE) {
				p.sendMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + "You have been placed on the Blue team.");
				TdmSpawnManager.spawn(p, TdmConfig.getPossibleBlueSpawnLocation(this));
				this.addPlayerToBlue(p);
				
			}
			
		}
		
		this.teamtrackboard.put("&red", 0);
		this.teamtrackboard.put("&blue", 0);
		
		this.updateSigns();
		this.setCountdownTimer(Config.getCountdown(RoomType.TDM));
		
	}
	
	public void updateSigns() {
		Location[] locs = TdmConfig.getSignsStored(this.getRoomId());
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
				sign.setLine(0, RoomType.TDM.getColor() + "" + ChatColor.BOLD + "[TDM]");
				sign.setLine(1, (this.isPremium() ? ChatColor.GOLD + "" : "") + this.getRoomId());
				sign.setLine(2, ChatColor.ITALIC + "" + this.getPlayersInRoom() + "/" + Config.getPlayerLimit(RoomType.TDM));
				
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
				
				TdmConfig.clearSignLocations(this.getRoomId());
				
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
							
						TdmConfig.setSignLocation(this.getRoomId(), i, l);
						i++;
							
					}
				}
			}
		}
	}
	
	public void resetRoom(boolean areYouSure) {
		if (areYouSure) {
			this.teamtrackboard = new HashMap<String, Integer>();
			this.kit = new HashMap<Player, Integer>();
			
			this.removeAllPlayerScoreboard();
			this.wipeTeamboard();
			
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

	public String getMapNameFancy() {
		return TdmConfig.getData().getString("tdm.maps." + this.getMapName() + ".info.map-name");
	}
	
	public String getMapAuthor() {
		return TdmConfig.getData().getString("tdm.maps." + this.getMapName() + ".info.author");
	}
	
	public String getMapName() {
		return this.mapname;
	}

	public void setMapName(String mapname) {
		this.mapname = mapname;
	}

	public HashMap<String, Integer> getTeamtrackboard() {
		return this.teamtrackboard;
	}

	public void setTeamtrackboard(HashMap<String, Integer> teamtrackboard) {
		this.teamtrackboard = teamtrackboard;
	}
	
	public void setPlayerTeamtrackboard(Player p, int amt) {
		this.teamtrackboard.put(p.getName(), amt);
	}
	
	public HashMap<String, Integer> getPointboard() {
		return pointboard;
	}

	public void setPointboard(HashMap<String, Integer> pointboard) {
		this.pointboard = pointboard;
	}

	public HashMap<Player, Integer> getKit() {
		return this.kit;
	}

	public void setKit(HashMap<Player, Integer> kit) {
		this.kit = kit;
	}

}