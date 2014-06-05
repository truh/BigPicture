package kehd.bigpicture.test.logic.commands.events;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import java.util.Map;

import static org.junit.Assert.fail;

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
    public void test() {
        fail("Not implemented");
    }

}
