package kehd.bigpicture.test.logic.commands.appointment;

import argo.jdom.JsonNode;
import kehd.bigpicture.exceptions.NotAuthentificated;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.logic.commands.appointment.GetAppointments;
import kehd.bigpicture.mock.EntityManagerAdapter;
import kehd.bigpicture.mock.TypedQueryAdapter;
import kehd.bigpicture.model.User;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.HashMap;

import static kehd.bigpicture.mock.model.MockModels.user0;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * Created by jakob on 6/5/14.
 */
public class GetAppointments_Test {
    TypedQueryAdapter<User> userQuery;
    EntityManagerAdapter em;
    EntityManagerFactory emf;

    @Before
    public void before() {
        userQuery = mock(TypedQueryAdapter.class);
        doReturn(user0).when(userQuery).getSingleResult();
        doReturn(userQuery).when(userQuery).setParameter("username", user0.getName());

        em = new EntityManagerAdapter() {
            @Override
            public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
                return (TypedQuery<T>) userQuery;
            }
        };

        emf = mock(EntityManagerFactory.class);
        doReturn(em).when(emf).createEntityManager();
    }

    @Test
    public void test() throws NotAuthentificated {
        GetAppointments getAppointments = new GetAppointments(emf);
        JsonNode node = getAppointments.execute(user0.getName(), new HashMap<String, String>()).build();

        assertEquals("Datum sollte das gleiche sein.",
                Command.DATE_FORMAT.format(user0.getAppointments().iterator().next().getTimestamp()),
                node.getNode(0, "timestamp").getText());

        assertEquals("Datum sollte das gleiche sein.",
                user0.getAppointments().iterator().next().getEvent().getTitle(),
                node.getNode(0, "eventName").getText());
    }
}
