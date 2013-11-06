package me.taur.arenagames.lfl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import me.taur.arenagames.Config;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.Items;
import me.taur.arenagames.util.Players;
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

public class LflRoom extends Room {
	private String mapname;
	private HashMap<String, Integer> scoreboard;
	private HashMap<Player, Integer> kit;
	
	public LflRoom(String roomId) {
		this.scoreboard = new HashMap<String, Integer>();
		this.kit = new HashMap<Player, Integer>();
		this.setRoomId(roomId);
		this.setRoomType(RoomType.LFL);
	}

	public String getMapName() {
		return this.mapname;
	}

	public void setMapName(String mapname) {
		this.mapname = mapname;
	}

	public HashMap<String, Integer> getScoreboard() {
		return this.scoreboard;
	}

	public void setScoreboard(HashMap<String, Integer> scoreboard) {
		this.scoreboard = scoreboard;
	}
	
	public void setPlayerScore(Player p, int amt) {
		this.scoreboard.put(p.getName(), amt);
	}
	
	public HashMap<Player, Integer> getKit() {
		return this.kit;
	}

	public void setKit(HashMap<Player, Integer> kit) {
		this.kit = kit;
	}
	
	public void giveKit(Player p, int kitnum) {
		String kitname = LflConfig.getKitName(this.kit.get(p));
		p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "You have been given a " + kitname + " kit.");
		
		PlayerInventory inv = p.getInventory();
		inv.setItem(8, Items.getKitSelector()); // Give the player a kit selector first
		
		int playerkit = this.kit.get(p); // Get what kit the player has.
		
