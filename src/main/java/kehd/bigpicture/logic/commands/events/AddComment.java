package kehd.bigpicture.logic.commands.events;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.NoSuchElement;
import kehd.bigpicture.exceptions.NotAuthorized;
import kehd.bigpicture.exceptions.ParameterException;
import kehd.bigpicture.exceptions.UserDoesNotExist;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.model.Comment;
import kehd.bigpicture.model.Event;
import kehd.bigpicture.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.util.Date;
import java.util.Map;

import static argo.jdom.JsonNodeBuilders.aStringBuilder;

/**
 * Kommentar zu einem Event hinzufuegen
 */
public class AddComment implements Command {
    private EntityManagerFactory entityManagerFactory;

    public AddComment(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public JsonNodeBuilder execute(String username, Map<String, String> params)
            throws ParameterException {
        // Kommentare koennen nur hinzugefuegt werden wenn eingeloggt
        if(username == null) {
            throw new NotAuthorized("AddComment");
        }

        String eventName = params.get("eventName");
        String content = params.get("content");

        EntityManager manager = entityManagerFactory.createEntityManager();
        manager.getTransaction().begin();

        User user;
        try {
            user = manager.createQuery(
                    "SELECT DISTINCT User " +
                            "FROM User " +
                            "WHERE User.name = :userName", User.class)
                    .setParameter("userName", username)
                    .getSingleResult();
        } catch (NoResultException noResultException) {
            manager.getTransaction().rollback();
            throw new UserDoesNotExist(noResultException);
        }

        Event event;
        try {
            event = manager.createQuery(
                    "SELECT DISTINCT Event " +
                            "FROM Event " +
                            "WHERE Event.title = :eventName", Event.class)
                    .setParameter("eventName", eventName)
                    .getSingleResult();
        } catch (NoResultException noResultException) {
            manager.getTransaction().rollback();
            throw new NoSuchElement("Event", noResultException);
        }

        // ueberpruefen ob user kommentieren darf
        boolean allowed = false;
        if(event.getOrganisator().getName().equals(username)) {
            allowed = true;
        } else {
            for(User u: event.getUsers()) {
                if(u.getName().equals(username)) {
                    allowed = true;
                    break;
                }
            }
        }

        if(!allowed) {
            manager.getTransaction().rollback();
            throw new NotAuthorized("AddComment");
        } else {
            Comment comment = new Comment();
            comment.setTimestamp(new Date());
            comment.setComment(content);
            comment.setAuthor(user);
            comment.setEvent(event);

            manager.persist(comment);

            manager.getTransaction().commit();

            return aStringBuilder("Okay!");
        }
    }
}
