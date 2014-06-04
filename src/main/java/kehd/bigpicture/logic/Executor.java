package kehd.bigpicture.logic;

import argo.format.CompactJsonFormatter;
import argo.format.JsonFormatter;
import argo.format.PrettyJsonFormatter;
import argo.jdom.JsonNodeBuilder;
import argo.jdom.JsonRootNode;
import kehd.bigpicture.exceptions.NoSuchMethod;
import kehd.bigpicture.exceptions.ParameterException;
import kehd.bigpicture.logic.commands.Command;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static argo.jdom.JsonNodeBuilders.*;

/**
 * Commands koennen unter einem Namen abgelegt werden
 * und spaeter unter diesem aufgerufen werden.
 *
 * Dies ermoeglicht eine lose Kopplung zwischen Aufruf
 * und Implementierung.
 */
public class Executor {
    private static final Logger log = Logger.getLogger(Executor.class);
    /**
     * JSON formatter to be used by all command implementations.
     *
     * Set system property bp.pretty-json to true if you want
     * pretty json.
     */
    public static final JsonFormatter JSON_FORMATTER =
            (System.getProperty("bp-debug") == null
                    ?false // && funktioniert hier nicht -> NullPointerException, bedingte zuweisung geht
                    :System.getProperty("bp.pretty-json").toLowerCase().equals("true"))
                    ?new PrettyJsonFormatter()
                    :new CompactJsonFormatter();

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
     * @return Rueckgabe des Commands
     */
	public String execute(String commandName, String username, Map<String, String> params) {
        log.info("execute: " + commandName + " params: " + params.toString());

        Command command = commandMapping.get(commandName);

        JsonNodeBuilder errorNodeBuilder = aNullBuilder();
        JsonNodeBuilder resultNodeBuilder = aNullBuilder();

        try {
            if(command == null) {
                throw new NoSuchMethod(commandName);
            }
            resultNodeBuilder = command.execute(username, params);
        } catch (ParameterException e) {
            errorNodeBuilder = anObjectBuilder()
                    .withField("code", aNumberBuilder("" + e.getErrorId()))
                    .withField("message", aStringBuilder(e.getMessage()));
        }

        JsonRootNode resultNode = anObjectBuilder()
                .withField("error", errorNodeBuilder)
                .withField("result", resultNodeBuilder)
                .build();
        return JSON_FORMATTER.format(resultNode);
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

    /**
     * Set mit allen Command Namen
     * 
     * @return Commands Names
     */
    public Set<String> getCommandNames() {
        return commandMapping.keySet();
    }
}
