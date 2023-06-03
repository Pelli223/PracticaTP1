package characters;

public class GameObjectBoard {
	private VampireList vampires;
	private SlayerList slayers;
	
	public GameObjectBoard(int dimSlayers, int dimVampires) {
		this.vampires = new VampireList(dimVampires);
		this.slayers = new SlayerList(dimSlayers);
	}
	
	public boolean isCellEmpty(int x, int y) {
		boolean empty = true;
		if (vampires.isVampireHere(x, y)) {
			empty = false;
		}
		else if (slayers.isSlayerHere(x, y)) {
			empty = false;
		}
		else {
			empty = true;
		}
		
		return empty;
	}
	private boolean isVampire(int x, int y){
		boolean isVampire = false;
		if(this.vampires.isVampireHere(x, y)) {
			isVampire = true;
		}
		else {
			isVampire = false;
		}
		return isVampire;
	}
	private boolean isSlayer(int x, int y) {
		boolean isSlayer = false;
		if (this.slayers.isSlayerHere(x, y)) {
			isSlayer = true;
		}
		else {
			isSlayer = false;
		}
		return isSlayer;
	}
	public boolean checkSlayerAttack(int x, int y) {
		return isVampire(x, y);
	}
	public boolean checkVampireAttack(int x, int y) {
		return isSlayer(x, y);
	}
	public void slayerAttack(int x, int y, int damage) {
		this.vampires.receiveAttack(x, y, damage);
	}
	public void vampireAttack(int x, int y, int damage) {
		this.slayers.receiveAttack(x, y, damage);
	}
	public void attacks() {
		this.slayers.slayersAttacks();
		this.vampires.vampiresAttacks();
	}
	public String positionToString(int x, int y) {
		String cell;
		if (isSlayer(x, y)) {
			cell = this.slayers.slayerToString(x, y);
		}
		else if (isVampire(x, y)) {
			cell = this.vampires.vampireToString(x, y);
		}
		else {
			cell = " ";
		}
		return cell;
	}
	public void updateCycles() {
		this.vampires.updateCycles();
	}
	
	public boolean vampireWin(int numRows) {
		boolean end = false;
		int posRow = 0;
		if(Vampire.getVampiresOnBoard() >= 1) {
			while(posRow < numRows && (!end)) {
				if(this.vampires.isVampireHere(-1, posRow)) {
					end = true;
				}
				else {
					posRow++;
				}
			}
		}
		else {
			end = false;
		}
		return end;
	}
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
	public void addNewSlayer(Slayer slayer) {
		slayers.newSlayer(slayer);
	}
	public void addNewVampire(Vampire vampire) {
		vampires.newVampire(vampire);
	}
	public void moveVampires() {
		this.vampires.move();
	}
	
	public int slayerCost(int x, int y) {
		return slayers.getSlayerCost(x, y);
	}
}
	
