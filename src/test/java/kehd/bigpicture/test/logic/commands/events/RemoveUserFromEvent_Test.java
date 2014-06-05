package kehd.bigpicture.test.logic.commands.events;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import kehd.bigpicture.exceptions.FieldMissing;
import kehd.bigpicture.exceptions.NoSuchElement;
import kehd.bigpicture.exceptions.NotAuthentificated;
import kehd.bigpicture.exceptions.NotAuthorized;
import kehd.bigpicture.logic.commands.events.GetComments;
import kehd.bigpicture.logic.commands.events.RemoveUserFromEvent;
import kehd.bigpicture.mock.EntityManagerAdapter;
import kehd.bigpicture.mock.TypedQueryAdapter;
import kehd.bigpicture.model.Comment;
import kehd.bigpicture.model.Event;
import kehd.bigpicture.model.User;

import org.junit.Test;

import argo.jdom.JsonNodeBuilder;

import static kehd.bigpicture.mock.model.MockModels.comment0;
import static kehd.bigpicture.mock.model.MockModels.comment1;
import static kehd.bigpicture.mock.model.MockModels.event0;
import static kehd.bigpicture.mock.model.MockModels.list;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static kehd.bigpicture.mock.model.MockModels.*;
/**
 * 
 */
public class RemoveUserFromEvent_Test {
    @Test
    public void test() throws NoSuchElement, FieldMissing, NotAuthentificated, NotAuthorized {
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
					public Event getSingleResult(){
						return event1;
					}

				};

				return query;
			}
		}).when(emf).createEntityManager();

		GetComments hv = new GetComments(emf);
		RemoveUserFromEvent rufe= new RemoveUserFromEvent(emf);

		JsonNodeBuilder nodeBuilder = rufe.execute("user1",
				new HashMap<String, String>() {
					{
						put("eventName", "event1");
						put("username","user0");
					}
				});
		
		assertEquals("Nachricht sollte wie gegeben sein.", "Okay!", nodeBuilder.build().getText());
    }
}
