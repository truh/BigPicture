package kehd.bigpicture.logic.commands.events.getvotes;

import argo.jdom.JsonNode;
import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.FieldMissing;
import kehd.bigpicture.exceptions.NoSuchElement;
import kehd.bigpicture.logic.commands.events.GetVotes;
import kehd.bigpicture.mock.EntityManagerAdapter;
import kehd.bigpicture.mock.TypedQueryAdapter;
import kehd.bigpicture.model.Event;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;

import static kehd.bigpicture.mock.model.MockModels.event0;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class GetVotes_Test {

	@Test
	public void test() throws FieldMissing, NoSuchElement {
		EntityManagerFactory emf = mock(EntityManagerFactory.class);
		doReturn(new EntityManagerAdapter() {
			@Override
			public <T> TypedQuery<T> createQuery(String qlString,
					Class<T> resultClass) {
				TypedQueryAdapter query = new TypedQueryAdapter<Event>() {
                    @Override
                    public Event getSingleResult() {
                        return event0;
                    }
                };

				return query;
			}
		}).when(emf).createEntityManager();
		
		GetVotes hv = new GetVotes(emf);

		JsonNodeBuilder nodeBuilder = hv.execute("user0",
                new HashMap<String, String>(){{
                    put("eventName", "event0");
                }}
        );

		assertNotNull("Sollte nicht null zurückgeben.", nodeBuilder);

		List<JsonNode> nodes = nodeBuilder.build().getElements();

		assertSame("Laenge sollte wie gegeben sein.", 1, nodes.size());

		assertEquals("Nachricht sollte wie gegeben sein.", "user1", nodes.get(0).getStringValue("username"));

	}
}
