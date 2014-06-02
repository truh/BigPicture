package kehd.bigpicture.logic.commands.events;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.Main;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.model.Comment;
import kehd.bigpicture.model.Event;
import kehd.bigpicture.model.User;

import javax.persistence.EntityManager;
import java.util.Map;

public class AddComment implements Command {

    @Override
    public JsonNodeBuilder execute(Map<String, String> params) {
        String eventName = params.get("eventName");
        // TODO Comment title aus Planung entfernen
        //String title = params.get("title");
        String content = params.get("content");
        String userName = params.get("userName");

        EntityManager manager = Main.getEntityManagerFactory().createEntityManager();
        manager.getTransaction().begin();

        User user = manager.createQuery(
                "SELECT DISTINCT User " +
                        "FROM User " +
                        "WHERE User.name = :userName", User.class)
                .setParameter("userName", userName)
                .getSingleResult();

        Comment comment = new Comment();
        comment.setComment(content);
        comment.setUser(user);

        Event event = manager.createQuery(
                "SELECT DISTINCT Event " +
                        "FROM Event " +
                        "WHERE Event.title = :eventName", Event.class)
                .setParameter("eventName", eventName)
                .getSingleResult();

        //event
        // TODO Beziehung zwischen Comment & Event

        return null;
    }
}
