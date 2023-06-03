package control;

import logic.Game;

public class LightFlashCommand extends Command{
	public LightFlashCommand() {
		super("light", "l", "[l]ight", "kills all the vampires");
	}

	public boolean execute(Game game) {
		return game.light();
	}

	public Command parse(String[] commandWords) {
		return parseNoParamsCommand(commandWords);
	}
}
