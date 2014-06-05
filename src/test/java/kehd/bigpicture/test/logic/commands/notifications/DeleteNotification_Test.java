package kehd.bigpicture.test.logic.commands.notifications;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import kehd.bigpicture.exceptions.FieldMissing;
import kehd.bigpicture.exceptions.NoSuchElement;
import kehd.bigpicture.exceptions.NotANumber;
import kehd.bigpicture.exceptions.NotAuthentificated;
import kehd.bigpicture.logic.commands.events.GetComments;
import kehd.bigpicture.logic.commands.notifications.DeleteNotification;
import kehd.bigpicture.mock.EntityManagerAdapter;
import kehd.bigpicture.mock.TypedQueryAdapter;
import kehd.bigpicture.model.Comment;
import kehd.bigpicture.model.Event;
import kehd.bigpicture.model.Notification;

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
public class DeleteNotification_Test {
    @Test
    public void test() throws NotAuthentificated, FieldMissing, NotANumber, NoSuchElement {
    	EntityManagerFactory emf = mock(EntityManagerFactory.class);
		doReturn(new EntityManagerAdapter() {
			@Override
			public <T> TypedQuery<T> createQuery(String qlString,
					Class<T> resultClass) {
				TypedQueryAdapter query = new TypedQueryAdapter() {
					
					@Override
					public Notification getSingleResult(){
						return notification0;
					}

				};

				return query;
			}
		}).when(emf).createEntityManager();

	
		DeleteNotification fn= new DeleteNotification(emf);

		JsonNodeBuilder nodeBuilder = fn.execute("user0",
				new HashMap<String, String>() {
					{
						put("notificationId", "0");
					}
				});
		assertEquals("Nachricht sollte wie gegeben sein.", "Okay!", nodeBuilder.build().getText());
    }
}
