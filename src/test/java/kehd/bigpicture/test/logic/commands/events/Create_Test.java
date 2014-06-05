package kehd.bigpicture.test.logic.commands.events;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import kehd.bigpicture.exceptions.FieldMissing;
import kehd.bigpicture.exceptions.NotAuthentificated;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Create_Test {
	private Logger log = Logger.getLogger(Create_Test.class);
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
	public void test() throws FieldMissing, NotAuthentificated {
//		Create ct= new Create(entityManagerFactory);
//		ct.execute("username", new HashMap<String, String>(){{
//            put("eventname", "test");
//        }});
		
		
		
		
		
	}

}
