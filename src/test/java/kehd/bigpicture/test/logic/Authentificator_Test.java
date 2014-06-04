package kehd.bigpicture.test.logic;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javassist.bytecode.ByteArray;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import kehd.bigpicture.exceptions.NotAuthentificated;
import kehd.bigpicture.logic.*;
import kehd.bigpicture.mock.EntityManagerAdapter;
import kehd.bigpicture.model.Event;
import kehd.bigpicture.model.Notification;
import kehd.bigpicture.model.User;
import kehd.bigpicture.test.logic.commands.events.AddComment_Test;


import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.junit.Test;

import argo.jdom.JsonNodeBuilder;

//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO
//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO
//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO
//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO
//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO//TODO

public class Authentificator_Test {

	 private Logger log = Logger.getLogger(Authentificator.class);
	    public Integer executionCount = 0;
	    public Map paramMap;
	    private EntityManagerFactory entityManagerFactory= mock(EntityManagerFactory.class);
	@Test
	public void test() throws NotAuthentificated {
		
		doReturn(new EntityManagerAdapter() {
			@Override
            public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
                TypedQuery<T> query = mock(TypedQuery.class);
                
                ArrayList<User> recipients = new ArrayList<User>() {{
                    User johnDoe = new User();
                    johnDoe.setName("JohnDoe");
                    johnDoe.setPassword("1234");
                    add(johnDoe);
                }};
                doReturn(recipients).when(query).getResultList();

                return query;
			}
		}
		).when(entityManagerFactory)
        .createEntityManager();
		
		Authentificator ar = new Authentificator(entityManagerFactory);
				
				
		String name = "JohnDoe:1234";
		byte[] byteArray = Base64.encodeBase64(name.getBytes());
		String ads=new String(byteArray);
		String xy = ar.authentificate("Basic "+ads);
		System.out.println(xy);
		
		byte[] by= Base64.decodeBase64(name.getBytes());
		
		assertEquals("ok",name , xy);
		
	}

}
