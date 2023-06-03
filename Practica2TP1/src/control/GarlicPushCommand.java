package control;

import logic.Game;

public class GarlicPushCommand extends Command{
	public GarlicPushCommand() {
		super("garlic", "g", "[g]arlic", "pushes back vampires");
	}

	public boolean execute(Game game) {
		return game.garlicPush();
	}

	public Command parse(String[] commandWords) {
		return parseNoParamsCommand(commandWords);
	}
}
