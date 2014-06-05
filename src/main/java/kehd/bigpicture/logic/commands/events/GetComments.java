package kehd.bigpicture.logic.commands.events;

import argo.jdom.JsonArrayNodeBuilder;
import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.NoSuchElement;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.model.Comment;
import kehd.bigpicture.model.Event;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Map;

import static argo.jdom.JsonNodeBuilders.aStringBuilder;
import static argo.jdom.JsonNodeBuilders.anArrayBuilder;
import static argo.jdom.JsonNodeBuilders.anObjectBuilder;

public class GetComments implements Command {
    private EntityManagerFactory entityManagerFactory;

    public GetComments(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public JsonNodeBuilder execute(String username, Map<String, String> params)
            throws NoSuchElement {
        String eventName = params.get("eventName");

        EntityManager manager = entityManagerFactory.createEntityManager();

        // check if event exists
        Event event = manager.createQuery(
                "SELECT DISTINCT Event " +
                        "FROM Event " +
                        "WHERE Event.title = :eventName", Event.class)
                .setParameter("eventName", eventName)
                .getSingleResult();

        if(event == null) {
            throw new NoSuchElement("Event");
        }

        List<Comment> comments = manager.createQuery(
                "SELECT Comment " +
                        "FROM Comment " +
                        "WHERE Comment.event = :event ", Comment.class)
                .setParameter("event", event)
                .getResultList();

        JsonArrayNodeBuilder arrayNodeBuilder = anArrayBuilder();
        for(Comment comment: comments) {
            arrayNodeBuilder.withElement(
                    anObjectBuilder()
                            .withField("timestamp", aStringBuilder(DATE_FORMAT.format(comment.getTimestamp())))
                            .withField("comment", aStringBuilder(comment.getComment()))
                            .withField("author", aStringBuilder(comment.getAuthor().getName()))
            );
        }

        return arrayNodeBuilder;
    }
}
