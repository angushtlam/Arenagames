package me.taur.arenagames.player;

import me.taur.arenagames.perk.EffectPerkUtil;

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
}
