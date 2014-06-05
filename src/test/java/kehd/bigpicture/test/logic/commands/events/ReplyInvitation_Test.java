package kehd.bigpicture.test.logic.commands.events;

import kehd.bigpicture.exceptions.FieldMissing;
import static kehd.bigpicture.mock.model.MockModels.*;
import kehd.bigpicture.exceptions.DateInvalid;
import kehd.bigpicture.exceptions.NoSuchElement;
import kehd.bigpicture.exceptions.NotAuthentificated;
import kehd.bigpicture.exceptions.NotAuthorized;
import kehd.bigpicture.logic.commands.events.*;
import kehd.bigpicture.mock.EntityManagerAdapter;
import kehd.bigpicture.mock.TypedQueryAdapter;
import kehd.bigpicture.model.*;

import org.apache.log4j.Logger;
import org.junit.Test;

import argo.jdom.JsonNodeBuilder;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import kehd.bigpicture.logic.commands.Command;

import static kehd.bigpicture.mock.model.MockModels.event1;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static kehd.bigpicture.mock.model.MockModels.list;
public class ReplyInvitation_Test {
	 private Logger log = Logger.getLogger(ReplyInvitation.class);
	    public Integer executionCount = 0;
	    public Map paramMap;
	    private EntityManagerFactory entityManagerFactory;
	    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

    @Test
    public void test() throws FieldMissing, NotAuthentificated, NotAuthorized, NoSuchElement, DateInvalid, ParseException {
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
						return event0;
					}

				};

				return query;
			}
		}).when(emf).createEntityManager();

		ReplyInvitation ri = new ReplyInvitation(emf);
		
			System.out.println(new Date());
		JsonNodeBuilder nodeBuilder = ri.execute("user0",
				new HashMap<String, String>() {
					{
						put("eventName", "event0");
						put("accept","true");
						put("date","2004-02-12'T'22:22:22XXX");
					}
				});
    }

}
