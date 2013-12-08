package me.taur.arenagames.player;

public enum PlayerRank {
	ADMIN(10),
	MOD(6),
	PERMA_PREMIUM(4),
	MEMBER(1),
	USER(0),
	BANNED(-1);
	
	private int rank;
	
	PlayerRank(int rank) {
		this.rank = rank;
	}
	
	public int getRankNumber() {
		return rank;
	}
	
}
