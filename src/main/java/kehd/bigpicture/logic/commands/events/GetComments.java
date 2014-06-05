package kehd.bigpicture.logic.commands.events;

import argo.jdom.JsonArrayNodeBuilder;
import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.NoSuchElement;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.model.Comment;
import kehd.bigpicture.model.Event;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Map;

import static argo.jdom.JsonNodeBuilders.*;

/**
 * The Class GetComments.
 */
public class GetComments implements Command {
    private EntityManagerFactory entityManagerFactory;

    /**
     * Instantiates a new gets the comments.
     *
     * @param entityManagerFactory the entity manager factory
     */
    public GetComments(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /* (non-Javadoc)
     * @see kehd.bigpicture.logic.commands.Command#execute(java.lang.String, java.util.Map)
     */
    @Override
    public JsonNodeBuilder execute(String username, Map<String, String> params)
            throws NoSuchElement {
        String eventName = params.get("eventName");

        EntityManager manager = entityManagerFactory.createEntityManager();

        // check if event exists
        Event event = null;
        try {
            event = manager.createQuery(
                    "SELECT DISTINCT Event " +
                            "FROM Event " +
                            "WHERE Event.title = :eventName", Event.class)
                    .setParameter("eventName", eventName)
                    .getSingleResult();
        } catch (NoResultException nre) {}
       
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
