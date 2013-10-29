package me.taur.arenagames.ffa;

import java.util.HashMap;
import java.util.Random;

import me.taur.arenagames.Config;
import me.taur.arenagames.util.Items;
import me.taur.arenagames.util.Room;
import me.taur.arenagames.util.RoomType;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FfaRoom extends Room {
	private String mapname;
	private HashMap<String, Integer> scoreboard;
	private HashMap<Player, Integer> kit;
	
	public FfaRoom(String roomId) {
		this.scoreboard = new HashMap<String, Integer>();
		this.kit = new HashMap<Player, Integer>();
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
	
	public HashMap<Player, Integer> getKit() {
		return kit;
	}

	public void setKit(HashMap<Player, Integer> kit) {
		this.kit = kit;
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
			
			p.setFireTicks(0);
			p.getActivePotionEffects().clear();
			p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 800));
			p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 60, 1));
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 100));
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 60, 100));
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 2));
			
			if (kit.containsKey(p.getName())) {
				String kitname = FfaConfig.getKitName(kit.get(p));
				p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "You have been given a " + kitname + " kit.");
				
			} else { // If the player didn't pick a kit, give them a random one.
				ConfigurationSection cs = FfaConfig.getKits();
				int kits = cs.getKeys(false).size();
				
				Random rand = new Random();
				int r = rand.nextInt(kits);
				
				kit.put(p, r);
				String kitname = FfaConfig.getKitName(r);
				p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "You forgot to set your kit while in queue. ");
				p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "You have been randomly given a " + kitname + " kit.");
				
			}
			
			for (String item : FfaConfig.getKitItems(this.kit.get(p.getName()))) {
				PlayerInventory inv = p.getInventory();
				
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
			
			p.teleport(FfaConfig.getPossibleSpawnLocation(this));
			scoreboard.put(p.getName(), 0);
			
		}
		
		this.setCountdownTimer(Config.getCountdown(RoomType.FFA));
		
	}
	
	public void resetRoom(boolean areYouSure) {
		if (areYouSure) {
			scoreboard = null;
			scoreboard = new HashMap<String, Integer>();
			
			kit = null;
			kit = new HashMap<Player, Integer>();
			
			this.setMapName(null);
			this.resetRoomBasics(areYouSure);
			
		}
		
	}
	
}
