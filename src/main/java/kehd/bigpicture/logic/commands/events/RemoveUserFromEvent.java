package kehd.bigpicture.logic.commands.events;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.FieldMissing;
import kehd.bigpicture.exceptions.NoSuchElement;
import kehd.bigpicture.exceptions.NotAuthentificated;
import kehd.bigpicture.exceptions.NotAuthorized;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import static argo.jdom.JsonNodeBuilders.aStringBuilder;

/**
 *
 */
public class RemoveUserFromEvent implements Command {
    private EntityManagerFactory entityManagerFactory;

    public RemoveUserFromEvent(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public JsonNodeBuilder execute(String username, Map<String, String> params)
            throws FieldMissing, NotAuthentificated, NotAuthorized, NoSuchElement {
        if(username == null) {
            throw new NotAuthentificated();
        }
        String eventName = params.get("eventName");
        if(eventName == null) {
            throw new FieldMissing("eventName");
        }
        String targetUser = params.get("username");
        if(targetUser == null) {
            throw new FieldMissing("username");
        }

        EntityManager manager = entityManagerFactory.createEntityManager();

        manager.getTransaction().begin();

        Event event = manager.createQuery(
                "SELECT DISTINCT Event FROM Event " +
                        "WHERE Event.title = :eventName", Event.class)
                .setParameter("eventName", eventName)
                .getSingleResult();

        if(!event.getOrganisator().getName().equals(username)) {
            manager.getTransaction().rollback();
            throw new NotAuthorized("RemoveUserFromEvent");
        }

        // targetUser finden
        User targetUserObj = null;
        for(User user: event.getUsers()) {
            if(user.getName().equals(targetUser)) {
                targetUserObj = user;
            }
        }
        if(targetUserObj == null) {
            manager.getTransaction().rollback();
            throw new NoSuchElement("userInEvent");
        }

        // user aus event entfernen
        event.getUsers().remove(targetUserObj);
        manager.persist(event);

        // user aus appointment entfernen
        for(Appointment appointment: targetUserObj.getAppointments()) {
            if(appointment.getEvent().getId() == event.getId()) {
                targetUserObj.getAppointments().remove(appointment);
            }
        }
        manager.persist(targetUserObj);

        // notification senden
        Notification notification = new Notification();
        notification.setEvent(event);
        notification.setMessage("Sie wurden aus dem Event: " + event.getTitle() + " entfernt.");
        ArrayList<User> recipient = new ArrayList<User>();
        recipient.add(targetUserObj);
        notification.setRecipients(recipient);
        notification.setTimestamp(new Date());
        notification.setType(NotificationType.DEL_INVITATION);
        manager.persist(notification);

        manager.getTransaction().commit();

        return aStringBuilder("Okay!");
    }
}
