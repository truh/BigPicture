package kehd.bigpicture.logic.commands.events;

import argo.jdom.JsonArrayNodeBuilder;
import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.model.Appointment;
import kehd.bigpicture.model.Event;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.Map;

import static argo.jdom.JsonNodeBuilders.*;

/**
 * Params:
 * None
 *
 * Result:
 * [Event
 *  {title
 *   owner: User.name
 *   [Appointment
 *    {
 *     date(rfc 3339 datetime)
 *    }]
 *   [Participant
 *    {username: User.name
 *     date(rfc 3339 datetime)
 *    }]
 *   [Comment
 *    {timestamp: date(rfc 3339 datetime)
 *     author: User.name
 *     content
 *    }]
 *  }]
 */
public class GetEvents implements Command {
    private EntityManagerFactory entityManagerFactory;

    public GetEvents(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private JsonNodeBuilder appointments(Collection<Appointment> appointments) {
        JsonArrayNodeBuilder nodeBuilder = anArrayBuilder();
        for(Appointment appointment: appointments) {
            nodeBuilder.withElement(aStringBuilder(
                    DATE_FORMAT.format(appointment.getTimestamp())));
        }
        return nodeBuilder;
    }

    @Override
    public JsonNodeBuilder execute(Map<String, String> params) {

        EntityManager manager = entityManagerFactory.createEntityManager();

        TypedQuery<Event> query = manager.createQuery("SELECT Event FROM Event", Event.class);

        JsonArrayNodeBuilder nodeBuilder = anArrayBuilder();
        for(Event event: query.getResultList()) {
            nodeBuilder.withElement(anObjectBuilder()
                    .withField("title", aStringBuilder(event.getTitle()))
                    .withField("owner", aStringBuilder(event.getOrganisator().getUser().getName()))
                    .withField("appointments", appointments(event.getAppointments()))
                    .withField("participants", anArrayBuilder()/* TODO
                                                                  Relation fehlt!? */)
                    .withField("comments", anArrayBuilder()/* TODO
                                                              Relation fehlt!? */)
            );
        }

        return nodeBuilder;
    }
}
