package characters;
import logic.Game;

public class Slayer {
	private int x, y, life, cost, damage;
	private Game game;
	private final char  letra = 'S';
	
	public Slayer(int x,int y, Game game) {
		this.life = 3;
		this.damage = 1;
		this.cost = 50;
		this.x = x;
		this.y = y;
		this.game = game;
	}
	
	//Slayer realiza un ataque siempre que la lógica de game lo permite
	public void attack() {
		if(this.life > 0) {
			this.game.slayerAttack(this.x, this.y, this.damage);
		}
	}
	
	//Slayer recibe el ataque de el vampire de la posición siguiente posición al slayer x+1
	public void receiveAttack(int damage) {
		this.life = this.life - damage;
	}
	
	//Devuelve el char del slayer + su vida en forma de string
	public String slayerString() {
		return this.letra + "[" + this.life + "]";
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public int getLife() {
		return this.life;
	}
	public int getCost(){
		return this.cost;
	}
	public int getDamage() {
		return this.damage;
	}

}
