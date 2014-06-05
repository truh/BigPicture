package kehd.bigpicture.logic.commands.events.replyinvitation;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import kehd.bigpicture.exceptions.DateInvalid;
import kehd.bigpicture.exceptions.FieldMissing;
import kehd.bigpicture.exceptions.NoSuchElement;
import kehd.bigpicture.exceptions.NotAuthentificated;
import kehd.bigpicture.logic.commands.events.*;
import kehd.bigpicture.test.logic.commands.events.AddComment_Test;
import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import argo.jdom.JsonNodeBuilder;

public class ReplyInvitation_Test {
	 private Logger log = Logger.getLogger(ReplyInvitation.class);
	    public Integer executionCount = 0;
	    public Map paramMap;
	    private EntityManagerFactory entityManagerFactory;

	
	@Test
	public void test() throws NotAuthentificated, FieldMissing, DateInvalid, NoSuchElement {
		ReplyInvitation kk = new ReplyInvitation(entityManagerFactory);
		JsonNodeBuilder aa=kk.execute("username", new HashMap<String, String>(){{
            put("param", "test");
        }});
		assertNull(aa);
	}

}
