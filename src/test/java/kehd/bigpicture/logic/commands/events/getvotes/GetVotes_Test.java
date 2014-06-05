package kehd.bigpicture.logic.commands.events.getvotes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.management.Query;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import kehd.bigpicture.exceptions.FieldMissing;
import kehd.bigpicture.logic.commands.events.*;
import kehd.bigpicture.logic.commands.notifications.GetNotifications;
import kehd.bigpicture.mock.EntityManagerAdapter;
import kehd.bigpicture.model.Appointment;
import kehd.bigpicture.model.Event;
import kehd.bigpicture.model.Notification;
import kehd.bigpicture.model.NotificationType;
import kehd.bigpicture.model.User;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import argo.jdom.JsonNode;
import argo.jdom.JsonNodeBuilder;

public class GetVotes_Test {

	@Test
	public void test() throws FieldMissing {
		EntityManagerFactory emf = mock(EntityManagerFactory.class);
		doReturn(new EntityManagerAdapter() {
			@Override
			public <T> TypedQuery<T> createQuery(String qlString,
					Class<T> resultClass) {
				TypedQuery<T> query = mock(TypedQuery.class);

				ArrayList<User> notifications = new ArrayList<User>() {
					{
						User johnDoe = new User();
						johnDoe.setName("JohnDoe");

						User huah = new User();
						huah.setName("SLLE");
						add(huah);

						ArrayList<Event> recipients = new ArrayList<Event>() {
							{

								Event e1 = new Event();
								e1.setId(1);
								e1.setTitle("Event1");
								add(e1);

								Event e2 = new Event();
								e2.setId(2);
								e2.setTitle("Event2");
								add(e2);
							}
						};

						ArrayList<Appointment> appointments = new ArrayList<Appointment>() {
							{
								Appointment ap = new Appointment();
								ap.setEvent(new Event());
								ap.setTimestamp(new Date());
								add(ap);

							}
						};
						johnDoe.setAppointments(appointments);
						add(johnDoe);

					}
				};
				doReturn(notifications).when(query).getResultList();
				return query;

			}
		}).when(emf).createEntityManager();

		
		GetVotes hv = new GetVotes(emf);

		
		JsonNodeBuilder nodeBuilder = hv.execute("johnDoe",
				new HashMap<String, String>(){{
					put("eventName","Event1");
				}});

		assertNotNull("Sollte nicht null zurückgeben.", nodeBuilder);

		List<JsonNode> nodes = nodeBuilder.build().getElements();

		assertSame("Laenge sollte wie gegeben sein.", 3, nodes.size());

		assertEquals("Nachricht sollte wie gegeben sein.", "Message1", nodes
				.get(0).getStringValue("message"));
		assertEquals("Nachricht sollte wie gegeben sein.", "Message2", nodes
				.get(1).getStringValue("message"));
		assertEquals("Nachricht sollte wie gegeben sein.", "Message3", nodes
				.get(2).getStringValue("message"));

		assertEquals("Id sollte wie gegeben sein.", "1", nodes.get(0)
				.getNumberValue("id"));
		assertEquals("Id sollte wie gegeben sein.", "2", nodes.get(1)
				.getNumberValue("id"));
		assertEquals("Id sollte wie gegeben sein.", "3", nodes.get(2)
				.getNumberValue("id"));

	}
}
