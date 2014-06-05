package kehd.bigpicture.test.logic.commands.events;

import argo.jdom.JsonNode;
import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.ParameterException;
import kehd.bigpicture.logic.commands.events.AddComment;
import kehd.bigpicture.logic.commands.events.GetVotes;
import kehd.bigpicture.mock.EntityManagerAdapter;
import kehd.bigpicture.mock.TypedQueryAdapter;
import kehd.bigpicture.model.Comment;
import kehd.bigpicture.model.Event;
import kehd.bigpicture.model.User;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kehd.bigpicture.mock.model.MockModels.comment0;
import static kehd.bigpicture.mock.model.MockModels.comment1;
import static kehd.bigpicture.mock.model.MockModels.event0;
import static kehd.bigpicture.mock.model.MockModels.list;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static kehd.bigpicture.mock.model.MockModels.*;
public class AddComment_Test {
	private Logger log = Logger.getLogger(AddComment_Test.class);
	public Map paramMap;
	public int x=0;
	
	@Test
	public void test() throws ParameterException {
		EntityManagerFactory emf = mock(EntityManagerFactory.class);
		doReturn(new EntityManagerAdapter() {
			@Override
			public <T> TypedQuery<T> createQuery(String qlString,
					Class<T> resultClass) {
				TypedQueryAdapter query=null;
				TypedQueryAdapter query2=null;
				if(x==0){
					query= new TypedQueryAdapter() {
					
					@Override
					public User getSingleResult() {
						x++;
						return user0;
					}
					
				};
				}else{
					query2= new TypedQueryAdapter() {
					@Override
					public Event getSingleResult(){
						return event0;
					}
					
				};
				}
				if(x==0){
				return query;
				}else{
					return query2;
				}
			}
		}).when(emf).createEntityManager();

		
		AddComment ac = new AddComment(emf);  

				
		JsonNodeBuilder nodeBuilder = ac.execute("user0",
                new HashMap<String, String>(){{
                    put("eventName", "event0");
                    put("content","contenttest");
                }}
        );
		
		
		assertNotNull("Sollte nicht null zurückgeben.", nodeBuilder);

		assertSame("Ueberpruefen ob text enthalten ist.", true, nodeBuilder.build().hasText());

	}
}
