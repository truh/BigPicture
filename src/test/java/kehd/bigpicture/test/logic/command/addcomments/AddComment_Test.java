package kehd.bigpicture.test.logic.command.addcomments;

import static argo.jdom.JsonNodeBuilders.aStringBuilder;
import static org.junit.Assert.*;
import kehd.bigpicture.exceptions.ParameterException;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.logic.commands.events.AddComment;
import kehd.bigpicture.logic.Executor;

import java.util.Map;

import javax.persistence.EntityManagerFactory;

import kehd.bigpicture.test.logic.Executor_Test;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import argo.jdom.JsonNodeBuilder;

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
	
		
		
			
		};
		
	}

}
