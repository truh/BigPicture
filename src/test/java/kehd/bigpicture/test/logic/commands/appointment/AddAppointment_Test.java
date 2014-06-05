package kehd.bigpicture.test.logic.commands.appointment;

import kehd.bigpicture.mock.EntityManagerAdapter;
import kehd.bigpicture.mock.TypedQueryAdapter;
import kehd.bigpicture.model.Event;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import static kehd.bigpicture.mock.model.MockModels.*;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class AddAppointment_Test {
    TypedQueryAdapter<Event> eventQuery;
    EntityManagerAdapter em;
    EntityManagerFactory emf;

    @Before
    public void before() {
        eventQuery = mock(TypedQueryAdapter.class);
        doReturn(event0).when(eventQuery).getSingleResult();
        doReturn(eventQuery).when(eventQuery).setParameter("username", user0.getName());

        em = new EntityManagerAdapter() {
            @Override
            public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
                return (TypedQuery<T>) eventQuery;
            }
        };

        emf = mock(EntityManagerFactory.class);
        doReturn(em).when(emf).createEntityManager();
    }

    @Test
    public void test() {

        fail("Not implemented");
    }
}
