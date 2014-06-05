package kehd.bigpicture.test.logic.commands.events;

import argo.jdom.JsonNode;
import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.DateInvalid;
import kehd.bigpicture.exceptions.NoSuchElement;
import kehd.bigpicture.exceptions.NotAuthentificated;
import kehd.bigpicture.exceptions.NotAuthorized;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.logic.commands.events.Invite;
import kehd.bigpicture.logic.commands.notifications.GetNotifications;
import kehd.bigpicture.mock.EntityManagerAdapter;
import kehd.bigpicture.model.Event;
import kehd.bigpicture.model.User;
import org.apache.log4j.Logger;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class Invite_Test {

	private Logger log = Logger.getLogger(Invite.class);
	public Integer executionCount = 0;
	public Map paramMap;

	@Test
	public void test() throws NoSuchElement, NotAuthentificated, NotAuthorized,
			DateInvalid {
		EntityManagerFactory emf = mock(EntityManagerFactory.class);
		doReturn(new EntityManagerAdapter() {
			@Override
			public <T> TypedQuery<T> createQuery(String qlString,
					Class<T> resultClass) {
				TypedQuery<T> query = mock(TypedQuery.class);

				ArrayList<Event> notifications = new ArrayList<Event>() {
					{
						Event e1 = new Event();
						e1.setId(1);
						e1.setTitle("Event1");

						Event e2 = new Event();
						e2.setId(2);
						e2.setTitle("Event2");

						ArrayList<User> recipients = new ArrayList<User>() {
							{
								User johnDoe = new User();
								johnDoe.setName("JohnDoe");
								add(johnDoe);

							}
						};
						e1.setUsers(recipients);

						// Notification n1 = new Notification();
						// n1.setId(1);
						// n1.setMessage("Message1");
						// n1.setEvent(e1);
						// n1.setTimestamp(new Date());
						// n1.setRecipients(recipients);
						// n1.setType(NotificationType.NEW_INVITATION);
						// add(n1);
						//
						// Notification n2 = new Notification();
						// n2.setId(2);
						// n2.setMessage("Message2");
						// n2.setEvent(e1);
						// n2.setTimestamp(new Date());
						// n2.setRecipients(recipients);
						// n2.setType(NotificationType.NEW_INVITATION);
						// add(n2);
						//
						// Notification n3 = new Notification();
						// n3.setId(3);
						// n3.setMessage("Message3");
						// n3.setEvent(e2);
						// n3.setTimestamp(new Date());
						// n3.setRecipients(recipients);
						// n3.setType(NotificationType.NEW_INVITATION);
						// add(n3);
						//
						// Notification n4 = new Notification();
						// n4.setId(4);
						// n4.setMessage("Message4");
						// n4.setEvent(e2);
						// n4.setTimestamp(new Date());
						// n4.setRecipients(new ArrayList<User>());
						// n4.setType(NotificationType.NEW_INVITATION);
						// add(n4);
					}
				};

				doReturn(notifications).when(query).getResultList();

				return query;
			}
		}).when(emf).createEntityManager();
		GetNotifications getNotifications = new GetNotifications(emf);
		Invite iv = new Invite(emf);

		JsonNodeBuilder nodeBuilder = iv.execute("JohnDoe",
				new HashMap<String, String>() 
				{
					{
						put("eventName", "Event1");
						put("date", Command.DATE_FORMAT.format(new Date()));
						put("user", "JohnDoe");
					}
				}
		);

		assertNotNull("Sollte nicht null zurückgeben.", nodeBuilder);

		List<JsonNode> nodes = nodeBuilder.build().getElements();

		assertSame("Laenge sollte wie gegeben sein.", 1, nodes.size());

		assertEquals("EventName", "Event1", nodes.get(0)
				.getNumberValue("event"));
		assertEquals("EventName", "Event2", nodes.get(1)
				.getNumberValue("event"));

		assertEquals("User heißt.", "JohnDoe",
				nodes.get(0).getStringValue("user"));

	}

}
