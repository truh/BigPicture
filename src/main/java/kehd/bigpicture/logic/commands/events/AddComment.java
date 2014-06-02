package kehd.bigpicture.logic.commands.events;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.NoSuchElement;
import kehd.bigpicture.exceptions.ParameterException;
import kehd.bigpicture.exceptions.UserDoesNotExist;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.model.Comment;
import kehd.bigpicture.model.Event;
import kehd.bigpicture.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.util.Map;

import static argo.jdom.JsonNodeBuilders.aStringBuilder;

public class AddComment implements Command {
    private EntityManagerFactory entityManagerFactory;

    public AddComment(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public JsonNodeBuilder execute(Map<String, String> params) throws ParameterException {
        String eventName = params.get("eventName");
        String content = params.get("content");
        String userName = params.get("userName");

        EntityManager manager = entityManagerFactory.createEntityManager();
        manager.getTransaction().begin();

        User user;
        try {
            user = manager.createQuery(
                    "SELECT DISTINCT User " +
                            "FROM User " +
                            "WHERE User.name = :userName", User.class)
                    .setParameter("userName", userName)
                    .getSingleResult();


        } catch (NoResultException noResultException) {
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
            throw new NoSuchElement("Event", noResultException);
        }

        Comment comment = new Comment();
        comment.setComment(content);
        comment.setUser(user);
        comment.setEvent(event);

        manager.persist(comment);

        manager.getTransaction().commit();

        return aStringBuilder("Okay!");
    }
}
