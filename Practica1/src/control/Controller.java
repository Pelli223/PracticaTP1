package control;

import java.util.Scanner;

import logic.Game;

public class Controller {

	
	public final String prompt = "Command > ";
	public static final String helpMsg = String.format(
			"Available commands:%n" +
			"[a]dd <x> <y>: add a slayer in position x, y%n" +
			"[h]elp: show this help%n" + 
			"[r]eset: reset game%n" + 
			"[e]xit: exit game%n"+ 
			"[n]one | []: update%n");
	
	public static final String unknownCommandMsg = String.format("Unknown command");
	public static final String invalidCommandMsg = String.format("Invalid command");
	public static final String invalidPositionMsg = String.format("Invalid position");

    private Game game;
    private Scanner scanner;
    
    public Controller(Game game, Scanner scanner) {
	    this.game = game;
	    this.scanner = scanner;
    }
    
    public void  printGame() {
   	 System.out.println(game);
   }
    
    public void run() {
    	boolean exit = false;
    	boolean end = false;
    	this.printGame();
    	while((!exit) && (!end)) {
    		System.out.println(prompt); 
    		String line = scanner.nextLine();
    		System.out.println("\n[DEBUG] Executing: " + line);
    		String[]words = line.toLowerCase().trim().split("\\s+");
    		if ((words[0].equals("a")) || words[0].equals("add")){
    			int x = Integer.parseInt(words[1]);
    			int y = Integer.parseInt(words[2]);
    			if(this.game.addSlayer(x, y)) {
    				this.game.update();
    				this.printGame();
    			}
    		}
    		else if ((words[0].equals("n")) || (words[0].equals("")) || (words[0].equals("none"))) {
    			this.game.update();
    			this.printGame();
    		}
    		else if((words[0].equals("r")) || (words[0].equals("reset"))) {
    			this.game.init();
    			this.printGame();
    		}
    		else if((words[0].equals("h")) || (words[0].equals("help"))) {
    			System.out.println(helpMsg);
    		}
    		else if((words[0].equals("e")) || (words[0].equals("exit"))) {
    			exit = true;
    		}
    		else {
    			System.out.println("[ERROR]: " + unknownCommandMsg);
    		}
    		end = this.game.checkEnd();
    	}
    	System.out.println(this.game.printEnd(exit));
    	System.out.println("Game Over");
    }
}