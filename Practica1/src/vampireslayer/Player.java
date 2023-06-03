package vampireslayer;

public class Player {
	private int coins;
	
	public Player() {
		this.coins = 50;
	}
	
	public void buySlayer(int cost) {
		this.coins = this.coins - cost;
	}
	public void earnCoins() {
		this.coins = this.coins + 10;
	}
	public int getCoins() {
		return this.coins;
	}
	
}
