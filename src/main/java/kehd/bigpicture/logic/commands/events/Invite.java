package kehd.bigpicture.logic.commands.events;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.*;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.model.Event;
import kehd.bigpicture.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Map;

import static argo.jdom.JsonNodeBuilders.aStringBuilder;

public class Invite implements Command {
    private EntityManagerFactory entityManagerFactory;

    public Invite(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public JsonNodeBuilder execute(String username, Map<String, String> params)
            throws NoSuchElement, NotAuthentificated, NotAuthorized, DateInvalid {
        // check if logged in
        if(username == null) {
            throw new NotAuthentificated();
        }

        // ueberpruefen ob parameter gesetzt sind
        String eventName = params.get("eventName");
        if(eventName == null) {
            throw new NoSuchElement("elementName");
        }

        String userName = params.get("user");
        if(userName == null) {
            throw new NoSuchElement("user");
        }

        EntityManager manager = entityManagerFactory.createEntityManager();

        manager.getTransaction().begin();

        Event event = manager.createQuery(
                "SELECT DISTINCT Event FROM Event " +
                        "WHERE Event.title = :eventName", Event.class)
                .setParameter("eventName", eventName)
                .getSingleResult();

        if(!event.getOrganisator().getName().equals(username)) {
            throw new NotAuthorized("Invite");
        }

        User user = manager.createQuery(
                "SELECT DISTINCT User FROM User " +
                        "WHERE User.name = :userName", User.class)
                .setParameter("userName", userName)
                .getSingleResult();

        if(user == null) {
            throw new UserDoesNotExist();
        }

        event.getUsers().add(user);
        manager.persist(event);

        manager.getTransaction().commit();

        return aStringBuilder("Okay!");
    }
}
