package kehd.bigpicture.test.logic.commands.events;

import kehd.bigpicture.logic.commands.events.ReplyInvitation;
import org.apache.log4j.Logger;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import java.util.Map;

import static org.junit.Assert.fail;

public class ReplyInvitation_Test {
	 private Logger log = Logger.getLogger(ReplyInvitation.class);
	    public Integer executionCount = 0;
	    public Map paramMap;
	    private EntityManagerFactory entityManagerFactory;


    @Test
    public void test() {
        fail("Not implemented");
    }

}
