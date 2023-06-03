package characters;

import java.util.ArrayList;

public class GameObjectList {
	private ArrayList<GameObject> gameObjects;
	
	public GameObjectList() {
		this.gameObjects = new ArrayList<GameObject>();
	}

	public void newGameObject(GameObject gameObject) {
		gameObjects.add(gameObject);
	}

	//Llama al método move de todos los objetos del arrayList
	public void move() {
		for(int i = 0; i < gameObjects.size(); i++) {
			gameObjects.get(i).move();
		}	
	}

	//Comprueba que la celda en la posicion x, y este vacia
	public boolean isCellEmpty(int x, int y) {
		boolean empty = true;
		
		if(getPos(x, y) != -1) {
			empty = false;
		}
		return empty;
	}
	
	//Encuentra el objeto en el array que se encuentra en la posicion x, y
	private int getPos(int x, int y) {
		int pos = 0;
		boolean ok = false;
		while((pos < gameObjects.size()) && (!ok)) {
			if((gameObjects.get(pos).getX() == x) && (gameObjects.get(pos).getY() == y)) {
				ok = true;
			}
			else {
				pos++;
			}
		}
		if(!ok) pos = -1;
		return pos;
	}

	//Decuelve el toString del objeto en la posicion x, y
	public String positionToString(int x, int y) {
		if(!isCellEmpty(x, y)) {
			return gameObjects.get(getPos(x, y)).toString();
		}
		else {
			return " ";
		}
	}

	//Devuelve un cast de la interfaz IAttack del objeto en la posicion x, y
	public IAttack getAttackableInPosition(int x, int y) {
		if(!isCellEmpty(x, y)) {
			IAttack attackable = (IAttack)gameObjects.get(getPos(x, y));
			return attackable;
		}
		else {
			return null;
		}
		
	}

	//Comprueba que clase de ataque se realiza de los tres posibles y llama a los objetos del arrayList para que lo reciban
	public void attacks(boolean garlic, boolean light) {
		for(int i = this.gameObjects.size() - 1; i >= 0; i--) {
			if((!garlic) && (!light)) {
				gameObjects.get(i).attack();
			}
			else if(garlic) {
				gameObjects.get(i).receiveGarlicPush();
			}
			else if(light) {
				gameObjects.get(i).receiveLightFlash();
			}
		}
	}
	
	//Elimina el objeto en la posicion x, y del arrayList
	public void eliminate(int x, int y) {
		gameObjects.remove(getPos(x, y));
	}
	
	//Comprueba si los slayers han ganado
	public boolean slayerWin() {
		boolean win = false;
		if((Vampire.getRemainingVampires() == 0) && (Vampire.getVampiresOnBoard() == 0)) {
			win = true;
		}
		else {
			win = false;
		}
		return win;
	}
	
	//Comprueba si los vampiros han ganado
	public boolean vampireWin(int numRows) {
		boolean end = false;
		int posRow = 0;
		if(Vampire.getVampiresOnBoard() >= 1) {
			while(posRow < numRows && (!end)) {
				if(!isCellEmpty(-1, posRow)) {
					end = true;
				}
				else {
					posRow++;
				}
			}
		}
		return end;
	}
}
