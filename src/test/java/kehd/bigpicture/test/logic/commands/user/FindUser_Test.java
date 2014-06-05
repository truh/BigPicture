package kehd.bigpicture.test.logic.commands.user;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import kehd.bigpicture.exceptions.NoSuchElement;
import kehd.bigpicture.logic.commands.events.GetComments;
import kehd.bigpicture.mock.EntityManagerAdapter;
import kehd.bigpicture.mock.TypedQueryAdapter;
import kehd.bigpicture.model.Comment;
import kehd.bigpicture.model.Event;
import kehd.bigpicture.model.User;

import org.junit.Test;

import argo.jdom.JsonNodeBuilder;
import argo.jdom.JsonNode;
import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.FieldMissing;
import kehd.bigpicture.exceptions.NoSuchElement;
import kehd.bigpicture.logic.commands.events.GetVotes;
import kehd.bigpicture.logic.commands.user.FindUser;
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
import static kehd.bigpicture.mock.model.MockModels.*;
import static kehd.bigpicture.mock.model.MockModels.comment0;
import static kehd.bigpicture.mock.model.MockModels.comment1;
import static kehd.bigpicture.mock.model.MockModels.event0;
import static kehd.bigpicture.mock.model.MockModels.list;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * The Class FindUser_Test.
 */
public class FindUser_Test {
    @Test
    public void test() throws NoSuchElement, FieldMissing {
    	EntityManagerFactory emf = mock(EntityManagerFactory.class);
		doReturn(new EntityManagerAdapter() {
			@Override
			public <T> TypedQuery<T> createQuery(String qlString,
					Class<T> resultClass) {
				TypedQueryAdapter query = new TypedQueryAdapter() {
					
					@Override
					public List<User> getResultList(){
						return list(user0,user1);
					}

				};

				return query;
			}
		}).when(emf).createEntityManager();
		
		FindUser fu = new FindUser(emf);
		
		
		JsonNodeBuilder nodeBuilder = fu.execute("user0",
				new HashMap<String, String>() {
					{
						put("userPattern", "er0");
					}
				});
		assertNotNull("Sollte nicht null zurückgeben.", nodeBuilder);

		List<JsonNode> nodes = nodeBuilder.build().getElements();

		assertSame("Laenge sollte wie gegeben sein.", 1, nodes.size());

		assertEquals("Nachricht sollte wie gegeben sein.", "user0", nodes
				.get(0).getStringValue("username"));

		
		
    }
}
