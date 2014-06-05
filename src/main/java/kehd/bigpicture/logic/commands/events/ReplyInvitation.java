package kehd.bigpicture.logic.commands.events;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.*;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import static argo.jdom.JsonNodeBuilders.aStringBuilder;

/**
 * The Class ReplyInvitation.
 */
public class ReplyInvitation implements Command {
    private EntityManagerFactory entityManagerFactory;

    /**
     * Instantiates a new reply invitation.
     *
     * @param entityManagerFactory the entity manager factory
     */
    public ReplyInvitation(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    // TODO ueberlegen ob aender damit geht
    /* (non-Javadoc)
     * @see kehd.bigpicture.logic.commands.Command#execute(java.lang.String, java.util.Map)
     */
    @Override
    public JsonNodeBuilder execute(String username, Map<String, String> params)
            throws NotAuthentificated, FieldMissing, DateInvalid, NoSuchElement {
        if(username == null) {
            throw new NotAuthentificated();
        }

        String eventName = params.get("eventName");
        if(eventName == null) {
            throw new FieldMissing("eventName");
        }

        String acceptString = params.get("accept").toLowerCase();
        boolean accept = acceptString==null
                ?true
                :acceptString.equals("true");

        String dateString = params.get("date");
        if(accept && dateString == null) {
            throw new FieldMissing("date");
        }

        // uebepruefen ob Date gueltig
        Date date;
        try {
            date = DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            throw new DateInvalid();
        }

        EntityManager manager = entityManagerFactory.createEntityManager();

        manager.getTransaction().begin();

        Event event = null;
        try {
            event = manager.createQuery(
                    "SELECT Event FROM Event " +
                        "WHERE Event.title = :eventName", Event.class)
                    .setParameter("eventName", eventName)
                    .getSingleResult();
        } catch (NoResultException nre) {}

        if(event == null) {
            manager.getTransaction().rollback();
            throw new NoSuchElement("Event");
        }

        // richtiges appointment suchen
        Appointment appointment = null;
        for(Appointment appointment1: event.getAppointments()) {
        	
            if(appointment1.getTimestamp().equals(date)) {
                appointment = appointment1;
                break;
            }
        }
        // ueberpruefen ob appointment existiert
        if(appointment == null) {
            manager.getTransaction().rollback();
            throw new NoSuchElement("Appointment");
        }

        User user = null;
        try {
        user = manager.createQuery(
                "SELECT User FROM User " +
                        "WHERE User.name = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
        } catch (NoResultException nre) {}

        if(user == null) {
            manager.getTransaction().rollback();
            throw new UserDoesNotExist();
        }

        // ueberpruefen ob user teil des events ist
        // user soll termin nur annehmen wenn er eingeladen wurde
        boolean userInEvent = false;
        for(User user1: event.getUsers()) {
            if(user.getId() == user1.getId()) {
                userInEvent = true;
                break;
            }
        }
        if(!userInEvent) {
            manager.getTransaction().rollback();
            throw new NoSuchElement("userInEvent");
        }

        user.getAppointments().add(appointment);
        manager.persist(user);

        // ueberpruefen ob alle benutzer ein appointment haben
        int usersNotVoted = event.getUsers().size();
        for(User user1: event.getUsers()) {
            for(Appointment appointment1: user1.getAppointments()) {
                if(appointment1.getEvent().getId() == event.getId()) {
                    usersNotVoted --;
                    break;
                }
            }
        }
        // wenn( ja ) -> Organisator benachrichtigen
        if(usersNotVoted == 0) {
            User organisator = event.getOrganisator();
            Notification notification = new Notification();
            notification.setEvent(event);
            notification.setMessage("Alle Teilnehmer haben einen Termin gewaehlt.");
            ArrayList<User> recipients = new ArrayList<>();
            recipients.add(organisator);
            notification.setRecipients(recipients);
            notification.setTimestamp(new Date());
            notification.setType(NotificationType.ALL_USERS_HAVE_CHOSEN);

            manager.persist(notification);
        }

        manager.getTransaction().commit();

        return aStringBuilder("Okay!");
    }
}
