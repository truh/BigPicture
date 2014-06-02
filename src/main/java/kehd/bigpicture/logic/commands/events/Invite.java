package kehd.bigpicture.logic.commands.events;

import kehd.bigpicture.Main;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.model.Invitation;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

public class Invite implements Command {

    @Override
    public String execute(Map<String, String> params) {
        String eventName = params.get("eventName");
        String date = params.get("date");
        String user = params.get("user");

        EntityManager manager = Main.getEntityManagerFactory().createEntityManager();

        Invitation invitation = new Invitation();

        try {
            Date parsedDate = DATE_FORMAT.parse(date);
            // TODO setter doesn't exist
            //invitation.setDate(date);
        } catch (ParseException e) {
            // TODO return error response
            return "";
        }

        // TODO response
        return null;
    }
}
