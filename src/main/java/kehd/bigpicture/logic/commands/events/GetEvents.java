package kehd.bigpicture.logic.commands.events;

import argo.jdom.JsonArrayNodeBuilder;
import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.NotAuthentificated;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.model.Appointment;
import kehd.bigpicture.model.Event;
import kehd.bigpicture.model.User;

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
 *  }]
 */
public class GetEvents implements Command {
    private EntityManagerFactory entityManagerFactory;

    /**
     * Instantiates a new gets the events.
     *
     * @param entityManagerFactory the entity manager factory
     */
    public GetEvents(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * Appointments.
     *
     * @param appointments the appointments
     * @return the json node builder
     */
    private JsonNodeBuilder appointments(Collection<Appointment> appointments) {
        JsonArrayNodeBuilder nodeBuilder = anArrayBuilder();
        for(Appointment appointment: appointments) {
            nodeBuilder.withElement(aStringBuilder(
                    DATE_FORMAT.format(appointment.getTimestamp())));
        }
        return nodeBuilder;
    }

    /* (non-Javadoc)
     * @see kehd.bigpicture.logic.commands.Command#execute(java.lang.String, java.util.Map)
     */
    @Override
    public JsonNodeBuilder execute(String username, Map<String, String> params)
            throws NotAuthentificated {	
        if(username == null) {	
            throw new NotAuthentificated();	
        }	
        
        EntityManager manager = entityManagerFactory.createEntityManager();

        TypedQuery<Event> query = manager.createQuery("SELECT Event FROM Event", Event.class);

        JsonArrayNodeBuilder nodeBuilder = anArrayBuilder();
        for(Event event: query.getResultList()) {
            for(User user: event.getUsers()) {
                // Nur die events auflisten die etwas mit dem user zu tun haben
                if(user.getName().equals(username)
                        || event.getOrganisator().getName().equals(username)) {
                    JsonArrayNodeBuilder participantsBuilder = anArrayBuilder();
                    for(User participant: event.getUsers()) {
                        participantsBuilder
                                .withElement(anObjectBuilder()
                                        .withField("name", aStringBuilder(participant.getName()))
                                );
                    }
                    nodeBuilder.withElement(anObjectBuilder()
                            .withField("title", aStringBuilder(event.getTitle()))
                            .withField("owner", aStringBuilder(event.getOrganisator().getName()))
                            .withField("appointments", appointments(event.getAppointments()))
                            .withField("participants", participantsBuilder)
                    );
                    break;
                }
            }
        }

        return nodeBuilder;
    }
}
