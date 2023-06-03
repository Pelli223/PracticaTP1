package characters;

public class SlayerList {
	private Slayer []slayerList;
	private int cont;
	
	public SlayerList(int dim) {
		this.cont = 0;
		this.slayerList = new Slayer[dim];
	}
	
	//Introduce el nuevo slayer en la posición de cont y aumenta en uno este
	public void newSlayer(Slayer slayer) {
		this.slayerList[this.cont] = slayer;
		this.cont++;
	}
	
	//Invoca el método toString de Slayer para cada slayer del array
	public String slayerToString(int x, int y) {
		String slayerString = slayerList[getPosSlayer(x, y)].slayerString();
		return slayerString;
	}
	
	//Recorre el array comparando las coordenadas pasadas como parámetro y devuelve true si coinciden algún slayer del array
	public boolean isSlayerHere(int x, int y) {
		boolean ok = false;
		int pos = 0;
		while(pos < cont) {
			if((slayerList[pos].getX() == x) && (slayerList[pos].getY() == y)) {
				ok = true;
				pos = this.cont;
			}
			else {
				ok = false;
				pos++;
			}
		}
		return ok;
	}
	
	//Busca el slayer que recive el ataque y se comprueba si ha muerto en dicho caso se le quita del array
	public void receiveAttack(int x, int y, int damage) {
		this.slayerList[getPosSlayer(x, y)].receiveAttack(damage);
		if(slayerList[getPosSlayer(x, y)].getLife() == 0) {
			eliminateSlayer(getPosSlayer(x, y));
		}
	}
	
	//Recorre el array de slayers llamando al ataque de estos
	public void slayersAttacks() {
		for (int i = 0; i < this.cont; i++) {
			this.slayerList[i].attack();
		}
	}
	
	//Elimina un slayer del array
	private void eliminateSlayer(int ind) {
		for (int i = ind ; i < this.cont ; i++) {
			slayerList[i] = slayerList[i + 1];
		}
		this.cont--;
	}
	
	//Devuelve el índice del slayer del array con dichas coordenadas
	private int getPosSlayer(int x, int y) {
		int pos = 0;
		int ind = 0;
		while (pos < cont) {
			if((slayerList[pos].getX() == x) && (slayerList[pos].getY() == y)) {
				ind = pos;
				pos = cont;
			}
			else {
				pos++;
			}
		}
		
		return ind;
	}


	public int getSlayerCost(int x, int y) {
		return slayerList[getPosSlayer(x, y)].getCost();
	}
}
