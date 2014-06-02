package kehd.bigpicture.logic.commands.notifications;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.logic.commands.Command;

import javax.persistence.EntityManagerFactory;
import java.util.Map;

/**
 * Created by jakob on 6/2/14.
 */
public class DeleteNotification implements Command {
    private EntityManagerFactory entityManagerFactory;

    public DeleteNotification(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public JsonNodeBuilder execute(Map<String, String> params) {
        return null;
    }
}
