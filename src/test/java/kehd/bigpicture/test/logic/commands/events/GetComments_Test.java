package kehd.bigpicture.test.logic.commands.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import kehd.bigpicture.exceptions.FieldMissing;
import kehd.bigpicture.exceptions.NoSuchElement;
import kehd.bigpicture.logic.commands.events.GetComments;
import kehd.bigpicture.logic.commands.events.GetVotes;
import kehd.bigpicture.mock.EntityManagerAdapter;
import kehd.bigpicture.mock.TypedQueryAdapter;
import kehd.bigpicture.model.*;

import static kehd.bigpicture.mock.model.MockModels.event0;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import static kehd.bigpicture.mock.model.MockModels.*;

import argo.jdom.JsonNode;
import argo.jdom.JsonNodeBuilder;

public class GetComments_Test {

	@Test
	public void test() throws NoSuchElement, FieldMissing {
		EntityManagerFactory emf = mock(EntityManagerFactory.class);
		doReturn(new EntityManagerAdapter() {
			@Override
			public <T> TypedQuery<T> createQuery(String qlString,
					Class<T> resultClass) {
				TypedQueryAdapter query = new TypedQueryAdapter() {
					@Override
					public List<Comment> getResultList() {
						return list(comment0, comment1);
					}
					@Override
					public Event getSingleResult(){
						return event0;
					}

				};

				return query;
			}
		}).when(emf).createEntityManager();

		GetComments hv = new GetComments(emf);

		JsonNodeBuilder nodeBuilder = hv.execute("user0",
				new HashMap<String, String>() {
					{
						put("eventName", "event0");
					}
				});

		assertNotNull("Sollte nicht null zurückgeben.", nodeBuilder);

		List<JsonNode> nodes = nodeBuilder.build().getElements();

		assertSame("Laenge sollte wie gegeben sein.", 2, nodes.size());

		assertEquals("Nachricht sollte wie gegeben sein.", "user0", nodes
				.get(0).getStringValue("author"));
		
		assertEquals("Nachricht sollte wie gegeben sein.", "comment0", nodes
				.get(0).getStringValue("comment"));
		
	}

}
