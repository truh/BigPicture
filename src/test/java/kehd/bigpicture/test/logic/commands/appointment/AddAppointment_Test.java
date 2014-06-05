package kehd.bigpicture.test.logic.commands.appointment;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.FieldMissing;
import kehd.bigpicture.exceptions.NoSuchElement;
import kehd.bigpicture.exceptions.NotAuthentificated;
import kehd.bigpicture.exceptions.NotAuthorized;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.logic.commands.appointment.AddAppointment;
import kehd.bigpicture.mock.EntityManagerAdapter;
import kehd.bigpicture.mock.TypedQueryAdapter;
import kehd.bigpicture.model.Event;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.HashMap;

import static kehd.bigpicture.mock.model.MockModels.event0;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class AddAppointment_Test {
    TypedQueryAdapter<Event> eventQuery;
    EntityManagerAdapter em;
    EntityManagerFactory emf;

    Date now = new Date();

    @Before
    public void before() {
        eventQuery = mock(TypedQueryAdapter.class);
        doReturn(event0).when(eventQuery).getSingleResult();
        doReturn(eventQuery).when(eventQuery).setParameter("eventName", event0.getTitle());

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
    public void test() throws NoSuchElement, NotAuthentificated, FieldMissing, NotAuthorized {
        AddAppointment addAppointment = new AddAppointment(emf);
        JsonNodeBuilder nodeBuilder = addAppointment.execute(event0.getOrganisator().getName(),
                new HashMap<String, String>() {{
            put("timestamp", Command.DATE_FORMAT.format(now));
            put("eventName", event0.getTitle());
        }});

        assertEquals("Sollte Okay! sein", "Okay!", nodeBuilder.build().getText());
    }
}
