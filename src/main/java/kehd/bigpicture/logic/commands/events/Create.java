package kehd.bigpicture.logic.commands.events;

import kehd.bigpicture.Main;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.model.Event;
import kehd.bigpicture.model.User;

import javax.persistence.EntityManager;
import java.util.Map;

public class Create implements Command {

    @Override
    public String execute(Map<String, String> params) {
        String eventName = params.get("eventName");
        String eventType = params.get("eventType");
        String userName = params.get("userName");

        EntityManager manager = Main.getEntityManagerFactory().createEntityManager();

        User organisator = manager.createQuery(
                "SELECT DISTINCT User " +
                        "FROM User " +
                        "WHERE User.name = :userName", User.class)
                .setParameter("userName", userName)
                .getSingleResult();

        Event event = new Event();
        event.setTitle(eventName);

        // TODO Eventtyp fehlt (Einzeltermine?)
        //if(eventType.equals("single")) {
        //    event.setSingle(true);
        //}

        // TODO
        // vermutlich ein problem im model
        //event.setOrganisator(organisator);

        // TODO
        // response erstellen

        // TODO Exceptions möglich?
        // -> Fehlermeldung als error an client senden.
        //    auch in anderen Klassen

        return null;
    }
}
