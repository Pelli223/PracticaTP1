package characters;

import logic.Game;

public class ExplosiveVampire extends Vampire{
	public ExplosiveVampire(int x, int y, Game game) {
		super(x, y, game);
		this.letra = "EV";
	}
	
	//Sobreescribe el método attack para que este cuando su vida sea 0 explote y sea eliminado del arrayList
	private void explosion() {
			for(int i = this.x - 1; i <= this.x + 1; i++) {
				for(int j = this.y - 1; j <= this.y + 1; j++) {
					IAttack other = this.game.getAttackableInPosition(i, j);
					if(other != null) {
						other.receiveExplosion(this.damage);
					}
				}
			}
			this.game.removeDeadObject(this.x, this.y);
			vampiresOnTheBoard--;
		}
	
	public void attack() {
		if(!isAlive()) explosion();
		else super.attack();
	}
	
	//Sobreescribe el método receiveSlayerAttack para que este no sea eliminado del arrayList al morir y así pueda explotar
	public boolean receiveSlayerAttack(int damage) {
		this.life = this.life - damage;
		return true;
	}
	
	//Sobreescribe el método receiveExplosion para que este no sea eliminado del arrayList al morir y así pueda explotar
	public boolean receiveExplosion(int damage) {
		this.life = this.life - damage;
		return true;
	}
}
