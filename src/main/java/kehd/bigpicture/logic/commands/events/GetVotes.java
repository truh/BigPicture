package kehd.bigpicture.logic.commands.events;

import argo.jdom.JsonArrayNodeBuilder;
import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.FieldMissing;
import kehd.bigpicture.exceptions.NoSuchElement;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.model.Appointment;
import kehd.bigpicture.model.Event;
import kehd.bigpicture.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Map;

import static argo.jdom.JsonNodeBuilders.*;

public class GetVotes implements Command {
    private EntityManagerFactory entityManagerFactory;

    public GetVotes(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public JsonNodeBuilder execute(String username, Map<String, String> params)
            throws FieldMissing, NoSuchElement {
        String eventName = params.get("eventName");
        if(eventName == null) {
            throw new FieldMissing("eventName");
        }

        EntityManager manager = entityManagerFactory.createEntityManager();

        Event event = manager.createQuery(
                "SELECT DISTINCT Event " +
                        "FROM Event WHERE " +
                        "Event.title = :eventName", Event.class)
                .setParameter("eventName", eventName)
                .getSingleResult();

        if(event == null) {
            throw new NoSuchElement("Event");
        }

        JsonArrayNodeBuilder arrayNodeBuilder = anArrayBuilder();

        for(User user: event.getUsers()) {
            // heraussuchen des richtigen Appointment fuer den aktuellen User
            Appointment appointment = null;
            for(Appointment appointment1: user.getAppointments()) {
                if(appointment1 != null) {
                    if(appointment1.getEvent().getId() == event.getId()) {
                        appointment = appointment1;
                        break;
                    }
                }
            }

            arrayNodeBuilder.withElement(
                    anObjectBuilder()
                            .withField("username", aStringBuilder(user.getName()))
                            .withField("appointment", appointment == null
                                    ?aNullBuilder() // user hat kein appointment fuer dieses event
                                    :aStringBuilder(DATE_FORMAT.format(appointment.getTimestamp())))
            );
        }

        return arrayNodeBuilder;
    }
}
