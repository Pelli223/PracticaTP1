package control;

import logic.Game;

public class AddVampireCommand extends Command{
	private int x;
	private int y;
	private String letra;
	
	public AddVampireCommand() {
		super("vampire", "v", "[v]ampire [<type>] <x> <y>. Type = {\"\"|\"D\"|\"E\"}", "add a vampire in position x, y");
	}
	
	public AddVampireCommand(String letra, int x, int y) {
		super("vampire", "v", "[v]ampire [<type>] <x> <y>. Type = {\"\"|\"D\"|\"E\"}", "add a vampire in position x, y");
		this.x = x;
		this.y = y;
		this.letra = letra;
	}

	public boolean execute(Game game) {
		return game.addVampire(this.x, this.y, this.letra);
	}

	public Command parse(String[] commandWords) {
		if (commandWords.length == 4) {
			if(matchCommandName(commandWords[0])) {
				return new AddVampireCommand(commandWords[1], Integer.parseInt(commandWords[2]), Integer.parseInt(commandWords[3]));
			}
			else {
				return null;
			}
		}
		else if (commandWords.length == 3) {
			if(matchCommandName(commandWords[0])) {
				return new AddVampireCommand("v", Integer.parseInt(commandWords[1]), Integer.parseInt(commandWords[2]));
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
	}
}
