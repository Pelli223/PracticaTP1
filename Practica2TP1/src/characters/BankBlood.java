package characters;

import logic.Game;

public class BankBlood extends GameObject{
	private int z;
	public BankBlood(int x, int y, int z, Game game) {
		super(x, y, 0, 1, game, "B");
		this.z = z;
	}
	
	//Este objeto no ataca en vez de eso devuelve al player el 10% de su atributo z
	public void attack() {
		this.game.bankBloodCoins(this.z);
	}
	
	//Este objeto no se mueve
	public void move() {
	}
	
	//Sobreescribe el metodo toString para mostrar z en lugar de su vida
	public String toString() {
		return this.letra + "[" + this.z + "]";
	}
	
	public boolean receiveVampireAttack(int damage) {
		this.life = this.life - damage;
		if(!isAlive()) {
			this.game.removeDeadObject(this.x, this.y);
		}
		return true;
	}
	
	public boolean receiveDraculaAttack() {
		this.life = 0;
		this.game.removeDeadObject(this.x, this.y);
		return true;
	}
	
	public boolean receiveExplosion(int damage) {
		this.life = this.life - damage;
		if(!isAlive()) {
			this.game.removeDeadObject(this.x, this.y);
		}
		return true;
	}
	
	public int getZ() {
		return this.z;
	}
}
