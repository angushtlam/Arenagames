package me.taur.arenagames.player;

import me.taur.arenagames.perk.EffectPerkUtil;
import me.taur.arenagames.perk.HatPerkUtil;

import org.bukkit.entity.Player;

public class PlayerPerk {
	public static boolean isPerkOwned(Player p, EffectPerkUtil fx) {
		PlayerData data = null;
		if (!PlayerData.isLoaded(p)) {
			data = new PlayerData(p);
		} else {
			data = PlayerData.get(p);
		}

		if (fx.equals(EffectPerkUtil.STORM)) {
			return (data.getPerkFxStorm() > 0 ? true : false); // If the value is higher than 1;
		}

		if (fx.equals(EffectPerkUtil.BLOOM)) {
			return (data.getPerkFxBloom() > 0 ? true : false); // If the value is higher than 1;
		}

		if (fx.equals(EffectPerkUtil.MUSIC)) {
			return (data.getPerkFxMusic() > 0 ? true : false); // If the value is higher than 1;
		}

		if (fx.equals(EffectPerkUtil.CLOUD)) {
			return (data.getPerkFxCloud() > 0 ? true : false); // If the value is higher than 1;
		}

		if (fx.equals(EffectPerkUtil.SOIL)) {
			return (data.getPerkFxSoil() > 0 ? true : false); // If the value is higher than 1;
		}

		if (fx.equals(EffectPerkUtil.ENDER)) {
			return (data.getPerkFxEnder() > 0 ? true : false); // If the value is higher than 1;
		}

		if (fx.equals(EffectPerkUtil.FIRESCARF)) {
			return (data.getPerkFxFireScarf() > 0 ? true : false); // If the value is higher than 1;
		}

		if (fx.equals(EffectPerkUtil.FIRETRAIL)) {
			return (data.getPerkFxFireTrail() > 0 ? true : false); // If the value is higher than 1;
		}

		if (fx.equals(EffectPerkUtil.HEARTSCARF)) {
			return (data.getPerkFxHeartScarf() > 0 ? true : false); // If the value is higher than 1;
		}

		if (fx.equals(EffectPerkUtil.HEARTTRAIL)) {
			return (data.getPerkFxHeartTrail() > 0 ? true : false); // If the value is higher than 1;
		}

		if (fx.equals(EffectPerkUtil.KNOWLEDGE)) {
			return (data.getPerkFxKnowledge() > 0 ? true : false); // If the value is higher than 1;
		}

		if (fx.equals(EffectPerkUtil.SUPERCHICKEN)) {
			return (data.getPerkFxSuperchicken() > 0 ? true : false); // If the value is higher than 1;
		}

		if (fx.equals(EffectPerkUtil.BLOOD)) {
			return (data.getPerkFxBlood() > 0 ? true : false); // If the value is higher than 1;
		}

		return false;

	}
	
	public static boolean isPerkOwned(Player p, HatPerkUtil hat) {
		PlayerData data = null;
		if (!PlayerData.isLoaded(p)) {
			data = new PlayerData(p);
		} else {
			data = PlayerData.get(p);
		}

		switch (hat) {
		case BEDROCK: return (data.getPerkHatBedrock() > 0 ? true : false);
		case BOOKSHELF: return (data.getPerkHatBookshelf() > 0 ? true : false);
		case BRICK: return (data.getPerkHatBrick() > 0 ? true : false);
		case CHEST: return (data.getPerkHatChest() > 0 ? true : false);
		case COAL: return (data.getPerkHatCoal() > 0 ? true : false);
		case DIAMOND: return (data.getPerkHatDiamond() > 0 ? true : false);
		case EMERALD: return (data.getPerkHatEmerald() > 0 ? true : false);
		case ENDER_CHEST: return (data.getPerkHatEnderChest() > 0 ? true : false);
		case GLASS: return (data.getPerkHatGlass() > 0 ? true : false);
		case GOLD: return (data.getPerkHatGold() > 0 ? true : false);
		case ICE: return (data.getPerkHatIce() > 0 ? true : false);
		case IRON: return (data.getPerkHatIron() > 0 ? true : false);
		case LAPIS: return (data.getPerkHatLapis() > 0 ? true : false);
		case MELON: return (data.getPerkHatMelon() > 0 ? true : false);
		case NOTE: return (data.getPerkHatNote() > 0 ? true : false);
		case PUMPKIN: return (data.getPerkHatPumpkin() > 0 ? true : false);
		case REDSTONE: return (data.getPerkHatRedstone() > 0 ? true : false);
		case SOULSAND: return (data.getPerkHatSoulsand() > 0 ? true : false);
		case SPONGE: return (data.getPerkHatSponge() > 0 ? true : false);
		case TNT: return (data.getPerkHatTNT() > 0 ? true : false);
		default: return false;
		
		}
	}
	
