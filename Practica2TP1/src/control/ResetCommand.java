package control;

import logic.Game;

public class ResetCommand extends Command{
	public ResetCommand() {
		super("reset", "r", "[r]eset", "reset game");
	}

	public boolean execute(Game game) {
		game.init();
		return true;
	}

	public Command parse(String[] commandWords) {
		return parseNoParamsCommand(commandWords);
	}
}
