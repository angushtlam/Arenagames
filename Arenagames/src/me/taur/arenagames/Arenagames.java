package me.taur.arenagames;

import me.taur.arenagames.admin.CashCommand;
import me.taur.arenagames.admin.CurrencyCommand;
import me.taur.arenagames.admin.MemberCommand;
import me.taur.arenagames.admin.PremiumCommand;
import me.taur.arenagames.chat.ChatCommand;
import me.taur.arenagames.chat.ChatListener;
import me.taur.arenagames.chat.KickCommand;
import me.taur.arenagames.chat.MuteCommand;
import me.taur.arenagames.ffa.FfaKitSelectorListener;
import me.taur.arenagames.ffa.FfaPlayerListener;
import me.taur.arenagames.ffa.FfaRespawnListener;
import me.taur.arenagames.ffa.FfaRoomListener;
import me.taur.arenagames.ffa.FfaSignListener;
import me.taur.arenagames.ffa.FfaUtil;
import me.taur.arenagames.fix.ArrowFix;
import me.taur.arenagames.fix.InventoryFix;
import me.taur.arenagames.fix.RespawnFix;
import me.taur.arenagames.fix.StrengthFix;
import me.taur.arenagames.fix.TeleportFix;
import me.taur.arenagames.item.CustomConsumableListener;
import me.taur.arenagames.item.CustomItemListener;
import me.taur.arenagames.item.CustomItemUtil;
import me.taur.arenagames.item.CustomProjectileListener;
import me.taur.arenagames.item.CustomWeaponListener;
import me.taur.arenagames.item.WarpMenu;
import me.taur.arenagames.item.WarpSelectorListener;
import me.taur.arenagames.mastery.MasteryMenu;
import me.taur.arenagames.mastery.MasterySelectorListener;
import me.taur.arenagames.mastery.MasteryTree;
import me.taur.arenagames.perk.PerkMenu;
import me.taur.arenagames.perk.PerkHatListener;
import me.taur.arenagames.perk.PerkSelectorListener;
import me.taur.arenagames.player.PlayerCommand;
import me.taur.arenagames.player.PlayerDataListener;
import me.taur.arenagames.player.PlayerActionListener;
import me.taur.arenagames.player.PlayerProfileListener;
import me.taur.arenagames.room.RoomCommand;
import me.taur.arenagames.room.SignCreateListener;
import me.taur.arenagames.room.SignDestroyListener;
import me.taur.arenagames.shop.ShopMenu;
import me.taur.arenagames.shop.ShopSignListener;
import me.taur.arenagames.spawn.SpawnWorldListener;
import me.taur.arenagames.tdm.TdmKitSelectorListener;
import me.taur.arenagames.tdm.TdmPlayerListener;
import me.taur.arenagames.tdm.TdmRespawnListener;
import me.taur.arenagames.tdm.TdmRoomListener;
import me.taur.arenagames.tdm.TdmSignListener;
import me.taur.arenagames.tdm.TdmUtil;
import me.taur.arenagames.util.RoomType;

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
		regCmd("mute", new MuteCommand());
		regCmd("kick", new KickCommand());
		regCmd("member", new MemberCommand());
		regCmd("premium", new PremiumCommand());
		regCmd("curr", new CurrencyCommand());
		regCmd("cash", new CashCommand());
		
		regEvent(new PlayerActionListener());
		regEvent(new SignCreateListener());
		regEvent(new SignDestroyListener());
		
		regEvent(new PlayerDataListener());
		regEvent(new PlayerProfileListener());
		
		regEvent(new ChatListener());
		
		regEvent(new ShopSignListener());
		ShopMenu.enable();
		
		regEvent(new PerkSelectorListener());
		regEvent(new PerkHatListener());
		PerkMenu.enable();
		
		regEvent(new CustomItemListener());
		regEvent(new CustomWeaponListener());
		regEvent(new CustomConsumableListener());
		regEvent(new CustomProjectileListener());
		CustomItemUtil.enable();
		
		regEvent(new WarpSelectorListener());
		WarpMenu.enable();
		
		regEvent(new ArrowFix());
		regEvent(new InventoryFix());
		regEvent(new RespawnFix());
		regEvent(new StrengthFix());
		regEvent(new TeleportFix());
		
		regEvent(new SpawnWorldListener());
		
		regEvent(new MasterySelectorListener());
		MasteryTree.loadValues();
		MasteryMenu.enable();
		
		loadGamemodes();
		Scheduler.start();
		
	}

	@Override
	public void onDisable() {
		
	}
	
	private static void loadGamemodes() {
		if (Config.isEnabled(RoomType.FFA)) {
			FfaUtil.enable();
			regFfa();

		}

		if (Config.isEnabled(RoomType.TDM)) {
			TdmUtil.enable();
			regTdm();
			
		}
	}

	private static void regFfa() {		
		regEvent(new FfaRespawnListener());
		regEvent(new FfaKitSelectorListener());
		regEvent(new FfaPlayerListener());
		regEvent(new FfaSignListener());
		regEvent(new FfaRoomListener());
		
	}
	
	private static void regTdm() {
		regEvent(new TdmRespawnListener());
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