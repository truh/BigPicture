package kehd.bigpicture.test.logic;

import kehd.bigpicture.exceptions.NotAuthentificated;
import kehd.bigpicture.logic.Authentificator;
import kehd.bigpicture.mock.EntityManagerAdapter;
import kehd.bigpicture.mock.TypedQueryAdapter;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.Map;

import static kehd.bigpicture.mock.model.MockModels.password0;
import static kehd.bigpicture.mock.model.MockModels.user0;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class Authentificator_Test {

	private Logger log = Logger.getLogger(Authentificator_Test.class);
	public Integer executionCount = 0;
	public Map paramMap;

    TypedQueryAdapter query;
	@Test
	public void test() throws NotAuthentificated {
        query = mock(TypedQueryAdapter.class);
        doReturn(user0).when(query).getSingleResult();
        doReturn(query).when(query).setParameter("userName", "user0");

		EntityManagerAdapter em = new EntityManagerAdapter() {
            @Override
            public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
                return query;
            }
        };

        EntityManagerFactory entityManagerFactory = mock(EntityManagerFactory.class);
		doReturn(em).when(entityManagerFactory).createEntityManager();
		
		Authentificator ar = new Authentificator(entityManagerFactory);

        String userPass =  user0.getName() + ":" + password0;

        String base64 = "Basic " + new String(java.util.Base64.getEncoder().encode(userPass.getBytes()));

		String username = ar.authentificate(base64);

        assertEquals("Username sollte der gleiche sein.", user0.getName(), username);
		
	}

    @Test
    public void userDoesNotExist() throws NotAuthentificated {
        query = mock(TypedQueryAdapter.class);
        doReturn(null).when(query).getSingleResult();
        doReturn(query).when(query).setParameter("userName", "user0");

        EntityManagerAdapter em = new EntityManagerAdapter() {
            @Override
            public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
                return query;
            }
        };

        EntityManagerFactory entityManagerFactory = mock(EntityManagerFactory.class);
        doReturn(em).when(entityManagerFactory).createEntityManager();

        Authentificator ar = new Authentificator(entityManagerFactory);

        String userPass =  user0.getName() + ":" + password0;

        String base64 = "Basic " + new String(Base64.encodeBase64(userPass.getBytes()));

        boolean exceptionThrown =false;
        try {
            String username = ar.authentificate(base64);
        } catch (NotAuthentificated na) {
            exceptionThrown = true;
        }

        assertTrue("Sollte eine NotAuthentificated werfen.", exceptionThrown);
    }

}
