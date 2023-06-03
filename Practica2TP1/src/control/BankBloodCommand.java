package control;

import logic.Game;

public class BankBloodCommand extends Command{
	private int col;
	private int row;
	private int z;
	public BankBloodCommand() {
		super("bank", "b", "[b]ank <x> <y> <z>", "add a blood bank with cost z in position x, y");
	}

	public BankBloodCommand(int x, int y, int z) {
		super("bank", "b", "[b]ank <x> <y> <z>", "add a blood bank with cost z in position x, y");
		this.col = x;
		this.row = y;
		this.z = z;
	}
	public boolean execute(Game game) {
		return game.addBankBlood(col, row, z);
	}

	public Command parse(String[] commandWords) {
		if (commandWords.length == 4) {
			if(matchCommandName(commandWords[0])) {
				return new BankBloodCommand(Integer.parseInt(commandWords[1]), Integer.parseInt(commandWords[2]), Integer.parseInt(commandWords[3]));
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
