package me.taur.arenagames.util;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public enum ParticleUtil {

	EXPAND_SMOKE("largesmoke", 0.1F, 20),
	CRAZY_SMOKE("largesmoke", 1.0F, 20),
	RISING_SMOKE("largesmoke", 0.0F, 20),
	RED_SMOKE("reddust", 0F, 40),
	RAINBOW_SMOKE("reddust", 1F, 100),
	FIRE("flame", 0.05F, 50),
	FIRE_STILL("flame", 0.0F, 50),
	HEART("heart", 0F, 4),
	COLLIDING_RUNES("enchantmenttable", 1F, 100),
	FALLING_RUNES("enchantmenttable", 0.0F, 100),
	LAVA_SPARK("lava", 0F, 4),
	SPLASH("splash", 1F, 40),
	PORTAL("portal", 2.0F, 100),
	PORTAL_FUNNEL("portal", 0.1F, 100),
	PORTAL_STILL("portal", 0.0F, 100),
	CLOUD("cloud", 0.0F, 10),
	EXPLODE("explode", 0.1F, 10),
	CRITICAL("crit", 0.1F, 100),
	MAGIC_CRITIAL("magicCrit", 0.1F, 100),
	MAGIC_CRITIAL_SMALL("magicCrit", 0.01F, 20),
	ANGRY_VILLAGER("angryVillager", 0F, 3),
	SPARKLE("happyVillager", 0F, 8),
	WATER_DRIP("dripWater", 0F, 20),
	LAVA_DRIP("dripLava", 0F, 80),
	WITCH_MAGIC("witchMagic", 1F, 20),
	WITCH_MAGIC_SMALL("witchMagic", 0.1F, 5),
	SNOWBALL("snowballpoof", 1F, 20),
	SNOW_SHOVEL("snowshovel", 0.02F, 30),
	SLIME_SPLAT("slime", 1F, 30),
	BUBBLE("bubble", 0F, 50),
	SPELL_AMBIENT("mobSpellAmbient", 1F, 100),
	VOID("townaura", 1F, 100),
	FIREWORK_SPARK("fireworksSpark", 0F, 50),
	MOB_SPELL("mobSpell", 2F, 50),
	SPELL("spell", 1F, 50),
	INSTANT_SPELL("instantSpell", 1F, 50),
	NOTE("note", 1F, 20),
	GREEN_NOTE("note", 0F, 20);

	private String name;
	private float defaultSpeed;
	private int defaultAmount;

	ParticleUtil(String name, float defaultSpeed, int defaultAmount) {
		this.name = name;
		this.defaultSpeed = defaultSpeed;
		this.defaultAmount = defaultAmount;

	}

	public void sendToLocation(Location loc) {
		sendToLocation(loc, new Random().nextFloat(), new Random().nextFloat(), defaultAmount);
	}

	public void sendToLocation(Location loc, float offset, float offsetVert, int amt) {
		try {
			Object packet = Class.forName("net.minecraft.server." + ReflectionUtil.getVersionString() + ".PacketPlayOutWorldParticles").getConstructors()[0].newInstance();
			ReflectionUtil.setValue(packet, "a", name); // Particle ID
			ReflectionUtil.setValue(packet, "b", (float) loc.getX()); // X Coord
			ReflectionUtil.setValue(packet, "c", (float) loc.getY()); // Y Coord
			ReflectionUtil.setValue(packet, "d", (float) loc.getZ()); // Z Coord
			ReflectionUtil.setValue(packet, "e", offset); // X Offset
			ReflectionUtil.setValue(packet, "f", offsetVert); // Y Offset
			ReflectionUtil.setValue(packet, "g", offset); // Z Offset
			ReflectionUtil.setValue(packet, "h", defaultSpeed); // Speed (Data)
			ReflectionUtil.setValue(packet, "i", amt); // Amount of Particles Spawned
			ReflectionUtil.sendPacketToLocation(loc, packet);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendToLocation(Player p, float offset, float offsetVert, int amt) {
		try {
			Object packet = Class.forName("net.minecraft.server." + ReflectionUtil.getVersionString() + ".PacketPlayOutWorldParticles").getConstructors()[0].newInstance();
			ReflectionUtil.setValue(packet, "a", name); // Particle ID
			ReflectionUtil.setValue(packet, "b", (float) p.getLocation().getX()); // X Coord
			ReflectionUtil.setValue(packet, "c", (float) p.getLocation().getY()); // Y Coord
			ReflectionUtil.setValue(packet, "d", (float) p.getLocation().getZ()); // Z Coord
			ReflectionUtil.setValue(packet, "e", offset); // X Offset
			ReflectionUtil.setValue(packet, "f", offsetVert); // Y Offset
			ReflectionUtil.setValue(packet, "g", offset); // Z Offset
			ReflectionUtil.setValue(packet, "h", defaultSpeed); // Speed (Data)
			ReflectionUtil.setValue(packet, "i", amt); // Amount of Particles Spawned
			
			Object nmsPlayer = ReflectionUtil.getMethod(p.getClass(), "getHandle").invoke(p);
			Object con = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
			ReflectionUtil.getMethod(con.getClass(), "sendPacket").invoke(con, packet);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public float getDefaultSpeed() {
		return defaultSpeed;
	}

	public int getDefaultAmount() {
		return defaultAmount;
	}

}
