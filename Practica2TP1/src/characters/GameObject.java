package characters;
import logic.Game;

public abstract class GameObject implements IAttack {
	protected int x;
	protected int y;
	protected int life;
	protected int damage;
	protected String letra;
	protected Game game;
	
	public GameObject(int x, int y, int damage, int life, Game game, String letra) {
		this.x = x;
		this.y = y;
		this.damage = damage;
		this.life = life;
		this.game = game;
		this.letra = letra;
	}
	
	public abstract void move();
	public abstract void attack();
	
	//Pasa el objeto a String para que pueda ser dibujado en el tablero
	public String toString() {
		return this.letra + "[" + this.life + "]";
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	//Comprueba que el gameObject se encuentre vivo
	public boolean isAlive() {
		boolean alive = true;
		if(this.life <= 0) {
			alive = false;
		}
		return alive;
	}
	
	public int getDamage() {
		return this.damage;
	}
	
}
