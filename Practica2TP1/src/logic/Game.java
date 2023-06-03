package logic;
import vampireslayer.Player;
import view.GamePrinter;
import view.IPrintable;
import characters.BankBlood;
import characters.Dracula;
import characters.ExplosiveVampire;
import characters.IAttack;
import characters.Slayer;
import characters.Vampire;
import control.CommandGenerator;

import java.util.Random;

public class Game implements IPrintable{
	private GameObjectBoard board;
	private GamePrinter printer;
	private int numberCycles;
	private boolean exit;
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
		this.exit = false;
		this.random = new Random(this.seed);
		this.printer = new GamePrinter((IPrintable)this, this.level.getDim_X(), this.level.getDim_Y());
		this.player = new Player();
		this.board = new GameObjectBoard();
		Vampire.init(this.level.getNumberOfVampires());
	}

	//Recibe el tablero pintado en forma de string y añade la información del juego
	public String getInfo() {
		String text = String.format (
				"Number of Cycles: " + this.numberCycles +
				"%n" + 
				"Coins: " + this.player.getCoins() +
				"%n"+ 
				"Remaining vampires: " + Vampire.getRemainingVampires() +
				"%n"+ 
				"Vampires on the board: " + Vampire.getVampiresOnBoard() +
				"%n");
		if(Dracula.getDraculaAlive()) {
			text = text + "Dracula is alive";
		}
		return text;
	}
	
	public String toString() {
		return this.printer.toString();
	}
	
	public String getPositionToString(int x, int y) {
		return this.board.positionToString(x, y);
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
					this.board.newGameObject(new Slayer(x, y, this));
					subtractCoins(new Slayer(x, y, this).getCost());
					update();
					ok = true;
				}
				else {
					ok = false;
					System.out.println("[ERROR]: Invalid Position");
				}
			}
			else {
				ok = false;
				System.out.println("[ERROR]: Not enough coins");
			}
		}
		else {
			ok = false;
			System.out.println("[ERROR]: Invalid Position");
		}
		return ok;
	}
	
	public boolean addBankBlood(int x, int y, int z) {
		boolean ok;
		if((z % 10 == 0) && (z > 0)) {
			if((x < getDimX() - 1) && (y < getDimY()) && (x >= 0) && (y >= 0) ) {
				if(this.player.getCoins() >= ((new BankBlood(x, y, z, this).getZ()))) {
					if(this.board.isCellEmpty(x, y)) {
					this.board.newGameObject(new BankBlood(x, y, z, this));
						subtractCoins(new BankBlood(x, y, z, this).getZ());
						update();
						ok = true;
					}
					else {
						ok = false;
						System.out.println("[ERROR]: Invalid Position");
					}
				}
				else {
					ok = false;
					System.out.println("[ERROR]: Not enough coins");
				}
			}
			else {
				ok = false;
				System.out.println("[ERROR]: Invalid Position");
			}
		}
		else {
			ok = false;
			System.out.println("[ERROR]: Invalid value of z");
		}
		return ok;
	}
	
	public void bankBloodCoins(int z) {
		this.player.bankCoins(z);
	}
	
	//Añade un vampiro en una fila aleatoria del tablero según la frecuencia de level
	public void addVampire() {
		if((random.nextDouble() < level.getVampireFrecuency()) && (Vampire.getRemainingVampires() > 0)) {
			int x = this.level.getDim_X() - 1;
			int y = random.nextInt(this.level.getDim_Y());
			if(this.board.isCellEmpty(x, y)) {
				this.board.newGameObject(new Vampire(x, y, this));	
			}
		}
	}
	
	//Añade un vampiro explosivo en una fila aleatoria del tablero segun la frecuencia de level
	public void addExplosiveVampire() {
		if((random.nextDouble() < level.getVampireFrecuency()) && (Vampire.getRemainingVampires() > 0)) {
			int x = this.level.getDim_X() - 1;
			int y = random.nextInt(this.level.getDim_Y());
			if(this.board.isCellEmpty(x, y)) {
				this.board.newGameObject(new ExplosiveVampire(x, y, this));	
			}
		}
	}
	
	//Añade un dracula  en una fila aleatoria del tablero según la frecuencia de level siempre que no haya ningun otro en el tablero
	public void addDracula() {
		if(!Vampire.getDraculaAlive()) {
			if((random.nextDouble() < level.getVampireFrecuency()) && (Vampire.getRemainingVampires() > 0)) {
				int x = this.level.getDim_X() - 1;
				int y = random.nextInt(this.level.getDim_Y());
				if(this.board.isCellEmpty(x, y)) {
					this.board.newGameObject(new Dracula(x, y, this));	
				}
			}
		}
	}
	
	//El player recibe 10 monedas con un 50% de chances
	private void getCoins() {
		if(random.nextFloat() >= 0.5) {
			player.earnCoins();
		}
	}
	
	//Llama a todos los ataques 
	public void attacks() {
		this.board.attacks(false, false);
	}
	
	//Llama a movimiento de todos los vampires
	private void moveGameObjects() {
		this.board.move();
	}
	
	//Actualiza la información de game
	public void update() {
		getCoins();
		attacks();
		moveGameObjects();
		addVampire();
		addDracula();
		addExplosiveVampire();
		if(!checkEnd()) {
			numberCycles++;
		}
	}
	
	public void exit() {
		this.exit = true;
	}
	
	//Comprueba si hay monedas suficientes para garlicPush y en caso afirmativo llama al arraylist de objetos para que lo reciban
	public boolean garlicPush() {
		boolean ok = false;
		if(this.player.getCoins() >= 10) {
			this.player.buySlayer(10);
			this.board.attacks(true, false);
			ok = true;
			update();
		}
		else {
			System.out.println("[ERROR]: Not enough coins");
		}
		return ok;
	}
	
	//Comprueba si hay monedas suficientes para light y en caso afirmativo llama al arraylist de objetos para que lo reciban
	public boolean light() {
		boolean ok = false;
		if(this.player.getCoins() >= 50) {
			this.player.buySlayer(50);
			this.board.attacks(false, true);
			update();
			ok = true;
		}
		else {
			System.out.println("[ERROR]: Not enough coins");
		}
		return ok;
	}
	
	//Añade 1000 coins a player
	public void superCoins() {
		this.player.superCoins();
	}
	
	//Añade un vampiro del tipo que queramos en la posicion que queramos del tablero
	public boolean addVampire(int x, int y, String letra) {
		boolean ok;
		if((x < getDimX()) && (y < getDimY()) && (x >= 0) && (y >= 0) ) {
			if(Vampire.getRemainingVampires() > 0) {
				if(this.board.isCellEmpty(x, y)) {
					if(letra.equals("v")){
						this.board.newGameObject(new Vampire(x, y, this));
						ok = true;
					}
					else if(letra.equals("d")&& (!Vampire.getDraculaAlive())) {
						this.board.newGameObject(new Dracula(x, y, this));
						ok = true;
					}
					else if(letra.equals("e")) {
						ok = true;
						this.board.newGameObject(new ExplosiveVampire(x, y, this));	
					}
					else {
						ok = false;
						System.out.println("[ERROR]: Invalid type of vampire");
					}
				}
			
				else {
					ok = false;
					System.out.println("[ERROR]: Invalid Position");
				}
			}
			else {
				ok = false;
				System.out.println("[ERROR]: No more remaining vampires left");
			}
		}
		else {
			ok = false;
			System.out.println("[ERROR]: Invalid Position");
		}
		return ok;
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
		else if(this.exit) {
			end = true;
		}
		return end;
	}
	
	//En caso de que la partida haya acabado imprime un mensaje que concuerde con como ha acabado la partida
	public String printEnd() {
		String end = "";
		if(this.board.vampireWin(this.level.getDim_Y())) {
			end = "Vampires Win";
		}
		if(this.board.slayerWin()) {
			end = "Slayers Win";
		}
		if (this.exit) {
			end = "Nobody Wins...";
		}
		return end;
	}
	
	//Llama al texto de ayuda de todos los command de commandGenerator
	public void helpText() {
		System.out.println(CommandGenerator.commandHelp());
	}
	
	//Llama al método que quita 50 coins a player
	private void subtractCoins(int cost) {
		player.buySlayer(cost);
	}

	public int getDimX() {
		return this.level.getDim_X();
	}
	public int getDimY() {
		return this.level.getDim_Y();
	}

	public IAttack getAttackableInPosition(int x, int y) {
		return this.board.getAttackableInPosition(x, y);
	}

	public void removeDeadObject(int x, int y) {
		this.board.eliminate(x, y);
	}
}
