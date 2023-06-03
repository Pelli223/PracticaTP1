package characters;

import logic.Game;

public class Dracula extends Vampire{
	

	public Dracula(int x, int y, Game game) {
		super(x, y, game);
		this.letra = "D";
		draculaAlive = true;
	}
	
	public void attack() {
		if(isAlive()) {
			IAttack other = this.game.getAttackableInPosition(this.x - 1, this.y);
			if(other != null) {
				other.receiveDraculaAttack();
			}
		}
	}

	//Sobreescribe el método receiveSlayer attack para en caso de morir poner draculaAlive a false
	public boolean receiveSlayerAttack(int damage) {
		super.receiveSlayerAttack(damage);
		if(!isAlive()) {
			draculaAlive = false;
		}
		return true;
	}
	
	//Sobreescribe el método receiveExplosion para en caso de morir poner draculaAlive a false
	public boolean receiveExplosion(int damage) {
		super.receiveExplosion(damage);
		if(!isAlive()) {
			draculaAlive = false;
		}
		return true;
	}
	
	//Sobreescribe le método receiveGarlicPush para que este no pueda ser hechado del tablero ni muera cuando esto pase
	public boolean receiveGarlicPush() {
		if((this.x < this.game.getDimX() - 1) && (this.game.isCellEmpty(this.x + 1, this.y))){
			this.x += 1;
		}
		return false;
	}
	
	//Sobreescribe el metodo receiveLightFlash para que este no le afecte
	public boolean receiveLightFlash() {
		return false;
	}
}
