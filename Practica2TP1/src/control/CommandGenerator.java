package control;

public class CommandGenerator {
	private static Command[] availableCommands = {
		new AddCommand(),
		new HelpCommand(),
		new ResetCommand(),
		new ExitCommand(),
		new UpdateCommand(),
		new GarlicPushCommand(),
		new LightFlashCommand(),
		new BankBloodCommand(),
		new SuperCoinsCommand(),
		new AddVampireCommand()
	};
	
	public static Command parseCommand(String[] commandWords) {
		int ind = 0;
		for(int i = 0; i < availableCommands.length; i++) {
			if(availableCommands[i].parse(commandWords) != null) {
				ind = i;
			}
		}
		return availableCommands[ind].parse(commandWords);
	}
	
	public static String commandHelp() {
		String help ="Available Commands: \n" ;
		for(int i = 0; i < availableCommands.length; i++) {
			help += availableCommands[i].helpText();
		}
		return help;
	}
}
