package kehd.bigpicture.test.logic;

import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.jdom.JsonNodeBuilder;
import argo.saj.InvalidSyntaxException;
import kehd.bigpicture.exceptions.ParameterException;
import kehd.bigpicture.logic.Executor;
import kehd.bigpicture.logic.commands.Command;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static argo.jdom.JsonNodeBuilders.aNullBuilder;
import static argo.jdom.JsonNodeBuilders.aStringBuilder;

public class Executor_Test {
    private Logger log = Logger.getLogger(Executor_Test.class);
    public Integer executionCount = 0;
    public Map paramMap;

    @Test
    public void correctExecution() throws InvalidSyntaxException {
        log.info("Test fuer kehd.bigpicture.logic.Executor!");

        // instanzieren eines Executors
        Executor executor = new Executor();
        Assert.assertNotNull("Object instance should not be null", executor);

        // Command registrieren
        Command command = new Command() {
        
			@Override
			public JsonNodeBuilder execute(String username, Map<String, String> params)
                    throws ParameterException {
				paramMap = params;
				executionCount ++;
				return aStringBuilder("Test");
			}
        };

        executor.registerCommand(command, "testCommand");
        Assert.assertTrue(executor.getCommandNames().contains("testCommand"));

        String result = executor.execute("testCommand", "username", new HashMap<String, String>(){{
            put("param", "test");
        }});

        JdomParser parser = new JdomParser();
        JsonNode rootNode = parser.parse(result);

        Assert.assertEquals("Sollte 'Test' zurueckgeben", "Test", rootNode.getStringValue("result"));
        Assert.assertEquals("Sollte keinen error geben", aNullBuilder().build(), rootNode.getNode("error"));
        Assert.assertTrue("", paramMap.containsKey("param"));
        Assert.assertTrue("", paramMap.containsValue("test"));
        Assert.assertEquals("", "test", paramMap.get("param"));
    }

}
