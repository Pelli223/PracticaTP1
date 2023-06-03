package control;

import logic.Game;

public class UpdateCommand extends Command{
	private final String newShortcut;
	public UpdateCommand() {
		super("none", "n", "[n]one | []", "update");
		this.newShortcut = "";
	}

	public boolean execute(Game game) {
		game.update();
		return true;
	}

	public Command parse(String[] commandWords) {
		if(commandWords[0].equalsIgnoreCase(this.newShortcut)) {
			return this;
		}
		else {
		return parseNoParamsCommand(commandWords);
		}
	}
}
