package kehd.bigpicture.test.logic.commands.events;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import kehd.bigpicture.exceptions.NotAuthentificated;
import kehd.bigpicture.logic.commands.events.*;
import kehd.bigpicture.mock.EntityManagerAdapter;
import kehd.bigpicture.model.Event;
import kehd.bigpicture.model.Notification;
import kehd.bigpicture.model.User;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import argo.jdom.JsonNode;
import argo.jdom.JsonNodeBuilder;

public class GetEvents_Test {
	 private Logger log = Logger.getLogger(ReplyInvitation.class);
	    public Integer executionCount = 0;
	    public Map paramMap;
	    private EntityManagerFactory entityManagerFactory = mock(EntityManagerFactory.class);

	@Test
	public void test() throws NotAuthentificated {
		 EntityManagerFactory emf = mock(EntityManagerFactory.class);
	        doReturn(new EntityManagerAdapter() {
	            @Override
	            public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
	                TypedQuery<T> query = mock(TypedQuery.class);

	                ArrayList<Event> notifications = new ArrayList<Event>(){{
	                    Event e1 = new Event();
	                    e1.setId(1);
	                    e1.setTitle("Event1");

	                    Event e2 = new Event();
	                    e2.setId(2);
	                    e2.setTitle("Event2");

	                    ArrayList<User> recipients = new ArrayList<User>() {{
	                        User johnDoe = new User();
	                        johnDoe.setName("JohnDoe");
	                        add(johnDoe);
	                        
	                        User misterT= new User();
	                        misterT.setName("MisterT");
	                        add(misterT);
	                        
	                        
	                    }};
	                    e1.setOrganisator(recipients.get(0));
	                    e2.setUsers(recipients);
	}
	                };
	                doReturn(notifications).when(query).getResultList();

	                return query;
	            
	            }
	        }).when(entityManagerFactory).createEntityManager();
	        GetEvents ew= new GetEvents(entityManagerFactory);
	        JsonNodeBuilder nodeBuilder = ew.execute("JohnDoe", new HashMap<String, String>());
	        System.out.println(nodeBuilder.build().hasElements());
	        
	        List<JsonNode> nn= nodeBuilder.build().getElements();
	        
	        List<JsonNode> nodes = nodeBuilder.build().getElements();
	        
	        System.out.println( nodeBuilder.build().getElements().size());
	        //assertSame("", 0, nodes.size());
	        
	       // assertEquals("Nachricht sollte wie gegeben sein.", "Event1", nodes.get(0).getStringValue("title"));
	        }
}
	        


