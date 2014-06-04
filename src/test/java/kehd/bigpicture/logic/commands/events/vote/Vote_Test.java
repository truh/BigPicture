package kehd.bigpicture.logic.commands.events.vote;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import junit.framework.Assert;

import kehd.bigpicture.logic.commands.events.Vote;
import kehd.bigpicture.test.logic.commands.events.AddComment_Test;
import static org.junit.Assert.*;

import org.apache.log4j.Logger;

import org.junit.Test;

import argo.jdom.JsonNodeBuilder;

public class Vote_Test {

	 private Logger log = Logger.getLogger(AddComment_Test.class);
	    public Integer executionCount = 0;
	    public Map paramMap;
	    private EntityManagerFactory entityManagerFactory;
	
	@Test
	public void test() {
		Vote kk = new Vote(entityManagerFactory);
		JsonNodeBuilder aa=kk.execute("username", new HashMap<String, String>(){{
            put("param", "test");
        }});
		assertNull(aa);
	}

}
