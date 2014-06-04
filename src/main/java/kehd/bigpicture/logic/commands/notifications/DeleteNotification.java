package kehd.bigpicture.logic.commands.notifications;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.FieldMissing;
import kehd.bigpicture.exceptions.NoSuchElement;
import kehd.bigpicture.exceptions.NotANumber;
import kehd.bigpicture.exceptions.NotAuthentificated;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.model.Notification;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Map;

import static argo.jdom.JsonNodeBuilders.aStringBuilder;

/**
 *
 */
public class DeleteNotification implements Command {
    private EntityManagerFactory entityManagerFactory;

    public DeleteNotification(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public JsonNodeBuilder execute(String username, Map<String, String> params)
            throws NotAuthentificated, FieldMissing, NotANumber, NoSuchElement {
        if(username == null) {
            throw new NotAuthentificated();
        }

        String notificationIdString = params.get("notificationId");
        if(notificationIdString == null) {
            throw new FieldMissing("notificationId");
        }
        long notificationId;
        try {
            notificationId = Long.parseLong(notificationIdString);
        } catch (NumberFormatException nfe) {
            throw new NotANumber(nfe);
        }

        EntityManager manager = entityManagerFactory.createEntityManager();

        manager.getTransaction().begin();

        Notification notification = manager.createQuery(
                "SELECT DISTINCT Notification FROM Notification " +
                        "WHERE Notification.id = :notificationId", Notification.class)
                .setParameter("notificationId", notificationId).getSingleResult();

        if(notification == null) {
            throw new NoSuchElement("Notification");
        }

        manager.remove(notification);

        manager.getTransaction().commit();

        return aStringBuilder("Okay!");
    }
}
