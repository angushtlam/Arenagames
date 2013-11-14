package me.taur.arenagames.item;

public enum PotionColor {
	PINK(8193, 16385),
	BLUE(8194, 16386),
	ORANGE(8195, 16387),
	GREEN(8196, 16388),
	DARK_PURPLE(8262, 16390),
	DARK_GRAY(8264, 16392),
	RED(8265, 16393),
	GRAY(8266, 16394),
	DARK_BLUE(8269, 16397),
	PURPLE(8270, 16398);
	
	private final int dmg;
	private final int splash;
	
	PotionColor(int dmg, int splash) {
		this.dmg = dmg;
		this.splash = splash;
		
	}
	
	public short getDmg() {
		return (short) this.dmg;
	}
	
	public short getSplash() {
		return (short) this.splash;
	}
	
}
