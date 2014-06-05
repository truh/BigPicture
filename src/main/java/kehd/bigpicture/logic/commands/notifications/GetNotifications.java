package kehd.bigpicture.logic.commands.notifications;

import argo.jdom.JsonArrayNodeBuilder;
import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.NotAuthentificated;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.model.Notification;
import kehd.bigpicture.model.User;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static argo.jdom.JsonNodeBuilders.*;

/**
 * The Class GetNotifications.
 */
public class GetNotifications implements Command {
    private static final Logger log = Logger.getLogger(GetNotifications.class);

    private EntityManagerFactory entityManagerFactory;

    /**
     * Instantiates a new gets the notifications.
     *
     * @param entityManagerFactory the entity manager factory
     */
    public GetNotifications(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
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

        manager.getTransaction().begin();
        List<Notification> notiList = manager.createQuery (
                "SELECT Notification FROM Notification ", Notification.class)
                .getResultList();

        JsonArrayNodeBuilder arrayNodeBuilder = anArrayBuilder();
        for(Notification notification: notiList) {
            Collection<User> recipients = notification.getRecipients();
            if(recipients == null) {
                log.error("WHY!!?");
            } else {
                for(User user: recipients) {
                    if (user.getName().equals(username)) {
                        arrayNodeBuilder
                            .withElement(anObjectBuilder()
                                .withField("id", aNumberBuilder("" + notification.getId()))
                                .withField("message", aStringBuilder(notification.getMessage()))
                                .withField("timestamp", aStringBuilder(DATE_FORMAT.format(notification.getTimestamp())))
                                .withField("type", aStringBuilder(notification.getType().toString()))
                                .withField("event", anObjectBuilder()
                                    .withField("id", aNumberBuilder("" + notification.getEvent().getId()))
                                    .withField("title", aStringBuilder(notification.getEvent().getTitle())))
                            );
                        break;
                    }
                }
            }
        }

        return arrayNodeBuilder;
    }
}
