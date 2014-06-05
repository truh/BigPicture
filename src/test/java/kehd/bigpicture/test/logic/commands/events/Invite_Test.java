package kehd.bigpicture.test.logic.commands.events;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.DateInvalid;
import kehd.bigpicture.exceptions.NoSuchElement;
import kehd.bigpicture.exceptions.NotAuthentificated;
import kehd.bigpicture.exceptions.NotAuthorized;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.logic.commands.events.Invite;
import kehd.bigpicture.logic.commands.notifications.GetNotifications;
import kehd.bigpicture.mock.EntityManagerAdapter;
import kehd.bigpicture.mock.TypedQueryAdapter;
import kehd.bigpicture.model.Event;
import kehd.bigpicture.model.Notification;
import kehd.bigpicture.model.User;
import org.apache.log4j.Logger;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;

import static kehd.bigpicture.mock.model.MockModels.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class Invite_Test {

	private Logger log = Logger.getLogger(Invite.class);
	public Integer executionCount = 0;
	public Map paramMap;

    TypedQueryAdapter<Event> eventQuery;
    TypedQueryAdapter<User> userQuery;

    EntityManagerAdapter em;

    Notification notification;
    Event event;

	@Test
	public void test() throws NoSuchElement, NotAuthentificated, NotAuthorized,
			DateInvalid {
        eventQuery = mock(TypedQueryAdapter.class);
        doReturn(event1).when(eventQuery).getSingleResult();
        doReturn(eventQuery).when(eventQuery).setParameter("eventName", event1.getTitle());

        userQuery = mock(TypedQueryAdapter.class);
        doReturn(user0).when(userQuery).getSingleResult();
        doReturn(userQuery).when(userQuery).setParameter("userName", user0.getName());

        em = new EntityManagerAdapter() {
            @Override
            public void persist(Object entity) {
                if(entity instanceof Event) {
                    event = (Event) entity;
                }
                if(entity instanceof Notification) {
                    notification = (Notification) entity;
                }
            }

            @Override
            public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
                if(resultClass == Event.class) {
                    return (TypedQuery<T>) eventQuery;
                }
                if(resultClass == User.class) {
                    return (TypedQuery<T>) userQuery;
                }
                return super.createQuery(qlString, resultClass);
            }
        };

		EntityManagerFactory emf = mock(EntityManagerFactory.class);
        doReturn(em).when(emf).createEntityManager();

		GetNotifications getNotifications = new GetNotifications(emf);
		Invite iv = new Invite(emf);

		JsonNodeBuilder nodeBuilder = iv.execute(user1.getName(),
				new HashMap<String, String>() {{
                    put("eventName", event1.getTitle());
                    put("date", Command.DATE_FORMAT.format(event1.getAppointments().iterator().next().getTimestamp()));
                    put("user", user0.getName());
                }}
		);

		assertNotNull("Sollte nicht null zurückgeben.", nodeBuilder);

		assertEquals("Sollte ok zurueckgeben", "Okay!", nodeBuilder.build().getText());

        // notification ueberpruefen
        assertEquals("Notification sollte an den richtigen User gerichtet sein.", user0.getName(),
                notification.getRecipients().iterator().next().getName());

        // event ueberpruefen
        assertEquals("Event sollte das gleiche sein", event1.getTitle(), event.getTitle());


	}

}
