package kehd.bigpicture.logic.commands.events;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.model.Event;
import kehd.bigpicture.model.Organisator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Map;

public class Create implements Command {
    private EntityManagerFactory entityManagerFactory;

    public Create(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public JsonNodeBuilder execute(Map<String, String> params) {
        String eventName = params.get("eventName");
        String eventType = params.get("eventType");
        String userName = params.get("userName");

        EntityManager manager = entityManagerFactory.createEntityManager();

        Organisator organisator = manager.createQuery(
                "SELECT DISTINCT Organisator " +
                        "FROM User " +
                        "JOIN Organisator " +
                        "WHERE User.name = :userName", Organisator.class)
                .setParameter("userName", userName)
                .getSingleResult();

        Event event = new Event();
        event.setTitle(eventName);

        // TODO Eventtyp fehlt (Einzeltermine?)
        //if(eventType.equals("single")) {
        //    event.setSingle(true);
        //}

        event.setOrganisator(organisator);

        // TODO
        // response erstellen

        // TODO Exceptions moeglich?
        // -> Fehlermeldung als error an client senden.
        //    auch in anderen Klassen

        return null;
    }
}
