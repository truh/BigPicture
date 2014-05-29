package kehd.bigpicture.logic;

import kehd.bigpicture.logic.commands.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * Commands koennen unter einem Namen abgelegt werden
 * und spaeter unter diesem aufgerufen werden.
 *
 * Dies ermöglicht eine lose Kopplung zwischen Aufruf
 * und Implementierung.
 */
public class Executor {
    private Map<String, Command> commandMapping;

    /**
     * Neue Instanz ohne Command Mappings
     */
    public Executor() {
        commandMapping = new HashMap<>();
    }

    /**
     * Fuehrt ein Command aus
     *
     * @param commandName Name des Commands
     * @param params Map mit Parametern
     * @return Rückgabe des Commands
     */
	public String execute(String commandName, Map<String, String> params) {
        String result;
        Command command = commandMapping.get(commandName);
        result = command.execute(params);
		return result;
	}

    /**
     * Registriert ein Command unter gegebenen Namen
     *
     * @param command Command
     * @param commandName Name des Commands
     */
    public void registerCommand(Command command, String commandName) {
        commandMapping.put(commandName, command);
    }
}
