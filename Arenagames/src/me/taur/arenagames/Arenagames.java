package me.taur.arenagames;

import me.taur.arenagames.admin.CashCommand;
import me.taur.arenagames.admin.CurrencyCommand;
import me.taur.arenagames.admin.PremiumCommand;
import me.taur.arenagames.chat.ChatCommand;
import me.taur.arenagames.chat.ChatListener;
import me.taur.arenagames.crk.CrkDeathListener;
import me.taur.arenagames.crk.CrkKitSelectorListener;
import me.taur.arenagames.crk.CrkPlayerListener;
import me.taur.arenagames.crk.CrkRoomListener;
import me.taur.arenagames.crk.CrkSignListener;
import me.taur.arenagames.crk.CrkUtil;
import me.taur.arenagames.ffa.FfaDeathListener;
import me.taur.arenagames.ffa.FfaKitSelectorListener;
import me.taur.arenagames.ffa.FfaPlayerListener;
import me.taur.arenagames.ffa.FfaRoomListener;
import me.taur.arenagames.ffa.FfaSignListener;
import me.taur.arenagames.ffa.FfaUtil;
import me.taur.arenagames.fix.ArrowFix;
import me.taur.arenagames.fix.TeleportFix;
import me.taur.arenagames.item.CustomConsumableListener;
import me.taur.arenagames.item.CustomItemListener;
import me.taur.arenagames.item.CustomItemUtil;
import me.taur.arenagames.item.CustomProjectileListener;
import me.taur.arenagames.item.CustomWeaponListener;
import me.taur.arenagames.perk.Perk;
import me.taur.arenagames.perk.PerkSelectorListener;
import me.taur.arenagames.player.PlayerCommand;
import me.taur.arenagames.player.PlayerDataListener;
import me.taur.arenagames.player.PlayerLoginListener;
import me.taur.arenagames.player.PlayerProfileListener;
import me.taur.arenagames.room.RoomCommand;
import me.taur.arenagames.room.SignCreateListener;
import me.taur.arenagames.room.SignDestroyListener;
import me.taur.arenagames.shop.Shop;
import me.taur.arenagames.shop.ShopSignListener;
import me.taur.arenagames.tdm.TdmDeathListener;
import me.taur.arenagames.tdm.TdmKitSelectorListener;
import me.taur.arenagames.tdm.TdmPlayerListener;
import me.taur.arenagames.tdm.TdmRoomListener;
import me.taur.arenagames.tdm.TdmSignListener;
import me.taur.arenagames.tdm.TdmUtil;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.edawg878.identifier.IdentifierAPI;

public class Arenagames extends JavaPlugin {
	public static Arenagames plugin;
	public static IdentifierAPI identifier;

	@Override
	public void onEnable() {
		plugin = this;
		
		hookIdentifierAPI();
		
		Config.startCheck();
		
		regCmd("queue", new RoomCommand());
		regCmd("player", new PlayerCommand());
		regCmd("chat", new ChatCommand());
		regCmd("premium", new PremiumCommand());
		regCmd("curr", new CurrencyCommand());
		regCmd("cash", new CashCommand());
		
		regEvent(new PlayerLoginListener());
		regEvent(new SignCreateListener());
		regEvent(new SignDestroyListener());
		
		regEvent(new PlayerDataListener());
		regEvent(new PlayerProfileListener());
		
		regEvent(new ChatListener());
		
		regEvent(new ShopSignListener());
		Shop.enable();
		
		regEvent(new PerkSelectorListener());
		Perk.enable();
		
		regEvent(new CustomItemListener());
		regEvent(new CustomWeaponListener());
		regEvent(new CustomConsumableListener());
		regEvent(new CustomProjectileListener());
		CustomItemUtil.enable();
		
		regEvent(new ArrowFix());
		regEvent(new TeleportFix());
		
		loadGamemodes();
		Scheduler.start();
		
	}

	@Override
	public void onDisable() {
		
	}
	
	private static void loadGamemodes() {
		String[] gm = Config.gamemode;
		
		for (int i = 0; i < gm.length; i++) {
			if (gm[i].contains("ffa")) {
				FfaUtil.enable();
				regFfa();
				continue;
				
			}
			
			if (gm[i].contains("crk")) {
				CrkUtil.enable();
				regCrk();
				continue;
				
			}
			
			if (gm[i].contains("tdm")) {
				TdmUtil.enable();
				regTdm();
				continue;
				
			}
			
		}
	}

	private static void regFfa() {		
		regEvent(new FfaDeathListener());
		regEvent(new FfaKitSelectorListener());
		regEvent(new FfaPlayerListener());
		regEvent(new FfaSignListener());
		regEvent(new FfaRoomListener());
		
	}
	
	private static void regCrk() {
		regEvent(new CrkDeathListener());
		regEvent(new CrkKitSelectorListener());
		regEvent(new CrkPlayerListener());
		regEvent(new CrkSignListener());
		regEvent(new CrkRoomListener());
		
	}

	private static void regTdm() {
		regEvent(new TdmDeathListener());
		regEvent(new TdmKitSelectorListener());
		regEvent(new TdmPlayerListener());
		regEvent(new TdmSignListener());
		regEvent(new TdmRoomListener());
		
	}
	
	private static void hookIdentifierAPI() {
		if (Bukkit.getPluginManager().getPlugin("IdentifierAPI") instanceof IdentifierAPI) {
			identifier = (IdentifierAPI) Bukkit.getPluginManager().getPlugin("IdentifierAPI");
		}
	}
	
	public static void regEvent(Listener file) {
		Bukkit.getServer().getPluginManager().registerEvents(file, Arenagames.plugin);
	}

	public static void regCmd(String cmd, CommandExecutor exe) {
		Bukkit.getServer().getPluginCommand(cmd).setExecutor(exe);
	}
	
}