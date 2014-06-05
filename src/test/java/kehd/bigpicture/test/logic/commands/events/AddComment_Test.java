package kehd.bigpicture.test.logic.commands.events;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.ParameterException;
import kehd.bigpicture.logic.commands.events.AddComment;
import kehd.bigpicture.mock.EntityManagerAdapter;
import kehd.bigpicture.mock.TypedQueryAdapter;
import kehd.bigpicture.model.Event;
import kehd.bigpicture.model.User;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class AddComment_Test {
	private Logger log = Logger.getLogger(AddComment_Test.class);
	public Map paramMap;

	@Test
	public void test() throws ParameterException {
		log.info("Test fuer kehd.bigpicture.logic.commands.events.AddComment!");

        EntityManagerAdapter manager = new EntityManagerAdapter() {
            Class aClass;
            @Override
            public void persist(Object entity) {}

            @Override
            public TypedQuery createQuery(String sqlString, Class resultClass) {
                aClass = resultClass;
                return new TypedQueryAdapter() {
                    @Override
                    public Object getSingleResult() {
                        Object obj = null;
                        if(aClass.equals(User.class)) {
                            User user = new User();
                            obj = user;
                        }
                        if(aClass.equals(Event.class)) {
                            Event event = new Event();
                            obj = event;
                        }
                        return obj;
                    }
                };
            }
        };

        EntityManagerFactory entityManagerFactory = mock(EntityManagerFactory.class);
        doReturn(manager).when(entityManagerFactory).createEntityManager();

		AddComment ac = new AddComment(entityManagerFactory);

		JsonNodeBuilder result = ac.execute("username", new HashMap<String, String>(){{
            put("param", "test");
        }});

		Assert.assertTrue("", paramMap.containsKey("param"));
	    Assert.assertTrue("", paramMap.containsValue("test"));
	}
}
