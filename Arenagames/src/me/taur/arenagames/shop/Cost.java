package me.taur.arenagames.shop;

public class Cost {
	private int curr, cash;
	
	public Cost(int curr, int cash) {
		this.curr = curr;
		this.cash = cash;
		
	}

	public int getCurr() {
		return curr;
	}

	public void setCurr(int curr) {
		this.curr = curr;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

}
