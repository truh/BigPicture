package kehd.bigpicture.test.logic;

import kehd.bigpicture.logic.Executor;
import kehd.bigpicture.logic.commands.Command;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class Executor_Test {
    private Logger log = Logger.getLogger(Executor_Test.class);
    public Integer executionCount = 0;
    public Map paramMap;
    @Test
    public void test_executor(){
        log.info("Test fuer kehd.bigpicture.logic.Executor!");

        // instanzieren eines Executors
        Executor executor = new Executor();
        Assert.assertNotNull("Object instance should not be null", executor);

        // Command registrieren
        Command command = new Command() {
            @Override
            public String execute(Map<String, String> params) {
                paramMap = params;
                executionCount ++;
                return "Test";
            }
        };

        executor.registerCommand(command, "testCommand");
        Assert.assertTrue(executor.getCommandNames().contains("testCommand"));

        String result = executor.execute("testCommand", new HashMap<String, String>(){{
            put("param", "test");
        }});

        Assert.assertEquals("Sollte 'Test' zurückgeben", "Test", result);
        Assert.assertTrue("", paramMap.containsKey("param"));
        Assert.assertTrue("", paramMap.containsValue("test"));
        Assert.assertEquals("", "test", paramMap.get("param"));
    }
}
