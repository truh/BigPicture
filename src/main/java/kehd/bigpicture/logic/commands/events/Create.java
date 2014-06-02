package kehd.bigpicture.logic.commands.events;

import kehd.bigpicture.Main;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.model.Event;
import kehd.bigpicture.model.Organisator;

import javax.persistence.EntityManager;
import java.util.Map;

public class Create implements Command {

    @Override
    public String execute(Map<String, String> params) {
        String eventName = params.get("eventName");
        String eventType = params.get("eventType");
        String userName = params.get("userName");

        EntityManager manager = Main.getEntityManagerFactory().createEntityManager();

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
