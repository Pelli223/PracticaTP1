package control;

import logic.Game;

public class AddCommand extends Command{
	private int row;
	private int col;
	public AddCommand() {
		super("add", "a", "[a]dd <x> <y>", "add a slayer in position x, y");
	}
	public AddCommand(int x, int y) {
		super("add", "a", "[a]dd <x> <y>", "add a slayer in position x, y");
		this.row = y;
		this.col = x;
	}
	public boolean execute(Game game) {
		return game.addSlayer(col, row);
	}

	public Command parse(String[] commandWords) {
		if (commandWords.length == 3) {
			if(matchCommandName(commandWords[0])) {
				return new AddCommand(Integer.parseInt(commandWords[1]), Integer.parseInt(commandWords[2]));
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
