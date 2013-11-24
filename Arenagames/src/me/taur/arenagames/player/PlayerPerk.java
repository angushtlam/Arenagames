package me.taur.arenagames.player;

import me.taur.arenagames.util.EffectPerk;

import org.bukkit.entity.Player;

public class PlayerPerk {
	public static boolean isPerkOwned(Player p, EffectPerk fx) {
		PlayerData data = null;
		if (!PlayerData.isLoaded(p)) {
			data = new PlayerData(p);
		} else {
			data = PlayerData.get(p);
		}

		if (fx.equals(EffectPerk.STORM)) {
			return (data.getPerkFxStorm() > 0 ? true : false); // If the value is higher than 1;
		}

		if (fx.equals(EffectPerk.BLOOM)) {
			return (data.getPerkFxBloom() > 0 ? true : false); // If the value is higher than 1;
		}

		if (fx.equals(EffectPerk.MUSIC)) {
			return (data.getPerkFxMusic() > 0 ? true : false); // If the value is higher than 1;
		}

		if (fx.equals(EffectPerk.CLOUD)) {
			return (data.getPerkFxCloud() > 0 ? true : false); // If the value is higher than 1;
		}

		if (fx.equals(EffectPerk.SOIL)) {
			return (data.getPerkFxSoil() > 0 ? true : false); // If the value is higher than 1;
		}

		if (fx.equals(EffectPerk.ENDER)) {
			return (data.getPerkFxEnder() > 0 ? true : false); // If the value is higher than 1;
		}

		if (fx.equals(EffectPerk.FIRESCARF)) {
			return (data.getPerkFxFireScarf() > 0 ? true : false); // If the value is higher than 1;
		}

		if (fx.equals(EffectPerk.FIRETRAIL)) {
			return (data.getPerkFxFireTrail() > 0 ? true : false); // If the value is higher than 1;
		}

		if (fx.equals(EffectPerk.HEARTSCARF)) {
			return (data.getPerkFxHeartScarf() > 0 ? true : false); // If the value is higher than 1;
		}

		if (fx.equals(EffectPerk.HEARTTRAIL)) {
			return (data.getPerkFxHeartTrail() > 0 ? true : false); // If the value is higher than 1;
		}

		if (fx.equals(EffectPerk.KNOWLEDGE)) {
			return (data.getPerkFxKnowledge() > 0 ? true : false); // If the value is higher than 1;
		}

		if (fx.equals(EffectPerk.SUPERCHICKEN)) {
			return (data.getPerkFxSuperchicken() > 0 ? true : false); // If the value is higher than 1;
		}

		if (fx.equals(EffectPerk.BLOOD)) {
			return (data.getPerkFxBlood() > 0 ? true : false); // If the value is higher than 1;
		}

		return false;

	}
	
	public static void grantPerk(Player p, EffectPerk fx) {
		PlayerData data = null;
		if (!PlayerData.isLoaded(p)) {
			data = new PlayerData(p);
		} else {
			data = PlayerData.get(p);
		}

		if (fx.equals(EffectPerk.STORM)) {
			data.setPerkFxStorm(1);
			return;
			
		}

		if (fx.equals(EffectPerk.BLOOM)) {
			data.setPerkFxBloom(1);
			return;
			
		}

		if (fx.equals(EffectPerk.MUSIC)) {
			data.setPerkFxMusic(1);
			return;
			
		}

		if (fx.equals(EffectPerk.CLOUD)) {
			data.setPerkFxCloud(1);
			return;
			
		}

		if (fx.equals(EffectPerk.SOIL)) {
			data.setPerkFxSoil(1);
			return;
			
		}

		if (fx.equals(EffectPerk.ENDER)) {
			data.setPerkFxEnder(1);
			return;
			
		}

		if (fx.equals(EffectPerk.FIRESCARF)) {
			data.setPerkFxFireScarf(1);
			return;
			
		}

		if (fx.equals(EffectPerk.FIRETRAIL)) {
			data.setPerkFxFireTrail(1);
			return;
			
		}

		if (fx.equals(EffectPerk.HEARTSCARF)) {
			data.setPerkFxHeartScarf(1);
			return;
			
		}

		if (fx.equals(EffectPerk.HEARTTRAIL)) {
			data.setPerkFxHeartTrail(1);
			return;
			
		}

		if (fx.equals(EffectPerk.KNOWLEDGE)) {
			data.setPerkFxKnowledge(1);
			return;
			
		}

		if (fx.equals(EffectPerk.SUPERCHICKEN)) {
			data.setPerkFxSuperchicken(1);
			return;
			
		}

		if (fx.equals(EffectPerk.BLOOD)) {
			data.setPerkFxBlood(1);
			return;
			
		}
		
	}
}
