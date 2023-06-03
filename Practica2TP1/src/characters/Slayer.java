package characters;
import logic.Game;

public class Slayer extends GameObject{
	private int cost = 50;
	
	public Slayer(int x,int y, Game game) {
		super(x, y, 1, 3, game, "S");
	}
	
	public void attack() {
		int pos = this.x + 1;
		if(isAlive()) {
			while(pos < this.game.getDimX()) {
				IAttack other = this.game.getAttackableInPosition(pos, this.y);
				if(other != null) {
					if(other.receiveSlayerAttack(this.damage)) pos = this.game.getDimX();
					else pos ++;
				}
				else pos ++;
			}
		}
	}

	public void move() {
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
	
	public int getCost() {
		return this.cost;
	}

}