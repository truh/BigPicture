package kehd.bigpicture.logic.commands.appointment;

import argo.jdom.JsonArrayNodeBuilder;
import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.NoSuchElement;
import kehd.bigpicture.exceptions.NotAuthentificated;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.model.Appointment;
import kehd.bigpicture.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.util.Map;

import static argo.jdom.JsonNodeBuilders.*;


/**
 * The Class GetAppointments.
 */
public class GetAppointments implements Command {
    private EntityManagerFactory entityManagerFactory;

    public GetAppointments(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /* (non-Javadoc)
     * @see kehd.bigpicture.logic.commands.Command#execute(java.lang.String, java.util.Map)
     */
    @Override
    public JsonNodeBuilder execute(String username, Map<String, String> params)
            throws NotAuthentificated, NoSuchElement {
        if(username == null) {
            throw new NotAuthentificated();
        }

        EntityManager manager = entityManagerFactory.createEntityManager();

        User user = null;
        try {
            user = manager.createQuery(
                    "SELECT DISTINCT User " +
                            "FROM User " +
                            "WHERE User.name = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException nre) {}

        if(user == null) {
            throw new NoSuchElement("User");
        }

        JsonArrayNodeBuilder arrayNodeBuilder = anArrayBuilder();

        for(Appointment appointment: user.getAppointments()) {
            arrayNodeBuilder.withElement(
                    anObjectBuilder()
                            .withField("timestamp", aStringBuilder(DATE_FORMAT.format(appointment.getTimestamp())))
                            .withField("eventName", aStringBuilder(appointment.getEvent().getTitle()))
            );
        }

        return arrayNodeBuilder;
    }
}
