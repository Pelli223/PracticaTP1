package control;

import logic.Game;

public class HelpCommand extends Command{
	public HelpCommand() {
		super("help", "h", "[h]elp", "show this help");
	}

	public boolean execute(Game game) {
		game.helpText();
		return false;
	}

	public Command parse(String[] commandWords) {
		return parseNoParamsCommand(commandWords);
	}
}
