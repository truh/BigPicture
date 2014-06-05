package kehd.bigpicture.test.logic.commands.events;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import kehd.bigpicture.logic.commands.events.GetComments;
import kehd.bigpicture.test.logic.commands.events.AddComment_Test;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GetComments_Test {
	 private Logger log = Logger.getLogger(AddComment_Test.class);
	    public Integer executionCount = 0;
	    public Map paramMap;
	    private EntityManagerFactory entityManagerFactory;
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		GetComments gc = new GetComments(entityManagerFactory);
	}

}
