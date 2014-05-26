package logic.commands.events;

import logic.commands.Command;

public class AddComment implements Command {

	private String eventName;

	private String title;

	private String content;


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
