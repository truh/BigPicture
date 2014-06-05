package kehd.bigpicture.logic.commands;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.ParameterException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

public interface Command {
    /**
     * DateFormat to be used by command implementations.
     */
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

    /**
     * Fuehrt Command aus
     *
     * @param username User mit dem das Command ausgefuehrt werden soll.
     * @param params Parameter
     *
     * @return Ergebniss als Json
     * @throws ParameterException
     */
	public abstract JsonNodeBuilder execute(String username, Map<String, String> params) throws ParameterException;

}
