package me.taur.arenagames.util;


public class GameMathUtil {
	public static int getConstantByElo(int elo) {
		if (elo < 501) {
			return 42;
		} else if (501 < elo && elo < 801) {
			return 35;
		} else if (801 < elo && elo < 1501) {
			return 30;
		} else if (1501 < elo && elo < 2601) {
			return 25;
		} else if (2601 < elo && elo < 3301) {
			return 18;
		} else {
			return 9;
		}
		
	}
	
	public static int addElo(int oldelo, int avg) {
		int constant = getConstantByElo(oldelo);
		double percent = ((double) avg) / ((double) oldelo);
		double modifier = constant * percent;
		
		int newelo = oldelo + ((int) modifier);
		
		if (newelo > 4000) {
			newelo = 4000;
		}
		
		return newelo;
		
	}
	
	public static int removeElo(int oldelo, int avg) {
		int constant = getConstantByElo(oldelo);
		double percent = ((double) avg) / ((double) oldelo);
		double modifier = constant * percent;
		
		int newelo = oldelo - ((int) modifier);
		
		if (newelo < 0) {
			newelo = 0;
		}
		
		return newelo;
		
	}
	
	public static double kdrCalculator(int kills, int deaths) {
		if (deaths == 0) {
			return (double) kills;
		}
		
		double raw = ((double) kills) / ((double) deaths);
		double roundOff = Math.round(raw * 100.0) / 100.0;
		return roundOff;
		
	}
}
