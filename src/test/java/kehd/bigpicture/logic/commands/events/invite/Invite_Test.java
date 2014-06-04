package kehd.bigpicture.logic.commands.events.invite;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.logic.commands.events.*;
import kehd.bigpicture.test.logic.commands.events.AddComment_Test;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Invite_Test {

	 private Logger log = Logger.getLogger(AddComment_Test.class);
	    public Integer executionCount = 0;
	    public Map paramMap;
	    private EntityManagerFactory entityManagerFactory;

	@Test
	public void test() {
		Invite ie= new Invite(entityManagerFactory);
			
		
		ie.execute("username", new HashMap<String, String>(){{
            put("eventname", "party");
            put("date", Command.DATE_FORMAT.format(new Date()));
            put("user", "test");
        }});
		
	}

}
