package kehd.bigpicture.logic.commands;

import java.util.Map;

public interface Command {

	public abstract String execute(Map<String, String> params);

}
