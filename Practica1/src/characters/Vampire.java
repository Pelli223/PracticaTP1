package characters;
import logic.Game;

public class Vampire {
	private int x, y, life, damage;
	private int vampireCycle;
	private Game game;
	private final char letra = 'V';
	private static int remainingVampires;
	private static int vampiresOnTheBoard;
	
	public Vampire (int x, int y, Game game){
		this.x = x;
		this.y = y;
		this.life = 5;
		this.damage = 1;
		this.vampireCycle = 0;
		this.game = game;
		remainingVampires--;
		vampiresOnTheBoard++;
	}
	public static void init(int numberVampires) {
		remainingVampires = numberVampires;
		vampiresOnTheBoard = 0;
	}
	
	//Vampire realiza un ataque siempre que la lógica de game lo permite
	public void attack() {
		if(this.life > 0) {
			this.game.vampireAttack(this.x, this.y, this.damage);
		}
	}
	
	//Vampire recibe el ataque de el vampire de la posición siguiente posición al slayer x+1
	public void receiveAttack(int damage) {
		this.life = this.life - damage;
	}
	
	//Actualiza el número del ciclo interno de cada vampire siempre que el vampire se pueda mover
	public void updateCycle() {
		if(this.game.isCellEmpty(this.x - 1, this.y)){
			this.vampireCycle ++;
		}
		else if ((!this.game.isCellEmpty(this.x - 1, this.y)) && (this.vampireCycle % 2 == 0)){
			this.vampireCycle--;
		}
	}
	
	//Vampire se mueve siempre que la lógica de game se lo permita
	public boolean vampireMove(){
		boolean canMove;
		if((this.vampireCycle % 2 == 0)&&(this.game.isCellEmpty(this.x - 1, this.y))) {
			this.x--;
			canMove = true;
		}
		else {
			canMove = false;
		}
		return canMove;
	}
	
	//Devuelve el char del vampire + su vida en forma de string
	public String vampireString() {
		return this.letra + "[" + this.life + "]";
	}
	public static void decreaseVampireOnBoard() {
		vampiresOnTheBoard--;
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
	public static int getRemainingVampires() {
		return remainingVampires;
	}
	public static int getVampiresOnBoard() {
		return vampiresOnTheBoard;
	}
}
