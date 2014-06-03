package kehd.bigpicture.logic.commands.notifications;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.logic.commands.Command;

import javax.persistence.EntityManagerFactory;
import java.util.Map;

/**
 *
 */
public class GetNotifications implements Command {
    private EntityManagerFactory entityManagerFactory;

    public GetNotifications(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public JsonNodeBuilder execute(String username, Map<String, String> params) {
        return null;
    }
}
