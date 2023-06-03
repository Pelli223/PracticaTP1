package characters;
import logic.Game;

public class Vampire extends GameObject {
	private int vampireCycle;
	private static int remainingVampires;
	protected static int vampiresOnTheBoard;
	protected static boolean draculaAlive;
	
	public Vampire (int x, int y, Game game){
		super(x, y, 1, 5, game, "V");
		this.vampireCycle = 0;
		remainingVampires--;
		vampiresOnTheBoard++;
	}
	
	public static void init(int numberVampires) {
		draculaAlive = false;
		remainingVampires = numberVampires;
		vampiresOnTheBoard = 0;
	}
	
	//Llama al receiveVampireAttack del objeto delante suyo
	public void attack() {
		if(isAlive()) {
			IAttack other = this.game.getAttackableInPosition(this.x - 1, this.y);
			if(other != null) {
				other.receiveVampireAttack(this.damage);
			}
		}
	}
	
	//Se mueve cados ciclos internos del objeto
	public void move(){
		if(this.game.isCellEmpty(this.x - 1, this.y)){
			this.vampireCycle ++;
			if(this.vampireCycle % 2 == 0) {
				this.x--;
			}
		}
		else if((this.vampireCycle % 2 == 0) && (!this.game.isCellEmpty(this.x - 1, this.y))) {
			this.vampireCycle--;
		}
	}
	
	//Recive el ataque de un slayer y se comprueba si ha muerto
	public boolean receiveSlayerAttack(int damage) {
		this.life = this.life - damage;
		if(!isAlive()) {
			vampiresOnTheBoard--;
			this.game.removeDeadObject(this.x, this.y);
		}
		return true;
	}
	
	//Recibe la explosion de un vampiro y se comprueba si ha muerto
	public boolean receiveExplosion(int damage) {
		this.life = this.life - damage;
		if(!isAlive()) {
			vampiresOnTheBoard--;
			this.game.removeDeadObject(this.x, this.y);
		}
		return true;
	}
	
	//Recibe garlicpush y se comprueba si ha muerto
	public boolean receiveGarlicPush() {
		boolean ok;
		if(this.game.isCellEmpty(this.x + 1, this.y)){
		this.x += 1;
			if(this.x == this.game.getDimX()){
				vampiresOnTheBoard--;
				this.game.removeDeadObject(this.x, this.y);
				ok = true;
			}
			else {
				ok = false;
			}
		}
		else {
			ok = false;
		}
		return ok;
	}
	
	//Recibe light y muere
	public boolean receiveLightFlash() {
		vampiresOnTheBoard--;
		this.game.removeDeadObject(this.x, this.y);
		return true;
	}
	
	public static int getRemainingVampires() {
		return remainingVampires;
	}
	
	public static int getVampiresOnBoard() {
		return vampiresOnTheBoard;
	}
	
	public static boolean getDraculaAlive() {
		return draculaAlive;
	}
}