		for (String item : LflConfig.getKitItems(playerkit)) {
			ItemStack i = Items.convertToItemStack(item); 

			// Automatically set the player's armor if the item is an armor, and they don't have the armor on.
			if (inv.getHelmet() == null && i.getType().name().contains("HELMET")) {
				inv.setHelmet(i);
				continue;
			}
			
			if (inv.getHelmet() == null && (i.getType().name().equals(Material.PUMPKIN) || i.getType().name().equals(Material.JACK_O_LANTERN))) {
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
		
		Items.updatePlayerInv(p);
		
	}
	
	public void resetKit(Player p) {
		int kitnum = this.kit.get(p); // Get what kit the player has.
		p.getInventory().setArmorContents(null);
		p.getInventory().clear();
		this.giveKit(p, kitnum);
		
		Items.updatePlayerInv(p);
		
	}

	public String getMapNameFancy() {
		return LflConfig.get().getString("lfl.maps." + this.getMapName() + ".info.map-name");
		
	}
	
	public String getMapAuthor() {
		return LflConfig.get().getString("lfl.maps." + this.getMapName() + ".info.author");
		
	}
	
	public String getWinningPlayer() {
		String player = "";
		boolean firstLoop = true;
		int score = 0;
		
		for (String name : this.scoreboard.keySet()) {
			int amt = this.scoreboard.get(name);
			
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
	
	public void respawnPlayer(Player p) {
		this.resetKit(p);
		p.teleport(LflConfig.getPossibleSpawnLocation(this));
		p.setHealth(p.getMaxHealth());
		
	}
	
	public void playerDied(Player p, int subtract, String msg) {
		int died = this.scoreboard.get(p.getName());
		
		if (this.getPlayers() != null) {
			for (Player pl : this.getPlayers()) {
				if (pl != null) {
					pl.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + msg);
				}
			}	
		}
		
		if (died - subtract > 0) {
			p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "You have lost " + subtract + " point for dying.");
			
			int newscore = died - subtract;
			p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "You now have " + newscore + (newscore == 1 ? " point" : " points") + ".");
			this.scoreboard.put(p.getName(), died - subtract);
		} else {
			p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "You have lost " + died + (died == 1 ? " point" : " points") + " for dying.");
			p.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "You now have 0 points.");
			this.scoreboard.put(p.getName(), 0);
		}
		
		this.respawnPlayer(p);
	}
	
	public void playerDied(Player p, Player killer) { // Player loses 1 point for being executed by another player.
		int kill = this.scoreboard.get(killer.getName());
		this.scoreboard.put(killer.getName(), kill + 3);
		
		this.playerDied(p, 1, p.getName() + " has been executed by " + killer.getName() + "!");
		killer.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "You have gained 3 points by slaying " + p.getName() + ".");
		killer.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "You now have " + (kill + 3) + " points.");

	}
	
	public void playerDied(Player p, EntityType ent) {
		String entity = ent.toString().charAt(0) + ent.toString().toLowerCase().substring(1);
		this.playerDied(p, 2, p.getName() + " has been slain by " + entity + ".");
		
	}
	
	public void playerDied(Player p, DamageCause cause) {
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
			c = "died by parried damage";
			
		} else if (cause.equals(DamageCause.WITHER)) {
			c = "withered away";
			
		} else {
			c = "died";
			
		}
		
		this.playerDied(p, 2, p.getName() + " " + c + ".");
		
	}

	public void playerDied(Player p) {
		this.playerDied(p, 2, p.getName() + " died.");

	}
	
	public void startGame() {
		for (Player p : this.getPlayers()) {
			if (p != null) {
				p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " --- THE GAME HAS STARTED! ---");
				p.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "Map: " + this.getMapNameFancy() + " by " + this.getMapAuthor() + ".");
				p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " --- -------------------- ---");
				
				if (!this.kit.containsKey(p)) { // If the player didn't pick a kit, give them a random one.
					ConfigurationSection cs = LflConfig.getKits();
					int kits = cs.getKeys(false).size();
					
					Random rand = new Random();
					int r = rand.nextInt(kits);
					
					this.kit.put(p, r);
					p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "You forgot to set your kit while in queue. Here's a random one.");
					
				}
				
				Players.respawnEffects(p);
				
				int playerkit = this.kit.get(p); 
				this.giveKit(p, playerkit);
				
				p.teleport(LflConfig.getPossibleSpawnLocation(this));
				this.scoreboard.put(p.getName(), 0);
			}
		}
		
		this.updateSigns();
		this.setCountdownTimer(Config.getCountdown(RoomType.LFL));
		
	}
	
	public void updateSigns() {
		Location[] locs = LflConfig.getSignsStored(this.getRoomId());
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
				sign.setLine(0, ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[Lifeline]");
				sign.setLine(1, (this.isPremium() ? ChatColor.WHITE + "" : "") + this.getRoomId());
				sign.setLine(2, ChatColor.ITALIC + "" + this.getPlayersInRoom() + "/" + Config.getPlayerLimit(RoomType.LFL));
				
				String setl3 = ChatColor.GREEN + "Queue Open";
				if (this.isGameInProgress()) {
					setl3 = ChatColor.YELLOW + "In Progress";
					
				} else if (this.isGameInWaiting()) {
					setl3 = ChatColor.AQUA + "Starting Soon";
					
				}
				
				sign.setLine(3, setl3);
				sign.update();
			}
			
			if (fix) {
				Set<Location> signlocs = new HashSet<Location>();
				
				for (Location l : locs) { // Copy the list first
					signlocs.add(l);
					
				}
				
				LflConfig.clearSignLocations(this.getRoomId());
				
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
							
						LflConfig.setSignLocation(this.getRoomId(), i, l);
						i++;
							
					}
				}
			}
		}
	}
	
	public void resetRoom(boolean areYouSure) {
		if (areYouSure) {
			this.scoreboard = null;
			this.scoreboard = new HashMap<String, Integer>();
			
			this.kit = null;
			this.kit = new HashMap<Player, Integer>();
			
			this.setMapName(null);
			this.resetRoomBasics(areYouSure);
			this.updateSigns();
			
		}
	}
	
}