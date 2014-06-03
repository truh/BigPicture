package kehd.bigpicture.logic.commands.events;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.model.Invitation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

public class Invite implements Command {
    private EntityManagerFactory entityManagerFactory;

    public Invite(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public JsonNodeBuilder execute(String username, Map<String, String> params) {
        String eventName = params.get("eventName");
        String date = params.get("date");
        String user = params.get("user");

        EntityManager manager = entityManagerFactory.createEntityManager();

        Invitation invitation = new Invitation();

        try {
            Date parsedDate = DATE_FORMAT.parse(date);
            // TODO setter doesn't exist
            //invitation.setDate(date);
        } catch (ParseException e) {
            // TODO return error response
            return null;
        }

        // TODO response
        return null;
    }
}
