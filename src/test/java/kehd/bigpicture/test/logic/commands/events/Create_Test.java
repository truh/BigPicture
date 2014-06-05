package kehd.bigpicture.test.logic.commands.events;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import argo.jdom.JsonNodeBuilder;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import java.util.HashMap;
import java.util.Map;

import kehd.bigpicture.exceptions.FieldMissing;
import kehd.bigpicture.exceptions.NotAuthentificated;
import kehd.bigpicture.exceptions.NotAuthorized;
import kehd.bigpicture.exceptions.UserDoesNotExist;
import kehd.bigpicture.logic.commands.events.Create;
import kehd.bigpicture.logic.commands.events.GetComments;
import kehd.bigpicture.logic.commands.events.RemoveUserFromEvent;
import kehd.bigpicture.mock.EntityManagerAdapter;
import kehd.bigpicture.mock.TypedQueryAdapter;
import kehd.bigpicture.model.Event;
import kehd.bigpicture.model.User;

import static kehd.bigpicture.mock.model.MockModels.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class Create_Test {
	private Logger log = Logger.getLogger(Create_Test.class);
    public Integer executionCount = 0;
    public Map paramMap;
    private EntityManagerFactory entityManagerFactory;


    @Test
    public void test() throws UserDoesNotExist, FieldMissing, NotAuthentificated, NotAuthorized {
    	EntityManagerFactory emf = mock(EntityManagerFactory.class);
		doReturn(new EntityManagerAdapter() {
			@Override
			public <T> TypedQuery<T> createQuery(String qlString,
					Class<T> resultClass) {
				TypedQueryAdapter query = new TypedQueryAdapter() {
//					@Override
//					public List<User> getResultList() {
//						return list(user0, user1);
//					}
					@Override
					public User getSingleResult(){
						return user0;
					}

				};

				return query;
			}
		}).when(emf).createEntityManager();

		
		Create ce= new Create(emf);

		JsonNodeBuilder nodeBuilder = ce.execute("user0",
				new HashMap<String, String>() {
					{
						put("eventName", "event0");
						put("eventType","SINGLE");
					}
				});
		assertEquals("Nachricht sollte wie gegeben sein.", "Okay!", nodeBuilder.build().getText());
    }

}
