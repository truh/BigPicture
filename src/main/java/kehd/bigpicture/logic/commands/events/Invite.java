package logic.commands.events;

import logic.commands.Command;
import java.util.Date;

public class Invite implements Command {

	private String eventName;

	private Date date;

	private String userName;


	/**
	 * @see logic.commands.Command#execute()
	 */
	public void execute() {

	}


	/**
	 * @see logic.commands.Command#result()
	 */
	public String result() {
		return null;
	}

}
