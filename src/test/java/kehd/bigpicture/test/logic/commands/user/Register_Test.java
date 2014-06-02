package kehd.bigpicture.test.logic.commands.user;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.ParameterException;
import kehd.bigpicture.exceptions.UserAlreadyExists;
import kehd.bigpicture.logic.commands.user.Register;
import kehd.bigpicture.model.User;
import kehd.bigpicture.mock.EntityManagerAdapter;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class Register_Test {
    private static final Logger log = Logger.getLogger(Register_Test.class);

    String username = "username";
    String password = "password";

    @Test
    public void correctExecution() throws UserAlreadyExists {
        User user = null;
        EntityManager em = mock(EntityManager.class);
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                User user1 = (User) invocation.getArguments()[0];
                assertEquals("username should be the same", username, user1.getName());
                assertTrue("Password hash should match", BCrypt.checkpw(password, user1.getPassword()));
                return null;
            }
        }).when(em).persist(user);

        EntityTransaction entityTransaction = mock(EntityTransaction.class);
        when(em.getTransaction()).thenReturn(entityTransaction);

        EntityManagerFactory emf = mock(EntityManagerFactory.class);
        when(emf.createEntityManager())
                .thenReturn(em);

        Register register = new Register(emf);
        JsonNodeBuilder nodeBuilder = register.execute(new HashMap<String, String>() {{
            put("username", username);
            put("password", password);
        }});

        //log.info(Executor.JSON_FORMATTER.format(nodeBuilder.build().getRootNode()));

        String result = nodeBuilder.build().getText();

        assertEquals("Sollte Okay! zurueckgeben.", "Okay!", result);
    }

    @Test
    public void userAlreadyExists() {
        User user = new User();
        EntityManager em = new EntityManagerAdapter() {
            @Override
            public void persist(Object entity) {
                throw new EntityExistsException();
            }
        };

        EntityManagerFactory emf = mock(EntityManagerFactory.class);
        when(emf.createEntityManager())
                .thenReturn(em);

        Register register = new Register(emf);

        boolean exceptionHappened = false;

        try {
            JsonNodeBuilder nodeBuilder = register.execute(new HashMap<String, String>() {{
                put("username", username);
                put("password", password);
            }});
        } catch (ParameterException pe) {
            exceptionHappened = true;
        }
        assertTrue("Sollte eine Exception geworfen haben." , exceptionHappened);
    }
}
