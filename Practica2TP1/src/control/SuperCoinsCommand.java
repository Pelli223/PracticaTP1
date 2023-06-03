package control;

import logic.Game;

public class SuperCoinsCommand extends Command{
	public SuperCoinsCommand() {
		super("coins", "c", "[c]oins", "add 1000 coins");
	}

	public boolean execute(Game game) {
		game.superCoins();
		return true;
	}

	public Command parse(String[] commandWords) {
		return parseNoParamsCommand(commandWords);
	}
}
