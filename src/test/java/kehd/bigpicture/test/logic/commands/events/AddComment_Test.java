package kehd.bigpicture.test.logic.commands.events;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.ParameterException;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.logic.commands.events.AddComment;
import kehd.bigpicture.test.logic.Executor_Test;
import org.apache.log4j.Logger;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import java.util.Map;

import static argo.jdom.JsonNodeBuilders.aStringBuilder;

public class AddComment_Test {
	 private Logger log = Logger.getLogger(Executor_Test.class);
	    public Integer executionCount = 0;
	    public Map paramMap;
	    private EntityManagerFactory entityManagerFactory;


	@Test
	public void test() {
		log.info("Test fuer kehd.bigpicture.logic.commands.events.AddComment!");
		AddComment ac = new AddComment(entityManagerFactory);
		
		Command cm = new Command() {
			
			@Override
			public JsonNodeBuilder execute(String username, Map<String, String> params)
					throws ParameterException {
				paramMap = params;
				executionCount ++;
				
				return aStringBuilder("Test");
			}
		};
		
	}

}
