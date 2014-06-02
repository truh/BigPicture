package kehd.bigpicture.logic.commands;

import argo.format.CompactJsonFormatter;
import argo.format.JsonFormatter;
import argo.format.PrettyJsonFormatter;
import kehd.bigpicture.exceptions.ParameterException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

public interface Command {
    /**
     * JSON formatter to be used by all command implementations.
     *
     * Set system property bp.pretty-json to true if you want
     * pretty json.
     */
    public static final JsonFormatter JSON_FORMATTER =
            (System.getProperty("bp-debug") == null
                    && System.getProperty("bp.pretty-json").toLowerCase().equals("true"))
                    ?new PrettyJsonFormatter()
                    :new CompactJsonFormatter();

    /**
     * DateFormat to be used by command implementations.
     */
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

	public abstract String execute(Map<String, String> params) throws ParameterException;

}
