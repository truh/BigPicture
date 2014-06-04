package kehd.bigpicture.logic.commands.events;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.FieldMissing;
import kehd.bigpicture.exceptions.NotAuthentificated;
import kehd.bigpicture.exceptions.NotAuthorized;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.model.Event;
import kehd.bigpicture.model.EventType;
import kehd.bigpicture.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Map;

import static argo.jdom.JsonNodeBuilders.aStringBuilder;

public class Create implements Command {
    private EntityManagerFactory entityManagerFactory;

    public Create(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public JsonNodeBuilder execute(String username, Map<String, String> params)
            throws FieldMissing, NotAuthentificated, NotAuthorized {
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

        User organisator = manager.createQuery(
                "SELECT DISTINCT User " +
                        "FROM User " +
                        "JOIN User " +
                        "WHERE User.name = :userName", User.class)
                .setParameter("userName", username)
                .getSingleResult();

        event.setOrganisator(organisator);

        return aStringBuilder("Okay!");
    }
}
