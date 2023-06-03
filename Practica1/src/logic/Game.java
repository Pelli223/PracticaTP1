package logic;
import vampireslayer.Player;
import view.GamePrinter;
import characters.GameObjectBoard;
import characters.Slayer;
import characters.Vampire;
import control.Controller;

import java.util.Random;

public class Game {
	private GameObjectBoard board;
	private GamePrinter printer;
	private int numberCycles;
	private long seed;
	private Level level;
	private Player player;
	private Random random;
	
	public Game(long seed,  Level level) {
		this.seed = seed;
		this.level = level;
		this.init();
	}
	
	//Inicializa el Game será usado en reset 
	public  void init() {
		this.numberCycles = 0;
		this.random = new Random(this.seed);
		this.printer = new GamePrinter(this, this.level.getDim_X(), this.level.getDim_Y());
		this.player = new Player();
		this.board = new GameObjectBoard((level.getDim_X()*level.getDim_Y()), level.getNumberOfVampires());
		Vampire.init(this.level.getNumberOfVampires());
	}

	//Recibe el tablero pintado en forma de string y añade la información del juego
	public String toString() {
		String text = String.format (
				"Number of Cycles: " + this.numberCycles +
				"%n" + 
				"Coins: " + this.player.getCoins() +
				"%n"+ 
				"Remaining vampires: " + Vampire.getRemainingVampires() +
				"%n"+ 
				"Vampires on the board: " + Vampire.getVampiresOnBoard() +
				"%n"+ 
				this.printer.toString());
		return text;
	}
	
	//Pasa el string de la posición x y del tablero
	public String positionToString(int x, int y) {
		String stringPos = this.board.positionToString(x, y);
		return stringPos;
	}
	
	//Comprueba si la celda x y está vacía
	public boolean isCellEmpty(int x, int y) {
		return this.board.isCellEmpty(x, y);
	}
	
	//Comprueba si se puede añadir un slayer en x y y en caso afirmativo lo añade
	public boolean addSlayer(int x, int y) {
		boolean ok;
		if((x < getDimX() - 1) && (y < getDimY()) && (x >= 0) && (y >= 0)) {
			if(this.player.getCoins() >= ((new Slayer(x, y, this).getCost()))) {
				if(this.board.isCellEmpty(x, y)) {
					this.board.addNewSlayer(new Slayer(x, y, this));
					subtractCoins(x, y);
					ok = true;
				}
				else {
					System.out.println("[ERROR]: " + Controller.invalidPositionMsg);
					ok = false;
				}
			}
			else {
				System.out.println("[ERROR]: Not enough coins");
				ok = false;
			}
		}
		else {
			System.out.println("[ERROR]: " + Controller.invalidPositionMsg);
			ok = false;
		}
		return ok;
	}
	
	//Añade un vampiro en una fila aleatoria del tablero según la frecuencia de level
	public void addVampire() {
		if((random.nextDouble() < level.getVampireFrecuency()) && (Vampire.getRemainingVampires() > 0)) {
			int x = this.level.getDim_X() - 1;
			int y = random.nextInt(this.level.getDim_Y());
			if(this.board.isCellEmpty(x, y)) {
				this.board.addNewVampire(new Vampire(x, y, this));	
			}
		}
	}
	
	//El palyer recibe 10 monedas con un 50% de chances
	private void getCoins() {
		if(random.nextFloat() >= 0.5) {
			player.earnCoins();
		}
	}
	
	//Comprueba si el slayer puede atacar
	public void slayerAttack(int x, int y, int damage) {
		int pos = x + 1;
		boolean attack = false;
		while((pos < level.getDim_X()) && (!attack)) {
			if (this.board.checkSlayerAttack(pos, y)) {
				this.board.slayerAttack(pos, y, damage);
				attack = true;
			}
			else {
				attack = false;
				pos ++;
			}
		}
	}
	
	//Comprueba si el vampire puede atacar
	public void vampireAttack(int x, int y, int damage) {
		if(this.board.checkVampireAttack(x - 1, y)) {
			this.board.vampireAttack(x - 1, y, damage);
		}
	}
	
	//Llama a todos los ataques 
	public void attacks() {
		this.board.attacks();
	}
	
	//Llama a movimiento de todos los vampires
	private void vampireMove() {
		this.board.moveVampires();
	}
	
	//Actualiza la información de game
	public void update() {
		vampireMove();
		getCoins();
		attacks();
		addVampire();
		updateVampireCycles();
		if(!checkEnd()) {
			numberCycles++;
		}
	}
	
	//Comprueba si ha acabado el juego
	public boolean checkEnd() {
		boolean end = false;
		if(this.board.vampireWin(this.level.getDim_Y())) {
			end = true;
		}
		else if(this.board.slayerWin()) {
			end = true;
		}
		else {
			end = false;
		}
		return end;
	}
	
	public String printEnd(boolean exit) {
		String end = "";
		if(this.board.vampireWin(this.level.getDim_Y())) {
			end = "Vampires Win";
		}
		if(this.board.slayerWin()) {
			end = "Slayers Win";
		}
		if(exit) {
			end = "Nobody wins...";
		}
		return end;
	}
	
	//Llama al método que quita 50 coins a player
	private void subtractCoins(int x, int y) {
		player.buySlayer(board.slayerCost(x, y));
	}
	
	//Actualiza el ciclo de cada vampiro
	private void updateVampireCycles() {
		this.board.updateCycles();
	}


	public int getNumberOfVampires() {
		return this.level.getNumberOfVampires();
	}
	public int getDimX() {
		return this.level.getDim_X();
	}
	public int getDimY() {
		return this.level.getDim_Y();
	}
}
