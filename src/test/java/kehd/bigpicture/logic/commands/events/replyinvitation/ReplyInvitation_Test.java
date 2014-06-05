package kehd.bigpicture.logic.commands.events.replyinvitation;
import kehd.bigpicture.logic.commands.events.ReplyInvitation;
import org.apache.log4j.Logger;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import java.util.Map;

public class ReplyInvitation_Test {
	 private Logger log = Logger.getLogger(ReplyInvitation.class);
	    public Integer executionCount = 0;
	    public Map paramMap;
	    private EntityManagerFactory entityManagerFactory;

	
	@Test
	public void test() {
		ReplyInvitation kk = new ReplyInvitation(entityManagerFactory);
		//JsonNodeBuilder aa=kk.execute("username", new HashMap<String, String>(){{
        //    put("param", "test");
        //}});
		//assertNull(aa);
	}

}
