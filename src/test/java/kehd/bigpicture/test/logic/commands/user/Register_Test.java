package kehd.bigpicture.test.logic.commands.user;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.ParameterException;
import kehd.bigpicture.exceptions.UserAlreadyExists;
import kehd.bigpicture.logic.commands.user.Register;
import kehd.bigpicture.mock.EntityManagerAdapter;
import kehd.bigpicture.mock.TypedQueryAdapter;
import kehd.bigpicture.model.User;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class Register_Test {
    private static final Logger log = Logger.getLogger(Register_Test.class);

    String username = "username";
    String password = "password";

    TypedQuery<User> userQuery;

    @Before
    public void before() {

    }

    @Test
    public void correctExecution() throws UserAlreadyExists {
        userQuery = new TypedQueryAdapter<User>() {
            @Override
            public User getSingleResult() {
                return null;
            }
            @Override
            public TypedQuery setParameter(String field, Object o) {
                return this;
            }
        };

        EntityManager em = new EntityManagerAdapter() {
            @Override
            public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
                TypedQuery query = null;

                if(resultClass == User.class)
                    query = userQuery;
                return query;
            }
        };

        EntityManagerFactory emf = mock(EntityManagerFactory.class);
        doReturn(em).when(emf).createEntityManager();

        Register register = new Register(emf);
        JsonNodeBuilder nodeBuilder = register.execute("none", new HashMap<String, String>() {{
            put("username", username);
            put("password", password);
        }});

        //log.info(Executor.JSON_FORMATTER.format(nodeBuilder.build().getRootNode()));

        String result = nodeBuilder.build().getText();

        assertEquals("Sollte Okay! zurueckgeben.", "Okay!", result);
    }

    @Test
    public void userAlreadyExists() {
        userQuery = new TypedQueryAdapter<User>() {
            @Override
            public User getSingleResult() {
                User user = new User();
                user.setName("username");
                return user;
            }
            @Override
            public TypedQuery setParameter(String field, Object o) {
                return this;
            }
        };

        User user = new User();

        EntityManager em = new EntityManagerAdapter() {
            @Override
            public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
                TypedQuery query = null;

                if(resultClass == User.class)
                    query = userQuery;
                return query;
            }
        };

        EntityManagerFactory emf = mock(EntityManagerFactory.class);
        when(emf.createEntityManager())
                .thenReturn(em);

        Register register = new Register(emf);

        boolean exceptionHappened = false;

        try {
            JsonNodeBuilder nodeBuilder = register.execute("none", new HashMap<String, String>() {{
                put("username", username);
                put("password", password);
            }});
        } catch (ParameterException pe) {
            exceptionHappened = true;
        }
        assertTrue("Sollte eine Exception geworfen haben." , exceptionHappened);
    }
}
