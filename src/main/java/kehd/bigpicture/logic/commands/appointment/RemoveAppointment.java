package kehd.bigpicture.logic.commands.appointment;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.*;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.model.Appointment;
import kehd.bigpicture.model.Event;
import kehd.bigpicture.model.Notification;
import kehd.bigpicture.model.NotificationType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import static argo.jdom.JsonNodeBuilders.aStringBuilder;


/**
 * The Class RemoveAppointment.
 */
public class RemoveAppointment implements Command {
    private EntityManagerFactory entityManagerFactory;

    public RemoveAppointment(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /* (non-Javadoc)
     * @see kehd.bigpicture.logic.commands.Command#execute(java.lang.String, java.util.Map)
     */
    @Override
    public JsonNodeBuilder execute(String username, Map<String, String> params)
            throws NotAuthentificated, FieldMissing, NoSuchElement, NotAuthorized, DateInvalid {
        if(username == null) {
            throw new NotAuthentificated();
        }

        // authentifizierung ueberpruefen
        if(username == null) {
            throw new NotAuthentificated();
        }

        // parameter ueberpruefen
        String timestamp = params.get("timestamp");
        if(timestamp == null) {
            throw new FieldMissing("timestamp");
        }
        Date date = null;
        try {
            date = DATE_FORMAT.parse(timestamp);
        } catch (ParseException e) {
            throw new DateInvalid(e);
        }

        String eventName = params.get("eventName");
        if(eventName == null) {
            throw new FieldMissing("eventName");
        }

        // transaction starten
        EntityManager manager = entityManagerFactory.createEntityManager();
        manager.getTransaction().begin();

        // event object holen
        Event event = manager.createQuery(
                "SELECT DISTINCT Event FROM Event " +
                        "WHERE Event.title = :eventName", Event.class)
                .setParameter("eventName", eventName)
                .getSingleResult();

        // event object ueberpruefen
        if (event == null) {
            manager.getTransaction().rollback();
            throw new NoSuchElement("Event");
        }

        // ueberpruefen: username == event.organisator
        if (event.getOrganisator().getName().equals(username)) {
            manager.getTransaction().rollback();
            throw new NotAuthorized("RemoveAppointment");
        }

        // Appointment finden
        Appointment appointment = manager.createQuery(
                "SELECT DISTINCT Appointment FROM Appointment " +
                        "WHERE Appointment.timestamp = :timestamp " +
                        "AND Appointment.event = :event", Appointment.class)
                .setParameter("timestamp", date)
                .setParameter("event", event)
                .getSingleResult();

        // Appointment entfernen
        event.getAppointments().remove(appointment);
        manager.remove(appointment);

        // notification erzeugen und persistieren
        Notification notification = new Notification();
        notification.setEvent(event);
        notification.setMessage("Termin am: " + timestamp + " von Event: " + eventName + "entfernt.");
        notification.setRecipients(event.getUsers());
        notification.setTimestamp(new Date());
        notification.setType(NotificationType.CHANGED_INVITATION);
        manager.persist(notification);

        manager.getTransaction().commit();

        return aStringBuilder("Okay!");
    }
}