	public static void grantPerk(Player p, EffectPerkUtil fx) {
		PlayerData data = null;
		if (!PlayerData.isLoaded(p)) {
			data = new PlayerData(p);
		} else {
			data = PlayerData.get(p);
		}

		if (fx.equals(EffectPerkUtil.STORM)) {
			data.setPerkFxStorm(1);
			return;
			
		}

		if (fx.equals(EffectPerkUtil.BLOOM)) {
			data.setPerkFxBloom(1);
			return;
			
		}

		if (fx.equals(EffectPerkUtil.MUSIC)) {
			data.setPerkFxMusic(1);
			return;
			
		}

		if (fx.equals(EffectPerkUtil.CLOUD)) {
			data.setPerkFxCloud(1);
			return;
			
		}

		if (fx.equals(EffectPerkUtil.SOIL)) {
			data.setPerkFxSoil(1);
			return;
			
		}

		if (fx.equals(EffectPerkUtil.ENDER)) {
			data.setPerkFxEnder(1);
			return;
			
		}

		if (fx.equals(EffectPerkUtil.FIRESCARF)) {
			data.setPerkFxFireScarf(1);
			return;
			
		}

		if (fx.equals(EffectPerkUtil.FIRETRAIL)) {
			data.setPerkFxFireTrail(1);
			return;
			
		}

		if (fx.equals(EffectPerkUtil.HEARTSCARF)) {
			data.setPerkFxHeartScarf(1);
			return;
			
		}

		if (fx.equals(EffectPerkUtil.HEARTTRAIL)) {
			data.setPerkFxHeartTrail(1);
			return;
			
		}

		if (fx.equals(EffectPerkUtil.KNOWLEDGE)) {
			data.setPerkFxKnowledge(1);
			return;
			
		}

		if (fx.equals(EffectPerkUtil.SUPERCHICKEN)) {
			data.setPerkFxSuperchicken(1);
			return;
			
		}

		if (fx.equals(EffectPerkUtil.BLOOD)) {
			data.setPerkFxBlood(1);
			return;
			
		}
		
	}
	
	public static void grantPerk(Player p, HatPerkUtil hat) {
		PlayerData data = null;
		if (!PlayerData.isLoaded(p)) {
			data = new PlayerData(p);
		} else {
			data = PlayerData.get(p);
		}

		switch (hat) {
		case BEDROCK: data.setPerkHatBedrock(1);
		case BOOKSHELF: data.setPerkHatBookshelf(1);
		case BRICK: data.setPerkHatBrick(1);
		case CHEST: data.setPerkHatChest(1);
		case COAL: data.setPerkHatCoal(1);
		case DIAMOND: data.setPerkHatDiamond(1);
		case EMERALD: data.setPerkHatEmerald(1);
		case ENDER_CHEST: data.setPerkHatEnderChest(1);
		case GLASS: data.setPerkHatGlass(1);
		case GOLD: data.setPerkHatGold(1);
		case ICE: data.setPerkHatIce(1);
		case IRON: data.setPerkHatIron(1);
		case LAPIS: data.setPerkHatLapis(1);
		case MELON: data.setPerkHatMelon(1);
		case NOTE: data.setPerkHatNote(1);
		case PUMPKIN: data.setPerkHatPumpkin(1);
		case REDSTONE: data.setPerkHatRedstone(1);
		case SOULSAND: data.setPerkHatSoulsand(1);
		case SPONGE: data.setPerkHatSponge(1);
		case TNT: data.setPerkHatTNT(1);
		default: return;
		
		}
		
	}
}
