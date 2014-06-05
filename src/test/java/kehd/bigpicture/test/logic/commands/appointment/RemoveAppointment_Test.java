package kehd.bigpicture.test.logic.commands.appointment;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.*;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.logic.commands.appointment.RemoveAppointment;
import kehd.bigpicture.mock.EntityManagerAdapter;
import kehd.bigpicture.mock.TypedQueryAdapter;
import kehd.bigpicture.model.Appointment;
import kehd.bigpicture.model.Event;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.HashMap;

import static kehd.bigpicture.mock.model.MockModels.appointment0;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class RemoveAppointment_Test {
    TypedQueryAdapter<Event> eventQuery;
    TypedQueryAdapter<Appointment> appointmentQuery;
    EntityManagerAdapter em;
    EntityManagerFactory emf;

    Date now = new Date();

    @Before
    public void before() {
        eventQuery = mock(TypedQueryAdapter.class);
        doReturn(appointment0.getEvent()).when(eventQuery).getSingleResult();
        doReturn(eventQuery).when(eventQuery).setParameter("eventName", appointment0.getEvent().getTitle());

        appointmentQuery = new TypedQueryAdapter<Appointment>() {
            @Override
            public Appointment getSingleResult() {
                return appointment0;
            }
        };

        em = new EntityManagerAdapter() {
            @Override
            public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
                if(resultClass == Event.class) {
                    return (TypedQuery<T>) eventQuery;
                }
                if(resultClass == Appointment.class) {
                    return (TypedQuery<T>) appointmentQuery;
                }
                throw new RuntimeException("NOPE");
            }
        };

        emf = mock(EntityManagerFactory.class);
        doReturn(em).when(emf).createEntityManager();
    }

    @Test
    public void test() throws NoSuchElement, NotAuthentificated, FieldMissing, DateInvalid, NotAuthorized {
        RemoveAppointment removeAppointment = new RemoveAppointment(emf);
        JsonNodeBuilder nodeBuilder = removeAppointment.execute(appointment0.getEvent().getOrganisator().getName(),
                new HashMap<String, String>() {{
                    put("timestamp", Command.DATE_FORMAT.format(appointment0.getTimestamp()));
                    put("eventName", appointment0.getEvent().getTitle());
                }}
        );

        assertEquals("Alles sollte 'Okay!' sein.", "Okay!", nodeBuilder.build().getText());
    }
}
