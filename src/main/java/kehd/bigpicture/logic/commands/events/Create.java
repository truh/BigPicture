package kehd.bigpicture.logic.commands.events;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.FieldMissing;
import kehd.bigpicture.exceptions.NotAuthentificated;
import kehd.bigpicture.exceptions.NotAuthorized;
import kehd.bigpicture.exceptions.UserDoesNotExist;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.model.Event;
import kehd.bigpicture.model.EventType;
import kehd.bigpicture.model.User;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Map;

import static argo.jdom.JsonNodeBuilders.aStringBuilder;

/**
 * The Class Create.
 */
public class Create implements Command {
    private static final Logger log = Logger.getLogger(Create.class);
    private EntityManagerFactory entityManagerFactory;

    /**
     * Instantiates a new creates the.
     *
     * @param entityManagerFactory the entity manager factory
     */
    public Create(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /* (non-Javadoc)
     * @see kehd.bigpicture.logic.commands.Command#execute(java.lang.String, java.util.Map)
     */
    @Override
    public JsonNodeBuilder execute(String username, Map<String, String> params)
            throws FieldMissing, NotAuthentificated, NotAuthorized, UserDoesNotExist {
        if(username == null) {
            throw new NotAuthorized("Create");
        }

        String eventName = params.get("eventName");
        String eventType = params.get("eventType");

        // Testen ob parameter null sind
        if(eventName == null) {
            throw new FieldMissing("EventName");
        }
        // np, faellt auf default typ MULTI zurueck
        //if(eventType == null) {
        //    throw new FieldMissing("EventType");
        //}
        if(username == null) {
            throw new NotAuthentificated();
        }

        EntityManager manager = entityManagerFactory.createEntityManager();

        Event event = new Event();
        event.setTitle(eventName);

        if(eventType.equals("single")) {
            event.setType(EventType.SINGLE);
        } else {
            event.setType(EventType.MULTI);
        }

        manager.getTransaction().begin();

        User organisator = manager.createQuery(
                "SELECT DISTINCT User " +
                        "FROM User " +
                        "JOIN User " +
                        "WHERE User.name = :userName", User.class)
                .setParameter("userName", username)
                .getSingleResult();

        if(organisator == null) {
            manager.getTransaction().rollback();
            log.error("User fehlt, sollte aber wirklich nicht fehlen.");
            throw new UserDoesNotExist();
        }

        event.setOrganisator(organisator);

        manager.persist(event);
        manager.getTransaction().commit();

        return aStringBuilder("Okay!");
    }
}
