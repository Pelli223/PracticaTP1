package logic;
import characters.GameObject;
import characters.GameObjectList;
import characters.IAttack;

public class GameObjectBoard {
	private GameObjectList objectList;
	
	public GameObjectBoard() {
		objectList = new GameObjectList();
	}
	
	public void newGameObject(GameObject gameObject) {
		this.objectList.newGameObject(gameObject);
	}
	
	public void move() {
		this.objectList.move();
	}
	
	public boolean isCellEmpty(int x, int y) {
		return this.objectList.isCellEmpty(x, y);
	}
	
	public String positionToString(int x, int y) {
		return this.objectList.positionToString(x, y);
	}
	
	public IAttack getAttackableInPosition(int x, int y) {
		return this.objectList.getAttackableInPosition(x, y);
	}
	
	public void attacks(boolean garlic, boolean light) {
		this.objectList.attacks(garlic, light);
	}
	
	public void eliminate(int x, int y) {
		this.objectList.eliminate(x, y);
	}
	
	public boolean slayerWin() {
		return this.objectList.slayerWin();
	}
	
	public boolean vampireWin(int numRows) {
		return this.objectList.vampireWin(numRows);
	}
	
}
