package me.taur.arenagames.player;

import java.io.File;
import java.util.HashMap;

import me.taur.arenagames.Arenagames;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PlayerData {
	public static boolean USE_FLATFILE = true;
	public static HashMap<Player, PlayerData> STORE = new HashMap<Player, PlayerData>();
	
	private String name;
	private boolean premium;
	private int ffaGamesPlayed;
	private int ffaEloRank;
	private int ffaRecord;
	private int lflGamesPlayed;
	private int lflEloRank;
	private int lflRecord;
	
	public PlayerData(Player p) {
		name = p.getName();
		createFile(p);
		loadData(p);
		STORE.put(p, this);
		
	}
	
	public static boolean isLoaded(Player p) {
		if (STORE.get(p) != null) {
			return true;
			
		}
		
		return false;
		
	}
	
	public static PlayerData get(Player p) {
		return STORE.get(p);
	}
	
	public void createFile(Player p) { // Also loads the file and adds new unwritten values.
		if (USE_FLATFILE) {
			File dir = new File(Arenagames.plugin.getDataFolder(), "players");
			if (!dir.exists()) {
				dir.mkdir();

			}

			File file = new File(Arenagames.plugin.getDataFolder(), "players/" + p.getName().toLowerCase() + ".yml");
			if (!file.exists()) {
				try {
					file.createNewFile();
					FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
					conf.addDefault("user.name", p.getName());
					conf.addDefault("user.premium", false);

					conf.addDefault("user.ffa.games", 0);
					conf.addDefault("user.ffa.elo", 1000);
					conf.addDefault("user.ffa.record", 0);

					conf.addDefault("user.lfl.games", 0);
					conf.addDefault("user.lfl.elo", 1000);
					conf.addDefault("user.lfl.record", 0);
					conf.options().copyDefaults(true);
					conf.save(file);

				} catch (Exception e) {

				}
				
			}
		}
		
	}
	
	public void loadData(Player p) {
		if (USE_FLATFILE) {
			File dir = new File(Arenagames.plugin.getDataFolder(), "players");
			if (!dir.exists()) {
				createFile(p);

			}

			File file = new File(Arenagames.plugin.getDataFolder(), "players/" + p.getName().toLowerCase() + ".yml");
			if (!file.exists()) {
				createFile(p);

			} else {
				FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
				this.setName(conf.getString("user.name"));
				this.setPremium(conf.getBoolean("user.premium"));

				this.setFfaGamesPlayed(conf.getInt("user.ffa.games"));
				this.setFfaEloRank(conf.getInt("user.ffa.elo"));
				this.setFfaRecord(conf.getInt("user.ffa.record"));

				this.setLflGamesPlayed(conf.getInt("user.lfl.games"));
				this.setLflEloRank(conf.getInt("user.lfl.elo"));
				this.setLflRecord(conf.getInt("user.lfl.record"));

			}
			
		}
		
	}
	
	public void save(Player p) {
		if (USE_FLATFILE) {
			File dir = new File(Arenagames.plugin.getDataFolder(), "players");
			if (!dir.exists()) {
				dir.mkdir();

			}

			File file = new File(Arenagames.plugin.getDataFolder(), "players/" + p.getName().toLowerCase() + ".yml");
			try {
				file.createNewFile();
				FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
				conf.set("user.name", p.getName());
				conf.set("user.premium", this.isPremium());

				conf.set("user.ffa.games", this.getFfaGamesPlayed());
				conf.set("user.ffa.elo", this.getFfaEloRank());
				conf.set("user.ffa.record", this.getFfaRecord());
				
				conf.set("user.lfl.games", this.getLflGamesPlayed());
				conf.set("user.lfl.elo", this.getLflEloRank());
				conf.set("user.lfl.record", this.getLflRecord());
				conf.save(file);

			} catch (Exception e) {

			}
		}
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isPremium() {
		return premium;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	public int getFfaGamesPlayed() {
		return ffaGamesPlayed;
	}

	public void setFfaGamesPlayed(int ffaGamesPlayed) {
		this.ffaGamesPlayed = ffaGamesPlayed;
	}

	public int getFfaEloRank() {
		return ffaEloRank;
	}

	public void setFfaEloRank(int ffaEloRank) {
		this.ffaEloRank = ffaEloRank;
	}

	public int getFfaRecord() {
		return ffaRecord;
	}

	public void setFfaRecord(int ffaRecord) {
		this.ffaRecord = ffaRecord;
	}

	public int getLflGamesPlayed() {
		return lflGamesPlayed;
	}

	public void setLflGamesPlayed(int lflGamesPlayed) {
		this.lflGamesPlayed = lflGamesPlayed;
	}

	public int getLflEloRank() {
		return lflEloRank;
	}

	public void setLflEloRank(int lflEloRank) {
		this.lflEloRank = lflEloRank;
	}

	public int getLflRecord() {
		return lflRecord;
	}

	public void setLflRecord(int lflRecord) {
		this.lflRecord = lflRecord;
	}
}
