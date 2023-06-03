package characters;


public class VampireList {
	private Vampire []vampireList;
	private int cont;
	
	public VampireList(int dim) {
		this.cont = 0;
		this.vampireList  = new Vampire[dim];
	}
	
	//Invoca el método toString de Vampire para cada slayer del array
	public String vampireToString(int x, int y) {
		String vampireString = vampireList[getPosVampire(x, y)].vampireString();
		return vampireString;
	}
	
	//Introduce el nuevo vampire en la posición de cont y aumenta en uno este
	public void newVampire(Vampire vampire) {
		vampireList[this.cont++] = vampire;
	}
	
	//Actualiza el ciclo inerno de cada vampire
	public void updateCycles() {
		for(int i = 0; i < this.cont; i++) {
			this.vampireList[i].updateCycle();
		}
	}
	
	//Busca el vampire que recive el ataque y se comprueba si ha muerto en dicho caso se le quita del array
	public void receiveAttack(int x, int y, int damage) {
		vampireList[getPosVampire(x, y)].receiveAttack(damage);
		if(vampireList[getPosVampire(x, y)].getLife() == 0) {
			eliminateVampire(getPosVampire(x, y));
		}
	}
	
	//Recorre el array de vampire llamando al ataque de estos
	public void vampiresAttacks() {
		for(int i = 0; i < this.cont; i++) {
			this.vampireList[i].attack();
		}
	}
	public void move() {
		for(int i = 0; i < this.cont; i++) {
			vampireList[i].vampireMove();
		}
	}
	
	//Recorre el array comparando las coordenadas pasadas como parámetro y devuelve true si coinciden algún slayer del array
	public boolean isVampireHere(int x, int y) {
		boolean ok = false;
		int pos = 0;
		while(pos < cont) {
			if((this.vampireList[pos].getX() == x) && (this.vampireList[pos].getY() == y)) {
				ok = true;
			}
			pos++;
		}
		return ok;
	}
	
	//Devuelve el índice del vampire del array con dichas coordenadas
	private int getPosVampire(int x, int y) {
		int pos = 0;
		boolean ok = false;
		while ((pos < cont) && (!ok)){
			if((vampireList[pos].getX() == x) && (vampireList[pos].getY() == y)) {
				ok = true;
			}
			else {
				pos++;
			}
		}
		
		return pos;
	}
	
	//Elimina un slayer del array
	private void eliminateVampire(int ind) {
		Vampire.decreaseVampireOnBoard();
		for(int i = ind  ; i < this.cont ; i++) {
			vampireList[i] = vampireList[i + 1];
		}
		this.cont--;
	}


}
